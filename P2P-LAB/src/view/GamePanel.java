package view;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	public GameRegion mainRegion;
	public GameRegion gp00;
	public GameRegion gp01;
	public GameRegion gp02;
	public GameRegion gp10;
	public GameRegion gp12;
	public GameRegion gp20;
	public GameRegion gp21;
	public GameRegion gp22;

	public GamePanel(GameWindow gameWindow, int GAMEMODE) {
		
		    Dimension size = new Dimension(2016,1248);
		    setLocation(-448, -288);
		    setPreferredSize(size);
		    setSize(size);
		    setLayout(null);
		    
		    
			mainRegion = new GameRegion("src/resources/b11.png", gameWindow, GAMEMODE);

			gp00 = new GameRegion("src/resources/b00.png", gameWindow, GAMEMODE);
			gp01 = new GameRegion("src/resources/b01.png", gameWindow, GAMEMODE);
			gp02 = new GameRegion("src/resources/b02.png", gameWindow, GAMEMODE);
			gp10 = new GameRegion("src/resources/b10.png", gameWindow, GAMEMODE);

			gp12 = new GameRegion("src/resources/b12.png", gameWindow, GAMEMODE);
			gp20 = new GameRegion("src/resources/b20.png", gameWindow, GAMEMODE);
			gp21 = new GameRegion("src/resources/b21.png", gameWindow, GAMEMODE);
			gp22 = new GameRegion("src/resources/b22.png", gameWindow, GAMEMODE);

			gp00.setLocation(0,0);
			gp01.setLocation(0,416);
			gp02.setLocation(0,832);
			gp10.setLocation(672,0);
			mainRegion.setLocation(672,416);
			gp12.setLocation(672,832);
			gp20.setLocation(1344,0);
			gp21.setLocation(1344,416);
			gp22.setLocation(1344,832);
			
			add(gp00);
			add(gp01);
			add(gp02);
			add(gp10);
			add(gp12);
			add(gp20);
			add(gp21);
			add(gp22);
			add(mainRegion);
	}

	public GameRegion getMainRegion() {
		return mainRegion;
	}
	
	

}
