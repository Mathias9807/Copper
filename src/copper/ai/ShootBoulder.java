package copper.ai;

import copper.entities.Entity;
import copper.entities.particles.Boulder;

public class ShootBoulder extends Routine {
	
	public int run(Entity e) {
		Entity target = e.target;
		double xx = target.x + target.width / 2 - e.x - e.width / 2;
		double yy = target.y + target.height / 2 - e.y - e.height / 2;
		double angle = Math.toDegrees(Math.atan2(yy, xx));
		
		new Boulder(e, e.x + e.width / 2, e.y + e.height / 2, e.z + 1, angle);
		
		return succeed();
	}
	
}
