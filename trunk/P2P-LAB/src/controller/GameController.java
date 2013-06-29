package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.swing.Timer;

import overlay.UpdateGameState;
import model.NetworkTarget;
import model.Tank;

import view.GameRegion;
import view.GameWindow;

public class GameController implements ActionListener, KeyListener {

	private final NetworkTarget ME;

	private final int POWERUP_SPAWNRATE = 50; // Time till the next PowerUp
												// spawns on the map
	private final int POWERUP_LIMIT = 20; // The max number of PowerUps that can
											// be on the map
	private final int TANK_LIMIT = 273; // The max number of Tanks that can be
										// on the map
	private final int MISSILE_LIMIT = 273; // The max number of Missiles that
											// can be on the map

	private Timer gameTimer; // Game Timer
	private int gameTime = 0; // Game Time in Seconds
	public GameWindow gameWindow; // Game Window

	private Timer respawn;
	private final int MAP_WIDTH = 21; // map width in number of fields
	private final int MAP_HEIGHT = 13; // map height in number of fields

	private final ObjectController OBJECT_CONTROLLER;

	private boolean regionController;

	private UpdateGameState overlay;

	private NetworkTarget deadPlayer;

	private NetworkTarget server;

	/***** SERVER ****************************************************************************************************/

	public GameController(GameWindow parGameWindow, NetworkTarget server) {
		regionController = true;

		ME = server;

		OBJECT_CONTROLLER = new ObjectController(this, POWERUP_LIMIT,
				TANK_LIMIT, MISSILE_LIMIT, 0);

		gameWindow = parGameWindow;

		gameTimer = new Timer(100, this);
		respawn = new Timer(100, this);
		respawn.setInitialDelay(1000);

		overlay = new UpdateGameState(OBJECT_CONTROLLER, 8080);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		OBJECT_CONTROLLER.addTankRandom(ME);
		OBJECT_CONTROLLER.addPowerUpRandom();
		OBJECT_CONTROLLER.addPowerUpRandom();
		OBJECT_CONTROLLER.addPowerUpRandom();
		OBJECT_CONTROLLER.addPowerUpRandom();
		gameTimer.start();
	}

	/***** CLIENT ****************************************************************************************************/

