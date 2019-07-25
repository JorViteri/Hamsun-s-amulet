package Factories;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import Elements.Item;
import Rogue.World;
import Utils.NameSynonymsGetter;
import asciiPanel.AsciiPanel;

public class ArmorFactory {

	private World world;
	private NameSynonymsGetter getter;
	
	public ArmorFactory(World world){
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
	
	public Item newLightArmor(int depth) {
		String name = getter.getRandomSynonym("tunic");
		Item item = new Item('[', AsciiPanel.green, "tunic", name, null);
		item.modifyDefenseValue(2);
		world.addAtEmptyLocation(item, depth);
		return item;
	}

	public Item newMediumArmor(int depth) {
		String name = getter.getRandomSynonym("chain mail");
		Item item = new Item('[', AsciiPanel.white, "chain mail", name, null);
		item.modifyDefenseValue(4);
		world.addAtEmptyLocation(item, depth); //Wordl es null, WTF como sucede esto?? eso no lo he tocado en absoluto!
		return item;
	}

	public Item newHeavyArmor(int depth) {
		String name = getter.getRandomSynonym("plate armour");
		Item item = new Item('[', AsciiPanel.brightWhite, "plate armour", name, null);
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
