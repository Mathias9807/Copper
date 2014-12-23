package copper.entities.particles;

import copper.Panel;
import copper.entities.Entity;
import copper.graphics.Sprite;

public class Boulder extends Particle {

	public Boulder(Entity p, double x, double y, double z, double rot) {
		super(p, x, y, z, rot, 180);
		setSprite(Sprite.boulder);
		dz 				= 0.4;
		mass  			= 0.1;
		bounce  		= 0;
		reactsOnTouch	= true;
		setShadow(Sprite.shadowNormal);
		setTime(1000);
	}
	
	public void tick() {
		super.tick();
		if (dx == 0 || dy == 0) alive = false;
		spriteRot += Panel.delta;
	}
	
	protected void collidedWithEntity(Entity e) {
		e.damage(50, parent);
		alive = false;
	}
	
	public void dead() {
		for (int i = 0; i < 8; i++) {
			new Particle(this, x, y, z + 1, rand.nextInt(360), rand.nextDouble() * 60).setBounce(0.1).setMass(0.5).setColor(0x777777);
		}
	}
	
}
