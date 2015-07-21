package copper.ai;

import copper.entities.Entity;

public class IsInReach extends Routine {
	
	private int reach;
	
	public IsInReach(int reach) {
		this.reach = reach;
	}
	
	public int run(Entity e) {
		int xx = (int) (e.target.x + e.target.width / 2 - e.x + e.width / 2);
		int yy = (int) (e.target.y + e.target.height / 2 - e.y + e.height / 2);
		if (Math.pow(xx * xx + yy * yy, 0.5) < reach) 
			return succeed();
		return fail();
	}
	
}
