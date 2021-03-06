package model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import controller.GameController;

public class Tank implements ActionListener {

	private int tankAngle; // tank angle {0,90,180,270}
	private int posX = 0; // tank x-coordinate Position in fields {0..40}
	private int posY = 0; // tank y-coordinate Position in fields {0..25}

	private int movementSpeed = 400; // tank speed (field to field)
	private int kills = 0; // number of kills
	private int range = 7; // attack range of the tank
	private int attackRate = 2000; // attack rate of the tank
	private Timer attackDelayTimer; // Timer for the shooting delay
	private Timer moveDelayTimer;
	private boolean readyToShoot = true; // tank is ready to move to the next
											// field only if ready is set to
											// true
	private boolean readyToMove = true;

	private int MAP_HEIGHT;
	private int MAP_WIDTH;

	private GameController gc; // Game Controller

	private final NetworkTarget NT;
	
	private String status = "NONE";
	private int timeLeft = 11;

	public Tank(GameController parGC, NetworkTarget nt, Point pos, int angle) {
		posX = (int) pos.getX();
		posY = (int) pos.getY();

		NT = nt;
		gc = parGC;
		tankAngle = 0;

		attackDelayTimer = new Timer(10, this);
		attackDelayTimer.setInitialDelay(attackRate);

		moveDelayTimer = new Timer(10, this);
		
		moveDelayTimer.setInitialDelay(movementSpeed);

		

		MAP_HEIGHT = gc.getMapHeight();
		MAP_WIDTH = gc.getMapWidth();

	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public boolean moveDown() {
		if (readyToMove) {
			if (posY < MAP_HEIGHT - 1) {
				readyToMove = false;
				moveDelayTimer.start();
				tankAngle = 270;
				gc.getMainRegion().rotateTank(NT, 270);
				String nextField = gc.getFieldInfo(posX, posY + 1);

				if (nextField != "ROCK" && nextField != "TANK") {
					if (nextField == "POWERUP") {
						setStatus(gc.getPowerUp(posX,posY+1));
						gc.manadgePowerUps();
						gc.removePowerUp(posX, posY + 1);
					}
					
					gc.getMainRegion().moveTank(NT, 270);
					posY++;
					return true;
				}
			} else {
			//	gc.moveToNextRegion(new Point(posX, posY), 270);
			}
		}
		return false;
	}

	public boolean moveUp() {
		if (readyToMove) {
			if (posY > 0) {
				readyToMove = false;
				moveDelayTimer.start();
				tankAngle = 90;
				gc.getMainRegion().rotateTank(NT, 90);
				String nextField = gc.getFieldInfo(posX, posY - 1);

				if (nextField != "ROCK" && nextField != "TANK") {
					if (nextField == "POWERUP") {
						setStatus(gc.getPowerUp(posX,posY - 1));
						gc.manadgePowerUps();
						gc.removePowerUp(posX, posY - 1);
					}
					gc.getMainRegion().moveTank(NT, 90);
					posY--;
					return true;
				}
			} else {
			//	gc.moveToNextRegion(new Point(posX, posY), 90);
			}
		}
		return false;
	}

	public boolean moveLeft() {
		if (readyToMove) {
			if (posX > 0) {
				readyToMove = false;
				moveDelayTimer.start();
				tankAngle = 180;
				gc.getMainRegion().rotateTank(NT, 180);
				String nextField = gc.getFieldInfo(posX - 1, posY);

				if (nextField != "ROCK" && nextField != "TANK") {
					if (nextField == "POWERUP") {
						setStatus(gc.getPowerUp(posX-1,posY));
						gc.manadgePowerUps();
						gc.removePowerUp(posX - 1, posY);
					}
					
					gc.getMainRegion().moveTank(NT, 180);
					posX--;
					return true;
				}
			} else {
			//	gc.moveToNextRegion(new Point(posX, posY), 180);
			}
		}
		return false;
	}

	public boolean moveRight() {
		if (readyToMove) {
			if (posX < MAP_WIDTH - 1) {

				readyToMove = false;
				moveDelayTimer.start();
				tankAngle = 0;
				gc.getMainRegion().rotateTank(NT, 0);
				String nextField = gc.getFieldInfo(posX + 1, posY);

				if (nextField != "ROCK" && nextField != "TANK") {
					if (nextField == "POWERUP") {
						setStatus(gc.getPowerUp(posX+1,posY));
						gc.manadgePowerUps();
						gc.removePowerUp(posX + 1, posY);
					}
					
					gc.getMainRegion().moveTank(NT, 0);
					posX++;
					return true;
				}
			} else {
			//	gc.moveToNextRegion(new Point(posX, posY), 0);
			}
		}
		return false;
	}
	
	public boolean checkIfNewRegion(int angle) {
		switch(angle){
		case 0:
			if (posX < MAP_WIDTH - 1)
				return false;
			break; 
		case 90: 
			if (posY > 0) 
				return false;
			break;
		case 180: 
			if (posX > 0) 
				return false;
			break;
		case 270: 
			if (posY < MAP_HEIGHT - 1) 
				return false;
			break;
		}
		return true;
		
	}
	
	public void setStatus(String newStatus){
		status = newStatus;
		
		
		movementSpeed = 350;
		range = 7;
		attackRate = 2000;
		
		if(status.equals("RATE")){
			attackRate = 1000;
		}else if(status.equals("SPEED")){
			movementSpeed = 250;
			
		}else if(status.equals("RANGE")){
			range = 11;
			
		}else if(status.equals("SLOW")){
			movementSpeed = 1000;
			
		}
		
		timeLeft = 11;
		moveDelayTimer.setInitialDelay(movementSpeed);
	}

	public Point getPos() {
		return new Point(posX, posY);
	}

	public void setTankAngle(int parAngle) {
		tankAngle = parAngle;
	}

	public int getSpeed() {
		return movementSpeed;
	}
	
	public int getRange() {
		return range;
	}
	
	public int getRate() {
		return attackRate;
	}

	public void fire() {
		
		if(!gc.isDead(NT)){
			if (readyToShoot) {
				gc.addMissile(NT,new Point(posX,posY), tankAngle, range);
				restart();
			}
		}
		
	}
	
	public void fire(NetworkTarget nt, Point pos, int angle, int range) {
		if (readyToShoot) {
			System.err.println("");
			new Missile(gc, nt, pos, angle,range);
			restart();
		}
	}
	
	public boolean readyToShoot(){
		return readyToShoot;
	}
	
	public void restart(){
		readyToShoot = false;
		readyToMove = false;
		moveDelayTimer.start();
		attackDelayTimer.start();
	}

	public void setPosition(Point parPoint) {
		posX = (int) parPoint.getX();
		posY = (int) parPoint.getY();
	}

	public int getTankGridPosX() {
		return posX;
	}

	public int getTankGridPosY() {
		return posY;
	}
	
	public void decTime(){
		timeLeft--;
	}
	
	public String getStatus(){
		return status;
	}
	
	public int getTimeLeft(){
		return timeLeft;
	}

	public void destroy() {
		attackDelayTimer.stop();
		moveDelayTimer.stop();
		readyToShoot = false;
		readyToMove = false;
		gc.getMainRegion().destroyTank(NT);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == attackDelayTimer) {
			readyToShoot = true;
			System.out.println("ready to shoot!");
			attackDelayTimer.stop();
		}

		if (e.getSource() == moveDelayTimer) {
			readyToMove = true;
			moveDelayTimer.stop();
		}
	}

	public int getAngle() {
		return tankAngle;
	}

	public void setTimeLeft( int time) {
		timeLeft = time;
		
	}

	public void rotate(int angle) {
		tankAngle = angle;
		gc.gameWindow.getMainRegion().rotateTank(NT, angle);
		
	}


}
