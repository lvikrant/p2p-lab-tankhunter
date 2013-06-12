package model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import controller.GameController;

import view.GameRegion;

public class Tank implements ActionListener{
	
    private int tankAngle;        // tank angle {0,90,180,270}
	private int posX = 0; // tank x-coordinate Position in fields {0..40}
	private int posY = 0; // tank y-coordinate Position in fields {0..25}
	
	private int movementSpeed = 300;         // tank speed (field to field)
	private int kills = 0;         // number of kills
	private int range = 5;        // attack range of the tank
	private int attackRate = 2000; // attack rate of the tank
	private Timer attackDelayTimer;           // Timer for the shooting delay
	private Timer moveDelayTimer; 
	private boolean readyToShoot = true;  // tank is ready to move to the next field only if ready is set to true
	private boolean readyToMove = true;
	

	private int MAP_HEIGHT;
	private int MAP_WIDTH;
	

	
	private GameController gc;       // Game Controller
	
	private final NetworkTarget NT;    // tank ID
	
    private GameRegion mainRegion;
	
	
	
	public Tank(GameController parGC,NetworkTarget nt, Point pos, int angle){
		System.out.println("TANK ID : " + 0);
		posX = (int)pos.getX();
		posY = (int)pos.getY();
		
		
		NT = nt;
		gc = parGC;
		mainRegion = gc.getMainRegion();
		tankAngle = 0;
	    
		attackDelayTimer = new Timer(10,this);
		attackDelayTimer.setInitialDelay(attackRate);
		
		moveDelayTimer = new Timer(10,this);	        
		moveDelayTimer.setInitialDelay(movementSpeed);
		
		MAP_HEIGHT = gc.getMapHeight();
		MAP_WIDTH = gc.getMapWidth();
		

	}
	

	public int getPosX(){
		return posX;
	}

	public int getPosY(){
		return posY;
	}


	public void showPosition(){
		System.out.println(posX + "  %% " + posY);
	}
	
    public void moveDown(){
       if(readyToMove && posY < MAP_HEIGHT-1){
    	   readyToMove = false;
    	   moveDelayTimer.start();
   	       tankAngle = 270;
   	       mainRegion.rotateTank(NT,270);
   		   String nextField = gc.getFieldInfo(posX,posY+1);
   		   
   	 	   if(nextField != "ROCK" && nextField != "TANK"){
   	    	   if(nextField == "POWERUP"){
   	    	    	  gc.removePowerUp(posX, posY+1);
   	    	      }
   	              posY++;
   	              mainRegion.moveTank(NT,270);
   	       }
       }
    }
    
    public void moveUp(){
    	if(readyToMove && posY > 0 ){
    		readyToMove = false;
    		moveDelayTimer.start();
    		tankAngle = 90;
    		mainRegion.rotateTank(NT,90);
    		String nextField = gc.getFieldInfo(posX,posY-1);
    		
    		if(nextField != "ROCK" && nextField != "TANK"){
    			   if(nextField == "POWERUP"){
   	    	    	   gc.removePowerUp(posX, posY-1);
   	    	       }
    			   mainRegion.moveTank(NT,90);
	               posY--;
    		   }
    	}
    }
    
    public void moveLeft(){
    	if(readyToMove && posX > 0){
    	    readyToMove = false;
    	    moveDelayTimer.start();
   	        tankAngle = 180;
   	        mainRegion.rotateTank(NT,180);
        	String nextField = gc.getFieldInfo(posX-1,posY);
   	 
        	if(nextField != "ROCK" && nextField != "TANK"){
        		   	if(nextField == "POWERUP"){
        		   		gc.removePowerUp(posX-1, posY);
        		   	} 
        		   	posX--;
        		   	mainRegion.moveTank(NT,180);
        	   }
    	}
    }
    
    public void moveRight(){
    	if(readyToMove){
    		if(posX < MAP_WIDTH-1){
    		
    			readyToMove = false;
    			moveDelayTimer.start();
    			tankAngle = 0;
    			mainRegion.rotateTank(NT,0);
    			String nextField = gc.getFieldInfo(posX+1,posY);
    		
    			if(nextField != "ROCK" && nextField != "TANK"){
    				if(nextField == "POWERUP"){
    					gc.removePowerUp(posX+1, posY);
   	    	   		}
    				posX++;
    				mainRegion.moveTank(NT,0);
    			}
    		} else {
    				gc.moveToRegion(posX,posY,0);
    		}
    	}
    }
    
    
    
    public Point getPos(){
    	return new Point(posX,posY);
    }

    
    
    
    public void setTankAngle(int parAngle){
     	tankAngle = parAngle;
     }
    
    public int getSpeed(){
    	return movementSpeed;
    }
    
    
    public void fire(){
    	if(readyToShoot){
        new Missile(NT,tankAngle,range,posX,posY,gc);
        readyToShoot = false;
        readyToMove = false;
        moveDelayTimer.start();
        attackDelayTimer.start();
    	}
    }

    public void setPosition(Point parPoint){
    	posX = (int)parPoint.getX();
    	posY = (int)parPoint.getY();
    }
    
    public int getTankGridPosX(){
    	return posX;
    }
    
    public int getTankGridPosY(){
    	return posY;
    }
    

    public void destroy(){
    	attackDelayTimer.stop();
    	moveDelayTimer.stop();
    	readyToShoot = false;
    	readyToMove = false;
    	mainRegion.destroyTank(NT);
    	
    
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == attackDelayTimer) {
			readyToShoot = true;
			attackDelayTimer.stop();
		}	
		
		if (e.getSource() == moveDelayTimer) {
			readyToMove = true;
			moveDelayTimer.stop();
		}	
	}
}
