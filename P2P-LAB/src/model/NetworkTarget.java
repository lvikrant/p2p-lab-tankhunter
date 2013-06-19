package model;

import java.io.Serializable;

public class NetworkTarget implements Serializable {

	public String IP;
	public int PORT;
	public String NAME;
        public boolean isRC;
	
	public NetworkTarget(String ip,int port, String name){
		IP = ip;
		PORT = port;
		NAME = name;
	}
	public NetworkTarget(String ip,int port){
		IP = ip;
		PORT = port;
		NAME = "no name";
	}
	
	public String getIP(){
		return IP;
	}
	
	public int getPort(){
		return PORT;
	}
	
	public String getName(){
		return NAME;
	}
}
