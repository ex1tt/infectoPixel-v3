package entity;

import java.awt.Color;
import java.awt.Rectangle;

import main.Panel;

public class Entity {
	
	Panel gp;
	
	public int worldX;
	public int worldY;
	public int screenX;
	public int screenY;
	
	public int width;
	public int height;
	public double speed;
	public String direction = "up";
	public Rectangle solidArea;
	public Color color;
	public boolean collision = false;
	
	Entity(Panel gp) {
		
		this.gp = gp;
	}

}
