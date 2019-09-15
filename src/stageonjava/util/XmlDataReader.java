package stageonjava.util;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Luke Bradtke
 * @version 1.3
 * @since 1.0
 */
public class XmlDataReader {

    private static final String XML_LAYOUTS_START_TAG = "<DisplayLayouts";
    private static final String XML_LAYOUTS_END_TAG = "</DisplayLayouts>";
    private static final String XML_UPDATE_START_TAG = "<StageDisplayData>";
    private static final String XML_UPDATE_END_TAG = "</StageDisplayData>";
    
    private static final String NEWLINE = "\n";
    
    private String layoutData = "";
    private String updateData = "";
    
    private boolean insideLayout;
    private boolean insideUpdate;

    public boolean readXmlData(BufferedReader in) {
        StringBuilder readXmlData = new StringBuilder(256);
        boolean readCompleted = false;
        while (!readCompleted) {
        	
            String inputLine = null;
            
            
            // Try reading input
			try {
				inputLine = in.readLine();
				System.out.println(inputLine);
			} catch (IOException e) {
				System.out.println("Error Reading Line...");
			}
			
            if (inputLine == null) {
                return false;
            }
            
            
            // Add tags to separate from, as well as variable to track inside XML or not
            if (inputLine.contains(XML_LAYOUTS_START_TAG)) {
            	readXmlData.setLength(0);
            	insideLayout = true;
            } else if (inputLine.contains(XML_UPDATE_START_TAG)) {
            	readXmlData.setLength(0);
            	insideUpdate = true;
            } 
            
            
            
            if (insideLayout) {
            	
            	readXmlData.append(inputLine);
            	
            	if (inputLine.contains(XML_LAYOUTS_END_TAG)) {
                    layoutData = readXmlData.toString();
                    insideLayout = false;
                } else {
                    readXmlData.append(NEWLINE);
                }
            } else if (insideUpdate) {
            	
            	readXmlData.append(inputLine);
                
                if (inputLine.contains(XML_UPDATE_END_TAG)) {
                    updateData = readXmlData.toString();
                    insideUpdate = false;
                    readCompleted = true;
                } else {
                    readXmlData.append(NEWLINE);
                }
            }
        }
        return true;
    }
    
    public String getUpdateXmlData() {
    	return updateData;
    }

    
    public String getLayoutXmlData() {
    	return layoutData;
    }
}
