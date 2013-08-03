package overlay;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import utils.Pair;
import model.NetworkObject;
import model.NetworkTarget;
import model.NetworkObject.dataType;
import comparator.NTComparator;
import java.util.Date;

public class OverlayManager {

	Map<NetworkTarget, Pair > map;
	Date date= new Date();
	UpdateGameState upGameState;
	NetworkObject networkObject = new NetworkObject();

	public OverlayManager(UpdateGameState upGameState){
		this.upGameState = upGameState;
		map  = new TreeMap<NetworkTarget, Pair>(new NTComparator());
	}

	
	public boolean hasClients(){
		System.out.println("Size: "  + map.size());
		for (Map.Entry<NetworkTarget, Pair> entry : map.entrySet()) {
		
			if(entry.getValue().getType() == 1){
				return true;
			}
		}
		return false;
	}

	public void addEntry(NetworkTarget nt, int type, Date date2){
		System.out.println("HERE");
		System.out.println(nt.getIP());
		map.put(nt, new Pair(type, date2));
	}

	public void deleteEntry(NetworkTarget nt)
	{
		map.remove(nt);
	}
	public int getType(NetworkTarget nt){
		if(map.containsKey(nt)){
			return map.get(nt).getType();
		}
		return -1;
	}

	public Date getTimeStamp(NetworkTarget nt){
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
	
	public NetworkTarget getRC() {
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry data = (Map.Entry)it.next();
			if(((Pair)(data.getValue())).getType() == 0)
				return (NetworkTarget)data.getKey();
		}
		return null;
	}
	
	public List<NetworkTarget> getClients() {
		
		List<NetworkTarget> list = new LinkedList<NetworkTarget>();
		
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry data = (Map.Entry)it.next();
			if(((Pair)(data.getValue())).getType() == 1)
				list.add((NetworkTarget)data.getKey());
		}
		return list;
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
					upGameState.man.Send(nt, toSend);
					
					
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
					upGameState.man.Send(nt, toSend);
					
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
