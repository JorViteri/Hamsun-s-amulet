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
import TextManagement.WordDataGetterSPA;
import Utils.FieldOfView;
import asciiPanel.AsciiPanel;

public class ElementsFactory {

	private World world;

	public ElementsFactory(World world) {
		this.world = world;
	}

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock", "rock", null, null, "commom", null, null);
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) {
		Item item = new Item('*', AsciiPanel.brightWhite, "Hamsun's amulet",  "Hamsun's amulet", null, null, "common", null, null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
}
