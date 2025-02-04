package entity;

import java.awt.Rectangle;
import java.awt.Color;

import main.Panel;

public class SpeedBoost extends Boost {

    public SpeedBoost(Panel gp, int worldRow, int worldCol) {

        super(gp);
		
		// Boost's world coordinates
		worldX = gp.TILE_SIZE * worldRow;
		worldY = gp.TILE_SIZE * worldCol;
		
		width = gp.TILE_SIZE;
		height = gp.TILE_SIZE;

		duration = 500;
		boostColor = Color.yellow;

        solidArea = new Rectangle(gp.TILE_SIZE/4,gp.TILE_SIZE/4, width/2, (int) Math.round(height/2));		

		setImage("res/boostSprites/speedBoost.png");
    }    

	// This is distinct to each boost
	@Override
	public void powerUp() {
		gp.player.speed *=2;
	}

	@Override
    public void powerDown() {
        gp.player.speed = 5;
    }
}