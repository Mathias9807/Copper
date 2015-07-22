package copper.ai;

import copper.entities.Entity;
import copper.entities.particles.*;

public class ShootHoming extends Routine {
	
	public int run(Entity e) {
		new Homing(e, e.x + e.width / 2, e.y + e.height / 2, e.z + 1, e.target);
		
		return succeed();
	}
	
}
