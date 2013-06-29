package overlay;

import java.awt.Dimension;
import java.util.Map;
import java.util.TreeMap;

import utils.Pair;

import model.NetworkTarget;

import comparator.NTComparator;

public class OverlayManager {

	Map<NetworkTarget, Pair > map;
	
	public OverlayManager(){
		map  = new TreeMap<NetworkTarget, Pair>(new NTComparator());
	}
		
	
	public void addEntry(NetworkTarget nt, int type, int timeStamp){
		map.put(nt, new Pair(type, timeStamp));
	}
	
	public int getType(NetworkTarget nt){
		if(map.containsKey(nt)){
			return map.get(nt).getType();
		}
		return -1;
	}
	
	public int getTimeStamp(NetworkTarget nt){
		if(map.containsKey(nt)){
			return map.get(nt).getTimeStamp();
		}
		return -1;
	}
	
	public void setType(NetworkTarget nt, int type){
		if(map.containsKey(nt)){
			map.get(nt).setType(type);
		}
		System.err.println("No network-target!");
	}
	
	public void setTimeStamp(NetworkTarget nt, int timeStamp){
		if(map.containsKey(nt)){
			map.get(nt).setTimeStamp(timeStamp);
		}
		System.err.println("No network-target!");
	}
	
	public Map<NetworkTarget, Pair> exportOverlayInfo(){
		return map;
	}
	
	public void importOverlayInfo(Map<NetworkTarget, Pair> parMap){
		map = parMap;
	}
	
}
