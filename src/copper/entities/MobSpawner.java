package copper.entities;

import copper.Panel;

public class MobSpawner extends Entity {
	
	private double 	spawnCounter = 0, 
					spawnTime = 1;

	public MobSpawner(double x, double y, double z) {
		super(x, y, z);
		setBaseHealth(180);
		armor = 0.25;
		speed = 0;
	}
	
	public void tick() {
		spawnCounter -= Panel.delta;
		if (spawnCounter == 0) {
			spawnCounter = spawnTime;
			
			int xx = (int) x >> 4;
			int yy = (int) y >> 4;
			
			if (!isTileSolid(xx + 1, yy)) 
				new Zombie((xx + 1) << 4, yy << 4, z);
			else if (!isTileSolid(xx - 1, yy)) 
				new Zombie((xx - 1) << 4, yy << 4, z);
			else if (!isTileSolid(xx, yy + 1))
				new Zombie(xx << 4, (yy + 1) << 4, z);
			else if (!isTileSolid(xx, yy - 1))
				new Zombie(xx, (yy - 1) << 4, z);
			else new Zombie(xx << 4, yy << 4, z);
		}
	}
	
}
