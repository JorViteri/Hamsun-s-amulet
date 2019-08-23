package Screens;

import Elements.Creature;
import Elements.Item;

public class ExamineScreen extends InventoryBasedScreen {

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
