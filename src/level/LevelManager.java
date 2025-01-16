package level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.Panel;
import main.UtilityTool;

public class LevelManager {
	
	Panel gp;
	
	UtilityTool uTool;
	public BufferedImage current;
	private BufferedImage ground;
	private BufferedImage grass;
	private BufferedImage wood;
	private BufferedImage brick;
	
	public boolean[][] staticObstacleCoords;
	
	private int[][] mapTiles;
	
	public LevelManager(Panel gp) {
	
		this.gp = gp;
		
		setImage();
		
		current = ground;
		
		mapTiles = new int[gp.WORLD_ROW][gp.WORLD_COL];
		
		mapTiles = readMapFileToMatrix("res/map.txt", mapTiles);

		setStaticObstacles();		
	}
	
	public void setImage() {
		
		uTool = new UtilityTool();
		
		ground = uTool.loadImage("res/levelTiles/ground.png",  gp.TILE_SIZE);
		grass = uTool.loadImage("res/levelTiles/grass.png",  gp.TILE_SIZE);
		wood = uTool.loadImage("res/levelTiles/wood.png",  gp.TILE_SIZE);
		brick = uTool.loadImage("res/levelTiles/brick.png",  gp.TILE_SIZE);
	}
	
	private int[][] readMapFileToMatrix(String filePath, int[][] tiles) {
		
		int row = gp.WORLD_ROW;
		int col = gp.WORLD_COL;
		
		try {
			
			File file = new File(filePath);
			FileReader fr = new FileReader(file); 
			BufferedReader bR = new BufferedReader(fr);
			
			for(int i=0; i<col; i++) {
				
				String line = bR.readLine();
				
				for(int z=0; z<row; z++) {		
					String splitNumList[] = line.split(" ");
					int num = Integer.parseInt(splitNumList[z]);
					tiles[z][i] = num;					
				}
			}
			bR.close();			
			return tiles;
				
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}

	// For reference, static obstacles currently have an integer value 1
	private void setStaticObstacles() {
		
		staticObstacleCoords = new boolean[gp.WORLD_ROW][gp.WORLD_COL];
		
		for(int i=0; i<mapTiles.length; i++) {			
			for(int z=0; z<mapTiles[i].length; z++) {
				
				if(mapTiles[i][z] == 1) {
					staticObstacleCoords[i][z] = true;
				}				
			}		
		}		
	}
	
	public void draw(Graphics2D g2) {

	    for (int i=0; i < gp.WORLD_ROW; i++) {
	        for (int z=0; z < gp.WORLD_COL; z++) {

	            int worldX = i * gp.TILE_SIZE;
	            int worldY = z * gp.TILE_SIZE;
	            int screenX = worldX - gp.player.worldX + gp.player.screenX;
	            int screenY = worldY - gp.player.worldY + gp.player.screenY;

	            if (isTileVisibleOnScreen(worldX, worldY)) {
	                boolean isObstacle = staticObstacleCoords[i][z];

	                if (isObstacle) {
	                	g2.drawImage(brick, screenX, screenY, null);
	                }
	                else if(mapTiles[i][z] == 2){
	                	g2.drawImage(current, screenX, screenY, null);
	                }
	                else if(mapTiles[i][z] == 4){
	                	g2.drawImage(wood, screenX, screenY, null);
	                }
	                else {
	                	g2.drawImage(grass, screenX, screenY, null);
	                }
	            }
	        }
	    }
	}

	public boolean isTileVisibleOnScreen(int worldX, int worldY) {

	    return (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX && 
				worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX && 
				worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY && 
				worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY);
	}
}
