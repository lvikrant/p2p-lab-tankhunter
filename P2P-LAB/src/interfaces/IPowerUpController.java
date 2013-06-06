package interfaces;

import java.awt.Point;
import java.util.Map;

import model.PowerUp;

public interface IPowerUpController {
	
	public boolean add(Point point);

	public boolean addRandom();

	public void remove(Point point);
	
	public void importMap(Map<Point,PowerUp> map);
	
	public Map<Point,PowerUp> exportMap();

	public void removeAll();
	
	public boolean contains(Point point);
	
	public int getSize();

	public int getMaxSize();
	
}
