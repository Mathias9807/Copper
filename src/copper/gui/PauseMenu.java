package copper.gui;

import java.awt.event.KeyEvent;

import copper.*;
import copper.entities.Editor;


public class PauseMenu extends Menu {
	
	public Button continueButton;
	public Button saveButton;
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
		
		int buttons = 2, offs = 16;
		
		components.add(continueButton = new Button("Continue", Copper.WIDTH / 2, buttons++ * offs, 0, new Functional() {
			public void call() {
				current = new InterfaceMenu();
				Audio.resumeAll();
				Panel.pressedMButtons.clear();
			}
		}));
		
		components.add(saveButton = new Button("Save", Copper.WIDTH / 2, buttons++ * offs, 0, new Functional() {
			public void call() {
				Audio.bop.play();
				Copper.level.writeLevel();
			}
		}));
		
		components.add(editorButton = new Button("Editor", Copper.WIDTH / 2, buttons++ * offs, 1, new Functional() {
			public void call() {
				new Editor(0, 0, 1);
				current = new EditorMenu();
			}
		}));
		
		components.add(exit = new Button("Exit", Copper.WIDTH / 2, ++buttons * offs, 1, new Functional() {
			public void call() {
				current = new TitleMenu();
			}
		}));
	}
	
	protected void tickComponents() {
		continueButton.checkClicks();
		saveButton.checkClicks();
		exit.checkClicks();
		editorButton.checkClicks();
		if (console != null) console.tick();
		
		if (Panel.pressedKeys.contains(KeyEvent.VK_ESCAPE)) {
			current = new InterfaceMenu();
			Audio.resumeAll();
		}
	}
	
}
