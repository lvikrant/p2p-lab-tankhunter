package utils;

import java.sql.Timestamp;
import java.util.Date;
import java.sql.Timestamp;
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
	Timestamp timeStamp = new Timestamp(date.getTime());
	
	public Pair(int type, Timestamp timeStamp){
		this.type = type;
		this.timeStamp = timeStamp;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
