package stageonjava.util.deprecated;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import stageonjava.Main;


/*
 * Author: Luke Bradtke
 * Since: 1.0
 * Version: 1.5
 */


public class PickerWindow {

	// Initializes Picker window
	static JFrame pickerWindow;
	
	// Initializes Picker panel
	static JPanel pickerPanel;
	
	// Initializes text labels
	static JLabel title;
	
	// Initializes buttons
	static JButton exitButton;
	
	
	public PickerWindow() {
	
		// Creates a new JFrame for holding standard JPanel component
		pickerWindow = new JFrame("Pick Host");
		
		// Creates a new JPanel for holding standard Label and Field components
		pickerPanel = new JPanel();

		// Creates a new GridLayout with 2 Columns
		pickerPanel.setLayout(new GridLayout(0, 1));
				
		// Sets border
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		pickerPanel.setBorder(padding);

	    // Creates new text labels for each setting
	    title = new JLabel("Pick ProPresenter Host:");
		
	    // Adds Labels and Fields to the Picker Window Panel
	    pickerPanel.add(title);
	    
	    for (String host : Main.Hosts) { 
	    	JButton button = new JButton(GetIPHostname.get(host));
	    	pickerPanel.add(button);
	    	button.addActionListener(
	    		new ActionListener() {
    				public void actionPerformed (ActionEvent event) {
    					Main.ConnectPP(host);
    					// Removes Picker Window
    					pickerWindow.dispose();
    				}
    			}
	    	);
	    }

	    // Adds Exit button
	    exitButton = new JButton("Exit");
	    exitButton.addActionListener((ActionEvent event) -> {
	    	// Removes Picker Window
	    	pickerWindow.dispose();
	    	System.exit(0);
        });
	    
	    // Adds buttons to Settings Window
	    pickerPanel.add(exitButton);
	    
	    // Assigns Panels to the Window Frames
	    pickerWindow.setContentPane(pickerPanel);
	    
	    // Finalizes Window Frame items
	    pickerWindow.pack();
	    
	    // Shows Picker Window
	    pickerWindow.setVisible(true);
	}
}
