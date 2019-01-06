package stageonjava.util;


import static stageonjava.util.ThreadUtil.sleep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
 * @version 1.5
 * @since 1.0
 */


public class ProPresenterConnector {

	private String HOST = "localhost";
	private String PORT = "53333";
	private String PASSWORD = "stage";
	private String RESPONSE_TIME_MILLIS = "10";
		
	
    private static final String SUCCESSFUL_LOGIN = "<StageDisplayLoginSuccess />";
    private static final String SUCCESSFUL_LOGIN_WINDOWS = "<StageDisplayLoginSuccess>";
    private static final XmlDataReader xmlDataReader = new XmlDataReader();
    private static final XmlParser xmlParser = new XmlParser();
	
    private volatile boolean running = true;
    private volatile boolean activeConnection = false;
    private volatile boolean loginFailed = false;
    
    private String slide;
    private StageDisplayWindow SD;
    
	public ProPresenterConnector(String[] configArray) {
		this.HOST = configArray[0];
		this.PORT = configArray[1];
		this.PASSWORD = configArray[2];
	}

	public void connect() throws FileNotFoundException {
		SD = new StageDisplayWindow();
			while (running) {
	            try (Socket socket = new Socket(HOST.toString(), Integer.parseInt(PORT))) {
	                try (
	                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))
	                ) {
	                    activeConnection = true;
	                    out.println(getLoginString());

	                    String loginResponse = in.readLine();
	                    if (SUCCESSFUL_LOGIN.equals(loginResponse) || SUCCESSFUL_LOGIN_WINDOWS.equals(loginResponse)) {
	                        loginFailed = false;
	                    } else {
	                        loginFailed = true;
	                    }
	                    while (running && activeConnection && socket.isConnected()) {
	                        activeConnection = update(in, out);
	                        sleep(Integer.parseInt(RESPONSE_TIME_MILLIS));
	                    }
	                }
	            } catch (IOException e) {
	            	activeConnection = false;
	                SD.disconnected();
	            }
	            sleep(500);
	        }
	}
	
	private boolean update(BufferedReader in, PrintWriter out) throws IOException {

		// Reads data into XML format
        String xmlRawData = xmlDataReader.readXmlData(in);
        if (xmlRawData == null) {
            return false;
        }
        
        // Creates a stage display object to pull data fields from
        StageDisplay stageDisplay = xmlParser.parse(xmlRawData);
        
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
						"IP Address: "+HOST+":"+PORT+"\n"+
						"Password: "+PASSWORD;
		} else if (loginFailed) {
			status = 	"Failed to authenticate with ProPresenter."+"\n"+
						"IP Address: "+HOST+":"+PORT+"\n"+
						"Password: "+PASSWORD;
		} else {
			status = 	"Failed to connect to ProPresenter."+"\n"+
						"IP Address: "+HOST+":"+PORT+"\n"+
						"Password: "+PASSWORD;
		}
		return status;
	}
	
    private String getLoginString() {
        return "<StageDisplayLogin>" + PASSWORD + "</StageDisplayLogin>\n\r";
    }
    
    public String getCurrentSlide() {
		return this.slide;
    }
    
    public String getHost() {
		return this.HOST;
    }
    
    public String getPort() {
		return this.PORT;
    }
}
