package copper.entities.particles;

import copper.entities.Entity;
import copper.graphics.Sprite;

public class Flame extends Particle {
	
	/**
	 * Pre-made constructor.
	 * @param x
	 * @param y
	 * @param z
	 * @param xMouse
	 * @param yMouse
	 * @param length
	 */
	
	public Flame(Entity p, double x, double y, double z, int xMouse, int yMouse, double length) {
		this(p, x, y, z, Math.toDegrees(
				Math.atan2(yMouse, xMouse)) + rand.nextInt(40) - 20, rand.nextInt(300) / 2D + 180);
		setTime(rand.nextDouble() * 0.25);
		spriteRot = rand.nextDouble() * Math.PI * 2;
	}
	
	/**
	 * Base constructor.
	 * @param x
	 * @param y
	 * @param z
	 * @param rot
	 * @param speed
	 */

	public Flame(Entity p, double x, double y, double z, double rot, double speed) {
		super(p, x, y, z, rot, speed);
		setSprite(Sprite.flame);
		setShadow(Sprite.shadowNormal);
		mass  			= -0.005;
		bounce  		= 0;
		dz				= -0.005;
		reactsOnTouch 	= true;
	}
	
	protected void collidedWithEntity(Entity collided) {
		collided.damage(1, parent);
	}

}
