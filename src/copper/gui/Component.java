package copper.gui;

import copper.graphics.*;


/**
 * The Component class is the superclass of every item in a menu, e.g. buttons.  
 * @author Mathias Johansson
 *
 */
public class Component {
	
	protected int x, y, width, height, xOrigin, yOrigin;
	
	public void tick() {
	}
	
	public void render(int[][] pixels) {
	}
	
	public void setBounds(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		width  = w;
		height = h;
	}
	
	public void setOrigin(int x, int y) {
		xOrigin = x;
		yOrigin = y;
	}

	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setWidth(int w) { width = w; }
	public void setHeight(int h) { height = h; }
	
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
}
