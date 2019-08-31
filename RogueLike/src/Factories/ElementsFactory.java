package Factories;

/**
 * Defines the Elements Factory which creates some special items by their characteristics and places the in the dungeon
 * 
 * @author comec
 */
import java.util.HashMap;
import Elements.Item;
import Rogue.World;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

public class ElementsFactory {

	private World world;

	/**
	 * Constructor
	 * @param world world of the dungeon so it can add the items to it
	 */
	public ElementsFactory(World world) {
		this.world = world;
	}

	public Item newRock(int depth) {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("rock");
		HashMap<String, String> adjData = getter.getAdjData("average", nameData.get("genere"));
		Item rock = new Item(',', AsciiPanel.yellow, "rock", nameData.get("baseNoun"), nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("amulet");
		Item item = new Item('*', AsciiPanel.brightWhite, "Hamsun's amulet",
				String.format(getter.getDirectTranslation("ElementsFactory", "hamsunAmulet"), nameData.get("baseNoun")),
				String.format(getter.getDirectTranslation("ElementsFactory", "hamsunAmulet"), nameData.get("plural")),
				nameData.get("genere"), null, null, null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

}
