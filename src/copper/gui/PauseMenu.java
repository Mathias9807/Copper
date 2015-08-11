package copper.gui;

import java.awt.event.KeyEvent;

import copper.*;
import copper.entities.Editor;
import copper.graphics.*;


public class PauseMenu extends Menu {
	
	public Button continueButton;
	public Button consoleButton;
	public Button editorButton;
	public Button exit;
	public Window console;
	
	public PauseMenu() {
		freezeWorld = true;
		Audio.pauseAll();
		initComponents();
	}
	
	public void initComponents() {
		components.add(new Label(Copper.WIDTH / 2, 8, "Paused", true));
		
		components.add(continueButton = new Button("Continue", Copper.WIDTH / 2, 32, 0, new Functional() {
			public void call() {
				current = new InterfaceMenu();
				Audio.resumeAll();
				Panel.pressedMButtons.clear();
			}
		}));
		
		components.add(consoleButton = new Button("Console", Copper.WIDTH / 2, 48, 0, new Functional() {
			public void call() {
				Audio.hit.play();
				if (console == null) {
					components.add(console = new Window(0, 0, 50, 45));
					console.subComponents.add(new TextField(2, 2, 47, Sprite.fontSmall));
				}
			}
		}));
		
		components.add(editorButton = new Button("Editor", Copper.WIDTH / 2, 64, 1, new Functional() {
			public void call() {
				new Editor(0, 0, 1);
				current = new EditorMenu();
			}
		}));
		
		components.add(exit = new Button("Exit", Copper.WIDTH / 2, 96, 1, new Functional() {
			public void call() {
				Audio.hit.play();
				Copper.engine.stop();
			}
		}));
	}
	
	protected void tickComponents() {
		continueButton.checkClicks();
		consoleButton.checkClicks();
		exit.checkClicks();
		editorButton.checkClicks();
		if (console != null) console.tick();
		
		if (Panel.pressedKeys.contains(KeyEvent.VK_ESCAPE)) {
			current = new InterfaceMenu();
			Audio.resumeAll();
		}
	}
	
}
