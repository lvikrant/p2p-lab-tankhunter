package controller;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import model.Missile;
import model.NetworkTarget;

import comparator.NTComparator;

public class MissileController {
	
	private GameController gc;

	private final int MISSILE_LIMIT;

	Map<NetworkTarget, Missile> map = new TreeMap<NetworkTarget, Missile>(
			new NTComparator());

	public MissileController(GameController gameController, int missileLimit) {
		gc = gameController;
		MISSILE_LIMIT = missileLimit;
	}

	public boolean contains(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

}
