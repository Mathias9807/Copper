package copper.gui;

import copper.Copper;


public class PauseMenu extends Menu {
	
	public Button b0;
	
	public PauseMenu() {
		freezeWorld = true;
		initComponents();
	}
	
	public void initComponents() {
		b0 = new Button("Console", Copper.WIDTH / 2, 8, 0, this);
		components.add(b0);
	}
	
	protected void tickComponents() {
		b0.checkClicks();
	}
	
	public void buttonClicked(int id) {
		switch (id) {
		case 0: 
			components.add(new Console(16, 16, 64, 64));
			break;
		}
	}
	
}
