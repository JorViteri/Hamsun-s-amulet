package CreaturesAI;

/**
 * Defines the basic AI behaviours for the creatures
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DungeonComponents.Line;
import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import Utils.LevelUpController;
import Utils.Path;
import Utils.Position;

public class CreatureAi {

	protected Creature creature;
	/**
	 * Names of the items that know
	 */
	private Map<String, String> itemNames;
	
	public CreatureAi(Creature creature){
		this.creature = creature;
		this.creature.setCreatureAi(this);
		this.itemNames = new HashMap<String, String>();

	}
	
	public void onEnter(int x, int y, int z, Tile tile){
		if(tile.isGround()){
			creature.setX(x);
			creature.setY(y);
			creature.setZ(z);
		} else{
			creature.doAction("bump into a wall");
		}
	}
	
	/**
	 * Moves the creatures of the dungeon
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

	
	public Tile rememberedTile(int wx, int wy, int wz) {
        return Tile.UNKNOWN;
    }

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

	protected boolean canRangedWeaponAttack(Creature other) {
		return creature.getWeapon() != null && creature.getWeapon().getRangedAttackValue() > 0
				&& creature.canSee(other.getX(), other.getY(), other.getZ());
	}

	protected boolean canThrowAt(Creature other) { 
		return creature.canSee(other.getX(), other.getY(), other.getZ()) && getWeaponToThrow() != null;
	}

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
	
	public String getName(Item item){
		String name = itemNames.get(item.getKey()); 
		return name == null ? item.getCharacteristic()+item.getAppearance() : item.getCharacteristic()+name;
	}
	
	public void setName(Item item, String name){
		itemNames.put(item.getKey(), name);
	}
	
}
