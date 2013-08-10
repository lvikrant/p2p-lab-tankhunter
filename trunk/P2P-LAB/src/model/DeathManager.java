package model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Timer;

import comparator.NTComparator;

import controller.GameController;

public class DeathManager implements ActionListener {
	
	private final GameController GC;
	
	Map<NetworkTarget, Timer> map = new TreeMap<NetworkTarget, Timer>(new NTComparator());
	
	public DeathManager(GameController gc){
		GC = gc;
	}

	public void destroy(NetworkTarget nt) {
		Timer temp = new Timer(2000,this);
		map.put(nt, temp);
		temp.start();
	}

	
	public boolean isDead(NetworkTarget nt){
		return map.containsKey(nt);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for (Iterator<Map.Entry<NetworkTarget, Timer>> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<NetworkTarget, Timer> entry = it.next();
			if (e.getSource() == entry.getValue()) {
				
				GC.addTankRandom(entry.getKey(), false);
				entry.getValue().stop();
				it.remove();
			}
		}
	}
}
