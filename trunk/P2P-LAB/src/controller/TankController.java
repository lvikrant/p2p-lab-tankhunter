package controller;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import comparator.NTComparator;

import model.NetworkTarget;
import model.Sound;
import model.Tank;

public class TankController {

	private GameController gc;

	private final int TANK_LIMIT;

	Map<NetworkTarget, Tank> map = new TreeMap<NetworkTarget, Tank>(
			new NTComparator());

	public TankController(GameController gameController, int tankLimit) {
		gc = gameController;
		TANK_LIMIT = tankLimit;
	}

	boolean addRandom(NetworkTarget nt) {

		int posX = (int) (Math.random() * (gc.getMapWidth()));
		int posY = (int) (Math.random() * (gc.getMapHeight()));
		Point pos = new Point(posX, posY);

		if (gc.getFieldInfo(pos).equals("FREE")) {
			Tank tank = new Tank(gc, nt, pos, 0);
			if (map.size() <= TANK_LIMIT) {
				map.put(nt, tank);
				gc.getMainRegion().addTank(nt, pos, 0);
				if(nt == gc.getPlayer()){
					gc.setGamePanelMiddle(pos);
				}
				return true;
			} else {
				return false;
			}

		} else {
			return addRandom(nt);
		}
	}

	public Tank get(NetworkTarget nt) {
		return map.get(nt);
	}

	boolean add(NetworkTarget nt, Point pos, int angle) {
		if (gc.getFieldInfo(pos).equals("FREE")) {
			Tank tank = new Tank(gc, nt, pos, angle);
			map.put(nt, tank);
			gc.getMainRegion().addTank(nt, pos, 0);
			if(nt == gc.getPlayer()){
			//	gc.setGamePanelMiddle(pos);
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
	}

	public Map<NetworkTarget, Tank> exportMap() {
		return map;
	}

	public void destroy(NetworkTarget nt) {
		Sound.play("unitDown");
		gc.getMainRegion().destroyTank(nt);
	}

}
