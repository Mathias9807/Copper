package copper.ai;

import copper.entities.Entity;

public class Loop extends Routine {
	
	private Routine routine;
	
	public Loop(Routine routine) {
		this.routine = routine;
	}
	
	public int run(Entity e) {
		if (routine.run(e) == FAILURE) return fail();
		
		return succeed();
	}
	
}
