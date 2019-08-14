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
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import TextManagement.WordDataGetterSPA;
import Utils.FieldOfView;
import asciiPanel.AsciiPanel;

public class ElementsFactory {

	private World world;

	public ElementsFactory(World world) {
		this.world = world;
	}

	public Item newRock(int depth) {
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("rock");
		HashMap<String, String> adjData = getter.getAdjData("average", nameData.get("genere"));
		Item rock = new Item(',', AsciiPanel.yellow, "rock", nameData.get("baseNoun"), nameData.get("plural"),
				nameData.get("genere"), adjData.get("singular"), adjData.get("plural"), null);
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) { //TODO a este le tengo que meter las traducciones manuales o traducir amuleto y completarlo como lo de cadaver
		Item item = new Item('*', AsciiPanel.brightWhite, "Hamsun's amulet",  "Hamsun's amulet", null, null, "common", null, null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
}
