package TextManagement;

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

	private RealizatorENG() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		this.getter = factory.getWordDataGetter();
	}

	public static RealizatorENG getInstance() {
		if (realizator == null) {
			realizator = new RealizatorENG();
		}
		return realizator;
	}

	private static boolean isVowel(char c) {
	    return VOWELS.indexOf(Character.toUpperCase(c)) >= 0;
	}

	@Override
	public String realizatePhrase(HashMap<String, String> verb, HashMap<String, String> Subject, HashMap<String, String> CD,
			HashMap<String, String> CI, HashMap<String, String> CCI, Restrictions restrictions, String templateType) {
		String finalPhrase = "";
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, Integer> template = getTemplate(templateType); //aqui obtendriqa la plantilla que necesito 
		String verbFinal = null;
		if (CCI!=null){  //TODO aqui solo pillo el ID del verbo
			verbFinal = getter.getActionVerb(verb.get("actionType"), CCI.get("key"));
		} else{
			verbFinal = getter.getActionVerb(verb.get("actionType"), null);
		}
		String vForm = verb.get("Form");
		verbFinal = getter.getVerbData(verbFinal).get(vForm); //aqui tendria que cargarlo y pillar lo que me interese
		if (verb.equals("summons")){
			boolean c = true;
		}
		HashMap<String, String> hashMapPhrase = phraseConstructor(template, verbFinal, verb.get("adverb"), Subject, CD,
				CI, CCI, restrictions, verb.get("actionType"));
		int size = template.size();
		for (int i = 0; i < size; i++) {
			String key = getKeyByValue(template, i);
			String component = hashMapPhrase.get(key);
			finalPhrase = finalPhrase + component +" ";
		}
		finalPhrase = finalPhrase.trim();
		finalPhrase = finalPhrase.substring(0, 1).toUpperCase() + finalPhrase.substring(1);
		return finalPhrase;
	}

	private String getKeyByValue(HashMap<String, Integer> map, int i) { //quizas seria mejor definir esta funcion en la interaz con un default
		for (Entry<String, Integer> entry : map.entrySet()){
			if (i == entry.getValue()) {
				return entry.getKey();
			}
		}
		return null;
	}

	private HashMap<String, String> phraseConstructor(HashMap<String, Integer> template, String verb, String adverb,
			HashMap<String, String> subject, HashMap<String, String> cD, HashMap<String, String> cI,
			HashMap<String, String> cCI, Restrictions restrictions, String actionType) {
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
			result.put("VB",  verb);
		}
		if (template.get("CD") != null) {
			aux = getObjectTemplate("CD", actionType);
			result.put("CD", constructCD(aux, cD, restrictions));
		}
		if (template.get("CI") != null) {
			aux = getObjectTemplate("CI", actionType);
			result.put("CI", constructCI(aux, cI, restrictions));
		}
		if (template.get("CCI") != null) {
			aux = getObjectTemplate("CCI", null);
			result.put("CCI", constructCCI(aux, cCI, restrictions));
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

	private String constructCCI(HashMap<String, String> prepPhrase, HashMap<String, String> cCI, Restrictions restrictions) {
		String result = " ";
		if (prepPhrase.get("PR") != null){
			result = result + getter.getPreposition("CCI", null, null);
		}
		if (prepPhrase.get("ART") != null) {
			result = result + " " +getter.getArticle("defined", null);
		}
		if (prepPhrase.get("ADJ") != null) {
			result = result + " " + cCI.get("characteristic");
		}
		if (prepPhrase.get("NOUN") != null) {
			result = result + " " + cCI.get("name");
		}		
		
		return result.trim();
	}

	private String constructCI(HashMap<String, String> prepPhrase, HashMap<String, String> cI,
			Restrictions restrictions) {
		String result = "";
		if (prepPhrase.get("PR") != null) {
			result = result + getter.getPreposition("CI", null, null);
		}
		if (prepPhrase.get("ART") != null) {
			result = result + " " + getter.getArticle("defined", null);
		}
		if (prepPhrase.get("ADJ") != null) {
			result = result + " " + cI.get("characteristic");
		}
		if (prepPhrase.get("NOUN") != null) {
			result = result + " " + cI.get("name");
		}
		
		return result.trim();
	}

	private String constructCD(HashMap<String, String> nominalPhrase, HashMap<String, String> cD,
			Restrictions restrictions) {
		HashMap<String, String> res = restrictions.getRestrictions();
		String result = "";
		String type = "undefined_nvoc";
		String number = res.get("CDNum");
		
		if (nominalPhrase.get("ADJ")!= null){
			if (isVowel(cD.get("characteristic").charAt(0))){
				type = "undefined_voc";
			}
		} else {
			if (isVowel(cD.get("name").charAt(0))){
				type = "undefined_voc";
			}
		}

		if (nominalPhrase.get("DetUn") != null) {
			if (number.equals("singular")) {
				result = result + getter.getDetUndefined(type, null);
			} else {
				result = result + "";
			}
		}

		if (nominalPhrase.get("ART") != null) {
			result = result +getter.getArticle("defined", null)+ " ";
		}
		if (nominalPhrase.get("ADJ") != null) {
			result = result + " " + cD.get("characteristic");
		}
		result = result + " " + cD.get("name");

		return result.trim();
	}

	private String constructSUJ(HashMap<String, String> nominalPhrase, HashMap<String, String> subject,
			Restrictions restrictions) {
		String result = "";
		result = result + getter.getArticle("defined", null);
		if (nominalPhrase.get("ADJ") != null) {
			result = result + " " + subject.get("characteristic");
		}
		result = result + " " + subject.get("name");
		return result.trim();
	}

	private HashMap<String, String> getObjectTemplate(String ID, String actionType) {
		HashMap<String,String> result = new HashMap<>();
		JSONObject object = null;
		JSONArray arr = null;
		Random random = new Random();
		String objectString = null;
		String[] StringArr;
		if(actionType!=null){
			actionType = actionType.toLowerCase();
			if (actionType.equals("be_space")||(actionType.equals("summon"))){
				ID = ID+"_"+actionType;
			}
		}
		try{
			File file = new File("res/Templates/ENG_Templates/ENG_PhraseComponents.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		arr = object.getJSONArray(ID);
		int i  = random.nextInt(arr.length());
		objectString = arr.getString(i);
		StringArr = objectString.split(" ");
		for (String s : StringArr){
			result.put(s, s);
			
		}
		return result;
	}

	@Override
	public HashMap<String, Integer> getTemplate(String actionType) {
		HashMap<String,Integer> result = new HashMap<>();
		JSONObject object = null;
		Random r = new Random();
		JSONArray arr = null;
		int index = 0;
		String template;
		String[] templateSplit;
		try {
			File file = new File("res/Templates/ENG_Templates/ENG_templates.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}
		arr = object.getJSONArray(actionType);
		int i = r.nextInt(arr.length());
		template = arr.getString(i);
		templateSplit = template.split(" ");
		for (String s : templateSplit){
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
		return possNoun+"'s "+noun;
	}
	
	

}
