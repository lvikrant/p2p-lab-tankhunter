package controller;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import utils.Move;

import comparator.NTComparator;
import model.NetworkObject;
import model.RegionTypes;
import model.Missile;
import model.MissileInfo;
import model.NetworkTarget;
import model.PowerUp;
import model.Tank;
import model.TankInfo;
import model.NetworkObject.dataType;
import interfaces.IObjectController;

public class ObjectController implements IObjectController{


	private final PowerUpController POWER_UP_CONTROLLER;
	private final TankController TANK_CONTROLLER;
	private final MissileController MISSILE_CONTROLLER;

	private final RegionTypes MAP_ELEMENTS;
	private GameController gc;

	private boolean readyToBecomeRC = false;

	private List<NetworkTarget> list = new LinkedList<NetworkTarget>();

	public ObjectController(GameController gameC, int powerUpLimit,int tankLimit, int missileLimit, int mapID) {
		gc = gameC;
		POWER_UP_CONTROLLER = new PowerUpController(gameC,powerUpLimit);
		TANK_CONTROLLER = new TankController(gameC,tankLimit);
		MISSILE_CONTROLLER = new MissileController(gameC,missileLimit);
		MAP_ELEMENTS = new RegionTypes(gameC,mapID);

	}


	public GameController getGc() {
		return gc;
	}


	public void setGc(GameController gc) {
		this.gc = gc;
	}


	/************ POWER_UP_CONTROLLER *******************************************************************************/

	public void addPowerUpRandom(){
		POWER_UP_CONTROLLER.addRandom();
	}

