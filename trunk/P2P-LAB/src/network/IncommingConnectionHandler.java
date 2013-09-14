package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class IncommingConnectionHandler extends Thread{

	public List<ConnectionHandler> handlers;
	public ServerSocket myServerSocket;
	public int myStartPort;
	public ConnectionManager myMan;
	
	/**
	 * Constructor to set the ConnectionHandler object, Server port, 
	 * peers own port and ConnectionManager 
	 * @param _handlers list of ConnectionHandler
	 * @param _myServerSocket Server port number
	 * @param _myStartPort peers port number
	 * @param _myMan ConnectionManager class instance
	 */
	public IncommingConnectionHandler(List<ConnectionHandler> _handlers, ServerSocket _myServerSocket, int _myStartPort, ConnectionManager _myMan)
	{
		handlers = _handlers;
		myServerSocket = _myServerSocket;
		myStartPort = _myStartPort;
		myMan = _myMan;
	}
	
	public void run()
	{
		ConnectionHandler handler;

		try {
			

			while(true)
			{
				Socket newSocket = myServerSocket.accept();
				handler = new ConnectionHandler(newSocket, myMan);
				synchronized (handlers) {
					handlers.add(handler);
				}
				new Thread(handler).start();

				Thread.sleep(10);
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
