package main;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.Bullet;
import entity.Zombie;

public class GunManager {
	
	Panel gp;
	
	public ArrayList<Bullet> bullets;
	private ArrayList<Zombie> zombies;
	
	private final int HANDGUN_MAGAZINE = 6;
	private final int HANDGUN_RELOAD_TIME = 45;

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
		}
	}
	
	private boolean canShoot() {		
        return bulletReloadCount >= HANDGUN_RELOAD_TIME && bullets.size() < HANDGUN_MAGAZINE;
    }
	
	private void checkBulletCollision() {

		zombies = gp.zombieMng.zombies;
		for(Bullet bullet: bullets) {
			for(Zombie zombie: zombies) {
				if(gp.cChecker.checkEntityCollision(bullet, zombie)) {
					zombie.damage();
					bullet.destroyBullet = true;
					break;
				}
			}
		}
	}
	
	public void bulletUpdate() {
		
		if(gp.keyH.shoot) {
			shoot(gp.player.direction);
		}
		
		checkBulletCollision();
		bulletReloadCount ++;
		
		for(Bullet bullet: bullets) {
			if(bullet.destroyBullet) {
				bullets.remove(bullet);
				break;
			}
			else {
				bullet.update();
			}
		}
	}

	public void drawBullets(Graphics2D g2) {
		for(Bullet bullet: bullets) {
			bullet.draw(g2);
		}
	}	
}
