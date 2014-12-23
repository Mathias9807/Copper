package copper.tiles;

import copper.graphics.Sprite;

public class Rock extends Tile {

	public Rock(int id) {
		super(id);
		sprite = Sprite.terrain.getSprite(1, 0);
	}
	
	public boolean solid(){
		return true;
	}

}
