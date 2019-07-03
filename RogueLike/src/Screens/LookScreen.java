package Screens;

import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;

public class LookScreen extends TargetBasedScreen {

	public LookScreen(Creature player, String caption, int sx, int sy) {
		super(player, caption, sx, sy);
	}
	
	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
		Creature creature = player.creature(x, y, player.getZ());
		if (creature != null) {
			caption = creature.glyph() + " " + creature.name() + creature.getDetails();
			return;
		}

		Item item = player.getItem(x, y, player.getZ());
		if (item != null) {
			caption = item.getGlyph() + " " + player.nameOf(item) + item.getDetails();
			return;
		}

		Tile tile = player.tile(x, y, player.getZ());
		caption = tile.glyph() + " " + tile.getDetails();
	}

}
