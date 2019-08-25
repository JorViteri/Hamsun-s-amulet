package TextManagement;
/**
 * Implements the WordDataGetterInterface for the spanish language
 */
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

	/**
	 * Constructor
	 */
	private WordDataGetterSPA() {
	}

	/**
	 * Singleton function
	 * @return the instance of this class
	 */
	public static WordDataGetterSPA getSingletonInstance() {
		if (getter == null) {
			getter = new WordDataGetterSPA();
		}
		return getter;
	}

	@Override
	public HashMap<String, String> getNounData(String noun) {
		HashMap<String, String> result = new HashMap<>();
		ArrayList<String> names = new ArrayList<>();
		String aux;
		int j;
		JSONObject objectNames = null;
		JSONObject object = null;
		Random random = new Random();
		noun = noun.replace(" ", "_");
		try {
			File file = new File("res/Synsets/SPA_Synsets/SPA_names.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
			objectNames = object.getJSONObject(noun);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Collection<String> keys = objectNames.keySet();
		names = new ArrayList<String>(keys);

		j = random.nextInt(names.size());
		aux = names.get(j);
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		object = objectNames.getJSONObject(aux);
		aux = aux.replace("_", " ");
		result.put("baseNoun", aux);
		result.put("plural", object.getString("plural"));
		result.put("genere", object.getString("genere"));
		return result;
	}

	@Override
	public HashMap<String, String> getAdjData(String adj, String genere) {
		HashMap<String, String> result = new HashMap<>();
		String key, adj_sing, adj_plu, aux;
		ArrayList<String> adjectives = new ArrayList<>();
		JSONObject objectAdjectivesSysnset = null;
		JSONObject adjectiveObj = null;
		JSONObject object = null;
		Random random = new Random();
		int i;

		adj = adj.replace(" ", "_");
		switch (genere) {
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
		try { //TODO creo que va a ser mejor hacer de esta una clase general para varios usos y que segun la funcion tendra un objeto u otro
			File file = new File("res/Synsets/SPA_Synsets/SPA_adjectives.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
			objectAdjectivesSysnset = object.getJSONObject(adj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collection<String> keys = objectAdjectivesSysnset.keySet();
		adjectives = new ArrayList<String>(keys);

		i = random.nextInt(adjectives.size());
		key = adjectives.get(i);
		adjectiveObj = objectAdjectivesSysnset.getJSONObject(key);

		aux = adjectiveObj.getString(adj_sing);
		aux = aux.replace("_", " ");
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		result.put("singular", aux);

		aux = adjectiveObj.getString(adj_plu);
		aux = aux.replace("_", " ");
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
		JSONObject objectNames = null;
		JSONObject object = null;
		Random random = new Random();
		verb = verb.replace(" ", "_");
		try {
			File file = new File("res/Synsets/SPA_Synsets/SPA_verbs.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
			objectNames = object.getJSONObject(verb);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Collection<String> keys = objectNames.keySet();
		names = new ArrayList<String>(keys);

		j = random.nextInt(names.size());
		aux = names.get(j);
		aux = aux.replace("[", "");
		aux = aux.replace("]", "");
		object = objectNames.getJSONObject(aux);
		aux = aux.replace("_", " ");
		result.put("infinitive", aux);
		result.put("ThirdPresentSingular", object.getString("3PS"));
		result.put("ThirdPresentPlural", object.getString("3PP"));
		result.put("ThirdPastSingular", object.getString("3SS"));
		result.put("ThirdPastPlural", object.getString("3SP"));
		return result;
	}

	@Override
	public HashMap<String, String> getAdvData(String adv) {
		HashMap<String, String> results = new HashMap<>();
		JSONArray objectAdvArr = null;
		JSONObject object = null;
		Random random = new Random();
		int pos;
		try {
			File file = new File("res/Synsets/SPA_Synsets/SPA_adverbs.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
			objectAdvArr = object.getJSONArray(adv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pos = random.nextInt(objectAdvArr.length());
		results.put("adverb", objectAdvArr.getString(pos));
		return results;
	}

	@Override
	public String getArticle(String genere, String number) {
		JSONObject object = null;
		try {
			File file = new File("res/Others Text Resources/SPA/SPA_articles.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if ((number == null)||(genere==null)){
			boolean c =true;
			
		}*/
		object = object.getJSONObject(number);

		return object.getString(genere);
	}

	@Override
	public String getPreposition(String cID, String genere, String number) {
		JSONObject object = null;
		JSONArray arr = null;
		int index;
		Random r = new Random();
		try {
			File file = new File("res/Others Text Resources/SPA/SPA_prepositions.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cID.equals("CCI")) {
			arr = object.getJSONArray(cID);
			index = r.nextInt(arr.length());
			return arr.getString(index);
		} else {
			object = object.getJSONObject("CI"); // es el mismo que el CCL,
													// menos problemas
			return object.getString(genere + "_" + number);
		}
	}

	@Override
	public String getDirectTranslation(String mainKey, String specificKey) {
		JSONObject object = null;
		try {
			File file = new File("res/Others Text Resources/SPA/SPA_directTranslation.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
		} catch (Exception e) {
			e.printStackTrace();
		}

		object = object.getJSONObject(mainKey);
		return object.getString(specificKey);
	}

	@Override
	public String getDetIndefinite(String genere, String number) {
		JSONObject object = null;
		try {
			File file = new File("res/Others Text Resources/SPA/SPA_indefiniteDeter.json");
			String content = FileUtils.readFileToString(file, "utf-8");
			object = new JSONObject(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (genere == null) {
			genere = "masculine"; // TODO esto es temporal ya que los cuperos
									// deberian tener genero
		}
		object = object.getJSONObject(genere);
		return object.getString(number);
	}
}
