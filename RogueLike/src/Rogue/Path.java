package Rogue;

import java.util.List;

public class Path {

	private static PathFinder pf = new PathFinder();

	private List<Position> positions;

	public List<Position> getPositions() {
		return positions;
	}

	public Path(Creature creature, int x, int y) {
		Position creaturePos = new Position(creature.x, creature.y, creature.z);
		Position coordPos = new Position(x, y, creature.z);
		positions = pf.findPath(creature, creaturePos, coordPos, 300);
	}
}
