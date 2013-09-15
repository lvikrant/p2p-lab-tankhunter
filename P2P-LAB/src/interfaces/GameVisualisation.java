package interfaces;

import java.awt.Point;

import model.NetworkTarget;

public interface GameVisualisation {

	// -------TANK-----------------------------------------------------------------
	/**
	 * show tank with ID = nt on field position (x,y) and tank direction = angle
	 * 
	 * @param nt
	 *            network address of peer
	 * @param angle
	 *            the direction of the tank {0,90,180,270}
	 */
	public void addTank(NetworkTarget nt, Point point, int angle);

	/**
	 * rotate tank with ID = nt in direction = angle
	 * 
	 * @param nt
	 *            network address of peer
	 * @param angle
	 *            the direction of the tank {0,90,180,270}
	 */
	public void rotateTank(NetworkTarget nt, int angle);

	/**
	 * move tank with ID = nt in direction = angle
	 * 
	 * @param nt
	 *            network address of peer
	 * @param angle
	 *            the direction of the tank {0,90,180,270}
	 */
	public void moveTank(NetworkTarget nt, int angle);

	/**
	 * remove tank with ID = nt
	 * 
	 * @param nt
	 *            network address of peer
	 */
	public void removeTank(NetworkTarget nt);

	/**
	 * Method destroyTank starts the animation of tank distraction.
	 * 
	 * @param nt
	 *            network address of peer
	 */
	public void destroyTank(NetworkTarget nt);

	// -------MISSILE--------------------------------------------------------------

	/**
	 * show missile with ID = nt on field point and missile direction = angle
	 * 
	 * @param nt
	 *            network address of peer
	 * @param point
	 *            the cordinates to move to
	 * @param angle
	 *            the direction of the missile {0,90,180,270}
	 */
	public void addMissile(NetworkTarget nt, Point point, int angle);

	/**
	 * move missile with ID = nt
	 * 
	 * @param nt
	 *            network address of peer
	 */
	public void moveMissile(NetworkTarget nt);

	/**
	 * remove missile with ID = nt
	 * 
	 * @param nt
	 *            network address of peer
	 */
	public void removeMissile(NetworkTarget nt);

	/**
	 * Method explodeMissile starts the animation of missile explosion.
	 * 
	 * @param nt
	 *            network address of peer
	 */
	public void explodeMissile(NetworkTarget nt);

	// -------POWERUP--------------------------------------------------------------

	/**
	 * show powerUp at point and picture = parPowerUp
	 * 
	 * @param parPowerUp
	 *            name of the powerUp as String
	 * @param point
	 *            position of powerUp
	 */
	public void addPowerUp(Point point, String parPowerUp);

	/**
	 * remove powerUp at point
	 * 
	 * @param point
	 *            position of powerUp
	 */
	void remove(Point point);

}
