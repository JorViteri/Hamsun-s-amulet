package TextManagement;

import Elements.Creature;
import Elements.Item;

public interface Realizator {

	public String realizatePhrase(String actionType, Item item, Creature subject, String CD, Restrictions restrictions);
	
	public String getTemplate(String actionType, Restrictions restrictions); //TODO debe hacer la solicitud al getter, obtener la estructura y añadir las palabras

}