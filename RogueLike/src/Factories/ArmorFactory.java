package Factories;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import Elements.Item;
import Rogue.World;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterSPA;
import asciiPanel.AsciiPanel;

public class ArmorFactory {

	private World world;
	private WordDataGetter getter;
	
	public ArmorFactory(World world, WordDataGetter getter){	
		this.world = world;
		this.getter = getter;
	}
	
	public Item newLightArmor(int depth) {
		String[] arr = {"red","green","grey","old","big_size","short"};
		ArrayList<String> nameData = getter.getNounData("tunic");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item('[', AsciiPanel.green, "tunic", nameData.get(0),nameData.get(1), nameData.get(2), adjData.get(0), adjData.get(1), null);
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newMediumArmor(int depth) {
		String[] arr = {"average","old","big_size","new_quality","shiny","rusty","dusty"};
		ArrayList<String> nameData = getter.getNounData("chain_mail");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item('[', AsciiPanel.white, "chain mail", nameData.get(0),nameData.get(1), nameData.get(2), adjData.get(0), adjData.get(1), null);
		item.modifyDefenseValue(4);
		world.addAtEmptyLocation(item, depth); //Wordl es null, WTF como sucede esto?? eso no lo he tocado en absoluto!
		return item;
	}

	public Item newHeavyArmor(int depth) {
		String[] arr = {"great_quality","old","big_size","new_quality","shiny","rusty","dusty"};
		ArrayList<String> nameData = getter.getNounData("plate_armour");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Item item = new Item('[', AsciiPanel.brightWhite, "plate armour", nameData.get(0),nameData.get(1), nameData.get(2), adjData.get(0), adjData.get(1), null);
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
