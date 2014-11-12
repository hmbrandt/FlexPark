package de.tud.wise.caredroid.db;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.model.Path;
import de.tud.wise.caredroid.model.Patient;
import de.tud.wise.caredroid.model.Visitor;
import de.tud.wise.caredroid.model.VisitorReturner;

import android.content.Context;

/**
 * This class should be used by the controller for saving objects into the database
 * 
 * @author Mario
 *
 */
public class Resource {

	private final static Map<Class<? extends MetaObject>, Class<?>> classMapping;
	
	static {
		classMapping = new HashMap<Class<? extends MetaObject>, Class<?>>();
		classMapping.put(Path.class, PathsDataSource.class);
		classMapping.put(Patient.class, PatientDataSource.class);

	};
	
	Map<MetaObject, Context> objects;
	
	public Resource(){
		objects = new HashMap<MetaObject, Context>();
	}
	
	public void save(MetaObject object, Context context){
		objects.put(object, context);
	}
	
	public void savePersistent(){
		
		for(MetaObject object : objects.keySet()){
			
			
			object.accept(new VisitorReturner(), objects.get(object));
			
			Class<?> c = classMapping.get(object).getClass();
			
			Class.forName(c.getName())
			
			GeneralDataSource x = new PatientDataSource(objects.get(object));
		
		}
	}
	
}
