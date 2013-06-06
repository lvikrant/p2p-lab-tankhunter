package model;

public class NetworkTarget {

	private final String IP;
	private final int PORT;
	private final String NAME;
	
	public NetworkTarget(String ip,int port, String name){
		IP = ip;
		PORT = port;
		NAME = name;
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
