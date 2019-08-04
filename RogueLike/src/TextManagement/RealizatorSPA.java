package TextManagement;

import Elements.Creature;
import Elements.Item;

public class RealizatorSPA implements Realizator{

	@Override
	public String realizatePhrase(String actionType, Item item, Creature subject, String CD,
			Restrictions restrictions) {
		String finalPhrase = null;
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		String template = getTemplate(actionType, restrictions); //aqui obtendriqa la plantilla que necesito 
		String verb = getter.getActionVerb(actionType, item.getKey()); //la llave del synset de los verbos que me interesa
		//TODO debo cambiar los getter a que devuelvan HashMaps con las cosas que me interesan taggeadas, nada de listas
		 
		return finalPhrase;
	}

	@Override
	public String getTemplate(String actionType, Restrictions restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

}
