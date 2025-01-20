package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import entity.Player;
import level.LevelManager;
import ui.UIManager;

public class Panel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private Thread gameThread;
	public KeyHandler keyH;
	public UIManager ui;
	public GunManager gunMng;
	public ZombieManager zombieMng;
	public SoundManager soundMng;
	public CollisionChecker cChecker;
	public LevelManager levelMng;
	public Player player;
	
	private final int ORIGINAL_TILE_SIZE = 32;
	private final int SCALE = 3;
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
	
	public final int SCREEN_ROW = 14;
	public final int SCREEN_COL = 8;
	
	public final int SCREEN_WIDTH = SCREEN_ROW * TILE_SIZE;
	public final int SCREEN_HEIGHT = SCREEN_COL* TILE_SIZE;
	
	public final int WORLD_ROW = 40;
	public final int WORLD_COL = 24;
	
	public final int WORLD_WIDTH = WORLD_ROW * TILE_SIZE;
	public final int WORLD_HEIGHT = WORLD_COL * TILE_SIZE;
	
	private final int FPS = 60;

	public Clip backgroundMusic;
	
	Panel() {

		soundMng = new SoundManager(this);
		levelMng = new LevelManager(this);
		player = new Player(this);
		keyH = new KeyHandler(this);
		gunMng = new GunManager(this);
		zombieMng = new ZombieManager(this);
		cChecker = new CollisionChecker(this);
		ui = new UIManager(this);
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.setDoubleBuffered(true);
		
		gameThread = new Thread(this);
		gameThread.start();	

		backgroundMusic = soundMng.setSoundFile("res/soundFiles/bgMusic.wav");

		soundMng.playBackground(backgroundMusic);
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();		
				delta --;	
			}
		}		
	}
	
	private void update() {

		player.update();
		zombieMng.update();
		gunMng.bulletUpdate();		
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//long drawStart = 0;
		//drawStart = System.nanoTime();
		
		levelMng.draw(g2);
		gunMng.drawBullets(g2);		
		player.draw(g2);		
		zombieMng.draw(g2);		
		ui.draw(g2);
		
		//long drawEnd = System.nanoTime();
		//long passed = drawEnd - drawStart;
		
		//System.out.println("DRAWTIME: " + passed);
		
		g2.dispose();	
	}
}