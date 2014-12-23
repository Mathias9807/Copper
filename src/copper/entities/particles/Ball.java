package copper.entities.particles;

import copper.entities.Entity;
import copper.graphics.Sprite;

public class Ball extends Particle {

	public Ball(Entity p, double x, double y, double z, double rot, double speed) {
		super(p, x, y, z, rot, speed);
		setSprite(Sprite.ball);
		mass  			= 0.01;
		bounce  		= 0.95;
		dz				= 1.75;
		reactsOnTouch	= true;
		setShadow(Sprite.shadowSmall);
		setTime(1000);
	}
	
	protected void collidedWithEntity(Entity e) {
		e.damage(Math.sqrt(dx * dx + dy * dy) * 10, parent);
		dx = dx * -bounce;
		dy = dy * -bounce;
	}
	
}
