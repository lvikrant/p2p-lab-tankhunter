package model;

import controller.GameController;

public class RegionTypes {
	
	private final int MAP_WIDTH;
	private final int MAP_HEIGHT;
	private String[][] map;
	private int ID;
	private GameController gc;

public RegionTypes(GameController gc, int mapID) {
	this.gc = gc;
	MAP_WIDTH = gc.getMapWidth();
	MAP_HEIGHT = gc.getMapHeight();
	map = new String[MAP_WIDTH][MAP_HEIGHT];
	clearMap();
	setRegion(mapID);
}

public String getFieldInfo(int posX, int posY){
    	return map[posX][posY];
    }

public void setRegion(int mapID) {
	ID = mapID;
	switch(mapID){
	case 0 : clearMap(); break;
	case 1 : loadMap1(); break;
	case 2 : clearMap(); break;
	case 3 : clearMap(); break;
	case 4 : clearMap(); break;
	case 5 : clearMap(); break;
	case 6 : clearMap(); break;
	case 7 : clearMap(); break;
	case 8 : clearMap(); break;
	case 9 : clearMap(); break;
	case 10 : clearMap(); break;
	case 11 : clearMap(); break;
	case 12 : clearMap(); break;
	case 13 : clearMap(); break;
	case 14 : clearMap(); break;
	case 15 : clearMap(); break;
	case 16 : clearMap(); break;
	}
	
}


private void clearMap(){
	for (int a = 0; a < MAP_WIDTH; a++) {
	    for (int i = 0; i < MAP_HEIGHT; i++) {
     
        	if(i == 0 || a == 0 || i == MAP_HEIGHT-1 || a == MAP_WIDTH-1){
            map[a][i] = "FREE";
        	} else {
        	map[a][i] = "FREE";
        	}
        }
    }
}

private void loadMap1() {
			clearMap();	
	}


public int getRegionType() {
	return ID;
}



}
