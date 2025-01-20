package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.Clip;

import entity.Bullet;
import entity.Zombie;

public class GunManager {
	
	Panel gp;
	
	public ArrayList<Bullet> bullets;
	private ArrayList<Zombie> zombies;
	
	private final int HANDGUN_MAGAZINE = 6;
	private final int HANDGUN_RELOAD_TIME = 45;

	private Clip gunshotSound;

	private int bulletReloadCount;
	
	public GunManager(Panel gp) {
		
		this.gp = gp;
		
		bulletReloadCount = HANDGUN_RELOAD_TIME;
		
		bullets = new ArrayList<>();		
	}
	
	public void shoot(String direction) {
		
		if(canShoot()) {
			bullets.add(new Bullet(gp, direction));
			bulletReloadCount = 0;

			// Play handgun sound effect -> creating new instance each time is needed
			gunshotSound = gp.soundMng.setSoundFile("res/soundFiles/handGun.wav");
			gp.soundMng.playSound(gunshotSound);
		}
	}
	
	private boolean canShoot() {		
        return bulletReloadCount >= HANDGUN_RELOAD_TIME && bullets.size() < HANDGUN_MAGAZINE;
    }
	
	private void checkBulletCollision() {

		// Array list holding all live zombies...
		zombies = gp.zombieMng.zombies;

		for(Bullet bullet: bullets) {
			for(Zombie zombie: zombies) {

				if(gp.cChecker.checkEntityCollision(bullet, zombie)) {
					zombie.damage();	// Apply damage to zombie
					bullet.destroyBullet = true;	// Destroy bullet after it has hit a zombie
					break;
				}
			}
		}
	}
	
	public void bulletUpdate() {
		
		if(gp.keyH.shoot) {
			shoot(gp.player.direction);
		}

		if(!bullets.isEmpty()) {

			checkBulletCollision();
		
			// Using Iterator to safely remove bullets during iteration
			// Without iterator I would need to break out of loop after each discover of a bullet to remove (un-efficient for many checks..)
			Iterator<Bullet> iterator = bullets.iterator();

			while(iterator.hasNext()) {
				Bullet bullet = iterator.next();
	
				if (bullet.destroyBullet) {
					iterator.remove();  // Safely remove bullet from the list
				} else {
					bullet.update();  // Update bullet if not marked for destruction
				}
			}
		}
		// Update bullet reload count no matter what...
		bulletReloadCount ++;
	}

	public void drawBullets(Graphics2D g2) {
		for(Bullet bullet: bullets) {
			bullet.draw(g2);
		}
	}	
}