package view;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class TankLabel {
	
	private JLabel label;
	private int posX; 
	private int posY; 
	private int angle = 0;
	private boolean dead = false;
	private Timer destroyTimer;
	private int onTheWay = 16;
	private Timer moveTimer;
	
	public TankLabel(Point point,GamePanel gp) {
		posX = (int)point.getX()*32;
		posY = (int)point.getY()*32;
		label = new JLabel();
		label.setBounds(posX, posY, 32, 32);	
		moveTimer = new Timer(10,gp);
		
		destroyTimer = new Timer(600, gp);
		destroyTimer.setInitialDelay(100);
	}

	public JLabel getLabel(){
		return label;
	}
	
	public void setIcon(ImageIcon icon) {
		label.setIcon(icon);
	}
	
	public void setAngle(int a){
		angle = a;
	}
	
	public int getAngle() {
		return angle;
	}
	
	public int getWay() {
		return onTheWay;
	}

	public void setWay(int way) {
		onTheWay = way;
	}
	
	public Timer getMoveTimer() {
		return moveTimer;
	}
	
	public void movePosX(int delay){
		posX = posX+delay;
		label.setLocation(posX, posY);
	}
	
	public void movePosY(int delay){
		posY = posY+delay;
		label.setLocation(posX, posY);
	}
	
	public void decWay(){
		onTheWay--;
	}
	
	public Timer getDestroyTimer() {
		return destroyTimer;
	}
	
	public boolean isDead(){
		return dead;
	}

	public void setDead(boolean d){
		dead = d;
	}
}
