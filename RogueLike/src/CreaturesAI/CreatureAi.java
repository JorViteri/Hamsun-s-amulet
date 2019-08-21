package CreaturesAI;

/**
 * Defines the basic AI behaviours for the creatures
 * 
 * @author comec
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DungeonComponents.Line;
import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import TextManagement.Restrictions;
import TextManagement.RestrictionsFactory;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import Utils.LevelUpController;
import Utils.Path;
import Utils.Position;

public class CreatureAi {

	protected Creature creature;
	/**
	 * Names of the items that the creature knows
	 */
	private Map<String, String> itemNames;
	
	public CreatureAi(Creature creature){
		this.creature = creature;
		this.creature.setCreatureAi(this);
		this.itemNames = new HashMap<String, String>();

	}
	
	/**
	 * Allows the creature to access a new position if it's ground. In the case
	 * It's ground, the creature mantains it's position and a message is shown.
	 * @param x Int that indicates the coordinate in x axis.
	 * @param y Int that indicates the coordinate in y axis.
	 * @param z Int that indicates the coordinate in z axis.
	 * @param tile The tile which the creatur is trying to access.
	 */
	public void onEnter(int x, int y, int z, Tile tile){
		if(tile.isGround()){
			creature.setX(x);
			creature.setY(y);
			creature.setZ(z);
		} else{
			WordDataGetterAndRealizatorFactory factoryG = WordDataGetterAndRealizatorFactory.getInstance();
			WordDataGetter getter = factoryG.getWordDataGetter();
			RestrictionsFactory factory = RestrictionsFactory.getInstance();
			HashMap<String, String> subjectData = creature.getMorfData("singular");
			HashMap<String, String> subject = creature.getNameAdjectiveKey("singular");
			HashMap<String, String> verb = new HashMap<>();
			verb.put("actionType", "collide");
			verb.put("adverb", null);
			HashMap<String, String> ccThing = getter.getNounData("wall");
			HashMap<String, String> ccData = new HashMap<>();
			ccData.put("genere", ccThing.get("genere"));
			ccData.put("number", "singular");
			HashMap<String, String> cc =  new HashMap<>();
			cc.put("name", ccThing.get("baseNoun"));
			cc.put("type", "CCI");
			cc.put("characteristic", "");
			
			Restrictions res = factory.getRestrictions("singular", "third", "active", "present",
					subjectData.get("genere"), subjectData.get("number"), null,
					null, null, null, ccData.get("genere"), ccData.get("number"), null, null);
			
			creature.doActionComplex(verb, subject, null, null, cc, null, res, "CollideWall");
			//creature.doAction("bump into a wall"); //TODO es una accion de CCI
		}
	}
	
	/**
	 * Moves the creatures through the dungeon
	 */
	public void wander(){
		int mx = (int)(Math.random()*3)-1;
		int my = (int)(Math.random()*3)-1;

		Creature other = creature.creature(creature.getX() + mx, creature.getY() + my, creature.getZ());
	    
	    if (other != null && other.glyph() == creature.glyph())
	        return;
	    else
	        creature.moveBy(mx, my, 0);
	}
	
	public void onUpdate(){
		
	}
	
	public void onNotify(String message){
		
	}
	
	public void onGainLevel() {
		new LevelUpController().autoLevelUp(creature);
	}
	
	/**
	 * Checks if the localization given the coordinates is visible
	 * @param wx
	 * @param wy
	 * @param wz
	 * @return true if it's visible, false the other case
	 */
	public boolean canSee(int wx, int wy, int wz) {
		if (creature.getZ() != wz)
			return false;
		
		if ((creature.getX()-wx)*(creature.getX()-wx) + (creature.getY()-wy)*(creature.getY()-wy) > creature.visionRadius()*creature.visionRadius())
			return false;
		
		for (Position p : new Line(creature.getX(), creature.getY(), wx, wy)){
			if (creature.realTile(p.getIntX(), p.getIntY(), wz).isGround() || p.getIntX() == wx && p.getIntY() == wy)
				continue;
			
			return false;
		}
		
		return true;
	}

	
	/**
	 * Checks if the creature knows the tile. By default, the function 
	 * returns unknown
	 * 
	 * @param wx coordinate in x.
	 * @param wy coordinate in y.
	 * @param wz coordintae in z.
	 * @return Tile.UNKNOWN The tile is not known by the creature.
	 */
	public Tile rememberedTile(int wx, int wy, int wz) {
        return Tile.UNKNOWN;
    }

	/**
	 * The creature follows the target creature. It relies in a Path Finding algorithm.
	 * 
	 * @param target Objective creature.
	 */
	
	public void hunt(Creature target) {
		int mx, my;
		
		List<Position> positions = new Path(creature, target.getX(), target.getY()).getPositions();
		if (positions==null||positions.isEmpty()){
			return;
		}
		
		mx = positions.get(0).getIntX() - creature.getX();
		my = positions.get(0).getIntY() - creature.getY();
	
		creature.moveBy(mx, my, 0);
	}
	
	/**
	 * Checks if It's possible to attack the other creature with a ranged weapon.
	 * 
	 * @param other Possible objective creature.
	 * @return Boolean True if It's possible, false the other case
	 */
	protected boolean canRangedWeaponAttack(Creature other) {
		return creature.getWeapon() != null && creature.getWeapon().getRangedAttackValue() > 0
				&& creature.canSee(other.getX(), other.getY(), other.getZ());
	}

	/**
	 * Checks if It's possible to throw something to the other creature.
	 * 
	 * @param other Possible objective creature.
	 * @return Boolean True if It's possible, false the other case
	 */
	protected boolean canThrowAt(Creature other) { 
		return creature.canSee(other.getX(), other.getY(), other.getZ()) && getWeaponToThrow() != null;
	}

	/**
	 * Obtains an item to throw.
	 * @return Item Item that is going to be throw.
	 */
	protected Item getWeaponToThrow() { 
		Item toThrow = null;
	
		for (Item item : creature.inventory().getItems()) {
			if (item == null || creature.getWeapon() == item || creature.getArmor() == item)
				continue;
	
			if (toThrow == null || item.getThrownAttackValue() > toThrow.getAttackValue())
				toThrow = item;
		}
	
		return toThrow;
	}

	/**
	 * Checks if it's possible to pick an item.
	 * @return Boolean true if It's possible. Returns false in the other case.
	 */
	protected boolean canPickup() {
		return creature.item(creature.getX(), creature.getY(), creature.getZ()) != null && !creature.inventory().isFull();
	}
	
	protected boolean canUseBetterEquipment() {
		int currentWeaponRating = creature.getWeapon() == null ? 0
				: creature.getWeapon().getAttackValue() + creature.getWeapon().getRangedAttackValue();
		int currentArmorRating = creature.getArmor() == null ? 0 : creature.getArmor().getDefenseValue();

		for (Item item : creature.inventory().getItems()) {
			if (item == null)
				continue;

			boolean isArmor = item.getAttackValue() + item.getRangedAttackValue() < item.getDefenseValue();

			if (item.getAttackValue() + item.getRangedAttackValue() > currentWeaponRating
					|| isArmor && item.getDefenseValue() > currentArmorRating)
				return true;
		}

		return false;
	}

	/**
	 *  Allows the creature to put better equipement
	 */
	protected void useBetterEquipment() {
		int currentWeaponRating = creature.getWeapon() == null ? 0
				: creature.getWeapon().getAttackValue() + creature.getWeapon().getRangedAttackValue();
		int currentArmorRating = creature.getArmor() == null ? 0 : creature.getArmor().getDefenseValue();

		for (Item item : creature.inventory().getItems()) {
			if (item == null)
				continue;

			boolean isArmor = item.getAttackValue() + item.getRangedAttackValue() < item.getDefenseValue();

			if (item.getAttackValue() + item.getRangedAttackValue() > currentWeaponRating
					|| isArmor && item.getDefenseValue() > currentArmorRating) {
				creature.equip(item);
			}
		}
	}
	
	/**
	 * Obtains the name of the item if It's known, if not returns the 
	 * key of the item.
	 * @param item
	 * @return
	 */
	public String getName(Item item){
		String name = itemNames.get(item.getKey());  //TODO lo de los espacios no me vale, eso deberia anhadirlo el realizator segun se necesite o no
		return name == null ? item.getAppearance() : name;
	}
	
	
	/**
	 * Sets a name for a item that was known by It's key.
	 * @param item
	 * @param name
	 */
	public void setName(Item item, String name){
		itemNames.put(item.getKey(), name);
	}
	
}
