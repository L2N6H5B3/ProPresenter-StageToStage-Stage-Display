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

@XmlRootElement(name = "DisplayLayouts")
public class DisplayLayouts {

	@XmlAttribute(name = "selected")
	private String selected;
	
	@XmlElement(name = "DisplayLayout")
	private List<DisplayLayout> layout = new ArrayList<DisplayLayout>();

	
	public DisplayLayouts() {
		
	}
	
	public DisplayLayouts(String selected, List<DisplayLayout> layout) {
		this.selected = selected;
		this.layout = layout;
	}
	
	public String getSelected() {
		return this.selected;
	}
	
	public List<DisplayLayout> getLayouts() {
		return this.layout;
	}
	
	@Override
	public String toString() {
		return "DisplayLayouts [Selected:"+selected+
				", Layouts:"+layout+
				"]";
  }
	
}