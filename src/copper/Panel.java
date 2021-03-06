package copper;

import static copper.levels.Level.entities;

import java.awt.event.*;
import java.util.ArrayList;

import copper.entities.Editor;
import copper.gui.Menu;
import copper.levels.Level;

/**
 * Handles game updates and Keyboard/Mouse input.
 * @author Mathias Johansson
 */

public class Panel implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	public static double 				time 	= 0;
	
	/**
	 * Use this variable to convert from X per second to X per tick.
	 */
	public static double 				delta 	= 0;
	
	public static boolean[] 			keys = new boolean[65535];
	public static ArrayList<Integer> 	pressedKeys = new ArrayList<Integer>();
	
	public static ArrayList<Character> 	typedKeys = new ArrayList<Character>();
	public static boolean				backspaceHit = false;
	
	public static boolean[] 			mButtons = new boolean[12];
	public static ArrayList<Integer> 	pressedMButtons = new ArrayList<Integer>();
	private static int 					xMouse, yMouse;
	
	public static Editor				editor;
	
	public void tick(){
		time += delta;
		
		Menu.current.tick();
		
		if (!Menu.current.freezeWorld && !Copper.EDITOR_MODE) {
			for (int i = 0; i < entities.size(); i++){
				entities.get(i).tick();
				entities.get(i).timeAlive += delta;
				if (entities.get(i).getHealth() <= 0 || !entities.get(i).alive) {
					entities.get(i).alive = false;
					entities.get(i).dead();
				}
			}
			for (int i = 0; i < entities.size(); i++){
				if (!entities.get(i).alive) entities.remove(i);
			}
		}
		
		if (Copper.EDITOR_MODE) editor.tick();
		
		pressedKeys.clear();
		typedKeys.clear();
		pressedMButtons.clear();
		backspaceHit = false;
	}

	public void keyTyped(KeyEvent e) {
		if ((int) e.getKeyChar() != 8) 
			typedKeys.add(e.getKeyChar());
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		pressedKeys.add(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			backspaceHit = true;
			return;
		}
		keys[e.getKeyCode()] = false;
	}

	public void focusGained(FocusEvent e) {
		Copper.focused = true;
	}

	public void focusLost(FocusEvent e) {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
		Copper.focused = false;
	}

	public void mouseDragged(MouseEvent e) {
		double xScale = (double) Copper.window.getWidth() / Copper.window.getContentPane().getWidth() / Copper.SCALE;
		double yScale = (double) Copper.window.getHeight() / Copper.window.getContentPane().getHeight() / Copper.SCALE;
		xMouse = (int) (e.getX() * xScale);
		yMouse = (int) (e.getY() * yScale);
	}

	public void mouseMoved(MouseEvent e) {
		double xScale = (double) Copper.window.getWidth() / Copper.window.getContentPane().getWidth() / Copper.SCALE;
		double yScale = (double) Copper.window.getHeight() / Copper.window.getContentPane().getHeight() / Copper.SCALE;
		xMouse = (int) (e.getX() * xScale);
		yMouse = (int) (e.getY() * yScale);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mButtons[e.getButton() - 1] = true;
		pressedMButtons.add(e.getButton() - 1);
	}

	public void mouseReleased(MouseEvent e) {
		mButtons[e.getButton() - 1] = false;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
	
	public static int getMouseX() {
		return xMouse;
	}
	
	public static int getMouseY() {
		return yMouse;
	}
	
	/**
	 * Translates Integer i from pixel precision to Tile precision.
	 * @param i
	 * @return
	 */
	
	public static double toTile(double d) {
		return d / Level.TILE_SIZE;
	}
	
	/**
	 * Translates Integer i from Tile precision to pixel precision.
	 * @param i
	 * @return
	 */
	
	public static double toPixel(double d) {
		return d * Level.TILE_SIZE;
	}
	
}
