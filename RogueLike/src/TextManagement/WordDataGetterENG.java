package TextManagement;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WordDataGetterENG implements WordDataGetter {
	
	public WordDataGetterENG(){
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
		JSONArray objectNames=null;
		JSONObject object = null;
		Random random = new Random();
		noun= noun.replace(" ", "_");
		try{
			File file = new File("res/Synsets/ENG_Synsets/ENG_names_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectNames = object.getJSONArray(noun);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		if (objectNames != null) {
			for (int i = 0; i < objectNames.length(); i++) {
				String key = objectNames.getJSONObject(i).keySet().toString();
				names.add(key);
			}
		}
		
		j=random.nextInt(names.size());
		aux = names.get(j); 
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		object = objectNames.getJSONObject(j);
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
		String key, adj_sing, adj_plu, aux;
		ArrayList<String> adjectives = new ArrayList<>();
		JSONArray objectAdjArr=null;
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
			File file = new File("res/Synsets/ENG_Synsets/ENG_adjectives_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectAdjArr = object.getJSONArray(adj);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}	
		
		if (objectAdjArr != null) { //TODO en principio coge la llave lo cual MAL, deberia obtener la forma para la situaciion adecuada
			for (i = 0; i < objectAdjArr.length(); i++) {
				key = objectAdjArr.getJSONObject(i).keySet().toString();
				adjectives.add(key);
			}
		}
		i = random.nextInt(adjectives.size());
		key= adjectives.get(i);
		adjectiveObj = objectAdjArr.getJSONObject(i); //wut
		key = key.replace("[", "");
		key = key.replace("]", "");
		adjectiveObj = adjectiveObj.getJSONObject(key);
		
		
		aux =  adjectiveObj.getString(adj_sing); 
		aux =  aux.replace("_", " ");
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		results.add(0,aux);
		
		aux =  adjectiveObj.getString(adj_plu);  
		aux =  aux.replace("_", " ");
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		results.add(1,aux);
		
		return results;
		
	}

	@Override
	public ArrayList<String> getVerbData(String verb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAdvData(String adv) {
		// TODO Auto-generated method stub
		return null;
	}

}
