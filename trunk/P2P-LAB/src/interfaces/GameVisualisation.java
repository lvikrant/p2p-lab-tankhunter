package interfaces;

import java.awt.Point;


public interface GameVisualisation {
	
//-------TANK-----------------------------------------------------------------
	  /** 
	   * show tank with ID = tankID on field position (x,y) and tank direction = angle
	   * 
	   * @param tankID ID of the tank
	   * @param posX x-coordinate position in fields
	   * @param posY y-coordinate position in fields
	   * @param angle the direction of the tank {0,90,180,270}
	   */
	void addTank(int tankID, int posX, int posY, int angle);
	
	   /** 
	    *  rotate tank with ID = tankID in direction = angle
	    * 
	    * @param tankID ID of the tank
	    * @param angle the direction of the tank {0,90,180,270}
	    */
	void rotateTank(int tankID, int angle);
	
	   /** 
	    *  move tank with ID = tankID in direction = angle
	    * 
	    * @param tankID ID of the tank
	    * @param angle the direction of the tank {0,90,180,270}
	    */
	void moveTank(int tankID, int angle);
	 
	   /**
	    * remove tank with ID = tankID
	    *  
	    * @param tankID ID of the tank
	    */
	void removeTank(int tankID);
	
	   /**
	    * Method destroyTank starts the animation of tank distraction.  
	    *  
	    * @param tankID ID of the tank
	    */
	void destroyTank(int tankID);
	

		//-------MISSILE--------------------------------------------------------------
	
	  /** 
	   * show missile with ID = missileID on field position (x,y) and missile direction = angle
	   * 
	   * @param missileID ID of the missile
	   * @param posX x-coordinate position in fields
	   * @param posY y-coordinate position in fields
	   * @param angle the direction of the missile {0,90,180,270}
	   */
    void addMissile(int missileID,int posX, int posY, int angle);
    
    	   /** 
	    *  move missile with ID = missileID in direction = angle
	    * 
	    * @param missileID ID of the missile
	    * @param angle the direction of the missile {0,90,180,270}
	    */
    void moveMissile(int missileID);
    
	   /**
	    * remove missile with ID = missileID
	    *  
	    * @param tankID ID of the tank
	    */
    void removeMissile(int missileID);
    
	   /**
	    * Method explodeMissile starts the animation of missile explosin.  
	    *  
	    * @param tankID ID of the tank
	    */
    void explodeMissile(int missileID);

    
//-------POWERUP--------------------------------------------------------------

	   /** 
	    * show powerUp with ID = powerUpID on field position (x,y) and picture = parPowerUp
	    * 
	    * @param powerUpID number of the powerUp
	    * @param parPowerUp name of the powerUp as String
	    * @param posX x-coordinate position in fields
	    * @param posY y-coordinate position in fields
	    */
    void addPowerUp(Point point, String parPowerUp);
    
	   /** 
	    * remove powerUp with ID = powerUpID
	    * 
	    * @param powerUpID the ID of the powerUp
	    */
    void remove(Point point);
    
}

