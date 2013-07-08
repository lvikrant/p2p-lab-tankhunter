package utils;

import java.util.Date;
/*Types:
 * 0: RC
 * 1: Peer
 * 2: Backup RC1
 * 3. Backup RC2
 * 4. Backup RC3
 * */

public class Pair {
	
	private int type;
	Date date= new java.util.Date();
	Date timeStamp = new Date();
	
	public Pair(int type, Date timeStamp){
		this.type = type;
		this.timeStamp = timeStamp;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
