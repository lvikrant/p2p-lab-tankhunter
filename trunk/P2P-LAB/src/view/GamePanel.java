package view;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public GameRegion mainRegion;
	public GameRegion[] regionArray = new GameRegion[8];
	
	public GameRegion r0;
	public GameRegion r1;
	public GameRegion r2;
	public GameRegion r3;
	public GameRegion r4;
	public GameRegion r5;
	public GameRegion r6;
	public GameRegion r7;
	

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
		    
			mainRegion = new GameRegion("src/resources/bmain.png", gw, GAMEMODE);
			mainRegion.setLocation(672,416);
			add(mainRegion);
			
			r0 = new GameRegion("src/resources/b0.png", gw, GAMEMODE);
			r0.setLocation(0,0);
			add(r0);
			
			r1 = new GameRegion("src/resources/b1.png", gw, GAMEMODE);
			r1.setLocation(672,0);
			add(r1);
			
			r2 = new GameRegion("src/resources/b2.png", gw, GAMEMODE);
			r2.setLocation(1344,0);
			add(r2);
			
			r3 = new GameRegion("src/resources/b3.png", gw, GAMEMODE);
			r3.setLocation(0,416);
			add(r3);
			
			r4 = new GameRegion("src/resources/b4.png", gw, GAMEMODE);
			r4.setLocation(1344,416);
			add(r4);
			
			r5 = new GameRegion("src/resources/b5.png", gw, GAMEMODE);
			r5.setLocation(0,832);
			add(r5);
			
			r6 = new GameRegion("src/resources/b6.png", gw, GAMEMODE);
			r6.setLocation(672,832);
			add(r6);
			
			r7 = new GameRegion("src/resources/b7.png", gw, GAMEMODE);
			r7.setLocation(1344,832);
			add(r7);
		
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
