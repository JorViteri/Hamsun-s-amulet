package Utils;

/**
 * Defines the PathFinding functions
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Elements.Creature;

public class PathFinder {

	private ArrayList<Position> open;
	private ArrayList<Position> closed;
	private HashMap<Position, Position> parents;
	private HashMap<Position, Integer> totalCost;

	/**
	 * Constructor
	 */
	public PathFinder() {
		this.open = new ArrayList<Position>();
		this.closed = new ArrayList<Position>();
		this.parents = new HashMap<Position, Position>();
		this.totalCost = new HashMap<Position, Integer>();
	}

	/**
	 * Returns the heuristic cost for moving from one position to another. 
	 * @param from Position initial
	 * @param to Position final
	 * @return max distance between the two positions in x or y
	 */
	private int heuristicCost(Position from, Position to) {
		return Math.max(Math.abs(from.getIntX() - to.getIntX()), Math.abs(from.getIntY() - to.getIntY()));
	}

	/**
	 * Obtains how much costed reaching the actual position
	 * @param from actual position
	 * @return how much costed comming the actual position
	 */
	private int costToGetTo(Position from) {
		return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
	}

	/**
	 * Returns the total cost which is the sum of heuristic cost an the previous cost
	 * @param from actual position
	 * @param position of destination
	 * @return total cost of movement
	 */
	private int totalCost(Position from, Position to) {
		int cost;
		if (totalCost.containsKey(from)) {
			return totalCost.get(from);
		}
		cost = costToGetTo(from) + heuristicCost(from, to);
		totalCost.put(from,  cost); //TODO me faltaba esto y creo que era un error grave
		return cost;
	}

	/**
	 * Changes a child it's parent and then removes the child from the HashMap of totalCost
	 * @param child the old child
	 * @param parent the new parent
	 */
	private void reParent(Position child, Position parent) {
		parents.put(child, parent);
		totalCost.remove(child);
	}

	/**
	 * Obtains the path to a position 
	 * @param creature creatrue to move
	 * @param start position from which movement starts
	 * @param end  final position
	 * @param maxTries number of tries 
	 * @return ArrayList of the positions to the destination
	 */
	public ArrayList<Position> findPath(Creature creature, Position start, Position end, int maxTries) {
		open.clear();
		closed.clear();
		parents.clear();
		totalCost.clear();

		open.add(start);

		for (int tries = 0; tries < maxTries && open.size() > 0; tries++) {
			Position closest = getClosestPosition(end);

			open.remove(closest);
			closed.add(closest);

			if (closest.equals(end)) {
				return createPath(start, closest);
			} else {
				checkNeighbors(creature, end, closest);
			}
		}
		return null;
	}

	/**
	 * Returns the closest position chechking the total cost
	 * @param end Position from which the closest to it must be obtained
	 * @return the closest position
	 */
	private Position getClosestPosition(Position end) {
		Position closest = open.get(0);
		for (Position p : open) {
			if (totalCost(p, end) < totalCost(closest, end)) {
				closest = p;
			}
		}
		return closest;
	}

	/**
	 * 
	 * @param creature
	 * @param end
	 * @param closest
	 */
	private void checkNeighbors(Creature creature, Position end, Position closest) {
		boolean enter;
		for (Position p : closest.getNeighbors(8)) {
			enter = creature.canEnter(p.getIntX(), p.getIntY(), creature.getZ());
			if (closed.contains(p) || !enter && !p.equals(end)) {
				continue;
			}
			if (open.contains(p)) {
				reParentNeighborIfNecessary(closest, p);
			} else {
				reParentNeighbor(closest, p);
			}
		}
	}

	/**
	 * Sets a new parent for a neighbor position
	 * @param closest new parent
	 * @param neighbor the neighborr as a child
	 */
	private void reParentNeighbor(Position closest, Position neighbor) {
		reParent(neighbor, closest);
		open.add(neighbor);
	}

	/**
	 * 
	 * @param closest
	 * @param neighbor
	 */
	private void reParentNeighborIfNecessary(Position closest, Position neighbor) {
		Position originalParent;
		double currentCost, reparentCost;

		originalParent = parents.get(neighbor);
		currentCost = costToGetTo(neighbor);
		reParent(neighbor, closest);
		reparentCost = costToGetTo(neighbor);

		if (reparentCost < currentCost) {
			open.remove(neighbor);
		} else {
			reParent(neighbor, originalParent);
		}

	}
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private ArrayList<Position> createPath(Position start, Position end){
		ArrayList<Position> path = new ArrayList<>();
		
		while(!end.equals(start)){
			path.add(end);
			end = parents.get(end);
		}
		
		Collections.reverse(path);
		return path;
	}

}
