package copper.graphics;

public class SpriteSheet extends Sprite {
	
	private Sprite[][] spriteSheet;

	public SpriteSheet(String path, int sWidth, int sHeight) {
		super(path);
		width 	= sWidth;
		height 	= sHeight;
		
		spriteSheet = new Sprite[buffer.length / sWidth][buffer[0].length / sHeight];
		for (int x = 0; x < spriteSheet.length; x++) {
			for (int y = 0; y < spriteSheet[0].length; y++) {
				spriteSheet[x][y] = getSubSprite(x * sWidth, y * sHeight, sWidth, sHeight);
			}
		}
		
		buffer = new int[sWidth][sHeight];
		setFrame(0, 0);
	}
	
	public void setFrame(int x, int y) {
		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				buffer[xx][yy] = spriteSheet[x][y].getBuffer()[xx][yy];
			}
		}
	}
	
	public Sprite getSprite(int x, int y) {
		return spriteSheet[x][y];
	}
	
	public Sprite getSprite(int i) {
		return getSprite(i % spriteSheet.length, i / spriteSheet.length);
	}

	public Sprite[][] getSpriteSheet() {
		return spriteSheet;
	}
	
	public int getFullWidth() {
		return spriteSheet.length * width;
	}
	
	public int getFullHeight() {
		return spriteSheet[0].length * height;
	}

}
