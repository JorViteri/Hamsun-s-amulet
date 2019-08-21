package TextManagement;

import java.util.HashMap;

import Elements.Creature;
import Elements.Item;

public interface Realizator {

	public String realizatePhrase(HashMap<String, String> verb, HashMap<String, String> Subject, HashMap<String, String> CD, HashMap <String, String> CI, HashMap <String, String> CII,
			HashMap<String, String> Atribute, Restrictions restrictions, String templateType);
	
	public HashMap<String, Integer> getTemplate(String actionType); //TODO debe hacer la solicitud al getter, obtener la estructura y añadir las palabras
	
	public String constructNounAppareance(String noun, String appareance);
	
	public String constructNounAndNoun(String noun, String descrpNoun);

	public String constructNounPosvNoun(String noun, String possNoun);
}