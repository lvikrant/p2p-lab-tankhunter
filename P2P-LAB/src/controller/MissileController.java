package controller;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import model.Missile;
import model.NetworkTarget;
import model.Tank;
import comparator.NTComparator;

/**
 * Management and storage class for Missiles
 *
 */
public class MissileController {
	
	private GameController gc;

	private final int MISSILE_LIMIT;

	Map<NetworkTarget, Missile> map = new TreeMap<NetworkTarget, Missile>(
			new NTComparator());

	/**
	 * Constructor
	 * @param gameController Reference to parent gameController
	 * @param missileLimit limit of how many missiles can be stored
	 */
	public MissileController(GameController gameController, int missileLimit) {
		gc = gameController;
		MISSILE_LIMIT = missileLimit;
	}
	
	/**
	 * Adds a new Missile to the map
	 * @param nt Creator the the missile
	 * @param Pos actual position of the missile
	 * @param Angle Angle of the missile
	 * @param Range Range of the missile
	 */
	public void add(NetworkTarget nt, Point pos, int angle, int range) {	
			map.put(nt, new Missile(gc, nt, pos, angle,range));
	}

	public boolean contains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	public void importMap(Map<NetworkTarget, Missile> parMap) {
		map = parMap;
		
	}

	public Map<NetworkTarget, Missile> exportMap() {
		return map;
	}

}
