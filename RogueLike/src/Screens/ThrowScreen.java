package Screens;

/**
 * Defines the Screen used to select which item throw
 */
import Elements.Creature;
import Elements.Item;

public class ThrowScreen extends InventoryBasedScreen {
	private int sx;
	private int sy;

	/**
	 * Constructor
	 * @param player creature tha calls this screen
	 * @param sx position in x
	 * @param sy position in y
	 */
	public ThrowScreen(Creature player, int sx, int sy) {
		super(player);
		this.sx = sx;
		this.sy = sy;
	}

	/**
	 * Gets the verb for this screen
	 * 
	 * @return string verb for this screen
	 */
	protected String getVerb() {
		return "throw";
	}

	/**
	 * Checks if the item is acceptable to throw
	 */
	protected boolean isAcceptable(Item item) {
		return true;
	}

	/**
	 * calls the screen in which the creature selects the objective
	 */
	protected Screen use(Item item) {
		return new ThrowAtScreen(player, sx, sy, item);
	}
}
