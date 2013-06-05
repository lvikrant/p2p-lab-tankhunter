package p2p.sockets;

import java.io.Serializable;
import java.net.InetSocketAddress;



public class NetworkObject implements Serializable {
	
	public enum dataType {
	    Ping, Pong, ConenctTo, Data;
	}
	
	dataType type = dataType.Data;
	
	/**
	 * filled my Handler
	 */
	String from;
	/**
	 * filled my Handler
	 */
	int openPort;
	
	
	String bla;
	String target;
	int targetPort;


	@Override
	public String toString() {
		return "NetworkObject [type=" + type + ", from=" + from + ", openPort="
				+ openPort + ", bla=" + bla + "]";
	}

}
