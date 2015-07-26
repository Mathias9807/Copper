package copper.entities;

import copper.Panel;
import copper.ai.*;
import copper.graphics.*;

public class Ghost extends Entity {
	
	private double movingTime = 0;
	private byte direction  = 0;
	
	public Ghost(double x, double y, double z) {
		super(x, y, z);
		setSprite(Sprite.ghost.getSprite(0, 0));
		xRenderOff 	= 4;
		yRenderOff	= 6;
		width   	= 8;
		height  	= 8;
		elevation 	= 3;
		
		routine = new Sequence(
			new Wait(3), 
			new FindTarget(), 
			new NavigateTo(), 
			new Custom((RoutineInterface) (e) -> {
				double xx = e.target.x - e.x;
				double yy = e.target.y - e.y;
				if (Math.pow(xx * xx + yy * yy, 0.5) > 20) return Routine.FAILURE;
				e.target.damage(30, e);
				return Routine.SUCCESS;
			})
		);
	}
	
	public void tick() {
		super.tick();
		
		routine.run(this);
		movingTime += Panel.delta;
		
		if (dx > Math.abs(dy)) direction = 1;
		else if (dx < -Math.abs(dy)) direction = 3;
		if (dy > Math.abs(dx)) direction = 0;
		else if (dy < -Math.abs(dx)) direction = 2;
		
		if (dx == 0 && dy == 0) movingTime = 0;
		setSprite(Sprite.ghost.getSprite(direction, (int) (movingTime * 4) % 2));
		
		move(dx, dy);
	}
	
	public boolean isPushable() {
		return false;
	}
	
	public boolean isGhost() {
		return true;
	}

}
