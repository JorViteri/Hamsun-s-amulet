package Factories;

/**
 * This factory is responsible for the creation of the "potions" that can
 *  be found in the dungeon.
 * 
 * @author comec
 * 
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Elements.Creature;
import Elements.Effect;
import Elements.Item;
import Rogue.World;
import TextManagement.Realizator;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

public class PotionFactory {

	private World world;
	private Map<String,Color> potionColours;
	private List<String> potionAppearances;
	private String[] arr = {"shiny","disgusting","magic","old"};

	public PotionFactory(World world) {
		this.world = world;
		setUpPotionAppearances();
	}

	/**
	 *This method creates a HashMap which stores colors under it's name. 
	 *Then the names are stored in a list and shuffled in order to get keys 
	 *from this list randomly .
	 *@param Nothing.
	 *@return Nothing.
	 */
	private void setUpPotionAppearances(){
		potionColours = new HashMap<String, Color>();
		potionColours.put("red", AsciiPanel.brightRed);
		potionColours.put("yellow", AsciiPanel.brightYellow);
		potionColours.put("green", AsciiPanel.brightGreen);
		potionColours.put("cyan", AsciiPanel.brightCyan);
		potionColours.put("blue", AsciiPanel.brightBlue);
		potionColours.put("magenta", AsciiPanel.brightMagenta);
		potionColours.put("dark", AsciiPanel.brightBlack);
		potionColours.put("grey", AsciiPanel.white);
		potionColours.put("light", AsciiPanel.brightWhite);

		potionAppearances = new ArrayList<String>(potionColours.keySet());
		Collections.shuffle(potionAppearances);

	}
	
	/**
	 * Creates one potion of health which restores health.
	 * @param depth The level of the dungeon in which it will be placed.
	 * @return Item The potion. 
	 */
	public Item newPotionOfHealth(int depth) {
		String appearance = potionAppearances.get(0);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		Realizator realizator = factory.getRealizator();
		HashMap<String,String> nameData = getter.getNounData("potion");
		HashMap<String,String> descrpNounData = getter.getNounData("health");
		String name = realizator.constructNounAndNoun(nameData.get("baseNoun"), descrpNounData.get("baseNoun") );
		//String name = "health "+nameData.get("baseNoun"); //TODO no tengo traducido lo de CURACION al espanohl
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		String finalAp = getter.getAdjData(appearance, nameData.get("genere")).get("singular");
		Item item = new Item('!', potionColours.get(appearance), "health potion", name, nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"),
				realizator.constructNounAppareance(nameData.get("baseNoun"), finalAp));
		item.setQuaffEffect(new Effect(1) {
			public void start(Creature creature, Creature source) {
				if (creature.hp() == creature.maxHp())
					return;

				creature.modifyHp(15, "Health Potion");
				creature.doAction(getter.getDirectTranslation("PotionFactory", "lookHealthier"));
			}
		});

		world.addAtEmptyLocation(item, depth);
		return item;
	}

	/**
	 * Creates one potion of mana which restores mana.
	 * @param depth The level of the dungeon in which it will be placed.
	 * @return Item The potion. 
	 */
	public Item newPotionOfMana(int depth) {
		String appearance = potionAppearances.get(1);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		Realizator realizator = factory.getRealizator();
		HashMap<String, String> nameData = getter.getNounData("potion");
		HashMap<String, String> descrpNounData = getter.getNounData("magic");
		String name = realizator.constructNounAndNoun(nameData.get("baseNoun"), descrpNounData.get("baseNoun"));
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		String finalAp = getter.getAdjData(appearance, nameData.get("genere")).get("singular");
		Item item = new Item('!', potionColours.get(appearance), "mana potion", name, nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"),
				realizator.constructNounAppareance(nameData.get("baseNoun"), finalAp));
		item.setQuaffEffect(new Effect(1) {
			public void start(Creature creature, Creature source) {
				if (creature.getMana() == creature.getMaxMana())
					return;

				creature.modifyMana(10);
				creature.doAction(getter.getDirectTranslation("PotionFactory", "lookRestored"));
			}
		});

		world.addAtEmptyLocation(item, depth);
		return item;
	}

	/**
	 * Creates one potion of health which reduces health for during some steps.
	 * @param depth The level of the dungeon in which it will be placed.
	 * @return Item The potion. 
	 */
	public Item newPotionOfPoison(int depth) {
		String appearance = potionAppearances.get(2);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		Realizator realizator = factory.getRealizator();
		HashMap<String, String> nameData = getter.getNounData("potion");
		HashMap<String, String> descrpNounData = getter.getNounData("poison");
		String name = realizator.constructNounAndNoun(nameData.get("baseNoun"), descrpNounData.get("baseNoun"));
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		String finalAp = getter.getAdjData(appearance, nameData.get("genere")).get("singular");
		Item item = new Item('!', potionColours.get(appearance), "poison potion", name, nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"),
				realizator.constructNounAppareance(nameData.get("baseNoun"), finalAp));
		item.setQuaffEffect(new Effect(20) {
			public void start(Creature creature, Creature source) {
				creature.doAction(getter.getDirectTranslation("PotionFactory", "lookSick"));
			}

			public void update(Creature creature) {
				super.update(creature);
				creature.modifyHp(-1, "Killed by Poison");
			}
		});
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	/**
	 * Creates one potion of health which  boots the strenght for during some steps.
	 * @param depth The level of the dungeon in which it will be placed.
	 * @return Item The potion. 
	 */
	public Item newPotionOfWarrior(int depth) {
		String appearance = potionAppearances.get(3);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		Realizator realizator = factory.getRealizator();
		HashMap<String, String> nameData = getter.getNounData("potion");
		HashMap<String, String> descrpNounData = getter.getNounData("warrior");
		String name = realizator.constructNounAndNoun(nameData.get("baseNoun"), descrpNounData.get("baseNoun"));
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		String finalAp = getter.getAdjData(appearance, nameData.get("genere")).get("singular");
		Item item = new Item('!', potionColours.get(appearance), "warrior potion", name, nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"),
				realizator.constructNounAppareance(nameData.get("baseNoun"), finalAp));
		item.setQuaffEffect(new Effect(20) {
			public void start(Creature creature, Creature source) {
				creature.modifyAttackValue(5);
				creature.modifyDefenseValue(5);
				creature.doAction(getter.getDirectTranslation("PotionFactory", "lookStrong"));
			}

			public void end(Creature creature) {
				creature.modifyAttackValue(-5);
				creature.modifyDefenseValue(-5);
				creature.doAction(getter.getDirectTranslation("PotionFactory", "lookNotSoStrong"));
			}
		});

		world.addAtEmptyLocation(item, depth);
		return item;
	}

	/**
	 * Creates a randomly selected potion.
	 * @param depth The level of the dungeon in which it will be placed.
	 * @return Item The potion.
	 */

	public Item randomPotion(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newPotionOfHealth(depth);
		case 1:
			return newPotionOfPoison(depth);
		case 2:
			return newPotionOfMana(depth);
		default:
			return newPotionOfWarrior(depth);
		}
	}
}
