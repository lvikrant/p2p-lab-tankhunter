package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;


import model.NetworkObject;
import model.NetworkTarget;

import model.NetworkObject.dataType;

public class ConnectionHandler extends Thread {

	ConnectionManager conManger;
	Socket baseSocket;
	public String targetIP = null;
	public int targetOpenPort = -1;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	AtomicBoolean running = new AtomicBoolean();

	private Queue<NetworkObject> recived = new ConcurrentLinkedQueue<NetworkObject>();
	private Queue<NetworkObject> toSend = new ConcurrentLinkedQueue<NetworkObject>();
	public ConnectionHandler(Socket s, ConnectionManager manager) {
		conManger = manager;
		running.set(true);
		baseSocket = s;
		targetIP = s.getInetAddress().getHostAddress();
		

	}

	private void init()
	{
		try {
			out = new ObjectOutputStream( baseSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream( baseSocket.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(NetworkObject data)
	{
		
		System.out.println("Send was called");
		toSend.add(data);
		//new Thread(this).start();
	}


	public NetworkObject getNewObject()
	{

		synchronized (recived) {
			return recived.poll();
		}

	}

	public void StopAndClose()
	{
		running.set(false);
	}



	public void run()
	{
		NetworkObject data;

		System.out.println("Starting ConnectionHandler");
		while(running.get())
		{
			//System.out.println("Hello3");
			//System.out.println(baseSocket + "\n"+targetIP);
			try
			{

				while(baseSocket.getInputStream().available() > 0)
				{
					if(in == null)
						init();
					
					//System.out.println("Hello4");
					data = (NetworkObject)(in.readObject());
					data.dataTarget.IP = baseSocket.getInetAddress().getHostAddress();
					targetOpenPort = data.openPort;
							
					
					synchronized (recived) {
						System.out.println("Recieved: " + data.type);
						recived.add(data);
					}
					
					
					if(data.type == dataType.Ping)
					{
						NetworkObject blub = new NetworkObject();
						blub.type = dataType.Pong;
						synchronized (toSend) {
							toSend.add(blub);
						}
						
					}

				}
				synchronized (toSend) {
					while(!toSend.isEmpty())
					{
						if(out == null)
							init();
						
						data = toSend.poll();
						System.out.println("Send: " + data.type);
						//data.from = baseSocket.getInetAddress().getHostAddress();
						data.dataTarget = new NetworkTarget("", conManger.myServerSocket.getLocalPort());
						out.writeObject(data);
						out.flush();

					}
				}
				


				Thread.sleep(100);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		try {
			in.close();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}



	}



}
