package copper.gui;

import copper.graphics.*;

public class Label extends Component {
	
	public String text;
	
	public Label(int x, int y, String text, boolean centeredX) {
		this.text = text;
		width = text.length() * Sprite.font.width;
		height = Sprite.font.height;
		if (centeredX) {
			int xCentered = x - width / 2;
			int yCentered = y - height / 2;
			setBounds(xCentered, yCentered, width, height);
		}else {
			setBounds(x, y, width, height);
		}
	}
	
	public void render(int[][] pixels) {
		Sprite.renderText(pixels, Sprite.font, text, 0xFFFFFF, x + xOrigin, y + yOrigin);
	}
	
}
