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
	
	public static final int 	WINDOW_BAR_HEIGHT = 7;
	public static final int 	WINDOW_BAR_COLOR = 0x8888AA;
	
	public String 				name;
	public int[][] 				pixels;
	public int					bgColor;
	public ArrayList<Component> subComponents = new ArrayList<Component>();
	
	public Functional			function = () -> {};
	
	private int mouseStartX, mouseStartY, windowStartX, windowStartY;
	private boolean mouseGrabbed;
	
	public Window(int x, int y, int w, int h, String name) {
		setBounds(x, y, w, h);
		mouseGrabbed = false;
		pixels = new int[w][h - WINDOW_BAR_HEIGHT];
		this.name = name;
	}
	
	public Window(int x, int y, int w, int h) {
		this(x, y, w, h, "");
	}
	
	public void tick() {
		if (Panel.pressedMButtons.contains(0)) {
			mouseStartX = Panel.getMouseX();
			mouseStartY = Panel.getMouseY();
			windowStartX = x;
			windowStartY = y;
			if (selected 
					&& mouseStartX >= x && mouseStartX < width + x
					&& mouseStartY >= y && mouseStartY < y + WINDOW_BAR_HEIGHT) 
				mouseGrabbed = true;
		}
		if (Panel.mButtons[0] && mouseGrabbed) {
			int deltaX = Panel.getMouseX() - mouseStartX;
			int deltaY = Panel.getMouseY() - mouseStartY;
			x = windowStartX + deltaX;
			y = windowStartY + deltaY;
		}else 
			mouseGrabbed = false;
		
		if (Panel.pressedMButtons.contains(0) && selected) 
			function.call();
		
		y = y < 0 ? 0 : y;
		y = y >= Copper.HEIGHT - WINDOW_BAR_HEIGHT ? Copper.HEIGHT - WINDOW_BAR_HEIGHT : y;
		
		for (int i = 0; i < subComponents.size(); i++) 
			subComponents.get(i).selected = false;
		
		for (int i = subComponents.size() - 1; i >= 0; i--) {
			Component c = subComponents.get(i);
			
			if (c.isInside(Panel.getMouseX() - x, Panel.getMouseY() - (y + WINDOW_BAR_HEIGHT))) {
				c.selected = true;
				break;
			}
		}
		
		for (int i = 0; i < subComponents.size(); i++) 
			subComponents.get(i).tick();
	}
	
	public void render(int[][] pixels) {
		Sprite.fillRect(this.pixels, bgColor, 0, 0, width, height - WINDOW_BAR_HEIGHT);
		for (int i = 0; i < subComponents.size(); i++) {
			subComponents.get(i).render(this.pixels);
		}
		
		Sprite.fillRect(pixels, WINDOW_BAR_COLOR, x, y, width, WINDOW_BAR_HEIGHT);
		Sprite.renderText(pixels, Sprite.fontSmall, name, 
				0xEEEEEE, x, y + 1);
		
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
