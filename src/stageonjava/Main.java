package stageonjava;

import java.util.List;
import stageonjava.tray.TrayItem;
import stageonjava.util.ProPresenterConnector;
import stageonjava.util.ScanPorts;
import stageonjava.util.PropertiesReader;

/*
 * Author: Luke Bradtke
 * Since: 1.0
 * Version: 1.6
 */


public class Main {
	
	public static ProPresenterConnector PP = null;
	public static PropertiesReader props;
	public static List<String> Hosts;
	
	public static void main(String[] args) {
		
		// Create a new PropertyReader for storing and retrieving settings
		props = new PropertiesReader();
		
		// Create new ProPresenter Connector object
		PP = new ProPresenterConnector(Integer.parseInt(props.getProperty("stage-port")), props.getProperty("stage-password"));
		
		// Create a system tray icon
		new TrayItem();
		
		// Determine StageDisplay Source
		if (props.getProperty("auto-scan").toLowerCase().contains("yes")) {
			// Scan network for ProPresenter Hosts and connect to first one
			ScanNetwork();
			AutoConnect();
		} else {
			// Initiate a connection to ProPresenter
			ConnectPP(props.getProperty("host"));
		}
	}
	
	
	public static void ScanNetwork() {
		// Create a new ScanPorts for auto-discovery of ProPresenter
		ScanPorts sp = new ScanPorts(Integer.parseInt(props.getProperty("stage-port")));
		Hosts = sp.Scan();
	}
	
	
	public static void AutoConnect() {
		try {
			// Set first Host found to current Host
			setHost(Hosts.get(0));
			System.out.println("Found host(s) on network... Using first one found.");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No hosts found on network...  Resorting to specified host address.");
		}

		// Initiate a connection to ProPresenter
		ConnectPP(props.getProperty("host"));
	}
	
	
	public static void ConnectPP(String host) {
		// Connect to ProPresenter
		System.out.println("Creating PP Connection...");
		PP.connect(host);
	}
	
	public static int getCurrentSlideFontSize() {
		return Integer.parseInt(props.getProperty("current-font-size"));
	}
	
	public static int getNextSlideFontSize() {
		return Integer.parseInt(props.getProperty("next-font-size"));
	}
	
	public static int getSmallFontSize() {
		return Integer.parseInt(props.getProperty("clock-message-size"));
	}
	
	public static int getTitleFontSize() {
		return Integer.parseInt(props.getProperty("title-size"));
	}
	
	public static void setHost(String host) {
		props.setProperty("host", host);
	}
}
