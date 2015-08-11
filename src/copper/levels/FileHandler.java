package copper.levels;

import java.io.*;
import java.nio.file.StandardOpenOption;

public class FileHandler {
	
	public static final String FILE_PATH = "copperdata/levels/";
	
	public static byte[] read(String path) {
		File file = getFile(path);
		
		try {
			return java.nio.file.Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static File getFile(String path) {
		File file = new File(FILE_PATH + path);
		
		if (!file.exists()) {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
		return file;
	}
	
	public static void write(byte[] data, String path) {
		File dir = new File(FILE_PATH);
		if (!dir.exists()) if (!dir.mkdirs()) System.err.println("Failed to create save folder. ");
		
		File file = null;
		file = new File(FILE_PATH + path);
		
		try {
			file.createNewFile();
			java.nio.file.Files.write(file.toPath(), data, 
					StandardOpenOption.CREATE, 
					StandardOpenOption.WRITE, 
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
