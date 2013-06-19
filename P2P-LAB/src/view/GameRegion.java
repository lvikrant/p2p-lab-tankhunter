package view;

import interfaces.GameVisualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import comparator.NTComparator;
import comparator.PointComparator;
import model.NetworkTarget;



public class GameRegion extends JPanel implements ActionListener,GameVisualisation{
	private static final long serialVersionUID = 1L;
	
	// green tank images
	private ImageIcon greenTankUpIcon = new ImageIcon("src/resources/greenTankUp.png");
	private ImageIcon greenTankDownIcon = new ImageIcon("src/resources/greenTankDown.png");
	private ImageIcon greenTankLeftIcon = new ImageIcon("src/resources/greenTankLeft.png");
	private ImageIcon greenTankRightIcon  = new ImageIcon("src/resources/greenTankRight.png"); 
	
	// red tank images
	private ImageIcon redTankUpIcon = new ImageIcon("src/resources/redTankUp.png");
	private ImageIcon redTankDownIcon = new ImageIcon("src/resources/redTankDown.png");
	private ImageIcon redTankLeftIcon = new ImageIcon("src/resources/redTankLeft.png");
	private ImageIcon redTankRightIcon = new ImageIcon("src/resources/redTankRight.png");
	
	// powerUp images
	private ImageIcon shieldUpIcon = new ImageIcon("src/resources/shield.png");
	private ImageIcon rateUpIcon = new ImageIcon("src/resources/rate.png");
	private ImageIcon speedUpIcon = new ImageIcon("src/resources/speed.png");
	private ImageIcon rangeUpIcon = new ImageIcon("src/resources/range.png");
	private ImageIcon slowDownIcon = new ImageIcon("src/resources/slow.png");
	

	// missile images
	private ImageIcon missileUpIcon = new ImageIcon("src/resources/rocketUp.png");
	private ImageIcon missileDownIcon = new ImageIcon("src/resources/rocketDown.png");
	private ImageIcon missileLeftIcon = new ImageIcon("src/resources/rocketLeft.png");
	private ImageIcon missileRightIcon = new ImageIcon("src/resources/rocketRight.png");
	
	// explosion image
	private ImageIcon exp = new ImageIcon("src/resources/exp.png");
	
	// image of a skull
	private ImageIcon skull = new ImageIcon("src/resources/skull.png");

        // this integer describes the step of tank visualization {0..16}
	
	private Image img;
	
	public GameWindow gameWindow;

	
	/**< POWERUP ********************************************************************************/
	Map<Point,JLabel> powerupMap= new TreeMap<Point, JLabel>(new PointComparator());
	
	Map<NetworkTarget,TankLabel> tankMap= new TreeMap<NetworkTarget, TankLabel>(new NTComparator());
	
	Map<NetworkTarget,MissileLabel> missileMap= new TreeMap<NetworkTarget, MissileLabel>(new NTComparator());
	
	
	
