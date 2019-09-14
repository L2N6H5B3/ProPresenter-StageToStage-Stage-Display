package stageonjava.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Luke Bradtke
 * @version 1.0
 * @since 1.0
 */

public class ScanPorts {

	private static int port;
	private static int timeout;
	private static String ip;
	private static String[] ipSplit;
	private static String ipBase;
	private static boolean gotIP;
	private ExecutorService es;
	private final int THREADS = 25;
	private List<Future<String>> futures;
	private List<String> hosts;
    
	
	// Constructor to initialise port and timeout
	public ScanPorts(int port) {
		ScanPorts.port = port;
		ScanPorts.timeout = 200;
		
		// Get the system IP address
		GetIPAddress();
	}
	
	// Get current system IP Address
	public void GetIPAddress() {
		// Get the system's IP address
		try {
			ScanPorts.ip = InetAddress.getLocalHost().getHostAddress();
			ipSplit = ip.split("\\.");
			ipBase = ipSplit[0]+"."+ipSplit[1]+"."+ipSplit[2]+".";
			gotIP = true;
		} catch (UnknownHostException e) {
			System.out.println("Unable to get IP Address...");
			gotIP = false;
		}
	}
	
	// Check if ScanPorts has an IP Address
	public boolean HasIPAddress() {
		return gotIP;
	}
	
	// Scan the local network for Hosts with open port
	public List<String> Scan() {
		try {
			
			// Create an Executor service for async processing of IPs
			es = Executors.newFixedThreadPool(THREADS);
			
			// Create a new list to hold hosts while processing
			futures = new ArrayList<>();
			// For each IP address to scan, add the host to the processing list
			for (int ip=1;ip<254;ip++) {
				futures.add(portIsOpen(es, ipBase+ip));
			}
			// Shutdown the Executor Service 
			es.shutdown();
			
			// Create a new list to hold hosts with open port
			hosts = new ArrayList<>();
			// Add each host found to list
			for (final Future<String> f : futures) {
				if (f.get() != null) {
					hosts.add(f.get());
			    }
			}
			
		} catch (InterruptedException e) {
			System.out.println("Connection Interrupted...");
		} catch (ExecutionException e) {
			System.out.println("Execution Error...");
		}
		return hosts;
	}
	
	// Scan IP for open port
	public static Future<String> portIsOpen(final ExecutorService es, final String ip) {
		return es.submit(
			new Callable<String>() {
				@Override public String call() {
			    try {
			    	Socket socket = new Socket();
		    		socket.connect(new InetSocketAddress(ip, port), timeout);
			    	socket.close();
			    	return ip;
			        } catch (Exception ex) {
			        	return null;
			        }
				}
			}
		);
	}
}