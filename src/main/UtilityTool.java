package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UtilityTool {
	
	public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
		
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0,0,width, height, null);
		g2.dispose();
		
		return scaledImage;
		
	}
	
	public static BufferedImage loadImage(String imagePath, int tileSize) {

		BufferedImage image = null;

		try {
			// Use File and ImageIO.read to load from disk

			File file = new File(imagePath);  // Create a File object for the image path

			if (!file.exists()) {  // Check if the file exists
				System.out.println("File not found: " + imagePath);
				return null;
			}

			image = ImageIO.read(file);  // Read the image from the file
			image = scaleImage(image, tileSize, tileSize);  // Scale the image

		} catch (IOException e) {

			System.out.println("ERROR loading image: " + imagePath);
			e.printStackTrace();
		}
		
		return image;
	}
}
