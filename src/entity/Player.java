package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Panel;
import main.UtilityTool;

public class Player extends Entity {
	
	private final int MAX_HEALTH = 5;
	public int health;
	
	public BufferedImage current;
	public BufferedImage front;
	public BufferedImage behind;
	public BufferedImage left;
	public BufferedImage right;
	public BufferedImage front2;
	public BufferedImage behind2;
	public BufferedImage left2;
	public BufferedImage right2;
	public BufferedImage front3;
	public BufferedImage behind3;
	public BufferedImage left3;
	public BufferedImage right3;
	
	private int spriteNum = 1;
	public int spriteCounter = 1;

	private final int SPRITE_UPDATE_CYCLE = 20;

	public Player(Panel gp) { 
		
		super(gp);
		
		setImage();
		
		current = behind2;
		
		// Players screen coordinates (static)
		screenX = gp.SCREEN_WIDTH / 2;
		screenY = gp.SCREEN_HEIGHT /2;
		
		// Players starting world coordinates
		worldX = gp.TILE_SIZE * 10;
		worldY = gp.TILE_SIZE * 10;
		
		width = gp.TILE_SIZE;
		height = gp.TILE_SIZE;

		speed = 5;		
		// Defining player solid area
		solidArea = new Rectangle(gp.TILE_SIZE/4,gp.TILE_SIZE/4, width/2, (int) Math.round(height/2));		
		health = MAX_HEALTH;		
		
	}
	
	public void setImage() {
		
		front = UtilityTool.loadImage("res/playerSprites/player_front_static.png",  gp.TILE_SIZE);
		behind = UtilityTool.loadImage("res/playerSprites/player_behind_static.png",  gp.TILE_SIZE);
		left = UtilityTool.loadImage("res/playerSprites/player_left_static.png",  gp.TILE_SIZE);
		right = UtilityTool.loadImage("res/playerSprites/player_right_static.png",  gp.TILE_SIZE);
		
		front2 = UtilityTool.loadImage("res/playerSprites/player_front_left.png",  gp.TILE_SIZE);
		behind2 = UtilityTool.loadImage("res/playerSprites/player_behind_left.png",  gp.TILE_SIZE);
		left2 = UtilityTool.loadImage("res/playerSprites/player_left_left.png",  gp.TILE_SIZE);
		right2 = UtilityTool.loadImage("res/playerSprites/player_right_left.png",  gp.TILE_SIZE);
		
		front3 = UtilityTool.loadImage("res/playerSprites/player_front_right.png",  gp.TILE_SIZE);
		behind3 = UtilityTool.loadImage("res/playerSprites/player_behind_right.png",  gp.TILE_SIZE);
		left3 = UtilityTool.loadImage("res/playerSprites/player_left_right.png",  gp.TILE_SIZE);
		right3 = UtilityTool.loadImage("res/playerSprites/player_right_right.png",  gp.TILE_SIZE);

	}

	// Method to reset player stats in the event of a new wave.
	public void reset_stats(int wave) {

		health = MAX_HEALTH;	
		worldX = gp.TILE_SIZE * 10;
		worldY = gp.TILE_SIZE * 10;
		speed = speed + (0.1*wave);
	}
	
	// Method to damage player health
	public void damage() {
		
		health --;

		// This code needs to be changed....
		if(health == 0) System.exit(0);
	}
	
	public void update() {
		
		// Check for collision
		collision = (!isInScreenBounds()) || checkSolidCollision();

		// If any of these keys were pressed update sprite and move player
		if(gp.keyH.up || gp.keyH.down || gp.keyH.left || gp.keyH.right) {
			updateDirectionAndSprite();
			movePlayer();
		}

		updateSpriteAnimation();
	} 

	private void updateDirectionAndSprite() {

		if(gp.keyH.up) {

			// Set direction
			direction = "up";	
			current = (spriteNum == 1) ? behind2 : behind3;
		}
		if(gp.keyH.down) {

			// Set direction
			direction = "down";	
			current = (spriteNum == 1) ? front2 : front3;
		}
		if(gp.keyH.left) {

			// Set direction
			direction = "left";				
			current = (spriteNum == 1) ? left2 : left3;
		}
		if(gp.keyH.right) {

			// Set direction
			direction = "right";				
			current = (spriteNum == 1) ? right2 : right3;
		}
	}

	// Method to update x and y coords of player
	private void movePlayer() {

		if(!collision) {

			if(gp.keyH.up) {	
				worldY -= speed;
			}
			if(gp.keyH.down) {
				worldY += speed;
			}
			if(gp.keyH.left) {
				worldX -= speed;
			}
			if(gp.keyH.right) {
				worldX += speed;
			}
		}
	}

	// Update sprite animation after x amount of loops (currently 20)
	private void updateSpriteAnimation() {

		spriteCounter ++;

		if(spriteCounter > SPRITE_UPDATE_CYCLE) {

			spriteNum = (spriteNum == 1) ? 2 : 1;

			spriteCounter = 0;
		}
	}
	
	private Boolean checkSolidCollision() {		
		return (gp.cChecker.checkSolidCollision(this));
	}
	
	private boolean isInScreenBounds() {
		return (!gp.cChecker.checkScreenCollision(this));
	}
	
	public void draw(Graphics2D g2) {	
		g2.drawImage(current, screenX, screenY, null);
		//g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
	}
}
