package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Panel;

public class Boost extends Entity {

	public boolean colided;
    public boolean active;
    public int duration;
    public int timer;
    public Color boostColor;

    public Boost(Panel gp) {

        super(gp);
    }    

    // Check if player has hit a speed boost
	private boolean playerHit() {
		return gp.cChecker.checkEntityCollision(this, gp.player);
	}


    // Each boost must have a custom powerUp and powerDown method
    public void powerUp() {}

    public void powerDown() {}

    public void update() {
        
        if(active) {
            if(timer < duration) {
                timer ++;
            }
            else {
                active = false;
                powerDown();
            }
        }

		if(playerHit() && !active) {
			// Double speed
			powerUp();
		}
	}

    public void draw(Graphics2D g2) {

		g2.setColor(boostColor);

		if(gp.levelMng.isTileVisibleOnScreen(worldX, worldY) && !active) {	
			
			screenX = worldX - gp.player.worldX + gp.player.screenX;
			screenY = worldY - gp.player.worldY + gp.player.screenY;
			g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);	
		}
    }
}