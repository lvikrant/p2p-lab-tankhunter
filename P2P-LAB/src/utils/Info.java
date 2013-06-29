package utils;

public class Info {
	private int type;
	private int timeStamp;
	private int myRC;
	
	public Info(int type2, int timeStamp2, int myRC2) {
		this.type = type2;
		this.timeStamp = timeStamp2;
		this.myRC = myRC2;
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
	public int getMyRC() {
		return myRC;
	}
	public void setMyRC(int myRC) {
		this.myRC = myRC;
	}

}
