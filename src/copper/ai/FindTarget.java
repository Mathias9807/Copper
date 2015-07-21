package copper.ai;

import copper.entities.Entity;
import copper.entities.items.Item;
import copper.entities.particles.Particle;
import copper.levels.Level;

public class FindTarget extends Routine {
	
	public int run(Entity e) {
		for (int i = 0; i < Level.entities.size(); i++) 
			if (isValidTarget(e, Level.entities.get(i))) {
				e.target = Level.entities.get(i);
				return succeed();
			}
		
		e.target = null;
		return fail();
	}
	
	private boolean isValidTarget(Entity e, Entity target) {
		return target.alive 
				&& !target.equals(e) 
				&& !target.isGhost() 
				&& !(target instanceof Particle) 
				&& !(target instanceof Item);
	}
	
}
