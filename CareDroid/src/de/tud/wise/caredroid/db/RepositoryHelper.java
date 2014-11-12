package de.tud.wise.caredroid.db;

import de.tud.wise.caredroid.model.MetaObject;
import android.content.Context;

/**
 * Helper for getting the right DataSource and updating with it.
 * 
 * @author Mario
 *
 */
public class RepositoryHelper {

	/**
	 * 
	 * This methods initiates the appropriate DataSource Handler
	 * and returns it
	 * 
	 * @param object The object for which the DataHandler should be instantiated
	 * @param context The context of the object
	 * @return The DataSource Handler
	 */
	private GeneralDataSource instantiateDataSourceHandler(MetaObject object, Context context) {
		return object.getDataSource(context);
	}

	/**
	 * This method executes the save to the database
	 * 
	 * @param object The object to be saved
	 * @param context The context of the object
	 */
	public void save(MetaObject object, Context context) {

		GeneralDataSource d = this.instantiateDataSourceHandler(object, context);

		d.open();

		d.update(object);

		d.close();

	}

}
