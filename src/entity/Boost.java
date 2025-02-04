package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Panel;
import main.UtilityTool;

public class Boost extends Entity {

	public boolean colided = false;
    public int duration;
    public int timer;
    public Color boostColor;
    public BufferedImage image;

    public UtilityTool uTool;

    public Boost(Panel gp) {

        super(gp);
    }    

    public void setImage(String pathName) {

        uTool = new UtilityTool();

        image = UtilityTool.loadImage(pathName,  gp.TILE_SIZE);
    }

    // Check if player has hit a speed boost
	private boolean playerHit() {
		return gp.cChecker.checkEntityCollision(this, gp.player);
	}

    // Each boost must have a custom powerUp and powerDown method
    public void powerUp() {}

    public void powerDown() {}

    public void update() {

        if(playerHit()) {
            colided = true;
        }
	}

    public void draw(Graphics2D g2) {

		g2.setColor(boostColor);

		if(gp.levelMng.isTileVisibleOnScreen(worldX, worldY)) {	
			
			screenX = worldX - gp.player.worldX + gp.player.screenX;
			screenY = worldY - gp.player.worldY + gp.player.screenY;
			//g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);	
            g2.drawImage(image, screenX, screenY, null);
		}
    }
}