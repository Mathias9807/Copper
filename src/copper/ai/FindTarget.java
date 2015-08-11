package copper.ai;

import java.util.*;

import copper.entities.*;
import copper.entities.items.Item;
import copper.entities.particles.Particle;
import copper.levels.Level;

public class FindTarget extends Routine {
	
	public int run(Entity e) {
		List<Entity> targets = new ArrayList<Entity>();
		for (int i = 0; i < Level.entities.size(); i++) 
			if (isValidTarget(e, Level.entities.get(i))) {
				targets.add(Level.entities.get(i));
			}
		
		int nearest = 0;
		for (int i = 0; i < targets.size(); i++) {
			if (targets.get(i) instanceof Player) {
				e.target = targets.get(i);
				return succeed();
			}
			Entity t = targets.get(i);
			nearest = getManhattan(e.x, e.y, t.x, t.y) < getManhattan(e.x, e.y, targets.get(nearest).x, targets.get(nearest).y) 
					? i : nearest; 
		}
		if (targets.size() > 0) {
			e.target = targets.get(nearest);
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
	
	private double getManhattan(double ax, double ay, double bx, double by) {
		return Math.abs(bx - ax) + Math.abs(by - ay);
	}
	
}
