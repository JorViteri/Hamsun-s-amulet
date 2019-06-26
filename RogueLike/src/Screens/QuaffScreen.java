package Screens;

import Elements.Creature;
import Elements.Item;

public class QuaffScreen extends InventoryBasedScreen{

	public QuaffScreen(Creature player){
		super(player);
	}
	
	public String getVerb(){
		return "quaff";
	}
	
	protected boolean isAcceptable(Item item){
		return item.getQuaffEffect() != null;
	}
	
	protected Screen use(Item item){
		player.quaff(item);
		return null;
	}
}
