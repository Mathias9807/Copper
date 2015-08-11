package copper.gui;

import com.sun.glass.events.KeyEvent;

import copper.*;
import copper.graphics.*;
import copper.levels.Level;

public class EditorMenu extends Menu {
	
	private static final int SPRITE_SIZE = 8;
	
	private Window terrain;
	
	private int selectedTile = 0;
	
	public EditorMenu() {
		freezeWorld = false;
		
		terrain = new Window(0, 0, 
				SPRITE_SIZE * Sprite.terrain.getSpriteSheet().length, 
				SPRITE_SIZE * Sprite.terrain.getSpriteSheet()[0].length, 
				"Terrain");
		
		terrain.function = () -> {
			selectedTile = (Panel.getMouseX() - terrain.x) / SPRITE_SIZE;
			selectedTile += (Panel.getMouseY() - terrain.y 
					- Window.WINDOW_BAR_HEIGHT + 1) / SPRITE_SIZE 
					* Sprite.terrain.getSpriteSheet().length;
		};
	}
	
	public void tick() {
		super.tick();

		terrain.tick();
		
		if ((Panel.mButtons[0] || Panel.pressedMButtons.contains(0)) 
				&& !terrain.isInside(Panel.getMouseX(), Panel.getMouseY())) 
			Copper.level.setTile(selectedTile, 
					(Panel.getMouseX() + Screen.getCamX()) / Level.TILE_SIZE, 
					(Panel.getMouseY() + Screen.getCamY()) / Level.TILE_SIZE);
		
		if (Panel.pressedKeys.contains(KeyEvent.VK_ESCAPE)) {
			Copper.EDITOR_MODE = false;
			Level.entities.remove(Panel.editor);
			Screen.setFocus(Level.players.get(0));
			
			current = new PauseMenu();
		}
	}

	public void render(Screen s) {
		Sprite[][] spr = Sprite.terrain.getSpriteSheet();

		for (int x = 0; x < spr.length; x++) 
			for (int y = 0; y < spr[0].length; y++) 
				spr[x][y].render(terrain.pixels, 
						x * SPRITE_SIZE, y * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
		
		Sprite.renderRect(
			terrain.pixels, 0xFFFF00, 
			(selectedTile % spr.length) * SPRITE_SIZE, 
			(selectedTile / spr.length) * SPRITE_SIZE, 
			SPRITE_SIZE, SPRITE_SIZE
		);
		terrain.render(s.pixels);
	}

}
