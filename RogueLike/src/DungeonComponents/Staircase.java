package DungeonComponents;

import Utils.Position;

/**
 * Defines the Staircase class which links two floors of the dungeon
 * @author comec
 *
 */
public class Staircase {

	private Position beginning;
	private Position ending;
	
	public Staircase(Position a, Position b){
		this.beginning=a;
		this.ending = b;
	}

	public Position getBeginning() {
		return beginning;
	}
	
	public Position getEnding() {
		return ending;
	}


	
	
}
