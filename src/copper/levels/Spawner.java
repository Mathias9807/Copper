package copper.levels;

import copper.entities.*;
import copper.entities.items.*;

public class Spawner {
	
	private int entity;
	private double x, y, z;
	
	public Spawner(int e, double x, double y, double z) {
		entity = e;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void spawnEntity() {
		Entity e = getEntityInstance();
		Level.entities.add(e);
		if (e instanceof Player) Level.players.add((Player) e);
	}

	public Entity getEntityInstance() {
		Entity e = null;
		switch (entity) {
		case (0):
			e = new Entity(x, y, z);
			break;
		case (1):
			e = new Player(x, y, z);
			break;
		case (2):
			e = new Item(x, y, z, new HealthKit());
			break;
		case (3):
			e = new Ghost(x, y, z);
			break;
		case (4):
			e = new Zombie(x, y, z);
			break;
		case (5):
			e = new MobSpawner(x, y, z);
			break;
		case (6):
			e = new EarthWizard(x, y, z);
			break;
		}
		Level.entities.remove(e);
		
		return e;
	}
	
	public int getEntityID() {
		return entity;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
}
