package Utils;

import java.util.List;

import Elements.Creature;

public class Path {

	private static PathFinder pf = new PathFinder();

	private List<Position> positions;

	public List<Position> getPositions() {
		return positions;
	}

	public Path(Creature creature, int x, int y) {
		Position creaturePos = new Position(creature.getX(), creature.getY(), creature.getZ());
		Position coordPos = new Position(x, y, creature.getZ());
		positions = pf.findPath(creature, creaturePos, coordPos, 300);
	}
}
