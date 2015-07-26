package copper.ai;

public class Point {
	
	public double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {
		x = 0;
		y = 0;
	}
	
	public Point(Point p) {
		x = p.x;
		y = p.y;
	}
	
	public boolean intEquals(Object o) {
		if (o instanceof Point)
			return (int) ((Point) o).x == (int) x && (int) ((Point) o).y == (int) y;
		
		return false;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Point)
			return ((Point) o).x == x && ((Point) o).y == y;
		
		return false;
	}
	
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
	
}
