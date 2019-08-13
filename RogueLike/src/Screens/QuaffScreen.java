package Screens;

import java.util.HashMap;

import Elements.Creature;
import Elements.Item;
import TextManagement.WordDataGetter;

public class QuaffScreen extends InventoryBasedScreen{
	
	private String actionType="drink";

	public QuaffScreen(Creature player){
		super(player);
	}
	
	public String getVerb(){
		return "quaff";
	}
	
	protected boolean isAcceptable(Item item){
		return item.getQuaffEffect() != null;
	}
	
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
