package Rogue.Screens;

import Rogue.Creature;
import Rogue.Item;

public class ReadScreen extends InventoryBasedScreen{
	private int sx;
    private int sy;
    
    public ReadScreen(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }

    protected String getVerb() {
        return "read";
    }

    protected boolean isAcceptable(Item item) {
        return !item.getWrittenSpells().isEmpty();
    }

    protected Screen use(Item item) {
        return new ReadSpellScreen(player, sx, sy, item);
    }
}
