package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import overlay.OverlayManager;

import model.NetworkObject;
import model.NetworkTarget;
import model.NetworkObject.dataType;


public class ConnectionManager extends Thread{


	//public List<Socket> socketList = Collections.synchronizedList(new LinkedList<Socket>());
	//public ConnectionHandler handler = new ConnectionHandler(this);
	//public LinkedList<LinkedList<String>> recivedItems = new LinkedList<LinkedList<String>>(); 

	public List<ConnectionHandler> handlers = Collections.synchronizedList(new LinkedList<ConnectionHandler>());
	private Queue<NetworkObject> recived = new ConcurrentLinkedQueue<NetworkObject>();

	//public Hashtable<NetworkTarget, ConnectionHandler> handlers = new Hashtable<NetworkTarget, ConnectionHandler>();

	//public Hashtable<Socket,  List<String>> recivedItems = new Hashtable<Socket, List<String>>();


	int myStartPort = 0;

	public ServerSocket myServerSocket = null;
	OverlayManager overlay = null;

	/**
	 * Constructor to set instance of OverlayManager class
	 * @param overlay instance of OverlayManager Class
	 */
	public ConnectionManager(OverlayManager overlay)
	{
		this.overlay = overlay;
		init();
	}

	/**
	 * Constructor to set instance of OverlayManager class and port
	 * @param port the port number
	 * @param overlay instance of OverlayManager Class
	 */
	public ConnectionManager(int port, OverlayManager overlay)
	{
		myStartPort = port;
		this.overlay = overlay;
		init();
	}
	
	/**
	 * Initializes the IncomingConnectionHandler and sets the server socket 
	 */
	private void init()
	{
		try {
			myServerSocket = new ServerSocket(myStartPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IncommingConnectionHandler inHandler = new IncommingConnectionHandler(handlers, myServerSocket, myStartPort, this);
		new Thread(inHandler).start();
	}

	/**
	 * Establishes connection to a Network target
	 * @param target contains the port number and IP address of the target
	 */
	private void Connect(NetworkTarget target)
	{
		Connect(target.IP, target.PORT);
	}

	/**
	 * Sends the data to a specified target
	 * @param target IP address and port of the target peer
	 * @param data serialized data to be send across
	 * @return true/false if success/failure
	 */
	public boolean Send(NetworkTarget target, NetworkObject data)
	{
		synchronized (handlers) {
			ConnectionHandler h = getHandlerByTarget(target);
			if(h == null)
			{
				try {
					h = new ConnectionHandler(new Socket(target.IP, target.PORT), this);
					h.targetOpenPort = target.PORT;
					new Thread(h).start();
				}
				catch(Exception e) {
					System.out.println(e);
				}
				handlers.add(h);
				System.out.println("isEmpty"+handlers.isEmpty());
				h.send(data);
				return true;
			}
			else
			{
				h.send(data);
				return true;
			}	
		}
	}

	/**
	 * Method to retrieve the connectionHandler object for a peer
	 * @param target address and port number of the target peer
	 * @return ConnectionHandler instance of target peer
	 */
	private ConnectionHandler getHandlerByTarget(NetworkTarget target)
	{
		for(ConnectionHandler h : handlers)
		{
			if(h.targetIP.equals(target.IP))
			{
				if(h.targetOpenPort == target.PORT)
				{
					return h;
				}
			}
		}

		return null;
	}
	
	/**
	 * Method to return the peers IP address 
	 * @return NetworkTarget object with port number and IP address
	 */
	public NetworkTarget getMe()
	{
		return new NetworkTarget(myServerSocket.getInetAddress().toString(), myServerSocket.getLocalPort());
	}

	/**
	 * Method to establish connection and save the handler to the peer
	 * @param host IP address of the target peer
	 * @param port port number of the target peer
	 */
	private void Connect(String host, int port)
	{
		ConnectionHandler handler;
		if(getHandlerByTarget(new NetworkTarget(host,port)) == null)
				return;

		try
		{


			Enumeration e=NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements())
			{
				NetworkInterface n=(NetworkInterface) e.nextElement();
				Enumeration ee = n.getInetAddresses();
				while(ee.hasMoreElements())
				{
					InetAddress i= (InetAddress) ee.nextElement();
					if(i.getHostAddress().equals(host))
					{	
						if(port == myServerSocket.getLocalPort())
							return;
					}
				}
			}

			synchronized (handlers) {
				for(ConnectionHandler h : handlers)
				{
					if(h.targetIP.equals(host))
					{
						if(h.targetOpenPort == port || h.baseSocket.getPort() == port)
							return;
					}
				}
			}

			Socket newConnection = new Socket(host, port);
			handler = new ConnectionHandler(newConnection, this);
			handler.targetOpenPort = port;

			synchronized (handlers) {
				handlers.add(handler);

			}
			new Thread(handler).start();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Method to send data to all the connected peers
	 * @param data data to be send
	 */
	private void sendToAll(NetworkObject data)
	{
		synchronized (handlers) {
			for(ConnectionHandler h : handlers)
			{
				h.send(data);
			}
		}

	}

	private List<NetworkObject> getLowerData()
	{
		LinkedList<NetworkObject> toReturn = new LinkedList<NetworkObject>();
		NetworkObject data;

		synchronized (handlers) {
			for(ConnectionHandler h : handlers)
			{
				while(true) 
				{
					data = null;
					data = h.getNewObject();
					if(data == null)
					{
						break;
					}
					else
					{
						toReturn.add(data);
					}

				}
			}
		}
		return toReturn;
	}

	public List<NetworkObject> getNewData()
	{
		LinkedList<NetworkObject> toReturn = new LinkedList<NetworkObject>();
		NetworkObject data;

		synchronized (recived) {
			toReturn.addAll(recived);
			recived.clear();
		}

		return toReturn;
	}
	
	/**
	 * Method which sends a list of all connected peers
	 * @return list of NetworkTarget
	 */
	public List<NetworkTarget> getAllTargets()
	{
		LinkedList<NetworkTarget> toReturn = new LinkedList<NetworkTarget>();
		
		synchronized (handlers) {
			for(ConnectionHandler h : handlers)
			{
				toReturn.add(new NetworkTarget(h.targetIP, h.targetOpenPort));
			}
		}
		
		return toReturn;
	}

	/**
	 * Method to return a string containing all the connections
	 * @return String with all peers information
	 */
	public String showAllConnections()
	{
		String toReturn = "Here are my connections. My local port is: " + myServerSocket.getLocalPort() + "\n";
		synchronized (handlers) 
		{
			for(ConnectionHandler h : handlers)
			{
				toReturn += h.baseSocket.getInetAddress().toString() + ":" + h.baseSocket.getPort() + "\n";

			}
		}
		return toReturn;
	}


	public void run()
	{

		

		
		
		
		while(true)
		{
			for(NetworkObject n : getLowerData())
			{
				synchronized (recived) {
					recived.add(n);
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
}



