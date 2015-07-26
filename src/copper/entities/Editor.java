package copper.entities;

import static copper.Panel.keys;

import java.awt.event.KeyEvent;

import copper.*;
import copper.entities.items.Container;
import copper.entities.particles.*;
import copper.graphics.*;

public class Editor extends Entity {
	
	private boolean sprinting = false;
	
	public Editor(double x, double y, double z) {
		super(x, y, z);
		Screen.setFocus(this);
		speed 		= 60;
	}
	
	public void tick() {
		super.tick();
		dx = 0;
		dy = 0;
		double speed;
		
		if (keys[KeyEvent.VK_SHIFT]) sprinting = true;
		else sprinting = false;
		if (sprinting) speed = runningSpeed;
		else speed = this.speed;
		
		if (keys[KeyEvent.VK_W]) dy -= speed;
		if (keys[KeyEvent.VK_S]) dy += speed;
		if (keys[KeyEvent.VK_A]) dx -= speed;
		if (keys[KeyEvent.VK_D]) dx += speed;
		
		move(dx, 0);
		move(0, dy);
	}
	
	public void render(Screen s) {
	}
	
	public boolean isGhost() {
		return true;
	}
	
	public boolean isPushable() {
		return false;
	}

}
