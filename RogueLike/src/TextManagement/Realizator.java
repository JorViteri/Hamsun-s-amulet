package TextManagement;

import java.util.HashMap;

import Elements.Creature;
import Elements.Item;

public interface Realizator {

	public String realizatePhrase(String actionType,HashMap<String, String> Subject, HashMap<String, String> CD, HashMap <String, String> CI, HashMap <String, String> CII,
			Restrictions restrictions);
	
	public HashMap<String, Integer> getTemplate(String actionType); //TODO debe hacer la solicitud al getter, obtener la estructura y añadir las palabras

}