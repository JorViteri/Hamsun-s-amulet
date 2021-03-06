package Screens;
/**
 * Screen that allows the player to sellect an item and equip it
 */
import Elements.Creature;
import Elements.Item;

public class EquipScreen extends InventoryBasedScreen {

	/**
	 * Constructor
	 * 
	 * @param player creature that calls this function
	 */
	public EquipScreen(Creature player){
		super(player);
	}
	
	protected String getVerb(){
		return "equip";
	}
	
	protected boolean isAcceptable(Item item){
		return item.getAttackValue() > 0 || item.getDefenseValue() > 0;
	}
	
	protected Screen use(Item item){
		player.equip(item);
		return null;
	}
}
