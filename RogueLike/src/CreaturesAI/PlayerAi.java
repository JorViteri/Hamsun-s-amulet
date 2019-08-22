package CreaturesAI;

import java.util.ArrayList;
import java.util.HashMap;

import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import TextManagement.Realizator;
import TextManagement.Restrictions;
import TextManagement.RestrictionsFactory;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import Utils.FieldOfView;

/**
 * Implements the behaviors that the player can do
 */

public class PlayerAi extends CreatureAi {

	private ArrayList<String> messages;
	private FieldOfView fov;
	private WordDataGetterAndRealizatorFactory factory;
	private WordDataGetter getter;
	private RestrictionsFactory resFactory;

	public PlayerAi(Creature creature, ArrayList<String> messages, FieldOfView fov) {
		super(creature);
		this.messages = messages;
		this.fov = fov;
		this.factory = WordDataGetterAndRealizatorFactory.getInstance();
		this.getter = factory.getWordDataGetter();
		this.resFactory = RestrictionsFactory.getInstance();
	}

	/**
	 * In this implementation it also shows if there is an item or stairs in the current tile.
	 * @param x Int that indicates the coordinate in x axis.
	 * @param y Int that indicates the coordinate in y axis.
	 * @param z Int that indicates the coordinate in z axis.
	 * @param tile The tile which the player is trying to access.
	 */
	@Override
	public void onEnter(int x, int y, int z, Tile tile) {
		if (tile.isGround()) {
			creature.setX(x);
			creature.setY(y);
			creature.setZ(z);
			Item item = creature.item(creature.getX(), creature.getY(), creature.getZ());
			if (item != null) {
				String templateType = "ToBeInTemplate";
				HashMap<String, String> verb = new HashMap<>();
				verb.put("actionType", "be_space");
				verb.put("adverb", "here");
				verb.put("Form", "Singular");
				HashMap<String, String> CDMorf = item.getMorfData("singular");
				HashMap<String, String> CDNameAndAdjective = item.getNameAndAdjective("singular");
				CDNameAndAdjective.put("name", creature.nameOf(item));
				Restrictions res = resFactory.getRestrictions("singular", "third", "active", "present", null, null,
						CDMorf.get("genere"), CDMorf.get("number"), null, null, null, null, null, null);
				creature.doActionComplex(verb, null, CDNameAndAdjective, null, null, null, res, templateType);
			}
			if (tile.isStair()) {

				String templateType = "ToBeInTemplate";
				HashMap<String, String> verb = new HashMap<>();
				verb.put("actionType", "be_space");
				verb.put("adverb", "here");
				verb.put("Form", "Plural");
				HashMap<String, String> CDMorf = tile.getMorfStairs();
				HashMap<String, String> CDNameAndAdjective = tile.getStairsNounAndType(CDMorf.get("number"));
				Restrictions res = resFactory.getRestrictions("singular", "third", "active", "present", null, null,
						CDMorf.get("genere"), CDMorf.get("number"), null, null, null, null, null, null);
				creature.doActionComplex(verb, null, CDNameAndAdjective, null, null, null, res, templateType);

			}

		} else {
			HashMap<String, String> subjectData = creature.getMorfData("singular");
			HashMap<String, String> subject = creature.getNameAdjectiveKey("singular");
			HashMap<String, String> verb = new HashMap<>();
			verb.put("actionType", "collide");
			verb.put("adverb", null);
			HashMap<String, String> ccThing = getter.getNounData("wall");
			HashMap<String, String> ccData = new HashMap<>();
			ccData.put("genere", ccThing.get("genere"));
			ccData.put("number", "singular");
			HashMap<String, String> cc = new HashMap<>();
			cc.put("name", ccThing.get("baseNoun"));
			cc.put("type", "CCI");
			cc.put("characteristic", "");

			Restrictions res = resFactory.getRestrictions("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), null, null, null, null, ccData.get("genere"),
					ccData.get("number"), null, null);

			creature.doActionComplex(verb, subject, null, null, cc, null, res, "CollideWall");
		}/*else if (tile.isDiggable()) {
			creature.dig(x, y, z);
		}*/
	}
	
	public void onNotify(String message){
		messages.add(message);
	}
	
	public boolean canSee(int wx, int wy, int wz){
		return fov.isVisible(wx,wy,wz);
	}
	
	@Override
	public void onGainLevel(){
		
	}
	
	@Override
	public Tile rememberedTile(int wx, int wy, int wz) {
        return fov.tile(wx, wy, wz);
    }
}
