package Factories;

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

	private void setUpPotionAppearances() {
		potionColours = new HashMap<String, Color>();
		potionColours.put("red ", AsciiPanel.brightRed);
		potionColours.put("yellow ", AsciiPanel.brightYellow);
		potionColours.put("green ", AsciiPanel.brightGreen);
		potionColours.put("cyan ", AsciiPanel.brightCyan);
		potionColours.put("blue ", AsciiPanel.brightBlue);
		potionColours.put("magenta ", AsciiPanel.brightMagenta);
		potionColours.put("dark ", AsciiPanel.brightBlack);
		potionColours.put("grey ", AsciiPanel.white);
		potionColours.put("light ", AsciiPanel.brightWhite);

		potionAppearances = new ArrayList<String>(potionColours.keySet());
		Collections.shuffle(potionAppearances);

	}
	
	public Item newPotionOfHealth(int depth) {
		String appearance = potionAppearances.get(0);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String,String> nameData = getter.getNounData("potion");
		String name = "health "+nameData.get("baseNoun");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Item item = new Item('!', potionColours.get(appearance), "health potion", name, nameData.get("plural"), nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), appearance+nameData.get("baseNoun"));
		item.setQuaffEffect(new Effect(1) {
			public void start(Creature creature) {
				if (creature.hp() == creature.maxHp())
					return;

				creature.modifyHp(15, "Health Potion");
				creature.doAction("look healthier");
			}
		});

		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newPotionOfMana(int depth) {
		String appearance = potionAppearances.get(1);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("potion");
		String name = "mana "+nameData.get("baseNoun");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Item item = new Item('!', potionColours.get(appearance), "health potion", name, nameData.get("plural"), nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), appearance+nameData.get("baseNoun"));
		item.setQuaffEffect(new Effect(1) {
			public void start(Creature creature) {
				if (creature.getMana() == creature.getMaxMana())
					return;

				creature.modifyMana(10);
				creature.doAction("look restored");
			}
		});

		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newPotionOfPoison(int depth) {
		String appearance = potionAppearances.get(2);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String,String> nameData = getter.getNounData("potion");
		String name = "poison "+nameData.get("baseNoun");
		HashMap<String,String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Item item = new Item('!', potionColours.get(appearance), "health potion", name, nameData.get("plural"), nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), appearance+nameData.get("baseNoun"));
		item.setQuaffEffect(new Effect(20) {
			public void start(Creature creature) {
				creature.doAction("look sick");
			}

			public void update(Creature creature) {
				super.update(creature);
				creature.modifyHp(-1, "Killed by Poison");
			}
		});
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newPotionOfWarrior(int depth) {
		String appearance = potionAppearances.get(3);
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("potion");
		String name = "warrior's "+nameData.get("baseNoun");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Item item = new Item('!', potionColours.get(appearance), "health potion", name, nameData.get("plural"), nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), appearance+nameData.get("baseNoun"));
		item.setQuaffEffect(new Effect(20) {
			public void start(Creature creature) {
				creature.modifyAttackValue(5);
				creature.modifyDefenseValue(5);
				creature.doAction("look stronger");
			}
			public void end(Creature creature) {
				creature.modifyAttackValue(-5);
				creature.modifyDefenseValue(-5);
				creature.doAction("look less strong");
			}
		});

		world.addAtEmptyLocation(item, depth);
		return item;
	}

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
