package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import entity.Zombie;

public class ZombieManager {
	
	Panel gp;
	public ArrayList<Zombie> zombies;
	public int currentWave = 1;
	private Random random;
	
	public ZombieManager(Panel gp) {
		
		this.gp = gp;	
		random = new Random();
		zombies = new ArrayList<>();

		//startNewWave(currentWave);
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
				zombie.attack();
			}
			zombie.collision = playerCollision;
		}
	}

	public void checkNewWave() {

		// If no zombies left -> start new wave
		if(zombies.isEmpty()) {

			currentWave ++;
			startNewWave(currentWave);
		}
	}

	public void startNewWave(int wave) {

		gp.player.reset_stats(wave);

		// Add x amount of zombies
		for(int i=0; i<(wave*5); i++) {
			zombies.add(new Zombie(gp, 	random.nextInt(0, gp.WORLD_ROW) *gp.TILE_SIZE, random.nextInt(0, gp.WORLD_COL)*gp.TILE_SIZE, gp.levelMng.staticObstacleCoords));
		}
	}
	
	private boolean collidePlayer(Zombie zombie) {
		return gp.cChecker.checkEntityCollision(zombie, gp.player);
	}

	public void update() {
		
		checkCollision();
		checkForDeadZombie();
		//checkNewWave();
		
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
