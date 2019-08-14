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
	private Realizator realizator;
	private RestrictionsFactory resFactory;
	
	public PlayerAi(Creature creature, ArrayList<String> messages,  FieldOfView fov){
		super(creature);
		this.messages = messages;
		this.fov = fov;
		this.factory = WordDataGetterAndRealizatorFactory.getInstance();
		this.getter = factory.getWordDataGetter();
		this.realizator = factory.getRealizator();
		this.resFactory =  RestrictionsFactory.getInstance();
	}
	
	@Override
	public void onEnter(int x, int y, int z, Tile tile){
		if (tile.isGround()){
			creature.setX(x);
			creature.setY(y);
			creature.setZ(z);
			Item item = creature.item(creature.getX(), creature.getY(), creature.getZ());
			if (item != null){
				//creature.notify("There's a " + creature.nameOf(item) + " "+item.getCharacteristic()+ "here.");
				String templateType = "ToBeInTemplate";
				HashMap<String, String> verb = new HashMap<>();
				verb.put("actionType", "be_space");
				verb.put("adverb", "here");
				verb.put("Form", "Singular");
				HashMap<String, String> CDMorf  = item.getMorfData("singular");
				HashMap<String, String> CDNameAndAdjective =  item.getNameAndAdjective("singular");
				CDNameAndAdjective.put("name", creature.nameOf(item)); 
				Restrictions res = resFactory.getRestrictions("singular", "third",
						"active", "present", null, null,CDMorf.get("genere"), CDMorf.get("number"), null, null, null, null);
				creature.doActionComplex(verb, null, CDNameAndAdjective, null, null, res, templateType);
			}
			if (tile.isStair()){
				
				String templateType = "ToBeInTemplate";
				HashMap<String, String> verb = new HashMap<>();
				verb.put("actionType", "be_space");
				verb.put("adverb", "here");
				verb.put("Form", "Plural");
				HashMap<String, String> CDMorf  = tile.getMorfStairs();
				HashMap<String, String> CDNameAndAdjective =  tile.getStairsNounAndType( CDMorf.get("number"));
				Restrictions res = resFactory.getRestrictions("singular", "third",
						"active", "present", null, null,CDMorf.get("genere"), CDMorf.get("number"), null, null, null, null);
				creature.doActionComplex(verb, null, CDNameAndAdjective, null, null, res, templateType);
				
				//creature.notify("There're " + tile.getChairTypeString() + " here."); //tengo que hacer que se 
			}
				
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
