package utils;

public class Pair {
	
	private int type;
	private int timeStamp;
	
	public Pair(int type, int timeStamp){
		this.type = type;
		this.timeStamp = timeStamp;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
