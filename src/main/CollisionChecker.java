package main;

import java.awt.Rectangle;

import entity.Entity;

public class CollisionChecker {
	
	Panel gp;
	
	public CollisionChecker(Panel gp) {
		
		this.gp = gp;
	}

	public boolean checkEntityCollision(Entity entity1, Entity entity2) {
		// Clone solid areas to avoid modifying original ones
		Rectangle solidArea1 = new Rectangle(entity1.solidArea);
		Rectangle solidArea2 = new Rectangle(entity2.solidArea);
	
		// Offset solid areas by world positions
		solidArea1.x += entity1.worldX;
		solidArea1.y += entity1.worldY;
	
		solidArea2.x += entity2.worldX;
		solidArea2.y += entity2.worldY;

		// Check for collision using intersects
		return solidArea1.intersects(solidArea2);
	}
	
	public boolean checkScreenCollision(Entity entity) {
		
		switch(entity.direction) {
		
        case "up":
           return (entity.worldY <=0);
        case "down":
           return (entity.worldY >= gp.WORLD_HEIGHT-gp.TILE_SIZE);
        case "left":
           return (entity.worldX <= 0);
        case "right":
            return(entity.worldX >= gp.WORLD_WIDTH-gp.TILE_SIZE);
		}
    
    return false;
    
	}
	
	// Review this method?
	public Boolean checkSolidCollision(Entity entity) {

		int leftX = entity.worldX + entity.solidArea.x; 
	    int rightX = leftX + entity.solidArea.width;
	    int topY = entity.worldY + entity.solidArea.y;
	    int bottomY = topY + entity.solidArea.height;
		
		int playerLeftRow = leftX/gp.TILE_SIZE;
		int playerRightRow = rightX/gp.TILE_SIZE;
		int playerTopCol = topY/gp.TILE_SIZE;
		int playerBottomCol = bottomY/gp.TILE_SIZE;
		
		boolean[][] solidCoords = gp.levelMng.staticObstacleCoords;

		boolean collision = false;
		
		switch (entity.direction) {
			case "up":
				if (solidCoords[playerLeftRow][playerTopCol] || solidCoords[playerRightRow][playerTopCol]) {
					entity.worldY += entity.speed; // Push player down slightly to allow sliding
					collision = true;
				}
				break;
			case "down":
				if (solidCoords[playerLeftRow][playerBottomCol] || solidCoords[playerRightRow][playerBottomCol]) {
					entity.worldY -= entity.speed; // Push player up slightly
					collision = true;
				}
				break;
			case "left":
				if (solidCoords[playerLeftRow][playerTopCol] || solidCoords[playerLeftRow][playerBottomCol]) {
					entity.worldX += entity.speed; // Push player right slightly
					collision = true;
				}
				break;
			case "right":
				if (solidCoords[playerRightRow][playerTopCol] || solidCoords[playerRightRow][playerBottomCol]) {
					entity.worldX -= entity.speed; // Push player left slightly
					collision = true;
				}
				break;
		}
	
		return collision;
	}	
}