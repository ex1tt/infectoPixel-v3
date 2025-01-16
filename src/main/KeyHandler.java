package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	Panel gp;
	
	//  5 keys we currently handle
	public boolean up, down, left, right, shoot;
	
	KeyHandler(Panel gp) {
		
		this.gp = gp;		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
	
		switch(code) {
		case KeyEvent.VK_W:			
			up = true;
			down = false;
			right = false;
			left = false;
			break;
		case KeyEvent.VK_S:
			up = false;
			down = true;
			right = false;
			left = false;
			break;
		case KeyEvent.VK_A:
			up = false;
			down = false;
			right = false;
			left = true;
			break;
		case KeyEvent.VK_D:
			up = false;
			down = false;
			right = true;
			left = false;
			break;
		case KeyEvent.VK_ENTER:
			shoot = true;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();
		
		switch(code) {
		case KeyEvent.VK_W:
			up = false;
			gp.player.current = gp.player.behind;
			gp.player.spriteCounter = 0;
			break;
		case KeyEvent.VK_S:
			down = false;
			gp.player.current = gp.player.front;
			gp.player.spriteCounter = 0;
			break;
		case KeyEvent.VK_A:
			left = false;
			gp.player.current = gp.player.left;
			gp.player.spriteCounter = 0;
			break;
		case KeyEvent.VK_D:
			right = false;
			gp.player.current = gp.player.right;
			gp.player.spriteCounter = 0;
			break;
		case KeyEvent.VK_ENTER:
			shoot = false;
			break;
		}
	}
}
