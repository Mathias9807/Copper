package copper.ai;

import copper.entities.Entity;

public class Selector extends Routine {
	
	private Routine[] routines;
	private int i = 0;
	
	public Selector(Routine... routines) {
		this.routines = routines;
	}
	
	public int run(Entity e) {
		for (; i < routines.length; ) {
			int result = routines[i].run(e);
			if (result == RUNNING) return running();
			if (result == SUCCESS) return succeed();
			i++;
		}
		
		return fail();
	}
	
	protected void reset() {
		i = 0;
	}
	
}
