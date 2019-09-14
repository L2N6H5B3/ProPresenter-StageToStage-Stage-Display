package stageonjava.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import stageonjava.model.StageDisplayData;

/**
 * @author Luke Bradtke
 * @version 1.3
 * @since 1.0
 */

public class DataReader {
	
	// Create variable to hold DisplayLayouts object
	private StageDisplayData fields = null;
    
	// Create Objects from XML String
    public StageDisplayData convertToObjects(String xmlString) {
    	
    	JAXBContext jaxbContext;
    	try {
    	    jaxbContext = JAXBContext.newInstance(StageDisplayData.class);             
    	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    	    // Create Java Objects using JAXB
    	    fields = (StageDisplayData) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
    	} catch (JAXBException e) {
    	    System.out.println("Error creating StageDisplayField objects from XML... "+e);
    	    return fields;
    	}
    	return fields;
    }
}
