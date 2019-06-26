package Rogue.Screens;

import Elements.Creature;
import Elements.Item;

public class ConsumeScreen extends InventoryBasedScreen{

	public ConsumeScreen(Creature player){
		super(player);
	}
	
	protected String getVerb(){
		return "eat";
	}
	
	protected boolean isAcceptable(Item item){
		return item.getFoodValue() !=0;
	}
	
	protected Screen use(Item item){
		player.eat(item);
		return null;
	}
}
