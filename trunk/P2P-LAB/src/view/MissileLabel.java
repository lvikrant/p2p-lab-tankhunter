package view;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class MissileLabel {

	private JLabel label;
	
	public JLabel[] missileLabels = new JLabel[60];
	
	private int posX;
	private int posY;
	private final int ANGLE;
	
	private int onTheFly = 16;
	private boolean exploded = false;
	
	
	private Timer moveTimer;
	
	public MissileLabel(Point point,int angle, GameRegion gp) {
		ANGLE = angle;
		posX = (int)point.getX();
		posY = (int)point.getY();
		label = new JLabel();
		label.setBounds(posX, posY, 32, 32);	
		moveTimer = new Timer(10,gp);
	
	}
	
	
	public JLabel getLabel(){
		return label;
	}
	
	public void setIcon(ImageIcon icon) {
		label.setIcon(icon);
	}
	
	
	public int getAngle() {
		return ANGLE;
	}
	
	public int getFly() {
		return onTheFly;
	}

	public void setFly(int fly) {
		onTheFly = fly;
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
	
	public void decFly(){
		onTheFly--;
	}
	

	
	public boolean isExploded(){
		return exploded;
	}

	public void setExploded(boolean exp){
		exploded = exp;
	}
	
	public void setLocation(int x, int y){
		posX = x;
		posY = y;
	}

	public int getX(){
		return posX;
	}
	
	public int getY(){
		return posY;
	}
	
}
