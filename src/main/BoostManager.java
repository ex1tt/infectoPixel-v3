package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import entity.Boost;
import entity.SpeedBoost;

public class BoostManager {

    Panel gp;

    ArrayList<Boost> unactivatedBoosts;
    ArrayList<Boost> activatedBoosts;

    public BoostManager(Panel gp) {

        this.gp = gp;

        // This array list will hold the boosts currently on map
        unactivatedBoosts= new ArrayList<>();

        // This array list will hold the boosts currently active on the player
        activatedBoosts= new ArrayList<>();

        // Add a speed boost
        unactivatedBoosts.add(new SpeedBoost(gp, 2, 8));
    }

    // This checks for player collision of boosts currently on map
    public void checkForBoostCollision() {

        Iterator<Boost> iterator = unactivatedBoosts.iterator();

		while(iterator.hasNext()) {
			Boost boost = iterator.next();
	
			if(boost.colided) {
				iterator.remove();  // Safely remove boost from the array list
			}
            else {
                boost.update();
            }
		}
    }

    public void update() {

        checkForBoostCollision();
    }

    public void draw(Graphics2D g2) {

        // Loops through each boost currently on map and draws it...
        for(Boost boost : unactivatedBoosts) {
            boost.draw(g2);
        }
    }
}
