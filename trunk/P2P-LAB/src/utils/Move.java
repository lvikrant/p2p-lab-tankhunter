package utils;

import java.awt.Point;
import java.io.Serializable;

import model.NetworkTarget;

public class Move implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private NetworkTarget nt;
	private int angle;
	private Point pos;
	
	public Move(NetworkTarget nt, int angle,Point pos){
		this.nt = nt;
		this.angle = angle;
		this.pos = pos;
	}
	
	public Move(NetworkTarget nt, int angle){
		this.nt = nt;
		this.angle = angle;
	}

	public NetworkTarget getNetworkTarget() {
		return nt;
	}

	public int getAngle() {
		return angle;
	}
	
	public Point getLocation() {
		return pos;
	}

}
