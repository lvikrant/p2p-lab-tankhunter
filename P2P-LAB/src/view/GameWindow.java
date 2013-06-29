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
import model.NetworkTarget;

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

	private String space = "      ";

	private JMenu menuMusic;
	private JMenuItem menuItemPlayOrStopMusic;
	
	public  GameRegion [] regionArray = new GameRegion[9];

	private Timer movement;
	private int onTheWay = 16;
	private int angle = 0;
	
	GameController gc;
	
	private NetworkTarget myinfo;

	/** SERVER */
	
	public GameWindow(NetworkTarget server) {
	    initialWindow();
	    myinfo = server;
		
		gc = new GameController(this, server);
		addKeyListener(gc);
		setResizable(false);
		
		gc.setMainRegion(regionArray[4].getRegionType());
		initialMusic();

	}
	
	/** CLIENT */
	public GameWindow(NetworkTarget server, NetworkTarget client) {
        initialWindow();
        
        myinfo = client;
		
		gc = new GameController(this, server , client);
		addKeyListener(gc);
		setResizable(false);
		
		initialMusic();

	}

	public NetworkTarget getPlayer() {
		return myinfo;
	}

	private void initialWindow(){
		setIconImage(new ImageIcon("src/resources/TankHunters.png").getImage());

		setBounds(50, 50, 1125, 722);
		setLayout(null);
		
		setTitle("Tank Hunters");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(getMenuBar0());
		setVisible(true);

		movement = new Timer(10, this);
		
		for(int i = 0; i < 9; i++){
			regionArray[i] = createNewRegion();
			add(regionArray[i]);
		}
		
		regionArray[0].setLocation(0,0);
		regionArray[1].setLocation(672,0);
		regionArray[2].setLocation(1344,0);
		regionArray[3].setLocation(0,416);
		regionArray[4].setLocation(672,416);	
		regionArray[5].setLocation(1344,416);
		regionArray[6].setLocation(0,832);
		regionArray[7].setLocation(672,832);
		regionArray[8].setLocation(1344,832);
		
		
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
			bonusTimeLabel = new JLabel("");
			rangeLabel = new JLabel("Attack Range : 7" + space);
			rangeLabel.setForeground(Color.BLUE);
			speedLabel = new JLabel("Movement Speed : 350" + space);
			speedLabel.setForeground(Color.DARK_GRAY);
			rateLabel = new JLabel("Attack Rate : 2000" + space);
			rateLabel.setForeground(Color.MAGENTA);

			killsLabel = new JLabel();
			killsLabel.setFont(new Font("Serif", Font.BOLD, 16));
			timeLabel = new JLabel("Time : 0.0 Sec     ");

			menuBar.add(timeLabel);
			menuBar.add(bonusLabel);
			menuBar.add(bonusTimeLabel);
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
		    
			regionArray[0] = regionArray[1];
		  	regionArray[1] = regionArray[2];
		  	
		  	regionArray[3] = regionArray[4];
		  	regionArray[4] = regionArray[5];
		  	
		  	regionArray[6] = regionArray[7];
		  	regionArray[7] = regionArray[8];

		  	regionArray[2] = createNewRegion();
		  	regionArray[5] = createNewRegion();
		  	regionArray[8] = createNewRegion();
		  	
		  	getContentPane().add(regionArray[2]);
			getContentPane().add(regionArray[5]);
			getContentPane().add(regionArray[8]);
			 
			regionArray[2].setLocation(1248, -288+(6-(int)pos.getY())*32);
			regionArray[5].setLocation(1248, 128+(6-(int)pos.getY())*32);
			regionArray[8].setLocation(1248, 544+(6-(int)pos.getY())*32);
			
			angle = 0;
			break;
		case 90:
			
			regionArray[6] = regionArray[3];
			regionArray[3] = regionArray[0];
		  	
			regionArray[7] = regionArray[4];
			regionArray[4] = regionArray[1];
		  	
			regionArray[8] = regionArray[5];  	
			regionArray[5] = regionArray[2];

			regionArray[0] = createNewRegion();
			regionArray[1] = createNewRegion();
			regionArray[2] = createNewRegion();
		  	

		  
		  	getContentPane().add(regionArray[0]);
			getContentPane().add(regionArray[1]);
			getContentPane().add(regionArray[2]);
			 
			regionArray[0].setLocation(-224+(3-(int)pos.getX())*32,-512);
			regionArray[1].setLocation(448+(3-(int)pos.getX())*32, -512);
			regionArray[2].setLocation(1120+(3-(int)pos.getX())*32, -512);
			
			angle = 90;
			break;
		case 180:
			regionArray[2] = regionArray[1];
			regionArray[1] = regionArray[0];
		  	
			regionArray[5] = regionArray[4];
			regionArray[4] = regionArray[3];
		  	
			regionArray[8] = regionArray[7];
			regionArray[7] = regionArray[6];
		  	
			regionArray[0] = createNewRegion();
			regionArray[3] = createNewRegion();
			regionArray[6] = createNewRegion();
		  	
		  	getContentPane().add(regionArray[0]);
			getContentPane().add(regionArray[3]);
			getContentPane().add(regionArray[6]);
			 
			regionArray[0].setLocation(-800, -288+(6-(int)pos.getY())*32);
			regionArray[3].setLocation(-800, 128+(6-(int)pos.getY())*32);
			regionArray[6].setLocation(-800, 544+(6-(int)pos.getY())*32);
			
			angle = 180;
			break;
		case 270:
			
			regionArray[0] = regionArray[3];
			regionArray[3] = regionArray[6];
		  
			regionArray[1] = regionArray[4];
			regionArray[4] = regionArray[7];
		  
			regionArray[2] = regionArray[5];
			regionArray[5] = regionArray[8];
		  	
			regionArray[6] = createNewRegion();
			regionArray[7] = createNewRegion();
			regionArray[8] = createNewRegion();
		  	
		  	getContentPane().add(regionArray[6]);
			getContentPane().add(regionArray[7]);
			getContentPane().add(regionArray[8]);
			 
			regionArray[6].setLocation(-224+(3-(int)pos.getX())*32, 768);
			regionArray[7].setLocation(448+(3-(int)pos.getX())*32, 768);
			regionArray[8].setLocation(1120+(3-(int)pos.getX())*32, 768);
			
			angle = 270;
			break;
		}
	  
		movement.start();

		
	}

	public void showGameTime(int time) {
		timeLabel.setText("Time : " + time);
	}

	public void showBonus(String bonus) {
		bonusLabel.setText("Bonus : " + bonus);
		
		
		if(bonus.equals("NONE")){
			bonusLabel.setForeground(Color.BLACK);
			
		} else if(bonus.equals("RATE")){
			bonusLabel.setForeground(Color.MAGENTA);
			
		}else if(bonus.equals("SPEED")){
			bonusLabel.setForeground(Color.LIGHT_GRAY);
			
		}else if(bonus.equals("RANGE")){
			bonusLabel.setForeground(Color.BLUE);
			
		}else if(bonus.equals("SLOW")){
			bonusLabel.setForeground(new Color(70,200,40));
			
		}else if(bonus.equals("SHIELD")){
			bonusLabel.setForeground(Color.RED);
		}
		
		
	}

	public void showBonusTime(int time) {
		bonusTimeLabel.setText(space  + "Time left : "+ String.valueOf(time) + " sec");
		if(time == 0){
			bonusTimeLabel.setText("");
		}
	}

	public void showAttackRange(int range) {
		rangeLabel.setText("Attack Range : " + range + space);
	}

	public void showMovementSpeed(int speed) {
		speedLabel.setText("Movement Speed : " + speed + space);
	}

	public void showAttackRate(int rate) {
		rateLabel.setText("Attack Rate : " + rate + space);

	}

	public void showKills(int kills) {
		killsLabel.setText("Kills : " + kills);

	}

	public void showRegion(int posX, int posY) {
		regionLabel.setText("Region (" + posX + "|" + posY + ")");
	}


	public void movePanels(int angle){
		for(GameRegion reg : regionArray){
			switch (angle) {
			case 0:	
		   	   reg.setLocation((int)reg.getLocation().getX()-2,(int)reg.getLocation().getY());
		    	break;
			case 90:
			   	   reg.setLocation((int)reg.getLocation().getX(),(int)reg.getLocation().getY()+2);	  
				break;
			case 180:
			   	   reg.setLocation((int)reg.getLocation().getX()+2,(int)reg.getLocation().getY());
				break;
			case 270:
			   	   reg.setLocation((int)reg.getLocation().getX(),(int)reg.getLocation().getY()-2);
				break;
				
			}
		}
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == movement) {
			onTheWay--;
			if (onTheWay >= 0) {
				movePanels(angle);
			} else {
				movement.stop();
				onTheWay = 16;
			}

		}
	}

	public GameRegion getMainRegion() {
		return regionArray[4];
	}
	
	public void setGamePanelMiddle(Point pos){

		regionArray[0].setLocation(-128-(int)pos.getX()*32,-96-(int)pos.getY()*32);
		regionArray[1].setLocation(544-(int)pos.getX()*32,-96-(int)pos.getY()*32);
		regionArray[2].setLocation(1216-(int)pos.getX()*32,-96-(int)pos.getY()*32);
		regionArray[3].setLocation(-128-(int)pos.getX()*32,320-(int)pos.getY()*32);
		regionArray[4].setLocation(544-(int)pos.getX()*32,320-(int)pos.getY()*32);
		regionArray[5].setLocation(1216-(int)pos.getX()*32,320-(int)pos.getY()*32);
		regionArray[6].setLocation(-128-(int)pos.getX()*32,736-(int)pos.getY()*32);
		regionArray[7].setLocation(544-(int)pos.getX()*32,736-(int)pos.getY()*32);
		regionArray[8].setLocation(1216-(int)pos.getX()*32,736-(int)pos.getY()*32);

	}	
	
	public GameRegion createNewRegion(){
		int random = (int) (Math.random() * 3);
		return new GameRegion("src/resources/regions/region" + random +".png", this, random);
		
	}
	
		
	
	public NetworkTarget getMyNetworkTarget(){
		return gc.getPlayer();
	}

}
