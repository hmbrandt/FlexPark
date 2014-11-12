package de.tud.wise.caredroid.gateways;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import de.tud.wise.caredroid.db.RoleDataSource;
import de.tud.wise.caredroid.model.Role;

/**
 * Class for adding, getting or deleting Patients. Can be used by the controller.
 * 
 * @author Benni
 *
 */
public class RoleGateway extends Gateway {

	public RoleGateway(Context context) {
		super(context);
	}

	/**
	 * Adds a Role to the Database
	 * 
	 * @param r the Role to be added
	 * @param duplicateCheckMap a Map to check for duplicate Roles in the current Insert operation
	 * @return the Role Object added with its Database ID
	 */
	public Role addRoleToDB(Role r, Map<String, Integer> duplicateCheckMap) {
		long id;
		if (duplicateCheckMap.get(r.getCpModId()) == null) {
			RoleDataSource source = new RoleDataSource(context);
			source.open();
			try {
				id = source.add(r);
			} catch (SQLiteException e) {
				Log.e("SQLError", "Adding Role failed!");
				e.printStackTrace();
				source.close();
				return null;
			}
			source.close();
			duplicateCheckMap.put(r.getCpModId(), (int) id);
		} else {
			id = duplicateCheckMap.get(r.getCpModId());
		}
		r.setId((int) id);
		return r;
	}

	/**
	 * Gets a Role by its id from the Database
	 * 
	 * @param id the id of the role to be get
	 * @return the retrieved Role Object
	 */
	public Role getRoleFromDB(int id) {
		RoleDataSource source = new RoleDataSource(context);
		source.open();
		Role r = source.get(id);
		source.close();
		return r;
	}

	/**
	 * Gets all Roles in the Database
	 * 
	 * @return List of all Roles in the Database
	 */
	public List<Role> getAllRolesFromDB() {
		RoleDataSource source = new RoleDataSource(context);
		source.open();
		List<Role> roleList = source.getAll();
		source.close();
		return roleList;
	}

	/**
	 * Deletes a Role from the Database
	 * 
	 * @param id the id of the Role that is to be deleted
	 */
	public void deleteRoleFromDB(int id) {
		RoleDataSource source = new RoleDataSource(context);
		source.open();
		try {
			source.delete(id);
		} catch (SQLiteException e) {
			Log.e("SQLError", "Adding Role failed!");
			e.printStackTrace();
		}
		source.close();
	}
}
