

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConnectionHandler extends Thread{
	
	public List<Socket> ClientList = Collections.synchronizedList(new LinkedList<Socket>());
	public List<Byte> toSend = Collections.synchronizedList(new LinkedList<Byte>());
	public List<Byte> recived = Collections.synchronizedList(new LinkedList<Byte>());

	public void run()
	{
	
		while(true)
		{
			//Sending Outgoing data
			synchronized (ClientList) {
				/*
				
				for(Socket s: ClientList)
				{
					for(byte b : toSend)
					{
		
						try {
							s.getOutputStream().write(b);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
				}
				toSend.clear();
				*/
				
				for(Socket s: ClientList)
				{
					try {
						if(s.getInputStream().available() > 0)
						{
							recived.add((byte)s.getInputStream().read());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			
			
			//Reciveing Incomming data
			
			
		}
	}
	
}
