package copper.ai;

import copper.entities.Entity;

public class Routine {

	public static final int SUCCESS = 0;
	public static final int FAILURE = 1;
	public static final int RUNNING = 2;
	
	public int run(Entity e) {
		return SUCCESS;
	}
	
	protected int succeed() {
		reset();
		return SUCCESS;
	}
	
	protected int fail() {
		reset();
		return FAILURE;
	}
	
	protected int running() {
		return RUNNING;
	}
	
	protected void reset() {
	}
	
}
