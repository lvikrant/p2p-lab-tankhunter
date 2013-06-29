package model;

import java.io.Serializable;

public class TankInfo implements Serializable{

	private int posX;
	private int posY;
	private int angle;
	private String status;
	private int timeLeft;
	
	public TankInfo(int parPosX, int parPosY, int parAngle, String parStatus, int parTimeLeft){
		posX = parPosX;
		posY = parPosY;
		angle = parAngle;
		status = parStatus;
		timeLeft = parTimeLeft;
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

	public String getStatus() {
		return status;
	}

	public int getTimeLeft() {
		return timeLeft;
	}
}
