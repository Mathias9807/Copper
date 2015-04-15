package copper.gui;

import java.awt.event.KeyEvent;

import copper.*;
import copper.graphics.Sprite;


public class PauseMenu extends Menu {
	
	public Button b0;
	public Button b1;
	public Window console;
	
	public PauseMenu() {
		freezeWorld = true;
		Audio.pauseAll();
		initComponents();
	}
	
	public void initComponents() {
		components.add(new Label(Copper.WIDTH / 2, 8, "Paused", true));
		
		components.add(b0 = new Button("Console", Copper.WIDTH / 2, 32, 0, new Functional() {
			public void call() {
				Audio.hit.play();
				if (console == null) {
					components.add(console = new Window(0, 0, 50, 45));
					console.subComponents.add(new TextField(2, 2, 47, Sprite.fontSmall));
				}
			}
		}));
		
		components.add(b1 = new Button("Exit", Copper.WIDTH / 2, 64, 1, new Functional() {
			public void call() {
				Audio.hit.play();
				Copper.engine.stop();
			}
		}));
	}
	
	protected void tickComponents() {
		b0.checkClicks();
		b1.checkClicks();
		if (console != null) console.tick();
		
		if (Panel.pressedKeys.contains(KeyEvent.VK_ESCAPE)) {
			current = new InterfaceMenu();
			Audio.resumeAll();
		}
	}
	
}
