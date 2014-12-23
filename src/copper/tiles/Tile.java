package copper.tiles;

import copper.graphics.*;


public class Tile {
	
	public static Tile[] tiles = new Tile[64];
	public static Tile tile = new Tile(0);
	public static Tile grass = new Grass(1);
	public static Tile rock = new Rock(2);
	
	protected Sprite sprite;
	
	public Tile(int id){
		sprite = new Sprite(16, 16, 0xFF00FF);
		tiles[id] = this;
	}

	public void render(int[][] pixelBuffer, int x, int y){
		sprite.renderLevel(pixelBuffer, x, y);
	}
	
	public boolean solid(){
		return false;
	}
	
}
