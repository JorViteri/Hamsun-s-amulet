package Rogue.Screens;

import Rogue.Creature;
import Rogue.Position;
import Rogue.Line;

public class FireWeaponScreen extends TargetBasedScreen {
	public FireWeaponScreen(Creature player, int sx, int sy) {
		super(player, "Fire " + player.nameOf(player.getWeapon()) + " at?", sx, sy);
	}

	public boolean isAcceptable(int x, int y) {
		if (!player.canSee(x, y, player.z))
			return false;

		for (Position p : new Line(player.x, player.y, x, y)) {
			if (!player.realTile(p.getIntX(), p.getIntY(), player.z).isGround())
				return false;
		}

		return true;
	}

	public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
		Creature other = player.creature(x, y, player.z);

		if (other == null)
			player.notify("There's no one there to fire at.");
		else
			player.rangedWeaponAttack(other);
	}
}
