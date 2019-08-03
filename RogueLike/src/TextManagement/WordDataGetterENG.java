package TextManagement;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	
	public String getRandomSeed(String[] array){
		Random r = new Random();
		int randomNumber = r.nextInt(array.length);
		return array[randomNumber];
	}

	@Override
	public ArrayList<String> getNounData(String noun) {
		ArrayList<String> result = new ArrayList<>();
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
		object = object.getJSONObject(aux);
		aux =  aux.replace("_", " ");
		result.add(0, aux);
		result.add(1,object.getString("plural"));
		result.add(2, object.getString("genere"));
		return result;
	}

	@Override
	public ArrayList<String> getAdjData(String adj, String genere) {
		ArrayList<String> results = new ArrayList<>();
		JSONArray objectAdjArr=null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		try{
			File file = new File("res/Synsets/ENG_Synsets/ENG_names_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectAdjArr = object.getJSONArray(adj);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		pos = random.nextInt(objectAdjArr.length());
		results.add(objectAdjArr.getString(pos));
		return results;
	}

	@Override
	public ArrayList<String> getVerbData(String verb) {
		ArrayList<String> result = new ArrayList<>();
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
		result.add(0, aux);
		result.add(1,object.getString("ThirdPerson"));
		result.add(2, object.getString("Past"));
		return result;
	}

	@Override
	public ArrayList<String> getAdvData(String adv) {
		ArrayList<String> results = new ArrayList<>();
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
		results.add(objectAdvArr.getString(pos));
		return results;
	}
	

}
