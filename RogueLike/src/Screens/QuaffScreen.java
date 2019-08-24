package Screens;
/**
 * Screen used when the creature want to ingents items of the category "potion"
 */
import java.util.HashMap;
import Elements.Creature;
import Elements.Item;

public class QuaffScreen extends InventoryBasedScreen{
	
	private String actionType="drink";

	/**
	 * Constructor
	 * @param player creature that called this screen
	 */
	public QuaffScreen(Creature player){
		super(player);
	}
	
	/**
	 * Gets a verb for this
	 * 
	 * @return String that represents this screen
	 */
	public String getVerb(){
		return "quaff";
	}
	
	/**
	 * Checks if the item can be quaffed
	 * 
	 * @param item to check
	 */
	protected boolean isAcceptable(Item item){
		return item.getQuaffEffect() != null;
	}
	
	/**
	 * Ingests the item
	 * 
	 * @param item the item that is coing to be ingested
	 */
	protected Screen use(Item item){
		HashMap<String, String> verbData = new HashMap<>();
		verbData.put("actionType", actionType); //TODO esto seria aceptable para ingles y japo prque esto lo añado manual, luego es que le haga caso o no
		verbData.put("VbNum", "singular");
		verbData.put("VbPerson", "third");
		verbData.put("VbForm", "active"); //TODO pillar aleatorio con pasiva
		verbData.put("VbTime", "present"); //TODO pillar "aleatorio"
		String templateType = "BasicActionsTemplates";
		player.quaff(item, verbData, templateType);
		return null;
	}
}
