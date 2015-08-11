package copper.gui;

import copper.*;
import copper.graphics.Sprite;

public class Button extends Component {
	
	private String 		text;
	private Functional 	action;
	private int 		xCenter, yCenter;
	private boolean 	selected;
	
	public Button(String text, double x, double y, int id, Functional action) {
		width 			= Sprite.font.width * text.length() + 2;
		height 			= Sprite.font.height + 2;
		this.text 		= text;
		this.xCenter	= (int) x;
		this.yCenter	= (int) y;
		this.x			= (int) (x - width / 2) - 1;
		this.y			= (int) (y - height / 2) - 1;
		this.action 	= action;
		selected		= false;
	}
	
	public void checkClicks() {
		selected = isInside(Panel.getMouseX(), Panel.getMouseY());
		
		if (selected && Panel.pressedMButtons.contains(0)) action.call();
	}
	
	public void render(int[][] pixels) {
		Sprite.fillRect(pixels, 0x404030, x, y, width, height);
		Sprite.renderText(pixels, Sprite.font, text, selected ? 0xFFFF00 : 0xFFFFFF, x + xOrigin + 1, y + yOrigin + 1);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
