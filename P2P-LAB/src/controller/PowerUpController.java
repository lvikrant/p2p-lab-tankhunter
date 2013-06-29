package controller;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import comparator.PointComparator;

import model.NetworkObject;
import model.NetworkObject.dataType;
import model.PowerUp;


public class PowerUpController{
	
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
				gc.getMainRegion().addPowerUp(point,powerUp.getPowerUp());
				
				NetworkObject no = new NetworkObject();
				no.type = dataType.AddPowerUp;
				no.powerUp = powerUp;
				gc.overlay.man.sendToAll(no);
				
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
				gc.getMainRegion().addPowerUp(pos,powerUp.getPowerUp());
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
			gc.getMainRegion().remove(point);
			map.remove(point);
		} else {
			System.err.println("An der Stelle : " + point + " gibt es keinen PowerUp");
		}
	}
	
	public void importMap(Map<Point, PowerUp> map){
		removeAll();
		this.map = map;
		
		for (Map.Entry<Point, PowerUp> entry : map.entrySet()) {
			gc.gameWindow.getMainRegion().addPowerUp(entry.getKey(), entry.getValue().getPowerUp());
		}
	}
	
	public Map<Point, PowerUp> exportMap(){
		return map;
	}
	
	public void removeAll(){
		for (Map.Entry<Point, PowerUp> entry : map.entrySet()){
			gc.getMainRegion().remove(entry.getKey());
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

	public void add(PowerUp powerUp) {
		map.put(powerUp.getPos(), powerUp);
		gc.getMainRegion().addPowerUp(powerUp.getPos(),powerUp.getPowerUp());
		
	}

}
