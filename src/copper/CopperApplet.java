package copper;

import javax.swing.JApplet;

public class CopperApplet extends JApplet{
	private static final long serialVersionUID = 1L;
	
	private Copper game;
	
	public void init(){
		game = new Copper();
		setSize(Copper.WIDTH * Copper.SCALE, Copper.HEIGHT * Copper.SCALE);
		add(game);
	}
	
	public void start(){
		game.start();
	}
	
	public void stop(){
		game.stop();
	}

}
