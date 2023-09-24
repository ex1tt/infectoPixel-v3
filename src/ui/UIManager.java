package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Panel;

public class UIManager {
	
	Panel gp;
	Graphics2D g2;
	Font gameFont;
	
	private int x;
	private int y;
	
	private String text;
	
	public UIManager(Panel gp) {
		
		this.gp = gp;
		
		gameFont = new Font("Calibri", Font.BOLD, 35);
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		// HEALTH
		
		g2.setFont(gameFont);
		g2.setColor(Color.black);
		text = "Health: " + gp.player.health;
		
		x = 30;
		y = 50;
		
		g2.drawString(text, x, y);
		
	}

}
