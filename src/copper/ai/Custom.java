package copper.ai;

import copper.entities.Entity;

public class Custom extends Routine {
	
	private RoutineInterface function;
	
	public Custom(RoutineInterface function) {
		this.function = function;
	}
	
	public int run(Entity e) {
		int result = function.run(e);
		
		switch (result) {
		case SUCCESS: 	return succeed();
		case RUNNING: 	return running();
		default: 		return fail();
		}
	}
	
}
