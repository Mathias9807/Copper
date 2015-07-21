package copper.ai;

import copper.entities.Entity;

public class Sequence extends Routine {
	
	private Routine[] routines;
	private int i = 0;
	
	public Sequence(Routine... routines) {
		this.routines = routines;
	}
	
	public int run(Entity e) {
		for (; i < routines.length; ) {
			int result = routines[i].run(e);
			if (result == FAILURE) return fail();
			if (result == RUNNING) return running();
			i++;
		}
		
		return succeed();
	}
	
	protected void reset() {
		i = 0;
	}
	
}
