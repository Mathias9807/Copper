package copper.gui;

import java.util.ArrayList;

import copper.graphics.*;

public class Console extends Component {
	
	public ArrayList<String> 	history = new ArrayList<String>();
	public TextField			textField = new TextField(4, 4, 56, 8);
	
	public Console(int x, int y) {
		setBounds(x, y, 64, 64);
		textField.setOrigin(x, y);
	}
	
	public void tick() {
		textField.tickTextField();
	}
	
	public void render(Screen s) {
		Sprite.fillRect(s.pixels, 0x444444, x, y, width, height);
		textField.render(s);
	}
	
}
