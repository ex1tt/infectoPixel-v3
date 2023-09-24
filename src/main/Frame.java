package main;

import javax.swing.JFrame;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	Panel panel;
	
	Frame() {
		
		panel = new Panel();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Infecto Pixel");
		
		this.add(panel);
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
	}
}
