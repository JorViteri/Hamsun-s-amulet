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
import asciiPanel.AsciiPanel;

public class PotionFactory {

	private World world;
	private Map<String,Color> potionColours;
	private List<String> potionAppearances;

	public PotionFactory(World world) {
		this.world = world;
		setUpPotionAppearances();
	}

	private void setUpPotionAppearances() {
		potionColours = new HashMap<String, Color>();
		potionColours.put("red potion", AsciiPanel.brightRed);
		potionColours.put("yellow potion", AsciiPanel.brightYellow);
		potionColours.put("green potion", AsciiPanel.brightGreen);
		potionColours.put("cyan potion", AsciiPanel.brightCyan);
		potionColours.put("blue potion", AsciiPanel.brightBlue);
		potionColours.put("magenta potion", AsciiPanel.brightMagenta);
		potionColours.put("dark potion", AsciiPanel.brightBlack);
		potionColours.put("grey potion", AsciiPanel.white);
		potionColours.put("light potion", AsciiPanel.brightWhite);

		potionAppearances = new ArrayList<String>(potionColours.keySet());
		Collections.shuffle(potionAppearances);

	}
	
	public Item newPotionOfHealth(int depth) {
		String appearance = potionAppearances.get(0);
		final Item item = new Item('!', potionColours.get(appearance), "health potion", appearance);
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
		Item item = new Item('!', potionColours.get(appearance), "mana potion", appearance);
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
		Item item = new Item('!', potionColours.get(appearance), "poison potion", appearance);
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
		Item item = new Item('!', potionColours.get(appearance), "warrior's potion", appearance);
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
