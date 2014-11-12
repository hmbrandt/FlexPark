package de.tud.wise.caredroid.model;

import de.tud.wise.caredroid.db.GeneralDataSource;
import android.content.Context;

public interface Visitable {

	GeneralDataSource accept(Visitor visitor, Context context);
	
}
