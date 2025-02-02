package entity;
import java.awt.Rectangle;
import java.awt.Color;

import main.Panel;

public class HealthBoost extends Boost {

    public HealthBoost(Panel gp, int worldRow, int worldCol) {

        super(gp);

		// Boost's world coordinates
		worldX = gp.TILE_SIZE * worldRow;
		worldY = gp.TILE_SIZE * worldCol;
		
		width = gp.TILE_SIZE;
		height = gp.TILE_SIZE;

		colided = false;
		active = false;
        boostColor = Color.red;

        solidArea = new Rectangle(gp.TILE_SIZE/4,gp.TILE_SIZE/4, width/2, (int) Math.round(height/2));		
    }    

    @Override
    public void powerUp() {

        // Double health
        gp.player.health *= 2;
        colided = true;
    }
}