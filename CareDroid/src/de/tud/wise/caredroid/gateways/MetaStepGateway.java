package de.tud.wise.caredroid.gateways;

import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import de.tud.wise.caredroid.db.DecisionDataSource;
import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.StepDataSource;
import de.tud.wise.caredroid.db.VariableFlowDataSource;
import de.tud.wise.caredroid.model.Decision;
import de.tud.wise.caredroid.model.MetaStep;
import de.tud.wise.caredroid.model.Step;
import de.tud.wise.caredroid.model.VariableFlow;

/**
 * Class for adding, getting or deleting MetaSteps. Should only be used by other Gateways.
 * 
 * @author Benni
 *
 */
public class MetaStepGateway extends Gateway {

	public MetaStepGateway(Context context) {
		super(context);
	}

	/**
	 * Method for adding MetaSteps to the database. All connections are considered and added recursively
	 * 
	 * @param mStep the MetaStep to be added to the Database
	 * @param duplicateCheckMap a Map to check for duplicates in the current Insert Operation
	 * @return the id of the added MetaStep in the database
	 * @throws IllegalArgumentException if no CPModID is set for this object
	 */
	public long addMetaStepToDB(MetaStep mStep, Map<String, Integer> duplicateCheckMap) {

		for (MetaStep m : mStep.getChildren()) {
			m.setId((int) addMetaStepToDB(m, duplicateCheckMap));
		}

		if (mStep.getCpModId() == null) {
			throw new IllegalArgumentException("A Step with CPModID null cant be added to the database. Please use the given factories for building valid Objects for DB Operations");
		}

		long id = -1;
		if (mStep instanceof Step) {
			RoleGateway rGate = new RoleGateway(context);
			((Step) mStep).setRole(rGate.addRoleToDB(((Step) mStep).getRole(), duplicateCheckMap));
			if (duplicateCheckMap.get(mStep.getCpModId()) == null) {
				GeneralDataSource source = new StepDataSource(context);
				source.open();
				try {
					id = source.add(mStep);
				} catch (SQLiteException e) {
					Log.e("SQLError", "Adding Step failed!");
					e.printStackTrace();
				}
				source.close();
				duplicateCheckMap.put(mStep.getCpModId(), (int) id);
			} else {
				id = duplicateCheckMap.get(mStep.getCpModId());
			}
		} else if (mStep instanceof Decision) {
			RoleGateway rGate = new RoleGateway(context);
			((Decision) mStep).setRole(rGate.addRoleToDB(((Decision) mStep).getRole(), duplicateCheckMap));
			if (duplicateCheckMap.get(mStep.getCpModId()) == null) {
				GeneralDataSource source = new DecisionDataSource(context);
				source.open();
				try {
					id = source.add(mStep);
				} catch (SQLiteException e) {
					Log.e("SQLError", "Adding Decision failed!");
					e.printStackTrace();
				}
				source.close();
				duplicateCheckMap.put(mStep.getCpModId(), (int) id);
			} else {
				id = duplicateCheckMap.get(mStep.getCpModId());
			}
		} else if (mStep instanceof VariableFlow) {
			for (Step s : ((VariableFlow) mStep).getParts()) {
				s.setId((int) addMetaStepToDB(s, duplicateCheckMap));
			}
			if (duplicateCheckMap.get(mStep.getCpModId()) == null) {
				GeneralDataSource source = new VariableFlowDataSource(context);
				source.open();
				try {
					id = source.add(mStep);
				} catch (SQLiteException e) {
					Log.e("SQLError", "Adding VariableFlow failed!");
					e.printStackTrace();
				}
				source.close();
				duplicateCheckMap.put(mStep.getCpModId(), (int) id);
			} else {
				id = duplicateCheckMap.get(mStep.getCpModId());
			}
		}
		return (int) id;
	}

