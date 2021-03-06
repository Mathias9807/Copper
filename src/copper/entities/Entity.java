package copper.entities;

import static copper.Panel.delta;
import static copper.levels.Level.entities;

import java.util.Random;

import copper.Panel;
import copper.ai.Routine;
import copper.entities.items.*;
import copper.entities.particles.Particle;
import copper.graphics.*;
import copper.levels.Level;

public class Entity {
	
	protected static Random 		rand 		= new Random();
	private static long				idCount 	= Long.MIN_VALUE;
	
	private long 		id;
	public boolean 		alive;
	
	protected Sprite 	sprite, shadow;
	public double 		x, y, z, speed, runningSpeed, mass, elevation;
	public int 			width, height, dmgColor;
	public double 		timeAlive;
	
	public double		dx, dy, dz;
	protected int		maxHealth;
	protected double	health, armor;
	protected int		xAbsolute, yAbsolute, xRenderOff, yRenderOff;
	
	public Entity		attacker;
	public Entity 		target;
	
	public Container	inventory;
	
	public Routine 		routine;
	
	public Entity(double x, double y, double z){
		this.x 			= x;
		this.y 			= y;
		this.z 			= z;
		speed 			= 45;
		runningSpeed 	= 84;
		mass			= 0.15;
		elevation 		= 2;
		width 			= 16;
		height 			= 16;
		dmgColor		= 0xFF0000;
		alive 			= true;
		maxHealth		= 100;
		health			= maxHealth;
		armor 			= 1;
		xAbsolute		= (int) x - xRenderOff;
		yAbsolute		= (int) (y - z + elevation) - yRenderOff;
		inventory 		= new Container(1);
		id				= genId();
		setSprite(new Sprite(width, height, 0xFFFFFF));
		setShadow(Sprite.shadowNormal);
		entities.add(this);
	}
	
	public void tick() {
		gravity();
		xAbsolute = (int) x - xRenderOff;
		yAbsolute = (int) (y - z + elevation) - yRenderOff;
	}
	
	public boolean move(double dx, double dy) {
		dx *= delta;
		dy *= delta;
		
		boolean xb = false, yb = false; // Block collision
		boolean xe = true, ye = true;   // Entity collision
		
		double xc = x + width - 1;
		double yc = y + height - 1;
		if (x + dx < 0 || xc + dx >= Level.tileMap.length << 4) dx = 0;
		if (y + dy < 0 || yc + dy >= Level.tileMap[0].length << 4) dy = 0;
		
		if (dx > 0){
			if (!isTileSolid((int) (xc + dx) >> 4, (int) y >> 4) && !isTileSolid((int) (xc + dx) >> 4, (int) yc >> 4)) xb = true;
		}else if (dx < 0){
			if (!isTileSolid((int) (x + dx) >> 4, (int) y >> 4) && !isTileSolid((int) (x + dx) >> 4, (int) yc >> 4)) xb = true;
		}
		
		if (dy > 0){
			if (!isTileSolid((int) x >> 4, (int) (yc + dy) >> 4) && !isTileSolid((int) xc >> 4, (int) (yc + dy) >> 4)) yb = true;
		}else if (dy < 0){
			if (!isTileSolid((int) x >> 4, (int) (y + dy) >> 4) && !isTileSolid((int) xc >> 4, (int) (y + dy) >> 4)) yb = true;
		}
		
		if (!isGhost() && !(this instanceof Item)) 
		for (int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			if (e == this || e.isGhost() || e instanceof Particle) continue;
			if (x + dx < e.x + e.width && xc + dx >= e.x && y + dy < e.y + e.height && yc + dy + 1 >= e.y) {
				if (e instanceof Item) {
					e.onCollision(this);
					continue;
				}
				if (!e.isPushable()) continue;
				xe = false;
				ye = false;
				e.move(dx * 0.5, dy * 0.5, 0);
				onCollision(e);
			}
		}
		
		if (xb && xe) x += dx;
		if (yb && ye) y += dy;
		
		if (xb && xe || yb && ye) return true;
		
		return false;
	}
	
