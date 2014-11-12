package de.tud.wise.caredroid.model;

import android.content.Context;
import de.tud.wise.caredroid.db.CaseDataSource;
import de.tud.wise.caredroid.db.GeneralDataSource;

/**
 * Die Klasse, die einen einzelnen Fall beschreibt, der einem Patienten zugeordnet wird und dem ein einzelner Pfad zugeordnet werden kann. Jeder Fall kann nur einen Pfad haben und nur einem Patienten zugehörig sein.
 * 
 * @author Benni
 *
 */
public class Case extends MetaObject {
	private Integer id;
	private String name;
	private Path path;
	private Patient patient;

	public Case(String cpModId, Integer id, String name, Path path, Patient patient) {
		super(cpModId);
		this.id = id;
		this.setName(name);
		this.path = path;
		this.patient = patient;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public GeneralDataSource getDataSource(Context context) {
		return new CaseDataSource(context);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Case: ID: " + getId() + ", Name: " + getName() + " \nPatient: " + getPatient().toString() + " \nPath: " + getPath().toString();
	}

}
