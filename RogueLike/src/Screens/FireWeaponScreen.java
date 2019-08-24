package Screens;

/**
 * Screen that allows the creature to select a target to shoot
 */
import DungeonComponents.Line;
import Elements.Creature;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import Utils.Position;

public class FireWeaponScreen extends TargetBasedScreen {

	private static WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();

	/**
	 * Constructor
	 * @param player creature that called the screen
	 * @param sx coordinate x
	 * @param sy coordinate y
	 */
	public FireWeaponScreen(Creature player, int sx, int sy) {

		super(player, String.format(getter.getDirectTranslation("FireWeaponScreen", "title"),
				player.nameOf(player.getWeapon())), sx, sy);

	}

	public boolean isAcceptable(int x, int y) {
		if (!player.canSee(x, y, player.getZ()))
			return false;

		for (Position p : new Line(player.getX(), player.getY(), x, y)) {
			if (!player.realTile(p.getIntX(), p.getIntY(), player.getZ()).isGround())
				return false;
		}

		return true;
	}

	public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
		Creature other = player.creature(x, y, player.getZ());

		if (other == null)
			player.notify(getter.getDirectTranslation("FireWeaponScreen", "noObjective"));
		else
			player.rangedWeaponAttack(other);
	}
}
