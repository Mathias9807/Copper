package copper.gui;

import copper.Copper;


public class PauseMenu extends Menu {
	
	public Button b0;
	public Console c;
	
	public PauseMenu() {
		freezeWorld = true;
		initComponents();
	}
	
	public void initComponents() {
		components.add(new Label(Copper.WIDTH / 2, 8, "Paused", true));
		components.add(b0 = new Button("Console", Copper.WIDTH / 2, 32, 0, this));
	}
	
	protected void tickComponents() {
		b0.checkClicks();
		if (c != null) c.tick();
	}
	
	public void buttonClicked(int id) {
		switch (id) {
		case 0: 
			if (c == null) 
				components.add(c = new Console(16, 16));
			break;
		}
	}
	
}
