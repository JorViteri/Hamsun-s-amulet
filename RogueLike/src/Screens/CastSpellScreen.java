package Screens;
/**
 * Allows the player to select the objective of the spell
 */
import Elements.Creature;
import Elements.Spell;

public class CastSpellScreen extends TargetBasedScreen{
	
	private Spell spell;

	/**
	 * Constructor
	 * @param player creature that calls this scren
	 * @param caption string with some information of the action
	 * @param sx coordinate in x
	 * @param sy coordinate in y
	 * @param spell the spell which is going to be used
	 */
	public CastSpellScreen(Creature player, String caption, int sx, int sy, Spell spell) {
		super(player, caption, sx, sy);
		this.spell=spell;
		
	}
	
	public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
		player.castSpell(spell, x, y);
	}

}
