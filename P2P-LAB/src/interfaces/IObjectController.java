package interfaces;

import java.awt.Point;
import java.util.Map;

import controller.GameController;

import model.Missile;
import model.MissileInfo;
import model.NetworkObject;
import model.NetworkTarget;
import model.PowerUp;
import model.Tank;
import model.TankInfo;
import model.NetworkObject.dataType;

public interface IObjectController {

    /**
     * POWER_UP_CONTROLLER ******************
     */
    public void addPowerUp(Point point, String power);
    
    public void addPowerUp(PowerUp powerUp);

    public void addPowerUpRandom();

    public PowerUp getPowerUp(Point point);

    public boolean containsPowerUp(Point point);

    public void removePowerUp(Point point);

    public void removeAllPowerUps();

    public int getPowerUpMapSize();

    public int getPowerUpMapMaxSize();

    public void importPowerUpMap(Map<Point, PowerUp> map);

    public Map<Point, PowerUp> exportPowerUpMap();

    /**
     * TANK_CONTROLLER **********************
     */
    public void addTank(NetworkTarget nt, Point point, int angle);

    public void addTankRandom(NetworkTarget nt, boolean init);

    public void destroy(NetworkTarget nt);

    public Tank getTank(NetworkTarget nt);

    public void removeTank(NetworkTarget nt);

    public void importTankMap(Map<NetworkTarget, Tank> map);

    public Map<NetworkTarget, Tank> exportTankMap();

    public boolean contains(NetworkTarget nt);

    public boolean contains(Point point);
    
    public boolean moveTank(NetworkTarget nt, int angle, Point pos);
    
    public boolean moveTank(NetworkTarget nt, int angle);
    
    public void forceMoveTank(NetworkTarget nt, int angle,Point pos);
    
    public void importTankInfo(Map<NetworkTarget, TankInfo> parmap);
    
    public void rotateTank(NetworkTarget nt, int angle);

    /**
     * MISSILE_CONTROLLER *******************
     */
    
    public void addMissile(NetworkTarget nt, Point pos, int angle, int range);
    
    public void forceAddMissile(NetworkTarget nt, Point pos, int angle, int range);
    
    public void importMissileMap(Map<NetworkTarget, Missile> map);

    public Map<NetworkTarget, Missile> exportMissileMap();
    
    /**
     * MAP_ELEMENTS *************************
     */

    public String getFieldInfo(Point point);
	
	public Map<NetworkTarget, TankInfo> exportTankInfo();

	public void setMe(NetworkTarget networkTarget);

	public NetworkTarget getMe();

	public Map<NetworkTarget, MissileInfo> exportMissileInfo();

	public void importMissileInfo(Map<NetworkTarget, MissileInfo> missileData);

	public void setNewRegionTypes(int[] regionTypes);

	public int[] getRegionTypes();

//	public void sendExitGameRequest(NetworkTarget nt);
	
	public void getExitGameRequest(NetworkTarget nt);
	
	public void getBackupRCAck();
	
	public void exitGamePermission();

	public void exitGamePermission(NetworkTarget dataTarget);
	
	public GameController getGc();

	public void setGc(GameController gc);
}
