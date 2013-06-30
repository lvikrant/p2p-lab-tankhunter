package utils;

import java.io.Serializable;

import model.NetworkTarget;

public class Move implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private NetworkTarget nt;
	private int angle;
	
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

}
