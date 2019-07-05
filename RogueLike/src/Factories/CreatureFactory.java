package Factories;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import CreaturesAI.BatAi;
import CreaturesAI.FungusAi;
import CreaturesAI.GoblinAi;
import CreaturesAI.PlayerAi;
import CreaturesAI.ZombieAi;
import Elements.Creature;
import Rogue.World;
import Utils.FieldOfView;
import asciiPanel.AsciiPanel;

public class CreatureFactory {

	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(ArrayList<String> messages, FieldOfView fov) {
		Creature player = new Creature(world, '@', "Player", AsciiPanel.brightWhite, 100, 20, 5, 20);
		world.addAtExitStairs(player);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Creature newFungus(int depth) {
		Creature fungus = new Creature(world, 'f', "Fungi", AsciiPanel.green, 10, 0, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat(int depth) {
		Creature bat = new Creature(world, 'b', "Bat", AsciiPanel.yellow, 15, 5, 0, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Creature newZombie(int depth, Creature player) {
		Creature zombie = new Creature(world, 'z', "zombie", AsciiPanel.white, 50, 10, 10, 0);
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}
	
	public Creature newGoblin(int depth, Creature player) {
		Creature goblin = new Creature(world, 'g', "goblin", AsciiPanel.brightGreen, 66, 15, 5, 10);
		new GoblinAi(goblin, player);
		WeaponsFactory wf = new WeaponsFactory(this.world);
		ArmorFactory af = new  ArmorFactory(this.world);
		goblin.equip(wf.randomWeapon(depth));
		goblin.equip(af.randomArmor(depth));
		world.addAtEmptyLocation(goblin, depth);
		return goblin;
	}
}
