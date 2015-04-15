package copper.gui;

import java.util.ArrayList;

import copper.*;
import copper.graphics.Sprite;

/**
 * A small window that can hold additional components and be moved around. Dies when the current menu is exited. 
 * @author Mathias Johansson
 *
 */

public class Window extends Component {
	
	public static final int WINDOW_BAR_HEIGHT = 5;
	public static final int WINDOW_BAR_COLOR = 0x8888AA;
	
	public int[][] pixels;
	public ArrayList<Component> subComponents = new ArrayList<Component>();
	
	private int mouseStartX, mouseStartY, windowStartX, windowStartY;
	private boolean mouseGrabbed;
	
	public Window(int x, int y, int w, int h) {
		setBounds(x, y, w, h);
		mouseGrabbed = false;
		pixels = new int[w][h - WINDOW_BAR_HEIGHT];
	}
	
	public void tick() {
		if (Panel.pressedMButtons.contains(0)) {
			mouseStartX = Panel.getMouseX();
			mouseStartY = Panel.getMouseY();
			windowStartX = x;
			windowStartY = y;
			if (Panel.getMouseX() / Copper.SCALE >= x && Panel.getMouseY() / Copper.SCALE >= y
					&& Panel.getMouseX() / Copper.SCALE < x + width 
					&& Panel.getMouseY() / Copper.SCALE < y + WINDOW_BAR_HEIGHT) 
				mouseGrabbed = true;
		}
		if (Panel.mButtons[0] && mouseGrabbed) {
			int deltaX = Panel.getMouseX() / Copper.SCALE - mouseStartX / Copper.SCALE;
			int deltaY = Panel.getMouseY() / Copper.SCALE - mouseStartY / Copper.SCALE;
			x = windowStartX + deltaX;
			y = windowStartY + deltaY;
		}else 
			mouseGrabbed = false;
		
		for (int i = 0; i < subComponents.size(); i++) {
			
			subComponents.get(i).tick();
		}
	}
	
	public void render(int[][] pixels) {
		for (int i = 0; i < subComponents.size(); i++) {
			subComponents.get(i).render(this.pixels);
		}
		
		Sprite.fillRect(pixels, WINDOW_BAR_COLOR, x, y, width, WINDOW_BAR_HEIGHT);
		
		for (int x = 0; x < width; x++) {
			if (x + this.x < 0) continue;
			if (x + this.x >= pixels.length) break;
			
			for (int y = 0; y < height - WINDOW_BAR_HEIGHT; y++) {
				if (y + this.y + WINDOW_BAR_HEIGHT < 0) continue;
				if (y + this.y + WINDOW_BAR_HEIGHT >= pixels[0].length) break;
				
				pixels[x + this.x][y + this.y + WINDOW_BAR_HEIGHT] = this.pixels[x][y];
			}
		}
	}

}
