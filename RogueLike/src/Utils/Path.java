package Utils;

/**
 * Class that obtains the path to follow to reach a position
 */
import java.util.List;

import Elements.Creature;

public class Path {

	private static PathFinder pf = new PathFinder();

	private List<Position> positions;

	public List<Position> getPositions() {
		return positions;
	}

	/**
	 * Constructor
	 * @param creature creature to move
	 * @param x coordinate of the destination
	 * @param y coordinate of the final destination
	 */
	public Path(Creature creature, int x, int y) {
		Position creaturePos = new Position(creature.getX(), creature.getY(), creature.getZ());
		Position coordPos = new Position(x, y, creature.getZ());
		positions = pf.findPath(creature, creaturePos, coordPos, 300);
	}
}
