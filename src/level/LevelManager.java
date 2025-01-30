package level;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.Panel;
import main.UtilityTool;

public class LevelManager {
	
	Panel gp;
	
	UtilityTool uTool;

	private Tile path_1;
	private Tile path_2;
	private Tile path_3;

	private Tile grass_1;
	private Tile grass_2;
	private Tile grass_3;

	private Tile wood;
	private Tile brick;
	
	public boolean[][] staticObstacleCoords;

	Tile[] tileList;
	
	private int[][] mapTiles;
	
	public LevelManager(Panel gp) {
	
		this.gp = gp;
		
		tileList = new Tile[10];

		setTiles();
		
		mapTiles = new int[gp.WORLD_ROW][gp.WORLD_COL];
		
		mapTiles = readMapFileToMatrix("res/map_2.txt", mapTiles);

		setStaticObstacles();		
	}
	
	public void setTiles() {
		
		path_1 = new Tile("path_1.png", gp.TILE_SIZE);
		path_2 = new Tile("path_2.png", gp.TILE_SIZE);
		path_3= new Tile("path_3.png", gp.TILE_SIZE);

		grass_1 = new Tile("grass_1.png", gp.TILE_SIZE);
		grass_2 = new Tile("grass_2.png", gp.TILE_SIZE);
		grass_3 = new Tile("grass_3.png", gp.TILE_SIZE);

		wood = new Tile("wood.png", gp.TILE_SIZE);
		brick = new Tile("brick.png", gp.TILE_SIZE);

		tileList[0] = brick;
		tileList[1] = path_2;
		tileList[2] = grass_1;
		tileList[3] = grass_2;
		tileList[4] = grass_3;
		tileList[5] = grass_3;
		tileList[6] = path_1;
		tileList[7] = path_2;
		tileList[8] = path_3;
		tileList[9] = wood;
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
				
				if(mapTiles[i][z] == 0) {
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

					g2.drawImage(tileList[mapTiles[i][z]].image, screenX, screenY, null);

					/**
	                if (mapTiles[i][z] == 4) {
	                	g2.drawImage(wood.image, screenX, screenY, null);
	                }
	                else if(mapTiles[i][z] == 2){
	                	g2.drawImage(path.image, screenX, screenY, null);
	                }
	                else if(mapTiles[i][z] == 1){
	                	g2.drawImage(brick.image, screenX, screenY, null);
	                }
					else if(mapTiles[i][z] == 1){
	                	g2.drawImage(brick.image, screenX, screenY, null);
	                }
					else if(mapTiles[i][z] == 1){
	                	g2.drawImage(brick.image, screenX, screenY, null);
	                }
					else if(mapTiles[i][z] == 1){
	                	g2.drawImage(brick.image, screenX, screenY, null);
	                }
	                else {
	                	g2.drawImage(grass.image, screenX, screenY, null);
	                }
						 */
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
