package copper.entities;

import copper.graphics.*;

public class Zombie extends Entity {
	
	private Entity target;
	
	public Zombie(double x, double y, double z) {
		super(x, y, z);
		setBaseHealth(180);
		sprite 		= Sprite.zombie;
		yRenderOff  = 12;
		width 		= sprite.width;
		height 		= sprite.height - 12;
		speed  	 	= 48;
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
		if (targetX > thisX) dx = speed;
		if (targetX < thisX) dx = -speed;
		if (targetY > thisY) dy = speed;
		if (targetY < thisY) dy = -speed;
		move(dx, dy);
	}
	
	private boolean isValidTarget(Entity target) {
		return target.alive && !target.equals(this) && !target.isGhost() && !target.isParticle && !(target instanceof MobSpawner);
	}

}
