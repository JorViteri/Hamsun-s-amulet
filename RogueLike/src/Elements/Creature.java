package Elements;

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

	
	//TODO cambiar los gettrs y setters de esta clase!!
	private World world;
	private CreatureAi ai;
	private Position pos;
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
	}

	public void modifyAttackValue(int value) {
		attackValue += value;
	}

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

	public void gainXp(Creature other) {
		int amount = other.maxHp + other.attackValue() + other.defenseValue() - level * 2;

		if (amount > 0) {
			modifyXp(amount);
		}
	}

	public void modifyHp(int amount, String causeofDeath) {
		hp += amount;
		//this.causeOfDeath = causeOfDeath;
		if (hp > maxHp) {
			hp = maxHp;
		} else if (hp < 1) {
			
			RestrictionsFactory factory = RestrictionsFactory.getInstance();
			HashMap<String, String> subjectData = this.getMorfData("singular");
			HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
			HashMap<String, String> verb = new HashMap<>();
			verb.put("actionType", "die");
			verb.put("adverb", null);
			Restrictions res = factory.getRestrictions("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), null,
					null, null, null, null, null, null, null);
			
			doActionComplex(verb, subject, null, null, null, null,res, "MostBasicTemplate");
			leaveCorpse();
			world.remove(this);
		}
	}

	private void leaveCorpse() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		Realizator realizator = factory.getRealizator();
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

	public void unequip(Item item) {
		if (item == null)
			return;

		if (item == armor) {
			doAction("remove a " + nameOf(item));
			armor = null;
		} else if (item == weapon) {
			doAction("put away a " + nameOf(item));
			weapon = null;
		}
	}

	public void equip(Item item) {
		if (!inventory.contains(item)) {
			if (inventory.isFull()) {
				notify("Can't equip %s since you're holding too much stuff.", nameOf(item));
				return;
			} else {
				world.remove(item);
				inventory.add(item);
			}
		}

		if (item.getAttackValue() == 0 && item.getRangedAttackValue() == 0 && item.getDefenseValue() == 0)
			return;

		if (item.getAttackValue() + item.getRangedAttackValue() >= item.getDefenseValue()) {
			unequip(weapon);
			if(this.key.equals("Player")){
				doAction("wield a " + nameOf(item));
			}
			weapon = item;
		} else { //TODO el equipar se me ha petado ;/
			unequip(armor);
			if(this.key.equals("Player")){ //TODO me ha petado al pasar campo nombre a key nani
				doAction("put on a " + nameOf(item)); 
			}
			armor = item;
		}
	}

	public void moveBy(int mx, int my, int mz) {
		WordDataGetterAndRealizatorFactory factoryG = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factoryG.getWordDataGetter();
		Tile tile = world.tile(x + mx, y + my, z);
		Staircase stair;
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
				//doAction("walk up the stairs to level %d", numLevel);
				pos_x = stair.getBeginning().getIntX();
				pos_y = (world.getHeight() - 1) - stair.getBeginning().getIntY();
				pos_z = stair.getBeginning().getZ();
				
				
				RestrictionsFactory factory = RestrictionsFactory.getInstance();
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
				cc.put("characteristic", Integer.toString(numLevel)+"º");
				cc.put("type", "CCL");
				Restrictions res = factory.getRestrictions("singular", "third", "active", "present",
						subjectData.get("genere"), subjectData.get("number"), tileData.get("genere"),
						tileData.get("number"), null, null, ccData.get("genere"), ccData.get("number"), null, null);
				doActionComplex(verb, subject, tileNameAdj, null, cc, null, res, "ChangeDungeonLevel");

				tile = world.tile(stair.getBeginning().getIntX(), (world.getHeight() - 1) - stair.getBeginning().getIntY(),
						stair.getBeginning().getZ());
			} else {
				doAction("try to go up but are stopped by the cave ceiling"); //TODO traduccion simple
				return;
			}
		} else if (mz == 1) {
			if (tile == Tile.STAIRS_DOWN) {
				
				
				stair = world.getStairs().get(z);
				pos_x = stair.getEnding().getIntX();
				pos_y = (world.getHeight() - 1) - stair.getEnding().getIntY();
				pos_z = stair.getEnding().getZ();
				numLevel = z + mz + 1;
				
				
				RestrictionsFactory factory = RestrictionsFactory.getInstance();
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
 				cc.put("characteristic", Integer.toString(numLevel)+"º");
 				cc.put("type", "CCL");
				Restrictions res = factory.getRestrictions("singular", "third", "active", "present",
						subjectData.get("genere"), subjectData.get("number"), tileData.get("genere"),
						tileData.get("number"), null, null, ccData.get("genere"), ccData.get("number"), null, null);
				doActionComplex(verb, subject, tileNameAdj, null, cc, null, res, "ChangeDungeonLevel");
				
				//doAction("walk down the stairs to level %d", numLevel);
				tile = world.tile(stair.getEnding().getIntX(), (world.getHeight() - 1) - stair.getEnding().getIntY(),
						stair.getEnding().getZ());
				
				
			} else {
				doAction("try to go down but are stopped by the cave floor"); //TODO traduccion simple
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

	public void moveTo(Position p) {
		// this.ai.onStep(p);
	}

	public void dig(int wx, int wy, int wz) {
		world.dig(wx, wy, wz);
	}

	public void update() {
		regenerateHealth();
		regenerateMana();
		updateEffects();
		ai.onUpdate();
	}

	public Position getPosition() {
		return new Position(this.x,this.y,this.z);
	}

	public void modifyRegenHpPer1000(int amount) {
		regenHpPer1000 += amount;
	}


	public void doAction(String message, Object... params) {
		for (Creature other : getCreaturesWhoSeeMe()) {
			other.notify(message, params);
		}
	}

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
	
	//doAction en el caso de que haya items de por medio
	public void doActionComplex(HashMap<String, String> verb, HashMap<String, String> Subject,
			HashMap<String, String> CD, HashMap<String, String> CI, HashMap<String, String> CC,
			HashMap<String, String> Atribute, Restrictions res, String templateType, Item item) {

		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		Realizator realizator = factory.getRealizator();
		String phrase = realizator.realizatePhrase(verb, Subject, CD, CI, CC, Atribute, res, templateType);
		for (Creature other : getCreaturesWhoSeeMe()) {
			other.notify(phrase);
			other.learnName(item);
		}
		//learnName(item);
	}
	
	//doAction para cuando no hay items de por medio
	public void doActionComplex(HashMap<String, String> verb, HashMap<String, String> Subject,
			HashMap<String, String> CD, HashMap<String, String> CI, HashMap<String, String> CC,
			HashMap<String, String> Atribute, Restrictions res, String templateType) {
		
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		Realizator realizator = factory.getRealizator();
		String phrase = realizator.realizatePhrase(verb, Subject, CD, CI, CC, Atribute, res, templateType);
		for (Creature other : getCreaturesWhoSeeMe()) {
			other.notify(phrase);

		}
	}
	
	public String getNotification(HashMap<String, String> verb, HashMap<String, String> Subject,
			HashMap<String, String> CD, HashMap<String, String> CI, HashMap<String, String> CC,
			HashMap<String, String> Atribute, Restrictions res, String templateType) {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		Realizator realizator = factory.getRealizator();
		return realizator.realizatePhrase(verb, Subject, CD, CI, CC, Atribute, res, templateType);
	}

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
	
	public ArrayList<ArrayList<Position>> getVisibleThings() {
		ArrayList<ArrayList<Position>> result = new ArrayList<>();
		ArrayList<Position> creatures = new ArrayList<>();
		ArrayList<Position> items = new ArrayList<>();
		ArrayList<Position> stairs = new ArrayList<>();
		Position p = null;
		int r = this.visionRadius;
		for (int ox = -r; ox < r + 1; ox++) {
			for (int oy = -r; oy < r + 1; oy++) {
				if (ox * ox + oy * oy > r * r){
					continue;
				}
				if (this.canSee(x+ox, y+oy, z)){ 
					p = new Position(x + ox, y + oy, z);
					if (p.isValidPosition()){
						Creature otherCreature = world.creature(x + ox, y + oy, z);
						Item otherItem = world.item(x + ox, y + oy, z); 
						Tile tile = world.tile(x + ox, y + oy, z);
						if ((otherCreature != null)&&(!otherCreature.equals(this))){
							creatures.add(p);
						} else if (otherItem!=null){
							items.add(p);
						} else if (tile.isStair()){
							stairs.add(p);
						} else{
							continue;
						}
					}
				}
				
			}
		}
		result.add(creatures);
		result.add(items);
		result.add(stairs);
		return result;
	}
	
	// pasa la frase a segunda persona TODO IMPORTANTE PARA MI PAETE JODIDA DE
	// WORNDET
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

	public boolean canSee(int wx, int wy, int wz) {  //TODO esta funcion debe tener la solucion
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

	public void pickup() {
		Item item = world.item(x, y, z);

		if (inventory.isFull() || item == null) {
			WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
			WordDataGetter getter = factory.getWordDataGetter();
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
			RestrictionsFactory factory = RestrictionsFactory.getInstance();
			Restrictions res = factory.getRestrictions("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), cdData.get("genere"), cdData.get("number"),
					null, null, null, null, null, null);
			doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");
			world.remove(x, y, z);
			inventory.add(item);
		}
	}

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
			RestrictionsFactory factory = RestrictionsFactory.getInstance();
			Restrictions res = factory.getRestrictions("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), cdData.get("genere"), cdData.get("number"),
					null, null, null, null, null, null);
			doActionComplex(verb, subject, cd, null, null, null, res, "BasicActionsTemplates");			
			inventory.remove(item);
			unequip(item);
		} else {
			WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
			WordDataGetter getter = factory.getWordDataGetter();
			String phrase = getter.getDirectTranslation("Creature", "Cant drop");
			doAction(phrase);
		}
	}

	private void getRidOf(Item item) {
		inventory.remove(item);
		unequip(item);
	}

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
			RestrictionsFactory factory = RestrictionsFactory.getInstance();
			Restrictions res = factory.getRestrictions("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), cdData.get("genere"), cdData.get("number"),
					null, null, null, null, null, null);
			doActionComplex(verb, subject, cd, null, null, null,res, "BasicActionsTemplates");
		}

		putAt(item, wx, wy, wz);
	}

	public void meleeAttack(Creature other) {
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "Attack");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> ci = other.getNameAdjectiveKey("singular");
		HashMap<String, String> cc = new HashMap<>();
		HashMap<String, String> ccData = new HashMap<>();
		if (weapon!= null){
			cc = weapon.getNameAndAdjective("singular");
			ccData = weapon.getMorfData("singular");
			cc.put("key", weapon.getKey());
			cc.put("type", "CCI");
		} else{
			WordDataGetterAndRealizatorFactory factoryG = WordDataGetterAndRealizatorFactory.getInstance();
			WordDataGetter getter = factoryG.getWordDataGetter();
			HashMap<String, String> ccExtraData = getter.getNounData("body"); 
			
			ccData.put("number", "singular");
			ccData.put("genere", ccExtraData.get("genere"));
			cc.put("name", ccExtraData.get("baseNoun"));
			cc.put("key", "body");
			cc.put("characteristic","");
			cc.put("type", "CCI");
		}
		
		HashMap<String, String> ciData = other.getMorfData("singular");
		
		commonAttack(other, attackValue(), verb, null, null, ci, ciData, cc, ccData, "WeaponsAttacks", null);
	}

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
		other.addEffect(item.getQuaffEffect());
		this.learnName(item);
	}

	public void rangedWeaponAttack(Creature other) {
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "Attack");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> ci = other.getNameAdjectiveKey("singular");
		HashMap<String, String> cc = weapon.getNameAndAdjective("singular");
		cc.put("key", weapon.getKey()+"_shoot");
		cc.put("type", "CCI");
		HashMap<String, String> ciData = other.getMorfData("singular");
		HashMap<String, String> ccData = weapon.getMorfData("singular");
		commonAttack(other, attackValue / 2 + weapon.getRangedAttackValue(), verb, null, null, ci, ciData, cc, ccData,
				"WeaponsAttacks", null);
	}

	private void commonAttack(Creature other, int attack, HashMap<String, String> verb, HashMap<String, String> cd,
			HashMap<String, String> cdData, HashMap<String, String> ci, HashMap<String, String> ciData,
			HashMap<String, String> cc, HashMap<String, String> ccData, String templateType, Item item) {

		int amount = Math.max(0, attack - other.defenseValue());
		WordDataGetterAndRealizatorFactory factoryG = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factoryG.getWordDataGetter();
		String damPoints = getter.getDirectTranslation("Creature", "Damage points");

		amount = (int) (Math.random() * amount) + 1;

		HashMap<String, String> subject = this.getNameAdjectiveKey("singular");
		HashMap<String, String> subjectData = this.getMorfData("singular");
		RestrictionsFactory factory = RestrictionsFactory.getInstance();
		Restrictions res = null;
		if (verb.get("actionType").equals("Attack")) {
			res = factory.getRestrictions("singular", "third", "active", "present", subjectData.get("genere"),
					subjectData.get("number"), null, null, ciData.get("genere"), ciData.get("number"),
					ccData.get("genere"), ccData.get("number"), null, null);
			doActionComplex(verb, subject, cd, ci, cc, null, res, templateType);
		} else {
			res = factory.getRestrictions("singular", "third", "active", "present", subjectData.get("genere"),
					subjectData.get("number"), cdData.get("genere"), cdData.get("number"), ciData.get("genere"),
					ciData.get("number"), null, null, null, null);
			doActionComplex(verb, subject, cd, ci, cc, null, res, templateType, item);
		}
		
		doAction(damPoints, amount);
		
		other.modifyHp(-amount, "Killed in battle");

		if (other.hp < 1) {
			gainXp(other);
		}

	}

	public void modifyXp(int amount) {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		xp += amount;
		String phrase, verb;
		
		if (amount<0){
			verb = getter.getDirectTranslation("Creature", "modifyXpLose");
		} else {
			verb = getter.getDirectTranslation("Creature", "modifyXpGain");
		}
		notify(verb, amount); //Indica los puntos de experiencia ganados o perdidos, doComplex??

		while (xp > (int) (Math.pow(level, 1.5) * 20)) {
			phrase = getter.getDirectTranslation("Creature", "modifyXpLevelUp");
			level++;
			doAction(phrase, level);
			ai.onGainLevel();
			modifyHp(level * 2,"Extra HP");
		}
	}

	public void gainMaxHp() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		String phrase = getter.getDirectTranslation("Creature", "gainMaxHp");
		maxHp += 10;
		hp += 10;
		doAction(phrase);
	}

	public void gainAttackValue() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		String phrase = getter.getDirectTranslation("Creature", "gainAttackValue");
		attackValue += 2;
		doAction(phrase);
	}

	public void gainDefenseValue() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		String phrase = getter.getDirectTranslation("Creature", "gainDefenseValue");
		defenseValue += 2;
		doAction(phrase);
	}

	public void gainVision() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		String phrase = getter.getDirectTranslation("Creature", "gainVision");
		visionRadius += 1;
		doAction(phrase);
	}

	public void modifyVisionRadius(int value) {
		visionRadius += value;
	}

	public String getDetails() {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
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
	
	//TODO el problema es que llamo a esto si so si por los restrictions en español
	//no lo planifique bien, asi que estoy llamando a esto si o si algo mas abajo en codigo que no deberia tratarse con el texto
	// es decir, la creacion de las restricciones no deberia ser e esta capa
	public HashMap<String, String> getMorfData(String num){ // TODO en base a la frase se establece el numero de la palabra
		HashMap<String, String> data = new HashMap<>();
		data.put("genere", this.genere); //TODO, esto no vale por el ingles :)))))))))))))))))))))))))))) o no hay que llamarlo? no, en inlges no hacce falta
		data.put("number", num);
		return data;
	}
	
	public HashMap<String, String> getNameAdjectiveKey(String num){
		HashMap<String, String> data = new HashMap<>();
		data.put("key", this.key);
		if(num.equals("plural")){
			data.put("name", this.n_plu);
			data.put("characteristic", this.charc_plu);
		} else{
			data.put("name", this.name);
			data.put("characteristic", this.characteristic);
		}
		return data;
	}

	public void quaff(Item item, HashMap<String, String> verbData, String templateType) { //TODO algun modo de indicar que no ha surtido effecto alguno...
		RestrictionsFactory factory = RestrictionsFactory.getInstance();
		HashMap<String, String> subjectData = this.getMorfData(verbData.get("VbNum")); //Esto deberia decidirse junto al verbo en el quaff screen o donde se llame a esto
		HashMap<String, String> itemData = item.getMorfData("singular");
		HashMap<String, String> subject = this.getNameAdjectiveKey(verbData.get("VbNum"));

		HashMap<String, String> itemNameAndAjective = item.getNameAndAdjective("singular");
		itemNameAndAjective.put("name", nameOf(item));

		Restrictions res = factory.getRestrictions(verbData.get("VbNum"), verbData.get("VbPerson"),
				verbData.get("VbForm"), verbData.get("VbTime"), subjectData.get("genere"), subjectData.get("number"),
				itemData.get("genere"), itemData.get("number"), null, null, null, null, null, null);
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", verbData.get("actionType"));
		verb.put("adverb", null);
		verb.put("Form", "Singular");

		doActionComplex(verb, subject, itemNameAndAjective, null, null, null, res, templateType, item);
		consumeItem(item);
	}

	public void eat(Item item) { //TODO al final voy a definir esos HashMaps como variables privadas de la clase y atpc
		RestrictionsFactory factory = RestrictionsFactory.getInstance();
		HashMap<String, String> subjectData = this.getMorfData("singular"); 
		HashMap<String, String> itemData = item.getMorfData("singular");
		HashMap<String, String> subject = this.getNameAdjectiveKey("singular");

		HashMap<String, String> itemNameAndAjective = item.getNameAndAdjective("singular");
		itemNameAndAjective.put("name", nameOf(item));

		Restrictions res = factory.getRestrictions("singular", "third",
				"active", "present", subjectData.get("genere"), subjectData.get("number"), 
				itemData.get("genere"), itemData.get("number"), null, null, null, null, null, null);
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "consume");
		verb.put("adverb", null);
		verb.put("Form", "Singular");

		doActionComplex(verb, subject, itemNameAndAjective, null, null, null, res, "BasicActionsTemplates");
		
		consumeItem(item);
	}

	private void consumeItem(Item item) {
		addEffect(item.getQuaffEffect());

		modifyFood(item.getFoodValue());
		getRidOf(item);
	}

	private void addEffect(Effect effect) {
		if (effect == null)
			return;

		effect.start(this);
		effects.add(effect);
	}

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

	public void summon(ArrayList<Creature> others, HashMap<String, String> ccData, HashMap<String, String> cc,
			HashMap<String, String> ciData, HashMap<String, String> CI, HashMap<String, String> verbData,
			String templateType, Item item) {
		for (Creature other : others) {
			world.add(other);
		}
		RestrictionsFactory factory = RestrictionsFactory.getInstance();
		HashMap<String, String> subjectData = this.getMorfData(verbData.get("VbNum"));
		HashMap<String, String> subject = this.getNameAdjectiveKey(verbData.get("VbNum"));
		Restrictions res = factory.getRestrictions(verbData.get("VbNum"), verbData.get("VbPerson"),
				verbData.get("VbForm"), verbData.get("VbTime"), subjectData.get("genere"), subjectData.get("number"),
				null, null, ciData.get("genere"), ciData.get("number"), ccData.get("genere"), ccData.get("number"), null, null);
		
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", verbData.get("actionType"));
		verb.put("adverb", null);
		this.doActionComplex(verb, subject, null, CI, cc, null, res, templateType, item);

	}

	// TODO CHECK CODE, IT MIGHT FAIL 
	//Sale a una pantalla de TARGET cuando no debria ser necesario si el objetivo es el jugador TODO
	public void castSpell(Spell spell, int x2, int y2) {
		Creature other = creature(x2, y2, z);

		if (spell.getManaCost() > mana) {
			doAction("point and mumble but nothing happens");
			return;
		} else if (other == null) {
			doAction("point and mumble at nothing");
			return;
		}

		other.addEffect(spell.getEffect());
		modifyMana(-spell.getManaCost());
	}

	public String nameOf(Item item) {
		return ai.getName(item);
	}

	public void learnName(Item item) { 
		//doAction("LEARNEDOCWIEDNNCODNIOSNI"); //cuantas veces escribe esto?
		RestrictionsFactory factory = RestrictionsFactory.getInstance();
		HashMap<String, String> verb = new HashMap<>();
		verb.put("actionType", "beSomething");
		verb.put("adverb", null);
		verb.put("Form", "Singular");
		HashMap<String, String> subject = item.getNameAndAdjective("singular");
		subject.put("name", item.getAppearance());
		HashMap<String, String> subjectData = item.getMorfData("singular");
		HashMap<String, String> atribute = item.getNameAndAdjective("singular");
		HashMap<String, String> atrData = item.getMorfData("singular");
		Restrictions res = factory.getRestrictions("singular", "third", "active", "pasive", subjectData.get("genere"),
				subjectData.get("number"), null, null, null, null, null, null, atrData.get("genere"),
				atrData.get("number"));
		String phrase  = getNotification(verb, subject, null, null, null, atribute, res, "ToBeSomething");
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
