package copper.ai;

import java.util.function.IntSupplier;

import copper.entities.Entity;

public class Custom extends Routine {
	
	private IntSupplier function;
	
	public Custom(IntSupplier function) {
		this.function = function;
	}
	
	public int run(Entity e) {
		int result = function.getAsInt();
		
		switch (result) {
		case SUCCESS: 	return succeed();
		case RUNNING: 	return running();
		default: 		return fail();
		}
	}
	
}
