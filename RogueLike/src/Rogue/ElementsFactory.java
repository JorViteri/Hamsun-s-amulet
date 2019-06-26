package Rogue;

import java.util.ArrayList;
import java.util.Collections;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import asciiPanel.AsciiPanel;

public class ElementsFactory {

	private World world;
	private Map<String,Color> potionColours;
	private List<String> potionAppearances;

	public ElementsFactory(World world) {
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

	public Creature newPlayer(ArrayList<String> messages, FieldOfView fov) {
		Creature player = new Creature(world, '@', "Player", AsciiPanel.brightWhite, 100, 20, 5, 20);
		world.addAtExitStairs(player);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Creature newFungus(int depth) {
		Creature fungus = new Creature(world, 'f', "Fungi", AsciiPanel.green, 10, 0, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat(int depth) {
		Creature bat = new Creature(world, 'b', "Bat", AsciiPanel.yellow, 15, 5, 0, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Creature newZombie(int depth, Creature player) {
		Creature zombie = new Creature(world, 'z', "zombie", AsciiPanel.white, 50, 10, 10, 0);
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock", null);
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) {
		Item item = new Item('*', AsciiPanel.brightWhite, "Hamsun's amulet",null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newDagger(int depth) {
		Item item = new Item(')', AsciiPanel.white, "dagger", null);
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newSword(int depth) {
		Item item = new Item(')', AsciiPanel.brightWhite, "sword", null);
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth) {
		Item item = new Item(')', AsciiPanel.yellow, "staff", null);
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newLightArmor(int depth) {
		Item item = new Item('[', AsciiPanel.green, "tunic", null);
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newMediumArmor(int depth) {
		Item item = new Item('[', AsciiPanel.white, "chainmail", null);
		item.modifyDefenseValue(4);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newHeavyArmor(int depth) {
		Item item = new Item('[', AsciiPanel.brightWhite, "platemail", null);
		item.modifyDefenseValue(6);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item randomWeapon(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newDagger(depth);
		case 1:
			return newSword(depth);
		case 2:
			return newBow(depth);
		default:
			return newStaff(depth);
		}
	}

	public Item randomArmor(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newLightArmor(depth);
		case 1:
			return newMediumArmor(depth);
		default:
			return newHeavyArmor(depth);
		}
	}
	
	public Item newBow(int depth){
		Item item = new Item(')', AsciiPanel.yellow, "bow", null);
        item.modifyAttackValue(1);
        item.modifyRangedAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
	}
	
	public Creature newGoblin(int depth, Creature player) {
		Creature goblin = new Creature(world, 'g', "goblin", AsciiPanel.brightGreen, 66, 15, 5, 10);
		new GoblinAi(goblin, player);
		goblin.equip(randomWeapon(depth));
		goblin.equip(randomArmor(depth));
		world.addAtEmptyLocation(goblin, depth);
		return goblin;
	}
	
	public Item newPotionOfHealth(int depth) {
		String appearance = potionAppearances.get(0);
		final Item item = new Item('!', potionColours.get(appearance), "health potion", appearance);
		item.setQuaffEffect(new Effect(1) {
			public void start(Creature creature) {
				if (creature.hp() == creature.maxHp())
					return;

				creature.modifyHp(15,"Health Potion");
				creature.doAction("look healthier");
			}
		});

		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newPotionOfMana(int depth){
		String appearance = potionAppearances.get(1);
		Item item = new Item('!',potionColours.get(appearance), "mana potion", appearance);
		item.setQuaffEffect(new Effect(1){
			public void start(Creature creature){
				if (creature.getMana() == creature.getMaxMana())
					return;
				
				creature.modifyMana(10);
				creature.doAction("look restored");
			}
		});
		
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	
	public Item newPotionOfPoison(int depth){
		String appearance = potionAppearances.get(2);
		Item item = new Item('!', potionColours.get(appearance), "poison potion", appearance);
		item.setQuaffEffect(new Effect(20){
			public void start(Creature creature){
				creature.doAction("look sick");
			}
			
			public void update(Creature creature){
				super.update(creature);
				creature.modifyHp(-1,"Killed by Poison");
			}
		});
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newPotionOfWarrior(int depth){
		String appearance = potionAppearances.get(3);
		Item item =  new Item('!',potionColours.get(appearance), "warrior's potion", appearance);
		item.setQuaffEffect(new Effect(20){
			public void start(Creature creature){
				creature.modifyAttackValue(5);
	            creature.modifyDefenseValue(5);
	            creature.doAction("look stronger");
			}
			public void end(Creature creature){
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
	
	public Item newWhiteMagesSpellbook(int depth) {
		Item item = new Item('+', AsciiPanel.brightWhite, "white mage's spellbook", null);
		item.addWrittenSpell("minor heal", 4, new Effect(1) {
			public void start(Creature creature) {
				if (creature.hp() == creature.maxHp())
					return;

				creature.modifyHp(20,"Healing spell");
				creature.doAction("look healthier");
			}
		}, true);

		item.addWrittenSpell("major heal", 8, new Effect(1) {
			public void start(Creature creature) {
				if (creature.hp() == creature.maxHp())
					return;

				creature.modifyHp(50,"Healing spell");
				creature.doAction("look healthier");
			}
		}, true);

		item.addWrittenSpell("slow heal", 12, new Effect(50) {
			public void update(Creature creature) {
				super.update(creature);
				creature.modifyHp(2,"Healing spell");
			}
		}, true);

		item.addWrittenSpell("inner strength", 16, new Effect(50) {
			public void start(Creature creature) {
				creature.modifyAttackValue(2);
				creature.modifyDefenseValue(2);
				creature.modifyVisionRadius(1); //TODO
				creature.modifyRegenHpPer1000(10);
				creature.modifyRegenManaPer1000(-10);
				creature.doAction("seem to glow with inner strength");
			}

			public void update(Creature creature) {
				super.update(creature);
				if (Math.random() < 0.25)
					creature.modifyHp(1,"Little heal");
			}

			public void end(Creature creature) {
				creature.modifyAttackValue(-2);
				creature.modifyDefenseValue(-2);
				creature.modifyVisionRadius(-1); //TODO
				creature.modifyRegenHpPer1000(-10);
				creature.modifyRegenManaPer1000(10);
			}
		}, true);

		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	
	//TODO muchas cosas
	public Item newBlueMagesSpellbook(int depth) {
		Item item = new Item('+', AsciiPanel.brightBlue, "blue mage's spellbook", null);

		item.addWrittenSpell("blood to mana", 1, new Effect(1) {
			public void start(Creature creature) {
				int amount = Math.min(creature.hp() - 1, creature.getMaxMana() - creature.getMana());
				creature.modifyHp(-amount, "Killed by mana generation");
				creature.modifyMana(amount);
			}
		}, true);

		item.addWrittenSpell("blink", 6, new Effect(1) { //TODO no enntiendo este hechizo
			public void start(Creature creature) {
				creature.doAction("fade out");

				int mx = 0;
				int my = 0;

				do {
					mx = (int) (Math.random() * 11) - 5;
					my = (int) (Math.random() * 11) - 5;
				} while (!creature.canEnter(creature.x + mx, creature.y + my, creature.z)
						&& creature.canSee(creature.x + mx, creature.y + my, creature.z));

				creature.moveBy(mx, my, 0);

				creature.doAction("fade in");
			}
		}, true);

		item.addWrittenSpell("summon bats", 11, new Effect(1) {
			public void start(Creature creature) {
				for (int ox = -1; ox < 2; ox++) {
					for (int oy = -1; oy < 2; oy++) {
						int nx = creature.x + ox;
						int ny = creature.y + oy;
						if (ox == 0 && oy == 0 || creature.creature(nx, ny, creature.z) != null)
							continue;

						Creature bat = newBat(0);

						if (!bat.canEnter(nx, ny, creature.z)) {
							world.remove(bat);
							continue;
						}

						bat.x = nx;
						bat.y = ny;
						bat.z = creature.z;

						creature.summon(bat);
					}
				}
			}
		}, true);

		item.addWrittenSpell("detect creatures", 16, new Effect(75) {
			public void start(Creature creature) {
				creature.doAction("look far off into the distance");
				creature.modifyDetectCreatures(1);
			}

			public void end(Creature creature) {
				creature.modifyDetectCreatures(-1);
			}
		}, true);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item randomSpellBook(int depth){
		switch ((int)(Math.random() * 2)){
		case 0: return newWhiteMagesSpellbook(depth);
		default: return newBlueMagesSpellbook(depth);
		}
	}

	
}
