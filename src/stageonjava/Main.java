package stageonjava;

import java.io.FileNotFoundException;

import stageonjava.tray.TrayItem;
import stageonjava.util.ConfigReader;
import stageonjava.util.ProPresenterConnector;
import stageonjava.util.TextOutput;

public class Main {
	
	public static ProPresenterConnector PP;
	public static TextOutput PPText;
	public static TextOutput PPRaw;
	public static String[] configArray = new String[7];
	
	public static void main(String[] args) throws FileNotFoundException {
		
		// Read from configuration File
		ConfigReader config = new ConfigReader("config.txt");
		configArray = config.ReadFile();

		// Create a new connection file for connection to ProPresenter
		PP = new ProPresenterConnector(configArray);
		
		// Create a system tray icon
		@SuppressWarnings("unused")
		TrayItem ti = new TrayItem();
		
		// Initiate a connection to ProPresenter
		PP.connect();
		
	}
	
	public static int getCurrentSlideFontSize() {
		return Integer.parseInt(configArray[3]);
	}
	
	public static int getNextSlideFontSize() {
		return Integer.parseInt(configArray[4]);
	}
	
	public static int getSmallFontSize() {
		return Integer.parseInt(configArray[5]);
	}
	
	public static int getTitleFontSize() {
		return Integer.parseInt(configArray[6]);
	}

}
