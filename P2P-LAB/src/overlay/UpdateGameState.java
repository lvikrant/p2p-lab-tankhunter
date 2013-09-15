/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import java.awt.Point;
import java.util.Date;
import java.util.List;
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
	private OverlayManager overlayManager = new OverlayManager(this);
	boolean iAmRC = false;
	IObjectController controller;
	//private Queue<NetworkObject> dataToBroadcast = new ConcurrentLinkedQueue<NetworkObject>();
	int bRCCount = 0;
	boolean newRCElected = false;

	/**
	 * Start as client
	 * @param target NetworkTarget of the RC to connect to
	 */
	public UpdateGameState(IObjectController _controller, NetworkTarget target) {
		controller = _controller;
		iAmRC = false;
		overlayManager.addEntry(target, 0, new Date());
		man = new ConnectionManager(overlayManager);
		//man.Connect(target);

		NetworkObject networkObject = new NetworkObject();

		networkObject.type = dataType.Init;
		man.Send(target, networkObject);
		new Thread(man).start();
		new Thread(this).start();

	}

	public OverlayManager getOverlayManager(){
		return overlayManager;
	}
	/**
	 * Start as RC
	 * @param port Port to listen at
	 */
	public UpdateGameState(IObjectController _controller, int port) {
		controller = _controller;
		iAmRC = true;
		man = new ConnectionManager(port, overlayManager);
		new Thread(man).start();
		new Thread(this).start();

	}

	public void SendToAllClients(NetworkObject data) {
		for(NetworkTarget target : overlayManager.getClients()) {
			man.Send(target, data);
		}
	}

	public void SendToAllBackupRCs(NetworkObject data) {
		for(NetworkTarget target : overlayManager.getBackupRCs()) {
			man.Send(target, data);
		}
	}

	public void SendToOneClient(NetworkObject no, NetworkTarget nt){
		man.Send(nt, no);
	}

	public void SendToRC(NetworkObject data) {
		man.Send(overlayManager.getRC(), data);
	}



	public void SendToOtherRc(NetworkObject data) {

	}
	public void AddNewClient(NetworkTarget target) {
		overlayManager.addEntry(target, 1, new Date());
	}



	@Override
	public void run() {

		NetworkObject tmpNo;
		while(true) {

			//check for new received messages
			for(NetworkObject no: man.getNewData()){

				//TODO: add timestamp for no.sender

				switch(no.type) {
				case Init:
					if(iAmRC) {
						//Add new Tank
						controller.addTankRandom(no.target, true);

						//Add Tank for the existing clients
						tmpNo = new NetworkObject();
						tmpNo.type = dataType.AddTank;
						tmpNo.angle = controller.getTank(no.target).getAngle();
						tmpNo.point = controller.getTank(no.target).getPos();
						tmpNo.dataTarget = no.target;

						SendToAllClients(tmpNo);
						bRCCount++;

						//Send Init to the new client
						tmpNo = new NetworkObject();
						tmpNo.type = dataType.Init;
						tmpNo.tankData = controller.exportTankInfo(); 
						tmpNo.powerUpData = controller.exportPowerUpMap();
						tmpNo.missileData = controller.exportMissileInfo();
						tmpNo.regions = controller.getRegionTypes();
						tmpNo.dataTarget = no.target;
						man.Send(no.target, tmpNo);
						if(bRCCount<3)
							overlayManager.addEntry(no.target,2,new Date());
						overlayManager.addEntry(no.target,1,new Date());

					} else {
						//controller.setMe(no.dataTarget);
						controller.setMe(no.reciver);
						System.out.println("New me: "+ no.reciver.PORT);
						controller.setNewRegionTypes(no.regions);
						controller.importTankInfo(no.tankData);
						controller.importPowerUpMap(no.powerUpData);
						controller.importMissileInfo(no.missileData);

					}

					break;
				case MoveTank:
					if(iAmRC) {

						//TODO: pass information up

					} else {
						controller.forceMoveTank(no.move.getNetworkTarget(), no.move.getAngle(), no.move.getLocation());						
					}

					break;
				case MoveRequest:
					if(iAmRC) {
						controller.moveTank(no.move.getNetworkTarget(), no.move.getAngle());
					}

					break;
				case Shoot:
					if(iAmRC) {

						//TODO: pass information up
					} else {

						//controller.MoveMissile(no.dataTarget, no.missile);
					}
					break;

				case AddPowerUp:
					if(iAmRC) {

					} else {	
						//System.out.println("Empfangen!");
						controller.addPowerUp(no.powerUp);
					}
					break;

				case RemovePowerUp:
					if(iAmRC) {

					} else {			
						controller.removePowerUp(no.point);
					}
					break;

				case AddMissile:
					if(iAmRC) {

					}else {
						controller.forceAddMissile(no.dataTarget, no.point, no.angle, no.range);
					}
					break;	

				case AddMissileRequest:
					if(iAmRC) {
						controller.addMissile(no.dataTarget, no.point, no.angle, no.range);
					}

					break;

				case AddTank:
					if(iAmRC) {

					} else {
						controller.addTank(no.dataTarget, no.point, no.angle);
					}

					break;
				case RotateTank:
					if(iAmRC) {

					} else {
						controller.rotateTank(no.dataTarget, no.angle);
					}
					break;

				case Ping:
					//Answer Ping with Pong
					NetworkObject toSend = new NetworkObject();
					toSend.type = dataType.Ping;
					man.Send(no.target, toSend );

					break;
				case Pong:
					break;
					
				case ExitRequest:
					if(iAmRC) {
						controller.getExitGameRequest(no.dataTarget);						
						//TODO remove from Network
					} else { 
						controller.exitGamePermission();
					}
					break;
				case ExitPermission:
					if(iAmRC) {
						//TODO : Send information to backup RC
						controller.exitGamePermission(no.dataTarget);
						List <NetworkTarget> bRCList = overlayManager.getBackupRCs();
						for(NetworkTarget bRC : bRCList) {
							NetworkObject toSendRC = new NetworkObject();
							toSendRC.type = dataType.NewRCPing;
							man.Send(bRC, toSendRC);
						}
						
					} else {
						controller.exitGamePermission();
					}
					break;

				case NewRCPing:	
					//Answer Ping with Pong
					NetworkObject tempSend = new NetworkObject();
					tempSend.type = dataType.NewRCPong;
					tempSend.dataTarget = controller.getMe();
					//Assuming dataTarget has RC info
					//man.Send(no.dataTarget, tempSend);
					man.Send(no.target, tempSend);
					break;

				case NewRCPong:
					if(!newRCElected) {
						NetworkObject toSendNewRC = new NetworkObject();
						toSendNewRC.type = dataType.BeNewRC;
						toSendNewRC.dataTarget = controller.getMe();
						toSendNewRC.listOfClients = overlayManager.getClients();
						//man.Send(no.dataTarget, toSendNewRC);
						man.Send(no.target, toSendNewRC);
						newRCElected = true;
					}					
					break;

				case BeNewRC:
					if(iAmRC) {

					} else {

						//overlayManager.setType(no.dataTarget, 0);
						iAmRC = true;
						for(NetworkTarget nt : no.listOfClients) {
							overlayManager.addEntry(nt, 1, new Date());
						}
						NetworkObject tempNo = new NetworkObject();
						tempNo.type = dataType.NewRC;
						no.dataTarget = controller.getMe();
						SendToAllClients(tempNo);
						tempNo = new NetworkObject();
						tempNo.type = dataType.Kill;
						//man.Send(no.dataTarget, tempNo);
						man.Send(no.target, tempNo);
					}

					break;

				case NewRC:
					if(iAmRC) {

					} 
					else {
						overlayManager.deleteEntry(overlayManager.getRC());
						//overlayManager.addEntry(no.dataTarget, 0, new Date());
						overlayManager.addEntry(no.target, 0, new Date());
					}
					break;

				case Kill:
					System.exit(0);
					break;

				
				default:
					break;
				}
			}


			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {

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
	 * @param object 
	 *      NetworkObject with data required to be send to all
	 */
	/*

	private void sendUpdatesToALL( NetworkObject object) {

		man.sendToAll(object);
	}
	 */

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
		networkObject.type = dataType.MoveTank;
		networkObject.tank = controller.getTank(target);
		connection.Send(target, networkObject);
		networkObject.type = dataType.Shoot;
		//networkObject.missile = 
		//connection.Send(target, networkObject);
	}

}
