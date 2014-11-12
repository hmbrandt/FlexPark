package de.tud.wise.caredroid.model;

import java.util.ArrayList;

<<<<<<< .mine
import android.content.Context;


=======
import android.content.Context;
import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.PatientDataSource;

>>>>>>> .r71
/**
 * Beschreibt Patienten und ihre Stammdaten. Patienten werden Fälle zugeordnet.
 * 
 * @author Benni
 *
 */
<<<<<<< .mine
public class Patient extends MetaObject{
=======
public class Patient extends MetaObject {
>>>>>>> .r71
	private Integer id;
	private String name;
	private String adresse;
	private String versNummer;
	private final ArrayList<Case> cases;

	public Patient(String cpModId, Integer id, String name, String adresse, String versN) {
		super(cpModId);
		this.id = id;
		this.name = name;
		this.adresse = adresse;
		this.versNummer = versN;
		this.cases = new ArrayList<Case>();
	}

	public ArrayList<Case> getCases() {
		return cases;
	}

	/**
	 * Fügt neuen Case zum Patienten hinzu
	 * @param newCase neues Case-Objekt
	 * @return true wenn erfolgreich, false wenn nicht
	 */
	public boolean addCase(Case newCase) {
		if (cases.add(newCase)) {
			return true;
		}
		return false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getVersNummer() {
		return versNummer;
	}

	public void setVersNummer(String versNummer) {
		this.versNummer = versNummer;
	}

<<<<<<< .mine
	@Override
	public void accept(Visitor visitor, Context context) {
		// TODO Auto-generated method stub
		
	}
=======
	@Override
	public GeneralDataSource getDataSource(Context context) {
		return new PatientDataSource(context);
	}

	@Override
	public String toString() {
		return "ID: " + getId() + ", Name: " + getName() + ", Adresse: " + getAdresse() + ", VN: " + getVersNummer();
	}
>>>>>>> .r71
}