	/**
	 * Method for getting a MetaStep and all its interconnections from the Database
	 * 
	 * @param id the id of the MetaStep to be retrieved from the Database
	 * @return the MetaStep retrieved from the Database
	 */
	public MetaStep getMetaStepFromDB(int id) {
		MetaStep mStep = null;
		String type;
		int[] childs;
		int[] parts;

		//irgendeine DataSource weil nur auf Methoden der GeneralDataSource zugegriffen wird
		GeneralDataSource startSource = new StepDataSource(context);
		startSource.open();
		try {
			type = startSource.getTypeOfMetaStep(id);
		} catch (SQLiteDatabaseCorruptException e) {
			Log.e("SQLError", "Getting Type of MetaStep failed!");
			e.printStackTrace();
			startSource.close();
			return null;
		}
		try {
			childs = startSource.getPathConnections(id);
		} catch (Exception e) {
			Log.e("SQLError", "Getting PathConnections of MetaStep failed!");
			e.printStackTrace();
			startSource.close();
			return null;
		}
		startSource.close();

		if (type.equals("step")) {
			int roleId;
			StepDataSource source = new StepDataSource(context);
			source.open();
			Step s = source.get(id);
			try {
				roleId = source.getIdOfRole(id);
			} catch (SQLiteDatabaseCorruptException e) {
				Log.e("SQLError", "Getting Role of MetaStep failed!");
				e.printStackTrace();
				source.close();
				return null;
			}
			source.close();

			RoleGateway rGate = new RoleGateway(context);
			s.setRole(rGate.getRoleFromDB(roleId));
			mStep = s;
		} else if (type.equals("decision")) {
			int roleId;
			DecisionDataSource source = new DecisionDataSource(context);
			source.open();
			Decision d = source.get(id);
			try {
				roleId = source.getIdOfRole(id);
			} catch (SQLiteDatabaseCorruptException e) {
				Log.e("SQLError", "Getting Role of MetaStep failed!");
				e.printStackTrace();
				source.close();
				return null;
			}
			source.close();

			RoleGateway rGate = new RoleGateway(context);
			d.setRole(rGate.getRoleFromDB(roleId));
			mStep = d;
		} else if (type.equals("flow")) {
			VariableFlowDataSource source = new VariableFlowDataSource(context);
			source.open();
			VariableFlow v = source.get(id);
			try {
				parts = source.getFlowConnections(id);
			} catch (SQLiteDatabaseCorruptException e) {
				Log.e("SQLError", "Getting FlowConnections of MetaStep failed!");
				e.printStackTrace();
				source.close();
				return null;
			}
			source.close();
			if (parts.length != 0) {
				for (int i = 0; i < parts.length; i++) {
					MetaStep part = getMetaStepFromDB(parts[i]);
					if (part instanceof Step == false) {
						Log.e("ConsistencyError", "Non-Step Object is part of a VariableFlow");
						continue;
					}
					v.addPart((Step) part);
				}
			}
			mStep = v;
		} else {
			Log.e("ConsistencyError", "Type " + type + " not known");
			return null;
		}

		if (childs.length != 0) {
			for (int i = 0; i < childs.length; i++) {
				mStep.addChildren(getMetaStepFromDB(childs[i]));
			}
		}
		return mStep;
	}

	/**
	 * Method for deleting a MetaStep and all its interconnections from the Database
	 * 
	 * @param id
	 */
	public void deleteMetaStepFromDB(int id) {
		MetaStep mStep = getMetaStepFromDB(id);
		for (MetaStep child : mStep.getChildren()) {
			deleteMetaStepFromDB(child.getId());
		}
		if (mStep instanceof Step) {
			StepDataSource source = new StepDataSource(context);
			source.open();
			try {
				source.delete(id);
			} catch (SQLiteException e) {
				Log.e("SQLError", "Deleting MetaStep failed!");
				e.printStackTrace();
				source.close();
				return;
			}
			source.close();
		} else if (mStep instanceof Decision) {
			DecisionDataSource source = new DecisionDataSource(context);
			source.open();
			try {
				source.delete(id);
			} catch (SQLiteException e) {
				Log.e("SQLError", "Deleting MetaStep failed!");
				e.printStackTrace();
				source.close();
				return;
			}
			source.close();
		} else if (mStep instanceof VariableFlow) {
			for (Step part : ((VariableFlow) mStep).getParts()) {
				StepDataSource source = new StepDataSource(context);
				source.open();
				try {
					source.delete(part.getId());
				} catch (SQLiteException e) {
					Log.e("SQLError", "Deleting Part of MetaStep failed!");
					e.printStackTrace();
					source.close();
					return;
				}
				source.close();
			}
			VariableFlowDataSource source = new VariableFlowDataSource(context);
			source.open();
			try {
				source.delete(id);
			} catch (SQLiteException e) {
				Log.e("SQLError", "Deleting MetaStep failed!");
				e.printStackTrace();
				source.close();
				return;
			}
			source.close();
		}
	}
}
