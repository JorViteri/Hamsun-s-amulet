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
import Utils.FieldOfView;
import Utils.NameSynonymsGetter;
import asciiPanel.AsciiPanel;

public class CreatureFactory {

	private World world;
	private NameSynonymsGetter getter;
	
	//TODO la clase Creature precisa de cambios: tener un campo para nombre y otro para la llave
	public CreatureFactory(World world){
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
	
	public Creature newPlayer(ArrayList<String> messages, FieldOfView fov) {
		String adj = getter.getRandomAdjSynonym("brave");
		Creature player = new Creature(world, '@', "Player", "Player",AsciiPanel.brightWhite, 100, 20, 5, 20, adj);
		world.addAtExitStairs(player);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Creature newFungus(int depth) {
		String name = getter.getRandomSynonym("fungus");
		String adj = getter.getRandomAdjSynonym("disgusting");
		Creature fungus = new Creature(world, 'f', "Fungus", name,AsciiPanel.green, 10, 0, 0, 0, adj);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat(int depth) {
		String name = getter.getRandomSynonym("bat");
		String adj = getter.getRandomAdjSynonym("scary");
		Creature bat = new Creature(world, 'b', "Bat", name,AsciiPanel.yellow, 15, 5, 0, 0, adj);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Creature newZombie(int depth, Creature player) {
		String name = getter.getRandomSynonym("zombie");
		String adj = getter.getRandomAdjSynonym("cursed");
		Creature zombie = new Creature(world, 'z', "Zombie", name, AsciiPanel.white, 50, 10, 10, 0, adj);
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}
	
	public Creature newGoblin(int depth, Creature player) {
		String name = getter.getRandomSynonym("goblin");
		String adj = getter.getRandomAdjSynonym("little_size");
		Creature goblin = new Creature(world, 'g', "Goblin", name,AsciiPanel.brightGreen, 66, 15, 5, 10, adj);
		new GoblinAi(goblin, player);
		WeaponsFactory wf = new WeaponsFactory(this.world);
		ArmorFactory af = new  ArmorFactory(this.world);
		goblin.equip(wf.randomWeapon(depth));
		goblin.equip(af.randomArmor(depth));
		world.addAtEmptyLocation(goblin, depth);
		return goblin;
	}
}
