package copper.entities.particles;

import copper.entities.Entity;
import copper.graphics.Sprite;

public class Homing extends Particle {
	
	public int damage = 75;
	public Entity target;
	
	/**
	 * Base constructor.
	 * @param x
	 * @param y
	 * @param z
	 * @param rot
	 * @param speed
	 */

	public Homing(Entity p, double x, double y, double z, Entity t) {
		super(p, x, y, z, 0, 30);
		setSprite(Sprite.magic);
		setShadow(Sprite.shadowNormal);
		mass  			= 0;
		bounce  		= 0;
		dz				= 0;
		this.speed		= 30;
		lifeTime		= 4;
		reactsOnTouch 	= true;
		target = t;
	}
	
	public void tick() {
		super.tick();
		dx = target.x - x;
		dy = target.y - y;
		double length = Math.pow(dx * dx + dy * dy, 0.5);
		dx = dx / length * speed;
		dy = dy / length * speed;
	}
	
	protected void collidedWithEntity(Entity collided) {
		collided.damage(damage, parent);
		alive = false;
	}

}
