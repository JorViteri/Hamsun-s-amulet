package Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Elements.Creature;

public class PathFinder {

	private ArrayList<Position> open;
	private ArrayList<Position> closed;
	private HashMap<Position, Position> parents;
	private HashMap<Position, Integer> totalCost;

	public PathFinder() {
		this.open = new ArrayList<Position>();
		this.closed = new ArrayList<Position>();
		this.parents = new HashMap<Position, Position>();
		this.totalCost = new HashMap<Position, Integer>();
	}

	private int heuristicCost(Position from, Position to) {
		return Math.max(Math.abs(from.getIntX() - to.getIntX()), Math.abs(from.getIntY() - to.getIntY()));
	}

	private int costToGetTo(Position from) {
		return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
	}

	private int totalCost(Position from, Position to) {
		int cost;
		if (totalCost.containsKey(from)) {
			return totalCost.get(from);
		}
		cost = costToGetTo(from) + heuristicCost(from, to);
		totalCost.put(from,  cost); //TODO me faltaba esto y creo que era un error grave
		return cost;
	}

	private void reParent(Position child, Position parent) {
		parents.put(child, parent);
		totalCost.remove(child);
	}

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

	private Position getClosestPosition(Position end) {
		Position closest = open.get(0);
		for (Position p : open) {
			if (totalCost(p, end) < totalCost(closest, end)) {
				closest = p;
			}
		}
		return closest;
	}

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

	private void reParentNeighbor(Position closest, Position neighbor) {
		reParent(neighbor, closest);
		open.add(neighbor);
	}

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
