package de.tud.wise.caredroid.gateways;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import de.tud.wise.caredroid.db.CaseDataSource;
import de.tud.wise.caredroid.model.Case;

/**
 * Class for adding, getting or deleting Cases. Should be used by the controller.
 * 
 * @author Benni
 *
 */
public class CaseGateway extends Gateway {

	public CaseGateway(Context context) {
		super(context);
	}

	/**
	 * Method to add a Case to the Database
	 * 
	 * @param c the Case to be added
	 * @return the Case Object with its Database ID
	 */
	public Case addCaseToDB(Case c) {
		PathGateway pGate = new PathGateway(context);
		c.setPath(pGate.addPathToDB(c.getPath()));

		PatientGateway patGate = new PatientGateway(context);
		c.setPatient(patGate.addPatientToDB(c.getPatient()));

		CaseDataSource source = new CaseDataSource(context);
		source.open();
		try {
			c.setId((int) source.add(c));
		} catch (SQLiteException e) {
			Log.e("SQLError", "Adding Case failed!");
			e.printStackTrace();
			source.close();
			return null;
		}
		source.close();

		return c;
	}

	/**
	 * Method to get a Case from the Database
	 * 
	 * @param id the id of the case to be get
	 * @return the Case to be retrieved
	 */
	public Case getCaseFromDB(int id) {
		CaseDataSource source = new CaseDataSource(context);
		source.open();
		Case c = null;
		int pathId = -1;
		int patId = -1;
		try {
			c = source.get(id);

		} catch (SQLiteException e) {
			Log.e("SQLError", "Getting Case failed!");
			e.printStackTrace();
			source.close();
			return null;
		}

		try {
			pathId = source.getIdOfPath(id);
		} catch (SQLiteDatabaseCorruptException e) {
			Log.e("SQLError", "Getting Path Id of Case failed!");
			e.printStackTrace();
			source.close();
			return null;
		}

		try {
			patId = source.getIdOfPatient(id);
		} catch (SQLiteDatabaseCorruptException e) {
			Log.e("SQLError", "Getting Patient Id of Case failed!");
			e.printStackTrace();
			source.close();
			return null;
		}
		source.close();

		PathGateway pFact = new PathGateway(context);
		c.setPath(pFact.getPathFromDB(pathId));

		PatientGateway patFact = new PatientGateway(context);
		c.setPatient(patFact.getPatientFromDB(patId));

		return c;
	}

	/**
	 * Method to get all Cases from the Database
	 * 
	 * @return List of all Case Objects in the Database
	 */
	public List<Case> getAllCasesFromDB() {
		List<Case> caseList = new ArrayList<Case>();
		CaseDataSource source = new CaseDataSource(context);
		source.open();
		int[] caseIds = source.getIdOfAllCases();
		for (int i = 0; i < caseIds.length; i++) {
			caseList.add(getCaseFromDB(caseIds[i]));
		}
		source.close();
		return caseList;
	}

	/**
	 * Method to get all Cases for one Patient by his Id
	 * 
	 * @param patId the Id of the Patient to be filtered by
	 * @return List of all Case Objects for this Patient
	 */
	public List<Case> getAllCasesFromDBForPatient(int patId) {
		List<Case> caseList = new ArrayList<Case>();
		CaseDataSource source = new CaseDataSource(context);
		source.open();
		int[] caseIds = source.getIdOfAllCasesByPatient(patId);
		for (int i = 0; i < caseIds.length; i++) {
			caseList.add(source.get(caseIds[i]));
		}
		source.close();
		return caseList;
	}

	/**
	 * Method to delete a Case and all its Connections from the Database
	 * 
	 * @param id the id of the Case to be deleted
	 */
	public void deleteCaseFromDB(int id) {
		CaseDataSource source = new CaseDataSource(context);
		int pathId;
		source.open();
		try {
			pathId = source.getIdOfPath(id);
		} catch (SQLiteDatabaseCorruptException e) {
			Log.e("SQLError", "Getting Path Id of Case failed!");
			e.printStackTrace();
			source.close();
			return;
		}
		try {
			source.delete(id);
		} catch (SQLiteException e) {
			Log.e("SQLError", "Deleting Case failed!");
			e.printStackTrace();
			source.close();
			return;
		}
		source.close();
		PathGateway pGate = new PathGateway(context);
		pGate.deletePathFromDB(pathId);
	}
}
