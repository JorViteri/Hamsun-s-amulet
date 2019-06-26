package Rogue;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Room {

	private static final long serialVersionUID = 1060623638149583738L;

	private Position upLeft;
	private Position bottomLeft;
	private Position upRight;
	private Position bottomRight;
	private Position center;
	private int id;
	private ArrayList<Position> doors = new ArrayList<Position>();
	private ArrayList<Position> walls = new ArrayList<Position>();

	/** Constructor of a Room
	 * @param upLeft the upper-left Position
	 * @param botRight the bottom-right Position
	 * @param ind The index of the room.
	 */

	public Room(Position bottomLeft, Position topRight, int id) {
		this.id = id; 
		this.bottomLeft = bottomLeft;
		this.upRight = topRight;
		this.upLeft = new Position(bottomLeft.getX(), topRight.getY(), bottomLeft.getZ());
		this.bottomRight = new Position(topRight.getX(), bottomLeft.getY(), bottomLeft.getZ());
		this.center = this.genRoomCenter();
		this.walls = upLeft.getPositionNW().getSquare(bottomRight.getPositionSE());
		// addDoors() adds to this.walls the tiles needed
		// this.doors = addDoors(this.walls);
	}

	// TODO aun no me interesan las puertas
	/**
	 * Picks two random positions within the walls of a room and makes them doors. It avoids picking corners or two together positions
	 * @param walls The walls of the Room.
	 * @return A list of the doors.
	 */
	/*
	 * public ArrayList<Position> addDoors(ArrayList<Position> walls) {
	 * ArrayList<Position> toReturn = new ArrayList<Position>(); Position p, p2;
	 * int randomIndex = this.getRNG().nextInt(walls.size()); while
	 * (toReturn.size() != 2) { p = walls.get(randomIndex); // Is valid and it's
	 * not a corner if (p.isValidPositionForDoor() && !isCorner(p, walls)) { if
	 * (toReturn.size() == 0) toReturn.add(p); else { // Avoids having two doors
	 * together. p2 = toReturn.get(0); if (!p2.getNeighbors(4).contains(p) && p
	 * != p2) toReturn.add(p); } } randomIndex =
	 * this.getRNG().nextInt(walls.size()); } return toReturn; }
	 */

	public Position getCenter(){
		return this.center;
	}
	
	public int getWide() {
		return 1 + (-(this.getRoomUpperLeft().getIntX() - this.getRoomBottomRight().getIntX()));
	}

	public int getHeight() {
		return 1 + (-(this.getRoomUpperLeft().getIntY() - this.getRoomBottomRight().getIntY()));
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

	public ArrayList<Position> getFloorAndDoors() {
		ArrayList<Position> toReturn = new ArrayList<Position>();
		toReturn.addAll(this.getRoomUpperLeft().getSolidSquare(this.getRoomBottomRight()));
		toReturn.addAll(this.getDoors());
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

	public ArrayList<Position> getDoors() {
		return this.doors;
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
	
	
	

	// TODO de dungeon lo pilla de playrscreen, dunno why
	// TODO aun tampoco me interesa esto
	/*
	 * public Random getRNG() { return Dungeon.getRNG(); }
	 */

	// TODO puede venir bien para dar feedback... pero quizás merezca la pena
	// sólo comprobar un % de la habitación.
	// TODO tampoco me interesa aun esta funcion, la voy a dejar a parte
	/**
	 * Checks if a room has been completely explored.
	 * @return True if the room is completely explored, false otherwise
	 */

	/*
	 * TODO index es el identificador de la habitacion, luego de este obtiene
	 * sus casillas y va comprobando de una en una si esta explorada
	 */

	/*
	public boolean isAllExplored() {
		Tile t;
		Room r = PlayScreen.getPlayer().getPosition().getRoom(PlayScreen.getPlayer().getIndexDungeonLevel());
		int index = r.getIndex();
		ArrayList<Position> pos = PlayScreen.getDungeon().getLevel(PlayScreen.getPlayer().getIndexDungeonLevel())
				.getRooms().get(index).getFloorAndDoors();
		for (Position p : pos) {
			t = PlayScreen.getDungeon().getLevel(PlayScreen.getPlayer().getIndexDungeonLevel()).getTile(p);
			if (!t.isExplored())
				return false;
		}
		return true;
	}*/

	/*
	 * private int getIndex() { return this.index; }
	 */
}
