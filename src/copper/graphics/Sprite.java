package copper.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Sprite {

//	Tiles
	public static final SpriteSheet terrain 	= new SpriteSheet("/tiles/terrain.png", 16, 16);
	
//	Entities
	public static final Sprite 	player			= new Sprite("/entities/player.png");
	public static final Sprite 	box 			= new Sprite("/entities/box.png");
	public static final Sprite 	crate 			= new Sprite("/entities/crate.png");
	public static final Sprite 	david 			= new Sprite("/entities/david.png");
	
	public static final SpriteSheet ghost		= new SpriteSheet("/entities/ghost.png", 16, 16);
	public static final SpriteSheet wizard		= new SpriteSheet("/entities/wizard.png", 10, 12);
	public static final SpriteSheet zombie		= new SpriteSheet("/entities/zombie.png", 14, 16);
	
//	Particles
	public static final Sprite 	flame			= new Sprite("/particles/flame.png");
	public static final Sprite 	boulder			= new Sprite("/particles/boulder.png");
	public static final Sprite 	ball			= new Sprite("/particles/ball.png");
	public static final Sprite 	snowball		= new Sprite("/particles/snowball.png");
	public static final Sprite 	magic			= new Sprite("/particles/magic.png");
	
//	Miscellaneous
	public static final Sprite 	shadow			= new Sprite("/shadow.png");
	public static final Sprite 	shadowTiny		= shadow.getSubSprite(28, 0, 3, 1);
	public static final Sprite 	shadowSmall		= shadow.getSubSprite(24, 0, 4, 3);
	public static final Sprite 	shadowNormal	= shadow.getSubSprite(16, 0, 8, 4);
	public static final Sprite 	shadowLarge		= shadow.getSubSprite(0, 0, 16, 8);
	
	public static final SpriteSheet healthBar	= new SpriteSheet("/gui/health bar.png", 8, 8);
	
	public static final SpriteSheet font		= new SpriteSheet("/gui/font.png", 6, 8);
	public static final SpriteSheet fontSmall	= new SpriteSheet("/gui/font small.png", 4, 5);

	public static HashMap<Character, Integer> characters = createCharacterHashMap();

	private static final int 	NON_OPAQUE_COLOR = 0xFF00FF;
	
	protected int[][] buffer;
	public int width, height;

	public Sprite(int w, int h, int color) {
		buffer = new int[w][h];
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				buffer[x][y] = color;
			}
		}
		width = w;
		height = h;
	}

	public Sprite(String path) {
		BufferedImage b;
		try {
			b = ImageIO.read(this.getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			b = null;
		}
		width = b.getWidth();
		height = b.getHeight();
		buffer = new int[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				buffer[x][y] = b.getRGB(x, y);
			}
		}
	}

	public void renderLevel(int[][] pixelBuffer, int x, int y) {
		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				pixelBuffer[x + xx][y + yy] = buffer[xx][yy];
			}
		}
	}

	public void renderSprite(Screen screen, int x, int y) {
		int xPos = x - Screen.getCamX(), yPos = y - Screen.getCamY();
		
		for (int xx = 0; xx < width; xx++) {
			if (xPos + xx < 0 || xPos + xx >= screen.pixels.length) continue;
			
			for (int yy = 0; yy < height; yy++) {
				if (yPos + yy < 0 || yPos + yy >= screen.pixels[0].length) continue;
				
				int color = buffer[xx][yy];
				if ((color & 0xFFFFFF) == NON_OPAQUE_COLOR) continue; // Alpha testing.
				
				screen.pixels[xPos + xx][yPos + yy] = color;
			}
		}
	}

	public void renderSprite(Screen screen, int x, int y, double angle) {
//		int[][] temp = new int[(int) (width * 1.4)][(int) (height * 1.4)];
		int xPos = x - Screen.getCamX(), yPos = y - Screen.getCamY();
		angle = Math.toRadians(angle);
		
		for (int xx = -width / 2; xx < width / 2; xx++) {
			double xCos = xx * Math.cos(angle);
			double xSin = xx * Math.sin(angle);
			if (xPos + xx < 0 || xPos + xx >= screen.pixels.length) continue;
			
			for (int yy = -height / 2; yy < height / 2; yy++) {
				int color = this.getBuffer()[xx + width / 2][yy + height / 2];
				if ((color & 0xFFFFFF) == NON_OPAQUE_COLOR) continue; // Alpha testing.
				
				double yCos = yy * Math.cos(angle);
				double ySin = yy * Math.sin(angle);
				int xRot = (int) Math.round(xPos + (xCos + ySin) + width / 2);
				int yRot = (int) Math.round(yPos + (yCos - xSin) + height / 2);
				
				if (xRot < 0 || xRot >= screen.pixels.length) continue;
				if (yRot < 0 || yRot >= screen.pixels[0].length) continue;
				
				screen.pixels[xRot][yRot] = color;
			}
		}
	}
	
	public void renderSprite(Screen screen, int x, int y, int width, int height) {
		int xPos = x - Screen.getCamX(), yPos = y - Screen.getCamY();
		
		double xOff = width / (double) buffer.length;
		double yOff = height / (double) buffer[0].length;
		
		for (int xx = 0; xx < width; xx++) {
			if (xPos + xx < 0 || xPos + xx >= screen.pixels.length) continue;
			
			for (int yy = 0; yy < height; yy++) {
				if (yPos + yy < 0 || yPos + yy >= screen.pixels[0].length) continue;
				
				int color = buffer[(int) (xx / xOff)][(int) (yy / yOff)];
				if ((color & 0xFFFFFF) == NON_OPAQUE_COLOR) continue; // Alpha testing.
				
				screen.pixels[xPos + xx][yPos + yy] = buffer[(int) (xx / xOff)][(int) (yy / yOff)];
			}
		}
	}
	
	public static void renderText(int[][] buffer, SpriteSheet font, String text, int color, double x, double y) {
		for (int i = 0; i < text.length(); i++) {
			Integer c = characters.get(text.charAt(i));
			if (c == null) continue;
			Sprite ch = font.getSprite(c);
			
			for (int xx = 0; xx < ch.width; xx++) {
				if (x + xx < 0 || x + xx + i * ch.width >= buffer.length) continue;
				
				for (int yy = 0; yy < ch.height; yy++) {
					if (y + yy < 0 || y + yy >= buffer[0].length) continue;
					
					if ((ch.buffer[xx][yy] & 0xFFFFFF) == NON_OPAQUE_COLOR) continue;
					
					buffer[(int) x + xx + i * ch.width][(int) y + yy] = color;
				}
			}
		}
	}
	
	public static void renderRect(int[][] buffer, int x, int y, int w, int h) {
		for (int xx = 0; xx <= w; xx++) {
			if (x + xx < 0 || x + xx >= buffer.length || y < 0 || y + h >= buffer[0].length) continue;
			buffer[x + xx][y] = 0;
			buffer[x + xx][y + h] = 0;
		}
		for (int yy = 0; yy < h; yy++) {
			if (y + yy < 0 || y + yy >= buffer[0].length || x < 0 || x + w >= buffer.length) continue;
			buffer[x][y + yy] = 0;
			buffer[x + w][y + yy] = 0;
		}
	}
	
	public static void fillRect(int[][] buffer, int color, int x, int y, int w, int h) {
		for (int xx = 0; xx < w; xx++) {
			if (x + xx < 0 || x + xx >= buffer.length) continue;
			
			for (int yy = 0; yy < h; yy++) {
				if (y + yy < 0 || y + yy >= buffer[0].length) continue;
				
				buffer[x + xx][y + yy] = color;
			}
		}
	}

	private static HashMap<Character, Integer> createCharacterHashMap() {
		HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
		String[] s = { 
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}", 
				"abcdefghijklmnopqrstuvwxyz_               ", 
				"0123456789+-=*:;                          "
		};
		for (int j = 0; j < s.length; j++) 
			for (int i = 0; i < s[j].length(); i++) 
				hm.put(s[j].charAt(i), i + j * s[0].length());
		return hm;
	}
	
	public Sprite getSubSprite(int x, int y, int width, int height) {
		int[][] buffer = new int[width][height];
		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				buffer[xx][yy] = this.buffer[x + xx][y + yy];
			}
		}
		Sprite s = new Sprite(width, height, 0);
		s.setBuffer(buffer);
		return s;
	}
	
	public int[][] getBuffer() {
		return buffer;
	}
	
	public void setBuffer(int[][] nBuffer) {
		buffer = nBuffer;
	}

}
