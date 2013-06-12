package view;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public GameRegion mainRegion;
	public GameRegion[] regionArray = new GameRegion[8];
	
	public GameRegion gp00;
	public GameRegion gp01;
	public GameRegion gp02;
	public GameRegion gp10;
	public GameRegion gp12;
	public GameRegion gp20;
	public GameRegion gp21;
	public GameRegion gp22;

	private final int GAMEMODE;
	private GameWindow gw;
	
	public GamePanel(GameWindow gameWindow, int gameMode) {
		    GAMEMODE = gameMode;
		    gw = gameWindow;
		    Dimension size = new Dimension(2016,1248);
		    setLocation(-448, -288);
		    setPreferredSize(size);
		    setSize(size);
		    setLayout(null);  
		    
			mainRegion = new GameRegion("src/resources/b11.png", gw, GAMEMODE);
			mainRegion.setLocation(672,416);
			add(mainRegion);
			
			regionArray[0] = new GameRegion("src/resources/b00.png", gw, GAMEMODE);
			regionArray[0].setLocation(0,0);
			add(regionArray[0]);
			
			regionArray[1] = new GameRegion("src/resources/b10.png", gw, GAMEMODE);
			regionArray[1].setLocation(672,0);
			add(regionArray[1]);
			
			regionArray[2] = new GameRegion("src/resources/b20.png", gw, GAMEMODE);
			regionArray[2].setLocation(1344,0);
			add(regionArray[2]);
			
			regionArray[3] = new GameRegion("src/resources/b01.png", gw, GAMEMODE);
			regionArray[3].setLocation(0,416);
			add(regionArray[3]);
			
			regionArray[4] = new GameRegion("src/resources/b21.png", gw, GAMEMODE);
			regionArray[4].setLocation(1344,416);
			add(regionArray[4]);
			
			regionArray[5] = new GameRegion("src/resources/b02.png", gw, GAMEMODE);
			regionArray[5].setLocation(0,832);
			add(regionArray[5]);
			
			regionArray[6] = new GameRegion("src/resources/b12.png", gw, GAMEMODE);
			regionArray[6].setLocation(672,832);
			add(regionArray[6]);
			
			regionArray[7] = new GameRegion("src/resources/b22.png", gw, GAMEMODE);
			regionArray[7].setLocation(1344,832);
			add(regionArray[7]);
		
	}
	
	public GameRegion getRegion(int id){
		return regionArray[id];
	}

	public GameRegion getMainRegion() {
		return mainRegion;
	}
	
	public GameRegion createRegion(){
		return new GameRegion("src/resources/bg.png", gw, GAMEMODE);
	}
	
	

}
