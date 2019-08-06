package Factories;

import java.util.ArrayList;
import java.util.HashMap;

import CreaturesAI.BatAi;
import CreaturesAI.FungusAi;
import CreaturesAI.GoblinAi;
import CreaturesAI.PlayerAi;
import CreaturesAI.ZombieAi;
import Elements.Creature;
import Rogue.World;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import Utils.FieldOfView;
import asciiPanel.AsciiPanel;

public class CreatureFactory {

	private World world;

	// TODO la clase Creature precisa de cambios: tener un campo para nombre y
	// otro para la llave
	public CreatureFactory(World world) {
		this.world = world;
	}

	public Creature newPlayer(ArrayList<String> messages, FieldOfView fov) {
		String[] a_arr = { "good_character", "glorious", "kind", "big_character", "brave", "great_special" };
		String[] n_arr = { "hero", "heroine" };
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData(getter.getRandomSeed(n_arr));
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(a_arr), nameData.get("genere"));
		Creature player = new Creature(world, '@', "Player", nameData.get("baseNoun"), nameData.get("plural"),
				nameData.get("genere"), AsciiPanel.brightWhite, 100, 20, 5, 20, adjData.get("singular"),
				adjData.get("plural"));
		world.addAtExitStairs(player);
		new PlayerAi(player, messages, fov);
		return player;
	}

	public Creature newFungus(int depth) {
		String[] arr = { "green", "disgusting", "little_size", "big_size", "old", "small" };
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("fungus");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Creature fungus = new Creature(world, 'f', "Fungus", nameData.get("baseNoun"), nameData.get("plural"),
				nameData.get("genere"), AsciiPanel.green, 10, 0, 0, 0, adjData.get("singular"), adjData.get("plural"));
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}

	public Creature newBat(int depth) {
		String[] arr = { "scary", "disgusting", "little_size", "big_size", "creepy", "hairy" };
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("bat");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Creature bat = new Creature(world, 'b', "Bat", nameData.get("baseNoun"), nameData.get("plural"),
				nameData.get("genere"), AsciiPanel.yellow, 15, 5, 0, 0, adjData.get("singular"), adjData.get("plural"));
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}

	public Creature newZombie(int depth, Creature player) {
		String[] arr = { "scary", "disgusting", "cursed", "creepy" };
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("zombie");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Creature zombie = new Creature(world, 'z', "Zombie", nameData.get("baseNoun"), nameData.get("plural"),
				nameData.get("genere"), AsciiPanel.white, 50, 10, 10, 0, adjData.get("singular"),
				adjData.get("plural"));
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}

	public Creature newGoblin(int depth, Creature player) {
		String[] arr = { "normal", "disgusting", "average", "small", "bald", "annoying" };
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> nameData = getter.getNounData("goblin");
		HashMap<String, String> adjData = getter.getAdjData(getter.getRandomSeed(arr), nameData.get("genere"));
		Creature goblin = new Creature(world, 'g', "Goblin", nameData.get("baseNoun"), nameData.get("plural"),
				nameData.get("genere"), AsciiPanel.brightGreen, 66, 15, 5, 10, adjData.get("singular"),
				adjData.get("plural"));
		new GoblinAi(goblin, player);
		WeaponsFactory wf = new WeaponsFactory(this.world);
		ArmorFactory af = new ArmorFactory(this.world);
		goblin.equip(wf.randomWeapon(depth));
		goblin.equip(af.randomArmor(depth));
		world.addAtEmptyLocation(goblin, depth);
		return goblin;
	}
}
