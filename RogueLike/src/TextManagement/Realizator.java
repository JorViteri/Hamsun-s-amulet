package TextManagement;

import Elements.Creature;
import Elements.Item;

public interface Realizator {

	public String realizatePhrase(String actionType, Item item, Creature subject, String CD, Restrictions restrictions);
	
	public String getTemplate(String actionType, Restrictions restrictions); //TODO debe hacer la solicitud al getter, obtener la estructura y añadir las palabras
	public String getVerb(String actionType, Item item); //TODO esto deberia estar en el getter, que es el que debe leer los json

}