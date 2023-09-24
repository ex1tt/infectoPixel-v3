package pathFinding;

public class Node {
	
	public Node parent;
	public int col;
	public int row;
	public double gCost;
	public double hCost;
	public double fCost;
	public boolean open;
	public boolean closed;
	public boolean solid;
	public boolean path;
	
	Node(int row, int col) {
		
		this.row = row;	
		this.col = col;
	}
}