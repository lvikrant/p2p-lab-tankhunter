package interfaces;

public interface InformationVisualisation {
	
	/**
	 * shows the current game time on the MenuBar
	 * 
	 * @param time game time as String
	 */
	void showGameTime(int time);
	
	/**
	 * shows the current bonus on the MenuBar
	 * 
	 * @param bonus current bonus as String
	 */
	void showBonus(String bonus);
	
	/**
	 * shows the bonus time left on the MenuBar
	 * 
	 * @param time bonus time left as String
	 */
	void showBonusTime(int time);
	
	/**
	 * shows player attack range on the MenuBar
	 * 
	 * @param range
	 */
	void showAttackRange(int range);
	
	/**
	 * shows player movement speed on the MenuBar
	 * 
	 * @param speed
	 */
	void showMovementSpeed(int speed);
	
	/**
	 * shows player attack rate on the MenuBar
	 * 
	 * @param rate
	 */
	void showAttackRate(int rate);
	
	/**
	 * shows player current kills on the MenuBar
	 * 
	 * @param kills
	 */
	void showKills(int kills);

	/**
	 * show player current region on the MenuBar
	 * 
	 * @param posX
	 * @param posY
	 */
	void showRegion(int posX, int posY);

}
