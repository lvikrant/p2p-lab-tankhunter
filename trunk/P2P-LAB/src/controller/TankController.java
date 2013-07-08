package controller;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import comparator.NTComparator;

import model.NetworkObject;
import model.NetworkTarget;
import model.Sound;
import model.Tank;
import model.TankInfo;
import model.NetworkObject.dataType;

public class TankController {

	private GameController gc;

	private final int TANK_LIMIT;

	Map<NetworkTarget, Tank> map = new TreeMap<NetworkTarget, Tank>(new NTComparator());

	public TankController(GameController gameController, int tankLimit) {
		gc = gameController;
		TANK_LIMIT = tankLimit;
	}

	boolean addRandom(NetworkTarget nt, boolean init) {

		int posX = (int) (Math.random() * (gc.getMapWidth()));
		int posY = (int) (Math.random() * (gc.getMapHeight()));
		Point pos = new Point(posX, posY);
		
		int random = (int)Math.random()* 3;
		
		int angle = 0;
		switch(random){
		case 0 : angle = 0; break;
		case 1 : angle = 90; break;
		case 2 : angle = 180; break;
		case 3 : angle = 270; break;
		}


		if (gc.getFieldInfo(pos).equals("FREE")) {
			Tank tank = new Tank(gc, nt, pos, 0);
			if (map.size() <= TANK_LIMIT) {
				map.put(nt, tank);
				gc.getMainRegion().addTank(nt, pos, 0);
				if(nt == gc.getMe()){
					gc.setGamePanelMiddle(pos);
				}
				
				if(gc.isRegionController() && !init){
		    		NetworkObject no = new NetworkObject();
					no.type = dataType.AddTank;
					no.dataTarget = nt;
					no.point = pos;
					no.angle = angle;
					gc.overlay.SendToAllClients(no);
				}

				return true;
			} else {
				return false;
			}

		} else {
			return addRandom(nt, init);
		}
	}

	public Tank get(NetworkTarget nt) {
		return map.get(nt);
	}
	
	public NetworkTarget get(Point point) {
		for (Map.Entry<NetworkTarget, Tank> entry : map.entrySet()){
			if(entry.getValue().getPos().equals(point)){
			    return entry.getKey();
			}
		}
		System.err.println("NO TANK AT POINT : " + point);
		return null;
	}

	boolean add(NetworkTarget nt, Point pos, int angle) {
		if (gc.getFieldInfo(pos).equals("FREE")) {
			Tank tank = new Tank(gc, nt, pos, angle);
			map.put(nt, tank);
			gc.getMainRegion().addTank(nt, pos, angle);
			if(nt == gc.getMe()){
				gc.setGamePanelMiddle(pos);
			}
			return true;
		}
		return false;
	}

	public void remove(NetworkTarget nt) {
		if (map.containsKey(nt)) {
			gc.getMainRegion().removeTank(nt);
			map.remove(nt);
		}

	}

	public int getSize() {
		return map.size();
	}

	public boolean contains(NetworkTarget nt) {

		return map.containsKey(nt);
	}

	public boolean contains(Point point) {
		for (Map.Entry<NetworkTarget, Tank> entry : map.entrySet()) {
			if (entry.getValue().getPos().equals(point)) {
				return true;
			}
		}
		return false;

	}

	public int getMaxSize() {
		return TANK_LIMIT;
	}

	public void importMap(Map<NetworkTarget, Tank> newMap) {
		map = newMap;
		
		for (Map.Entry<NetworkTarget, Tank> entry : newMap.entrySet()) {
		
			int parPosX = entry.getValue().getPosX();
			int parPosY = entry.getValue().getPosY();
			int parAngle = entry.getValue().getAngle();
			gc.gameWindow.getMainRegion().addTank(entry.getKey(), new Point(parPosX, parPosY), parAngle);
			
			if(entry.getKey().equals(gc.getMe())){
				
				gc.gameWindow.setGamePanelMiddle(new Point(parPosX,parPosY));
			}
		}
	}

	public Map<NetworkTarget, Tank> exportMap() {
		return map;
	}

	public void destroy(NetworkTarget nt) {
		Sound.play("unitDown");
		gc.getMainRegion().destroyTank(nt);
	}

	public void enterRegion(NetworkTarget nt, Point pos, int angle) {
		if (gc.getFieldInfo(pos).equals("FREE")) {
			Tank tank = new Tank(gc, nt, pos, angle);
			map.put(nt, tank);
			gc.getMainRegion().enterRegion(nt, pos, angle);
			if(nt == gc.getMe()){
			//	gc.setGamePanelMiddle(pos);
			}
		}
		
	}

	public void jumpTank(NetworkTarget nt, int angle, Point pos) {
			if(map.containsKey(nt)){
				map.get(nt).setPosition(pos);
				map.get(nt).setTankAngle(angle);
				gc.gameWindow.getMainRegion().jumpTank(nt,angle,pos);
				gc.gameWindow.setGamePanelMiddle(pos);
			}
		
	}

}
