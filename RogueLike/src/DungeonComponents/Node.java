package DungeonComponents;

import java.util.ArrayList;

import Utils.Position;
/**
 * Node class used for the generation of the dungeon map by the use of graphs 
 * @author comec
 *
 */
public class Node {

	private Position position;
	private Position parent;
	private ArrayList<Position> adjacent;
	private double key;

	public Position getPosition() {
		return position;
	}

	public Node(Position pos, Position parent, ArrayList<Position> adj) {
		this.position = pos;
		this.parent = parent;
		this.adjacent = adj;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Position getParent() {
		return parent;
	}

	public void setParent(Position parent) {
		this.parent = parent;
	}

	public ArrayList<Position> getAdjacent() {
		return adjacent;
	}

	public void setAdjacent(ArrayList<Position> adjacent) {
		boolean cond;
		for (Position p1 : adjacent){
			cond = true;
			for (Position p2: this.adjacent){
				if (p1.equals(p2)){
					cond=false;
					break;
				}
			}
			if (cond){
				this.adjacent.add(p1);
			}
		}
	}

	public double getKey() {
		return key;
	}

	public void setKey(double key) {
		this.key = key;
	}

	public double getDistance(Node node) {
		return this.getPosition().distance(node.getPosition());
	}

}
