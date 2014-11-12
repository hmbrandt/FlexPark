package de.tud.wise.caredroid.model;

import android.content.Context;
import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.RoleDataSource;

/**
 * Beschreibt eine Rolle, die in einem Schritt oder einer Entscheidung festgelegt sein kann und Aufschluss über die Zuordnung gibt.
 * 
 * @author Benni
 *
 */
public class Role extends MetaObject {
	private Integer id;
	private String name;

	public Role(String cpModId, Integer id, String name) {
		super(cpModId);
		this.id = id;
		this.name = name;
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

	@Override
	public GeneralDataSource getDataSource(Context context) {
		return new RoleDataSource(context);
	}

	@Override
	public String toString() {
		return "RName: " + this.name;
	}
}
