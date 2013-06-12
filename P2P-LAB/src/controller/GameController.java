package controller;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import model.NetworkTarget;

import view.GameRegion;
import view.GameWindow;

public class GameController implements ActionListener, KeyListener{
	
	private final NetworkTarget ME;	
	private final NetworkTarget ENEMY = new NetworkTarget("255.168.0.42", 8082, "Enemy");
	private final String PLAYER_NAME;

	private final int POWERUP_SPAWNRATE  = 50; // Time till the next PowerUp spawns on the map
	private final int POWERUP_LIMIT = 20;      // The max number of PowerUps that can be on the map
	private final int TANK_LIMIT = 273;         // The max number of Tanks that can be on the map
	private final int MISSILE_LIMIT = 273;      // The max number of Missiles that can be on the map

	private Timer gameTimer;        // Game Timer
	private int gameTime = 0;       // Game Time in Seconds 
	public GameWindow gameWindow;   // Game Window
	
	private Timer respawn;
	
	private final int MAP_WIDTH = 21;      // map width in number of fields
    private final int MAP_HEIGHT = 13;     // map height in number of fields
    

    
    private final ObjectController OBJECT_CONTROLLER;
    

	public GameController(GameWindow parGameWindow,String playerName) {
	
		PLAYER_NAME = playerName;
		ME = new NetworkTarget("255.168.0.42", 8080, PLAYER_NAME);
		OBJECT_CONTROLLER = new ObjectController(this,POWERUP_LIMIT,TANK_LIMIT,MISSILE_LIMIT,1);
		
		gameWindow = parGameWindow;
		
		OBJECT_CONTROLLER.addTankRandom(ME);
		OBJECT_CONTROLLER.addTankRandom(ENEMY);
		
		OBJECT_CONTROLLER.addPowerUpRandom();
		OBJECT_CONTROLLER.addPowerUpRandom();
		OBJECT_CONTROLLER.addPowerUpRandom();
		OBJECT_CONTROLLER.addPowerUpRandom();
		
		

		gameTimer = new Timer(100, this);
		respawn = new Timer(100,this);
		respawn.setInitialDelay(1000);
		
		
		
		/**< LOCAL VERSION ONLY*/
		
		
		
	}
	
	
	

	


	
	//-------TANK--------------------------------------------------------------------------------------

	
	public void destroyTank(NetworkTarget nt){
		 respawn.start();
		 OBJECT_CONTROLLER.destroy(nt);
	}
	
	
	public int getMapWidth(){
		return MAP_WIDTH;
	}
	
	public int getMapHeight(){
		return MAP_HEIGHT;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gameTimer) {
			gameTime = gameTime + 1;
		
			if(gameTime % 10 == 0){
			gameWindow.timeLabel.setText("Time : " + (gameTime / 10) + " Sec     ");
		
			}
			if(gameTime % POWERUP_SPAWNRATE == 0){
				if(OBJECT_CONTROLLER.getPowerUpMapSize()<POWERUP_LIMIT){
				OBJECT_CONTROLLER.addPowerUpRandom();
				}
			}
		}else if (e.getSource() == respawn){
			respawn.stop();
			OBJECT_CONTROLLER.addTankRandom(ENEMY);
		}
		
  
	}
	

	//-------- KEYS------------------------------------------------------------------------

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
	    case KeyEvent.VK_LEFT:  	
	    	OBJECT_CONTROLLER.getTank(ME).moveLeft();
	        break;
	    case KeyEvent.VK_UP:  
	    	OBJECT_CONTROLLER.getTank(ME).moveUp();
	        break;
	    case KeyEvent.VK_DOWN:  
	    	OBJECT_CONTROLLER.getTank(ME).moveDown();
	        break;
	    case KeyEvent.VK_RIGHT: 
	    	OBJECT_CONTROLLER.getTank(ME).moveRight();
	        break;
	    case KeyEvent.VK_SPACE:
	    	OBJECT_CONTROLLER.getTank(ME).fire();
	    	break;
	    }
		
		if(e.getKeyCode() == KeyEvent.VK_F2){
		OBJECT_CONTROLLER.addPowerUpRandom();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	
	
	public void moveToNextRegion(Point pos, int angle){
		OBJECT_CONTROLLER.removeTank(ME);
		OBJECT_CONTROLLER.removeAllPowerUps();
		gameWindow.moveToNextRegion(pos, angle);
		switch(angle){
		case 0 :
			OBJECT_CONTROLLER.addTank(ME, new Point(0,(int)pos.getY()), angle);
			break;
		case 90 :
			OBJECT_CONTROLLER.addTank(ME, new Point((int)pos.getX(),MAP_HEIGHT-1), angle);
			break;
		case 180 :
			OBJECT_CONTROLLER.addTank(ME, new Point(MAP_WIDTH-1,(int)pos.getY()), angle);
			break;
		case 270 :
			OBJECT_CONTROLLER.addTank(ME, new Point((int)pos.getX(),0), angle);
			break;
		}
	
		OBJECT_CONTROLLER.setElements(0);
	}
	

	
	public String getFieldInfo(int posX, int posY){
		return OBJECT_CONTROLLER.getFieldInfo(new Point(posX,posY));
	}
	
	
	public String getFieldInfo(Point point){
		return OBJECT_CONTROLLER.getFieldInfo(point);
	}
	
	
	public void removePowerUp(int posX,int posY){
		OBJECT_CONTROLLER.removePowerUp(new Point(posX, posY));
	}

	public NetworkTarget getTank(Point point) {
		return ENEMY;
	}

	public GameRegion getMainRegion(){
		return gameWindow.getMainRegion();
	}
	
	public void setGamePanelMiddle(Point pos){
		gameWindow.setGamePanelMiddle(pos);
	}
	
	public NetworkTarget getPlayer(){
		return ME;
	}
	
}
