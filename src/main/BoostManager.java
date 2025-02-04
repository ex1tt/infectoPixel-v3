package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import entity.Boost;
import entity.SpeedBoost;
import entity.HealthBoost;

public class BoostManager {

    Panel gp;

    ArrayList<Boost> unactivatedBoosts;
    ArrayList<Boost> activatedBoosts;

    Random random;

    public BoostManager(Panel gp) {

        this.gp = gp;

        random = new Random();
    }

    public void generateNewBoosts() {

        // This array list will hold the boosts currently on map
        unactivatedBoosts= new ArrayList<>();

        // This array list will hold the boosts currently active on the player
        activatedBoosts= new ArrayList<>();

        unactivatedBoosts.add(new SpeedBoost(gp, random.nextInt(0, gp.WORLD_ROW), random.nextInt(0, gp.WORLD_COL)));
        unactivatedBoosts.add(new HealthBoost(gp, random.nextInt(0, gp.WORLD_ROW), random.nextInt(0, gp.WORLD_COL)));
    }

    // This checks for player collision of boosts currently on map
    public void checkForBoostCollision() {

        Iterator<Boost> iterator = unactivatedBoosts.iterator();

		while(iterator.hasNext()) {
			Boost boost = iterator.next();
	
			if(boost.colided) {
				iterator.remove();  // Safely remove boost from the array list
                // Add to activated boosts
                activatedBoosts.add(boost);
                boost.powerUp();
			}
            else {
                boost.update();
            }
		}
    }

    public void checkForBoostPowerDown() {

        Iterator<Boost> iterator = activatedBoosts.iterator();

        while(iterator.hasNext()) {
            Boost boost = iterator.next();

            if(boost.timer < boost.duration) {
                boost.timer ++;
            }
            else {
                boost.powerDown();
                iterator.remove();
            }
        }
    }

    public void update() {

        checkForBoostCollision();
        checkForBoostPowerDown();
    }

    public void draw(Graphics2D g2) {

        // Loops through each boost currently on map and draws it...
        for(Boost boost : unactivatedBoosts) {
            boost.draw(g2);
        }
    }
}
