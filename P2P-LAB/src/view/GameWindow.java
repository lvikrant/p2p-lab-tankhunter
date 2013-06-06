package view;

import interfaces.InformationVisualisation;

import java.awt.Color;
import java.awt.Font;
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

	public GamePanel gp;
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

	public GamePanel gp00;
	public GamePanel gp01;
	public GamePanel gp02;
	public GamePanel gp10;
	public GamePanel gp12;
	public GamePanel gp20;
	public GamePanel gp21;
	public GamePanel gp22;

	private Timer movement;
	private int onTheWay = 16;
	private int angle = 0;

	private final String PLAYER_NAME;

	public GameWindow(int parGameMode, String playerName) {
		PLAYER_NAME = playerName;
		setIconImage(new ImageIcon("src/resources/TankHunters.png").getImage());
		GAMEMODE = parGameMode;

		gp = new GamePanel("src/resources/b11.png", this, GAMEMODE);
		gp00 = new GamePanel("src/resources/b00.png", this, GAMEMODE);
		gp01 = new GamePanel("src/resources/b01.png", this, GAMEMODE);
		gp02 = new GamePanel("src/resources/b02.png", this, GAMEMODE);
		gp10 = new GamePanel("src/resources/b10.png", this, GAMEMODE);

		gp12 = new GamePanel("src/resources/b12.png", this, GAMEMODE);
		gp20 = new GamePanel("src/resources/b20.png", this, GAMEMODE);
		gp21 = new GamePanel("src/resources/b21.png", this, GAMEMODE);
		gp22 = new GamePanel("src/resources/b22.png", this, GAMEMODE);

		gp00.setLocation(-448, -288);
		gp01.setLocation(-448, 128);
		gp02.setLocation(-448, 544);
		gp10.setLocation(224, -288);
		gp.setLocation(224, 128);
		gp12.setLocation(224, 544);
		gp20.setLocation(896, -288);
		gp21.setLocation(896, 128);
		gp22.setLocation(896, 544);

		getContentPane().add(gp00);
		getContentPane().add(gp01);
		getContentPane().add(gp02);
		getContentPane().add(gp10);
		getContentPane().add(gp12);
		getContentPane().add(gp20);
		getContentPane().add(gp21);
		getContentPane().add(gp22);

		setBounds(50, 50, 1125, 722);

		setLayout(null);
		GameController gc = new GameController(this, playerName);
		setTitle("Tank Hunters");
		addKeyListener(gc);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(gp);
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

	public void createNewPanel(int type) {
		gp.setVisible(false);
		remove(gp);
		menuBar.setVisible(false);
		gp = new GamePanel("src/resources/background1.png", this, GAMEMODE);
		add(gp);
		menuBar.setVisible(true);
	}

	public void moveToRegionRight(int posY) {

		gp00 = gp10;
		gp01 = gp;
		gp02 = gp12;

		gp10 = gp20;
		gp = gp21;
		gp12 = gp22;

		gp20 = new GamePanel("src/resources/bg.png", this, GAMEMODE);
		gp21 = new GamePanel("src/resources/bg.png", this, GAMEMODE);
		gp22 = new GamePanel("src/resources/bg.png", this, GAMEMODE);

		getContentPane().add(gp20);
		getContentPane().add(gp21);
		getContentPane().add(gp22);

		gp20.setLocation(1248, -288 + (6 - posY) * 32);
		gp21.setLocation(1248, 128 + (6 - posY) * 32);
		gp22.setLocation(1248, 544 + (6 - posY) * 32);

		angle = 0;
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

	public void movePanels(int angle) {
		switch (angle) {
		case 0:
			gp00.setLocation((int) gp00.getLocation().getX() - 2, (int) gp00
					.getLocation().getY());
			gp01.setLocation((int) gp01.getLocation().getX() - 2, (int) gp01
					.getLocation().getY());
			gp02.setLocation((int) gp02.getLocation().getX() - 2, (int) gp02
					.getLocation().getY());
			gp10.setLocation((int) gp10.getLocation().getX() - 2, (int) gp10
					.getLocation().getY());
			gp.setLocation((int) gp.getLocation().getX() - 2, (int) gp
					.getLocation().getY());
			gp12.setLocation((int) gp12.getLocation().getX() - 2, (int) gp12
					.getLocation().getY());
			gp20.setLocation((int) gp20.getLocation().getX() - 2, (int) gp20
					.getLocation().getY());
			gp21.setLocation((int) gp21.getLocation().getX() - 2, (int) gp21
					.getLocation().getY());
			gp22.setLocation((int) gp22.getLocation().getX() - 2, (int) gp22
					.getLocation().getY());
			break;
		case 90:
			gp00.setLocation((int) gp00.getLocation().getX(), (int) gp00
					.getLocation().getY() + 2);
			gp01.setLocation((int) gp01.getLocation().getX(), (int) gp01
					.getLocation().getY() + 2);
			gp02.setLocation((int) gp02.getLocation().getX(), (int) gp02
					.getLocation().getY() + 2);
			gp10.setLocation((int) gp10.getLocation().getX(), (int) gp10
					.getLocation().getY() + 2);
			gp.setLocation((int) gp.getLocation().getX(), (int) gp
					.getLocation().getY() + 2);
			gp12.setLocation((int) gp12.getLocation().getX(), (int) gp12
					.getLocation().getY() + 2);
			gp20.setLocation((int) gp20.getLocation().getX(), (int) gp20
					.getLocation().getY() + 2);
			gp21.setLocation((int) gp21.getLocation().getX(), (int) gp21
					.getLocation().getY() + 2);
			gp22.setLocation((int) gp22.getLocation().getX(), (int) gp22
					.getLocation().getY() + 2);

			break;
		case 180:
			gp00.setLocation((int) gp00.getLocation().getX() + 2, (int) gp00
					.getLocation().getY());
			gp01.setLocation((int) gp01.getLocation().getX() + 2, (int) gp01
					.getLocation().getY());
			gp02.setLocation((int) gp02.getLocation().getX() + 2, (int) gp02
					.getLocation().getY());
			gp10.setLocation((int) gp10.getLocation().getX() + 2, (int) gp10
					.getLocation().getY());
			gp.setLocation((int) gp.getLocation().getX() + 2, (int) gp
					.getLocation().getY());
			gp12.setLocation((int) gp12.getLocation().getX() + 2, (int) gp12
					.getLocation().getY());
			gp20.setLocation((int) gp20.getLocation().getX() + 2, (int) gp20
					.getLocation().getY());
			gp21.setLocation((int) gp21.getLocation().getX() + 2, (int) gp21
					.getLocation().getY());
			gp22.setLocation((int) gp22.getLocation().getX() + 2, (int) gp22
					.getLocation().getY());
			break;
		case 270:

			gp00.setLocation((int) gp00.getLocation().getX(), (int) gp00
					.getLocation().getY() - 2);
			gp01.setLocation((int) gp01.getLocation().getX(), (int) gp01
					.getLocation().getY() - 2);
			gp02.setLocation((int) gp02.getLocation().getX(), (int) gp02
					.getLocation().getY() - 2);
			gp10.setLocation((int) gp10.getLocation().getX(), (int) gp10
					.getLocation().getY() - 2);
			gp.setLocation((int) gp.getLocation().getX(), (int) gp
					.getLocation().getY() - 2);
			gp12.setLocation((int) gp12.getLocation().getX(), (int) gp12
					.getLocation().getY() - 2);
			gp20.setLocation((int) gp20.getLocation().getX(), (int) gp20
					.getLocation().getY() - 2);
			gp21.setLocation((int) gp21.getLocation().getX(), (int) gp21
					.getLocation().getY() - 2);
			gp22.setLocation((int) gp22.getLocation().getX(), (int) gp22
					.getLocation().getY() - 2);
			break;

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == movement) {
			onTheWay--;
			if (onTheWay > 0) {
				movePanels(angle);
			} else {
				movement.stop();
				onTheWay = 16;
			}

		}
	}

}
