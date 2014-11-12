package de.tud.wise.caredroid.db;

import java.util.HashMap;
import java.util.Map;

import de.tud.wise.caredroid.model.MetaObject;
import android.content.Context;

/**
 * This class should be used by the controller for updating objects in the database
 * 
 * @author Mario
 *
 */
public class Repository {

	Map<MetaObject, Context> changedObjects;

	public Repository() {
		changedObjects = new HashMap<MetaObject, Context>();
	}

	/**
	 * Call this method to mark that an object has been changed
	 * call savePersistent() to execute the database requests
	 * 
	 * @param object The object that should be saved to the database
	 * @param context The context in which the object resides
	 */
	public void save(MetaObject object, Context context) {
		changedObjects.put(object, context);
	}

	/**
	 * Saves all changed objects persistently to the database
	 */
	public void savePersistent() {

		for (MetaObject metaObject : changedObjects.keySet()) {

			RepositoryHelper resourceHelper = new RepositoryHelper();

			resourceHelper.save(metaObject, changedObjects.get(metaObject));

		}

		changedObjects.clear();
	}
}
