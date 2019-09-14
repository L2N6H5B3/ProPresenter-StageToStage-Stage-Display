package stageonjava.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */

@XmlRootElement(name = "DisplayLayout")
public class DisplayLayout {
	
	@XmlAttribute(name = "identifier")
	private String identifier;
	@XmlAttribute(name = "showBorder")
	private String showBorder;
	@XmlAttribute(name = "height")
	private String height;
	@XmlAttribute(name = "width")
	private String width;
	
	@XmlElement(name = "Frame")
	private List<DisplayLayoutFrame> frame = new ArrayList<DisplayLayoutFrame>();
	
	
	public DisplayLayout() {
		
	}
  
	public DisplayLayout(String identifier, String showBorder, String height, String width, List<DisplayLayoutFrame> frame) {
		this.identifier = identifier;
		this.showBorder = showBorder;
		this.height = height;
		this.width = width;
		this.frame = frame;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public String getShowBorder() {
		return this.showBorder;
	}
	
	public int getHeight() {
		return Integer.parseInt(this.height);
	}
	
	public int getWidth() {
		return Integer.parseInt(this.width);
	}
	
	public List<DisplayLayoutFrame> getFrames() {
		return this.frame;
	}
	
	@Override
	public String toString() {
		return "DisplayLayout [Identifier:"+identifier+
				", ShowBorder:"+showBorder+
				", Height:"+height+
				", Width:"+width+
				", Frames:"+frame+
				"]";
  }
}