package controller;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import comparator.NTComparator;

import model.MapElements;
import model.Missile;
import model.MissileInfo;
import model.NetworkTarget;
import model.PowerUp;
import model.Tank;
import model.TankInfo;
import interfaces.IObjectController;

public class ObjectController implements IObjectController{

	
    private final PowerUpController POWER_UP_CONTROLLER;
    private final TankController TANK_CONTROLLER;
    private final MissileController MISSILE_CONTROLLER;
    
    private final MapElements MAP_ELEMENTS;
    private GameController gc;
    
	public ObjectController(GameController gameC, int powerUpLimit,int tankLimit, int missileLimit, int mapID) {
		gc = gameC;
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
	
	public NetworkTarget getTank(Point point) {
		return TANK_CONTROLLER.get(point);
	}	
	
	public void importTankMap(Map<NetworkTarget, Tank> map) {
		TANK_CONTROLLER.importMap(map);
	}

	public Map<NetworkTarget, Tank> exportTankMap() {
		return TANK_CONTROLLER.exportMap();
	}
	
	
	public Map<NetworkTarget, TankInfo> exportTankInfo() {
		Map<NetworkTarget, TankInfo> map = new TreeMap<NetworkTarget, TankInfo>(new NTComparator());
		
		for (Map.Entry<NetworkTarget, Tank> entry : TANK_CONTROLLER.exportMap().entrySet()) {
			int parPosX = entry.getValue().getPosX();
			int parPosY = entry.getValue().getPosY();
			int parAngle = entry.getValue().getAngle();
			String parStatus = entry.getValue().getStatus();
			int parTimeLeft = entry.getValue().getTimeLeft();
			map.put(entry.getKey(), new TankInfo(parPosX, parPosY, parAngle, parStatus, parTimeLeft));
		}
		
		return map;
	}
	
	
	public void importTankInfo(Map<NetworkTarget, TankInfo> parmap) {
		
		Map<NetworkTarget, Tank> map = new TreeMap<NetworkTarget, Tank>(new NTComparator());
		
		for (Map.Entry<NetworkTarget, TankInfo> entry : parmap.entrySet()) {
			int posX = entry.getValue().getPosX();
			int posY = entry.getValue().getPosY();
			Tank tempTank = new Tank(gc, entry.getKey(), new Point(posX, posY), entry.getValue().getAngle());
			tempTank.setStatus(entry.getValue().getStatus());
			tempTank.setTimeLeft(entry.getValue().getTimeLeft());
			map.put(entry.getKey(), tempTank );
		}
		
		TANK_CONTROLLER.importMap(map);
	}
	
	public Map<NetworkTarget, MissileInfo> exportMissileInfo() {
		Map<NetworkTarget, MissileInfo> map = new TreeMap<NetworkTarget, MissileInfo>(new NTComparator());
		
		for (Map.Entry<NetworkTarget, Missile> entry : MISSILE_CONTROLLER.exportMap().entrySet()) {
			int parPosX = entry.getValue().getPosX();
			int parPosY = entry.getValue().getPosY();
			int parAngle = entry.getValue().getAngle();
			int parRange = entry.getValue().getRange();
			map.put(entry.getKey(), new MissileInfo(parPosX, parPosY, parAngle, parRange));
		}
		
		return map;
	}
	
	public void importMissileInfo(Map<NetworkTarget, MissileInfo> parmap) {
		
		Map<NetworkTarget, Missile> map = new TreeMap<NetworkTarget, Missile>(new NTComparator());
		
		for (Map.Entry<NetworkTarget, MissileInfo> entry : parmap.entrySet()) {
			int posX = entry.getValue().getPosX();
			int posY = entry.getValue().getPosY();
			int angle = entry.getValue().getAngle();
			int range = entry.getValue().getRange();
			Missile tempMissile = new Missile(entry.getKey(), angle, range, posX, posY, gc);
			map.put(entry.getKey(), tempMissile);
		}
		
		MISSILE_CONTROLLER.importMap(map);
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


	public boolean moveTank(NetworkTarget nt, int angle) {
		if(TANK_CONTROLLER.contains(nt) == false){
			return false;
		}
		switch(angle){
		case 0: TANK_CONTROLLER.get(nt).moveRight(); break; 
		case 90: TANK_CONTROLLER.get(nt).moveUp(); break;
		case 180: TANK_CONTROLLER.get(nt).moveLeft(); break;
		case 270: TANK_CONTROLLER.get(nt).moveDown(); break;
		}
		return true;
	}



	public void importMissileMap(Map<NetworkTarget, Missile> map) {
		MISSILE_CONTROLLER.importMap(map);		
	}

	public Map<NetworkTarget, Missile> exportMissileMap() {
		return MISSILE_CONTROLLER.exportMap();
	}


	public void enterRegion(NetworkTarget nt, Point pos, int angle) {
		TANK_CONTROLLER.enterRegion(nt, pos, angle);	
	}
	
	public int getRegionId(){
		return MAP_ELEMENTS.getRegionId();
	}

}
