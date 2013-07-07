package overlay;

import java.awt.Dimension;
import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;
import network.ConnectionManager;
import utils.Pair;
import model.NetworkObject;
import model.NetworkTarget;
import model.NetworkObject.dataType;
import comparator.NTComparator;
import java.util.Date;

public class OverlayManager {

	Map<NetworkTarget, Pair > map;
	Date date= new Date();
	ConnectionManager man = new ConnectionManager();
	NetworkObject networkObject = new NetworkObject();

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

			// Check Timestamp expiration
			if(map.get(nt).getTimeStamp().before(new Timestamp(System.currentTimeMillis()))==true) {

				if(map.get(nt).getType()==0) {

					//CASE 1: RC failure
					/*send ping message, if replies in 10 msec, peer alive else dead
					 * If RC dead, get backup RC
					 */
					NetworkObject toSend = new NetworkObject();
					toSend.type = dataType.Ping;
					man.Send(nt, toSend);
					
					
					//if fails, remove it from list
					map.remove(nt);
					// Assign new backup RC
					//map.get(nt).getType()
				}
				
				//CASE 2: Peer failure
				else
				{
					//Peer failure
					/*
					 * Send ping message, if replies in 10 msec, peer alive else dead
					 * IF dead, inform RC and remove peer from list
					 */
					NetworkObject toSend = new NetworkObject();
					toSend.type = dataType.Ping;
					man.Send(nt, toSend);
					
					//Receive pong
					
					
					//if fails, remove it from list
					map.remove(nt);
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
