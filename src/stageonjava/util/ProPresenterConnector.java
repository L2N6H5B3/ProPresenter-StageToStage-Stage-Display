package stageonjava.util;


import static stageonjava.util.ThreadUtil.sleep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import stageonjava.model.*;


/**
 * @author Luke Bradtke
 * @version 1.6
 * @since 1.0
 */


public class ProPresenterConnector {
	
    private static final String SUCCESSFUL_LOGIN = "<StageDisplayLoginSuccess />";
    private static final String SUCCESSFUL_LOGIN_WINDOWS = "<StageDisplayLoginSuccess>";
    
	private String slide;
	private String host;
	private int port;
	private String password;
	private String responseTime = "10";
	
	private boolean socketActive = false;
    private boolean attemptConnection = true;
    private boolean activeConnection = false;
    private boolean loginFailed = false;
    private boolean sdCreated = false;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
	private StageDisplayWindow SD;
	
    private XmlDataReader xmlDataReader = new XmlDataReader();
    private XmlParser xmlParser = new XmlParser();
    private LayoutReader layoutReader = new LayoutReader();
	
    
	public ProPresenterConnector(int port, String password) {
		this.port = port;
		this.password = password;
	}

	
	public void createSDWindow() {
	    // Create StageDisplayWindow object
	    SD = new StageDisplayWindow();
	    // Loads initial Disconnected Information Set into StageDisplayWindow
	    SD.disconnected();
	    sdCreated = true;
	}
	
	
	public void connect(String host) {
		this.host = host;
		
		
		if (!hasWindow()) {
			// Create StageDisplay Window
			System.out.println("Creating PP Window...");
			createSDWindow();
		}
	    
		socketActive = false;
		attemptConnection = true;
		
		while (attemptConnection) {
			
			// Try connecting to ProPresenter on host and port
            try {
            	socket = new Socket(host, port);
            	out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                
                // Set socket tracker variable as active
                socketActive = true;
                
                // If Socket is created, and In and Out streams have been initialized, mark connection as active
                activeConnection = true;
                
                // Send Login String to ProPresenter
                out.println(getLoginString());

                // Read in Login Response
                String loginResponse = in.readLine();
                
                // If Password was correct, mark login as succeeded
                if (SUCCESSFUL_LOGIN.equals(loginResponse) || SUCCESSFUL_LOGIN_WINDOWS.equals(loginResponse)) {
                    loginFailed = false;
                } else {
                    loginFailed = true;
                }
                
                // If the connection is active, login has succeeded, and the socket is connected, update the Stage Display data
                while (attemptConnection && activeConnection && !loginFailed && socket.isConnected()) {
                    activeConnection = update(in, out);
                    sleep(Integer.parseInt(responseTime));
                }
                
                // Disconnect if conditions are not met
                disconnect();
            } catch (IOException e) {
            	// Disconnect if the Socket has disconnected
            	disconnect();
            }
            
            // Wait half a second and try connecting again
            sleep(500);
        }
	}
	
	
	public void disconnect() {
		
		// Display disconnected data on the Stage Display screen
    	SD.disconnected();
    	
    	// Close all elements
    	if (socketActive) {
    		try {
    			socket.close();
    			in.close();
    			out.close();
    		} catch (IOException e1) {
    			System.out.println("Unable to close element... "+e1);
    		}
    	}
    	
    	// Mark the connection as false
    	activeConnection = false;
    	// Mark the login as failed
    	loginFailed = false;
    	
    	System.out.println("Lost Active Connection...");
	}
	
	
	private boolean update(BufferedReader in, PrintWriter out) {
		// Reads data into XML format
        boolean readStatus = false;
		readStatus = xmlDataReader.readXmlData(in);
		
        if (!readStatus) {
            return false;
        }
        
        layoutReader.convertToObjects(xmlDataReader.getLayoutXmlData());
        
        // Creates a stage display object to pull data fields from
        StageDisplay stageDisplay = xmlParser.parse(xmlDataReader.getUpdateXmlData());
        
        // Extracts slide objects from stage display object
        String currentSlide = stageDisplay.getData("CurrentSlide");
        String nextSlide = stageDisplay.getData("NextSlide");
        String time = stageDisplay.getData("Clock");
        String videoCountdown = stageDisplay.getData("VideoCounter");
        String message = stageDisplay.getData("Message");
        
        // --- Mac Only Fixes: ---
        // Message is empty
        if (message == "") {
        	message = " ";
        }
        // Video Counter is idle (Spacing Issue)
        if (videoCountdown.isEmpty()) {
        	videoCountdown = "- - : - - : - -";
        } else if (videoCountdown.charAt(1) == '-') {
        	videoCountdown = "- - : - - : - -";
        }
        
        // Date format is different
        if (time.charAt(4) == '/') {
        	
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
				Date date;
				date = sdf.parse(time);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				String hour = Integer.toString(cal.get(Calendar.HOUR));
				String minute = Integer.toString(cal.get(Calendar.MINUTE));
				
				// Add extra styling zeroes to Hour
				if (hour.length() < 2) {
					
					// If hour is 12AM/12PM, convert to 12 from 0
					if (Integer.parseInt(hour) == 0) {
						hour = Integer.toString(12);
					}
					
					// If hour is another hour, add a zero to the front
					else {
						hour = "0"+hour;
					}
				}
				
				// Add extra styling zeroes to Minute
				if (minute.length() < 2) {
					minute = "0"+minute;
				}
				
				// Add Hour and Minute together with colon
				time = hour+":"+minute;
				
				// Add AM/PM Indicator
				if (cal.get(Calendar.AM_PM) == 1) {
					time = time+" PM";
				} else {
					time = time+" AM";
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        
        SD.setCurrentSlide(currentSlide);
        SD.setNextSlide(nextSlide);
        SD.setClock(time);
        SD.setVideoCountdown(videoCountdown);
        SD.setMessage(message);
        SD.revalidateWindow();
        return true;
    }
	
	
	public String connectionStatus() {
		String status = null; 
		if (activeConnection) {
			status = 	"Successfully connected to ProPresenter."+"\n"+
						"IP Address: "+host+":"+port+"\n"+
						"Password: "+password;
		} else if (loginFailed) {
			status = 	"Failed to authenticate with ProPresenter."+"\n"+
						"IP Address: "+host+":"+port+"\n"+
						"Password: "+password;
		} else {
			status = 	"Failed to connect to ProPresenter."+"\n"+
						"IP Address: "+host+":"+port+"\n"+
						"Password: "+password;
		}
		return status;
	}
	
	
    private String getLoginString() {
        return "<StageDisplayLogin>" + password + "</StageDisplayLogin>\n\r";
    }
    
    
    public String getCurrentSlide() {
		return this.slide;
    }
    
    
    public int getPort() {
		return this.port;
    }
    
    
    public String getHost() {
		return this.host;
    }
    
    public boolean hasWindow() {
    	return this.sdCreated;
    }
}
