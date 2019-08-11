package CreaturesAI;

import Elements.Creature;
/**
 * Defines the basic AI behaviours of the bats
 * 
 * @author comec
 */

public class BatAi extends CreatureAi {

	public BatAi(Creature creature){
		super(creature);
	}
	
	public void onUpdate(){
		wander();
		wander();
	}
}
