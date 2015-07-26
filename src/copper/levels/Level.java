package copper.levels;

import java.util.*;

import copper.*;
import copper.entities.*;
import copper.entities.items.*;
import copper.graphics.Sprite;

public class Level {

	public static ArrayList<Entity> entities 	= new ArrayList<Entity>();
	public static ArrayList<Player> players 	= new ArrayList<Player>();
	public static int[][] tileMap;
	public static boolean[] solid;
	public static int[][] levelBuffer;
	
	public static Audio bgMusic = null;
	
//	Global physics variables
	public static final double GRAVITY 		= 512;
	public static final double AIR_DENSITY 	= 0.1;
	
	public static final int    TILE_SIZE 	= 16;
	
	public void loadLevel(String path) {
		System.out.println("Loading Level: " + path);
		loadFromFile("/copper/levels" + path);
		levelBuffer = new int[tileMap.length * TILE_SIZE][tileMap[0].length * TILE_SIZE];
		renderBuffer();
		
		if (bgMusic != null) {
			bgMusic.stop();
			bgMusic.play(true);
		}
	}
	
	private void renderBuffer(){
		for (int x = 0; x < Level.tileMap.length; x++) {
			for (int y = 0; y < Level.tileMap[0].length; y++) {
				if (tileMap[x][y] == -1) continue;
				Sprite.render(levelBuffer, tileMap[x][y], x * TILE_SIZE, y * TILE_SIZE);
			}
		}
	}
	
	private void loadFromFile(String path) {
		Scanner in = null;
		in = new Scanner(this.getClass().getResourceAsStream(path));

		int width = in.nextInt(), height = in.nextInt(), solids = in.nextInt();
		tileMap = new int[width][height];
		solid = new boolean[solids];

		System.out.println("Building Level");
		for (int i = 0; i < solids; i++) {
			solid[i] = (in.nextInt() == 1) ? true : false;
		}
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (in.hasNext("ENTITY")) break;
				tileMap[x][y] = in.nextInt();
			}
		}

		System.out.println("Spawning Entities");
		if (in.hasNext("ENTITY")) in.next();
		while (in.hasNext()) spawnEntity(in.nextInt(), Panel.toPixel(in.nextInt()), Panel.toPixel(in.nextInt()), Panel.toPixel(in.nextInt()));
		in.close();
	}

	public void spawnEntity(int ID, double x, double y, double z) {
		switch (ID) {
		case (0):
			new Entity(x, y, z);
			break;
		case (1):
			players.add(new Player(x, y, z));
			break;
		case (2):
			new Item(x, y, z, new HealthKit());
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
