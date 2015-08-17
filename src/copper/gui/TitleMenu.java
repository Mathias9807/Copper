package copper.gui;

import java.awt.event.KeyEvent;

import copper.*;
import copper.graphics.*;


public class TitleMenu extends Menu {
	
	public Button newButton, loadButton;
	public Button quitButton;
	
	public TitleMenu() {
		freezeWorld = true;
		initComponents();
		
		Audio.playAndForget(Audio.spawn, false);
	}
	
	public void initComponents() {
		int buttons = 3, offs = 16;
		
		components.add(newButton = new Button("New", Copper.WIDTH / 2, buttons++ * offs, 0, new Functional() {
			public void call() {
				current = new InterfaceMenu();
				Audio.resumeAll();
				Panel.pressedMButtons.clear();
				Copper.level.loadLevel("/demo.txt");
				
				Audio.playAndForget(Audio.bop, false);
			}
		}));
		
		components.add(loadButton = new Button("Load", Copper.WIDTH / 2, buttons++ * offs, 0, new Functional() {
			public void call() {
				current = new InterfaceMenu();
				Audio.resumeAll();
				Panel.pressedMButtons.clear();
				Copper.level.loadLevel("save0.txt");
				
				Audio.playAndForget(Audio.bop, false);
			}
		}));
		
		components.add(quitButton = new Button("Quit", Copper.WIDTH / 2, ++buttons * offs, 1, new Functional() {
			public void call() {
				Audio.hit.play();
				Copper.engine.stop();
			}
		}));
	}
	
	public void tick() {
		super.tick();
		
		if (Panel.pressedKeys.contains(KeyEvent.VK_ESCAPE)) {
			System.out.println("TODO: Add exit in TitleMenu.java");
		}
	}

	public void render(Screen s) {
		super.render(s);
		
		Sprite.title.render(s.pixels, Copper.WIDTH / 2 - Sprite.title.width / 2, 12);
	}
	
}
