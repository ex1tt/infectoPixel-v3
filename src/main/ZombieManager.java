package main;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.Zombie;

public class ZombieManager {
	
	Panel gp;
	public ArrayList<Zombie> zombies;
	
	// Needs to be changed to incorporate waves
	public ZombieManager(Panel gp) {
		
		this.gp = gp;	
		
		// This keeps a track of current live zombies...
		zombies = new ArrayList<>();	
		
		zombies.add(new Zombie(gp, 4*gp.TILE_SIZE, 6*gp.TILE_SIZE, gp.levelMng.staticObstacleCoords));
		zombies.add(new Zombie(gp, 12*gp.TILE_SIZE, 10*gp.TILE_SIZE, gp.levelMng.staticObstacleCoords));
		zombies.add(new Zombie(gp, 6*gp.TILE_SIZE, 2*gp.TILE_SIZE, gp.levelMng.staticObstacleCoords));
		zombies.add(new Zombie(gp, 18*gp.TILE_SIZE, 8*gp.TILE_SIZE, gp.levelMng.staticObstacleCoords));
		zombies.add(new Zombie(gp, 28*gp.TILE_SIZE, 1*gp.TILE_SIZE, gp.levelMng.staticObstacleCoords));
		zombies.add(new Zombie(gp, 33*gp.TILE_SIZE, 9*gp.TILE_SIZE, gp.levelMng.staticObstacleCoords));
	}
	
	private void checkForDeadZombie() {
		
		for(Zombie zombie : zombies) {
			if(zombie.kill) {
				zombies.remove(zombie);
				break;
			}
		}
	}
	
	public void checkCollision() {
		
		for(Zombie zombie : zombies) {		
			boolean playerCollision = collidePlayer(zombie);
			
			if(playerCollision) {
				zombie.collision = true;
				zombie.attack();
				continue;
			}
			zombie.collision = playerCollision;
		}
	}
	
	private boolean collidePlayer(Zombie zombie) {
		return gp.cChecker.checkEntityCollision(zombie, gp.player);
	}

	public void update() {
		
		checkCollision();
		checkForDeadZombie();
		
		for(Zombie zombie : zombies) {
			zombie.update();
		}
	}

	public void draw(Graphics2D g2) {

		for(Zombie zombie : zombies) {
			zombie.draw(g2);
		}
	}
}
