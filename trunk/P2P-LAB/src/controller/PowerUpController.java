package controller;

import interfaces.IPowerUpController;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import comparator.PointComparator;

import model.PowerUp;

public class PowerUpController implements IPowerUpController {
	
	Map<Point,PowerUp> map= new TreeMap<Point, PowerUp>(new PointComparator());
	private GameController gc;
	private final int MAX_ELEMENTS;
	
	public PowerUpController(GameController parGC, int maxelem){

		gc = parGC;
		MAX_ELEMENTS = maxelem;
	}
	
	public boolean add(Point point){
		if(gc.getFieldInfo(point).equals("FREE")){	
			PowerUp powerUp = new PowerUp(point);
			
			if(map.size() <= MAX_ELEMENTS){
				map.put(powerUp.getPos(), powerUp);
				gc.gameWindow.gp.addPowerUp(point,powerUp.getPowerUp());
				return true;
			}		
		}
		return false; 
	}
	
	public boolean addRandom(){
		int posX = (int)(Math.random()*(gc.getMapWidth()));
		int posY = (int)(Math.random()*(gc.getMapHeight()));
		Point pos = new Point(posX,posY);
		
		if(gc.getFieldInfo(pos).equals("FREE")){	
			PowerUp powerUp = new PowerUp(pos);
			if(map.size() <= MAX_ELEMENTS){
				map.put(powerUp.getPos(), powerUp);
			    gc.gameWindow.gp.addPowerUp(pos,powerUp.getPowerUp());
		    	return true;
		    } else {
				return false;
		    }

		} else {
			return addRandom();
		}
		
	}
	
	public void remove(Point point){
		
		if(map.containsKey(point)){
			gc.gameWindow.gp.remove(point);
			map.remove(point);
		} else {
			System.err.println("An der Stelle : " + point + " gibt es keinen PowerUp");
		}
	}
	
	public void importMap(Map<Point, PowerUp> map){
		removeAll();
		this.map = map;
	}
	
	public Map<Point, PowerUp> exportMap(){
		return map;
	}
	
	public void removeAll(){
		for (Map.Entry<Point, PowerUp> entry : map.entrySet()){
			gc.gameWindow.gp.remove(entry.getKey());
		}
		map.clear();
	}

	
	public boolean contains(Point point){
		return map.containsKey(point);
	}
	
	public int getSize(){
		return map.size();
	}

	public int getMaxSize() {
		return MAX_ELEMENTS;
	}
	
	public PowerUp get(Point point){
		return map.get(point);
	}

}
