package TextManagement;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WordDataGetterSPA implements WordDataGetter {
	
	private static WordDataGetterSPA getter;
	
	private WordDataGetterSPA(){
	}
	
	public static WordDataGetterSPA getSingletonInstance(){
		if(getter == null){
			getter = new  WordDataGetterSPA();
		}
		return getter;
	}

	@Override
	public HashMap<String, String> getNounData(String noun) {
		HashMap<String, String> result = new HashMap<>();
		ArrayList<String> names = new ArrayList<>();
		String aux;
		int j;
		JSONObject objectNames=null;
		JSONObject object = null;
		Random random = new Random();
		noun= noun.replace(" ", "_");
		try{
			File file = new File("res/Synsets/SPA_Synsets/SPA_names_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectNames = object.getJSONObject(noun);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		
		Collection<String> keys = objectNames.keySet();
		names = new ArrayList<String>(keys);
		
		j=random.nextInt(names.size());
		aux = names.get(j);
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		object = objectNames.getJSONObject(aux);
		aux =  aux.replace("_", " ");
		result.put("baseNoun", aux);
		result.put("plural",object.getString("plural"));
		result.put("genere", object.getString("genere"));
		return result;
	}

	@Override
	public HashMap<String, String> getAdjData(String adj, String genere) {
		HashMap<String, String> result = new HashMap<>();
		String key, adj_sing, adj_plu, aux;
		ArrayList<String> adjectives = new ArrayList<>();
		JSONObject objectAdjectivesSysnset=null;
		JSONObject adjectiveObj = null;
		JSONObject object = null;
		Random random = new Random();
		int i;
		
		adj= adj.replace(" ", "_");
		switch (genere){
		case "femenine":
			adj_sing = "sing_f";
			adj_plu = "plu_f";
			break;
		case "masculine":
			adj_sing = "sing_m";
			adj_plu = "plu_m";
			break;
		default:
			adj_sing = "sing_f";
			adj_plu = "plu_f";
		}
		try{ //TODO creo que va a ser mejor hacer de esta una clase general para varios usos y que segun la funcion tendra un objeto u otro
			File file = new File("res/Synsets/SPA_Synsets/SPA_adjectives_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectAdjectivesSysnset= object.getJSONObject(adj);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}	
		Collection<String> keys = objectAdjectivesSysnset.keySet();
		adjectives = new ArrayList<String>(keys);
		
		i = random.nextInt(adjectives.size());
		key= adjectives.get(i);
		adjectiveObj = objectAdjectivesSysnset.getJSONObject(key);
		
		aux =  adjectiveObj.getString(adj_sing); 
		aux =  aux.replace("_", " ");
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		result.put("singular", aux);
		
		aux =  adjectiveObj.getString(adj_plu);  
		aux =  aux.replace("_", " ");
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		result.put("plural", aux);
		
		return result;
	}

	@Override
	public HashMap<String, String> getVerbData(String verb) {
		HashMap<String, String> result = new HashMap<>();
		ArrayList<String> names = new ArrayList<>();
		String aux;
		int j;
		JSONObject objectNames=null;
		JSONObject object = null;
		Random random = new Random();
		verb= verb.replace(" ", "_");
		try{
			File file = new File("res/Synsets/SPA_Synsets/SPA_verbs_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectNames = object.getJSONObject(verb);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		
		Collection<String> keys = objectNames.keySet();
		names = new ArrayList<String>(keys);
		
		j=random.nextInt(names.size());
		aux = names.get(j);
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		object = objectNames.getJSONObject(aux);
		aux =  aux.replace("_", " ");
		result.put("infinitive", aux);
		result.put("ThirdPresentSingular",object.getString("3PS"));
		result.put("ThirdPresentPlural", object.getString("3PP"));
		result.put("ThirdPastSingular",object.getString("3SS"));
		result.put("ThirdPastPlural", object.getString("3SP"));
		return result;
	}

	@Override
	public HashMap<String, String> getAdvData(String adv) {
		HashMap<String, String> results = new HashMap<>();
		JSONArray objectAdvArr=null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		try{
			File file = new File("res/Synsets/SPA_Synsets/SPA_adverbs_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectAdvArr = object.getJSONArray(adv);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		pos = random.nextInt(objectAdvArr.length());
		results.put("adverb",objectAdvArr.getString(pos));
		return results;
	}
	
	
}
