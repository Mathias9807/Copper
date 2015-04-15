package copper.gui;

import copper.*;
import copper.graphics.Sprite;

public class Button extends Component {
	
	private String 		text;
	private Functional 	action;
	private int 		xCenter, yCenter, x, y, width, height;
	private boolean 	selected;
	
	public Button(String text, double x, double y, int id, Functional action) {
		width 			= Sprite.font.width * text.length();
		height 			= Sprite.font.height;
		this.text 		= text;
		this.xCenter	= (int) x;
		this.yCenter	= (int) y;
		this.x			= (int) (x - width / 2);
		this.y			= (int) (y - height / 2);
		this.action 	= action;
		selected		= false;
	}
	
	public void checkClicks() {
		if (Panel.getMouseX() / Copper.SCALE >= x - 1 && Panel.getMouseX() / Copper.SCALE < x + width + 2 
				&& Panel.getMouseY() / Copper.SCALE >= y - 1 && Panel.getMouseY() / Copper.SCALE < y + height + 2) 
			selected = true;
		else selected = false;
		if (Panel.pressedMButtons.contains(0) && selected) action.call();
	}
	
	public void render(int[][] pixels) {
		Sprite.fillRect(pixels, 0x404030, x - 1 + xOrigin, y - 1 + yOrigin, width + 2, height + 2);
		Sprite.renderText(pixels, Sprite.font, text, selected ? 0xFFFF00 : 0xFFFFFF, x + xOrigin, y + yOrigin);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
