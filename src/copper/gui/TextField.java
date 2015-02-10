package copper.gui;

import copper.Panel;
import copper.graphics.*;

public class TextField extends Component {
	
	StringBuilder text = new StringBuilder();
	
	public TextField(int x, int y, int w, int h) {
		setBounds(x, y, w, h);
	}
	
	public void tickTextField() {
		while (Panel.typedKeys.size() > 0) {
			char key = Panel.typedKeys.get(0);
			text.append(key);
			Panel.typedKeys.remove(0);
		}
		
		if (Panel.backspaceHit && text.length() > 0) text.deleteCharAt(text.length() - 1);
	}
	
	public void render(Screen s) {
		Sprite.fillRect(s.pixels, 0xEEEEEE, x - 1 + xOrigin, y - 1 + yOrigin, width + 1, height + 1);
		Sprite.renderText(s.pixels, text.toString(), 0x000000, x + xOrigin, y + yOrigin);
	}
	
}
