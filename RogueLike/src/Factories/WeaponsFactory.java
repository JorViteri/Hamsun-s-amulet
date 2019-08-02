package Factories;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import Elements.Item;
import Rogue.World;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterFactory;
import asciiPanel.AsciiPanel;

public class WeaponsFactory {

	private World world;
	
	public WeaponsFactory(World world){	
		this.world = world;
	}
	
	public Item newDagger(int depth) {
		String[] arr = {"red","green","grey","rusty","new_quality","shiny","average","great_quality"};
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		ArrayList<String> nameData = getter.getNounData("dagger");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item(')', AsciiPanel.white, "dagger", nameData.get(0),nameData.get(1), nameData.get(2), adjData.get(0), adjData.get(1), null);
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newSword(int depth) {
		String[] arr = {"red","green","grey","rusty","new_quality","shiny","great_special","great_quality"};
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		ArrayList<String> nameData = getter.getNounData("sword");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item(')', AsciiPanel.brightWhite, "sword", nameData.get(0),nameData.get(1), nameData.get(2), adjData.get(0), adjData.get(1), null);
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth) {
		String[] arr = {"dusty","new_quality","old","average","great_quality"};
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		ArrayList<String> nameData = getter.getNounData("staff");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item(')', AsciiPanel.yellow, "staff", nameData.get(0),nameData.get(1), nameData.get(2), adjData.get(0), adjData.get(1), null);
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
	public Item newBow(int depth){
		String[] arr = {"dusty","new_quality","old","average","great_quality"};
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		ArrayList<String> nameData = getter.getNounData("bow");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item(')', AsciiPanel.yellow, "bow", nameData.get(0),nameData.get(1), nameData.get(2), adjData.get(0), adjData.get(1), null);
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
