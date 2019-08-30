package Factories;

/**
 * Defines the SpellBookFactory which creates Spell Books as Items
 * 
 * @author comec
 */
import java.util.ArrayList;
import java.util.HashMap;
import Elements.Creature;
import Elements.Effect;
import Elements.Item;
import Rogue.World;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;
import TextManagement.Realizator;

public class SpellBookFactory {

	private World world;
	private String[] n_arr = {"book", "perchment", "grimoire", "tome"};
	private String[] a_arr = {"old","dusty","big_size"};
	
	public SpellBookFactory(World world){
		this.world = world;
	}
	

	/**
	 * Creates a White Mages spell book which focuses on healing spells which are declared with it's effect too.
	 * @param depth level of the dungeon in which the book will be placed
	 * @return
	 */
	public Item newWhiteMagesSpellbook(int depth) {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		Realizator realizator = factory.getRealizator();
		HashMap<String,String> nameData = getter.getNounData(getter.getRandomSeed(n_arr));
		HashMap<String,String> adjData = getter.getAdjData(getter.getRandomSeed(a_arr), nameData.get("genere"));
		String wizardClass = getter.getDirectTranslation("SpellBookFactory", "white");
		String name = realizator.constructNounPosvNoun(nameData.get("baseNoun"), wizardClass);
		Item item = new Item('+', AsciiPanel.brightWhite, "white mage's spellbook", name, nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		item.addWrittenSpell(getter.getDirectTranslation("SpellBookFactory", "minorHeal"), 4, new Effect(1) {
			public void start(Creature creature, Creature source) {
				if (creature.hp() == creature.maxHp())
					return;

				creature.modifyHp(20,"Healing spell");
				creature.doAction(getter.getDirectTranslation("SpellBookFactory", "minorHealEffect"));
			}
		}, true);

		item.addWrittenSpell(getter.getDirectTranslation("SpellBookFactory", "majorHeal"), 8, new Effect(1) {
			public void start(Creature creature, Creature source) {
				if (creature.hp() == creature.maxHp())
					return;

				creature.modifyHp(50,"Healing spell");
				creature.doAction(getter.getDirectTranslation("SpellBookFactory", "majorHealEffect"));
			}
		}, true);

		item.addWrittenSpell(getter.getDirectTranslation("SpellBookFactory", "slowHeal"), 12, new Effect(50) {
			public void update(Creature creature) {
				super.update(creature);
				creature.modifyHp(2,"Healing spell");
			}
		}, true);

		item.addWrittenSpell(getter.getDirectTranslation("SpellBookFactory", "innerStrength"), 16, new Effect(50) {
			public void start(Creature creature, Creature source) {
				creature.modifyAttackValue(2);
				creature.modifyDefenseValue(2);
				creature.modifyVisionRadius(1); //TODO
				creature.modifyRegenHpPer1000(10);
				creature.modifyRegenManaPer1000(-10);
				creature.doAction(getter.getDirectTranslation("SpellBookFactory", "innerStrengthEffect"));
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
	
	
	/**
	 * Creates a Blue Mages spell book which focuses on "tricky" spells which are declared with it's effect too.
	 * @param depth level of the dungeon in which the book will be placed
	 * @return
	 */
	public Item newBlueMagesSpellbook(int depth) {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		Realizator realizator = factory.getRealizator();
		HashMap<String,String> nameData = getter.getNounData(getter.getRandomSeed(n_arr));
		HashMap<String,String> adjData = getter.getAdjData(getter.getRandomSeed(a_arr), nameData.get("genere"));
		String wizardClass = getter.getDirectTranslation("SpellBookFactory", "blue");
		String name = realizator.constructNounPosvNoun(nameData.get("baseNoun"), wizardClass);
		Item item = new Item('+', AsciiPanel.brightBlue, "blue mage's spellbook", name, nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);

		item.addWrittenSpell(getter.getDirectTranslation("SpellBookFactory", "bloodToMana"), 1, new Effect(1) {
			public void start(Creature creature, Creature source) {
				int amount = Math.min(creature.hp() - 1, creature.getMaxMana() - creature.getMana());
				creature.modifyHp(-amount, getter.getDirectTranslation("SpellBookFactory", "bloodToManaEffect"));
				creature.modifyMana(amount);
			}
		}, true);

		item.addWrittenSpell(getter.getDirectTranslation("SpellBookFactory", "blink"), 6, new Effect(1) { //TODO no enntiendo este hechizo
			public void start(Creature creature, Creature source) {
				creature.doAction(getter.getDirectTranslation("SpellBookFactory", "blinkEffect"));

				int mx = 0;
				int my = 0;

				do {
					mx = (int) (Math.random() * 11) - 5;
					my = (int) (Math.random() * 11) - 5;
				} while (!creature.canEnter(creature.getX() + mx, creature.getY() + my, creature.getZ())
						&& creature.canSee(creature.getX() + mx, creature.getY() + my, creature.getZ()));

				creature.moveBy(mx, my, 0);

				creature.doAction(getter.getDirectTranslation("SpellBookFactory", "blinkEndEffect"));
			}
		}, true);

		item.addWrittenSpell(getter.getDirectTranslation("SpellBookFactory", "summonBats"), 11, new Effect(1) {
			public void start(Creature creature, Creature source) {
				HashMap<String, String> ciData = new HashMap<String, String>();
				HashMap<String, String> CI = new HashMap<String, String>();
				ArrayList<Creature> bats = new ArrayList<>();
				for (int ox = -1; ox < 2; ox++) {
					for (int oy = -1; oy < 2; oy++) {
						int nx = creature.getX() + ox;
						int ny = creature.getY() + oy;
						if (ox == 0 && oy == 0 || creature.creature(nx, ny, creature.getZ()) != null)
							continue;
						CreatureFactory cf = new CreatureFactory(world);
						Creature bat = cf.newBat(0); 

						if (!bat.canEnter(nx, ny, creature.getZ())) {
							world.remove(bat);
							continue;
						}
						
						bat.setX(nx);
						bat.setY(ny);
						bat.setZ(creature.getZ());
						
						ciData = bat.getMorfData("plural");
						CI = bat.getNameAdjectiveKey("plural");	
						
						bats.add(bat);
					}
				}	
				
				HashMap<String, String> ccData =  item.getMorfData("singular");
				HashMap<String, String> cc = item.getNameAndAdjective("singular");
				cc.put("key", "book");
				cc.put("type", "CCI");
				HashMap<String, String> verbData = new HashMap<>();
				verbData.put("actionType", "Summon");
				verbData.put("VbNum", "singular");
				verbData.put("VbPerson", "third");
				verbData.put("VbForm", "active");
				verbData.put("VbTime", "present");
				creature.placeSummoned(source, bats, ccData, cc, ciData, CI, verbData, "WeaponsAttacks", item);
			}
		}, true);

		item.addWrittenSpell(getter.getDirectTranslation("SpellBookFactory", "detectCreatures"), 16, new Effect(75) {
			public void start(Creature creature, Creature source) {
				creature.doAction(getter.getDirectTranslation("SpellBookFactory", "detectCreaturesEffect"));
				creature.modifyDetectCreatures(1);
			}

			public void end(Creature creature) {
				creature.modifyDetectCreatures(-1);
			}
		}, true);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	/**
	 * Places a random book ina level of the dungeon
	 * @param depth level of the dungeon in which the book will be placed
	 * @return the book created
	 */
	public Item randomSpellBook(int depth) {
		switch ((int) (Math.random() * 2)) {
		case 0:
			return newWhiteMagesSpellbook(depth);
		default:
			return newBlueMagesSpellbook(depth);
		}
	}

}
