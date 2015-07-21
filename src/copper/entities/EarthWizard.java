package copper.entities;

import java.util.function.IntSupplier;

import copper.ai.*;
import copper.entities.particles.Flame;
import copper.graphics.*;

public class EarthWizard extends Entity {
	
	private Routine routine, shootBoulder;
	
	private int shootSpeed = 2;
	private double cooldown = shootSpeed;
	private boolean firstVolley = true;
	
	public EarthWizard(double x, double y, double z) {
		super(x, y, z);
		setBaseHealth(100);
		sprite 		= Sprite.wizard.getSprite(0, 0);
		width 		= sprite.width;
		height 		= sprite.height;
		speed  	 	= 72;
		dmgColor 	= 0xAA22AA;
		
		target = Screen.getFocusedEntity();
		
		routine = new Sequence(
			new Wait(2), 
			new Selector(
				new Sequence(
					new FindTarget(),
					new IsInReach(75), 
					new ShootBoulder()
				), 
				new Sequence(
					new Custom((IntSupplier) () -> {
						Flame f;
						for (int i = 0; i < 9; i++) { 
							f = new Flame(this, x + width / 2, y + height / 2, z + 1, 360 / 9 * i + 45, 90);
							f.damage = 15;
							f.lifeTime = 0.2;
						}
						return Routine.SUCCESS;
					}), 
					new Wait(4)
				)
			)
		);
	}
	
	public void tick() {
		super.tick();
		
		routine.run(this);

		/*double xx = target.x + target.width / 2 - x - width / 2;
		double yy = target.y + target.height / 2 - y - height / 2;
		double angle = Math.toDegrees(Math.atan2(yy, xx));
		setSprite(Sprite.wizard.getSprite((int) Math.min(angle / -90 + 2, 3), 0));
		if ((cooldown += Panel.delta) > shootSpeed) {
			double length = Math.pow(xx * xx + yy * yy, 0.5);
			if (length > 25 && length <= 75) {
				new Boulder(this, x + width / 2, y + height / 2, z + 1, angle);
				cooldown = 0;
			}else if (length > 75) {
				new Homing(this, x + width / 2, y + height / 2, z + 1, target);
				cooldown = 0;
			}else {
				final int fireAmount = 9;
				Flame f;
				if (!firstVolley) {
					for (int i = 0; i < fireAmount; i++) { 
						f = new Flame(this, x + width / 2, y + height / 2, z + 1, 360 / fireAmount * i + 45, 90);
						f.damage = 15;
						f.lifeTime = 0.2;
				}
					cooldown = -1;
					firstVolley = true;
				}
				else {
					for (int i = 0; i < fireAmount; i++) {
						f = new Flame(this, x + width / 2, y + height / 2, z + 1, 360 / fireAmount * i, 90);
						f.damage = 8;
						f.lifeTime = 0.2;
					}
					cooldown = shootSpeed - 0.1;
					firstVolley = false;
				}
			}
		}
		
		move(dx * speed, dy * speed);*/
	}
	
	public void damage(double damage, Entity attacker) {
		super.damage(damage, attacker);
	}

}
