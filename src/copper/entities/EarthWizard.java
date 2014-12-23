package copper.entities;

import copper.Panel;
import copper.entities.particles.*;
import copper.graphics.*;

public class EarthWizard extends Entity {
	
	private Entity target;
	
	public EarthWizard(double x, double y, double z) {
		super(x, y, z);
		setBaseHealth(100);
		sprite 		= Sprite.wizard.getSprite(0, 0);
		width 		= sprite.width;
		height 		= sprite.height;
		speed  	 	= 72;
		dmgColor 	= 0xAA22AA;
		
		target = Screen.getFocusedEntity();
	}
	
	public void tick() {
		super.tick();
		if (!isValidTarget(target)) {
			target = entities.get((int) (rand.nextDouble() * entities.size()));
			return;
		}

		double xx = target.x + target.width / 2 - x - width / 2;
		double yy = target.y + target.height / 2 - y - height / 2;
		double angle = Math.toDegrees(Math.atan2(yy, xx));
		setSprite(Sprite.wizard.getSprite((int) Math.min(angle / -90 + 2, 3), 0));
		if ((int) (Panel.time * 60) % 60 == 0) {
			new Boulder(this, x + width / 2, y + height / 2, z + 1, angle);
		}
		
		move(dx * speed, dy * speed);
	}
	
	public void damage(double damage, Entity attacker) {
		super.damage(damage, attacker);
	}
	
	private boolean isValidTarget(Entity target) {
		return target.alive && !target.equals(this) && !target.isGhost() && !target.isParticle && !(target instanceof MobSpawner);
	}

}
