package view;

import interfaces.InformationVisualisation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import model.Music;

import controller.GameController;

public class GameWindow extends JFrame implements InformationVisualisation,
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GamePanel gamePanel;


	private JMenuBar menuBar;

	public JLabel bonusLabel;
	public JLabel bonusTimeLabel;
	public JLabel rangeLabel;
	public JLabel rateLabel;
	public JLabel speedLabel;
	public JLabel killsLabel;
	public JLabel regionLabel;

	public JLabel timeLabel;

	private JMenu menuFile;
	private JMenuItem menuItemExitGame;
	private JMenuItem menuItemBack;

	private String space = "          ";

	public final int GAMEMODE;

	private JMenu menuMusic;
	private JMenuItem menuItemPlayOrStopMusic;



	private Timer movement;
	private int onTheWay = 16;
	private int angle = 0;

	private final String PLAYER_NAME;

	public GameWindow(int parGameMode, String playerName) {
		PLAYER_NAME = playerName;
		setIconImage(new ImageIcon("src/resources/TankHunters.png").getImage());
		GAMEMODE = parGameMode;

	

		gamePanel = new GamePanel(this, GAMEMODE);
		getContentPane().add(gamePanel);

		setBounds(50, 50, 1125, 722);
		setLayout(null);
		GameController gc = new GameController(this, playerName);
		
		setTitle("Tank Hunters");
		addKeyListener(gc);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(getMenuBar0());
		setVisible(true);

		movement = new Timer(10, this);

		initialMusic();

	}

	public String getPlayerName() {
		return PLAYER_NAME;
	}

	private void initialMusic() {

		new Thread() {
			public void run() {
				try {
					Music.PlayMusicFromFolder(new File("").getAbsolutePath()
							+ "\\src\\resources\\music", false);
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {

				}
			}
		}.start();

	}

	private JMenuBar getMenuBar0() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMenuFile());
			menuBar.add(getMenuMusic());

			bonusLabel = new JLabel("Bonus : None" + space);
			rangeLabel = new JLabel("Attack Range : 10" + space);
			rangeLabel.setForeground(Color.BLUE);
			speedLabel = new JLabel("Movement Speed : 2" + space);
			speedLabel.setForeground(Color.DARK_GRAY);
			rateLabel = new JLabel("Attack Rate : 1000" + space);
			rateLabel.setForeground(Color.MAGENTA);

			killsLabel = new JLabel();
			killsLabel.setFont(new Font("Serif", Font.BOLD, 16));
			timeLabel = new JLabel("Time : 0.0 Sec     ");

			menuBar.add(timeLabel);
			menuBar.add(bonusLabel);
			menuBar.add(Box.createGlue());

			killsLabel.setText("Kills : 0" + space);
			killsLabel.setForeground(Color.RED);

			regionLabel = new JLabel("Region : (0|0)" + space);
			regionLabel.setFont(new Font("Serif", Font.BOLD, 16));
			regionLabel.setForeground(new Color(100, 100, 100));
			menuBar.add(rangeLabel);
			menuBar.add(speedLabel);
			menuBar.add(rateLabel);
			menuBar.add(killsLabel);
			menuBar.add(regionLabel);

		}
		return menuBar;
	}

	private JMenu getMenuFile() {
		if (menuFile == null) {
			menuFile = new JMenu("File");
			menuFile.add(getMenuItemBack());
			menuFile.add(getMenuItemExit());
		}
		return menuFile;
	}

	private JMenu getMenuMusic() {
		if (menuMusic == null) {
			menuMusic = new JMenu("Music");
			menuMusic.add(getMenuItemPlay());
		}
		return menuMusic;
	}

	private JMenuItem getMenuItemPlay() {

		if (menuItemPlayOrStopMusic == null) {
			menuItemPlayOrStopMusic = new JMenuItem("Stop");

			menuItemPlayOrStopMusic.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (menuItemPlayOrStopMusic.getText().equals("Play")) {
						initialMusic();
						menuItemPlayOrStopMusic.setText("Stop");

					} else {
						Music.stop();
						menuItemPlayOrStopMusic.setText("Play");
					}

				}
			});
		}
		return menuItemPlayOrStopMusic;
	}

	private JMenuItem getMenuItemBack() {

		if (menuItemBack == null) {
			menuItemBack = new JMenuItem("Back to Intro");
			menuItemBack.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new WelcomeWindow();
					dispose();
					Music.stop();
				}
			});
		}
		return menuItemBack;
	}

	private JMenuItem getMenuItemExit() {

		if (menuItemExitGame == null) {
			menuItemExitGame = new JMenuItem("Exit");
			menuItemExitGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return menuItemExitGame;
	}

	public void moveToNextRegion(Point pos, int parAngle) {
		angle = parAngle;
		
		switch(angle){
		case 0 : gamePanel.setLocation((int)gamePanel.getLocation().getX()+672,(int)gamePanel.getLocation().getX());
		gamePanel.regionArray[0] = gamePanel.getRegion(1);
		gamePanel.regionArray[3] = gamePanel.getMainRegion();
		gamePanel.regionArray[5] = gamePanel.getRegion(6);

		gamePanel.regionArray[1] = gamePanel.getRegion(2);
		gamePanel.mainRegion     = gamePanel.getRegion(4);
		gamePanel.regionArray[6] = gamePanel.getRegion(7);

		gamePanel.regionArray[2] = gamePanel.createRegion();
		gamePanel.regionArray[4] = gamePanel.createRegion();
		gamePanel.regionArray[7] = gamePanel.createRegion();
		
		gamePanel.regionArray[2].setLocation(1248, -288 + (6 - (int)pos.getY() * 32));
		gamePanel.regionArray[4].setLocation(1248, 128 + (6 -(int)pos.getY()) * 32);
		gamePanel.regionArray[7].setLocation(1248, 544 + (6 -(int)pos.getY()) * 32);
		
		gamePanel.add(gamePanel.regionArray[2]);
		gamePanel.add(gamePanel.regionArray[4]);
		gamePanel.add(gamePanel.regionArray[7]);
		
			break;
		case 90 : gamePanel.setLocation((int)gamePanel.getLocation().getX()-416,(int)gamePanel.getLocation().getX());
			//TODO <- schieben!
			break;
		case 180 : gamePanel.setLocation((int)gamePanel.getLocation().getX()-672,(int)gamePanel.getLocation().getX());
			//TODO <- schieben!
			break;
		case 270 : gamePanel.setLocation((int)gamePanel.getLocation().getX()+416,(int)gamePanel.getLocation().getX());
			//TODO <- schieben!
			break;
		}

		movement.start();

	}

	public void showGameTime(int time) {
		timeLabel.setText("Time : " + time);
	}

	public void showBonus(String bonus) {
		bonusLabel.setText(bonus);
	}

	public void showBonusTime(int time) {
		bonusTimeLabel.setText(String.valueOf(time));
	}

	public void showAttackRange(int range) {
		rangeLabel.setText("Attack Range : " + range);
	}

	public void showMovementSpeed(int speed) {
		speedLabel.setText("Movement Speed : " + speed);
	}

	public void showAttackRate(int rate) {
		rateLabel.setText("Attack Rate : " + rate);

	}

	public void showKills(int kills) {
		killsLabel.setText("Kills : " + kills);

	}

	public void showRegion(int posX, int posY) {
		regionLabel.setText("Region (" + posX + "|" + posY + ")");
	}

	public void movePanel(int angle) {
		switch (angle) {
		case 0:
			gamePanel.setLocation((int) gamePanel.getLocation().getX()-2, (int) gamePanel.getLocation().getY());
			break;
		case 90:
			gamePanel.setLocation((int) gamePanel.getLocation().getX(), (int) gamePanel.getLocation().getY()+2);
			break;
		case 180:
			gamePanel.setLocation((int) gamePanel.getLocation().getX()+2, (int) gamePanel.getLocation().getY());
			break;
		case 270:
			gamePanel.setLocation((int) gamePanel.getLocation().getX(), (int) gamePanel.getLocation().getY()-2);
			break;

		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == movement) {
			onTheWay--;
			if (onTheWay > 0) {
				movePanel(angle);
			} else {
				movement.stop();
				onTheWay = 16;
			}

		}
	}

	public GameRegion getMainRegion() {
		return gamePanel.getMainRegion();
	}
	
	public void setGamePanelMiddle(Point pos){
		gamePanel.setLocation(-128-((int)pos.getX()*32),-96-((int)pos.getY()*32));
	}

}
