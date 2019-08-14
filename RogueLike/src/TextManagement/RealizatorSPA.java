package TextManagement;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class RealizatorSPA implements Realizator{
	
	private WordDataGetter getter;
	private static RealizatorSPA realizator;
	
	private RealizatorSPA(){
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		this.getter = factory.getWordDataGetter();
	}
	
	public static RealizatorSPA getInstance(){
		if (realizator == null){
			realizator =  new RealizatorSPA();
		}
		return realizator;
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
		verbFinal = getter.getVerbData(verbFinal).get("ThirdPresentSingular"); //aqui tendria que cargarlo y pillar lo que me interese
		HashMap<String, String> hashMapPhrase = phraseConstructor(template, verbFinal, verb.get("adverb"), Subject, CD,
				CI, CCI, restrictions, verb.get("actionType"));
		int size = template.size();
		for (int i = 0; i < size; i++) {
			String key = getKeyByValue(template, i);
			String component = hashMapPhrase.get(key);
			finalPhrase = finalPhrase + component+ " ";
		}
		finalPhrase = finalPhrase.trim();
		finalPhrase = finalPhrase.substring(0, 1).toUpperCase() + finalPhrase.substring(1);
		return finalPhrase+".";
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
			File file = new File("res/Templates/SPA_Templates/SPA_templates.json");
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
	
	
	private HashMap<String,String> getObjectTemplate(String ID, String actionType){
		HashMap<String,String> result = new HashMap<>();
		JSONObject object = null;
		JSONArray arr = null;
		Random random = new Random();
		String objectString = null;
		String[] StringArr;
		if(actionType!=null){
			if (actionType.equals("be_space")){
				ID = ID+"_"+actionType;
			}
		}
			
		try{
			File file = new File("res/Templates/SPA_Templates/SPA_PhraseComponents.json");
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
	
	
	private HashMap<String, String> phraseConstructor(HashMap<String, Integer> template, String verb, String adverb, HashMap<String, String> Subject,
			HashMap<String, String> CD, HashMap<String, String> CI, HashMap<String, String> CCI,
			Restrictions restrictions, String actionType) {
		HashMap<String, String> result = new HashMap<>();
		HashMap<String, String> aux = new HashMap<>();
		if(template.get("SUJ")!=null){
			aux = getObjectTemplate("SUJ", null);
			result.put("SUJ", constructSUJ(aux, Subject, restrictions));
		}
		if(template.get("VB")!=null){
			result.put("VB", " "+verb);
		}
		if (template.get("CD")!=null){//TODO con esto puedo pillar el CD con determinante indefinido
			aux = getObjectTemplate("CD", actionType); 
			result.put("CD", constructCD(aux, CD, restrictions));
		}
		if(template.get("CI")!=null){
			aux = getObjectTemplate("CI", null);
			result.put("CI", constructCI(aux, CI, restrictions));
		}
		if(template.get("CCI")!=null){
			aux = getObjectTemplate("CCI", null);
			result.put("CCI", constructCCI(aux, CCI, restrictions));
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
	
	
	private String constructCCI(HashMap<String, String> prepPhrase, HashMap<String, String> CCI, Restrictions restrictions) {
		HashMap<String, String> res = restrictions.getRestrictions();
		String result = "";
		if (prepPhrase.get("PR") != null){
			result = result + getter.getPreposition("CCI", res.get("CCIGen"), res.get("CCINum"));
		}
		if (prepPhrase.get("ART") != null) {
			result = result + " " +getter.getArticle(res.get("CCIGen"), res.get("CCINum"));
		}
		if (prepPhrase.get("NOUN") != null) {
			result = result + " " + CCI.get("name");
		}		
		if (prepPhrase.get("ADJ") != null) {
			result = result + " " + CCI.get("characteristic");
		}
		return result.trim();
	}

	private String constructCI(HashMap<String, String> prepPhrase, HashMap<String, String> CI, Restrictions restrictions) {
		HashMap<String, String> res = restrictions.getRestrictions();
		String result = "";
		if (prepPhrase.get("PR") != null){
			result = result + getter.getPreposition("CI", res.get("CIGen"), res.get("CINum"));
		} //TODO Obtener las preposiciones!! actualizar el restricitons para tener las nuevas en cuenta!!!
		if (prepPhrase.get("ART") != null) {
			result = result + " "+getter.getArticle(res.get("CIGen"), res.get("CINum"));
		}
		if (prepPhrase.get("NOUN") != null) {
			result = result + " " + CI.get("name");
		}		
		if (prepPhrase.get("ADJ") != null) {
			result = result + " " + CI.get("characteristic");
		}
		return result.trim();
	}

	private String constructSUJ(HashMap<String, String> nominalPhrase, HashMap<String, String> Subject,
			Restrictions restrict) {
		HashMap<String, String> res = restrict.getRestrictions();
		String result = "";
		result = result + getter.getArticle(res.get("SujGen"), res.get("SujNum"));
		if (nominalPhrase.get("ADJ") != null) {
			result = result + " " + Subject.get("characteristic");
		}
		result = result + " " + Subject.get("name");
		return result.trim();
	}
	
	
	private String constructCD(HashMap<String, String> nominalPhrase, HashMap<String, String> cD,
			Restrictions restrict){
		HashMap<String, String> res = restrict.getRestrictions();
		String result = "";
		if (nominalPhrase.get("DetUn")!=null){
			result = result+getter.getDetUndefined(res.get("CDGen"), res.get("CDNum"));
		}
		if (nominalPhrase.get("ART")!=null){
			result = result +" "+ getter.getArticle(res.get("CDGen"), res.get("CDNum"));
		}
		result = result + " " + cD.get("name");
		if (nominalPhrase.get("ADJ") != null) {
			result = result + " " + cD.get("characteristic");
		}
		return result.trim();
	}

	private static String getKeyByValue(HashMap <String, Integer> map, Integer i){
		for (Entry<String, Integer> entry : map.entrySet()){
			if (i==entry.getValue()){
				return entry.getKey();
			}
		}
		return null;
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
