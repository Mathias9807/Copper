package copper.entities.items;

import copper.Audio;
import copper.entities.Entity;
import copper.graphics.Sprite;

public class HealthKit extends ItemType {
		
	public HealthKit() {
		sprite = new Sprite(8, 8, 0xFF0000);
	}
	
	public void use(Entity e) {
		super.use(e);
		e.damage(-20, null);
		
		Audio.playAndForget(Audio.boost, false);
	}
	
}
