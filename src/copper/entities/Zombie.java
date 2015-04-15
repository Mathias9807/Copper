package copper.entities;

import static copper.levels.Level.entities;
import copper.Panel;
import copper.entities.items.Item;
import copper.entities.particles.Particle;
import copper.graphics.*;

public class Zombie extends Entity {
	
	private Entity target;
	private int attackingDistance = 16;
	
	public Zombie(double x, double y, double z) {
		super(x, y, z);
		setBaseHealth(180);
		sprite 		= Sprite.zombie;
		yRenderOff  = 12;
		width 		= sprite.width;
		height 		= sprite.height - 12;
		speed  	 	= 32;
		dmgColor 	= 0x22AA22;
		
		target = Screen.getFocusedEntity();
	}
	
	public void tick() {
		super.tick();
		if (!isValidTarget(target)) {
			target = entities.get((int) (rand.nextDouble() * entities.size()));
			return;
		}
		int thisX = (int) (x + width / 2), thisY = (int) (y + height / 2);
		int targetX = (int) (target.x + target.width / 2), targetY = (int) (target.y + target.height / 2);
		dx = dy = 0;
		if (Math.abs(targetX - thisX) + Math.abs(targetY - thisY) > attackingDistance) {
			if (targetX > thisX) dx = speed;
			if (targetX < thisX) dx = -speed;
			if (targetY > thisY) dy = speed;
			if (targetY < thisY) dy = -speed;
		}else {
			Entity.damage(target, 50 * Panel.delta, this);
		}
		move(dx, dy);
	}
	
	protected void onCollision(Entity collider) {
		collider.damage(0.2, this);
	}
	
	private boolean isValidTarget(Entity target) {
		return target.alive && !target.equals(this) && !target.isGhost() && !(target instanceof Particle) && !(target instanceof Item);
	}

}
