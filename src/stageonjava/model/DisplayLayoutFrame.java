package stageonjava.model;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */

@XmlRootElement(name = "Frame")
public class DisplayLayoutFrame {
	
	@XmlAttribute(name = "identifier")
	private String identifier;
	@XmlAttribute(name = "isVisible")
	private String isVisible;
	@XmlAttribute(name = "fontSize")
	private String fontSize;
	@XmlAttribute(name = "height")
	private String height;
	@XmlAttribute(name = "width")
	private String width;
	@XmlAttribute(name = "xAxis")
	private String xAxis;
	@XmlAttribute(name = "yAxis")
	private String yAxis;
	
	private Dimension screenSize;

	
	public DisplayLayoutFrame() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public DisplayLayoutFrame(String identifier, String isVisible, String fontSize, String height, String width, String xAxis, String yAxis) {
		this.identifier = identifier;
		this.isVisible = isVisible;
		this.fontSize = fontSize;
		this.height = height;
		this.width = width;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public String getName() {
		String name = this.identifier.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
		return name;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public String getIsVisible() {
		return this.isVisible;
	}
	
	public int getFontSize() {
		if (fontSize == null) {
			fontSize = "40";
		}
		
		return convertToInt(this.fontSize);
	}
	
	public int getWidth(int expectedWidth) {
		double frameWidth = convertToInt(this.width);
		double screenWidth = (int) screenSize.getWidth();
		double difference = expectedWidth - screenWidth;
		double percent = difference / expectedWidth;
		double returnedWidth = frameWidth * (1 - percent);
		
		return (int) returnedWidth;
	}
	
	public int getHeight(int expectedHeight) {
		double frameHeight = convertToInt(this.height);
		double screenHeight = (int) screenSize.getHeight();
		double difference = expectedHeight - screenHeight;
		double percent = difference / expectedHeight;
		double returnedHeight = frameHeight * (1 - percent);
		
		return (int) returnedHeight;
	}
	
	public int getXAxis(int expectedWidth) {
		
		double frameXAxis = convertToInt(this.xAxis);
		double screenWidth = (int) screenSize.getWidth();
		double difference = expectedWidth - screenWidth;
		double percent = difference / expectedWidth;
		double returnedXAxis = frameXAxis * (1 - percent);
		
		return (int) returnedXAxis;
	}
	
	public int getYAxis(int expectedHeight) {
		double frameYAxis = convertToInt(this.yAxis);
		double screenHeight = (int) screenSize.getHeight();
		double difference = expectedHeight - screenHeight;
		double percent = difference / expectedHeight;
		double returnedYAxis = frameYAxis * (1 - percent);
		
		return (int) returnedYAxis;
	}
	
	private int convertToInt(String string) {
		
		String newString = "";
		boolean foundAll = false;
		
		for (int i=0;i<string.length();i++) {
			if(!foundAll) {
				if (string.charAt(i) == '.') {
					foundAll = true;
				} else {
					newString += string.charAt(i);
				}

			}
		}
		
		return Integer.parseInt(newString);
	}
	
	@Override
	public String toString() {
		return "Frame [Identifier:"+identifier+
				", IsVisible:"+isVisible+
				", Height:"+height+
				", Width:"+width+
				", xAxis:"+xAxis+
				", yAxis:"+yAxis+
				"]";
  }
}
