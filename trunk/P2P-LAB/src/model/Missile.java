package model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import view.GameRegion;

import controller.GameController;


public class Missile implements ActionListener {
	
	boolean test = false;

	private final int ANGLE; // missile angle {0, 90, 180, 270}
	private final int MAP_HEIGHT;
	private final int MAP_WIDTH;
	
	private int posX = 0; // missile x-coordinate Position in fields
	private int posY = 0; // missile y-coordinate Position in fields


	private Timer move;  // timer for missile movement visualization
	private GameController gc;  // Game Controller
	private int range;
	private final NetworkTarget NT;
	
	


	public Missile(GameController parGC,NetworkTarget nt, Point pos,int parAngle, int parRange) {
		gc = parGC;
		ANGLE = parAngle;
		NT = nt;
		range = parRange;
		MAP_HEIGHT = gc.getMapHeight();
		MAP_WIDTH = gc.getMapWidth();
		posX = (int)pos.getX();
		posY = (int)pos.getY();
		gc.getMainRegion().addMissile(NT,new Point(posX,posY), ANGLE);
		move = new Timer(10, this);
		move.setInitialDelay(200);
		move.start();
	}
	
	public int getAngle(){
		return ANGLE;
	}
	
	public int getRange() {
		return range;
	}

	public int getPosX(){
		return posX;
	}
	

	public int getPosY(){
		return posY;
	}

	
	private void move(){
		move.stop();
		String nextField;
		switch (ANGLE) {
		case 0 :
			if(posX == 20){
				System.out.println("ENDE");
				
				//TODO move to the next Region
			} else {
				nextField = gc.getFieldInfo(posX+1, posY);
				
				/** Wenn nächstes Feld ROCK ist*/
				if (nextField.equals("ROCK")){
					gc.getMainRegion().explodeMissile(NT);	
				/** Wenn nächstes Feld TANK ist*/
				} else if(nextField.equals("TANK")){
				System.out.println("Destroy " + gc.getTank(new Point(posX+1,posY)).getName() + " At Position: " + (posX+1) + "|" + posY);
			    gc.destroyTank(gc.getTank(new Point(posX+1,posY)));
			    gc.getMainRegion().explodeMissile(NT);
				
				/** Wenn nächstes Feld weder ROCK noch TANK ist*/
				} else {
					gc.getMainRegion().moveMissile(NT);
					posX++;
					range--;
					move.start();
				}
			
			}
			break;
		case 90 :
			if(posY == 0){
				//TODO move Missile to the next Region
			} else {
				nextField = gc.getFieldInfo(posX, posY-1);
			
				if (nextField.equals("ROCK")){
					gc.getMainRegion().explodeMissile(NT);	
			
				} else if(nextField.equals("TANK")){
					
				    gc.destroyTank(gc.getTank(new Point(posX,posY-1)));
				    gc.getMainRegion().explodeMissile(NT);
			
				} else {
					gc.getMainRegion().moveMissile(NT);
					posY--;
					range--;
					move.start();
				}
			}
			break;
		case 180 :
			if(posX == 0){
				//TODO move to the next Region
			} else {
				nextField = gc.getFieldInfo(posX-1, posY);
			
				/** Wenn nächstes Feld ROCK ist*/
				if (nextField.equals("ROCK")){
					gc.getMainRegion().explodeMissile(NT);	
			
					/** Wenn nächstes Feld TANK ist*/
				} else if(nextField.equals("TANK")){
					
				    gc.destroyTank(gc.getTank(new Point(posX-1,posY)));
				    gc.getMainRegion().explodeMissile(NT);
			
				} else {
					gc.getMainRegion().moveMissile(NT);
					posX--;
					range--;
					move.start();
				}
			}
			break;
		case 270 :
			if(posY == 12){
				//TODO move Missile to the next Region
			} else {
				nextField = gc.getFieldInfo(posX, posY+1);
			

				if (nextField.equals("ROCK")){
					gc.getMainRegion().explodeMissile(NT);	
			

				} else if(nextField.equals("TANK")){
		
				    gc.destroyTank(gc.getTank(new Point(posX,posY+1)));
				    gc.getMainRegion().explodeMissile(NT);

				} else {
					gc.getMainRegion().moveMissile(NT);
					posY++;
					range--;
					move.start();
				}
			}
			break;
		}
}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == move) {
			move.stop();
			if(range > 0){
				move();
			} else {
				gc.getMainRegion().removeMissile(NT);
			}
			
		}
	}



}
