package main;

import java.awt.Rectangle;

import entity.Entity;

public class CollisionChecker {
	
	Panel gp;
	
	public CollisionChecker(Panel gp) {
		
		this.gp = gp;
	}

	public boolean checkEntityCollision(Entity entity1, Entity entity2) {
		
	    Rectangle solidArea1 = entity1.solidArea;	// Entity Colliding
	    Rectangle solidArea2 = entity2.solidArea;	// Entity Being Collided

	    int entity1LeftX = entity1.worldX + solidArea1.x;
	    int entity1RightX = entity1LeftX + solidArea1.width;
	    int entity1TopY = entity1.worldY + solidArea1.y;
	    int entity1BottomY = entity1TopY + solidArea1.height;

	    int entity2LeftX = entity2.worldX + solidArea2.x;
	    int entity2RightX = entity2LeftX + solidArea2.width;
	    int entity2TopY = entity2.worldY + solidArea2.y;
	    int entity2BottomY = entity2TopY + solidArea2.height;

	    switch(entity1.direction) {
	        case "up":
	            return (entity1LeftX <= entity2RightX && entity1RightX >= entity2LeftX) &&
	                   (entity1TopY <= entity2BottomY && entity1TopY >= entity2TopY);
	        case "down":
	            return (entity1LeftX <= entity2RightX && entity1RightX >= entity2LeftX) &&
	                   (entity1BottomY >= entity2TopY && entity1BottomY <= entity2BottomY);
	        case "left":
	            return (entity1TopY <= entity2BottomY && entity1BottomY >= entity2TopY) &&
	                   (entity1LeftX <= entity2RightX && entity1LeftX >= entity2LeftX);
	        case "right":
	            return (entity1TopY <= entity2BottomY && entity1BottomY >= entity2TopY) &&
	                   (entity1RightX >= entity2LeftX && entity1RightX <= entity2RightX);
	    }
	    
	    return false;
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
	
	public boolean checkSolidCollision(Entity entity) {

		int leftX = entity.worldX + entity.solidArea.x;
	    int rightX = leftX + entity.solidArea.width;
	    int topY = entity.worldY + entity.solidArea.y;
	    int bottomY = topY + entity.solidArea.height;
		
		int playerLeftRow = leftX/gp.TILE_SIZE;
		int playerRightRow = rightX/gp.TILE_SIZE;
		int playerTopCol = topY/gp.TILE_SIZE;
		int playerBottomCol = bottomY/gp.TILE_SIZE;
		
		boolean[][] solidCoords = gp.levelMng.staticObstacleCoords;
		
		switch(entity.direction) {
		case "up":			
			if(solidCoords[playerLeftRow][playerTopCol]||solidCoords[playerRightRow][playerTopCol]) {
				return true;
			}
			break;
		case "down":
			if(solidCoords[playerLeftRow][playerBottomCol]||solidCoords[playerRightRow][playerBottomCol]) {
				return true;
			}
			break;
		case "left":
			if(solidCoords[playerLeftRow][playerTopCol]||solidCoords[playerLeftRow][playerBottomCol]) {
				return true;
			}
			break;
		case "right":
			if(solidCoords[playerRightRow][playerTopCol]||solidCoords[playerRightRow][playerBottomCol]) {
				return true;
			}
			break;
		}
		
		return false;		
		}
	}	

