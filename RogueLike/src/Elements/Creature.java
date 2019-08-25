package Elements;

/**
 * Defines the creature class
 * 
 * @author comec
 */
import java.awt.Color;

import Rogue.World;
import Screens.PlayScreen;
import TextManagement.Realizator;
import TextManagement.Restrictions;
import TextManagement.RestrictionsFactory;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import CreaturesAI.CreatureAi;
import DungeonComponents.Line;
import DungeonComponents.Staircase;
import DungeonComponents.Tile;
import Elements.Effect;
import Utils.Position;

public class Creature {

	private World world;
	private CreatureAi ai;
	private int maxHp;
	private int hp;
	private int attackValue;
	private int defenseValue;
	private Color color;
	private char glyph;
	private int x;
	private int y;
	private int z;
	private int visionRadius;
	private String key;
	private String name;
	private Inventory inventory;
	private Item weapon;
	private Item armor;
	private int xp;
	private int level;
	private int regenHpCooldown;
	private int regenHpPer1000 = 1000;
	private ArrayList<Effect> effects;
	private int maxMana;
	private int mana;
	private int regenManaCooldown = 1000;
	private int regenManaPer1000;
	private int detectCreatures;
	private String causeOfDeath;
	private String characteristic;
	private String n_plu;
	private String genere;
	private String charc_plu;
	private RestrictionsFactory factoryR= null;
	private WordDataGetter getter = null;
	private Realizator realizator = null;
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public char glyph() {
		return glyph;
	}

	public Color color() {
		return color;
	}

	public int maxHp() {
		return maxHp;
	}

	public int hp() {
		return hp;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public int getMana() {
		return mana;
	}
	
	public void modifyMana(int amount) {
		mana = Math.max(0, Math.min(mana + amount, maxMana));
	}

	public void modifyRegenManaPer1000(int amount) {
		regenManaPer1000 += amount;
	}

	private void regenerateMana() {
		regenManaCooldown -= regenManaPer1000;
		if (regenManaCooldown < 0) {
			if (mana < maxMana) {
				modifyMana(1);
			}
			regenManaCooldown += 1000;
		}
	}

	public void gainMaxMana() {
		maxMana += 5;
		mana += 5;
		doAction("look more magical");
	}

	public void gainRegenMana() {
		regenManaPer1000 += 5;
		doAction("look a little less tired");
	}

	public void modifyDetectCreatures(int amount) {
		detectCreatures += amount;
	}
	
	public String getCauseOfDeath(){
		return causeOfDeath;
	}
	
	public String getCharacteristic(){
		return characteristic;
	}

	public String getCharactAndName(){
		return characteristic+" "+name;
	}

	/**
	 * Constructor of a Creature 
	 * @param world the dungeon so it can iteract with it
	 * @param glyph char that will represent the Creature in the game
	 * @param key string that will identify creatures of the same type
	 * @param name string that names the creature
	 * @param n_plu string that is the name in plural
	 * @param genere morphological gender of the word
	 * @param color color in which the glyph will be in the ga,e
	 * @param maxHp Life point of the creature 
	 * @param attack Attack points of the creature
	 * @param defense Defense points of the creature
	 * @param invt_size Size of the inventory carried by the creature
	 * @param characteristic String that is a characteristic of the creature
	 * @param charc_plu the characteristic but in plural
	 */
	public Creature(World world, char glyph, String key, String name, String n_plu, String genere, Color color,
			int maxHp, int attack, int defense, int invt_size, String characteristic, String charc_plu) {
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 9;
		this.key = key;
		this.name = name;
		this.n_plu = n_plu;
		this.genere = genere;
		this.inventory = new Inventory(invt_size);
		this.level = 1;
		this.regenHpPer1000 = 10;
		this.effects = new ArrayList<Effect>();
		this.maxMana = 5;
		this.mana = maxMana;
		this.regenManaPer1000 = 10;
		this.characteristic = characteristic;
		this.charc_plu = charc_plu;
		this.getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
		this.realizator = WordDataGetterAndRealizatorFactory.getInstance().getRealizator();
		this.factoryR = RestrictionsFactory.getInstance();
	}

	public void modifyAttackValue(int value) {
		attackValue += value;
	}

	/**
	 * Obtains the attack value  of the creature plus the value of it's equipement
	 * @return int that is the attack value
	 */
	public int attackValue() {
		int weaponValue = 0;
		int armorValue = 0;
		if (weapon != null) {
			weaponValue = weapon.getAttackValue();
		}
		if (armor != null) {
			armorValue = armor.getAttackValue();
		}
		return attackValue + weaponValue + armorValue;
	}

	public void modifyDefenseValue(int value) {
		defenseValue += value;
	}

	/**
	 * Obtains the defense value  of the creature plus the value of it's equipement
	 * @return int that is the defense value
	 */
	public int defenseValue() {
		int weaponValue = 0;
		int armorValue = 0;
		if (weapon != null) {
			weaponValue = weapon.getDefenseValue();
		}
		if (armor != null) {
			armorValue = armor.getDefenseValue();
		}
		return defenseValue + weaponValue + armorValue;
	}

	public void setCreatureAi(CreatureAi ai) {
		this.ai = ai;
	}

	public int visionRadius() {
		return this.visionRadius;
	}

	public String name() {
		return name;
	}

	public Inventory inventory() {
		return inventory;
	}

	public Item getWeapon() {
		return weapon;
	}

	public Item getArmor() {
		return armor;
	}

	public int getXp() {
		return xp;
	}

	public int getLevel() {
		return level;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}

	/**
	 * Gains exprecience points based on the data of the killed creature
	 * @param other
	 */
	public void gainXp(Creature other) {
		int amount = other.maxHp + other.attackValue() + other.defenseValue() - level * 2;

		if (amount > 0) {
			modifyXp(amount);
		}
	}
	
	/**
	 * Modifies the HP of the creature for both recovering and lost of it.
	 * If the character dies a message is shown.
	 * @param amount int that will be added or substracted from the hp of the creature
	 */
	public void modifyHp(int amount, String casuse) {
		hp += amount;
		//(this.causeOfDeath = causeOfDeath;
		if (hp > maxHp) {
			hp = maxHp;
		} else if (hp < 1) {
			HashMap<String, String> subjectData = this.getMorfData("singular");
			HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
			HashMap<String, String> verb = new HashMap<>();
			verb.put("actionType", "die");
			verb.put("adverb", null);
			// VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum
			Restrictions res = factoryR.getRestrictionsVbSuj("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"));

			doActionComplex(verb, subject, null, null, null, null, res, "MostBasicTemplate");
			leaveCorpse();
			world.remove(this);
		}
	}

