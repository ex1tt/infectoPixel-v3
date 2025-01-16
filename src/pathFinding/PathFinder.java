package pathFinding;

import java.util.ArrayList;

import main.Panel;


public class PathFinder {
	
	Panel gp;
	
	public Node nodes[][];
	private ArrayList<Node> openNodes;
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
	
		openNodes = new ArrayList<>();
		pathNodes = new ArrayList<>();
	}
	
	public void pathFind(int startRow, int startCol, int targetRow, int targetCol, boolean[][] staticSolidCoords) {
		
		pathFound = false;
		noPath = false;
		
		nodes = new Node[gp.WORLD_ROW][gp.WORLD_COL];
		
		openNodes.clear();
		pathNodes.clear();
		
		setMapNodes();
        setStartNode(startRow, startCol);
        setTargetNode(targetRow,targetCol);
        setSolidNodes(staticSolidCoords);
        getCost(startNode);
        getPath();
	}
	
	// Set solid nodes within array
	private void setSolidNodes(boolean[][] staticSolidCoords) {
		
		for(int i=0; i<gp.WORLD_ROW; i++) {
			for(int z=0; z<gp.WORLD_COL; z++) {
				if(staticSolidCoords[i][z]) {
					nodes[i][z].solid = true;
				}
				else {
					nodes[i][z].solid = false;
				}
			}
		}
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
	
	private void openNode(Node node) {

		if((!node.open) && !(node.closed) && !(node.solid)) {
			
			node.open = true;
			openNodes.add(node);
			node.parent = currentNode;
		}
	}
	
	private void search() {

	    if (pathFound) {
	        return;
	    }

	    if (openNodes.isEmpty()) {
	        noPath = true;
	        return;
	    }

	    Node bestNode = openNodes.get(0);

		// Finds and sets the best node
	    for (Node node : openNodes) {
	        if (node.fCost < bestNode.fCost ||
	            (node.fCost == bestNode.fCost && node.gCost < bestNode.gCost)) {
	            bestNode = node;
	        }
	    }

	    currentNode = bestNode;  // sets the current node to the node with the least cost
	    openNodes.remove(currentNode);
	    currentNode.closed = true;

	    if (currentNode == targetNode) {
	        pathFound = true;
	        setPathNodes();
	        return;
	    }

	    int col = currentNode.col;
	    int row = currentNode.row;

	    int[] dy = {-1, 0, 0, 1}; // Offsets for columns
	    int[] dx = {0, -1, 1, 0}; // Offsets for rows

	    for (int i = 0; i < 4; i++) {
	        int newRow = row + dy[i];
	        int newCol = col + dx[i];

	        if (newRow >= 0 && newRow < gp.WORLD_ROW && newCol >= 0 && newCol < gp.WORLD_COL) {
	            if (!(nodes[newRow][newCol].solid) && !(nodes[newRow][newCol].closed)) {
	                openNode(nodes[newRow][newCol]);
	                getCost(nodes[newRow][newCol]);
	            }
	        }
	    }
	}			
	
	private void getPath() {
		
		while(!(pathFound)) {
			if(noPath) {
				System.out.println("ERROR: NO PATH ");	// NO PATH POSSIBLE
				break;
			}
			else {
				search();
			}
        }
	}
	
	private void setPathNodes() {
		
		Node temp = targetNode;
		
		pathNodes.add(targetNode); //adds targetNode to list
		
		while(temp != null && temp != startNode) {
			temp = temp.parent;
			
			if(temp != null && temp != startNode) {
				temp.path = true;
				pathNodes.add(temp);
			}
		}		
	}
	
	public void updatePath(int startX, int startY, int targetX, int targetY) {
		
		int startRow = startX / gp.TILE_SIZE;
		int startCol = startY / gp.TILE_SIZE;
		int targetRow = targetX / gp.TILE_SIZE;
		int targetCol = targetY / gp.TILE_SIZE;

		pathFind(startRow, startCol, targetRow, targetCol, staticSolidCoords);	
	}
}
