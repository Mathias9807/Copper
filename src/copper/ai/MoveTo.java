package copper.ai;

import copper.Panel;
import copper.entities.Entity;

public class MoveTo extends Routine {
	
	private double x, y, lastX = -1, lastY = -1;
	private boolean moveToTarget = false;
	private int distance = 0;
	
	public MoveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public MoveTo(double x, double y, int d) {
		this.x = x;
		this.y = y;
		distance = d;
	}
	
	public MoveTo() {
		moveToTarget = true;
	}
	
	public MoveTo(int d) {
		moveToTarget = true;
		distance = d;
	}
	
	public int run(Entity e) {
		if (distance != 0 
			&& (int) Math.abs(e.x + e.width / 2 - x) < distance 
			&& (int) Math.abs(e.y + e.height / 2 - y) < distance) {
			e.dx = 0;
			e.dy = 0;
			return succeed();
		}
		if ((int) (e.x + e.width / 2) == (int) x 
				&& (int) (e.y + e.height / 2) == (int) y) {
			e.dx = 0;
			e.dy = 0;
			return succeed();
		}
		
		if (moveToTarget) {
			x = e.target.x + e.target.width / 2;
			y = e.target.y + e.target.height / 2;
		}
		double xx = x - (e.x + e.width / 2);
		double yy = y - (e.y + e.height / 2);
		double length = Math.pow(xx * xx + yy * yy, 0.5);
		e.dx = xx / length * e.speed;
		e.dy = yy / length * e.speed;
		
		if (length < e.speed * Panel.delta) {
			e.x = x - e.width / 2;
			e.y = y - e.height / 2;
			e.dx = 0;
			e.dy = 0;
		}
		
		if ((int) e.x == (int) lastX && (int) e.y == (int) lastY) return fail();
		lastX = e.x;
		lastY = e.y;
		
		return running();
	}
	
	public void setTarget(int x, int y) {
		this.x = x;
		this.y = y;
		moveToTarget = false;
	}
	
	public void reset() {
		x = y = lastX = lastY = distance = 0;
	}
	
}
