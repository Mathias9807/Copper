package copper.entities;

import copper.Panel;
import copper.graphics.*;

public class Ghost extends Entity {
	
	private double movingTime = 0;
	private byte direction  = 0;
	
	public Ghost(double x, double y, double z) {
		super(x, y, z);
		setSprite(Sprite.ghost.getSprite(0, 0));
		width  		= 10;
		height 		= 12;
		elevation 	= 3;
	}
	
	public void tick() {
		super.tick();
		movingTime += Panel.delta;
		if (health <= 0) {
			alive = false;
			dead();
		}
		
		if (timeAlive % 30 == 0) {
			dx = rand.nextInt(3) - 1;
			dy = rand.nextInt(3) - 1;
			
			if (rand.nextInt(2) == 0) {
				dx = 0;
				dy = 0;
			}
			
			if (dx != 0 && dy != 0) 
				if (rand.nextBoolean()) dx = 0;
					else dy = 0;
		}
		
		if (dx > 0) direction = 1;
		else if (dx < 0) direction = 3;
		if (dy > 0) direction = 0;
		else if (dy < 0) direction = 2;
		
		if (dx == 0 && dy == 0) movingTime = 0;
		setSprite(Sprite.ghost.getSprite(direction, (int) (movingTime * 4) % 2));
		
		move(dx, dy);
	}
	
	public void render(Screen screen) {
		sprite.renderSprite(screen, (int) x - 3, (int) (y - z + elevation - 2));
	}
	
	public boolean isPushable() {
		return false;
	}
	
	public boolean isGhost() {
		return true;
	}

}