	  public GameRegion(String img, GameWindow gw, int region) {
		this(new ImageIcon(img).getImage(), gw, region);
		gameWindow = gw;
	  
		setVisible(true);
	
		
	  }


	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);

	  }
	

	  public GameRegion(Image img, GameWindow gw,int region) {
		gameWindow = gw;
	    this.img = img;
	    Dimension size = new Dimension(672,416);
	    setPreferredSize(size);
	    setSize(size);
	    setLayout(null);
	 
	  }
	  
	  
	   public void addPowerUp(Point point, String parPowerUp){
		   JLabel label = new JLabel();
		   label.setSize(32,32);
		   label.setLocation((int)point.getX()*32,(int)point.getY()*32);
		   
		   if(parPowerUp.equals("SHIELD")){
			   label.setIcon(shieldUpIcon);  
		   } else if(parPowerUp.equals("RATE")){
			   label.setIcon(rateUpIcon);  
		   } else if(parPowerUp.equals("SPEED")){
			   label.setIcon(speedUpIcon);  
		   }  else if(parPowerUp.equals("RANGE")){
			   label.setIcon(rangeUpIcon);  
		   }  else if(parPowerUp.equals("SLOW")){
			   label.setIcon(slowDownIcon);  
		   }
		   add(label);
		   setVisible(false);
		   setVisible(true);
		   powerupMap.put(point, label);

	   }
    
	   public void remove(Point point){
		   powerupMap.get(point).setVisible(false);
		   remove(powerupMap.get(point));
		   powerupMap.remove(point);
	   }
     
	   public void addTank(NetworkTarget nt,Point point, int angle){
		   TankLabel tl = new TankLabel(point,this);
		   tankMap.put(nt, tl);
		   rotateTank(nt, angle);
		   add(tl.getLabel());
		   setVisible(false);
		   setVisible(true);
	   }
	   
	   public void rotateTank(NetworkTarget nt, int angle){
		   TankLabel tl = tankMap.get(nt);
		   
		   if(nt.getName().equals(gameWindow.getPlayerName())){
			   switch(angle){
			   case 0  : tl.setIcon(greenTankRightIcon); 
			             tl.setAngle(0);   
			             break; 
			   case 90 : tl.setIcon(greenTankUpIcon); 
			   			 tl.setAngle(90);  
			   			 break;
			   case 180: tl.setIcon(greenTankLeftIcon); 
			   			 tl.setAngle(180);  
			             break;
			   case 270: tl.setIcon(greenTankDownIcon);
			   			 tl.setAngle(270);  
	                     break;
			   }
		   } else {
			   switch(angle){
			   case 0  : tl.setIcon(redTankRightIcon);
	                     tl.setAngle(0);   
	                     break; 
			   case 90 : tl.setIcon(redTankUpIcon);  
                         tl.setAngle(90);   
                         break; 
			   case 180: tl.setIcon(redTankLeftIcon);
			   			 tl.setAngle(180);   
			   			 break; 
			   case 270: tl.setIcon(redTankDownIcon);
               			 tl.setAngle(270);   
               			 break; 
			   }
		   }
	   }
	   
	   public void moveTank(NetworkTarget nt, int angle){
		   	   TankLabel tl = tankMap.get(nt);
		   	   tl.setAngle(angle);
		   	   tl.setWay(16);
			   tl.getMoveTimer().start();

	   }
	   

	   
	   public void removeTank(NetworkTarget nt){
		   TankLabel tl = tankMap.get(nt);	   
		   tl.getLabel().setVisible(false);
		   remove(tl.getLabel());
		   tankMap.remove(nt);
	   }
	   
	    private void animateTankMovement(NetworkTarget nt){
	    	TankLabel tl = tankMap.get(nt);
	    	if(tl.getWay() > 0){
	    	
	    	switch (tl.getAngle()) {
			case 0:
		   	    tl.movePosX(2);
		   	    movePanels(0);	   	    
		    	break;
			case 90:
				tl.movePosY(-2);
				movePanels(90);	 
				break;
			case 180:
				tl.movePosX(-2);
				movePanels(180);	 
				break;
			case 270:
				tl.movePosY(2);
				movePanels(270);	 
				break;
			}
	    	tl.decWay();
	    	 } else {
	    		tl.setWay(16);
	    		tl.getMoveTimer().stop();
	    	 }
			
	    }
	   
	   
	   public void destroyTank(NetworkTarget nt){
		   tankMap.get(nt).setDead(true);
		   tankMap.get(nt).getDestroyTimer().start();
	   }

	   public void addMissile(NetworkTarget nt,Point point, int angle){
		   MissileLabel ml = new MissileLabel(point,angle, this);
		   missileMap.put(nt, ml);
		   switch(angle){
		   case 0: ml.setIcon(missileRightIcon);  
		  		   ml.setLocation((int)point.getX()*32+16,(int)point.getY()*32); 
				   break; 
		   case 90: ml.setIcon(missileUpIcon); 
		  			ml.setLocation((int)point.getX()*32+13,(int)point.getY()*32-6);
		   			break;
		   case 180: ml.setIcon(missileLeftIcon);
		   			ml.setLocation((int)point.getX()*32+3,(int)point.getY()*32);
		   			break;
		   case 270: ml.setIcon(missileDownIcon);
		   			ml.setLocation((int)point.getX()*32+12,(int)point.getY()*32+3);
		   			break;
		   }
		   ml.getLabel().setVisible(true);
		   add(ml.getLabel());
	   }
	   
	   
	   public void moveMissile(NetworkTarget nt){
		   missileMap.get(nt).getMoveTimer().start();
	   } 
	   
	   public void showExplosion(NetworkTarget nt){
		   MissileLabel ml = missileMap.get(nt);
		   ml.setIcon(exp);
		   switch(ml.getAngle()) {
		   		case 0: ml.setLocation(ml.getX()-5, ml.getY()); break;
		   		case 90: ml.setLocation(ml.getX()-10, ml.getY()); break;
		   		case 180:ml.setLocation(ml.getX()-5, ml.getY()); break;
		   		case 270: ml.setLocation(ml.getX()-10, ml.getY()+4); break;
		   }
		   
		
	   }
	   
	   public void animateMissileMovement(NetworkTarget nt){
		   MissileLabel ml = missileMap.get(nt);
		   
		   if(ml.isExploded() && ml.getFly() < 6){
			   
			   if(ml.getFly() == 5){
				   showExplosion(nt);   
			   }
			   
			   if(ml.getFly() == 0){
				   ml.getMoveTimer().stop();
				   removeMissile(nt);
			   } 
			   ml.decFly();
			   
		   }else{
			   
		   if(ml.getFly() > 0){
				switch (ml.getAngle()) {
				case 0:
			   	    ml.movePosX(2);    
			    	break;
				case 90:
					ml.movePosY(-2);  	 
					break;
				case 180:
					 ml.movePosX(-2); 	 
					break;
				case 270:
					 ml.movePosY(2); 	 
					break;
					
				}
				ml.decFly();
		    	 } else {
		    		 ml.setFly(16);
		    		ml.getMoveTimer().stop();
		    	 }
		   }
				
	   }
	   
	   public void removeMissile(NetworkTarget nt){
		   JLabel label = missileMap.get(nt).getLabel();
		   label.setVisible(false);
		   remove(label);
		   missileMap.remove(nt);
	   }
	   
		public void explodeMissile(NetworkTarget nt) {
			MissileLabel ml = missileMap.get(nt);
			ml.setExploded(true);
			ml.setFly(16);
			ml.getMoveTimer().start();
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for (Map.Entry<NetworkTarget, TankLabel> entry : tankMap.entrySet()){
			if (e.getSource() == entry.getValue().getMoveTimer()) {
				animateTankMovement(entry.getKey());
				return;
		    }
		}

		
		for (Map.Entry<NetworkTarget, MissileLabel> entry : missileMap.entrySet()){
			if (e.getSource() == entry.getValue().getMoveTimer()) {
				animateMissileMovement(entry.getKey());
				return;
		    }
		}

	for (Map.Entry<NetworkTarget, TankLabel> entry : tankMap.entrySet()){
		if (e.getSource() == entry.getValue().getDestroyTimer()) {
			if(entry.getValue().isDead()){
				entry.getValue().setDead(false);
				entry.getValue().setIcon(skull);
			} else {
                entry.getValue().getDestroyTimer().stop();
                entry.getValue().getLabel().setVisible(false);
     		    remove(entry.getValue().getLabel());
     		    tankMap.remove(entry.getKey());
			}
			return;
	    }
	}
	}
	
	
	public void movePanels(int angle){
	   gameWindow.movePanels(angle);
	}


	public void enterRegion(NetworkTarget nt, Point pos, int angle) {
		TankLabel tl = null;
		switch(angle){
			case 0: 
				tl = new TankLabel(new Point((int)pos.getX()-1,(int)pos.getY()),this);
				break;
		   }
		   tankMap.put(nt, tl);
		   rotateTank(nt, angle);
		   add(tl.getLabel());
		   setVisible(false);
		   setVisible(true);
		   moveTank(nt, angle);

	}

}
