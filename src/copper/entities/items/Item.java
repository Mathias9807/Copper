package copper.entities.items;

import copper.Audio;
import copper.entities.Entity;
import copper.graphics.Screen;

public class Item extends Entity {
	
	public ItemType type;

	public Item(double x, double y, double z, ItemType it) {
		super(x, y, z);
		type	= it;
		width 	= type.sprite.width;
		height	= type.sprite.height;
		sprite 	= type.sprite;
		speed 	= 0;
		armor 	= 0;
		setBaseHealth(1);
	}
	
	protected void onCollision(Entity collider) {
		if (!(collider instanceof Item) && alive) {
			collider.inventory.add(type);
			
			Audio.playAndForget(Audio.pickup, false);
			
			alive = false;
		}
	}
	
	public void dead() {
		if (Screen.getFocusedEntity().equals(this)) Screen.setFocus(attacker);
	}

}
