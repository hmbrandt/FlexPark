package de.tud.wise.caredroid.model;

import android.content.Context;
import de.tud.wise.caredroid.db.GeneralDataSource;

/**
 * Beschreibt Dokumente, die Schritten zugeordnet werden können.
 * 
 * @author Benni
 *
 */
public class Document extends MetaObject {

	private Integer id;
	private String name;

	public Document(String cpModId, int id, String name) {
		super(cpModId);
		this.setId(id);
		this.setName(name);
	}

	@Override
	public GeneralDataSource getDataSource(Context context) {
		return null;
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

}
