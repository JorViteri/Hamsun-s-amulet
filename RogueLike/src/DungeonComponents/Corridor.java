package DungeonComponents;

import Utils.Position;

/**
 * Defines a corridor of the dungeon which connects two rooms
 * @author comec
 *
 */
public class Corridor {

	
	private Position beginning;
	private Position end;
	
	
	public Corridor (Position end, Position beginning){
		this.beginning=beginning;
		this.end=end;
		
	}

	public Position getBeginning() {
		return beginning;
	}

	public void setBeginning(Position beginning) {
		this.beginning = beginning;
	}

	public Position getEnd() {
		return end;
	}

	public void setEnd(Position end) {
		this.end = end;
	}


	
	
	
	
	
}
