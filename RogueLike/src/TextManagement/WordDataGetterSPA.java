package TextManagement;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WordDataGetterSPA implements WordDataGetter {
	
	public WordDataGetterSPA(){
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
