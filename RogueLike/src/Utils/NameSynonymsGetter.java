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
		try{
			File file = new File("res/Synsets/"+language+"_Synsets/"+language+"_names_json.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			this.object= new JSONObject(content);
		} catch(Exception e){
			e.printStackTrace();
		}		
		//this.keys = new ArrayList<String>((this.object.keySet()));
	}
	
	//A partir de la key que se le pasa se obtiene un sinonimo
	public String getRandomSynonym(String word) {
		String result;
		ArrayList<String> listWeaponNames = new ArrayList<>();
		JSONArray objectNames;
		Random random = new Random();
		word= word.replace(" ", "_");
		objectNames = object.getJSONArray(word);
		if (objectNames != null) {
			for (int i = 0; i < objectNames.length(); i++) {
				String key = objectNames.getJSONObject(i).keySet().toString();
				listWeaponNames.add(key);
			}
		}
		result = listWeaponNames.get(random.nextInt(listWeaponNames.size()));
		result =  result.replace("_", " ");
		result = result.replace("[", "");
		result = result.replace("]", "");
		return result;
	}
	
	
}
