package Screens;
/**
 * Defines the screen that allows the player to select an item andr drop it in the dungeon
 */
import Elements.Creature;
import Elements.Item;

public class DropScreen extends InventoryBasedScreen {

	/**
	 * Constructor
	 * @param player creature that calls this window
	 */
	public DropScreen(Creature player){
		super(player);
	}
	
	@Override
	protected String getVerb() {
        return "drop";
    }
	
	@Override
	protected boolean isAcceptable(Item item) {
        return true;
    }
	
	@Override
	protected Screen use(Item item) {
        player.drop(item);
        return null;
    }
	
}
