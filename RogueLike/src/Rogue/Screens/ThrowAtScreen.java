package Rogue.Screens;

import DungeonComponents.Line;
import Elements.Creature;
import Elements.Item;
import Utils.Position;

public class ThrowAtScreen extends TargetBasedScreen {
	private Item item;

    public ThrowAtScreen(Creature player, int sx, int sy, Item item) {
        super(player, "Throw " + player.nameOf(item) + " at?", sx, sy);
        this.item = item;
    }

    public boolean isAcceptable(int x, int y) {
        if (!player.canSee(x, y, player.z))
            return false;
    
        for (Position p : new Line(player.x, player.y, x, y)){
            if (!player.realTile(p.getIntX(), p.getIntY(), player.z).isGround())
                return false;
        }
    
        return true;
    }

    public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
        player.throwItem(item, x, y, player.z);
    }
}
