package DungeonComponents;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import Utils.Position;

/**
 * Defines a room of the dungeon
 * @author comec
 *
 */
public class Room {

	private Position upLeft;
	private Position bottomLeft;
	private Position upRight;
	private Position bottomRight;
	private Position center;
	private ArrayList<Position> walls = new ArrayList<Position>();
	private int id;

	/** Constructor of a Room
	 * @param bottomLeft the bottom-left Position
	 * @param topRight the top-right Position
	 * @param id The index of the room.
	 */

	public Room(Position bottomLeft, Position topRight, int id) {
		this.id = id; 
		this.bottomLeft = bottomLeft;
		this.upRight = topRight;
		this.upLeft = new Position(bottomLeft.getX(), topRight.getY(), bottomLeft.getZ());
		this.bottomRight = new Position(topRight.getX(), bottomLeft.getY(), bottomLeft.getZ());
		this.center = this.genRoomCenter();
		this.walls = upLeft.getPositionNW().getSquare(bottomRight.getPositionSE());
	}

	public Position getCenter(){
		return this.center;
	}
	
	public int getWide() {
		return 1 + (-(this.getRoomUpperLeft().getIntX() - this.getRoomBottomRight().getIntX()));
	}

	
	public int getHeight() {
		return 1 + (this.getRoomUpperLeft().getIntY() - this.getRoomBottomRight().getIntY());
	}

	public boolean isCorner(Position p, ArrayList<Position> walls) {
		return !(walls.contains(p.getPositionN()) && walls.contains(p.getPositionS()))
				|| (walls.contains(p.getPositionE()) && walls.contains(p.getPositionW()));
	}

	public ArrayList<Position> getFloor() {
		ArrayList<Position> toReturn = new ArrayList<Position>();
		toReturn.addAll(this.getRoomUpperLeft().getSolidSquare(this.getRoomBottomRight()));
		return toReturn;
	}
	
	public ArrayList<Position> getFloorAndWalls() {
		ArrayList<Position> compltRoom = new ArrayList<Position>();
		compltRoom.addAll(this.getRoomUpperLeft().getPositionNW().getSolidSquare(this.getRoomBottomRight().getPositionSE()));
		return compltRoom;
	}

	public void addWallPosition(Position p) {
		ArrayList<Position> walls = this.getWalls();
		walls.add(p);
	}

	public ArrayList<Position> getWalls() {
		return this.walls;
	}

	public Position getRoomUpperLeft() {
		return this.upLeft;
	}

	public Position getRoomUpperRight() {
		return this.upRight;
	}

	public Position getRoomBottomLeft() {
		return this.bottomLeft;
	}

	public Position getRoomBottomRight() {
		return this.bottomRight;
	}

	private Position genRoomCenter() {
		int leftx, lefty, rightx, righty;
		leftx = this.upLeft.getIntX();
		lefty = this.upLeft.getIntY();
		rightx = this.bottomRight.getIntX();
		righty = this.bottomRight.getIntY();
		Position center = new Position((leftx + rightx) / 2, (lefty + righty) / 2, this.upLeft.getZ());
		return center;
	}
	
	public Position getRandom(){
		ArrayList<Position> floor = this.getFloor();
		int x = ThreadLocalRandom.current().nextInt(0, floor.size()-1);
		return floor.get(x);
	}

	/**
	 * Checks if two rooms share any position
	 * @param room
	 * @return false if the rooms share a position
	 */
	public boolean disjointRooms(Room room){
		ArrayList<Position> thisFloor = new ArrayList<>();
		ArrayList<Position> otherFloor = new ArrayList<>();
		
		thisFloor = this.getFloorAndWalls();
		otherFloor = room.getFloorAndWalls();
		
		for (Position p: thisFloor){
			for (Position o: otherFloor){
				if (p.equals(o)){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if a Room contains a position
	 * @param position
	 * @return true if the position is in the room, false in the other case
	 */
	public boolean contais(Position position) {
		Position topRight = this.getRoomUpperRight();
		Position bottomLeft = this.getRoomBottomLeft();
		
		if(!((position.getIntX()>=bottomLeft.getIntX())&&(position.getIntX()<=topRight.getIntX()))){
			return false;
		} 
		if(!((position.getIntY()>=bottomLeft.getIntY()) &&(position.getIntY()<=topRight.getIntY()))){
			return false;
		} 
		return true;
	}
	
}
