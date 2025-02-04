package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Panel;
import main.UtilityTool;

public class Bullet extends Entity {
	
	UtilityTool uTool;
	
	public boolean destroyBullet;
	public int bulletMaxDuration;
	public int bulletDurationCount;
	
	public BufferedImage currentImage;
	
	public BufferedImage upImage;
	public BufferedImage downImage;
	public BufferedImage leftImage;
	public BufferedImage rightImage;

	public Bullet(Panel gp, String playerDirection) {
		
		super(gp);
		
		setImage();
		
		direction = playerDirection;
		
		worldX = gp.player.worldX;
		worldY = gp.player.worldY;
	
		// Currently hard coded...
		speed = 35;
		solidArea = new Rectangle(gp.TILE_SIZE/3, gp.TILE_SIZE/3, gp.TILE_SIZE/3, gp.TILE_SIZE/3);
		
		bulletMaxDuration = 60;
	}
	
	public void setImage() {
		
		upImage = UtilityTool.loadImage("res/bulletSprites/bullet_up.png",  gp.TILE_SIZE);	
		downImage = UtilityTool.loadImage("res/bulletSprites/bullet_down.png",  gp.TILE_SIZE);	
		leftImage = UtilityTool.loadImage("res/bulletSprites/bullet_left.png",  gp.TILE_SIZE);	
		rightImage = UtilityTool.loadImage("res/bulletSprites/bullet_right.png",  gp.TILE_SIZE);
		
	}
	
	public void update() {
		
		// Conditional for destroying bullet
		if(bulletDurationCount > bulletMaxDuration || 
		gp.cChecker.checkScreenCollision(this) || 
		gp.cChecker.checkSolidCollision(this)) {
			destroyBullet = true;
			return;
		}

		// Handles bulled direction
		switch(direction) {
		case "up":
			currentImage = upImage;
			worldY -= speed;
			break;	
		case "down":
			currentImage = downImage;
			worldY += speed;
			break;	
		case "left":
			currentImage = leftImage;
			worldX -= speed;
			break;	
		case "right":
			currentImage = rightImage;
			worldX += speed;
			break;	
		}	
		// Update bullet duration count	
		bulletDurationCount ++;
	}
	
	public void draw(Graphics2D g2) {	
		
		// If tile is visible -> draw on screen
		if(gp.levelMng.isTileVisibleOnScreen(worldX, worldY)) {
			screenX = worldX - gp.player.worldX + gp.player.screenX;
			screenY = worldY - gp.player.worldY + gp.player.screenY;	
			
			g2.drawImage(currentImage, screenX, screenY, null);
		}	
	}
}