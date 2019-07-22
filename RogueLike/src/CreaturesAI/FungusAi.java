package CreaturesAI;

import Elements.Creature;
import Factories.CreatureFactory;
import Factories.ElementsFactory;

public class FungusAi extends CreatureAi{
	
	private CreatureFactory factory;
	//private int spreadcount;
	
	public FungusAi(Creature creature, CreatureFactory factory){
		super(creature);
		this.factory= factory;
	}
	
	public void onUpdate(){
		
	}
	
	/*public void onUpdate(){
		int n=(int)Math.random();
		if (spreadcount<1 && n < 0.002)
			spread();
	}*/
	
	/*private void spread(){
		int x = creature.x+(int)(Math.random()*11)-5;
		int y = creature.y+(int)(Math.random()*11)-5;
		
		if (!creature.canEnter(x,y))
			return;
		Creature child = factory.newFungus();
		child.x=x;
		child.y=y;
		spreadcount++;
	}*/
	
}
