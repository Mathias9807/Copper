package copper.entities.particles;

import copper.*;
import copper.entities.Entity;
import copper.graphics.Sprite;

public class SnowBall extends Particle {

	public SnowBall(Entity p, double x, double y, double z, double rot,
			double speed) {
		super(p, x, y, z, rot, speed);
		setSprite(Sprite.snowball);
		bounce = 0;
		mass = 1.15;
		dz = 92;
		reactsOnTouch = true;
		dmgColor = 0xFFFFFF;
	}
	
	protected void hitObstacle() {
		for (int i = 0; i < 8; i++) 
			new Particle(this, x, y, z + 1, 
					i / 8d * 360 + ((Panel.time * 100) % 3) * 24, 15 + ((Panel.time * i * 1000) % 3) * 15)
					.setColor(dmgColor).setBounce(0);
		
		Audio.playAndForget(Audio.snowhit, false);
		
		alive = false;
	}
	
	protected void collidedWithEntity(Entity e) {
		e.damage(30, parent);
		dx = dx * -bounce;
		dy = dy * -bounce;
	}

}
