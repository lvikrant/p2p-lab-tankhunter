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


	public ConnectionManager()
	{

	}

	public ConnectionManager(int port)
	{
		myStartPort = port;
	}

	public void Connect(NetworkTarget target)
	{
		Connect(target.IP, target.PORT);
	}

	public boolean Send(NetworkTarget target, NetworkObject data)
	{
		synchronized (handlers) {
			ConnectionHandler h = getHandlerByTarget(target);
			if(h == null)
			{
				try {
					h = new ConnectionHandler(new Socket(target.IP, target.PORT), this);
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

	private ConnectionHandler getHandlerByTarget(NetworkTarget target)
	{
		for(ConnectionHandler h : handlers)
		{
			if(h.targetIP == target.IP)
			{
				if(h.targetOpenPort == target.PORT)
				{
					return h;
				}
			}
		}

		return null;
	}
	
	public NetworkTarget getMe()
	{
		return new NetworkTarget(myServerSocket.getInetAddress().toString(), myServerSocket.getLocalPort());
	}

	public void Connect(String host, int port)
	{
		ConnectionHandler handler;

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

			synchronized (handlers) {
				handlers.add(handler);

			}
			new Thread(handler).start();
		}
		catch(Exception ex)
		{

		}
	}


	public void sendToAll(NetworkObject data)
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

		try {
			myServerSocket = new ServerSocket(myStartPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IncommingConnectionHandler inHandler = new IncommingConnectionHandler(handlers, myServerSocket, myStartPort, this);
		new Thread(inHandler).start();

		while(true)
		{

			for(NetworkObject n : getLowerData())
			{
				switch (n.type) {
				case ConenctTo:
					Connect(n.target);

					break;
				case Init:
					
					
				case Data:
					System.out.println("Hello9");
					synchronized (recived) {
						recived.add(n);
					}

					break;
				case Ping:
					NetworkObject toSend = new NetworkObject();
					toSend.type = dataType.Ping;
					Send(n.target, toSend );

					break;
				case Pong:

					break;

				default:
					break;
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



