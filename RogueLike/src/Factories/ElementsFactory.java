package Factories;

import java.util.ArrayList;
import java.util.Collections;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import CreaturesAI.BatAi;
import CreaturesAI.FungusAi;
import CreaturesAI.GoblinAi;
import CreaturesAI.PlayerAi;
import CreaturesAI.ZombieAi;
import Elements.Creature;
import Elements.Effect;
import Elements.Item;
import Rogue.World;
import Utils.FieldOfView;
import Utils.NameSynonymsGetter;
import asciiPanel.AsciiPanel;

public class ElementsFactory {

	private World world;
	private NameSynonymsGetter getter;

	public ElementsFactory(World world) {
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

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock", "rock", null, null);
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) {
		Item item = new Item('*', AsciiPanel.brightWhite, "Hamsun's amulet",  "Hamsun's amulet", null, null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
}
