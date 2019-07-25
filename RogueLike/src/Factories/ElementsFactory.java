package Factories;

import java.util.ArrayList;
import java.util.Collections;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import asciiPanel.AsciiPanel;

public class ElementsFactory {

	private World world;

	public ElementsFactory(World world) {
		this.world = world;
	}

	public Item newRock(int depth) {
		Item rock = new Item(',', AsciiPanel.yellow, "rock", "rock", null);
		world.addAtEmptyLocation(rock, depth);
		return rock;
	}

	public Item newVictoryItem(int depth) {
		Item item = new Item('*', AsciiPanel.brightWhite, "Hamsun's amulet",  "Hamsun's amulet",null);
		world.addAtEmptyLocation(item, depth);
		return item;
	}
	
}
