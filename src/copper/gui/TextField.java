package copper.gui;

import copper.Panel;
import copper.graphics.*;

public class TextField extends Component {
	
	StringBuilder 	text = new StringBuilder();
	SpriteSheet		font;
	
	public TextField(int x, int y, int w, SpriteSheet f) {
		setBounds(x, y, w, f.height + 1);
		font = f;
	}
	
	public void tick() {
		while (Panel.typedKeys.size() > 0) {
			char key = Panel.typedKeys.get(0);
			text.append(key);
			Panel.typedKeys.remove(0);
		}
		
		if (Panel.backspaceHit && text.length() > 0) text.deleteCharAt(text.length() - 1);
	}
	
	public void render(int[][] pixels) {
		Sprite.fillRect(pixels, 0xEEEEEE, x - 1 + xOrigin, y - 1 + yOrigin, width + 1, font.height + 1);
		Sprite.renderText(pixels, font, text.toString(), 0x000000, x + xOrigin, y + yOrigin);
	}
	
}
