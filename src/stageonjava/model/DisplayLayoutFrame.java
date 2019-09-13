package stageonjava.model;

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
	@XmlAttribute(name = "height")
	private String height;
	@XmlAttribute(name = "width")
	private String width;
	@XmlAttribute(name = "xAxis")
	private String xAxis;
	@XmlAttribute(name = "yAxis")
	private String yAxis;

	
	public DisplayLayoutFrame() {
		
	}
	
	public DisplayLayoutFrame(String identifier, String isVisible, String height, String width, String xAxis, String yAxis) {
		this.identifier = identifier;
		this.isVisible = isVisible;
		this.height = height;
		this.width = width;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public String getIsVisible() {
		return this.isVisible;
	}
	
	public String getHeight() {
		return this.height;
	}
	
	public String getWidth() {
		return this.width;
	}
	
	public String getXAxis() {
		return this.xAxis;
	}
	
	public String getYAxis() {
		return this.yAxis;
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
