package model;

import java.io.Serializable;

public class MissileInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int posX;
	private int posY;
	private int angle;
	private int range;
	
	public MissileInfo(int parPosX, int parPosY, int  parAngle,int parRange){
		posX = parPosX;
		posY = parPosY;
		angle = parAngle;
		range = parRange;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getAngle() {
		return angle;
	}

	public int getRange() {
		return range;
	}


}
