package Utils;

/**
 * Defines the class of Position in the dungeon
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import Screens.PlayScreen;

public class Position {

	private double x;
	private double y;
	private int z;
	private static int gridWidth = 89;
	private static int gridHeight = 30; 

	/**
	 * Constructor.
	 * @param x 
	 * @param y
	 */
	public Position(double x, double y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates the relative position between p and p2
	 * @param p
	 * @param p2
	 */
	public Position(Position p, Position p2) {
		this.x = p.getX() - p2.getX();
		this.y = p2.getY() - p.getY();
	}
	
	/**
	 * Calculates the real distance between two positions. 
	 * @param b The second position.
	 * @return The distance
	 */
	public Double distance(Position b) {
		Position a = this;
		return new Double(Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2)));
	}

	// Farthest first
	public static final Comparator<Position> MAX_DISTANCE_TO_PLAYER = new Comparator<Position>() {
		public int compare(Position p1, Position p2) {
			return p2.distance(PlayScreen.getPlayer().getPosition())
					.compareTo(p1.distance(PlayScreen.getPlayer().getPosition()));
		}
	};

	// Nearest first
	public static final Comparator<Position> MIN_DISTANCE_TO_PLAYER = new Comparator<Position>() {
		public int compare(Position p1, Position p2) {
			return p1.distance(PlayScreen.getPlayer().getPosition())
					.compareTo(p2.distance(PlayScreen.getPlayer().getPosition()));
		}
	};

	// Middle point
	public Position getMiddlePoint(Position b){
		int mx = (int) Math.round((0.5*(this.x+b.getX())));
		int my = (int) Math.round((0.5*(this.y+b.getY())));
		Position m = new Position(mx, my, b.getZ());
		return m;
	}

	public String toString() {
		return this.getX() + ", " + this.getY();
	}

	
	@Override
	public int hashCode() {
		final int prime = 93;
		int result = 9;
		result = result + this.getIntX() * prime;
		result = result + this.getIntY() * prime;
		result = result + this.getZ() * prime;
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if (this==obj)
			return true;
		if(obj==null)
			return false;
		if(!(obj instanceof Position))
			return false;
		Position p = (Position) obj;
		boolean x = this.getX()==p.getX();
		boolean y = this.getY()==p.getY();
		boolean z = this.getZ()==p.getZ();
		return (x && y && z);
		
	}
	
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	
	public int getIntX(){
		return (int)this.x;
	} 
	
	public int getIntY(){
		return (int) this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getZ(){
		return this.z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}

	/*/**
	 * Returns the room wich contains a given position
	 *  @param level  the level of the dungeon we are checking
	 *  @return the index of the room, null if it doesn' t belong to any room
	 */
	/*
	 * public Room getRoom(int level){ for(Room r : PlayScreen.getRooms()){
	 * //TODO a�adir levels if(r.getFloor().contains(this)) return r; } return
	 * null; }
	 */

	/**
	 * Checks if a position is within the map's limits.
	 * @return True if it is within the map's limits, false otherwise.
	 */
	public boolean isValidPosition() {
		if (0 <= this.getX() && this.getX() <= gridWidth && 0 <= this.getY() && this.getY() <= gridHeight) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a position is within the map's limits for a room, considering that it needs space for the wall.
	 * @return True if it's a valid position for a room, false otherwise.
	 */
	public boolean isValidPositionForRoom() {
		if (1 <= this.getX() && this.getX() <= gridWidth - 1 && 1 <= this.getY() && this.getY() <= gridHeight - 1) {
			return true;
		}
		return false;
	}

	public boolean isPositionUsed(Set<Position> posUsed) {
		if (posUsed.contains(this)) {
			return true;
		} else
			return false;
	}

	/**
	 * Get the neighbors of a  Position, in a determinate order.
	 * @param numNeighbors Can be 4 (NSWE) or 8 (NSWE and its combinations)
	 * @return The list of neighbors, in a determinate order.
	 */
	public ArrayList<Position> getNeighbors(int numNeighbors) {
		ArrayList<Position> toReturn = new ArrayList<Position>();
		if (this.getPositionN().isValidPosition())
			toReturn.add(this.getPositionN());
		if (numNeighbors == 8 && this.getPositionNE().isValidPosition())
			toReturn.add(this.getPositionNE());
		if (this.getPositionE().isValidPosition())
			toReturn.add(this.getPositionE());
		if (numNeighbors == 8 && this.getPositionSE().isValidPosition())
			toReturn.add(this.getPositionSE());
		if (this.getPositionS().isValidPosition())
			toReturn.add(this.getPositionS());
		if (numNeighbors == 8 && this.getPositionSW().isValidPosition())
			toReturn.add(this.getPositionSW());
		if (this.getPositionW().isValidPosition())
			toReturn.add(this.getPositionW());
		if (numNeighbors == 8 && this.getPositionNW().isValidPosition())
			toReturn.add(this.getPositionNW());
		return toReturn;
	}

	/** Gets a solid square of Positions, from the upper-left Position
	 * @param bR The bottom-right position.
	 * @return The list of positions within the square.
	 */
	public ArrayList<Position> getSolidSquare(Position bR) {
		Position uL = this;
		int z = bR.getZ();
		ArrayList<Position> toReturn = new ArrayList<Position>();
		for (int i = (int)uL.getX(); i <= (int)bR.getX(); i++) {
			for (int j = (int)uL.getY(); j >= (int)bR.getY(); j--) {
				toReturn.add(new Position(i, j, z));
			}
		}
		return toReturn;
	}

	/** Gets, from the upper-left Position, the border of a square.
	 * @param bR The bottom-right position.
	 * @return The list of positions of the border of the square.
	 */
	public ArrayList<Position> getSquare(Position bR) {
		ArrayList<Position> toReturn = this.getSolidSquare(bR);
		ArrayList<Position> toRemove = this.getPositionSE().getSolidSquare(bR.getPositionNW());
		toReturn.removeAll(toRemove);
		return toReturn;
	}

	public Position getPositionN() {
		Position p = new Position(this.getX(), this.getY() +1, this.getZ());
		return p;
	}

	public Position getPositionNE() {
		Position p = new Position(this.getX() + 1, this.getY() + 1, this.getZ());
		return p;
	}

	public Position getPositionE() {
		Position p = new Position(this.getX() + 1, this.getY(), this.getZ());
		return p;
	}

	public Position getPositionSE() {
		Position p = new Position(this.getX() + 1, this.getY() - 1, this.getZ());
		return p;
	}

	public Position getPositionS() {
		Position p = new Position(this.getX(), this.getY() - 1, this.getZ());
		return p;
	}

	public Position getPositionSW() {
		Position p = new Position(this.getX() - 1, this.getY() - 1, this.getZ());
		return p;
	}

	public Position getPositionW() {
		Position p = new Position(this.getX() - 1, this.getY(), this.getZ());
		return p;
	}

	public Position getPositionNW() {
		Position p = new Position(this.getX() - 1, this.getY() + 1, this.getZ());
		return p;
	}

	/**
	 * Gets the relative position to another Position, in coordinates.
	 * @param pos The position to which we're calculating the relative Position.
	 * @return The relative position.
	 */
	public Position getRelativeDetailedPosition(Position pos) {
		return new Position(pos.getX() - this.getX(), this.getY() - pos.getY(), this.getZ());
	}

	/**
	 * Gets the relative position to another Position, in text.
	 * @param pos The position to which we're calculating the relative Position.
	 * @return The description of relative position.
	 */
	public String getRelativePosition(Position position) {
		int x = position.getIntX() - this.getIntX();
		int y = this.getIntY() - position.getIntY();
		if (x == 0)
			if (y > 0)
				return ("north");
			else
				return ("south");
		if (y == 0)
			if (x < 0)
				return ("east");
			else
				return ("west");
		if (x == -y)
			if (x < 0)
				return ("southeast");
			else
				return ("northwest");
		if (x == y)
			if (x < 0)
				return ("northeast");
			else
				return ("southwest");

		if (Math.abs(x) <= Math.abs(y / 4))
			if (y > 0)
				return ("northish");
			else
				return ("southish");
		if (Math.abs(y) <= Math.abs(x / 4))
			if (x < 0)
				return ("eastish");
			else
				return ("westish");

		if (x < 0) {
			if (y > 0)
				return ("northeastish");
			else
				return ("southeastish");
		} else if (y > 0)
			return ("northwestish");
		else
			return ("southwestish");
	}

}
