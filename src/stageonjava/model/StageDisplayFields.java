package stageonjava.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */

@XmlRootElement(name = "Fields")
public class StageDisplayFields {
	
	@XmlElement(name = "Field")
	private List<StageDisplayField> field = new ArrayList<StageDisplayField>();
	
	
	public StageDisplayFields() {
		
	}
	
	public StageDisplayFields(List<StageDisplayField> field) {
		this.field = field;
	}
	
	public List<StageDisplayField> getFields() {
		return field;
	}
	
	@Override
	public String toString() {
		return "StageDisplayFields [Fields:"+field+
				"]";
	}
}
