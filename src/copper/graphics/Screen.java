package copper.graphics;

import static copper.entities.Entity.entities;
import static copper.levels.Level.levelBuffer;

import java.awt.event.*;
import java.util.*;

import copper.*;
import copper.entities.*;
import copper.gui.Menu;
import copper.levels.Level;

public class Screen {

	public int width, height;
	public int[][] pixels;
	
	private static int xCam, yCam;
	private static Entity focusedEntity;
	private int cameraDrag;
	
	private Comparator<Entity> comparator = new Comparator<Entity>() {
		public int compare(Entity e0, Entity e1) {
			if (e0.z > e1.z) return 1;
			if (e0.z < e1.z) return -1;
			return 0;
		}
	};

	public Screen(int width, int height) {
		this.width 	= width;
		this.height = height;
		pixels 		= new int[width][height];
		
		xCam = 0;
		yCam = 0;
		cameraDrag = 8;
		if (Level.tileMap.length << 4 < width) 		xCam -= width / 2 - (Level.tileMap.length << 4) / 2;
		if (Level.tileMap[0].length << 4 < height) 	yCam -= height / 2 - (Level.tileMap[0].length << 4) / 2;
		
		if (focusedEntity == null){
			for (int i = 0; i < entities.size(); i++) {
				if (entities.get(i) instanceof Player) focusedEntity = entities.get(i);
			}
			if (focusedEntity == null) focusedEntity = entities.get((int) (Math.random() * entities.size()));
		}
	}

	public void render() {
		centerScreen();
		clearScreen(Sprite.terrain.getSprite(1, 0));
		
		Collections.sort(entities, comparator);
		
		for (int x = xCam; x < width + xCam; x++) {
			if (x < 0 || x >= levelBuffer.length) continue;
			for (int y = yCam; y < height + yCam; y++) {
				if (y < 0 || y >= levelBuffer[0].length) continue;
				pixels[x - xCam][y - yCam] = 
						levelBuffer[x][y];
			}
		}
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			Sprite shadow = e.getShadow();
			if (shadow == null) continue;
			shadow.renderSprite(this, (int) e.x + e.width / 2 - shadow.width / 2, 
					(int) e.y + e.height - shadow.height / 2);
		}
		
		for (int i = 0; i < entities.size(); i++) 
			entities.get(i).render(this);
		
		if (Copper.DEBUG_MODE) 
			for (int i = 0; i < entities.size(); i++)
				Sprite.renderRect(pixels, (int) entities.get(i).x - xCam, (int) entities.get(i).y - yCam, 
						(int) entities.get(i).width, (int) entities.get(i).height);
		
		Menu.current.render(this);
	}
	
	@SuppressWarnings("unused")
	private void clearScreen(int color) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x][y] = color;
			}
		}
	}
	
	private void clearScreen(Sprite s) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x][y] = s.getBuffer()[Math.abs(x + xCam) % s.width][Math.abs(y + yCam) % s.height];
			}
		}
	}
	
	public void centerScreen() {
		try {
			if (!focusedEntity.alive) focusedEntity = entities.get(0);
		}catch (IndexOutOfBoundsException e) {
			System.err.println("There are no entities left. D:");
		}
		
		int xFocus = (int) (focusedEntity.x + focusedEntity.width / 2) - width / 2;
		int yFocus = (int) (focusedEntity.y + focusedEntity.height / 2 - focusedEntity.z) - height / 2;
		
		if (xCam < xFocus - cameraDrag) 		xCam = xFocus - cameraDrag;
		else if (xCam > xFocus + cameraDrag) 	xCam = xFocus + cameraDrag;
		
		if (yCam < yFocus - cameraDrag) 		yCam = yFocus - cameraDrag;
		else if (yCam > yFocus + cameraDrag) 	yCam = yFocus + cameraDrag;
	}
	
	public static void setFocus(Entity focus) {
		if (focus != null) focusedEntity = focus;
	}
	
	public static int getCamX() {
		return xCam;
	}
	
	public static int getCamY() {
		return yCam;
	}
	
	public static Entity getFocusedEntity() {
		return focusedEntity;
	}

}
