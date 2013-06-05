package p2p.sockets;

import java.net.Socket;
import java.util.LinkedList;

import p2p.sockets.NetworkObject.dataType;

public class Test_Base {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ConnectionManager server = new ConnectionManager(8080);
		new Thread(server).start();

		LinkedList<ConnectionManager> clients = new LinkedList<ConnectionManager>();

		for(int i = 0; i < 3; i++)
		{
			clients.add(new ConnectionManager());

		}

		for(ConnectionManager m : clients)
		{
			new Thread(m).start();
			m.Connect("localhost", 8080);
		}



		int globalCounter = 0;

		NetworkObject toSend;

		while(true)
		{
			/*
			if(globalCounter  == 0)
			{
				toSend = new NetworkObject();
				toSend.type = dataType.Ping;
				toSend.bla = "Startup " + globalCounter;
				server.sendToAll(toSend);
			}
			*/
			
			
			if(globalCounter % 10 == 0)
			{
				toSend = new NetworkObject();
				toSend.type = dataType.Ping;
				toSend.bla = "Ping?" + globalCounter;
				server.sendToAll(toSend);

				
				
			}
			
			if(globalCounter == 15)
			{
				for(NetworkTarget t : server.getAllTargets())
				{
					NetworkObject toSend2 = new NetworkObject();
					toSend2.type = dataType.ConenctTo;
					toSend2.target = t.host;
					toSend2.targetPort = t.port;
					server.sendToAll(toSend2);
				}
				
				/*
				for(ConnectionManager m : clients)
				{
					for(NetworkTarget t : server.getAllTargets())
					{
						m.Connect(t);
					}
				}
				*/
				
			}
			
			/*
			if(globalCounter == 15)
			{
				toSend = new NetworkObject();
				toSend.bla = "SPAM0" + globalCounter;
				server.sendToAll(toSend);
				toSend = new NetworkObject();
				toSend.bla = "SPAM1" + globalCounter;
				server.sendToAll(toSend);
				toSend = new NetworkObject();
				toSend.bla = "SPAM2" + globalCounter;
				server.sendToAll(toSend);
				toSend = new NetworkObject();
				toSend.bla = "SPAM3" + globalCounter;
				server.sendToAll(toSend);
			}
			*/
			try {


				for(ConnectionManager m : clients)
				{
					System.out.print(m.showAllConnections());
					for(NetworkObject n : m.getNewData())
					{
						System.out.println(n.toString());
						
					}
				}
				
				for(NetworkObject n : server.getNewData())
				{
					System.out.println(n.toString());
					NetworkObject connectTo = new NetworkObject();
					connectTo.type = dataType.ConenctTo;
					connectTo.target = n.from;
					connectTo.targetPort = n.openPort;
					server.sendToAll(connectTo);
					
					
				}



				System.out.println("Run\" " + globalCounter + "\" done, Sleeping 1sec");



				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			globalCounter++;
		}

	}
}
