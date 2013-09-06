package interfaces;

import java.awt.Point;
import java.util.Map;
import model.Missile;
import model.MissileInfo;
import model.NetworkTarget;
import model.PowerUp;
import model.Tank;
import model.TankInfo;

public interface IObjectController {

    /*POWER_UP_CONTROLLER*/
	
	/**
	 * Add PowerUp on position "point" and power-type "power".
	 * @param point - position to add the PowerUp
	 * @param power - type of the PowerUp to add 
	 */
    public void addPowerUp(Point point, String power);
    
    /**
     * Add a new PowerUp as object.
     * @param powerUp - PowerUp to add
     */   
    public void addPowerUp(PowerUp powerUp);

    /**
     * Add new PowerUp on a random position with a random power-type.
     */
    public void addPowerUpRandom();

    
    /**
     * Get PowerUp from the position "point".
     * @param point - position of the PowerUp
     * @return PowerUp on position "point" or null if there is no PowerUp on this position
     */
    public PowerUp getPowerUp(Point point);

    /**
     * Check if there is a PowerUp on the position point.
     * @param point - the position to check if there is a PowerUp
     * @return true, if there is a PowerUp on the position "point", false otherwise
     */
    public boolean containsPowerUp(Point point);

    /**
     * Remove PowerUp from the position "point".
     * @param point - the position to remove the PowerUp
     */
    public void removePowerUp(Point point);

    /**
     * Remove all PowerUps from the game.
     */
    public void removeAllPowerUps();

    /**
     * Get the number of PowerUps in the game.
     * @return the number of PowerUps in the game.
     */
    public int getPowerUpMapSize();

    /**
     * Get the max capacity of PoweUps.
     * @return the max capacity of PowerUps
     */
    public int getPowerUpMapMaxSize();
    
    /**
     * Import the new PowerUpMap into the game.
     * @param map - new PowerUpMap to be imported
     */

    public void importPowerUpMap(Map<Point, PowerUp> map);

    /**
     * Export a PowerUpMap from the game.
     * @return PowerUpMap to be exported
     */
     
    public Map<Point, PowerUp> exportPowerUpMap();

    /* TANK_CONTROLLER */
    
    /**
     * Add a Tank to the game.
     * @param nt - NetworkTarget of the Tank to add
     * @param point - the position of the Tank to add
     * @param angle - the direction of the Tank to add
     */
    public void addTank(NetworkTarget nt, Point point, int angle);
    
    /**
     * Add a Tank to the game randomly.
     * @param nt - NetworkTarget of the Tank to add
     * @param init - type of the adding
     */

    public void addTankRandom(NetworkTarget nt, boolean init);

    /**
     * Destroy Tank with NetworkTarget "nt".
     * @param nt - NetworkTarget of the Tank
     */
    public void destroy(NetworkTarget nt);

    /**
     * Get Tank with Network Target "nt".
     * @param nt - NetworkTarget of the Tank to get
     * @return Tank with NetworkTarget "nt" or null if there is no Tank with NT == "nt"
     */
    public Tank getTank(NetworkTarget nt);

    /**
     * Remove Tank from the Game.
     * @param nt - NetwokTarget of the Tank to be removed 
     */
    public void removeTank(NetworkTarget nt);

    /**
     * Import a new TankMap into the game.
     * @param map - TankMap to be imported.
     */
    public void importTankMap(Map<NetworkTarget, Tank> map);

    /**
     * Export TankMap of the game.
     * @return TankMap of the game
     */
    public Map<NetworkTarget, Tank> exportTankMap();

    /**
     * Check if there is a Tank with NetworkTarget "nt" in the game.
     * @param nt - NetworkTarget of the Tank to check
     * @return true, if there is a Tank with NetworkTarget "nt", false otherwise
     */
    public boolean contains(NetworkTarget nt);

    /**
     * Check if there is a Tank on the position "point".
     * @param point - position to check
     * @return true, if there is a Tank on the position "point", false otherwise
     */
    public boolean contains(Point point);
    
