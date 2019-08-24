package TextManagement;
/**
 * With the words obtained by the getter and the templates generates some of the phrases that the game will show
 */
import java.util.HashMap;
import java.util.Map.Entry;

public interface Realizator {

	/**
	 * Creates the phrse with the parameters
	 * @param verb HashMap that includes information about the verb to use
	 * @param Subject HashMap that includes information about the subject
	 * @param CD HashMap that includes information about the object of the action
	 * @param CI HashMap that includes information about the indirect object
	 * @param CC  HashMap that includes information about the item or place involved in the action
	 * @param Atribute HashMap that includes information about the atribute
	 * @param restrictions includes multiple morphological restrictions between the components of the phrase
	 * @param templateType indicates which type of template use
	 * @return the constructed phrase
	 */
	public String realizatePhrase(HashMap<String, String> verb, HashMap<String, String> Subject, HashMap<String, String> CD, HashMap <String, String> CI, HashMap <String, String> CC,
			HashMap<String, String> Atribute, Restrictions restrictions, String templateType);
	
	/**
	 * Obtains the template to use
	 * @param actionType string that identifies the template to use
	 * @return HashMap that contais each part of the template with a number that indicates it's position in the template
	 */
	public HashMap<String, Integer> getTemplate(String templateType); 
	
	/**
	 * Constructs a name with it's appareance
	 * @param noun name of the element
	 * @param appareance appareance of the element
	 * @return the new string constructed with the other two
	 */
	public String constructNounAppareance(String noun, String appareance);
	
	/**
	 * Constructs a name with another noun that gives more info
	 * @param noun name of the element
	 * @param descrpNoun descriptive noun
	 * @return the new string constructed with the other two
	 */
	public String constructNounAndNoun(String noun, String descrpNoun);

	/**
	 * Constructs a name with another noun that gives information about possesion
	 * @param noun noun name of the element
	 * @param possNoun noun about the possesion
	 * @return the new string constructed with the other two
	 */
	public String constructNounPosvNoun(String noun, String possNoun);
	
	/**
	 * Obtains a key from a HashMap by it's int value
	 * @param map HashMap in which the search takes place
	 * @param i vallue to search in the HashMap
	 * @return the key if the value is present in the HashMap
	 */
	default  String getKeyByValue(HashMap <String, Integer> map, Integer i){
		for (Entry<String, Integer> entry : map.entrySet()){
			if (i==entry.getValue()){
				return entry.getKey();
			}
		}
		return null;
	}
}