package DungeonComponents;

import Utils.Position;

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
