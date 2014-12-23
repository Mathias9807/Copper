package copper.levels;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import copper.Panel;
import copper.entities.*;
import copper.tiles.Tile;

public class Level {

	public static Tile[][] tileMap;
	public static int[][] levelBuffer;
	
//	Global physics variables
	public static final double GRAVITY 		= 9.8;
	public static final double AIR_DENSITY 	= 0.005;
	
	public void loadLevel(String path) {
		System.out.println("Loading Level: " + path);
		if (path.endsWith("txt")) 
			loadFromFile("/copper/levels" + path);
		else
			loadFromImage("/levels" + path);
		levelBuffer = new int[tileMap.length << 4][tileMap[0].length << 4];
		renderBuffer();
	}
	
	private void renderBuffer(){
		for (int x = 0; x < Level.tileMap.length; x++) {
			for (int y = 0; y < Level.tileMap[0].length; y++) {
				if (tileMap[x][y] == null) Tile.tile.render(levelBuffer, x << 4, y << 4);
				tileMap[x][y].render(levelBuffer, x << 4, y << 4);
			}
		}
	}
	
	/**
	 * Deprecated method.
	 * @param path
	 */
	
	private void loadFromImage(String path) {
		BufferedImage b;
		try {
			b = ImageIO.read(this.getClass().getResource(path));
		} catch (IOException e) {
			b = null;
			System.err.println("Failed to load Level. (Image)");
		}

		tileMap = new Tile[b.getWidth()][b.getHeight()];

		for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				tileMap[x][y] = Tile.tiles[(b.getRGB(x, y) & 0xffffff) >> 4];

				int alpha = (b.getRGB(x, y) >> 24) & 0xff;
				if (alpha == 255) continue;
				spawnEntity(alpha, x << 4, y << 4, 0);
			}
		}
	}
	
	private void loadFromFile(String path) {
		Scanner in = null;
		in = new Scanner(this.getClass().getResourceAsStream(path));

		int width = in.nextInt(), height = in.nextInt();
		tileMap = new Tile[width][height];

		System.out.println("Building Level");
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (in.hasNext("ENTITY")) break;
				tileMap[x][y] = Tile.tiles[in.nextInt()];
			}
		}

		System.out.println("Spawning Entities");
		if (in.hasNext("ENTITY")) in.next();
		while (in.hasNext()) spawnEntity(in.nextInt(), Panel.toPixel(in.nextInt()), Panel.toPixel(in.nextInt()), in.nextInt());
		in.close();
	}

	public void spawnEntity(int ID, int x, int y, int z) {
		switch (ID) {
		case (0):
			new Entity(x, y, z);
			break;
		case (1):
			new Player(x, y, z);
			break;
		case (2):
			new Crate(x, y, z);
			break;
		case (3):
			new Ghost(x, y, z);
			break;
		case (4):
			new Zombie(x, y, z);
			break;
		case (5):
			new MobSpawner(x, y, z);
			break;
		case (6):
			new EarthWizard(x, y, z);
			break;
		}
	}
	
	/*
	 * 
2 2 8 3
3 5 6 3
3 5 7 3
3 6 6 3
3 6 7 3
0 6 5 3
4 0 0 3
4 5 0 3
4 5 7 3
4 3 0 3
	 */

}
