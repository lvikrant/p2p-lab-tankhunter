package model;

import java.awt.Point;
import java.io.Serializable;

public class PowerUp implements Serializable{
     
	private static final long serialVersionUID = 1L;

	private final String POWERUP;
	
	private final Point POSITION;
	
	public PowerUp(Point pos){
		POSITION = pos;
		
		switch ((int)(Math.random()*5)){
		case 0 :  POWERUP = "SHIELD"; break;
		case 1 :  POWERUP = "SPEED";  break;
		case 2 :  POWERUP = "RANGE";  break;
		case 3 :  POWERUP = "RATE";   break;
		case 4 :  POWERUP = "SLOW";   break;
		default : POWERUP = "ERROR";  break;
		}
	}

	public String getPowerUp(){
		return POWERUP;
	}
	
	public Point getPos(){
		return POSITION;
	}
	


}


