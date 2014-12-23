package copper.gui;

import copper.graphics.*;

public class InterfaceMenu extends Menu {
	
	private int healthXOffs = 5, healthYOffs = 5;

	public void render(Screen s) {
		Sprite.healthBar.getSprite(0, 0).renderSprite(s, healthXOffs + Screen.getCamX(), healthYOffs + Screen.getCamY());
		for (int i = 1; i <= 3; i++) {
			Sprite.healthBar.getSprite(1, 0).renderSprite(s, healthXOffs + Screen.getCamX() + i * 8, healthYOffs + Screen.getCamY());
		}
		Sprite.healthBar.getSprite(2, 0).renderSprite(s, healthXOffs + Screen.getCamX() + 4 * 8, healthYOffs + Screen.getCamY());
		for (int x = healthXOffs + 1; x < (Screen.getFocusedEntity().getHealth() / 
				(double) Screen.getFocusedEntity().getMaxHealth()) * (healthXOffs + 16 + 3 * 8 - 1); x++) {
			for (int y = healthYOffs + 1; y < healthYOffs + 7; y++) {
				s.pixels[x][y] = 0xFF0000;
			}
		}
	}

}
