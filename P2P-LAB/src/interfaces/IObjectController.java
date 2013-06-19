package interfaces;

import java.awt.Point;
import java.util.Map;

import model.NetworkTarget;
import model.PowerUp;
import model.Tank;

public interface IObjectController {

    /**
     * POWER_UP_CONTROLLER ******************
     */
    public void addPowerUp(Point point);

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

    public void addTankRandom(NetworkTarget nt);

    public void destroy(NetworkTarget nt);

    public Tank getTank(NetworkTarget nt);

    public void removeTank(NetworkTarget nt);

    public void importTankMap(Map<NetworkTarget, Tank> map);

    public Map<NetworkTarget, Tank> exportTankMap();

    public boolean contains(NetworkTarget nt);

    public boolean contains(Point point);

    /**
     * MISSILE_CONTROLLER *******************
     */
    /**
     * MAP_ELEMENTS *************************
     */
    public void setElements(int mapID);

    public String getFieldInfo(Point point);
}
