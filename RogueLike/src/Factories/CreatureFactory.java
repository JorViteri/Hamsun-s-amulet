package Factories;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import CreaturesAI.BatAi;
import CreaturesAI.FungusAi;
import CreaturesAI.GoblinAi;
import CreaturesAI.PlayerAi;
import CreaturesAI.ZombieAi;
import Elements.Creature;
import Rogue.World;
import TextManagement.WordDataGetter;
import Utils.FieldOfView;
import asciiPanel.AsciiPanel;

public class CreatureFactory {

	private World world;
	private WordDataGetter getter;
	
	//TODO la clase Creature precisa de cambios: tener un campo para nombre y otro para la llave
	public CreatureFactory(World world){
		this.world = world;
		this.getter = world.getWordDataGetter(); 
	}
	
	public Creature newPlayer(ArrayList<String> messages, FieldOfView fov) { 
		String[] a_arr = {"good_character","glorious","kind","big_character","brave", "great_special"};
		String[] n_arr = {"hero","heroine"};
		ArrayList<String> nameData = getter.getNounData(getter.getRandomSeed(n_arr));
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(a_arr),nameData.get(2));
		Creature player = new Creature(world, '@', "Player", nameData.get(0), nameData.get(1), nameData.get(2), AsciiPanel.brightWhite, 100, 20, 5, 20, adjData.get(0), adjData.get(1));
		world.addAtExitStairs(player);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Creature newFungus(int depth) {
		String[] arr = {"green","disgusting","little_size","big_size","old", "small"};
		ArrayList<String> nameData = getter.getNounData("fungus");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Creature fungus = new Creature(world, 'f', "Fungus", nameData.get(0), nameData.get(1), nameData.get(2), AsciiPanel.green, 10, 0, 0, 0, adjData.get(0), adjData.get(1));
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat(int depth) {
		String[] arr = {"scary","disgusting","little_size","big_size","creepy", "hairy"};
		ArrayList<String> nameData = getter.getNounData("bat");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Creature bat = new Creature(world, 'b', "Bat", nameData.get(0), nameData.get(1), nameData.get(2), AsciiPanel.yellow, 15, 5, 0, 0, adjData.get(0), adjData.get(1));
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Creature newZombie(int depth, Creature player) {
		String[] arr = {"scary","disgusting","cursed","creepy"};
		ArrayList<String> nameData = getter.getNounData("zombie");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Creature zombie = new Creature(world, 'z', "Zombie",  nameData.get(0), nameData.get(1), nameData.get(2), AsciiPanel.white, 50, 10, 10, 0, adjData.get(0), adjData.get(1));
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}
	
	public Creature newGoblin(int depth, Creature player) {
		String[] arr = {"normal","disgusting","average","small", "bald", "annoying"}; //TODO anhadir molesto
		ArrayList<String> nameData = getter.getNounData("goblin");
		ArrayList<String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get(2));
		Creature goblin = new Creature(world, 'g', "Goblin",  nameData.get(0), nameData.get(1), nameData.get(2), AsciiPanel.brightGreen, 66, 15, 5, 10, adjData.get(0), adjData.get(1));
		new GoblinAi(goblin, player);
		WeaponsFactory wf = new WeaponsFactory(this.world);
		ArmorFactory af = new  ArmorFactory(this.world);
		goblin.equip(wf.randomWeapon(depth));
		goblin.equip(af.randomArmor(depth));
		world.addAtEmptyLocation(goblin, depth);
		return goblin;
	}
}