	/**
	 * Generates an item "corpse" when the creature is deceased and it drops all the items that it carried.
	 */
	private void leaveCorpse() {
		HashMap<String, String> corpseData = getter.getNounData("corpse");
		HashMap<String, String> adjData = getter.getAdjData("average", corpseData.get("genere"));
		String finalName = realizator.constructNounAndNoun(corpseData.get("baseNoun"), name);
		Item corpse = new Item('%', color, name + " corpse", finalName, corpseData.get("plural"),
				corpseData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		corpse.modifyFoodValue(maxHp);
		world.addAtEmptySpace(corpse, x, y, z);
		for (Item item : inventory.getItems()) {
			if (item != null)
				drop(item);
		}
	}
	
	/**
	 * The creaute unequips the current gear he has equiped.
	 * 
	 * @param item the Item that is going to be removed
	 */
	public void unequip(Item item) {
		if (item == null) {
			return;
		}
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "unequip");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
		HashMap<String, String> subjectData = this.getMorfData("singular");
		HashMap<String, String> cd = item.getNameAndAdjective("singular");
		cd.put("name", nameOf(item));
		HashMap<String, String> cdData = item.getMorfData("singular");
		//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum
		Restrictions res = factoryR.getRestrictionsVbSujCd("singular", "third", "active", "present",
				subjectData.get("genere"), subjectData.get("number"), cdData.get("genere"), cdData.get("number"));
		if (item == armor) {
			doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");
			armor = null;
		} else if (item == weapon) {
			doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");
			weapon = null;
		}
	}

	/**
	 * Creature equips an item, first removing the one that was carrying
	 * @param item The item that is going to be equipped
	 */
	public void equip(Item item) {
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "equip");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
		HashMap<String, String> subjectData = this.getMorfData("singular");
		HashMap<String, String> cd = item.getNameAndAdjective("singular");
		cd.put("name", nameOf(item));
		HashMap<String, String> cdData = item.getMorfData("singular");
		//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum
		Restrictions res = factoryR.getRestrictionsVbSujCd("singular", "third", "active", "present",
				subjectData.get("genere"), subjectData.get("number"), cdData.get("genere"), cdData.get("number"));

		if (!inventory.contains(item)) {
			if (inventory.isFull()) {
				notify(getter.getDirectTranslation("Creature", "cantEquip"), nameOf(item));
				return;
			} else {
				world.remove(item);
				inventory.add(item);
			}
		}

		if (item.getAttackValue() == 0 && item.getRangedAttackValue() == 0 && item.getDefenseValue() == 0) {
			return;
		}

