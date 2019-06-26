package Rogue.Screens;

import Rogue.Creature;
import Rogue.Item;

public class EquipScreen extends InventoryBasedScreen {

	public EquipScreen(Creature player){
		super(player);
	}
	
	protected String getVerb(){
		return "wear or wield";
	}
	
	protected boolean isAcceptable(Item item){
		return item.getAttackValue() > 0 || item.getDefenseValue() > 0;
	}
	
	protected Screen use(Item item){
		player.equip(item);
		return null;
	}
}
