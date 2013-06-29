/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import interfaces.IObjectController;
import model.NetworkObject;
import model.NetworkObject.dataType;
import model.NetworkTarget;
import model.Tank;
import network.ConnectionManager;

/**
 *
 * @author dell
 */
public class UpdateGameState extends Thread {

	public ConnectionManager man;
	boolean iAmRC = false;
	IObjectController controller;
	private Queue<NetworkObject> dataToBroadcast = new ConcurrentLinkedQueue<NetworkObject>();
	
	
	/**
	 * Start as client
	 * @param target NetworkTarget of the RC to connect to
	 */
	public UpdateGameState(IObjectController _controller, NetworkTarget target) {
		controller = _controller;
		iAmRC = false;
		man = new ConnectionManager();
		man.Connect(target);

		NetworkObject networkObject = new NetworkObject();
		
		new Thread(man).start();
		new Thread(this).start();
		
		networkObject.type = dataType.Init;
		man.Send(target, networkObject);

	}
	/**
	 * Start as RC
	 * @param port Port to listen at
	 */
	public UpdateGameState(IObjectController _controller, int port) {
		controller = _controller;
		iAmRC = true;
		man = new ConnectionManager(port);
		new Thread(man).start();
		new Thread(this).start();

	}



	@Override
	public void run() {

		NetworkObject tmpNo;
		while(true) {

			//check for new received messages
			for(NetworkObject no: man.getNewData()){
				switch(no.type) {
				case Init:
					if(iAmRC) {
						tmpNo = new NetworkObject();
						tmpNo.type = dataType.Init;
						controller.addTankRandom(no.target);
						tmpNo.tankData = controller.exportTankInfo(); 
						//tmpNo.powerUpData = controller.exportPowerUpMap();
						//tmpNo.missileData = controller.exportMissileMap();
						//tmpNo.region = controller.getRegionId();
						man.Send(no.target, tmpNo);
					} else {
						System.out.println("Hello10");
						
						controller.importTankInfo(no.tankData);
						controller.importPowerUpMap(no.powerUpData);
						controller.importMissileMap(no.missileData);
						controller.setElements(no.region);
		
					}

					break;
				case Move:
					if(iAmRC) {
						
						//TODO: pass information up
						
					} else {
						controller.moveTank(no.dataTarget, no.angle);
									
					}

					break;
				case Shoot:
					if(iAmRC) {
						
						//TODO: pass information up
					} else {
						
						//controller.MoveMissile(no.dataTarget, no.missile);
					}
					break;

				default:
					break;
				}
			}
			
			
			//check if messages have to been send
			synchronized (dataToBroadcast) {
				for(NetworkObject no: dataToBroadcast) {
					man.sendToAll(no);
				}
				
			}
		
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			


		}

	}

	/**
	 * Registers a new peer at the region Controller
	 * 
	 * @param target
	 *      the peers IP and host
	 * @param serverPort
	 *      the RC port address
	 * @return connectionManager
	 *      Object of ConnectionManager
	 */

	/*
	public ConnectionManager registerPeer(NetworkTarget target, int serverPort) {

		NetworkObject networkObject = new NetworkObject();
		networkObject.type = dataType.Init;
		ConnectionManager connectionManager = new ConnectionManager(serverPort);
		new Thread(connectionManager).start();
		connectionManager.Connect(target);
		connectionManager.Send(target, networkObject);
		return connectionManager;
	}
	 */

	public synchronized void receiveUpdates(NetworkObject object) {
		/**
		 * Calculate the new data structures and invoke sendUpdatesToALL method
		 */
	}

	/**
	 * Send updates too all peers
	 * @param connection
	 *      ConnectionManager object which contains all peers connectionHandlers
	 * @param object 
	 *      NetworkObject with data required to be send across
	 */
	public void sendUpdatesToALL(ConnectionManager connection, NetworkObject object) {
		new Thread(connection).start();
		connection.sendToAll(object);
	}

	/**
	 * A peer can send updates to the RC 
	 * @param controller
	 *      The controller object which contains the game data
	 * @param connection
	 *      ConnectionManager object which has the connection to the RC
	 * @param target 
	 *      The peers IP and host
	 */
	public void sendUpdatesToRC(IObjectController controller, ConnectionManager connection, NetworkTarget target) {

		// Retrieve missile updates and send it to RC
		NetworkObject networkObject = new NetworkObject();
		networkObject.type = dataType.Move;
		networkObject.tank = controller.getTank(target);
		connection.Send(target, networkObject);
		networkObject.type = dataType.Shoot;
		//networkObject.missile = 
		//connection.Send(target, networkObject);
	}

}