		if (item.getAttackValue() + item.getRangedAttackValue() >= item.getDefenseValue()) {
			unequip(weapon);
			if (this.key.equals("Player")) {
				doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");
			}
			weapon = item;
		} else {
			unequip(armor);
			if (this.key.equals("Player")) {
				doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");
			}
			armor = item;
		}
	}

	/**
	 * Deals with the movement of the creature and also the change of level of the dungeon trough the stairs
	 * @param mx Change on the position x
	 * @param my Change on the position y
	 * @param mz Change on the position z
	 */
	public void moveBy(int mx, int my, int mz) {
		Staircase stair;
		Tile tile = world.tile(x + mx, y + my, z);
		int pos_x = x + mx;
		int pos_y = y + my;
		int pos_z = z + mz;
		int numLevel = 0;

		if (mx == 0 && my == 0 && mz == 0)
			return;

		if (mz == -1) {
			if (tile == Tile.STAIRS_UP) {

				stair = world.getStairs().get(z - 1);
				numLevel = z + mz + 1;
				pos_x = stair.getBeginning().getIntX();
				pos_y = (world.getHeight() - 1) - stair.getBeginning().getIntY();
				pos_z = stair.getBeginning().getZ();

				HashMap<String, String> subjectData = this.getMorfData("singular");
				HashMap<String, String> tileData = tile.getMorfStairs();
				HashMap<String, String> tileNameAdj = tile.getStairsNounAndType("plural");
				HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
				HashMap<String, String> verb = new HashMap<>();
				verb.put("actionType", "ascend");
				verb.put("adverb", null);
				HashMap<String, String> ccThings = getter.getNounData("level");
				HashMap<String, String> ccData = new HashMap<>();
				ccData.put("number", "singular");
				ccData.put("genere", ccThings.get("genere"));
				HashMap<String, String> cc = new HashMap<>();
				cc.put("name", ccThings.get("baseNoun"));
				cc.put("key", "level");
				cc.put("characteristic", Integer.toString(numLevel) + "º");
				cc.put("type", "CCL");
				//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum, CCGen, CCNum
				Restrictions res = factoryR.getRestrictionsVbSujCdCc("singular", "third", "active", "present",
						subjectData.get("genere"), subjectData.get("number"), tileData.get("genere"),
						tileData.get("number"), ccData.get("genere"), ccData.get("number"));
				doActionComplex(verb, subject, tileNameAdj, null, cc, null, res, "ChangeDungeonLevel");

				tile = world.tile(stair.getBeginning().getIntX(),
						(world.getHeight() - 1) - stair.getBeginning().getIntY(), stair.getBeginning().getZ());
			} else {
				doAction(getter.getDirectTranslation("Creature", "caveCeiling")); //TODO traduccion simple
				return;
			}
		} else if (mz == 1) {
			if (tile == Tile.STAIRS_DOWN) {

				stair = world.getStairs().get(z);
				pos_x = stair.getEnding().getIntX();
				pos_y = (world.getHeight() - 1) - stair.getEnding().getIntY();
				pos_z = stair.getEnding().getZ();
				numLevel = z + mz + 1;

				HashMap<String, String> subjectData = this.getMorfData("singular");
				HashMap<String, String> tileData = tile.getMorfStairs();
				HashMap<String, String> tileNameAdj = tile.getStairsNounAndType("plural");
				HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
				HashMap<String, String> verb = new HashMap<>();
				verb.put("actionType", "descend");
				verb.put("adverb", null);
				HashMap<String, String> ccThings = getter.getNounData("level");
				HashMap<String, String> ccData = new HashMap<>();
				ccData.put("number", "singular");
				ccData.put("genere", ccThings.get("genere"));
				HashMap<String, String> cc = new HashMap<>();
				cc.put("name", ccThings.get("baseNoun"));
				cc.put("key", "level");
				cc.put("characteristic", Integer.toString(numLevel) + "º");
				cc.put("type", "CCL");
				//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum, CCgen, CCNum
				Restrictions res = factoryR.getRestrictionsVbSujCdCc("singular", "third", "active", "present",
						subjectData.get("genere"), subjectData.get("number"), tileData.get("genere"),
						tileData.get("number"), ccData.get("genere"), ccData.get("number"));
				doActionComplex(verb, subject, tileNameAdj, null, cc, null, res, "ChangeDungeonLevel");

				tile = world.tile(stair.getEnding().getIntX(), (world.getHeight() - 1) - stair.getEnding().getIntY(),
						stair.getEnding().getZ());

			} else {
				doAction(getter.getDirectTranslation("Creature", "caveFloor")); //TODO traduccion simple
				return;
			}
		}

		Creature other = world.creature(x + mx, y + my, z + mz);

		if (other == null)
			ai.onEnter(pos_x, pos_y, pos_z, tile);
		else {

			meleeAttack(other);
		}

	}

	public void moveTo(Position p) { //TODO wtf happened
		// this.ai.onStep(p);
	}

	/**
	 * Allows digging trough the walls (it's for testing)
	 * @param wx x of the position to dig
	 * @param wy y of the position to dig
	 * @param wz z of the position to dig
	 */
	public void dig(int wx, int wy, int wz) {
		world.dig(wx, wy, wz);
	}

	/**
	 * Function called when the games updates it's state. 
	 * Creature updates it's stats and also calls it's ai to update too
	 */
	public void update() {
		regenerateHealth();
		regenerateMana();
		updateEffects();
		ai.onUpdate();
	}

	public Position getPosition() {
		return new Position(this.x, this.y, this.z);
	}

	public void modifyRegenHpPer1000(int amount) {
		regenHpPer1000 += amount;
	}

	/**
	 * This functions allows the notification of the message to the creatures who can see this one.
	 * @param message String to notify
	 * @param params Another details of the message
	 */
	public void doAction(String message, Object... params) {
		for (Creature other : getCreaturesWhoSeeMe()) {
			other.notify(message, params);
		}
	}

	/**
	 * Similar to the previous one but this one also calls learnName so the creatures learn the item
	 * @param item Item to learn
	 * @param message String to notify
	 * @param params Another details of the message
	 */
	public void doAction(Item item, String message, Object... params) {
		if (hp < 1)
			return;

		for (Creature other : getCreaturesWhoSeeMe()) {
			if (other == this) {
				other.notify("You " + message + ".", params);
			} else {
				other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
			}
			other.learnName(item);
		}
	}

	/**
	 * Similar to the previous one but now this relies on text generation so it needs way more parameters.
	 * @param verb HashMap that contains data regarding the verb
	 * @param Subject HashMap that contains data regarding the subject
	 * @param CD HashMap that contains data regarding the object
	 * @param CI HashMap that contains data regarding the indirect object
	 * @param CC HashMap that contains data regarding the place in which the action takes place or the means by which the action was made
	 * @param Atribute HashMap that contains data regarding the attributes associated to the subject by the verb
	 * @param res HashMap that contains data regarding the  morfological restrictions
	 * @param templateType String that describes the type of template that must be used by te Realizator
	 * @param item Item to learn
	 */
	public void doActionComplex(HashMap<String, String> verb, HashMap<String, String> Subject,
			HashMap<String, String> CD, HashMap<String, String> CI, HashMap<String, String> CC,
			HashMap<String, String> Atribute, Restrictions res, String templateType, Item item) {

		String phrase = realizator.realizatePhrase(verb, Subject, CD, CI, CC, Atribute, res, templateType);
		for (Creature other : getCreaturesWhoSeeMe()) {
			other.notify(phrase);
			if (!verb.get("actionType").equals("Summon")) {
				if (item.getKey().contains("potion")){
					other.learnName(item);
				}
			}
		}
	}

	/**
	 * Similar to the previous one but this one does not call learnName
	 * @param verb HashMap that contains data regarding the verb
	 * @param Subject HashMap that contains data regarding the subject
	 * @param CD HashMap that contains data regarding the object
	 * @param CI HashMap that contains data regarding the indirect object
	 * @param CC HashMap that contains data regarding the place in which the action takes place or the means by which the action was made
	 * @param Atribute HashMap that contains data regarding the attributes associated to the subject by the verb
	 * @param res HashMap that contains data regarding the  morphological restrictions
	 * @param templateType String that describes the type of template that must be used by the Realizator
	 */
	public void doActionComplex(HashMap<String, String> verb, HashMap<String, String> Subject,
			HashMap<String, String> CD, HashMap<String, String> CI, HashMap<String, String> CC,
			HashMap<String, String> Atribute, Restrictions res, String templateType) {

		String phrase = realizator.realizatePhrase(verb, Subject, CD, CI, CC, Atribute, res, templateType);
		for (Creature other : getCreaturesWhoSeeMe()) {
			other.notify(phrase);

		}
	}

	/**
	 * This function obtains a String generated by the realizator but it is not notificated
	 * @param verb HashMap that contains data regarding the verb
	 * @param Subject HashMap that contains data regarding the subject
	 * @param CD HashMap that contains data regarding the object
	 * @param CI HashMap that contains data regarding the indirect object
	 * @param CC HashMap that contains data regarding the place in which the action takes place or the means by which the action was made
	 * @param Atribute HashMap that contains data regarding the attributes associated to the subject by the verb
	 * @param res HashMap that contains data regarding the  morphological restrictions
	 * @param templateType String that describes the type of template that must be used by the Realizator
	 * @return String that is the constructed phrase
	 */
	public String getNotification(HashMap<String, String> verb, HashMap<String, String> Subject,
			HashMap<String, String> CD, HashMap<String, String> CI, HashMap<String, String> CC,
			HashMap<String, String> Atribute, Restrictions res, String templateType) {

		return realizator.realizatePhrase(verb, Subject, CD, CI, CC, Atribute, res, templateType);
	}

	/**
	 * Obtains list of Creatures which can see this one
	 * @return list of Creatures
	 */
	private List<Creature> getCreaturesWhoSeeMe() {
		List<Creature> others = new ArrayList<Creature>();
		int r = 9;
		for (int ox = -r; ox < r + 1; ox++) {
			for (int oy = -r; oy < r + 1; oy++) {
				if (ox * ox + oy * oy > r * r)
					continue;

				Creature other = world.creature(x + ox, y + oy, z);

				if (other == null)
					continue;

				others.add(other);
			}
		}
		return others;
	}
	
	/**
	 * Obtains the creatures, items and stairs that are visible
	 * @return A HashMap containing three lists, one for the creatures, another one for the items and a last one for the stairs
	 */
	public HashMap<String, ArrayList<Position>> getVisibleThings() {
		HashMap<String, ArrayList<Position>> result = new HashMap<>();
		ArrayList<Position> creatures = new ArrayList<>();
		ArrayList<Position> items = new ArrayList<>();
		ArrayList<Position> stairs = new ArrayList<>();
		Position p = null;
		int r = this.visionRadius;
		for (int ox = -r; ox < r + 1; ox++) {
			for (int oy = -r; oy < r + 1; oy++) {
				if (ox * ox + oy * oy > r * r) {
					continue;
				}
				if (this.canSee(x + ox, y + oy, z)) {
					p = new Position(x + ox, y + oy, z);
					if (p.isValidPosition()) {
						Creature otherCreature = world.creature(x + ox, y + oy, z);
						Item otherItem = world.item(x + ox, y + oy, z);
						Tile tile = world.tile(x + ox, y + oy, z);
						if ((otherCreature != null) && (!otherCreature.equals(this))) {
							creatures.add(p);
						} else if (otherItem != null) {
							items.add(p);
						} else if (tile.isStair()) {
							stairs.add(p);
						} else {
							continue;
						}
					}
				}

			}
		}
		result.put("Creatures", creatures);
		result.put("Items", items);
		result.put("Stairs", stairs);
		return result;
	}

	//TODO esta la podre borrar en un futuro
	private String makeSecondPerson(String text) {
		String[] words = text.split(" ");
		words[0] = words[0] + "s";

		StringBuilder builder = new StringBuilder();
		for (String word : words) {
			builder.append(" ");
			builder.append(word);
		}

		return builder.toString().trim();
	}

	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}

	public static final Comparator<Creature> DISTANCE_TO_PLAYER = new Comparator<Creature>() {
		public int compare(Creature c1, Creature c2) {
			return c1.getPosition().distance(PlayScreen.getPlayer().getPosition())
					.compareTo(c2.getPosition().distance(PlayScreen.getPlayer().getPosition()));
		}
	};

	public void notify(String message, Object... params) {
		ai.onNotify(String.format(message, params));
	}

	public boolean canSee(int wx, int wy, int wz) {
		return (detectCreatures > 0 && world.creature(wx, wy, wz) != null || ai.canSee(wx, wy, wz));
	}

	public Tile realTile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}

	public Tile tile(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.tile(wx, wy, wz);
		else
			return ai.rememberedTile(wx, wy, wz);
	}

	public Creature creature(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.creature(wx, wy, wz);
		else
			return null;
	}

	public Item item(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.item(wx, wy, wz);
		else
			return null;
	}

	/**
	 * Takes an item in the position of the creature
	 */
	public void pickup() {
		Item item = world.item(x, y, z);

		if (inventory.isFull() || item == null) {
			String phrase = getter.getDirectTranslation("Creature", "Cant take more");
			doAction(phrase);
		} else {
			HashMap<String, String> verb = new HashMap<>();
			verb.put("actionType", "take");
			verb.put("adverb", null);
			verb.put("Form", "Singular");
			HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
			HashMap<String, String> subjectData = this.getMorfData("singular");
			HashMap<String, String> cd = item.getNameAndAdjective("singular");
			cd.put("name", nameOf(item));
			HashMap<String, String> cdData = item.getMorfData("singular");
			//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum
			Restrictions res = factoryR.getRestrictionsVbSujCd("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), cdData.get("genere"), cdData.get("number"));
			doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");
			world.remove(x, y, z);
			inventory.add(item);
		}
	}

	/**
	 * Drops the selected item in a nearby position
	 * @param item the Item that is going to be dropped
	 */
	public void drop(Item item) {
		if (world.addAtEmptySpace(item, x, y, z)) {
			HashMap<String, String> verb = new HashMap<>();
			verb.put("actionType", "drop");
			verb.put("adverb", null);
			verb.put("Form", "Singular");
			HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
			HashMap<String, String> subjectData = this.getMorfData("singular");
			HashMap<String, String> cd = item.getNameAndAdjective("singular");
			cd.put("name", nameOf(item));
			HashMap<String, String> cdData = item.getMorfData("singular");
			//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum
			Restrictions res = factoryR.getRestrictionsVbSujCd("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), cdData.get("genere"), cdData.get("number"));
			doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");
			inventory.remove(item);
			unequip(item);
		} else {
			String phrase = getter.getDirectTranslation("Creature", "Cant drop");
			doAction(phrase);
		}
	}

	/**
	 * Removes the item and from the inventory unequips it 
	 * @param item
	 */
	private void getRidOf(Item item) {
		inventory.remove(item);
		unequip(item);
	}


	/**
	 * Places the item in the indicated position and unequips it
	 * @param item
	 * @param wx
	 * @param wy
	 * @param wz
	 */
	private void putAt(Item item, int wx, int wy, int wz) {
		inventory.remove(item);
		unequip(item);
		world.addAtEmptySpace(item, wx, wy, wz);
	}

	public void modifyFood(int amount) {
		hp += amount;
		if (hp > maxHp) {
			hp = maxHp;
		}
	}

	/**
	 * Throws an item to the indicated coordinates
	 * @param item
	 * @param wx
	 * @param wy
	 * @param wz
	 */
	public void throwItem(Item item, int wx, int wy, int wz) {
		Position end = new Position(x, y, 0);

		for (Position p : new Line(x, y, wx, wy)) {
			if (!realTile(p.getIntX(), p.getIntY(), z).isGround())
				break;
			end = p;
		}

		wx = end.getIntX();
		wy = end.getIntY();

		Creature c = creature(wx, wy, wz);

		if (c != null) {

			throwAttack(item, c);
		} else {
			HashMap<String, String> verb = new HashMap<>();
			verb.put("actionType", "throw");
			verb.put("adverb", null);
			verb.put("Form", "Singular");
			HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
			HashMap<String, String> subjectData = this.getMorfData("singular");
			HashMap<String, String> cd = item.getNameAndAdjective("singular");
			cd.put("name", nameOf(item));
			HashMap<String, String> cdData = item.getMorfData("singular");
			//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum
			Restrictions res = factoryR.getRestrictionsVbSujCd("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), cdData.get("genere"), cdData.get("number"));
			doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");
		}

		putAt(item, wx, wy, wz);
	}

	/**
	 * Prepares the data necessary for the generation of the text and calculates the damage of this type of attack
	 * @param other the Creature which suffers the attack
	 */
	public void meleeAttack(Creature other) {
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "Attack");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> ci = other.getNameAdjectiveKey("singular");
		HashMap<String, String> cc = new HashMap<>();
		HashMap<String, String> ccData = new HashMap<>();
		if (weapon != null) {
			cc = weapon.getNameAndAdjective("singular");
			ccData = weapon.getMorfData("singular");
			cc.put("key", weapon.getKey());
			cc.put("type", "CCI");
		} else {
			HashMap<String, String> ccExtraData = getter.getNounData("body");
			ccData.put("number", "singular");
			ccData.put("genere", ccExtraData.get("genere"));
			cc.put("name", ccExtraData.get("baseNoun"));
			cc.put("key", "body");
			cc.put("characteristic", "");
			cc.put("type", "CCI");
		}

		HashMap<String, String> ciData = other.getMorfData("singular");

		commonAttack(other, attackValue(), verb, null, null, ci, ciData, cc, ccData, "WeaponsAttacks", null);
	}

	/**
	 * Prepares the data necessary for the generation of the text and calculates the damage of this type of attack
	 * @param other the Creature which suffers the attack
	 */
	private void throwAttack(Item item, Creature other) {
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "ThrowAttack");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> ci = other.getNameAdjectiveKey("singular");
		HashMap<String, String> ciData = other.getMorfData("singular");
		HashMap<String, String> cd = item.getNameAndAdjective("singular");
		cd.put("name", nameOf(item));
		HashMap<String, String> cdData = item.getMorfData("singular");

		commonAttack(other, attackValue / 2 + item.getThrownAttackValue(), verb, cd, cdData, ci, ciData, null, null,
				"ThrowAttack", item);
		other.addEffect(item.getQuaffEffect(), this);
	}

	/**
	 * Prepares the data necessary for the generation of the text and calculates the damage of this type of attack
	 * @param other the Creature which suffers the attack
	 */
	public void rangedWeaponAttack(Creature other) {
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "Attack");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> ci = other.getNameAdjectiveKey("singular");
		HashMap<String, String> cc = weapon.getNameAndAdjective("singular");
		cc.put("key", weapon.getKey() + "_shoot");
		cc.put("type", "CCI");
		HashMap<String, String> ciData = other.getMorfData("singular");
		HashMap<String, String> ccData = weapon.getMorfData("singular");
		commonAttack(other, attackValue / 2 + weapon.getRangedAttackValue(), verb, null, null, ci, ciData, cc, ccData,
				"WeaponsAttacks", null);
	}

	/**
	 * Generates the text necessary and executes the damage in the objective creature
	 * @param other the objective Creature
	 * @param attack the value of attack from the attacker
	 * @param verb HashMap that contains data regarding the verb
	 * @param cd HashMap that contains data regarding the object
	 * @param cdData HashMap that contains morphological data regarding the object
	 * @param ci HashMap that contains data regarding the indirect object
	 * @param ciData HashMap that contains morphological data regarding the indirect object
	 * @param cc HashMap that contains data regarding the place in which the action takes place or the means by which the action was made
	 * @param ccData HashMap that contains morphological data regarding the place in which the action takes place or the means by which the action was made
	 * @param templateType String that describes the type of template that must be used by the Realizator
	 * @param item Item used in the attack in case learName is called
	 */
	private void commonAttack(Creature other, int attack, HashMap<String, String> verb, HashMap<String, String> cd,
			HashMap<String, String> cdData, HashMap<String, String> ci, HashMap<String, String> ciData,
			HashMap<String, String> cc, HashMap<String, String> ccData, String templateType, Item item) {

		int amount = Math.max(0, attack - other.defenseValue());
		String damPoints = getter.getDirectTranslation("Creature", "Damage points");

		amount = (int) (Math.random() * amount) + 1;

		HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
		HashMap<String, String> subjectData = this.getMorfData("singular");
		Restrictions res = null;
		if (verb.get("actionType").equals("Attack")) {
			//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CIGen, CINuum, CCGen, CCnum
			res = factoryR.getRestrictionsVbSujCiCc("singular", "third", "active", "present", subjectData.get("genere"),
					subjectData.get("number"), ciData.get("genere"), ciData.get("number"), ccData.get("genere"),
					ccData.get("number"));
			doActionComplex(verb, subject, cd, ci, cc, null, res, templateType);
		} else {
			//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum, CIgen, CINum
			res = factoryR.getRestrictionsVbSujCdCi("singular", "third", "active", "present", subjectData.get("genere"),
					subjectData.get("number"), cdData.get("genere"), cdData.get("number"), ciData.get("genere"),
					ciData.get("number"));
			doActionComplex(verb, subject, cd, ci, cc, null, res, templateType, item);
		}

		doAction(damPoints, amount);

		other.modifyHp(-amount,"Killed in battle");

		if (other.hp < 1) {
			gainXp(other);
		}

	}

	/**
	 * Increases or decreases the experience of the creature
	 * @param amount int to add or remove from the experience of the creature
	 */
	public void modifyXp(int amount) {
		xp += amount;
		String phrase, verb;

		if (amount < 0) {
			verb = getter.getDirectTranslation("Creature", "modifyXpLose");
		} else {
			verb = getter.getDirectTranslation("Creature", "modifyXpGain");
		}
		notify(verb, amount);

		while (xp > (int) (Math.pow(level, 1.5) * 20)) {
			phrase = getter.getDirectTranslation("Creature", "modifyXpLevelUp");
			level++;
			doAction(phrase, level);
			ai.onGainLevel();
			modifyHp(level * 2,"Extra HP");
		}
	}

	public void gainMaxHp() {
		String phrase = getter.getDirectTranslation("Creature", "gainMaxHp");
		maxHp += 10;
		hp += 10;
		doAction(phrase);
	}

	public void gainAttackValue() {
		String phrase = getter.getDirectTranslation("Creature", "gainAttackValue");
		attackValue += 2;
		doAction(phrase);
	}

	public void gainDefenseValue() {
		String phrase = getter.getDirectTranslation("Creature", "gainDefenseValue");
		defenseValue += 2;
		doAction(phrase);
	}

	public void gainVision() {
		String phrase = getter.getDirectTranslation("Creature", "gainVision");
		visionRadius += 1;
		doAction(phrase);
	}

	public void modifyVisionRadius(int value) {
		visionRadius += value;
	}

	public String getDetails() {
		String phrase = getter.getDirectTranslation("Creature", "getDetails");
		return String.format(phrase, level, attackValue(), defenseValue(), hp);
	}

	public Item getItem(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.item(wx, wy, wz);
		else
			return null;
	}

	private void regenerateHealth() {
		regenHpCooldown -= regenHpPer1000;
		if (regenHpCooldown < 0) {
			modifyHp(1,"Regeneration");
			regenHpCooldown += 1000;
		}
	}

	
	/**
	 * Obtains the gender of the creature and sets it's number as the one in the parameters
	 * @param num number to set
	 * @return HashMap containing gender and the number
	 */
	public HashMap<String, String> getMorfData(String num) {
		HashMap<String, String> data = new HashMap<>();
		data.put("genere", this.genere);
		data.put("number", num);
		return data;
	}

	/**
	 * Gets the name and adjective associated to the creature
	 * @param num Number in which is wished the name and the chatracteristic
	 * @return HashMap containing the name and the characteristic
	 */
	public HashMap<String, String> getNameAdjectiveKey(String num) {
		HashMap<String, String> data = new HashMap<>();
		data.put("key", this.key);
		if (num.equals("plural")) {
			data.put("name", this.n_plu);
			data.put("characteristic", this.charc_plu);
		} else {
			data.put("name", this.name);
			data.put("characteristic", this.characteristic);
		}
		return data;
	}

	/**
	 * Action of drinking potion items.
	 * @param item Item to quaff
	 * @param verbData HashMap with the information regarding to verb which is going to be used in the text generation
	 * @param templateType indicates the kind of template that Realizator must use
	 */
	public void quaff(Item item, HashMap<String, String> verbData, String templateType) {
		HashMap<String, String> subjectData = this.getMorfData(verbData.get("VbNum"));
		HashMap<String, String> itemData = item.getMorfData("singular");
		HashMap<String, String> subject = this.getNameAdjectiveKey(verbData.get("VbNum"));

		HashMap<String, String> itemNameAndAjective = item.getNameAndAdjective("singular");
		itemNameAndAjective.put("name", nameOf(item));

		//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum
		Restrictions res = factoryR.getRestrictionsVbSujCd(verbData.get("VbNum"), verbData.get("VbPerson"),
				verbData.get("VbForm"), verbData.get("VbTime"), subjectData.get("genere"), subjectData.get("number"),
				itemData.get("genere"), itemData.get("number"));
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", verbData.get("actionType"));
		verb.put("adverb", null);
		verb.put("Form", "Singular");

		doActionComplex(verb, subject, itemNameAndAjective, null, null, null, res, templateType, item);
		consumeItem(item);
	}
	
	
	/**
	 * Action of eating an item.
	 * @param item Item that is going to be eaten
	 */
	public void eat(Item item) {
		HashMap<String, String> subjectData = this.getMorfData("singular");
		HashMap<String, String> itemData = item.getMorfData("singular");
		HashMap<String, String> subject = this.getNameAdjectiveKey("singular");

		HashMap<String, String> itemNameAndAjective = item.getNameAndAdjective("singular");
		itemNameAndAjective.put("name", nameOf(item));

		//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen, CDnum
		Restrictions res = factoryR.getRestrictionsVbSujCd("singular", "third", "active", "present",
				subjectData.get("genere"), subjectData.get("number"), itemData.get("genere"), itemData.get("number"));
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "consume");
		verb.put("adverb", null);
		verb.put("Form", "Singular");

		doActionComplex(verb, subject, itemNameAndAjective, null, null, null, res, "BasicActionsTemplates");

		consumeItem(item);
	}

	/**
	 * The action that consumes the item
	 * @param item Item that is consumed
	 */
	private void consumeItem(Item item) {
		addEffect(item.getQuaffEffect(), this);
		modifyFood(item.getFoodValue());
		getRidOf(item);
	}

	/**
	 * Activates the Effect in this creature
	 * @param effect Effect that will start
	 * @param source Creature which is the source of the effect 
	 */
	private void addEffect(Effect effect, Creature source) {
		if (effect == null)
			return;

		effect.start(this, source);
		effects.add(effect);
	}

	/**
	 * Manages the current effects 
	 */
	private void updateEffects() {
		ArrayList<Effect> done = new ArrayList<Effect>();

		for (Effect effect : effects) {
			effect.update(this);
			if (effect.isDone()) {
				effect.end(this);
				done.add(effect);
			}
		}

		effects.removeAll(done);
	}

	/**
	 * The creature places the summoned beings created by the spell that targeted it
	 * @param source the creature that targeted this one
	 * @param others the creatures to be summoned
	 * @param ccData HashMap that contains morphological data regarding the place in which the action takes place or the means by which the action was made
	 * @param cc HashMap that contains data regarding the place in which the action takes place or the means by which the action was made
	 * @param ciData HashMap that contains morphological data regarding the indirect object
	 * @param CI HashMap that contains data regarding the indirect object
	 * @param verbData HashMap that contains data regarding the verb
	 * @param templateType indicates the kind of template that Realizator must use
	 * @param item Item which the source used for the spell
	 */
	public void placeSummoned(Creature source, ArrayList<Creature> others, HashMap<String, String> ccData,
			HashMap<String, String> cc, HashMap<String, String> ciData, HashMap<String, String> CI,
			HashMap<String, String> verbData, String templateType, Item item) {
		for (Creature other : others) {
			world.add(other);
		}
		HashMap<String, String> subjectData = source.getMorfData(verbData.get("VbNum"));
		HashMap<String, String> subject = source.getNameAdjectiveKey(verbData.get("VbNum"));
		//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CiGen, Cinum, ccgen, ccnum
		Restrictions res = factoryR.getRestrictionsVbSujCiCc(verbData.get("VbNum"), verbData.get("VbPerson"),
				verbData.get("VbForm"), verbData.get("VbTime"), subjectData.get("genere"), subjectData.get("number"),
				ciData.get("genere"), ciData.get("number"), ccData.get("genere"), ccData.get("number"));

		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", verbData.get("actionType"));
		verb.put("adverb", null);
		this.doActionComplex(verb, subject, null, CI, cc, null, res, templateType, item);

	}

	/**
	 * With this the creature can cast spells
	 * @param spell Spell which is going to be casted
	 * @param x2 coordinate x of the objective
	 * @param y2 coordinate y of the objective
	 */
	public void castSpell(Spell spell, int x2, int y2) {
		Creature other = creature(x2, y2, z);
		String phrase = "";
		if (spell.getManaCost() > mana) {
			phrase = getter.getDirectTranslation("Creature", "nothingHappens");
			doAction(phrase);
			return;
		} else if (other == null) {
			phrase = getter.getDirectTranslation("Creature", "atNothing");
			doAction(phrase);
			return;
		}

		other.addEffect(spell.getEffect(), this);
		modifyMana(-spell.getManaCost());
	}

	public String nameOf(Item item) {
		return ai.getName(item);
	}

	/**
	 * This function manages the learning of items such as potions which are only know ate the beggining by their aspect. 
	 * After using it the creature will know it by it's effect
	 * @param item Item to be learned
	 */
	public void learnName(Item item) {
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "beSomething");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> subject = item.getNameAndAdjective("singular");
		subject.put("name", item.getAppearance());
		HashMap<String, String> subjectData = item.getMorfData("singular");
		HashMap<String, String> atribute = item.getNameAndAdjective("singular");
		HashMap<String, String> atrData = item.getMorfData("singular");
		//VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, Atrgen, atrnum
		Restrictions res = factoryR.getRestrictionsVbSujAtr("singular", "third", "active", "pasive",
				subjectData.get("genere"), subjectData.get("number"), atrData.get("genere"), atrData.get("number"));
		String phrase = getNotification(verb, subject, null, null, null, atribute, res, "ToBeSomething");
		notify(phrase);
		ai.setName(item, item.getName());
	}

	public String getKey() {
		return key;
	}

	public String getGenere() {
		return genere;
	}

	public String getCharc_plu() {
		return charc_plu;
	}

	public String getN_plu() {
		return n_plu;
	}

}
