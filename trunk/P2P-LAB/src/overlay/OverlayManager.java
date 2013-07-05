package overlay;

import java.awt.Dimension;
import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;

import utils.Pair;

import model.NetworkTarget;

import comparator.NTComparator;

public class OverlayManager {

	Map<NetworkTarget, Pair > map;
	java.util.Date date= new java.util.Date();


	public OverlayManager(){
		map  = new TreeMap<NetworkTarget, Pair>(new NTComparator());
	}


	public void addEntry(NetworkTarget nt, int type, Timestamp timeStamp){
		map.put(nt, new Pair(type, timeStamp));
	}

	public int getType(NetworkTarget nt){
		if(map.containsKey(nt)){
			return map.get(nt).getType();
		}
		return -1;
	}

	public Timestamp getTimeStamp(NetworkTarget nt){
		if(map.containsKey(nt)){
			return map.get(nt).getTimeStamp();
		}
		return null;
	}

	public void setType(NetworkTarget nt, int type){
		if(map.containsKey(nt)){
			map.get(nt).setType(type);
		}
		System.err.println("No network-target!");
	}

	public void setTimeStamp(NetworkTarget nt, Timestamp timeStamp){
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

	public void checkPeerAlive(NetworkTarget nt) {
		
		if(map.containsKey(nt)) {
			if(map.get(nt).getType()==0)
			{
				//RC failure
				if(map.get(nt).getTimeStamp().before(new Timestamp(System.currentTimeMillis()))==true)
				{
					/*send ping message, if replies in 10 msec, peer alive else dead
					 * If RC dead, get backup RC
					*/
					//map.get(nt)
				}
				
			}
			else
			{
				//Peer failure
				/*
				 * Send ping message, if replies in 10 msec, peer alive else dead
				 * IF dead, inform RC and remove peer from list
				 */
				if(map.get(nt).getTimeStamp().before(new Timestamp(System.currentTimeMillis()))==true)
				{
					
					//check if the peer is Backup RC
					if(map.get(nt).getType()==2||map.get(nt).getType()==3||map.get(nt).getType()==4)
					{
						
					}
				}
			}
		}
	}

	public void assignBackup(NetworkTarget nt) {

		if(map.containsKey(nt)) {
			switch(map.get(nt).getType())
			{
			case 2:
				map.get(nt).setType(3);
			case 3:
				map.get(nt).setType(4);

			default:
				map.get(nt).setType(2);
			}
		}
	}


}
