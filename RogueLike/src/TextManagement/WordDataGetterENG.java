package TextManagement;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WordDataGetterENG implements WordDataGetter {
	
	private static WordDataGetterENG getter;
	
	private WordDataGetterENG(){
	}
	
	public static WordDataGetterENG getSingletonInstance(){
		if(getter == null){
			getter = new  WordDataGetterENG();
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
			File file = new File("res/Synsets/ENG_Synsets/ENG_names_json.json");
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
		result.put("singular",object.getString("singular"));
		result.put("plural", object.getString("plural"));
		return result;
	}

	@Override
	public HashMap<String, String> getAdjData(String adj, String genere) {
		HashMap<String, String> results = new HashMap<>();
		JSONArray objectAdjArr=null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		try{
			File file = new File("res/Synsets/ENG_Synsets/ENG_adjectives_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectAdjArr = object.getJSONArray(adj);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		pos = random.nextInt(objectAdjArr.length());
		results.put("singular", objectAdjArr.getString(pos));
		results.put("plural",objectAdjArr.getString(pos));
		return results;
	}

	@Override
	public HashMap<String, String> getVerbData(String verb) {
		HashMap<String, String> result = new HashMap<>();
		ArrayList<String> verbs = new ArrayList<>();
		String aux;
		int j;
		JSONObject objectVerbs=null;
		JSONObject object = null;
		Random random = new Random();
		verb= verb.replace(" ", "_");
		try{
			File file = new File("res/Synsets/ENG_Synsets/ENG_verbs_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectVerbs = object.getJSONObject(verb);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		
		Collection<String> keys = objectVerbs.keySet();
		verbs = new ArrayList<String>(keys);
		
		j=random.nextInt(verbs.size());
		aux = verbs.get(j); 
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		object = objectVerbs.getJSONObject(aux);
		object = object.getJSONObject(aux);
		aux =  aux.replace("_", " ");
		result.put("Present", aux);
		result.put("ThirdPerson",object.getString("ThirdPerson"));
		result.put("Past", object.getString("Past"));
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
			File file = new File("res/Synsets/ENG_Synsets/ENG_adverbs_json.json");
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