	public GameController(GameWindow parGameWindow, NetworkTarget ser,
			NetworkTarget client) {
		regionController = false;

		ME = client;
		server = ser;

		OBJECT_CONTROLLER = new ObjectController(this, POWERUP_LIMIT,
				TANK_LIMIT, MISSILE_LIMIT, 0);

		gameWindow = parGameWindow;

		overlay = new UpdateGameState(OBJECT_CONTROLLER, new NetworkTarget(
				"127.0.0.1", 8080));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void destroyTank(NetworkTarget nt) {

		OBJECT_CONTROLLER.destroy(nt);
		deadPlayer = nt;
		respawn.start();
	}

	public int getMapWidth() {
		return MAP_WIDTH;
	}

	public int getMapHeight() {
		return MAP_HEIGHT;
	}
	
	
	public void manadgePowerUps(){
		for (Map.Entry<NetworkTarget, Tank> entry : OBJECT_CONTROLLER.exportTankMap().entrySet()) {
			if (!(entry.getValue().getStatus().equals("NONE"))) {
				entry.getValue().decTime();

				if (entry.getKey().equals(ME)) {
					gameWindow.showBonus(entry.getValue().getStatus());
					gameWindow.showBonusTime(entry.getValue().getTimeLeft());
					gameWindow.showAttackRange(entry.getValue().getRange());
					gameWindow.showAttackRate(entry.getValue().getRate());
					gameWindow.showMovementSpeed(entry.getValue().getSpeed());
					
					
				}

				if (entry.getValue().getTimeLeft() == 0) {
					entry.getValue().setStatus("NONE");

					if (entry.getKey().equals(ME)) {
						gameWindow.showBonus("NONE");
						entry.getValue().setStatus("NONE");
						gameWindow.showAttackRange(entry.getValue().getRange());
						gameWindow.showAttackRate(entry.getValue().getRate());
						gameWindow.showMovementSpeed(entry.getValue().getSpeed());
					}
				}
			}
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gameTimer) {
			gameTime = gameTime + 1;

			if (gameTime % 10 == 0) {
				gameWindow.timeLabel.setText("Time : " + (gameTime / 10) + " Sec     ");

				manadgePowerUps();

			}
			if (gameTime % POWERUP_SPAWNRATE == 0) {
				if (OBJECT_CONTROLLER.getPowerUpMapSize() < POWERUP_LIMIT) {
					OBJECT_CONTROLLER.addPowerUpRandom();
				}
			}
		} else if (e.getSource() == respawn) {
			respawn.stop();
			OBJECT_CONTROLLER.addTankRandom(deadPlayer);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (regionController == true) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				OBJECT_CONTROLLER.moveTank(ME, 180);
				break;
			case KeyEvent.VK_UP:
				OBJECT_CONTROLLER.moveTank(ME, 90);
				break;
			case KeyEvent.VK_DOWN:
				OBJECT_CONTROLLER.moveTank(ME, 270);
				break;
			case KeyEvent.VK_RIGHT:
				OBJECT_CONTROLLER.moveTank(ME, 0);
				break;
			case KeyEvent.VK_SPACE:
				OBJECT_CONTROLLER.getTank(ME).fire();
				break;
			}
		} else {
			// TODO
			System.err
					.println("Get the permission to move or to fire from the region controller");
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				OBJECT_CONTROLLER.moveTank(ME, 180);
				break;
			case KeyEvent.VK_UP:
				OBJECT_CONTROLLER.moveTank(ME, 90);
				break;
			case KeyEvent.VK_DOWN:
				OBJECT_CONTROLLER.moveTank(ME, 270);
				break;
			case KeyEvent.VK_RIGHT:
				OBJECT_CONTROLLER.moveTank(ME, 0);
				break;
			case KeyEvent.VK_SPACE:
				OBJECT_CONTROLLER.getTank(ME).fire();
				break;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_F2) {
			OBJECT_CONTROLLER.addPowerUpRandom();
		}

		if (e.getKeyCode() == KeyEvent.VK_F3) {
			printTankInfo();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_F4) {
			System.out.println(OBJECT_CONTROLLER.getRegionId());
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public void moveToNextRegion(Point pos, int angle) {
		// TODO test if position is free!
		OBJECT_CONTROLLER.removeTank(ME);
		OBJECT_CONTROLLER.removeAllPowerUps();
		OBJECT_CONTROLLER.setElements(0);
		gameWindow.moveToNextRegion(pos, angle);
		switch (angle) {
		case 0:
			OBJECT_CONTROLLER
					.addTank(ME, new Point(0, (int) pos.getY()), angle);
			break;
		case 90:
			OBJECT_CONTROLLER.addTank(ME, new Point((int) pos.getX(),
					MAP_HEIGHT - 1), angle);
			break;
		case 180:
			OBJECT_CONTROLLER.addTank(ME,
					new Point(MAP_WIDTH - 1, (int) pos.getY()), angle);
			break;
		case 270:
			OBJECT_CONTROLLER
					.addTank(ME, new Point((int) pos.getX(), 0), angle);
			break;
		}

	}

	public String getFieldInfo(int posX, int posY) {
		return OBJECT_CONTROLLER.getFieldInfo(new Point(posX, posY));
	}

	public String getFieldInfo(Point point) {
		return OBJECT_CONTROLLER.getFieldInfo(point);
	}

	public void removePowerUp(int posX, int posY) {
		OBJECT_CONTROLLER.removePowerUp(new Point(posX, posY));
	}

	public NetworkTarget getTank(Point point) {
		return OBJECT_CONTROLLER.getTank(point);
	}

	public GameRegion getMainRegion() {
		return gameWindow.getMainRegion();
	}

	public void setGamePanelMiddle(Point pos) {
		gameWindow.setGamePanelMiddle(pos);
	}

	public NetworkTarget getPlayer() {
		return ME;
	}

	public boolean isRegionController() {
		return regionController;
	}


	public String getPowerUp(int posX, int posY) {
		return OBJECT_CONTROLLER.getPowerUp(new Point(posX, posY)).getPowerUp();
	}
	
	public void setMainRegion(int region){
		OBJECT_CONTROLLER.setElements(region);
	}

	
	public void printTankInfo() {

		for (Map.Entry<NetworkTarget, Tank> entry : OBJECT_CONTROLLER
				.exportTankMap().entrySet()) {
			System.out.println(entry.getKey().getName() + "  Pos : "
					+ entry.getValue().getPosX() + "|"
					+ entry.getValue().getPosY());
		}
	}
}
