package copper.gui;

import java.util.ArrayList;

import copper.Panel;
import copper.graphics.Screen;

public class Menu {
	
	public static Menu current = new TitleMenu();
	public boolean freezeWorld = false;
	
	public ArrayList<Component> components = new ArrayList<Component>();
	
	public void tick() {
		current.tickComponents();
	}
	
	public void initComponents() {
	}
	
	protected void tickComponents() {
		for (int i = 0; i < components.size(); i++) 
			components.get(i).selected = false;
		
		for (int i = components.size() - 1; i >= 0; i--) {
			Component c = components.get(i);
			
			if (c.isInside(Panel.getMouseX(), Panel.getMouseY())) {
				c.selected = true;
				break;
			}
		}
		
		for (int i = 0; i < components.size(); i++) 
			components.get(i).tick();
	}
	
	public void buttonClicked(int id) {
	}

	public void render(Screen s) {
		for (Component c : components) {
			c.render(s.pixels);
		}
	}
	
}
