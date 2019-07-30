package Factories;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import Elements.Creature;
import Elements.Effect;
import Elements.Item;
import Rogue.World;
import Utils.NameSynonymsGetter;
import asciiPanel.AsciiPanel;

public class PotionFactory {

	private World world;
	private Map<String,Color> potionColours;
	private List<String> potionAppearances;
	private NameSynonymsGetter getter;
	private String[] arr = {"shiny","disgusting","magic","old"};

	public PotionFactory(World world) {
		Properties prop = new Properties();
		InputStream input;
		try {
			input =  new FileInputStream("language.properties");
			prop.load(input);
		} catch (Exception e){
			e.printStackTrace();
		}	
		this.world = world;
		this.getter = new NameSynonymsGetter(prop.getProperty("language"));
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
		String seed = getter.getRandomSynonym("potion");
		String name = "health "+seed;
		String adj = getter.getRandomAdjSynonym(getter.getRandomSeed(arr));
		final Item item = new Item('!', potionColours.get(appearance), "health potion", name, adj,appearance+seed);
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
		String seed = getter.getRandomSynonym("potion");
		String name = "mana "+seed;
		String adj = getter.getRandomAdjSynonym(getter.getRandomSeed(arr));
		Item item = new Item('!', potionColours.get(appearance), "mana potion", name, adj, appearance+seed);
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
		String seed = getter.getRandomSynonym("potion");
		String name = "poison "+seed;
		String adj = getter.getRandomAdjSynonym(getter.getRandomSeed(arr));
		Item item = new Item('!', potionColours.get(appearance), "poison potion", name, adj, appearance+seed);
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
		String seed = getter.getRandomSynonym("potion");
		String name = "warrior's "+seed;
		String adj = getter.getRandomAdjSynonym(getter.getRandomSeed(arr));
		Item item = new Item('!', potionColours.get(appearance), "warrior's potion", name, adj, appearance+seed);
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
