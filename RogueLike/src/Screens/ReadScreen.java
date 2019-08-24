package Screens;
/**
 * Screen that allows the player to select a book to read
 */
import Elements.Creature;
import Elements.Item;

public class ReadScreen extends InventoryBasedScreen{
	private int sx;
    private int sy;
    
    /**
     * Constructor
     * @param player creature that called the scree
     * @param sx coordinate x
     * @param sy coordinate y
     */
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
