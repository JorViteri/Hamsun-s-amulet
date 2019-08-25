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
	 * @return HashMap with the data
	 */
	public HashMap<String, String> getNounData(String noun);

	/**
	 * Gets the data for an adjective
	 * @param adj
	 * @param gender
	 * @return HashMap with the data
	 */
	public HashMap<String, String> getAdjData(String adj, String gender);

	/**
	 * Gets the data for a verb
	 * @param verb
	 * @return HashMap with the data
	 */
	public HashMap<String, String> getVerbData(String verb);

	/**
	 * Gets the dara for an adverb
	 * @param adv
	 * @return HashMap with the data
	 */
	public HashMap<String, String> getAdvData(String adv);

	/**
	 * Gets a direct translation for a phrase
	 * @param mainKey key that identifies the class that calls the function
	 * @param specificKey that identifies the phrase
	 * @return the translation of a phrase in string format
	 */
	public String getDirectTranslation(String mainKey, String specificKey);

	/**
	 * Obtains a random element from an array
	 * @param arr
	 * @return the string key
	 */
	default String getRandomSeed(String[] arr) {
		Random r = new Random();
		int randomNumber = r.nextInt(arr.length);
		return arr[randomNumber];
	}

	/**
	 *  Gets an attack verb based on the tool of the attack
	 * @param itemKey tool to look the verbs associated with it
	 * @return the verb as string
	 */
	default String getAttackVerb(String itemKey) {
		JSONArray objectVbArr = null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		try {
			File file = new File("res/Others Text Resources/weapons-verbs.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
			objectVbArr = object.getJSONArray(itemKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pos = random.nextInt(objectVbArr.length());
		return objectVbArr.getString(pos);
	}

	/**
	 * Get a verb based o the type of action and tool if used
	 * @param actionType type of action being performed
	 * @param itemKey tool if it's necessary to look for it's verbs
	 * @return the verb as string
	 */
	default String getActionVerb(String actionType, String itemKey) {
		JSONArray objectVbArr = null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		if ((actionType.equals("Attack")) || (actionType.equals("Summon"))) {
			return getAttackVerb(itemKey);
		}

		try {
			File file = new File("res/Others Text Resources/screenActions-verbs.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
			objectVbArr = object.getJSONArray(actionType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pos = random.nextInt(objectVbArr.length());
		return objectVbArr.getString(pos);
	}

	/**
	 * Gets a preposition
	 * @param cID string that identifies the type of complement
	 * @param string in spanish is the gender of the noun it acompains while in english is the type of article
	 * @param string2, in spanish is the number of the noun it acompains while in english is not used
	 * @return a prepositon in string format
	 */
	public String getPreposition(String cID, String string, String string2);

	/**
	 * Gets an article
	 * @param string in spanish is the gender of the noun it acompains while in english is the type of article
	 * @param string2, in spanish is the number of the noun it acompains while in english is not used
	 * @return an article in string format
	 */
	public String getArticle(String string, String string2);

	/**
	 * Gets an Indefinite article
	 * @param string in spanish is the gender of the noun it acompains while in english is the type of idefinite
	 * @param string2 in spanish is the number of the noun it acompains while in english is not used
	 * @return the indefinite article in string format
	 */
	public String getDetIndefinite(String string, String string2);

}
