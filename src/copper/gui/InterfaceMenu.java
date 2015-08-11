package copper.gui;

import com.sun.glass.events.KeyEvent;

import copper.*;
import copper.entities.Entity;
import copper.graphics.*;

public class InterfaceMenu extends Menu {
	
	private static final int itemXPos = Copper.WIDTH - 16, itemYPos = 8, itemSpacing = 12;
	private int healthXOffs = 5, healthYOffs = 5;
	private double currentHealth = 0;
	
	public InterfaceMenu() {
		freezeWorld = false;
	}
	
	public void tick() {
		super.tick();
		
		Entity focused = Screen.getFocusedEntity();
		if (currentHealth >= focused.getMaxHealth()) 
			currentHealth = focused.getMaxHealth() - 1;
		
		double newHealth = focused.getHealth();
		if (newHealth > currentHealth) {
			currentHealth += Math.min((newHealth - currentHealth) * 8 * Panel.delta, newHealth - currentHealth);
		}else if (newHealth < currentHealth) {
			currentHealth -= Math.min(150 * Panel.delta, currentHealth - newHealth);
		}
		
		if (Panel.pressedKeys.contains(KeyEvent.VK_ESCAPE)) 
			current = new PauseMenu();
	}

	public void render(Screen s) {
		Sprite.healthBar.getSprite(0, 0).renderSprite(s.pixels, healthXOffs + Screen.getCamX(), healthYOffs + Screen.getCamY());
		for (int i = 1; i <= 3; i++) 
			Sprite.healthBar.getSprite(1, 0).renderSprite(s.pixels, 
					healthXOffs + Screen.getCamX() + i * 8, healthYOffs + Screen.getCamY());
		
		Sprite.healthBar.getSprite(2, 0).renderSprite(s.pixels, 
				healthXOffs + Screen.getCamX() + 4 * 8, healthYOffs + Screen.getCamY());
		for (int x = healthXOffs + 1; x < (currentHealth / 
				(double) Screen.getFocusedEntity().getMaxHealth()) * (healthXOffs + 16 + 3 * 8 - 1); x++) {
			if (x < 0) continue;
			if (x >= Copper.WIDTH) break;
			
			for (int y = healthYOffs + 1; y < healthYOffs + 7; y++) {
				if (y < 0) continue;
				if (y >= Copper.HEIGHT) break;
				
				s.pixels[x][y] = 0xFF0000;
			}
		}
		
		Sprite.renderText(s.pixels, Sprite.fontSmall, 
				(int) Screen.getFocusedEntity().getHealth() + "/" + Screen.getFocusedEntity().getMaxHealth(), 
				0xFFFFFF, healthXOffs + 1, healthYOffs + 2);
		
		Entity focused = Screen.getFocusedEntity();
		for (int i = 0; i < focused.inventory.size(); i++) {
			if (focused.inventory.get(i) != null) 
				focused.inventory.get(i).sprite.renderSprite(s.pixels, 
						itemXPos + Screen.getCamX(), itemYPos + i * itemSpacing + Screen.getCamY());
		}
	}

}
