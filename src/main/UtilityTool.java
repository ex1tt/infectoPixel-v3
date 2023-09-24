package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UtilityTool {
	
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0,0,width, height, null);
		g2.dispose();
		
		return scaledImage;
		
	}
	
	public BufferedImage loadImage(String imagePath, int tileSize) {

		
		BufferedImage image = null;
		try {
			// here
			image = ImageIO.read(getClass().getResourceAsStream(imagePath));
			image = scaleImage(image, tileSize, tileSize);
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		return image;		
	}
}
