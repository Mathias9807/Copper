package copper.gui;

import static copper.Panel.pressedKeys;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import copper.graphics.Screen;

public class Menu {
	
	public static Menu current = new InterfaceMenu();
	public boolean freezeWorld = false;
	
	public ArrayList<Component> components = new ArrayList<Component>();
	
	public void tick() {
		if (pressedKeys.contains(KeyEvent.VK_ESCAPE)) {
			if (current instanceof PauseMenu) current = new InterfaceMenu();
			else current = new PauseMenu();
		}
		
		current.tickComponents();
	}
	
	public void initComponents() {
	}
	
	protected void tickComponents() {
	}
	
	public void buttonClicked(int id) {
	}

	public void render(Screen s) {
		for (Component c : components) {
			c.render(s);
		}
	}
	
}
