package TextManagement;
/**
 * Defines the class that holds the morphological restrictions
 */
import java.util.HashMap;

public class Restrictions {

	private HashMap<String, String> restrictions = new HashMap<>();

	/**
	 * Constructor
	 * @param VbNum Number of the verb
	 * @param VbPerson Person of the verb
	 * @param VbForm Form of the verb
	 * @param VbTime Time of the verb
	 * @param SujGen Gender of the subject
	 * @param SujNum Number of the subject
	 * @param CDGen Gender of the object
	 * @param CDNum Number of the object
	 * @param CIGen Gender of the indirect object
	 * @param CINum Number of the indirect object
	 * @param CCGen Gender of the tool or place
	 * @param CCNum Number of the tool or place
	 * @param AtrGender Gender of the atribue
	 * @param AtrNumber Number of the atribue
	 */
	public Restrictions(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen, String SujNum,
			String CDGen, String CDNum, String CIGen, String CINum, String CCGen, String CCNum, String AtrGender, String AtrNumber) {
		restrictions.put("VbNum", VbNum);
		restrictions.put("VbPerson", VbPerson);
		restrictions.put("VbForm", VbForm);
		restrictions.put("VbTime", VbTime);
		restrictions.put("SujGen", SujGen);
		restrictions.put("SujNum", SujNum);
		restrictions.put("CDGen", CDGen);
		restrictions.put("CDNum", CDNum);
		restrictions.put("CIGen", CIGen);
		restrictions.put("CINum", CINum);
		restrictions.put("CCGen", CCGen);
		restrictions.put("CCNum", CCNum);
		restrictions.put("AtrGen", AtrGender);
		restrictions.put("AtrNum", AtrNumber);
	}

	/**
	 * Obtains the restrictions as a HashMap
	 * @return
	 */
	public HashMap<String, String> getRestrictions() {
		return restrictions;
	}
	


}
