package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Panel;
import main.UtilityTool;
import pathFinding.PathFinder;

public class Zombie extends Entity {
	
	private PathFinder pathFinder;
	private Random random;

	private final int ZOMBIE_RELOAD_TIME = 60;
	private int zombieReloadCount;
	
	public boolean kill;
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
	
	public Zombie(Panel gp, int x, int y, boolean[][] obstacleCoords) {
		
		super(gp);
		
		setImage();
		
		// addition of 10 offset for directionChecking
		worldX = x+10;
		worldY = y+10;
		
		width = gp.TILE_SIZE;
		height = gp.TILE_SIZE;
		
		solidArea = new Rectangle(1, 1, gp.TILE_SIZE-20, gp.TILE_SIZE-20);
		
		color = new Color(124,252,0);
		
		random = new Random();
		
		speed = random.nextDouble(2.5, 3);
		health = 3;		
		direction = "";
		
		zombieReloadCount = ZOMBIE_RELOAD_TIME;

		pathFinder = new PathFinder(gp, obstacleCoords);
		
	}
	
	public void setImage() {
		
		uTool = new UtilityTool();
		
		front = uTool.loadImage("/zombieSprites/zombie_grey_front_static.png",  gp.TILE_SIZE);
		behind = uTool.loadImage("/zombieSprites/zombie_grey_behind_static.png",  gp.TILE_SIZE);
		left = uTool.loadImage("/zombieSprites/zombie_grey_left_static.png",  gp.TILE_SIZE);
		right = uTool.loadImage("/zombieSprites/zombie_grey_right_static.png",  gp.TILE_SIZE);
		
		front2 = uTool.loadImage("/zombieSprites/zombie_grey_front_left.png",  gp.TILE_SIZE);
		behind2 = uTool.loadImage("/zombieSprites/zombie_grey_behind_left.png",  gp.TILE_SIZE);
		left2 = uTool.loadImage("/zombieSprites/zombie_grey_left_left.png",  gp.TILE_SIZE);
		right2 = uTool.loadImage("/zombieSprites/zombie_grey_right_left.png",  gp.TILE_SIZE);
		
		front3 = uTool.loadImage("/zombieSprites/zombie_grey_front_right.png",  gp.TILE_SIZE);
		behind3 = uTool.loadImage("/zombieSprites/zombie_grey_behind_right.png",  gp.TILE_SIZE);
		left3 = uTool.loadImage("/zombieSprites/zombie_grey_left_right.png",  gp.TILE_SIZE);
		right3 = uTool.loadImage("/zombieSprites/zombie_grey_right_right.png",  gp.TILE_SIZE);

	}
	
	public void damage() {
		
		current = null;
		
		health -=1;
		
		switch(health) {
		case 2 :
			color = new Color(50,205,50);
			break;
		case 1:
			color = new Color(0,100,0);
			break;
		case 0:
			color = new Color(0,100,0);
			kill = true;
			break;
		}	
	}
	
	public void attack() {
		
		if(canAttack()) {
			gp.player.damage();
			zombieReloadCount = 0;
		}
	}
	
	private boolean canAttack() {		
        return zombieReloadCount >= ZOMBIE_RELOAD_TIME;
    }
	
	private void moveInDirection(PathFinder pf, int zx, int zy) {
		
		direction = getDirection(pf, zx, zy);
		switch(direction) {
			case "up":	
				if(spriteNum == 1) {
					current = behind2;
				}
				else {
					current = behind3;
				}

				worldY -= (speed-1);
				break;
			case "down":
				if(spriteNum == 1) {
					current = front2;
				}
				else {
					current = front3;
				}

				worldY += speed;
				break;
			case "left":
				if(spriteNum == 1) {
					current = left2;
				}
				else {
					current = left3;
				}

				worldX -= (speed-1);
				break;
			case "right":
				if(spriteNum == 1) {
					current = right2;
				}
				else {
					current = right3;
				}
	
				worldX += speed;
				break;
		}
		
		spriteCounter +=1;
		if(spriteCounter >20 * speed) {
			if(spriteNum == 1) {
				spriteNum =2;
			}
			else {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	private String getDirection(PathFinder pf, int x, int y) {
		
	    int leftX = x + solidArea.x;
	    int rightX = leftX + solidArea.width;
	    int topY = y + solidArea.y;
	    int bottomY = topY + solidArea.height;  // Fixed this line

	    if (pf.pathNodes.isEmpty()) {
	        return "";
	    }
	    
	    int nextX = pf.pathNodes.get(pf.pathNodes.size() - 1).row * gp.TILE_SIZE;
        int nextY = pf.pathNodes.get(pf.pathNodes.size() - 1).col * gp.TILE_SIZE;
   
	    switch(direction) {
	    case "up":
	    	if(checkUp(nextX, nextY, rightX, leftX, topY)) {
	    		return "up";
	 		}
	    case "down":
	    	if(checkDown(nextX, nextY, rightX, leftX, topY)) {
	    		return "down";
	 		}
	    case "left":
	    	if(checkLeft(nextX, nextY, leftX, bottomY, topY)) {
	    		return "left";
	 		}
	    case "right":
	    	if(checkRight(nextX, nextY, leftX, bottomY, topY)) {
	    		return "right";
	 		}
	    }   
	    
	    if(checkUp(nextX, nextY, rightX, leftX, topY)) {
	    	return "up";
 		}
	    if(checkDown(nextX, nextY, rightX, leftX, topY)) {
	    	return "down";
 		}
	    if(checkLeft(nextX, nextY, leftX, bottomY, topY)) {
    		return "left";
 		}
	    if(checkRight(nextX, nextY, leftX, bottomY, topY)) {
    		return "right";
 		}
		return direction;
	}
	
	private boolean checkUp(int nextX, int nextY, int rightX, int leftX, int topY) {		
		return (leftX >= nextX && rightX <= nextX + gp.TILE_SIZE && topY >= nextY);
	}
	
	private boolean checkDown(int nextX, int nextY, int rightX, int leftX, int topY) {
		return (leftX >= nextX && rightX <= nextX + gp.TILE_SIZE && topY <= nextY);
	}

	private boolean checkLeft(int nextX, int nextY, int leftX, int bottomY, int topY) {
		return (topY >= nextY && bottomY <= nextY + gp.TILE_SIZE && leftX >= nextX);
	}

	private boolean checkRight(int nextX, int nextY, int leftX, int bottomY, int topY) {
		return (topY >= nextY && bottomY <= nextY + gp.TILE_SIZE && leftX <= nextX);
	}
	
	public void update() {

		if(!collision) {
			pathFinder.updatePath(worldX,worldY, (gp.player.worldX + gp.player.width/2), (gp.player.worldY + gp.player.height/2));
			moveInDirection(pathFinder, worldX, worldY);
		}
		
		zombieReloadCount ++;
	}

	public void draw(Graphics2D g2) {
		
		if(gp.levelMng.isTileVisibleOnScreen(worldX, worldY)) {	
			
			screenX = worldX - gp.player.worldX + gp.player.screenX;
			screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			g2.drawImage(current, screenX, screenY, null);
		}
	}	
}