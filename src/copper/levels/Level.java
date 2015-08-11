package copper.levels;

import java.nio.charset.Charset;
import java.util.*;

import copper.*;
import copper.entities.*;
import copper.graphics.Sprite;

public class Level {

	public static ArrayList<Entity> entities 	= new ArrayList<Entity>();
	public static ArrayList<Player> players 	= new ArrayList<Player>();
	public static ArrayList<Spawner> spawners	= new ArrayList<Spawner>();
	
	public static int[][] tileMap;
	public static boolean[] solid;
	public static int[][] levelBuffer;
	
	public static Audio bgMusic = null;
	
//	Global physics variables
	public static final double GRAVITY 		= 512;
	public static final double AIR_DENSITY 	= 0.1;
	
	public static final int    TILE_SIZE 	= 16;
	
	private String currentLevelPath;
	private boolean mapHasChanged = true;
	
	public void loadLevel(String path) {
		System.out.println("Loading Level: " + path);
		loadFromFile("/copper/levels" + path);
		levelBuffer = new int[tileMap.length * TILE_SIZE][tileMap[0].length * TILE_SIZE];
		
		for (int i = 0; i < spawners.size(); i++) 
			spawners.get(i).spawnEntity();
		
		if (bgMusic != null) {
			bgMusic.stop();
			bgMusic.play(true);
		}
	}
	
	public void writeLevel() {
		StringBuilder s = new StringBuilder();
		
		s.append(tileMap.length + " " + tileMap[0].length + " " + solid.length + "\n");
		
		for (boolean b : solid) s.append((b ? 1 : 0) + " ");
		s.append("\n");
		if (tileMap.length > 0) 
			for (int y = 0; y < tileMap[0].length; y++) {
				for (int x = 0; x < tileMap.length; x++) 
					s.append(tileMap[x][y] + " ");
				s.append("\n");
			}
		
		s.append("ENTITY\n");
		for (int i = 0; i < spawners.size(); i++) 
			s.append(spawners.get(i).getEntityID() + 
					" " + (int) Panel.toTile(spawners.get(i).getX()) + 
					" " + (int) Panel.toTile(spawners.get(i).getY()) + 
					" " + (int) Panel.toTile(spawners.get(i).getZ()) + "\n");
		
		FileHandler.write(s.toString().getBytes(Charset.forName("UTF-8")), "save0.txt");
	}
	
	public void setTile(int tile, int x, int y) {
		if (x < 0 || x >= tileMap.length) return;
		if (y < 0 || y >= tileMap[0].length) return;
		if (tile == tileMap[x][y]) return;
		
		tileMap[x][y] = tile;
		mapHasChanged = true;
	}
	
	public void renderBuffer(){
		for (int x = 0; x < Level.tileMap.length; x++) {
			for (int y = 0; y < Level.tileMap[0].length; y++) {
				if (tileMap[x][y] == -1) continue;
				Sprite.render(levelBuffer, tileMap[x][y], x * TILE_SIZE, y * TILE_SIZE);
			}
		}
		mapHasChanged = false;
	}
	
	private void loadFromFile(String path) {
		currentLevelPath = path;
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
		while (in.hasNext()) 
			spawners.add(new Spawner(in.nextInt(), 
					Panel.toPixel(in.nextInt()), 
					Panel.toPixel(in.nextInt()), 
					Panel.toPixel(in.nextInt())));
		in.close();
	}
	
	public boolean hasMapChanged() {
		return mapHasChanged;
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
