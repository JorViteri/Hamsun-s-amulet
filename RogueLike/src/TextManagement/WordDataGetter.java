package TextManagement;
/**
 * Interface for defining the classes that have the duty to obtain the words and their data from the resources
 */
import java.io.File;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public interface WordDataGetter {

	/**
	 * Gets the data for a noun
	 * @param noun
	 * @return
	 */
	public HashMap<String, String> getNounData(String noun);

	/**
	 * Gets the data for an adjective
	 * @param adj
	 * @param genere
	 * @return
	 */
	public HashMap<String, String> getAdjData(String adj, String genere);

	/**
	 * Gets the data for a verb
	 * @param verb
	 * @return
	 */
	public HashMap<String, String> getVerbData(String verb);

	/**
	 * Gets the dara for an adverb
	 * @param adv
	 * @return
	 */
	public HashMap<String, String> getAdvData(String adv);
	
	/**
	 * Gets a direct translation for a phrase
	 * @param mainKey
	 * @param specificKey
	 * @return
	 */
	public String getDirectTranslation(String mainKey, String specificKey);

	/**
	 * Obtains a random element from an array
	 * @param arr
	 * @return
	 */
	default String getRandomSeed(String[] arr){
		Random r = new Random();
		int randomNumber = r.nextInt(arr.length);
		return arr[randomNumber];
	}
	
	/**
	 *  Gets an attack verb based on the tool of the attack
	 * @param itemKey
	 * @return
	 */
	default String getAttackVerb(String itemKey){
		JSONArray objectVbArr=null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		try{
			File file = new File("res/Others Text Resources/weapons-verbs.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectVbArr = object.getJSONArray(itemKey);
		} catch(Exception e){
			e.printStackTrace();
		}		
		pos = random.nextInt(objectVbArr.length());
		return objectVbArr.getString(pos);
	}

	/**
	 * Get a verb based o the type of action and tool if used
	 * @param actionType
	 * @param itemKey
	 * @return
	 */
	default String getActionVerb(String actionType, String itemKey) {
		JSONArray objectVbArr=null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		if ((actionType.equals("Attack"))||(actionType.equals("Summon"))){
			return getAttackVerb(itemKey);
		}
	
		try{
			File file = new File("res/Others Text Resources/screenActions-verbs.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectVbArr = object.getJSONArray(actionType);
		} catch(Exception e){
			e.printStackTrace();
		}		
		pos = random.nextInt(objectVbArr.length());
		return objectVbArr.getString(pos);
	}

	/**
	 * Gets a preposition
	 * @param CID
	 * @param genere
	 * @param number
	 * @return
	 */
	public String getPreposition(String CID, String genere, String number);

	/**
	 * Gets an article
	 * @param genere
	 * @param number
	 * @return
	 */
	public String getArticle(String genere, String number);

	/**
	 * Gets an Undefined article
	 * @param string
	 * @param string2
	 * @return
	 */
	public String getDetUndefined(String string, String string2);


}
