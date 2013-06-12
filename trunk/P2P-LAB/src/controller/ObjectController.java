package controller;

import java.awt.Point;
import java.util.Map;

import model.MapElements;
import model.NetworkTarget;
import model.PowerUp;
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


	/************ POWER_UP_CONTROLLER *******************************************************************************/
	
	public void addPowerUpRandom(){
		POWER_UP_CONTROLLER.addRandom();
	}
	
	public void addPowerUp(Point point) {
		POWER_UP_CONTROLLER.add(point);
	}
	
	public int getPowerUpMapSize(){
		return POWER_UP_CONTROLLER.getSize();
	}
	
	public int getPowerUpMapMaxSize() {
		return POWER_UP_CONTROLLER.getMaxSize();
	}
	
	public void removePowerUp(Point point) {
		POWER_UP_CONTROLLER.remove(point);
	}
	
	public void removeAllPowerUps() {
		POWER_UP_CONTROLLER.removeAll();		
	}
	
	public PowerUp getPowerUp(Point point) {
		return POWER_UP_CONTROLLER.get(point);
	}
	
	public boolean containsPowerUp(Point point) {
		return POWER_UP_CONTROLLER.contains(point);
	}
	
	public void importPowerUpMap(Map<Point, PowerUp> map) {
		POWER_UP_CONTROLLER.importMap(map);
	}

	public Map<Point, PowerUp> exportPowerUpMap() {
		return POWER_UP_CONTROLLER.exportMap();
	}
	
	/************* TANK_CONTROLLER ***********************************************************************************/
	
	public void addTank(NetworkTarget nt, Point point, int angle){
		TANK_CONTROLLER.add(nt, point, angle);
	}
	
	public void addTankRandom(NetworkTarget nt){
		TANK_CONTROLLER.addRandom(nt);
	}
	
	public void destroy(NetworkTarget nt){
		TANK_CONTROLLER.destroy(nt);
	}
	
	public void removeTank(NetworkTarget nt) {
		TANK_CONTROLLER.remove(nt);	
	}
	
	public Tank getTank(NetworkTarget nt) {
		return TANK_CONTROLLER.get(nt);
	}
	
	public void importTankMap(Map<NetworkTarget, Tank> map) {
		TANK_CONTROLLER.importMap(map);
	}

	public Map<NetworkTarget, Tank> exportTankMap() {
		return TANK_CONTROLLER.exportMap();
	}
	
	public boolean contains(NetworkTarget nt) {
		return TANK_CONTROLLER.contains(nt);
	}

	public boolean contains(Point point) {
		return TANK_CONTROLLER.contains(point);
	}
	
	/** MISSILE_CONTROLLER ********************/
	







	/******************* MAP_ELEMENTS *********************************************************************************************/
	
	public void setElements(int mapID) {
		MAP_ELEMENTS.setElements(mapID);
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
}