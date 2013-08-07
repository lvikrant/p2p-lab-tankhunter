package model;

import controller.GameController;

public class RegionTypes {
	
	private final int MAP_WIDTH;
	private final int MAP_HEIGHT;
	private String[][] map;
	private int id;

public RegionTypes(GameController gc, int mapID) {
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
	System.err.println("SET REGION " + mapID);
	id = mapID;
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
			
     map[3][2] = "ROCK";
     map[2][3] = "ROCK";
     
     map[11][3] = "ROCK";
     
     map[4][4] = "ROCK";
     map[7][4] = "ROCK";
     map[13][4] = "ROCK";
     
     map[4][5] = "ROCK";
     map[9][5] = "ROCK";
     
     map[7][6] = "ROCK";
     map[12][6] = "ROCK";
     map[13][6] = "ROCK";
     
     map[3][7] = "ROCK";
     
     map[13][8] = "ROCK";
     
     map[5][9] = "ROCK";
     map[6][9] = "ROCK";
     map[16][9] = "ROCK";
     
     map[2][10] = "ROCK";
     map[8][10] = "ROCK";
     map[11][10] = "ROCK";
     map[18][10] = "ROCK";
		
	}


public int getRegionType() {
	return id;
}

public void printRegioType(){
	
	System.out.println("RegionType : " + id);
	for (int i = 0; i < MAP_HEIGHT; i++) {	
		for (int a = 0; a < MAP_WIDTH; a++) {
	    
     
        	if(map[a][i].equals("FREE")){
        		System.out.print("_");
        	} else {
        		System.out.print("X");
        	}
        }
	    System.out.println();
    }
}

}
