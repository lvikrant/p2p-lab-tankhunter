package interfaces;

import java.awt.Point;

import model.NetworkTarget;


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
	 public void addTank(NetworkTarget nt,Point point, int angle);
	
	   /** 
	    *  rotate tank with ID = tankID in direction = angle
	    * 
	    * @param tankID ID of the tank
	    * @param angle the direction of the tank {0,90,180,270}
	    */
	 public void rotateTank(NetworkTarget nt, int angle);
	
	   /** 
	    *  move tank with ID = tankID in direction = angle
	    * 
	    * @param tankID ID of the tank
	    * @param angle the direction of the tank {0,90,180,270}
	    */
	 public void moveTank(NetworkTarget nt, int angle);
	 
	   /**
	    * remove tank with ID = tankID
	    *  
	    * @param tankID ID of the tank
	    */
	 public void removeTank(NetworkTarget nt);
	
	   /**
	    * Method destroyTank starts the animation of tank distraction.  
	    *  
	    * @param tankID ID of the tank
	    */
	 public void destroyTank(NetworkTarget nt);
	

		//-------MISSILE--------------------------------------------------------------
	
	  /** 
	   * show missile with ID = missileID on field position (x,y) and missile direction = angle
	   * 
	   * @param missileID ID of the missile
	   * @param posX x-coordinate position in fields
	   * @param posY y-coordinate position in fields
	   * @param angle the direction of the missile {0,90,180,270}
	   */
	 public void addMissile(NetworkTarget nt,Point point, int angle);
    
    	   /** 
	    *  move missile with ID = missileID in direction = angle
	    * 
	    * @param missileID ID of the missile
	    * @param angle the direction of the missile {0,90,180,270}
	    */
	  public void moveMissile(NetworkTarget nt);
    
	   /**
	    * remove missile with ID = missileID
	    *  
	    * @param tankID ID of the tank
	    */
	  public void removeMissile(NetworkTarget nt);
    
	   /**
	    * Method explodeMissile starts the animation of missile explosin.  
	    *  
	    * @param tankID ID of the tank
	    */
	  public void explodeMissile(NetworkTarget nt);

    
//-------POWERUP--------------------------------------------------------------

	   /** 
	    * show powerUp with ID = powerUpID on field position (x,y) and picture = parPowerUp
	    * 
	    * @param powerUpID number of the powerUp
	    * @param parPowerUp name of the powerUp as String
	    * @param posX x-coordinate position in fields
	    * @param posY y-coordinate position in fields
	    */
    public void addPowerUp(Point point, String parPowerUp);
    
	   /** 
	    * remove powerUp with ID = powerUpID
	    * 
	    * @param powerUpID the ID of the powerUp
	    */
    void remove(Point point);
    
}

