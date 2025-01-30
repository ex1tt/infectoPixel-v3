package level;

import java.awt.image.BufferedImage;
import main.UtilityTool;

public class Tile {

    // Default is false
    public boolean solid = false;
    public BufferedImage image;
    
    public Tile(String fileName, int tileSize) {

        image = UtilityTool.loadImage("res/levelTiles/" + fileName,  tileSize);

    }
}
