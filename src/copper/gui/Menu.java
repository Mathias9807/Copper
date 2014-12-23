package copper.gui;

import java.awt.event.KeyEvent;
import copper.graphics.Screen;
import static copper.Panel.pressedKeys;

public class Menu {
	
	public static Menu current = new InterfaceMenu();
	public boolean freezeWorld = false;
	
	public void tick() {
		if (pressedKeys.contains(KeyEvent.VK_ESCAPE)) {
			freezeWorld = freezeWorld ? false : true;
		}
	}

	public void render(Screen s) {
	}
	
}
