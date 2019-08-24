package Screens;
/**
 * Gives information about a slected item
 */
import Elements.Creature;
import Elements.Item;

public class ExamineScreen extends InventoryBasedScreen {

	/**
	 * Cosntructor
	 * @param player creature that calls the function
	 */
	public ExamineScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "examine";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		player.notify("Item: " + player.nameOf(item) + "." + item.getDetails());
		return null;
	}
}
