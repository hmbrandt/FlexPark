package de.tud.wise.caredroid.gateways;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.PathDataSource;
import de.tud.wise.caredroid.model.MetaStep;
import de.tud.wise.caredroid.model.Path;

/**
 * Class for adding, getting or deleting Paths. Should only be used by other Gateways.
 * 
 * @author Benni
 *
 */
public class PathGateway extends Gateway {

	public PathGateway(Context context) {
		super(context);
	}

	/**
	 * Method for adding a path and all his objects to the Database. Uses MetaStepGateway
	 * 
	 * @param p the path Object to be added
	 * @return the Path object with its new Database Id
	 */
	public Path addPathToDB(Path p) {
		Map<String, Integer> duplicateCheckMap = new HashMap<String, Integer>();
		MetaStep node = p.getNode();
		MetaStepGateway mFact = new MetaStepGateway(context);
		node.setId((int) mFact.addMetaStepToDB(node, duplicateCheckMap));
		GeneralDataSource source = new PathDataSource(context);
		source.open();
		try {
			p.setId((int) source.add(p));
		} catch (SQLiteException e) {
			Log.e("SQLError", "Adding Path failed!");
			e.printStackTrace();
			source.close();
			return null;
		}
		source.close();
		return p;
	}

	/**
	 * Method for getting a Path and all its connected Objects from the Database
	 * 
	 * @param id the id of the Path to be get
	 * @return the Path object to be retrieved
	 */
	public Path getPathFromDB(int id) {
		int firstId;
		PathDataSource source = new PathDataSource(context);
		source.open();
		Path p = source.get(id);
		try {
			firstId = source.getIdOfFirstStep(id);
		} catch (SQLiteDatabaseCorruptException e) {
			Log.e("SQLError", "Getting Node of Path failed!");
			e.printStackTrace();
			source.close();
			return null;
		}
		source.close();
		MetaStepGateway mFact = new MetaStepGateway(context);
		MetaStep node = mFact.getMetaStepFromDB(firstId);
		p.setNode(node);
		return p;
	}

	/**
	 * Method to delete a Path and all its connections from the Database
	 * 
	 * @param id the id of the Path to be deleted
	 */
	public void deletePathFromDB(int id) {
		int nodeID;
		MetaStepGateway mGate = new MetaStepGateway(context);
		PathDataSource source = new PathDataSource(context);
		source.open();
		try {
			nodeID = source.getIdOfFirstStep(id);
		} catch (SQLiteDatabaseCorruptException e) {
			Log.e("SQLError", "Getting Node of Path failed!");
			e.printStackTrace();
			source.close();
			return;
		}
		try {
			source.delete(id);
		} catch (SQLiteException e) {
			Log.e("SQLError", "Deleting Path failed!");
			e.printStackTrace();
			source.close();
			return;
		}
		source.close();
		mGate.deleteMetaStepFromDB(nodeID);
	}
}
