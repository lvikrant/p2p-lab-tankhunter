package overlay;

import java.awt.Dimension;
import java.util.Map;
import java.util.TreeMap;

import utils.Info;
import utils.Pair;

import model.NetworkTarget;

import comparator.NTComparator;

public class Manager {

	Map<NetworkTarget, Info > map;
	
	public Manager(){
		map  = new TreeMap<NetworkTarget, Info>(new NTComparator());
	}
		
	
	public void addEntry(NetworkTarget nt, int type, int timeStamp, int myRC){
		map.put(nt, new Info(type, timeStamp,myRC));
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
	
	public int getMyRC(NetworkTarget nt)
	{
		if(map.containsKey(nt))
		{
			return map.get(nt).getMyRC();
		}
		return -1;
		}
	public void setMyRC(NetworkTarget nt,int RC){
		if(map.containsKey(nt)){
			map.get(nt).setMyRC(RC);
		}
	}
	
	public Map<NetworkTarget, Info> exportOverlayInfo(){
		return map;
	}
	
	public void importOverlayInfo(Map<NetworkTarget, Info> parMap){
		map = parMap;
	}
	
	private void setCounter(){
		
	}
	private int getCounter()
	{
		return 0;		
	}
}