	public void addPowerUp(Point point,String power) {
		POWER_UP_CONTROLLER.add(point,power);
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

	public void addTankRandom(NetworkTarget nt, boolean init){
		TANK_CONTROLLER.addRandom(nt,init);
	}

	public void jumpTank(NetworkTarget nt, int angle,Point pos){
		TANK_CONTROLLER.jumpTank(nt,angle,pos);
	}

	public void addPowerUp(PowerUp powerUp) {
		POWER_UP_CONTROLLER.add(powerUp.getPos(),powerUp.getPowerUp());

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
			Missile tempMissile = new Missile(gc, entry.getKey(), new Point(posX,posY), angle, range);
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

	public void forceAddMissile(NetworkTarget nt, Point pos, int angle, int range){
		MISSILE_CONTROLLER.add(nt, pos, angle, range);
		if(TANK_CONTROLLER.contains(nt)){
			TANK_CONTROLLER.get(nt).fire(nt, pos, angle, range);
		}
	}


	public void addMissile(NetworkTarget nt, Point pos, int angle, int range){
		if(gc.isDead(nt)){
			return;
		}
		if(gc.isRegionController()){
			MISSILE_CONTROLLER.add(nt, pos, angle, range);
			NetworkObject no = new NetworkObject();
			no.type = dataType.AddMissile;
			no.dataTarget = nt;
			no.point = pos;
			no.angle = angle;
			no.range = range;
			gc.overlay.SendToAllClients(no);	
		} else {
			NetworkObject no = new NetworkObject();
			no.type = dataType.AddMissileRequest;
			no.dataTarget = nt;
			no.point = pos;
			no.angle = angle;
			no.range = range;
			gc.overlay.SendToRC(no);
		}
	}





	/******************* MAP_ELEMENTS *********************************************************************************************/

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

	public void forceMoveTank(NetworkTarget nt, int angle, Point pos) {


		if(!(TANK_CONTROLLER.get(nt).getPos().equals(pos))){		
			System.err.println("NOT SYNCHRONISED");

			System.err.println("Server Pos" + pos);
			System.err.println("Client Pos" + TANK_CONTROLLER.get(nt).getPos());

			TANK_CONTROLLER.jumpTank(nt, angle, pos);
		}

		switch(angle){
		case 0: TANK_CONTROLLER.get(nt).moveRight(); break; 
		case 90: TANK_CONTROLLER.get(nt).moveUp(); break;
		case 180: TANK_CONTROLLER.get(nt).moveLeft(); break;
		case 270: TANK_CONTROLLER.get(nt).moveDown(); break;
		}

	}


	public boolean moveTank(NetworkTarget nt, int angle) {



		if(!TANK_CONTROLLER.contains(nt)){
			return false;
		}

		if(gc.isDead(nt)){
			return false;
		}

		Point pos = TANK_CONTROLLER.get(nt).getPos();


		if(gc.isRegionController()){

			/*

			if(TANK_CONTROLLER.get(nt).checkIfNewRegion(angle)) {
				if(gc.getMe().equals(nt)){
					//Make someone other RC of this Region



					gc.moveToNextRegion(TANK_CONTROLLER.get(nt).getPos(), angle);
				} else {
					//Send nt to new Region


				}



			}

			 */
			boolean ready = false;
			switch(angle){
			case 0: ready = TANK_CONTROLLER.get(nt).moveRight(); break; 
			case 90: ready = TANK_CONTROLLER.get(nt).moveUp(); break;
			case 180: ready = TANK_CONTROLLER.get(nt).moveLeft(); break;
			case 270: ready = TANK_CONTROLLER.get(nt).moveDown(); break;
			}
			if(ready) {
				NetworkObject no = new NetworkObject();
				no.type = dataType.MoveTank;
				no.move = new Move(nt,angle,pos);
				gc.overlay.SendToAllClients(no);
			} else {
				NetworkObject no = new NetworkObject();
				no.type = dataType.RotateTank;
				no.dataTarget = nt;
				no.angle = angle;
				gc.overlay.SendToAllClients(no);
			}

		} else {
			NetworkObject no = new NetworkObject();
			no.type = dataType.MoveRequest;
			no.move = new Move(nt,angle);
			//gc.overlay.sendUpdatesToRC(controller, connection, target)
			gc.overlay.SendToRC(no);

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


	public void setMe(NetworkTarget networkTarget) {
		gc.setMe(networkTarget);

	}

	public NetworkTarget getMe() {
		return gc.getMe();
	}


	public void setRegionType(int regionType) {
		MAP_ELEMENTS.setRegion(regionType);	
	}

	public void setNewRegionTypes(int[] regionTypes){
		MAP_ELEMENTS.setRegion(regionTypes[4]);
		gc.gameWindow.setRegionTypes(regionTypes);
		//Alle
	}

	public int[] getRegionTypes() {
		return gc.gameWindow.getRegionTypes();
	}


	@Override
	public boolean moveTank(NetworkTarget nt, int angle, Point pos) {
		// TODO Auto-generated method stub
		return false;
	}


	public void rotateTank(NetworkTarget nt, int angle) {
		TANK_CONTROLLER.rotateTank(nt,angle);
	}

	public void sendExitGameRequest() {	

		if(gc.isRegionController()){

			if(!gc.overlay.getOverlayManager().hasClients()){
				//TODO send to all RC : EXIT-MESSAGE
				System.exit(0);
			}  else {
				System.out.println("sendExitGameRequest RC has clients");
				NetworkObject no = new NetworkObject();
				no.type = dataType.ExitPermission;
				no.dataTarget = gc.getMe();
				gc.overlay.SendToOneClient(no, no.dataTarget);
				
			}

		} else {

			NetworkObject no = new NetworkObject();
			no.type = dataType.ExitRequest;
			no.dataTarget = gc.getMe();
			gc.overlay.SendToRC(no);

		}	

	}

	public void getBackupRCAck() {
	/*	readyToBecomeRC = true;
		NetworkObject no = new NetworkObject();
		no.type = dataType.NewRCPing;			
		gc.overlay.SendToOneClient(no,nt);*/

	}

	public void getExitGameRequest(NetworkTarget nt) {
		if(gc.isRegionController()){
			System.out.println("Get exit game request RC");
			TANK_CONTROLLER.remove(nt);

			NetworkObject no = new NetworkObject();
			no.type = dataType.ExitPermission;
			gc.overlay.SendToOneClient(no,nt);
			gc.overlay.getOverlayManager().deleteEntry(nt);
			
			//Check this
			readyToBecomeRC = true;
		} else {

			NetworkObject no = new NetworkObject();
			no.type = dataType.ExitPermission;
			no.dataTarget = gc.getMe();
			gc.overlay.SendToOneClient(no,nt);

		}	
	}

	@Override
	public void exitGamePermission() {
		if(!gc.isRegionController()){
			System.exit(0);	
		}
	}


	@Override
	public void exitGamePermission(NetworkTarget nt) {

		if(readyToBecomeRC == false){

			System.out.println("exitGamePermission RC");
			NetworkObject no = new NetworkObject();
			no.type = dataType.ExitAck;
			//		no.peers
			gc.overlay.SendToOneClient(no,nt);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
		else {
		//readyToBecomeRC = true;
			NetworkObject no = new NetworkObject();
			no.type = dataType.NewRCPing;			
			gc.overlay.SendToOneClient(no,nt);
		}
	}

	public void printRegionType() {

	}
}