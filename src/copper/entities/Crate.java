package copper.entities;

import copper.graphics.Sprite;

public class Crate extends Entity {

	public Crate(double x, double y, double z) {
		super(x, y, z);
		setBaseHealth(20);
		sprite 	= Sprite.crate;
		this.x 	= x;
		this.y 	= y;
		speed 	= 0;
		width 	= sprite.width;
		height 	= sprite.height;
		dmgColor= 0xAAAA00;
	}

}
