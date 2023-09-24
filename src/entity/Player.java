package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Panel;
import main.UtilityTool;

public class Player extends Entity {
	
	private final int MAX_HEALTH = 5;
	public int health;
	
	UtilityTool uTool;
	
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

	public Player(Panel gp) { 
		
		super(gp);
		
		setImage();
		
		current = behind2;
		
		screenX = gp.SCREEN_WIDTH / 2;
		screenY = gp.SCREEN_HEIGHT /2;
		
		worldX = gp.TILE_SIZE * 10;
		worldY = gp.TILE_SIZE * 10;
		
		width = gp.TILE_SIZE;
		height = gp.TILE_SIZE;
		
		speed = 4;		
		solidArea = new Rectangle(20,20, width/2-10, height/2-10);		
		color = Color.red;
		health = MAX_HEALTH;		
		
	}
	
	public void setImage() {
		
		uTool = new UtilityTool();
		
		front = uTool.loadImage("/playerSprites/player_front_static.png",  gp.TILE_SIZE);
		behind = uTool.loadImage("/playerSprites/player_behind_static.png",  gp.TILE_SIZE);
		left = uTool.loadImage("/playerSprites/player_left_static.png",  gp.TILE_SIZE);
		right = uTool.loadImage("/playerSprites/player_right_static.png",  gp.TILE_SIZE);
		
		front2 = uTool.loadImage("/playerSprites/player_front_left.png",  gp.TILE_SIZE);
		behind2 = uTool.loadImage("/playerSprites/player_behind_left.png",  gp.TILE_SIZE);
		left2 = uTool.loadImage("/playerSprites/player_left_left.png",  gp.TILE_SIZE);
		right2 = uTool.loadImage("/playerSprites/player_right_left.png",  gp.TILE_SIZE);
		
		front3 = uTool.loadImage("/playerSprites/player_front_right.png",  gp.TILE_SIZE);
		behind3 = uTool.loadImage("/playerSprites/player_behind_right.png",  gp.TILE_SIZE);
		left3 = uTool.loadImage("/playerSprites/player_left_right.png",  gp.TILE_SIZE);
		right3 = uTool.loadImage("/playerSprites/player_right_right.png",  gp.TILE_SIZE);
		

	}
	
	public void damage() {
		
		health -=1;
		
		switch(health) {
		case 2 :
			color = Color.LIGHT_GRAY;
			break;
		case 1:
			color = Color.DARK_GRAY;
			break;
		case 0:
			color = Color.BLACK;
			System.exit(0);
			break;
		}		
	}
	
	public void update() {
		
		checkCollision();
			
		if(gp.keyH.up) {
			
			if(spriteNum == 1) {
				current = behind2;
			}
			else {
				current = behind3;
			}
			
			direction = "up";	
			if(!collision) {
				worldY -= speed;
			}
		}
		if(gp.keyH.down) {
			
			if(spriteNum == 1) {
				current = front2;
			}
			else {
				current = front3;
			}
			
			direction = "down";	
			if(!collision) {
				worldY += speed;
			}
		}
		if(gp.keyH.left) {
			
			if(spriteNum == 1) {
				current = left2;
			}
			else {
				current = left3;
			}
			
			direction = "left";
			if(!collision) {
				worldX -= speed;
			}
		}
		if(gp.keyH.right) {
			
			if(spriteNum == 1) {
				current = right2;
			}
			else {
				current = right3;
			}
			
			direction = "right";
			if(!collision) {
				worldX += speed;
			}
		}
		
		spriteCounter +=1;
		if(spriteCounter >20) {
			if(spriteNum == 1) {
				spriteNum =2;
			}
			else {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	} 
	
	private void checkCollision() {		
		collision = (!isInScreenBounds() || gp.cChecker.checkSolidCollision(this));
	}
	
	private boolean isInScreenBounds() {
		return (!gp.cChecker.checkScreenCollision(this));
	}
	
	public void draw(Graphics2D g2) {	
		g2.drawImage(current, screenX, screenY, null);
	}
}
