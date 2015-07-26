package copper.ai;

/**
 * Node for use in the A* search algorithm. 
 * @author mathiasjohansson
 *
 */
public class ANode {
	
	public Point p;
	public ANode parent;
	public double gCost;
	
	public ANode(Point p, ANode parent, double gCost) {
		this.p = p;
		this.parent = parent;
		this.gCost = gCost;
	}
	
}
