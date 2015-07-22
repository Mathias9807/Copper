package copper.entities;

import java.util.function.IntSupplier;

import copper.ai.*;
import copper.entities.particles.*;
import copper.graphics.*;

public class EarthWizard extends Entity {
	
	private Routine routine;
	
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
					new IsInReach(40), 
					new FireExplosion(9, 10), 
					new Wait(0.1), 
					new FireExplosion(7, 5), 
					new Wait(2)
				), 
				new Sequence(
					new FindTarget(),
					new IsInReach(75), 
					new ShootBoulder()
				), 
				new Sequence(
					new FindTarget(),
					new Wait(1), 
					new ShootHoming()
				)
			)
		);
	}
	
	public void tick() {
		super.tick();
		
		routine.run(this);
		
		move(dx * speed, dy * speed);
	}
	
	public void damage(double damage, Entity attacker) {
		super.damage(damage, attacker);
	}

}
