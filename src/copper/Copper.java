package copper;

import com.apple.eawt.*;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import copper.graphics.Screen;
import copper.levels.Level;

public class Copper extends Canvas implements Runnable {

	private static final long serialVersionUID 		= 1L;
	
	/**
	 * The current operative system. 
	 */
	public static final String		OS				= System.getProperty("os.name").toLowerCase();
	
	public static final String		TITLE			= "Copper Alpha";

	public static final boolean 	FULLSCREEN 		= OS.contains("mac") ? false : false;
	public static final DisplayMode DISPLAYMODE 	= GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice().getDisplayMode();
	public static final boolean 	FPS_LOCK		= true;
	
	public static final int 		SCALE 			= 4;
	public static final double 		ASPECT_RATIO	= (double) DISPLAYMODE.getHeight() / DISPLAYMODE.getWidth();
	public static final int 		WIDTH			= FULLSCREEN ? DISPLAYMODE.getWidth() / SCALE : 200;
	public static final int 		HEIGHT			= FULLSCREEN ? DISPLAYMODE.getHeight() / SCALE : (int) (WIDTH * ASPECT_RATIO);
	public static final long 		START_TIME 		= System.currentTimeMillis();
	
	public static final boolean 	DEBUG_MODE		= false;
	
	public static JFrame 			window;
	
	public static boolean 			focused;

	public static Level 			level;

	private Thread 					thread;
	private BufferedImage 			img;
	private boolean 				running 		= false;
	private int[] 					pixels;
	private Panel 					panel;
	private Screen 					screen;

	public Copper() {
		System.out.println("Setting up Engine components");
		level = new Level();
		level.loadLevel("/demo.txt");
		
		if (!FULLSCREEN) {
			Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
			setSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setPreferredSize(size);
		}
		
		img 		= new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels 		= ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		panel 		= new Panel();
		screen 		= new Screen(WIDTH, HEIGHT);

		System.out.println("Initializing Input");
		addKeyListener(panel);
		addFocusListener(panel);
		addMouseListener(panel);
		addMouseMotionListener(panel);
	}

	public void start() {
		System.out.println("Starting Thread");
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
		requestFocus();
		focused = true;
	}

	public void run() {
		System.out.println("Entering Gameloop");
		long pastTime 	= System.nanoTime();
		double tickTimeMs = 1000.0 / 60;
		int fps = 0;
		double secondTimer = 0;
		while (running) {
			long now 	= System.nanoTime();
			if (FPS_LOCK) {
				if (tickTimeMs - (now - pastTime) / 1E9D * 1000 > 0)
					try {
						Thread.sleep((long) (tickTimeMs - (now - pastTime) / 1E9D * 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			Panel.delta = (now - pastTime) / 1E9D;
			pastTime 	= now;
			
			secondTimer += Panel.delta;
			while (secondTimer >= 1) {
				System.out.println("FPS: " + fps);
				secondTimer--;
				fps = 0;
			}
			
			if (focused) {
				update();
				render();
				fps++;
			}
		}
	}
	
	public void update(){
		panel.tick();
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null){
			createBufferStrategy(3);
			return;
		}
		
		screen.render();
		
		for (int x = 0; x < WIDTH; x++){
			for (int y = 0; y < HEIGHT; y++){
				pixels[x + y * WIDTH] = screen.pixels[x][y];
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("OS: " + OS);
		Copper engine = new Copper();
		
		window = new JFrame("Copper Alpha");
		window.add(engine);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (FULLSCREEN) window.setUndecorated(true);
		try {
			if (OS.contains("mac")) {
				Application.getApplication().setDockIconImage(ImageIO.read(Copper.class.getClass().getResourceAsStream("/icon.png")));
			}else {
				ArrayList<Image> images = new ArrayList<Image>();
				images.add(ImageIO.read(Copper.class.getClass().getResourceAsStream("/icon.png")));
				images.add(ImageIO.read(Copper.class.getClass().getResourceAsStream("/icon small.png")));
				Copper.window.setIconImages(images);
			}
		} catch (IOException e) {
			System.err.println("Failed to set the programs icon. :(");
			e.printStackTrace();
		}
		window.pack();
		window.setLocationRelativeTo(null);
		window.setResizable(true);
		window.setVisible(true);
		
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gDevice = gEnv.getDefaultScreenDevice();
		if (FULLSCREEN) gDevice.setFullScreenWindow(window);
		
		engine.start();
	}

}
