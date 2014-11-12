package de.tud.wise.caredroid.model;

import de.tud.wise.caredroid.db.GeneralDataSource;
import android.content.Context;

public interface Visitor {

	GeneralDataSource visit(Path path, Context context);
	
	GeneralDataSource visit(Patient patient, Context context);
}
