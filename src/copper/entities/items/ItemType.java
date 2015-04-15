package copper.entities.items;

import copper.entities.Entity;
import copper.graphics.Sprite;

public class ItemType {
	
	public Sprite 	sprite = new Sprite(8, 8, 0xFFFFFF);
	public int 		usesLeft = 1;
	
	public void use(Entity e) {
		usesLeft--;
	}
	
}
