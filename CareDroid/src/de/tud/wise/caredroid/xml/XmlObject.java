package de.tud.wise.caredroid.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlObject{

	private String id;
	private List<String> connectedObjects;
	private List<String> connectedConnectors;
	
	private Map<String, String> objectProperty;
	private Map<String, String> name;
	private Map<String, String> information;
	private Map<String, String> presentationObject;
	private List<Map<Map<String,String>, Map<String,String>>> e3Property;
	
	public XmlObject(){
		
	}
	
	public Map<String, String> getObjectProperty() {
		return objectProperty;
	}

	public void setObjectProperty(Map<String, String> objectProperty) {
		this.objectProperty = objectProperty;
		this.id = objectProperty.get("id");
	}

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public Map<String, String> getInformation() {
		return information;
	}

	public void setInformation(Map<String, String> information) {
		this.information = information;
	}

	public Map<String, String> getPresentationObject() {
		return presentationObject;
	}

	public void setPresentationObject(Map<String, String> presentationObject) {
		this.presentationObject = presentationObject;
	}
	
	public void addPresentationPropertyToE3Property(Map<String, String> e3Property, Map<String, String> presentationProperty){
		for(Map<Map<String,String>,Map<String,String>> e3 : this.e3Property){
			if(e3.containsKey(e3Property)){
				e3.put(e3Property, presentationProperty);
				break;
			}
		}
	}
	
	public void addE3Property(Map<String, String> e3Property){
		if(this.e3Property == null){
			this.e3Property = new ArrayList<Map<Map<String,String>,Map<String,String>>>();
		}
		
		Map<Map<String,String>, Map<String,String>> e3 = new HashMap<Map<String,String>, Map<String,String>>();
		
		e3.put(e3Property,  null);
		this.e3Property.add(e3);
	}

	public List<Map<Map<String, String>, Map<String, String>>> getE3Property() {
		return e3Property;
	}

	public void setE3Property(
			List<Map<Map<String, String>, Map<String, String>>> e3Property) {
		this.e3Property = e3Property;
	}
	
}
