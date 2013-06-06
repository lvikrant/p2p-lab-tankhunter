package controller;

import java.awt.Point;

import model.MapElements;
import model.NetworkTarget;
import model.Tank;
import interfaces.IObjectController;

public class ObjectController implements IObjectController{

	
    private final PowerUpController POWER_UP_CONTROLLER;
    private final TankController TANK_CONTROLLER;
    private final MissileController MISSILE_CONTROLLER;
    
    private final MapElements MAP_ELEMENTS;
    
	public ObjectController(GameController gameC, int powerUpLimit,int tankLimit, int missileLimit, int mapID) {
		
		POWER_UP_CONTROLLER = new PowerUpController(gameC,powerUpLimit);
		TANK_CONTROLLER = new TankController(gameC,tankLimit);
		MISSILE_CONTROLLER = new MissileController(gameC,missileLimit);
		MAP_ELEMENTS = new MapElements(gameC,mapID);
		
	}

	
	
	void addTank(NetworkTarget nt, Point point, int angle){
		TANK_CONTROLLER.add(nt, point, angle);
	}
	
	void addTankRandom(NetworkTarget nt){
		TANK_CONTROLLER.addRandom(nt);
	}
	
	void addPowerUpRandom(){
		POWER_UP_CONTROLLER.addRandom();
	}
	
	void destroy(NetworkTarget nt){
		TANK_CONTROLLER.destroy(nt);
	}
	
	int getPowerUpMapSize(){
		return POWER_UP_CONTROLLER.getSize();
	}



	public Tank getTank(NetworkTarget nt) {
		return TANK_CONTROLLER.get(nt);
	}



	public void removeAllPowerUp() {
		POWER_UP_CONTROLLER.removeAll();
		
	}


	public void removeTank(NetworkTarget nt) {
		TANK_CONTROLLER.remove(nt);
		
	}


	public String getFieldInfo(Point point) {
		if(POWER_UP_CONTROLLER.contains(point)){
			return "POWERUP";
		} else if (TANK_CONTROLLER.contains(point)){
			return "TANK";
		} else if (MISSILE_CONTROLLER.contains(point)){
			return "MISSILE";
		} else {
			return MAP_ELEMENTS.getFieldInfo((int)point.getX(),(int)point.getY());
		}
		
	}

	public void removePowerUp(Point point) {
		POWER_UP_CONTROLLER.remove(point);
	}



	public void setElements(int mapID) {
		MAP_ELEMENTS.setElements(mapID);
	}
	
}
