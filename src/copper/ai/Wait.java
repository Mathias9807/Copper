package copper.ai;

import copper.*;
import copper.entities.Entity;

public class Wait extends Routine {
	
	private double startTime, waitTime;
	
	public Wait(double time) {
		startTime = Panel.time;
		waitTime = time;
	}
	
	public int run(Entity e) {
		if (Panel.time - startTime > waitTime) 
			return succeed();
		return running();
	}
	
	public void reset() {
		startTime = Panel.time;
	}
	
}
