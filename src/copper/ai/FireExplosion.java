package copper.ai;

import copper.entities.Entity;
import copper.entities.particles.*;

public class FireExplosion extends Routine {
	
	private int fire, damage;
	
	public FireExplosion(int fire, int damage) {
		this.fire = fire;
		this.damage = damage;
	}
	
	public int run(Entity e) {
		Flame f;
		for (int i = 0; i < fire; i++) { 
			f = new Flame(e, e.x + e.width / 2, e.y + e.height / 2, e.z + 1, 360 / fire * i, 90);
			f.damage = damage;
			f.lifeTime = 0.2;
		}
		return succeed();
	}
	
}
