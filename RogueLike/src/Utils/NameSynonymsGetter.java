package Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class NameSynonymsGetter {

	private String language;
	private JSONObject object;
	//private ArrayList<String> keys;	
	
	public NameSynonymsGetter(String language){
		this.language=language;
		//this.keys = new ArrayList<String>((this.object.keySet()));
	}
	
	public String getRandomSeed(String[] array){
		Random r = new Random();
		int randomNumber = r.nextInt(array.length);
		return array[randomNumber];
	}
	
	//A partir de la key que se le pasa se obtiene un sinonimo
	public String getRandomSynonym(String word) {
		String result;
		ArrayList<String> names = new ArrayList<>();
		JSONArray objectNames=null;
		Random random = new Random();
		word= word.replace(" ", "_");
		try{
			File file = new File("res/Synsets/"+language+"_Synsets/"+language+"_names_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			this.object= new JSONObject(content);
			objectNames = object.getJSONArray(word);
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
		
		result = names.get(random.nextInt(names.size()));
		result =  result.replace("_", " ");
		result = result.replace("[", "");
		result = result.replace("]", "");
		return result;
	}
	
	
	public String getRandomAdjSynonym(String adj){ //TODO necesita una/dos entradas mas para controlar genero y numero
		int i;
		String result, key;
		ArrayList<String> adjectives = new ArrayList<>();
		JSONArray objectAdjArr=null;
		JSONObject adjectiveObj = null;
		Random random = new Random();
		adj= adj.replace(" ", "_");
		try{ //TODO creo que va a ser mejor hacer de esta una clase general para varios usos y que segun la funcion tendra un objeto u otro
			File file = new File("res/Synsets/"+language+"_Synsets/"+language+"_adjectives_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			this.object= new JSONObject(content);
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
		adjectiveObj = objectAdjArr.getJSONObject(i);
		key = key.replace("[", "");
		key = key.replace("]", "");
		adjectiveObj = adjectiveObj.getJSONObject(key);
		
		//adjectiveObj=objectAdjArr.getJSONObject(random.nextInt(objectAdjArr.length()));
		
		result = adjectiveObj.getString("sing_f"); //TODO aqui esto peta, aglgo no hago bien ;/
		result =  result.replace("_", " ");
		result = result.replace("[", "");
		result = result.replace("]", "");
		return result;
		
	}
	
	
}
