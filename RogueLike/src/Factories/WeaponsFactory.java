package Factories;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import Elements.Item;
import Rogue.World;
import Utils.NameSynonymsGetter;
import asciiPanel.AsciiPanel;

public class WeaponsFactory {

	private World world;
	private NameSynonymsGetter getter;
	
	public WeaponsFactory(World world){
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
	
	public Item newDagger(int depth) {
		String[] arr = {"red","green","grey","rusty","new_quality","shiny","average","great_quality"};
		String adj = getter.getRandomAdjSynonym(getter.getRandomSeed(arr));
		String name = getter.getRandomSynonym("dagger");
		Item item = new Item(')', AsciiPanel.white, "dagger", name, adj, null);
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newSword(int depth) {
		String[] arr = {"red","green","grey","rusty","new_quality","shiny","great_special","great_quality"};
		String adj = getter.getRandomAdjSynonym(getter.getRandomSeed(arr));
		String name = getter.getRandomSynonym("sword");
		Item item = new Item(')', AsciiPanel.brightWhite, "sword", name, adj, null);
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth) {
		String[] arr = {"dusty","new_quality","old","average","great_quality"};
		String adj = getter.getRandomAdjSynonym(getter.getRandomSeed(arr));
		String name = getter.getRandomSynonym("staff");
		Item item = new Item(')', AsciiPanel.yellow, "staff", name, adj, null);
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newBow(int depth){
		String[] arr = {"dusty","new_quality","old","average","great_quality"};
		String adj = getter.getRandomAdjSynonym(getter.getRandomSeed(arr));
		String name = getter.getRandomSynonym("bow");
		Item item = new Item(')', AsciiPanel.yellow, "bow", name, adj, null);
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
