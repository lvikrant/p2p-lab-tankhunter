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

	public GameRegion r0;
	public GameRegion r1;
	public GameRegion r2;
	public GameRegion r3;
	public GameRegion r4;
	public GameRegion r5;
	public GameRegion r6;
	public GameRegion r7;
	public GameRegion r8;

	private Timer movement;
	private int onTheWay = 16;
	private int angle = 0;

	private final String PLAYER_NAME;

	public GameWindow(int parGameMode, String playerName) {
		PLAYER_NAME = playerName;
		setIconImage(new ImageIcon("src/resources/TankHunters.png").getImage());
		GAMEMODE = parGameMode;


		setBounds(50, 50, 1125, 722);
		setLayout(null);
		
		setTitle("Tank Hunters");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(getMenuBar0());
		setVisible(true);

		movement = new Timer(10, this);
		
		r0 = new GameRegion("src/resources/b0.png", this, GAMEMODE);
		r0.setLocation(0,0);
		add(r0);
		
		r1 = new GameRegion("src/resources/b1.png", this, GAMEMODE);
		r1.setLocation(672,0);
		add(r1);
		
		r2 = new GameRegion("src/resources/b2.png", this, GAMEMODE);
		r2.setLocation(1344,0);
		add(r2);
		
		r3 = new GameRegion("src/resources/b3.png", this, GAMEMODE);
		r3.setLocation(0,416);
		add(r3);
		
		r4 = new GameRegion("src/resources/bmain.png", this, GAMEMODE);
		r4.setLocation(672,416);
		add(r4);
		System.out.println(r4.toString());
		
		r5 = new GameRegion("src/resources/b4.png", this, GAMEMODE);
		r5.setLocation(1344,416);
		add(r5);
		
		r6 = new GameRegion("src/resources/b5.png", this, GAMEMODE);
		r6.setLocation(0,832);
		add(r6);
		
		r7 = new GameRegion("src/resources/b6.png", this, GAMEMODE);
		r7.setLocation(672,832);
		add(r7);
		
		r8 = new GameRegion("src/resources/b7.png", this, GAMEMODE);
		r8.setLocation(1344,832);
		add(r8);
		
		
		GameController gc = new GameController(this, playerName,GAMEMODE);
		addKeyListener(gc);
		setResizable(false);
		
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


	public void moveToNextRegion(Point pos, int parAngle){
		angle = parAngle;
		switch(angle){
		case 0:
		    
			r0 = r1;
		  	r1 = r2;
		  	
		  	r3 = r4;
		  	r4 = r5;
		  	
		  	r6 = r7;
		  	r7 = r8;

		  	r2 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		    r5 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		  	r8 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		  	
		  	getContentPane().add(r2);
			getContentPane().add(r5);
			getContentPane().add(r8);
			 
			r2.setLocation(1248, -288+(6-(int)pos.getY())*32);
			r5.setLocation(1248, 128+(6-(int)pos.getY())*32);
			r8.setLocation(1248, 544+(6-(int)pos.getY())*32);
			
			angle = 0;
			break;
		case 90:
			
		  	r6 = r3;
		  	r3 = r0;
		  	
		  	r7 = r4;
		  	r4 = r1;
		  	
		  	r8 = r5;  	
		  	r5 = r2;

		  	r0 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		    r1 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
	        r2 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		  	

		  
	//	  	getContentPane().add(r6);
	//		getContentPane().add(r7);
	//		getContentPane().add(r8);
			 
	//		r6.setLocation(-320+(3-(int)pos.getX())*32,-288);
	//		r7.setLocation(320+(3-(int)pos.getX())*32, -288);
	//		r8.setLocation(680+(3-(int)pos.getX())*32, -288);
			
			angle = 90;
			break;
		case 180:
			r2 = r1;
		  	r1 = r0;
		  	
		  	r5 = r4;
		  	r4 = r3;
		  	
		  	r8 = r7;
		  	r7 = r6;
		  	
		  	r0 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		    r3 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		  	r6 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		  	
		  	getContentPane().add(r0);
			getContentPane().add(r3);
			getContentPane().add(r6);
			 
			r0.setLocation(-800, -288+(6-(int)pos.getY())*32);
			r3.setLocation(-800, 128+(6-(int)pos.getY())*32);
			r6.setLocation(-800, 544+(6-(int)pos.getY())*32);
			
			angle = 180;
			break;
		case 270:
			
		  	r0 = r3;
		  	r3 = r6;
		  
			r1 = r4;
		  	r4 = r7;
		  
		  	r2 = r5;
		  	r5 = r8;
		  	
		  	r6 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		    r7 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		  	r8 = new GameRegion("src/resources/bg.png",this,GAMEMODE);
		  	
//		  	getContentPane().add(r2);
//			getContentPane().add(r5);
//			getContentPane().add(r8);
			 
//			r2.setLocation(1248, -288+(6-(int)pos.getY())*32);
//			r5.setLocation(1248, 128+(6-(int)pos.getY())*32);
//			r8.setLocation(1248, 544+(6-(int)pos.getY())*32);
			
			angle = 270;
			break;
		}
	  
		System.out.println("MOVE");
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


	public void movePanels(int angle){
		System.out.println("Position  +-2 : " + r0.getLocation());
		switch (angle) {
		case 0:
	   	   r0.setLocation((int)r0.getLocation().getX()-2,(int)r0.getLocation().getY());
	   	   r1.setLocation((int)r1.getLocation().getX()-2,(int)r1.getLocation().getY());
	   	   r2.setLocation((int)r2.getLocation().getX()-2,(int)r2.getLocation().getY());
	   	   r3.setLocation((int)r3.getLocation().getX()-2,(int)r3.getLocation().getY());
	   	   r4.setLocation((int)r4.getLocation().getX()-2,(int)r4.getLocation().getY());
	   	   r5.setLocation((int)r5.getLocation().getX()-2,(int)r5.getLocation().getY());
	   	   r6.setLocation((int)r6.getLocation().getX()-2,(int)r6.getLocation().getY());
	   	   r7.setLocation((int)r7.getLocation().getX()-2,(int)r7.getLocation().getY());
	   	   r8.setLocation((int)r8.getLocation().getX()-2,(int)r8.getLocation().getY());
	    	break;
		case 90:
		   	   r0.setLocation((int)r0.getLocation().getX(),(int)r0.getLocation().getY()+2);
		   	   r1.setLocation((int)r1.getLocation().getX(),(int)r1.getLocation().getY()+2);
		   	   r2.setLocation((int)r2.getLocation().getX(),(int)r2.getLocation().getY()+2);
		   	   r3.setLocation((int)r3.getLocation().getX(),(int)r3.getLocation().getY()+2);
		   	   r4.setLocation((int)r4.getLocation().getX(),(int)r4.getLocation().getY()+2);
		   	   r5.setLocation((int)r5.getLocation().getX(),(int)r5.getLocation().getY()+2);
		   	   r6.setLocation((int)r6.getLocation().getX(),(int)r6.getLocation().getY()+2);
		   	   r7.setLocation((int)r7.getLocation().getX(),(int)r7.getLocation().getY()+2);
		   	   r8.setLocation((int)r8.getLocation().getX(),(int)r8.getLocation().getY()+2);
			  
			break;
		case 180:
		   	   r0.setLocation((int)r0.getLocation().getX()+2,(int)r0.getLocation().getY());
		   	   r1.setLocation((int)r1.getLocation().getX()+2,(int)r1.getLocation().getY());
		   	   r2.setLocation((int)r2.getLocation().getX()+2,(int)r2.getLocation().getY());
		   	   r3.setLocation((int)r3.getLocation().getX()+2,(int)r3.getLocation().getY());
		   	   r4.setLocation((int)r4.getLocation().getX()+2,(int)r4.getLocation().getY());
		   	   r5.setLocation((int)r5.getLocation().getX()+2,(int)r5.getLocation().getY());
		   	   r6.setLocation((int)r6.getLocation().getX()+2,(int)r6.getLocation().getY());
		   	   r7.setLocation((int)r7.getLocation().getX()+2,(int)r7.getLocation().getY());
		   	   r8.setLocation((int)r8.getLocation().getX()+2,(int)r8.getLocation().getY());

			break;
		case 270:
		   	   r0.setLocation((int)r0.getLocation().getX(),(int)r0.getLocation().getY()-2);
		   	   r1.setLocation((int)r1.getLocation().getX(),(int)r1.getLocation().getY()-2);
		   	   r2.setLocation((int)r2.getLocation().getX(),(int)r2.getLocation().getY()-2);
		   	   r3.setLocation((int)r3.getLocation().getX(),(int)r3.getLocation().getY()-2);
		   	   r4.setLocation((int)r4.getLocation().getX(),(int)r4.getLocation().getY()-2);
		   	   r5.setLocation((int)r5.getLocation().getX(),(int)r5.getLocation().getY()-2);
		   	   r6.setLocation((int)r6.getLocation().getX(),(int)r6.getLocation().getY()-2);
		   	   r7.setLocation((int)r7.getLocation().getX(),(int)r7.getLocation().getY()-2);
		   	   r8.setLocation((int)r8.getLocation().getX(),(int)r8.getLocation().getY()-2);
			

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

	public GameRegion getMainRegion() {
		return r4;
	}
	
	public void setGamePanelMiddle(Point pos){
	   	   r0.setLocation((int)r0.getLocation().getX()-128-(int)pos.getX()*32,(int)r0.getLocation().getY()-96-(int)pos.getY()*32);
	   	   r1.setLocation((int)r1.getLocation().getX()-128-(int)pos.getX()*32,(int)r1.getLocation().getY()-96-(int)pos.getY()*32);
	   	   r2.setLocation((int)r2.getLocation().getX()-128-(int)pos.getX()*32,(int)r2.getLocation().getY()-96-(int)pos.getY()*32);
	   	   r3.setLocation((int)r3.getLocation().getX()-128-(int)pos.getX()*32,(int)r3.getLocation().getY()-96-(int)pos.getY()*32);
	   	   r4.setLocation((int)r4.getLocation().getX()-128-(int)pos.getX()*32,(int)r4.getLocation().getY()-96-(int)pos.getY()*32);
	   	   r5.setLocation((int)r5.getLocation().getX()-128-(int)pos.getX()*32,(int)r5.getLocation().getY()-96-(int)pos.getY()*32);
	   	   r6.setLocation((int)r6.getLocation().getX()-128-(int)pos.getX()*32,(int)r6.getLocation().getY()-96-(int)pos.getY()*32);
	   	   r7.setLocation((int)r7.getLocation().getX()-128-(int)pos.getX()*32,(int)r7.getLocation().getY()-96-(int)pos.getY()*32);
	   	   r8.setLocation((int)r8.getLocation().getX()-128-(int)pos.getX()*32,(int)r8.getLocation().getY()-96-(int)pos.getY()*32);
	}	
	

}
