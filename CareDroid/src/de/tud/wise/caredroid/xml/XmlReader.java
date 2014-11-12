package de.tud.wise.caredroid.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import de.tud.wise.caredroid.factories.CpModFactory;
import de.tud.wise.caredroid.model.MetaObject;

/**
 * 
 * @author Mario
 *
 */
public class XmlReader {
	
    public List<MetaObject> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            
            List<XmlObject> xmlObjects = readModel(parser);
            
            System.out.println(xmlObjects.size());
            
            CpModFactory cpModFactory = new CpModFactory();
            
            for(XmlObject xmlObject : xmlObjects){
            	cpModFactory.instantiateObject(xmlObject);
            }
            
            return null;
        } finally {
            in.close();
        }
    }
    
    private List<XmlObject> readModel(XmlPullParser parser) throws XmlPullParserException, IOException {

    	List<XmlObject> xmlObjects = new ArrayList<XmlObject>();
    	
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("object")){
            	//parse here anything within the model:
            	xmlObjects.add(readObject(parser));
            }
        }
        
        return xmlObjects;
    }
    
    private XmlObject readObject(XmlPullParser parser) throws XmlPullParserException, IOException {
       
    	XmlObject xmlObject = new XmlObject();
    	
    	xmlObject.setObjectProperty(this.getAttributes(parser));
    	
    	Map<String,String> e3Property = new HashMap<String, String>();
    	
    	while (parser.next() != XmlPullParser.END_TAG || !parser.getName().equals("object")) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();            
            
            Map<String,String> attributes = this.getAttributes(parser);
        	
            if(attributes != null){
                
            	if(name.equals("name")) xmlObject.setName(attributes);
            	else if(name.equals("information")) xmlObject.setInformation(attributes);
            	else if(name.equals("presentationObject")) xmlObject.setPresentationObject(attributes);
            	else if(name.equals("e3Property")) {
            		e3Property = new HashMap<String, String>();
            		e3Property.putAll(attributes);
            		xmlObject.addE3Property(attributes);
            	}
            	else if(name.equals("presentationProperty")){            		
            		xmlObject.addPresentationPropertyToE3Property(e3Property, attributes);
            	}
            }
        }
        return xmlObject;
    }
    
    /**
     * Return a map with all attributes of the given tag
     * 
     * @param parser The XmlPullParser
     * @return The attribute map of the element that the PullParser is at
     */
    private Map<String,String> getAttributes(XmlPullParser parser){
        Map<String,String> attrs=null;
        int account=parser.getAttributeCount();
        if(account != -1) {
            attrs = new HashMap<String,String>(account);
            for(int x=0;x<account;x++) {
                attrs.put(parser.getAttributeName(x), parser.getAttributeValue(x));
            }
        }
        else {
            return null;
        }
        return attrs;
    }
	
}
