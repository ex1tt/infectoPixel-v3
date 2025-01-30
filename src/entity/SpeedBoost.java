package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

import main.Panel;

public class SpeedBoost extends Boost {

    public SpeedBoost(Panel gp, int worldRow, int worldCol) {

        super(gp);
		
		// Players starting world coordinates
		worldX = gp.TILE_SIZE * worldRow;
		worldY = gp.TILE_SIZE * worldCol;
		
		width = gp.TILE_SIZE;
		height = gp.TILE_SIZE;

		colided = false;
		active = false;
		duration = 100;

        solidArea = new Rectangle(gp.TILE_SIZE/4,gp.TILE_SIZE/4, width/2, (int) Math.round(height/2));		
    }    

	// Check if player has hit a speed boost
	private boolean playerHit() {
		return gp.cChecker.checkEntityCollision(this, gp.player);
	}

	@Override
	public void update() {

		if(playerHit() && !colided) {
			// Double speed
			gp.player.speed *= 2;
			colided = true;
		}
	}

	@Override
    public void draw(Graphics2D g2) {

		// Set boost to yellow
		g2.setColor(Color.yellow);

		if(gp.levelMng.isTileVisibleOnScreen(worldX, worldY)) {	
			
			screenX = worldX - gp.player.worldX + gp.player.screenX;
			screenY = worldY - gp.player.worldY + gp.player.screenY;
			g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);	
		}
    }
}