package entity;

import java.awt.Color;
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
	
	public BufferedImage current;
	
	public BufferedImage up;
	public BufferedImage down;
	public BufferedImage left;
	public BufferedImage right;

	public Bullet(Panel gp, String playerDirection) {
		
		super(gp);
		
		setImage();
		
		direction = playerDirection;
		
		worldX = gp.player.worldX;
		worldY = gp.player.worldY;
	
		speed = 35;
		solidArea = new Rectangle(gp.TILE_SIZE/3, gp.TILE_SIZE/3, gp.TILE_SIZE/3, gp.TILE_SIZE/3);
		color = Color.black;
		
		bulletMaxDuration = 60;
	}
	
	public void setImage() {
		
		uTool = new UtilityTool();
		
		up = uTool.loadImage("/bulletSprites/bullet_up.png",  gp.TILE_SIZE);	
		down = uTool.loadImage("/bulletSprites/bullet_down.png",  gp.TILE_SIZE);	
		left = uTool.loadImage("/bulletSprites/bullet_left.png",  gp.TILE_SIZE);	
		right = uTool.loadImage("/bulletSprites/bullet_right.png",  gp.TILE_SIZE);
		
	}
	
	public void update() {
		
		if(bulletDurationCount > bulletMaxDuration || gp.cChecker.checkScreenCollision(this) || gp.cChecker.checkSolidCollision(this)) {	// Destroys bullet after (bulletMaxDuration) frames
			destroyBullet = true;
			return;
		}
		switch(direction) {
		case "up":
			current = up;
			worldY -= speed;
			break;	
		case "down":
			current = down;
			worldY += speed;
			break;	
		case "left":
			current = left;
			worldX -= speed;
			break;	
		case "right":
			current = right;
			worldX += speed;
			break;	
		}		
		bulletDurationCount ++;
	}
	
	public void draw(Graphics2D g2) {	
		
		if(gp.levelMng.isTileVisibleOnScreen(worldX, worldY)) {
			screenX = worldX - gp.player.worldX + gp.player.screenX;
			screenY = worldY - gp.player.worldY + gp.player.screenY;	
			
			g2.drawImage(current, screenX, screenY, null);
		}	
	}
}
