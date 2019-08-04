package Factories;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import Elements.Item;
import Rogue.World;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterFactory;
import asciiPanel.AsciiPanel;

public class WeaponsFactory {

	private World world;

	public WeaponsFactory(World world) {
		this.world = world;
	}

	public Item newDagger(int depth) {
		String[] arr = { "red", "green", "grey", "rusty", "new_quality", "shiny", "average", "great_quality" };
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("dagger");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item(')', AsciiPanel.white, "dagger", nameData.get("nounBase"), nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		item.modifyAttackValue(5);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newSword(int depth) {
		String[] arr = { "red", "green", "grey", "rusty", "new_quality", "shiny", "great_special", "great_quality" };
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("sword");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item(')', AsciiPanel.brightWhite, "sword", nameData.get("nounBase"), nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		item.modifyAttackValue(10);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newStaff(int depth) {
		String[] arr = { "dusty", "new_quality", "old", "average", "great_quality" };
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("staff");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item(')', AsciiPanel.yellow, "staff", nameData.get("nounBase"), nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		item.modifyAttackValue(5);
		item.modifyDefenseValue(3);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newBow(int depth) {
		String[] arr = { "dusty", "new_quality", "old", "average", "great_quality" };
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("bow");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item(')', AsciiPanel.yellow, "bow", nameData.get("nounBase"), nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
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
