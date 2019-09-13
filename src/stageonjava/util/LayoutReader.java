package stageonjava.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import stageonjava.model.DisplayLayouts;

/**
 * @author Luke Bradtke
 * @version 1.3
 * @since 1.0
 */
public class LayoutReader {
	
    
    public void convertToObjects(String xmlString) {
    	
    	JAXBContext jaxbContext;
    	try {
    	    jaxbContext = JAXBContext.newInstance(DisplayLayouts.class);             
    	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    	    DisplayLayouts frame = (DisplayLayouts) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
    	    System.out.println(frame);
    	} catch (JAXBException e) {
    	    e.printStackTrace();
    	}
    }
}
