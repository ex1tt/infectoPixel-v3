package pathFinding;

public class Node implements Comparable<Node>{
	
	public Node parent;
	public int row, col;
	public int gCost, hCost, fCost;
	public boolean open, closed, solid, path;
	
	Node(int row, int col) {
		
		this.row = row;	
		this.col = col;
	}

	@Override
    public int compareTo(Node other) {

        // Compare based on fCost, and break ties with gCost
        if (this.fCost < other.fCost) return -1;
        if (this.fCost > other.fCost) return 1;
        
        // If fCost is the same, compare gCost to prioritize closer nodes
        return Integer.compare(this.gCost, other.gCost);
    }
}