package stageonjava.util.deprecated;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIPHostname {
	
	public static String get(String ip) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ip);
			return address.getCanonicalHostName();
		} catch (UnknownHostException e) {
			System.out.println("Unable to get Hostname of: "+ip);
			return ip;
		}
	}
}
