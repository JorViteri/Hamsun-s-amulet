package TextManagement;

/**
 * Realizator for the spanish language
 */
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class RealizatorSPA implements Realizator {

	private WordDataGetter getter;
	private static RealizatorSPA realizator;

	/**
	 * Constructor
	 */
	private RealizatorSPA() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		this.getter = factory.getWordDataGetter();
	}

	/**
	 * Singleton function
	 * @return
	 */
	public static RealizatorSPA getInstance() {
		if (realizator == null) {
			realizator = new RealizatorSPA();
		}
		return realizator;
	}

	@Override
	public String realizatePhrase(HashMap<String, String> verb, HashMap<String, String> Subject,
			HashMap<String, String> CD, HashMap<String, String> CI, HashMap<String, String> CC,
			HashMap<String, String> atribute, Restrictions restrictions, String templateType) {
		String finalPhrase = "";
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, Integer> template = getTemplate(templateType);
		String verbFinal = null;
		if (CC != null) {
			if (CC.get("type").equals("CCI")) {
				verbFinal = getter.getActionVerb(verb.get("actionType"), CC.get("key"));
			} else {
				verbFinal = getter.getActionVerb(verb.get("actionType"), null);
			}
		} else {
			verbFinal = getter.getActionVerb(verb.get("actionType"), null);
		}
		verbFinal = getter.getVerbData(verbFinal).get("ThirdPresentSingular");
		HashMap<String, String> hashMapPhrase = phraseConstructor(template, verbFinal, verb.get("adverb"), Subject, CD,
				CI, CC, atribute, restrictions, verb.get("actionType"));
		int size = template.size();
		for (int i = 0; i < size; i++) {
			String key = getKeyByValue(template, i);
			String component = hashMapPhrase.get(key);
			finalPhrase = finalPhrase + component + " ";
		}
		finalPhrase = finalPhrase.trim();
		finalPhrase = finalPhrase.substring(0, 1).toUpperCase() + finalPhrase.substring(1);
		return finalPhrase + ".";
	}

	@Override
	public HashMap<String, Integer> getTemplate(String actionType) {
		HashMap<String, Integer> result = new HashMap<>();
		JSONObject object = null;
		Random r = new Random();
		JSONArray arr = null;
		int index = 0;
		String template;
		String[] templateSplit;
		try {
			File file = new File("res/Templates/SPA_Templates/SPA_templates.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		arr = object.getJSONArray(actionType);
		int i = r.nextInt(arr.length());
		template = arr.getString(i);
		templateSplit = template.split(" ");
		for (String s : templateSplit) {
			result.put(s, index);
			index++;
		}
		return result;

	}

	/**
	 * Gets the template of a component
	 * @param ID identification of the component
	 * @param actionType type of action, might be used to choose between templates
	 * @return the template fot the component as a HashMap
	 */
	private HashMap<String, String> getObjectTemplate(String ID, String actionType) {
		HashMap<String, String> result = new HashMap<>();
		JSONObject object = null;
		JSONArray arr = null;
		Random random = new Random();
		String objectString = null;
		String[] StringArr;
		if (actionType != null) {
			if (actionType.equals("be_space")) {
				ID = ID + "_" + actionType;
			}
		}

		try {
			File file = new File("res/Templates/SPA_Templates/SPA_PhraseComponents.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		arr = object.getJSONArray(ID);
		int i = random.nextInt(arr.length());
		objectString = arr.getString(i);
		StringArr = objectString.split(" ");
		for (String s : StringArr) {
			result.put(s, s);

		}
		return result;
	}

	/**
	 * Constructs each component of the phrase and returns it as a HaspMap
	 * @param template the components of the phrase to create
	 * @param verb the verb to use in the phrase
	 * @param adverb adverb that might be in the phrase
	 * @param subject info about the subject
	 * @param cD infor about the object
	 * @param cI info about the indirect object
	 * @param cC info about the item or place realted to the action
	 * @param atribute info about the atribute
	 * @param restrictions morphological restrictions
	 * @param actionType type of action that occurs in the phrase
	 * @return HashMap that contais each part of the phrase 
	 */
	private HashMap<String, String> phraseConstructor(HashMap<String, Integer> template, String verb, String adverb,
			HashMap<String, String> Subject, HashMap<String, String> CD, HashMap<String, String> CI,
			HashMap<String, String> CC, HashMap<String, String> atribute, Restrictions restrictions,
			String actionType) {
		HashMap<String, String> result = new HashMap<>();
		HashMap<String, String> aux = new HashMap<>();
		if (template.get("SUJ") != null) {
			aux = getObjectTemplate("SUJ", null);
			result.put("SUJ", constructSUJ(aux, Subject, restrictions));
		}
		if (template.get("VB") != null) {
			result.put("VB", verb);
		}
		if (template.get("CD") != null) {
			aux = getObjectTemplate("CD", actionType);
			result.put("CD", constructCD(aux, CD, restrictions));
		}
		if (template.get("CI") != null) {
			aux = getObjectTemplate("CI", null);
			result.put("CI", constructCI(aux, CI, restrictions));
		}
		if (template.get("CC") != null) {
			aux = getObjectTemplate(CC.get("type"), null);
			result.put("CC", constructCC(aux, CC, restrictions));
		}
		if (template.get("ATR") != null) {
			aux = getObjectTemplate("ATR", null);
			result.put("ATR", constructATR(aux, atribute, restrictions));
		}
		if (template.get("ADV") != null) {

			if (adverb != null) {
				adverb = getter.getAdvData(adverb).get("adverb");
				result.put("ADV", adverb);
			} else {
				result.put("ADV", "");
			}

		}
		return result;
	}

	/**
	 * Constructs the component Atribute of the phrase
	 * @param aux template of the component
	 * @param atribute info about the atribute
	 * @param restrictions morphologial restrictions
	 * @return the atribute for the string
	 */
	private String constructATR(HashMap<String, String> aux, HashMap<String, String> atribute,
			Restrictions restrictions) {
		HashMap<String, String> res = restrictions.getRestrictions();
		String result = "";
		result = result + getter.getDetIndefinite(res.get("AtrGen"), res.get("AtrNum"));
		result = result + " " + atribute.get("name");
		if ((aux.get("ADJ") != null) && (atribute.get("characteristic") != null)) {
			result = result + " " + atribute.get("characteristic");
		}
		return result.trim();
	}

	/**
	 * Constructs the component about the tool or place for the phrase
	 * @param prepPhrase template of the component
	 * @param cC info about the tool or place
	 * @param restrictions mophologial restricions
	 * @return the component constructed
	 */
	private String constructCC(HashMap<String, String> prepPhrase, HashMap<String, String> CC,
			Restrictions restrictions) {
		HashMap<String, String> res = restrictions.getRestrictions();
		String result = "";
		String prep = "";
		if (prepPhrase.get("PR") != null) {
			prep = getter.getPreposition(CC.get("type"), res.get("CCGen"), res.get("CCNum"));
			result = result + prep;
		}
		if ((prepPhrase.get("ART") != null) && (!prep.equals("al"))) {
			result = result + " " + getter.getArticle(res.get("CCGen"), res.get("CCNum"));
		}
		if (prepPhrase.get("NOUN") != null) {
			result = result + " " + CC.get("name");
		}
		if ((prepPhrase.get("ADJ") != null) && (CC.get("characteristic") != null)) {
			result = result + " " + CC.get("characteristic");
		}
		return result.trim();
	}

	/**
	 *  Constructs the component about the indirect object
	 * @param prepPhrase template of the component
	 * @param cI infor about the indirect object
	 * @param restrictions morphological restrictions
	 * @return the component constructed
	 */
	private String constructCI(HashMap<String, String> prepPhrase, HashMap<String, String> CI,
			Restrictions restrictions) {
		HashMap<String, String> res = restrictions.getRestrictions();
		String result = "";
		String prep = "";
		if (prepPhrase.get("PR") != null) {
			prep = getter.getPreposition("CI", res.get("CIGen"), res.get("CINum"));
			result = result + prep;
		}
		if ((prepPhrase.get("ART") != null) && (!prep.equals("al"))) {
			result = result + " " + getter.getArticle(res.get("CIGen"), res.get("CINum"));
		}
		if (prepPhrase.get("NOUN") != null) {
			result = result + " " + CI.get("name");
		}
		if ((prepPhrase.get("ADJ") != null) && (CI.get("characteristic") != null)) {
			result = result + " " + CI.get("characteristic");
		}
		return result.trim();
	}

	/**
	 * Constructs the component about the Subject
	 * @param nominalPhrase template of the object
	 * @param subject info about the subject
	 * @param restrictions mophological restrictions
	 * @return the component constructed
	 */
	private String constructSUJ(HashMap<String, String> nominalPhrase, HashMap<String, String> subject,
			Restrictions restrict) {
		HashMap<String, String> res = restrict.getRestrictions();
		String result = "";
		result = result + getter.getArticle(res.get("SujGen"), res.get("SujNum"));
		result = result + " " + subject.get("name");
		if ((nominalPhrase.get("ADJ") != null) && (subject.get("characteristic") != null)) {
			result = result + " " + subject.get("characteristic");
		}
		return result.trim();
	}

	/**
	 * Constructs the component about the object
	 * @param nominalPhrase template of the component
	 * @param cD info about the object
	 * @param restrictions morphologial restrictions
	 * @return the component constructed
	 */
	private String constructCD(HashMap<String, String> nominalPhrase, HashMap<String, String> cD,
			Restrictions restrict) {
		HashMap<String, String> res = restrict.getRestrictions();
		String result = "";
		if (nominalPhrase.get("DetUn") != null) {
			result = result + getter.getDetIndefinite(res.get("CDGen"), res.get("CDNum"));
		}
		if (nominalPhrase.get("ART") != null) {
			result = result + " " + getter.getArticle(res.get("CDGen"), res.get("CDNum"));
		}
		result = result + " " + cD.get("name");
		if ((nominalPhrase.get("ADJ") != null) && (cD.get("characteristic") != null)) {
			result = result + " " + cD.get("characteristic");
		}
		return result.trim();
	}

	@Override
	public String constructNounAppareance(String noun, String appareance) {
		return noun + " " + appareance;
	}

	@Override
	public String constructNounAndNoun(String noun, String descrpNoun) {
		return noun + " de " + descrpNoun;
	}

	@Override
	public String constructNounPosvNoun(String noun, String possNoun) {
		return noun + " del " + possNoun;
	}
}
