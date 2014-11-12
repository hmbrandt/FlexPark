package de.tud.wise.caredroid.model;

import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.PathsDataSource;
import de.tud.wise.caredroid.db.PatientDataSource;
import android.content.Context;

public class VisitorReturner implements Visitor{

	public PathsDataSource visit(Path path, Context context) {
		return new PathsDataSource(context);
	}

	public PatientDataSource visit(Patient patient, Context context) {
		return new PatientDataSource(context);
	}
}
