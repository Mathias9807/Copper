package copper.tiles;

import copper.graphics.Sprite;

public class Grass extends Tile {
	
	public Grass(int id){
		super(id);
		sprite = Sprite.terrain.getSprite(0, 0);
	}

}
