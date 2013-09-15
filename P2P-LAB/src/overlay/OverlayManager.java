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
import model.TankInfo;
import model.NetworkObject.dataType;
import comparator.NTComparator;
import java.util.Date;

public class OverlayManager {

	Map<NetworkTarget, Pair > map;
	Date date= new Date();
	UpdateGameState upGameState;
	NetworkObject networkObject = new NetworkObject();

	/**
	 * Constructor to set the UpdateGameState class instance
	 * @param upGameState instance of UpdateGameState class
	 */
	public OverlayManager(UpdateGameState upGameState){
		this.upGameState = upGameState;
		map  = new TreeMap<NetworkTarget, Pair>(new NTComparator());
	}

	/**
	 * Method to check if the peer has any active connections
	 * @return
	 */
	public boolean hasClients(){
		System.out.println("Size: "  + map.size());
		for (Map.Entry<NetworkTarget, Pair> entry : map.entrySet()) {
		
			if(entry.getValue().getType() == 1 || entry.getValue().getType() == 2){
				return true;
			}
		}
		return false;
	}

	/**
	 * To add/update a new peers information
	 * @param nt network address of the peer
	 * @param type client/Backup RC/RC
	 * @param date2 timestamp
	 */
	public void addEntry(NetworkTarget nt, int type, Date date2){
		System.out.println("HERE");
		System.out.println(nt.getIP());
		map.put(nt, new Pair(type, date2));
	}

	/**
	 * Deletes a peer information from the overlay layer
	 * @param nt network address of the peer
	 */
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

	/**
	 * Exports all active peer information from the region
	 * @return all peers information
	 */
	public Map<NetworkTarget, Pair> exportOverlayInfo(){
		return map;
	}

	/**
	 * Imports information about all active peers
	 * @param parMap all peers information
	 */
	public void importOverlayInfo(Map<NetworkTarget, Pair> parMap){
		map = parMap;
	}
	
	/**
	 * Method to return the RC of the peer
	 * @return NetworkTarget of the RC
	 */
	public NetworkTarget getRC() {
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry data = (Map.Entry)it.next();
			if(((Pair)(data.getValue())).getType() == 0)
				return (NetworkTarget)data.getKey();
		}
		return null;
	}
	
	/**
	 * Method to return the list of clients
	 * @return list of NetworkTarget
	 */
	public List<NetworkTarget> getClients() {
		
		List<NetworkTarget> list = new LinkedList<NetworkTarget>();
		
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry data = (Map.Entry)it.next();
			if(((Pair)(data.getValue())).getType() == 1 || ((Pair)(data.getValue())).getType() == 2)
				list.add((NetworkTarget)data.getKey());
		}
		return list;
	}
	
	/**
	 * Method to return a list of backup RC
	 * @return list of NetworkTarget
	 */
	public List<NetworkTarget> getBackupRCs() {
		
		List<NetworkTarget> list = new LinkedList<NetworkTarget>();
		
		for (Map.Entry<NetworkTarget, Pair> entry : map.entrySet()){
			if(entry.getValue().getType() == 2){
				list.add(entry.getKey());
			}
		}	
		return list;
	}
}
