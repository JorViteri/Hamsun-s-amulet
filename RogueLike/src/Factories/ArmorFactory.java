package Factories;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import Elements.Item;
import Rogue.World;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterFactory;
import TextManagement.WordDataGetterSPA;
import asciiPanel.AsciiPanel;

public class ArmorFactory {

	private World world;

	public ArmorFactory(World world) {
		this.world = world;
	}

	public Item newLightArmor(int depth) {
		String[] arr = { "red", "green", "grey", "old", "big_size", "short" };
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("tunic");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Item item = new Item('[', AsciiPanel.green, "tunic", nameData.get("nounBase"), nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newMediumArmor(int depth) {
		String[] arr = { "average", "old", "big_size", "new_quality", "shiny", "rusty", "dusty" };
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("chain_mail");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Item item = new Item('[', AsciiPanel.white, "chain mail", nameData.get("nounBase"), nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		item.modifyDefenseValue(4);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newHeavyArmor(int depth) {
		String[] arr = { "great_quality", "old", "big_size", "new_quality", "shiny", "rusty", "dusty" };
		WordDataGetterFactory factory = WordDataGetterFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("plate_armour");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Item item = new Item('[', AsciiPanel.brightWhite, "plate armour", nameData.get("nounBase"),
				nameData.get("plural"), nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		item.modifyDefenseValue(6);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item randomArmor(int depth) {
		switch ((int) (Math.random() * 3)) {
		case 0:
			return newLightArmor(depth);
		case 1:
			return newMediumArmor(depth);
		default:
			return newHeavyArmor(depth);
		}
	}

}
