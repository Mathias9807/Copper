package copper.ai;

import static copper.entities.Entity.isTileSolid;

import java.util.*;

import copper.Panel;
import copper.entities.Entity;

public class NavigateTo extends Routine {
	
	private List<Point> moves = null;
	private List<ANode> openList;
	private List<Point> closedList;
	private MoveTo move;
	private int distance = 0;
	
	private Comparator<ANode> comparator = (a, b) -> {
		if (a.gCost > b.gCost) return +1;
		if (a.gCost < b.gCost) return -1;
		
		return 0;
	};
	
	public NavigateTo(int d) {
		openList = new ArrayList<ANode>();
		closedList = new ArrayList<Point>();
		distance = d;
	}
	
	public NavigateTo() {
		openList = new ArrayList<ANode>();
		closedList = new ArrayList<Point>();
	}
	
	public int run(Entity e) {
		if (moves == null) {
			findPath(
					new Point((int) (e.x + e.width / 2), (int) (e.y + e.height / 2)), 
					new Point((int) (e.target.x + e.target.width / 2), (int) (e.target.y + e.target.height / 2)));
			if (moves == null) return fail();
		}
		
		if (moves.size() == 0) {
			moves = null;
			openList.clear();
			closedList.clear();
			move = null;
			return succeed();
		}
		
		if (move == null) {
			move = new MoveTo(
				Panel.toPixel(moves.get(moves.size() - 1).x), 
				Panel.toPixel(moves.get(moves.size() - 1).y), 
				(moves.size() > 1) ? 0 : distance
			);
		}
		
		int r = move.run(e);
		if (r == SUCCESS) {
			move = null;
			moves.remove(moves.size() - 1);
		}
		if (r == FAILURE) {
			return fail();
//			closedList.add(moves.get(moves.size() - 1));
		}
		
		return running();
	}
	
	private void findPath(Point s, Point e) {
		moves = new ArrayList<Point>();
		Point start = new Point(Panel.toTile(s.x), Panel.toTile(s.y));
		Point end = new Point(Panel.toTile(e.x), Panel.toTile(e.y));
		ANode cur = null;
		
		openList.add(new ANode(start, null, getManhattan(start.x, start.y, end.x, end.y)));
		loop: 
			while (openList.size() > 0) {
				openList.sort(comparator);
				cur = openList.get(0);
				
				System.out.println(cur.p);
				for (int x = -1; x < 2; x++) 
					tileLoop: 
						for (int y = -1; y < 2; y++) {
							if (x == 0 && y == 0) continue;
							double aX = cur.p.x + x;
							double aY = cur.p.y + y;
							if (isTileSolid(aX, aY)) 
								continue;
							if ((x + 1) % 2 == 0 && (y + 1) % 2 == 0) 
								if (isTileSolid(aX, cur.p.y) || isTileSolid(cur.p.x, aY)) continue;
							
							for (int i = 0; i < closedList.size(); i++) 
								if (closedList.get(i).intEquals(new Point(aX, aY))) 
									continue tileLoop;
							
							openList.add(new ANode(new Point((int) aX + 0.5, (int) aY + 0.5), cur, getManhattan(aX, aY, end.x, end.y)));
						}
				
				openList.remove(cur);
				closedList.add(cur.p);
				
				for (int i = 0; i < closedList.size(); i++) {
					if (closedList.get(i).intEquals(end)) 
						break loop;
				}
			}
		
		openList.clear();
		closedList.clear();
		
		while (cur != null) {
			moves.add(cur.p);
			cur = cur.parent;
		}
	}
	
	private double getManhattan(double ax, double ay, double bx, double by) {
		return Math.abs(bx - ax) + Math.abs(by - ay);
	}

	public void reset() {
		moves = null;
		openList.clear();
		closedList.clear();
		move = null;
		distance = 0;
	}
	
}