	public void move(double dx, double dy, int n) {
		boolean xb = false, yb = false; // boolean for block collision
		boolean xe = true, ye = true; 	// boolean for Entity collision
		
		double xc = x + width - 1;
		double yc = y + height;
		
		if (!(x + dx < 0) && !(xc + dx >= Level.tileMap.length << 4)) {
			if (dx > 0){
				if (!isTileSolid((int) (xc + dx) >> 4, (int) y >> 4) && 
						!isTileSolid((int) (xc + dx) >> 4, (int) yc >> 4)) 
					xb = true;
			}else if (dx < 0){
				if (!isTileSolid((int) (x + dx) >> 4, (int) y >> 4) && 
						!isTileSolid((int) (x + dx) >> 4, (int) yc >> 4)) 
					xb = true;
			}
		}
		
		if (!(y + dy < 0) && !(yc + dy >= Level.tileMap[0].length << 4)) {
			if (dy > 0){
				if (!isTileSolid((int) x >> 4, (int) (yc + dy) >> 4) && 
						!isTileSolid((int) xc >> 4, (int) (yc + dy) >> 4)) 
					yb = true;
			}else if (dy < 0){
				if (!isTileSolid((int) x >> 4, (int) (y + dy) >> 4) && 
						!isTileSolid((int) xc >> 4, (int) (y + dy) >> 4)) 
					yb = true;
			}
		}
		
		if (xb && xe) x += dx;
		if (yb && ye) y += dy;
	}
	
	/**
	 * Called when this Entity collides with another. 
	 * @param collider
	 */
	protected void onCollision(Entity collider) {
	}
	
	public void dead() {
		for (int i = 0; i < 10; i++) {
			new Particle(this, x + rand.nextInt(width) - xRenderOff, 
				y + rand.nextInt(height) - yRenderOff, z - 1, (rand.nextDouble() * 360), rand.nextDouble() * 40).setColor(0xFF0000)
				.setTime((int) (rand.nextFloat() * 2)).setColor(dmgColor);
		}
		if (Screen.getFocusedEntity().equals(this)) Screen.setFocus(attacker);
	}
	
	public void damage(double damage, Entity attacker) {
		this.attacker = attacker;
		
		if (damage < 0) health = Math.min(health + -damage, maxHealth);
		else health -= damage * armor;
		
		if ((int) (Panel.time * 60) % 3 == 0) new Particle(this, x + rand.nextInt(width), 
				y + rand.nextInt(height), z + 1, rand.nextDouble() * 360, rand.nextDouble() * 40).setColor(dmgColor)
				.setTime((int) (rand.nextFloat() * 2));
	}
	
	public static void damage(Entity attacked, double damage, Entity attacker) {
		attacked.damage(damage, attacker);
	}
	
	protected void gravity() {
		dz -= mass * Level.GRAVITY * delta;
		
		if (z + dz > elevation) {
			z += dz;
		}else {
			dz = 0;
			z  = elevation;
		}
	}
	
	public static boolean isTileSolid(double x, double y) {
		if (x < 0 || y < 0) return true;
		if (x >= Level.tileMap.length || y >= Level.tileMap[0].length) return true;
		if (Level.tileMap[(int) x][(int) y] >= Level.solid.length) return false;
		return Level.solid[Level.tileMap[(int) x][(int) y]];
	}
	
	public boolean isPushable()						{ return true; 							}
	
	public boolean isGhost()						{ return false; 						}
	
	public void render(Screen screen) {
		sprite.renderSprite(screen.pixels, xAbsolute, yAbsolute);
	}
	
//	Setters
	
	protected void setBaseHealth(int health) {
		maxHealth 	= health;
		this.health	= health;
	}
	
	protected void setSprite(Sprite s) {
		sprite = s;
	}
	
	protected void setShadow(Sprite s) {
		shadow = s;
	}
	
	public String toString() {
		return "Name: " + getClass().getSimpleName() + ", ID: " + id + ", X: " + (int) x + ", Y: " + (int) y + ", Z:" + (int) z;
	}
	
//	Getters
	
	public Sprite getShadow() {
		return shadow;
	}
	
	public double getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public long getId() {
		return id;
	}
	
	public static long getIdCount() {
		return idCount;
	}
	
	public static long genId() {
		return ++idCount;
	}
	
}
