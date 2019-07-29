package Factories;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import Elements.Creature;
import Elements.Effect;
import Elements.Item;
import Rogue.World;
import Utils.NameSynonymsGetter;
import asciiPanel.AsciiPanel;

public class SpellBookFactory {

	private World world;
	private NameSynonymsGetter getter;
	private String[] arr = {"book", "perchment", "grimoire", "tome"};
	
	public SpellBookFactory(World world){
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
	}
	
	private String getRandomNameSeed(){
		Random r = new Random();
		int randomNumber = r.nextInt(arr.length);
		return arr[randomNumber];
	}
	
	public Item newWhiteMagesSpellbook(int depth) {
		String seed = getter.getRandomSynonym(getRandomNameSeed());
		String name = "white mage's "+seed;
		String adj = getter.getRandomAdjSynonym("old");
		Item item = new Item('+', AsciiPanel.brightWhite, "white mage's spellbook", name, adj, null);
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
		String seed = getter.getRandomSynonym(getRandomNameSeed());
		String name = "blue mage's "+seed;
		String adj = getter.getRandomAdjSynonym("old");
		Item item = new Item('+', AsciiPanel.brightBlue, "blue mage's spellbook", name, adj, null);

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
				} while (!creature.canEnter(creature.getX() + mx, creature.getY() + my, creature.getZ())
						&& creature.canSee(creature.getX() + mx, creature.getY() + my, creature.getZ()));

				creature.moveBy(mx, my, 0);

				creature.doAction("fade in");
			}
		}, true);

		item.addWrittenSpell("summon bats", 11, new Effect(1) {
			public void start(Creature creature) {
				for (int ox = -1; ox < 2; ox++) {
					for (int oy = -1; oy < 2; oy++) {
						int nx = creature.getX() + ox;
						int ny = creature.getY() + oy;
						if (ox == 0 && oy == 0 || creature.creature(nx, ny, creature.getZ()) != null)
							continue;
						CreatureFactory cf = new CreatureFactory(world);
						Creature bat = cf.newBat(0); //TODO la profundiadd no deberia ser 0, deberia ser donde este el jugador

						if (!bat.canEnter(nx, ny, creature.getZ())) {
							world.remove(bat);
							continue;
						}
						
						bat.setX(nx);
						bat.setY(ny);
						bat.setZ(creature.getZ());

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