    /**
     * Move Tank with NetworkTarget "nt" in direction "angle" and check if it is on the right position "pos".
     * @param nt - NetworkTarget of the Tank to move
     * @param angle - direction to move
     * @param pos - position to check if is is right
     * @return true, if it is possible to move the Tank, false otherwise 
     */
    public boolean moveTank(NetworkTarget nt, int angle, Point pos);
    
    /**
     * Move Tank with NetworkTarget "nt" in direction "angle".
     * @param nt - NetworkTarget of the Tank to move
     * @param angle - direction to move
     * @return true, if it is possible to move the Tank, false otherwise 
     */
    public boolean moveTank(NetworkTarget nt, int angle);
    
    /**
     * Force Tank to move with NetworkTarget "nt" in direction "angle" and check if it is on the right position "pos".
     * @param nt - NetworkTarget of the Tank to move
     * @param angle - direction to move
     * @param pos - position to check if is is right
     */
    public void forceMoveTank(NetworkTarget nt, int angle,Point pos);
    
    /**
     * Import TankInfo of a game.
     * @param parmap - TankInfo to be imported
     */
    public void importTankInfo(Map<NetworkTarget, TankInfo> parmap);
    
    /**
     * Export TankInfo of the game.
     * @return TankInfo of the game.
     */
	public Map<NetworkTarget, TankInfo> exportTankInfo();
    
    /**
     * Rotate Tank with NetworkTarget "nt" in direction "angle".
     * @param nt - NetworkTarget of the Tank to be rotated
     * @param angle - direction of the Tank to be rotated
     */
    public void rotateTank(NetworkTarget nt, int angle);

    /* MISSILE_CONTROLLER */
   
    /**
     * Add a new Missile into the game.
     * @param nt - NetworkTarget of the Missile
     * @param pos - position of the Missile
     * @param angle - the direction of the Missile
     * @param range - the range of the Missile  
     */
    public void addMissile(NetworkTarget nt, Point pos, int angle, int range);
    
    /**
     * Force to add a new Missile into the game.
     * @param nt - NetworkTarget of the Missile
     * @param pos - position of the Missile
     * @param angle - the direction of the Missile
     * @param range - the range of the Missile  
     */
    public void forceAddMissile(NetworkTarget nt, Point pos, int angle, int range);
    
    /**
     * Import a new MissileMap into the game.
     * @param map - new MissileMap to import.
     */
    public void importMissileMap(Map<NetworkTarget, Missile> map);

    /**
     * Export MissileMap.
     * @return MissileMap of the exported game
     */
    public Map<NetworkTarget, Missile> exportMissileMap();
    
    /**
     * Export MissileInfo of the current game.
     * @return MissileInfo of the game
     */
	public Map<NetworkTarget, MissileInfo> exportMissileInfo();
	
	/**
	 * Import MissileInfo of a new game.
	 * @param missileData - MissileInfo to be imported
	 */
	public void importMissileInfo(Map<NetworkTarget, MissileInfo> missileData);
    
    /* MAP_ELEMENTS */

	/**
	 * Get the field-type on the position "point".
	 * @param point - position of the field to get
	 * @return field-type on the position "point"
	 */
    public String getFieldInfo(Point point);
	
    /**
     * Get all 9 RegionTypes of the current game.
     * @return array with all 9 RegionTypes
     */
	public int[] getRegionTypes();
	
	/**
	 * Set all 9 RegionTypes
	 * @param regionTypes - RegionTypes to be set
	 */
	public void setNewRegionTypes(int[] regionTypes);

	/* OVERLAY_ELEMENTS */
	
	public void setMe(NetworkTarget networkTarget);

	public NetworkTarget getMe();

	public void getExitGameRequest(NetworkTarget nt);
		
	public void exitGamePermission();

	public void exitGamePermission(NetworkTarget dataTarget);

	public void becomeRC();
}
