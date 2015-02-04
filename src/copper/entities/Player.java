package copper.entities;

import static copper.Panel.keys;

import java.awt.event.KeyEvent;

import copper.*;
import copper.entities.particles.*;
import copper.graphics.*;

public class Player extends Entity {
	
	private boolean sprinting = false;
	
	private boolean moving = false;
	private int direction  = 0;

	public Player(double x, double y, double z) {
		super(x, y, z);
		setBaseHealth(100);
		Screen.setFocus(this);
		setSprite(Sprite.ghost.getSprite(0, 0));
		xRenderOff 	= 4;
		yRenderOff	= 6;
		width   	= 8;
		height  	= 8;
		speed 		= 60;
	}
	
	public void tick() {
		super.tick();
		dx = 0;
		dy = 0;
		double speed;
		moving = true;
		
		if (keys[KeyEvent.VK_SHIFT]) sprinting = true;
		else sprinting = false;
		if (sprinting) speed = runningSpeed;
		else speed = this.speed;
		
		if (keys[KeyEvent.VK_E]) Copper.level.loadLevel("/demo2.txt");
		if (keys[KeyEvent.VK_W]) dy -= speed;
		if (keys[KeyEvent.VK_S]) dy += speed;
		if (keys[KeyEvent.VK_A]) dx -= speed;
		if (keys[KeyEvent.VK_D]) dx += speed;
		if (keys[KeyEvent.VK_SPACE]) dz += 0.005;
		
		int xx = Panel.getMouseX() / Copper.SCALE - (xAbsolute - Screen.getCamX()) - width / 2;
		int yy = Panel.getMouseY() / Copper.SCALE - (yAbsolute - Screen.getCamY()) - height / 2;
		
		direction = (int) (Math.atan2(yy, xx) / Math.PI * 180);
		
		int spriteDirection = 2;
		if (direction >= 45 && direction < 135) spriteDirection = 0;
		if (direction >= -45 && direction < 45) spriteDirection = 1;
		if (direction >= -135 && direction < -45) spriteDirection = 2;
		if (direction >= 135 || direction < -135) spriteDirection = 3;
		
		if (dx == 0 && dy == 0) moving = false;
		setSprite(Sprite.ghost.getSprite(spriteDirection, (int) (moving ? Panel.time * 4 : 0) % 2));
		
		if (Panel.pressedMButtons.contains(0)) 
			new Ball(this, x + width / 2, y + height / 2, z, direction, 256);
		
		/*if (Panel.mButtons[0]) 
			for (int i = 0; i < 2; i++) 
				new Flame(this, x + width / 2, y + height / 2, z - 1, xx, yy, length);
		
		if (Panel.pressedMButtons.contains(1)) 
			for (int i = 0; i < 3; i++) 
				new Ball(this, x + width / 2, y + height / 2, z, direction, length * 4);
		
		if (Panel.pressedMButtons.contains(2)) 
			for (int i = 0; i < 3; i++) 
				new Boulder(this, x + width / 2, y + height / 2, z, direction);*/
		
		if (sprinting && (dx != 0 || dy != 0) && Panel.time % 4 == 0) new Particle(this, x + rand.nextInt(width), 
				y + rand.nextInt(height), z + 1, (rand.nextDouble() * 360), 0).setColor(0xFFFFFF)
				.setTime(rand.nextInt(10));
		
		move(dx, 0);
		move(0, dy);
	}
	
	public boolean isGhost() {
		return false;
	}

}
