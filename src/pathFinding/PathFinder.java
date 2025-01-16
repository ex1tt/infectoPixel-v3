package pathFinding;

import java.util.ArrayList;
import java.util.PriorityQueue;

import main.Panel;

public class PathFinder {
	
	Panel gp;
	
	public Node nodes[][];
	private PriorityQueue<Node> openNodes;
	public ArrayList<Node> pathNodes;
	
	private Node currentNode;
	private Node startNode;
	private Node targetNode;
	
	private boolean[][] staticSolidCoords;
	
	private boolean pathFound;
	private boolean noPath;
	
	public PathFinder(Panel gp, boolean [][] staticSolidCoords) {
		
		this.staticSolidCoords = staticSolidCoords;
		
		this.gp = gp;	
	
		openNodes = new PriorityQueue<>();
		pathNodes = new ArrayList<>();
	}
	
	public void pathFind(int startRow, int startCol, int targetRow, int targetCol) {
		
		pathFound = false;
		noPath = false;
		
		nodes = new Node[gp.WORLD_ROW][gp.WORLD_COL];
		
		openNodes.clear();
		pathNodes.clear();
		
		// Setting nodes
		setMapNodes();
		setTargetNode(targetRow,targetCol);
        setStartNode(startRow, startCol);
        setSolidNodes(staticSolidCoords);

		// Need to look at the path finding algo... (for optimizations...)

        getCost(startNode);

		// Gets the path...
        getPath();
	}

	// Fill 2D array with nodes
	private void setMapNodes() {
		
		for(int i=0; i<gp.WORLD_ROW; i++) {
			for(int z=0; z<gp.WORLD_COL; z++) {	
				nodes[i][z] = new Node(i, z);		
			}
		}
	}
	
	// Set the start node
	private void setStartNode(int row, int col) {
		
		startNode = nodes[row][col];	
		openNode(startNode);
		currentNode = startNode;
	}
	
	// Set the target node
	private void setTargetNode(int row, int col) {	
		targetNode = nodes[row][col];		
	}

	// Set solid nodes within array
	private void setSolidNodes(boolean[][] staticSolidCoords) {
		
		for(int i=0; i<gp.WORLD_ROW; i++) {
			for(int z=0; z<gp.WORLD_COL; z++) {
				nodes[i][z].solid = (staticSolidCoords[i][z] == true);
			}
		}
	}
	
	// Calculate cost of an individual node
	private void getCost(Node node) {
		
	    // G COST
	    int xDistance = Math.abs(node.row - startNode.row);
	    int yDistance = Math.abs(node.col - startNode.col);
	    node.gCost = xDistance + yDistance;
	    
	    // H COST
	    xDistance = Math.abs(node.row - targetNode.row);
	    yDistance = Math.abs(node.col - targetNode.col);
	    node.hCost = xDistance + yDistance;
	    
	    // F COST
	    node.fCost = node.gCost + node.hCost;
	}

	// Method to get path from startNode to TargetNode
	private void getPath() {
		
		while(!pathFound && !noPath) {
			search();
        }
	}
	
	private void search() {

	    if (openNodes.isEmpty()) {
	        noPath = true;
			System.out.println("Error: No path...");
	        return;
	    }

		// Sets the current node to the node with the least f cost and or g cost through priority queue
		currentNode = openNodes.poll();

		// Close the node to ensure it isn't ever added back to open nodes
	    currentNode.closed = true;

		// If current node is our target node, set path nodes
	    if (currentNode == targetNode) {
	        pathFound = true;
	        setPathNodes();
	        return;
	    }

		// Search current nodes neighbours...
		searchNeigbours(currentNode);
	}			

	// Method to search a nodes neighbours and add them to open nodes if possible
	private void searchNeigbours(Node curNode) {

		int col = curNode.col;
	    int row = curNode.row;

	    int[] dy = {-1, 0, 0, 1}; // Offsets for columns
	    int[] dx = {0, -1, 1, 0}; // Offsets for rows

		//Check current nodes horizontally and vertically adjacent neighbours
	    for (int i = 0; i < 4; i++) {
	        int nRow = row + dy[i];
	        int nCol = col + dx[i];

			// If neigbour is in bounds
	        if (nRow >= 0 && nRow < gp.WORLD_ROW && nCol >= 0 && nCol < gp.WORLD_COL) {

				// If neigbour has not been visited or a solid node
	            if (!(nodes[nRow][nCol].solid) && !(nodes[nRow][nCol].closed)) {
	                openNode(nodes[nRow][nCol]);
	                //getCost(nodes[nRow][nCol]);
	            }
	        }
	    }
	}

	// Method to open a node
	private void openNode(Node node) {

        if (!node.open && !node.closed) {

            node.open = true;
            node.parent = currentNode;
            getCost(node);
            openNodes.add(node);
        }
    }
	
	// If target node is reached, backtrack and find path
	private void setPathNodes() {
		
		// Set current path node to target node
		Node curPathNode = targetNode;
		pathNodes.add(curPathNode);
		
		while(curPathNode.parent != null && curPathNode.parent != startNode) {

			// Backtrack
			curPathNode = curPathNode.parent;
			pathNodes.add(curPathNode);
		}		
	}
	
	// Update path
	public void updatePath(int startX, int startY, int targetX, int targetY) {
		
		int startRow = startX / gp.TILE_SIZE;
		int startCol = startY / gp.TILE_SIZE;
		int targetRow = targetX / gp.TILE_SIZE;
		int targetCol = targetY / gp.TILE_SIZE;

		pathFind(startRow, startCol, targetRow, targetCol);	
	}
}
