package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Panel;

public class UIManager {
	
	Panel gp;
	Graphics2D g2;
	Font gameFont;
	private final Color FONT_COLOR = Color.black;
	
	private int x;
	private int y;
	
	private String health_text;
	private String wave_text;
	
	public UIManager(Panel gp) {
		
		this.gp = gp;
		gameFont = new Font("Calibri", Font.BOLD, 35);
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		// HEALTH
		
		g2.setFont(gameFont);
		g2.setColor(FONT_COLOR);
		health_text = "Health: " + gp.player.health;
		wave_text = "Wave: " + gp.zombieMng.currentWave;

		
		x = 30;
		y = 50;
		
		g2.drawString(health_text, x, y);
		g2.drawString(wave_text, x, y+50);
		
	}

}
