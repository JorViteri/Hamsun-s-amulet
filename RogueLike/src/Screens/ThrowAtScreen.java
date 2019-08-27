package Screens;
/**
 * Screen that allows the player to select the objective to launch an item
 */
import DungeonComponents.Line;
import Elements.Creature;
import Elements.Item;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import Utils.Position;

public class ThrowAtScreen extends TargetBasedScreen {
	
	private Item item;
	private static WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
	private static String phrase  = getter.getDirectTranslation("ThrowAtScreen", "throwAt");

	/**
	 * Construcor
	 * @param player creature that called the screen
	 * @param sx coordinate in x
	 * @param sy coordinate in y
	 * @param item item to launch
	 */
    public ThrowAtScreen(Creature player, int sx, int sy, Item item) {
        super(player, String.format(phrase, player.nameOf(item)), sx, sy);
        this.item = item;
    }

    public boolean isAcceptable(int x, int y) {
        if (!player.canSee(x, y, player.getZ()))
            return false;
    
        for (Position p : new Line(player.getX(), player.getY(), x, y)){
            if (!player.realTile(p.getIntX(), p.getIntY(), player.getZ()).isGround())
                return false;
        }
    
        return true;
    }

    public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
        player.throwItem(item, x, y, player.getZ());
    }
}
