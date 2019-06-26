package CreaturesAI;

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
	private Map<String, String> itemNames;
	
	public CreatureAi(Creature creature){
		this.creature = creature;
		this.creature.setCreatureAi(this);
		this.itemNames = new HashMap<String, String>();

	}
	
	public void onEnter(int x, int y, int z, Tile tile){
		if(tile.isGround()){
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else{
			creature.doAction("bump into a wall");
		}
	}
	
	public void wander(){
		int mx = (int)(Math.random()*3)-1;
		int my = (int)(Math.random()*3)-1;

		Creature other = creature.creature(creature.x + mx, creature.y + my, creature.z);
	    
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
	
	public boolean canSee(int wx, int wy, int wz) {
		if (creature.z != wz)
			return false;
		
		if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
			return false;
		
		for (Position p : new Line(creature.x, creature.y, wx, wy)){
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
		
		List<Position> positions = new Path(creature, target.x, target.y).getPositions();
		if (positions==null||positions.isEmpty()){
			return;
		}
		
		mx = positions.get(0).getIntX() - creature.x;
		my = positions.get(0).getIntY() - creature.y;
	
		creature.moveBy(mx, my, 0);
	}

	protected boolean canRangedWeaponAttack(Creature other) {
		return creature.getWeapon() != null && creature.getWeapon().getRangedAttackValue() > 0
				&& creature.canSee(other.x, other.y, other.z);
	}

	protected boolean canThrowAt(Creature other) {
		return creature.canSee(other.x, other.y, other.z) && getWeaponToThrow() != null;
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
		return creature.item(creature.x, creature.y, creature.z) != null && !creature.inventory().isFull();
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
		String name = itemNames.get(item.getName()); 
		return name == null ? item.getAppearance() : name;
	}
	
	public void setName(Item item, String name){
		itemNames.put(item.getName(), name);
	}
	
}
