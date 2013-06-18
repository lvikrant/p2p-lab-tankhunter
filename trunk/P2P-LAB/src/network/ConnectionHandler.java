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

import model.NetworkObject.dataType;

public class ConnectionHandler extends Thread {

	ConnectionManager conManger;
	Socket baseSocket;
	public String targetIP = null;
	public int targetOpenPort = -1;
	ObjectInputStream in;
	ObjectOutputStream out;
	AtomicBoolean running = new AtomicBoolean();

	private Queue<NetworkObject> recived = new ConcurrentLinkedQueue<NetworkObject>();
	private Queue<NetworkObject> toSend = new ConcurrentLinkedQueue<NetworkObject>();
	public ConnectionHandler(Socket s, ConnectionManager manager) {
		conManger = manager;
		running.set(true);
		baseSocket = s;
		targetIP = s.getInetAddress().getHostAddress();
		try {
			out = new ObjectOutputStream( s.getOutputStream());
			out.flush();
			in = new ObjectInputStream( s.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void send(NetworkObject data)
	{

		toSend.add(data);

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


		while(running.get())
		{
			try
			{

				while(baseSocket.getInputStream().available() > 0)
				{
					

					
					
					data = (NetworkObject)(in.readObject());
					data.from = baseSocket.getInetAddress().getHostAddress();
					
					
					targetOpenPort = data.openPort;
					
					
					
					synchronized (recived) {
						recived.add(data);
						if(data.type == dataType.Pong)
						{
						 int i = 0;
						}
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
						data = toSend.poll();

						
						//data.from = baseSocket.getInetAddress().getHostAddress();
						data.openPort = conManger.myServerSocket.getLocalPort();
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
