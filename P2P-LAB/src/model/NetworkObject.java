package model;

import java.awt.Point;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Map;

import utils.Move;



public class NetworkObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum dataType {
	    Ping, Pong, ConenctTo, Init, AddTank, MoveTank, 
	    MoveRequest, Shoot, Data, Tank, AddPowerUp, 
	    RemovePowerUp, AddMissile, AddMissileRequest,
	    Exit,RotateTank;
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
	public int angle;
    public Tank tank;
    public Missile missile;
    public int range;
    
    public Move move;
    
    public PowerUp powerUp;
    
    public Map<NetworkTarget, TankInfo>  tankData;
    public Map<Point, PowerUp>  powerUpData;
    public Map<NetworkTarget, MissileInfo>  missileData;
    public int region;
	public Point point;

}
