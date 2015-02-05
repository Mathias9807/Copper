package copper.gui;

import java.util.ArrayList;

import copper.graphics.*;

public class Console extends Component {
	
	public ArrayList<String> history;
	
	public Console(int x, int y, int w, int h) {
		setBounds(x, y, w, h);
	}
	
	public void render(Screen s) {
		Sprite.fillRect(s.pixels, 0xAAAAAA, x, y, width, height);
	}
	
}
