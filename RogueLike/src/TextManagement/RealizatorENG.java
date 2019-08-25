package TextManagement;
/**
 * Realizator for the english language
 */
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class RealizatorENG implements Realizator {

	private static String VOWELS = "AÀÁÂÃÄÅĀĂĄǺȀȂẠẢẤẦẨẪẬẮẰẲẴẶḀÆǼEȄȆḔḖḘḚḜẸẺẼẾỀỂỄỆĒĔĖĘĚÈÉÊËIȈȊḬḮỈỊĨĪĬĮİÌÍÎÏĲOŒØǾȌȎṌṎṐṒỌỎỐỒỔỖỘỚỜỞỠỢŌÒÓŎŐÔÕÖUŨŪŬŮŰŲÙÚÛÜȔȖṲṴṶṸṺỤỦỨỪỬỮỰYẙỲỴỶỸŶŸÝ";

	private WordDataGetter getter;
	private static RealizatorENG realizator;

	/**
	 * Cosntructor
	 */
	private RealizatorENG() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		this.getter = factory.getWordDataGetter();
	}

	/**
	 * Singleton function
	 * @return
	 */
	public static RealizatorENG getInstance() {
		if (realizator == null) {
			realizator = new RealizatorENG();
		}
		return realizator;
	}

	/**
	 * Checks if the char is a vowel
	 * @param c
	 * @return true if it's a vowel, false in the other case
	 */
	private static boolean isVowel(char c) {
		return VOWELS.indexOf(Character.toUpperCase(c)) >= 0;
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
		verbFinal = getter.getVerbData(verbFinal).get("Singular");

		int size = template.size();
		HashMap<String, String> hashMapPhrase = phraseConstructor(template, verbFinal, verb.get("adverb"), Subject, CD,
				CI, CC, atribute, restrictions, verb.get("actionType"));

		for (int i = 0; i < size; i++) {
			String key = getKeyByValue(template, i);
			String component = hashMapPhrase.get(key);
			finalPhrase = finalPhrase + component + " ";
		}
		finalPhrase = finalPhrase.trim();
		finalPhrase = finalPhrase.substring(0, 1).toUpperCase() + finalPhrase.substring(1);
		return finalPhrase + ".";
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
			HashMap<String, String> subject, HashMap<String, String> cD, HashMap<String, String> cI,
			HashMap<String, String> cC, HashMap<String, String> atribute, Restrictions restrictions,
			String actionType) {
		HashMap<String, String> result = new HashMap<>();
		HashMap<String, String> aux = new HashMap<>();
		if (template.get("PRO") != null) {
			result.put("PRO", "There");
		}
		if (template.get("SUJ") != null) {
			aux = getObjectTemplate("SUJ", null);
			result.put("SUJ", constructSUJ(aux, subject, restrictions));
		}
		if (template.get("VB") != null) {
			result.put("VB", verb);
		}
		if (template.get("CD") != null) {
			aux = getObjectTemplate("CD", actionType);
			result.put("CD", constructCD(aux, cD, restrictions));
		}
		if (template.get("CI") != null) {
			aux = getObjectTemplate("CI", actionType);
			result.put("CI", constructCI(aux, cI, restrictions));
		}
		if (template.get("CC") != null) {
			aux = getObjectTemplate(cC.get("type"), null);
			result.put("CC", constructCC(aux, cC, restrictions));
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
		String number = res.get("AtrNum");
		String type = "indefinite_nvoc";

		if ((aux.get("ADJ") != null) && (atribute.get("characteristic") != null)) {
			if (isVowel(atribute.get("characteristic").charAt(0))) {
				type = "indefinite_voc";
			}
		} else {
			if (isVowel(atribute.get("name").charAt(0))) {
				type = "indefinite_voc";
			}
		}

		if (aux.get("DetUn") != null) {
			if (number.equals("singular")) {
				result = result + getter.getDetIndefinite(type, null);
			} else {
				result = result + "";
			}
		}

		if ((aux.get("ADJ") != null) && (atribute.get("characteristic") != null)) {
			result = result + " " + atribute.get("characteristic");
		}
		result = result + " " + atribute.get("name");
		return result.trim();
	}

	/**
	 * Constructs the component about the tool or place for the phrase
	 * @param prepPhrase template of the component
	 * @param cC info about the tool or place
	 * @param restrictions mophologial restricions
	 * @return the component constructed
	 */
	private String constructCC(HashMap<String, String> prepPhrase, HashMap<String, String> cC,
			Restrictions restrictions) {
		String result = " ";
		if (prepPhrase.get("PR") != null) {
			result = result + getter.getPreposition(cC.get("type"), null, null);
		}
		if (prepPhrase.get("ART") != null) {
			result = result + " " + getter.getArticle("definite", null);
		}
		if ((prepPhrase.get("ADJ") != null) && (cC.get("characteristic") != null)) {
			result = result + " " + cC.get("characteristic");
		}
		if (prepPhrase.get("NOUN") != null) {
			result = result + " " + cC.get("name");
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
	private String constructCI(HashMap<String, String> prepPhrase, HashMap<String, String> cI,
			Restrictions restrictions) {
		String result = "";
		if (prepPhrase.get("PR") != null) {
			result = result + getter.getPreposition("CI", null, null);
		}
		if (prepPhrase.get("ART") != null) {
			result = result + " " + getter.getArticle("definite", null);
		}
		if ((prepPhrase.get("ADJ") != null) && (cI.get("characteristic") != null)) {
			result = result + " " + cI.get("characteristic");
		}
		if (prepPhrase.get("NOUN") != null) {
			result = result + " " + cI.get("name");
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
			Restrictions restrictions) {
		HashMap<String, String> res = restrictions.getRestrictions();
		String result = "";
		String type = "indefinite_nvoc";
		String number = res.get("CDNum");

		if ((nominalPhrase.get("ADJ") != null) && (cD.get("characteristic") != null)) {
			if (isVowel(cD.get("characteristic").charAt(0))) {
				type = "indefinite_voc";

			}
		} else {
			if (isVowel(cD.get("name").charAt(0))) {
				type = "indefinite_voc";
			}
		}

		if (nominalPhrase.get("DetUn") != null) {
			if (number.equals("singular")) {
				result = result + getter.getDetIndefinite(type, null);
			} else {
				result = result + "";
			}
		}
		if (nominalPhrase.get("ART") != null) {
			result = result + " " + getter.getArticle("definite", null);
		}
		if ((nominalPhrase.get("ADJ") != null) && (cD.get("characteristic") != null)) {
			result = result + " " + cD.get("characteristic");
		}
		/*if (nominalPhrase.get("ADJ") != null) {
			result = result + " " + cD.get("characteristic");
		}*/
		result = result + " " + cD.get("name");

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
			Restrictions restrictions) {
		String result = "";
		result = result + getter.getArticle("definite", null);
		if ((nominalPhrase.get("ADJ") != null) && (subject.get("characteristic") != null)) {
			result = result + " " + subject.get("characteristic");
		}
		result = result + " " + subject.get("name");
		return result.trim();
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
		String aux = "";
		if (actionType != null) {
			aux = ID+"_"+actionType.toLowerCase();
			if (aux.equals("CD_be_space") || (aux.equals("CI_throwattack"))) {
				ID = ID + "_" + actionType;
			}
		}
		try {
			File file = new File("res/Templates/ENG_Templates/ENG_PhraseComponents.json");
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
			File file = new File("res/Templates/ENG_Templates/ENG_templates.json");
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

	@Override
	public String constructNounAppareance(String noun, String appareance) {
		return appareance + " " + noun;
	}

	@Override
	public String constructNounAndNoun(String noun, String descrpNoun) {
		return descrpNoun + " " + noun;
	}

	@Override
	public String constructNounPosvNoun(String noun, String possNoun) {
		return possNoun + "'s " + noun;
	}
	
	

}
