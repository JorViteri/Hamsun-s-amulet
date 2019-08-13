package TextManagement;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public interface WordDataGetter {

	public HashMap<String, String> getNounData(String noun);

	public HashMap<String, String> getAdjData(String adj, String genere);

	public HashMap<String, String> getVerbData(String verb);

	public HashMap<String, String> getAdvData(String adv);
	
	public String getDirectTranslation(String mainKey, String specificKey);

	default String getRandomSeed(String[] arr){
		Random r = new Random();
		int randomNumber = r.nextInt(arr.length);
		return arr[randomNumber];
	}
	
	default String getAttackVerb(String itemKey){
		JSONArray objectVbArr=null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		try{
			File file = new File("res/Others Text Resources/weapons-verbs.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectVbArr = object.getJSONArray(itemKey);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		pos = random.nextInt(objectVbArr.length());
		return objectVbArr.getString(pos);
	}

	default String getActionVerb(String actionType, String itemKey) {
		JSONArray objectVbArr=null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		if ((actionType.equals("Attack"))||(actionType.equals("Summon"))){
			return getAttackVerb(itemKey);
		}
	
		try{
			File file = new File("res/Others Text Resources/screenActions-verbs.json");
			String content = FileUtils.readFileToString(file,"utf-8");	
			object= new JSONObject(content);
			objectVbArr = object.getJSONArray(actionType);
		} catch(Exception e){
			boolean c = true;
			e.printStackTrace();
		}		
		pos = random.nextInt(objectVbArr.length());
		return objectVbArr.getString(pos);
	}

	public String getPreposition(String CID, String genere, String number);

	public String getArticle(String genere, String number);

	public String getDetUndefined(String string, String string2);


}
