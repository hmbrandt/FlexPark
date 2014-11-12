package de.tud.wise.caredroid.gateways;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import de.tud.wise.caredroid.db.PatientDataSource;
import de.tud.wise.caredroid.model.Patient;

/**
 * Class for adding, getting or deleting Patients. Should be used by the controller.
 * 
 * @author Benni
 *
 */
public class PatientGateway extends Gateway {

	public PatientGateway(Context context) {
		super(context);
	}

	/**
	 * Add a patient to the Database
	 * 
	 * @param pat the Patient to be added
	 * @return the Patient Object with its Database ID
	 */
	public Patient addPatientToDB(Patient pat) {
		PatientDataSource source = new PatientDataSource(context);
		source.open();
		try {
			pat.setId((int) source.add(pat));
		} catch (SQLiteException e) {
			Log.e("SQLError", "Adding Patient failed!");
			e.printStackTrace();
			source.close();
			return null;
		}
		source.close();
		return pat;
	}

	/**
	 * Get a Patient from the Database by his ID
	 * 
	 * @param id the Id of the patient to be get
	 * @return the Patient to be retrieved
	 */
	public Patient getPatientFromDB(int id) {
		PatientDataSource source = new PatientDataSource(context);
		source.open();
		Patient pat = source.get(id);
		source.close();
		return pat;
	}

	/**
	 * Get all Patients from the Database
	 * 
	 * @return List of all Patients in the Database
	 */
	public List<Patient> getAllPatientsFromDB() {
		PatientDataSource source = new PatientDataSource(context);
		source.open();
		List<Patient> patList = source.getAll();
		source.close();
		return patList;
	}

	/**
	 * Delete a Patient from the Database
	 * 
	 * @param id the id of the patient that is to be deleted
	 */
	public void deletePatientFromDB(int id) {
		PatientDataSource source = new PatientDataSource(context);
		source.open();
		try {
			source.delete(id);
		} catch (SQLiteException e) {
			Log.e("SQLError", "Deleting Patient failed!");
			e.printStackTrace();
		}
		source.close();
	}
}
