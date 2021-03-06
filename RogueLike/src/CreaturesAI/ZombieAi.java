package CreaturesAI;

import Elements.Creature;
/**
 * Defines the basic AI behaviours of the zombies
 */

public class ZombieAi extends CreatureAi {
	
	private Creature player;
	
	public ZombieAi(Creature creature, Creature player){
		super(creature);
		this.player = player;
	}
	
	public void onUpdate(){
		if(Math.random()<0.2){
			return;
		}
		if(creature.canSee(player.getX(), player.getY(), player.getZ())){
			hunt(player);
		} else{
			wander();
		}
	}
	
}
