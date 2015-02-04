package copper.entities.particles;

import static copper.levels.Level.entities;

import java.util.ArrayList;

import copper.Panel;
import copper.entities.Entity;
import copper.entities.items.Item;
import copper.graphics.*;
import copper.levels.Level;

public class Particle extends Entity {

	protected Entity 	parent;
	protected double 	lifeTime;
	protected double 	dx, dy, dz, rot, speed, bounce, spriteRot;
	protected boolean 	reactsOnTouch;
	protected ArrayList<Long> hitEntities;

	public Particle(Entity p, double x, double y, double z, double rot, double speed) {
		super(x, y, z);
		setSprite(new Sprite(1, 1, 0xFF9900));
		setShadow(null);
		hitEntities = new ArrayList<Long>();
		hitEntities.add(p.getId());
		parent			= p;
		this.x 			= x;
		this.y 			= y;
		this.z 			= z;
		dx 				= Math.cos(Math.toRadians(rot)) * speed;
		dy 				= Math.sin(Math.toRadians(rot)) * speed;
		dz 				= 0.5;
		spriteRot 		= 0;
		width 			= 1;
		height 			= 1;
		mass 			= 0.5;
		xRenderOff		= sprite.width / 2;
		yRenderOff		= sprite.height / 2;
		bounce 			= 0.8;
		timeAlive 		= 0;
		lifeTime 		= 1;
		alive 			= true;
		reactsOnTouch 	= false;
		isParticle		= true;
	}

	public Particle setColor(int h) {
		setSprite(new Sprite(1, 1, h));
		return this;
	}

	public Particle setTime(double d) {
		lifeTime = d;
		return this;
	}

	public Particle setBounce(double b) {
		bounce = b;
		return this;
	}

	public Particle setMass(double m) {
		mass = m;
		return this;
	}

	public void tick() {
		if (z <= 0) {
			dx *= bounce;
			dy *= bounce;
			dz = dz * -bounce;
		}else {
			dz -= mass * Level.GRAVITY * Panel.delta;
		}
		dx -= dx * Level.AIR_DENSITY;
		dy -= dy * Level.AIR_DENSITY;
		dz -= dz * Level.AIR_DENSITY;

		if (!isTileSolid(Panel.toTile((int) (x + dx * Panel.delta)), Panel.toTile((int) y))) x += dx * Panel.delta;
		else dx *= -bounce;
		if (!isTileSolid(Panel.toTile((int) x), Panel.toTile((int) (y + dy * Panel.delta)))) y += dy * Panel.delta;
		else dy *= -bounce;
		
		if (reactsOnTouch) 
			for (int i = 0; i < entities.size(); i++){
				Entity e = entities.get(i);
				if (e.isGhost() || e.isParticle || e instanceof Item) continue;
				if (hitEntities.contains(e.getId())) {
					continue;
				}
				if (x + dx * Panel.delta < e.x + e.width && 
						x + dx * Panel.delta >= e.x && y + dy * Panel.delta < e.y + e.height && y + dy * Panel.delta + 1 >= e.y) {
					hitEntities.add(e.getId());
					collidedWithEntity(e);
				}
			}
		z += dz;

		if (timeAlive > lifeTime) alive = false;
		xRenderOff	= sprite.width / 2;
		yRenderOff	= sprite.height / 2;
		xAbsolute 	= (int) x - xRenderOff;
		yAbsolute 	= (int) (y - z + elevation) - yRenderOff;
	}

	protected void collidedWithEntity(Entity collided) {
	}
	
	public void dead() {
	}

	public void render(Screen screen) {
		if (spriteRot != 0) sprite.renderSprite(screen, xAbsolute, yAbsolute, spriteRot);
		else sprite.renderSprite(screen, xAbsolute, yAbsolute);
	}

	public boolean isPushable() {
		return false;
	}

	public boolean isGhost() {
		return true;
	}

}
