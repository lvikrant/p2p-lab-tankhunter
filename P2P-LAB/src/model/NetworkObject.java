package model;

import java.awt.Point;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Map;



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
	
	/**
	 * sender of the message
	 */
	public NetworkTarget target;
	
	
	/**
	 * target of the data (tank or missile)
	 */
	public NetworkTarget dataTarget;
    public Tank tank;
    public Missile missile;
    
    public Map<NetworkTarget, Tank>  tankData;
    public Map<Point, PowerUp>  powerUpData;


}
