/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

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

    public ConnectionManager registerPeer(NetworkTarget target, int serverPort) {

        NetworkObject networkObject = new NetworkObject();
        networkObject.type = dataType.Init;
        ConnectionManager connectionManager = new ConnectionManager(serverPort);
        new Thread(connectionManager).start();
        connectionManager.Connect(target);
        connectionManager.Send(target, networkObject);
        return connectionManager;
    }

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

    public void receiveUpdatesFromRC(NetworkObject object) {
    }
}
