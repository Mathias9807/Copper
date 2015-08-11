package copper.entities;

import static copper.Panel.keys;

import java.awt.event.KeyEvent;

import copper.*;
import copper.graphics.*;

public class Editor extends Entity {
	
	public Editor(double x, double y, double z) {
		super(x, y, z);
		Screen.setFocus(this);
		Copper.EDITOR_MODE 	= true;
		Panel.editor 		= this;
		speed 				= 60;
		setShadow(null);
	}
	
	public void tick() {
		dx = 0;
		dy = 0;
		
		if (keys[KeyEvent.VK_W]) dy -= speed;
		if (keys[KeyEvent.VK_S]) dy += speed;
		if (keys[KeyEvent.VK_A]) dx -= speed;
		if (keys[KeyEvent.VK_D]) dx += speed;
		
		x += dx * Panel.delta;
		y += dy * Panel.delta;
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
