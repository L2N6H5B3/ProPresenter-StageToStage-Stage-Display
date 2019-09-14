package stageonjava.model;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import stageonjava.Main;

/**
 * @author Luke Bradtke
 * @version 1.4
 * @since 1.0
 */

public class StageDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Color flashColour;
	private static Border newBorder;
	private static MatteBorder mainGrayBorder;
	
	// Hold currently set Layout name
	private String currentLayoutName = "empty";
	
	// Hashmap to hold the Frames in the Layout
	private HashMap<String, JLabel> addedFrames;
	
	
	public StageDisplayPanel() {
		this.setLayout(null);
	    setBackground(Color.BLACK);
	    
	    // Creates Border Template for Stage Display Elements
	    newBorder = BorderFactory.createEmptyBorder();
	    mainGrayBorder = BorderFactory.createMatteBorder(20, 2, 2, 2, Color.GRAY);
	}
	
	public void setLayout(DisplayLayout layout, int expectedWidth, int expectedHeight) {
		removeAll();
		
		// Change the current layout name
		currentLayoutName = layout.getIdentifier();
		
		addedFrames = new HashMap<String, JLabel>();
		
		// For each Frame in the Layout, add the fields to the StageDisplayPanel
	    for (DisplayLayoutFrame frame : layout.getFrames()) {
	    	
	    	if (frame.hasFlashColour()) {
	    		flashColour = frame.getFlashColour();
	    	}
	    	
	    	JLabel textFrame = new JLabel();
	    	
	    	// Set Fonts for elements
	    	Font font = new Font("Arial", Font.PLAIN, frame.getFontSize());
		    Font titleFont = new Font("Arial", Font.PLAIN, Integer.parseInt(Main.props.getProperty("title-size")));
		    // Creates a Border for each Element
		    TitledBorder textBorder = setBorder(frame.getName(), newBorder, titleFont, mainGrayBorder);
		    
		    // Set Frame attributes
		    textFrame.setFont(font);
	    	textFrame.setBorder(textBorder);
		    textFrame.setForeground(Color.WHITE);
		    textFrame.setVerticalAlignment(SwingConstants.TOP);
		    textFrame.setSize(frame.getWidth(expectedWidth), frame.getHeight(expectedHeight));
			textFrame.setLocation(frame.getXAxis(expectedWidth), frame.getYAxis(expectedHeight));
		    
			addedFrames.put(frame.getIdentifier(), textFrame);
			add(textFrame);
			repaint();
			revalidate();
	    }
	}
	
	public String getCurrentLayout() {
		return currentLayoutName;
	}
	
	public HashMap<String, JLabel> getAddedFrames() {
		return addedFrames;
	}
	
	public Color getMessageFlashColour() {
		return flashColour;
	}
	
	private TitledBorder setBorder(String text, Border newBorder, Font titleFont, MatteBorder mainGrayBorder) {
		TitledBorder border = BorderFactory.createTitledBorder(newBorder, text);
	    border.setTitleColor(Color.GRAY);
	    border.setTitleFont(titleFont);
	    border.setBorder(mainGrayBorder);
	    border.setTitlePosition(TitledBorder.TOP);
	    return border;
	}
}