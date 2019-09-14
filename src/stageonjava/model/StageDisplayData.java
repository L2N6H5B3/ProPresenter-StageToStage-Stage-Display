package stageonjava.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */

@XmlRootElement(name = "StageDisplayData")
public class StageDisplayData {
	
	@XmlElement(name = "Fields")
	private StageDisplayFields fields;
	
	public StageDisplayData() {
		
	}
	
	public StageDisplayData(StageDisplayFields fields) {
		this.fields = fields;
	}
	
	public StageDisplayFields getStageDisplayDataFields() {
		return fields;
	}
	
	@Override
	public String toString() {
		return "StageDisplayData [Fields:"+fields+
				"]";
	}
}
