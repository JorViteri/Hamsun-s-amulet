package Factories;

import Elements.Item;
import Rogue.World;
import asciiPanel.AsciiPanel;

public class WeaponsFactory {

	private World world;
	
	public WeaponsFactory(World world){
		this.world = world;
	}
	
	public Item newDagger(int depth) {
		Item item = new Item(')', AsciiPanel.white, "dagger","dagger", null);
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newSword(int depth) {
		Item item = new Item(')', AsciiPanel.brightWhite, "sword", "sword", null);
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth) {
		Item item = new Item(')', AsciiPanel.yellow, "staff", "staff", null);
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newBow(int depth){
		Item item = new Item(')', AsciiPanel.yellow, "bow", "bow", null);
        item.modifyAttackValue(1);
        item.modifyRangedAttackValue(5);
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
}
