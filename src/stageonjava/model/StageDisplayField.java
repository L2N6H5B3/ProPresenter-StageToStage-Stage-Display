package stageonjava.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */

@XmlRootElement(name = "Field")
public class StageDisplayField {
	
	@XmlAttribute(name = "identifier")
	private String identifier;
	@XmlAttribute(name = "label")
	private String label;
	@XmlAttribute(name = "type")
	private String type;
	@XmlAttribute(name = "alpha")
	private String alpha;
	@XmlAttribute(name = "red")
	private String red;
	@XmlAttribute(name = "green")
	private String green;
	@XmlAttribute(name = "blue")
	private String blue;
	@XmlValue
	private String value;
	
	
	public StageDisplayField() {
		
	}
	
	public StageDisplayField(String identifier, String label, String type, String alpha, String red, String green, String blue, String value) {
		this.identifier = identifier;
		this.label = label;
		this.type = type;
		this.alpha = alpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.value = value;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getType() {
		return type;
	}
	
	public String getAlpha() {
		return alpha;
	}
	
	public int getRed() {
		return convertColour(red);
	}
	
	public int getGreen() {
		return convertColour(green);
	}
	
	public int getBlue() {
		return convertColour(blue);
	}
	
	public String getValue() {
		
		switch (this.getIdentifier()) {
		case "Clock":
			value = editTime(value);
			break;
		case "CurrentSlide":
			value = editSlide(value);
			break;
		case "NextSlide":
			value = editSlide(value);
			break;
		case "VideoCounter":
			value = editVideoCounter(value);
			break;
		case "Message":
			value = editMessage(value);
			break;
		}
	
		return value;
	}
	
	private int convertColour(String colour) {
		System.out.println(colour);
		Double value = Double.parseDouble(colour);
		System.out.println(value);
		int value1 = (int) (255 * value);
		System.out.println(value1);
		return value1;
	}
	
	private String editTime(String time) {
		// Date format is different
        if (time.charAt(4) == '/') {
        	
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
				Date date;
				date = sdf.parse(time);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				String hour = Integer.toString(cal.get(Calendar.HOUR));
				String minute = Integer.toString(cal.get(Calendar.MINUTE));
				
				// Add extra styling zeroes to Hour
				if (hour.length() < 2) {
					
					// If hour is 12AM/12PM, convert to 12 from 0
					if (Integer.parseInt(hour) == 0) {
						hour = Integer.toString(12);
					}
					
					// If hour is another hour, add a zero to the front
//					else {
//						hour = "0"+hour;
//					}
				}
				
				// Add extra styling zeroes to Minute
				if (minute.length() < 2) {
					minute = "0"+minute;
				}
				
				// Add Hour and Minute together with colon
				time = hour+":"+minute;
				
				// Add AM/PM Indicator
				if (cal.get(Calendar.AM_PM) == 1) {
					time = time+" PM";
				} else {
					time = time+" AM";
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        
        return time;
	}
	
	private String editSlide(String slide) {
		slide = "<html>" + slide.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>";
		return slide;
	}
	
	private String editVideoCounter(String videoCounter) {
		// Video Counter is idle (Spacing Issue)
		if (videoCounter.isEmpty()) {
			videoCounter = "- - : - - : - -";
		} else if (videoCounter.charAt(1) == '-') {
			videoCounter = "- - : - - : - -";
		}
		return videoCounter;
	}
	
	private String editMessage(String message) {
		// Message is empty
		if (message.equals("")) {
			message = " ";
		}
		
		return message;
	}
	
	@Override
	public String toString() {
		return "StageDisplayField [Identifier:"+identifier+
				", Label:"+label+
				", Type:"+type+
				", Alpha:"+alpha+
				", Red:"+red+
				", Green:"+green+
				", Blue:"+blue+
				", Value:"+value+
				"]";
	}
}