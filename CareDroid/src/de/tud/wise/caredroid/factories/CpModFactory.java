package de.tud.wise.caredroid.factories;

import java.util.HashMap;
import java.util.Map;

import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.xml.XmlObject;

public class CpModFactory {

	private static final String OBJECTTYPE = "objectType";
	Map<String,String> objectTypes;
	
	public CpModFactory() {
		
		objectTypes = new HashMap<String, String>(); 
		
		objectTypes.put("0d9edc8b4006274f014006a6c74935bf", "Entscheidungskriterium");
		objectTypes.put("0d9edc8b40056dec01400604b1a45c0a", "Evidenzniveau");
		//ham wa
		objectTypes.put("0d9edc8b4006274f014006a4efc4352b", "Entscheidung");
		objectTypes.put("0d9edc8b40056dec014005ad40d75169", "Icon");
		objectTypes.put("0d9edc8b40056dec01400576ee5a023e", "Informationsfluss");
		objectTypes.put("0d9edc8b40056dec014005747e8201cb", "Prozessfluss");
		objectTypes.put("0d9edc8b40056dec0140059d043c445a", "Eigenschaft");
		objectTypes.put("0d9edc8b40056dec014005aabe2e50e0", "Typ");
		//ham wa - Attribut
		objectTypes.put("0d9edc8b4006274f014006aef28b3a36", "Ausführungslogik");
		//ham wa
		objectTypes.put("0d9edc8b40056dec014005a98ead5094", "Dokument");
		//ham wa - Attribut
		objectTypes.put("0d9edc8b40056dec01400581198a0964", "Zustand");
		objectTypes.put("0d9edc8b40056dec014006021ffb5bb3", "Evidenzanzeigerknoten");
		//ham wa - Attribut
		objectTypes.put("0d9edc8b4006274f01400643a5cf1862", "Typ");
		objectTypes.put("0d9edc8b40056dec01400570077b00e3", "Prozessknoten");
		//ham wa
		objectTypes.put("0d9edc8b4006274f014006abc1a439af", "Variable Behandlungsfolge");
		objectTypes.put("0d9edc8b40056dec01400582416209b8", "Annotation");
		//ham wa
		objectTypes.put("0d9edc8c426a7ae701426b6a1e65278a", "Rolle");
		objectTypes.put("0d9edc8c426a7ae701426b6a625f279d", "Rollenknoten");
		objectTypes.put("0d9edc8b40056dec014005819f06098e", "Annotationsknoten");
		//ham wa - aber als Attribut
		objectTypes.put("0d9edc8b40056dec01400602bac65bdd", "Evidenzindikator");
		objectTypes.put("0d9edc8b4006274f0140063ecaf417b3", "Behandlungsschritt");
		objectTypes.put("0d9edc8b40056dec014005737de20165", "Informationsknoten");
		objectTypes.put("0d9edc8b4006274f014006ceac5b4059", "Regionsknoten");
		objectTypes.put("0d9edc8b40056dec014005ac7e9a5137", "IconableObject");
	}
	
	public String getObjectType(XmlObject xmlObject){
		
		Map<String, String> objectProperties = xmlObject.getObjectProperty();
		
		String objectType = objectProperties.get(OBJECTTYPE);
		
		return objectTypes.get(objectType);
	}
	
	public MetaObject instantiateObject(XmlObject xmlObject){
		
		String objectType = this.getObjectType(xmlObject);
		
		if(objectType.equals("Behandlungsschritt")){
			System.out.println(xmlObject.getName());
			System.out.println(xmlObject.getE3Property());
			System.out.println(xmlObject.getInformation());
			System.out.println(xmlObject.getObjectProperty());
			System.out.println(xmlObject.getPresentationObject());
		}
		
		
		return null;
	}
	
}
