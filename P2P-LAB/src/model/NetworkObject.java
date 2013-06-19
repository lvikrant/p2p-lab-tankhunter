package model;

import java.io.Serializable;
import java.net.InetSocketAddress;



public class NetworkObject implements Serializable {
	
	public enum dataType {
	    Ping, Pong, ConenctTo, Init, Move, Shoot, Data;
	}
	
	public dataType type = dataType.Data;
	
	/**
	 * filled my Handler
	 */
	public String from;
	/**
	 * filled my Handler
	 */
	public int openPort;
	
	
	public String bla;
	public String target;
	public int targetPort;
        public Tank tank;
        public Missile missile;


	@Override
	public String toString() {
		return "NetworkObject [type=" + type + ", from=" + from + ", openPort="
				+ openPort + ", bla=" + bla + "]";
	}

}
