package copper;

import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;

/**
 * An audio manager. Can currently only load WAV files. 
 * @author Mathias Johansson
 *
 */

public class Audio {
	
	/**
	 * The directory all audio files will be located in. 
	 */
	public static final String AUDIO_PATH = "/sounds/";
	
	/**
	 * A collection of all loaded sounds. 
	 */
	private static ArrayList<Audio> sounds = new ArrayList<Audio>();
	
	public static Audio test = load("test.wav");
	public static Audio hit = load("hit.wav");
	public static Audio snowhit = load("snowhit.wav");
	public static Audio pickup = load("pickup.wav");
	public static Audio boost = load("boost.wav");
	public static Audio longbg = load("long.wav");
	
	public Clip clip;
	
	public String path;
	
	private boolean looping = false;
	
	public Audio() {
		sounds.add(this);
	}
	
	/**
	 * Plays the sound beginning at currentFrame. 
	 * 
	 * Will loop sound if the sound was played looping before. 
	 */
	public void play() {
		play(looping);
	}
	
	/**
	 * Plays the sound beginning at currentFrame. 
	 * 
	 * Loops the sound indefinitely if loop is equal to true. 
	 * @param loop
	 */
	public void play(boolean loop) {
		if (clip.getFramePosition() == clip.getFrameLength()) 
			clip.setFramePosition(0);
		
		clip.loop((looping = loop) ? Clip.LOOP_CONTINUOUSLY : 0);
	}
	
	public void pause() {
		clip.stop();
	}
	
	public void stop() {
		clip.stop();
		clip.setFramePosition(0);
	}
	
	/**
	 * Loads the audio file located in path. 
	 * 
	 * Returns null if no file can be found. 
	 * @param path
	 * @return
	 */
	private static Audio load(String path) {
		try {
			Audio a = new Audio();
			a.clip = AudioSystem.getClip();
			a.path = AUDIO_PATH + path;
			
			AudioInputStream stream = AudioSystem.getAudioInputStream(
					new BufferedInputStream(Copper.class.getResourceAsStream(a.path)));
			
			a.clip.open(stream);
			
			return a;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Pauses all playing sounds. 
	 */
	
	public static void pauseAll() {
		for (int i = 0; i < sounds.size(); i++) {
			sounds.get(i).pause();
		}
	}
	
	/**
	 * Resumes all would-be-playing sounds. 
	 */
	public static void resumeAll() {
		for (int i = 0; i < sounds.size(); i++) {
			Audio a = sounds.get(i);
			
			if (a.clip.getFramePosition() > 0) {
				a.play();
			}
		}
	}
	
	/**
	 * Plays Audio a in a separate object. 
	 * 
	 * This sound can't be paused or stopped but it can play multiple sounds at the 
	 * same time. 
	 * @param a
	 */
	public static void playAndForget(Audio a, boolean loop) {
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(
					new BufferedInputStream(Copper.class.getResourceAsStream(a.path))));
			clip.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
}
