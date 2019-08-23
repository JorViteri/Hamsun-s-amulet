package Screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import Factories.ArmorFactory;
import Factories.CreatureFactory;
import Factories.ElementsFactory;
import Factories.PotionFactory;
import Factories.SpellBookFactory;
import Factories.WeaponsFactory;
import Rogue.World;
import Rogue.WorldBuilder;
import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import Utils.FieldOfView;
import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {

	private World world;
	private static Creature player;
	private static ArrayList<String> messages;
	private int screenWidth;
	private int screenHeight;
	private static ElementsFactory elementsFactory;
	private static CreatureFactory creatureFactory;
	private static WeaponsFactory weaponsFactory;
	private static ArmorFactory armorFactory;
	private static PotionFactory potionFactory;
	private static SpellBookFactory bookFactory;
	private static FieldOfView fov;
	private Screen subscreen;
	private TextManager textManager = TextManager.getTextManager();
	private boolean newEnemies = true;
	private int depth = 0;

	public PlayScreen() {
		//TODO definir como constantes
		screenWidth = 80; 
		screenHeight = 23; 
		Properties prop = new Properties();
		InputStream input;
		try{
			input =  new FileInputStream("dungeon.properties");
			prop.load(input);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.depth = Integer.valueOf(prop.getProperty("depth"));
		messages = new ArrayList<String>();
		createWorld();
		fov = new FieldOfView(world);
		elementsFactory = new ElementsFactory(world);
		creatureFactory = new CreatureFactory(world);
		armorFactory = new ArmorFactory(world);
		potionFactory = new PotionFactory(world);
		bookFactory = new SpellBookFactory(world);
		weaponsFactory = new WeaponsFactory(world);
		createCreatures(creatureFactory);
		createItems(armorFactory, potionFactory, elementsFactory, weaponsFactory, bookFactory);
		
	
	}

	private void createCreatures(CreatureFactory creatureFactory) {
		player = creatureFactory.newPlayer(messages, fov);

		for (int z = 0; z < world.getDepth(); z++) {
			for (int i = 0; i < 8; i++) {
				creatureFactory.newFungus(z);
			}
			for (int b = 0; b < 10; b++) {
				creatureFactory.newBat(z);
			}
			for (int i = 0; i < z + 1; i++) {
				creatureFactory.newGoblin(z, player);
			}
		}
	}

	private void createItems(ArmorFactory armorFactory, PotionFactory potionFactory, ElementsFactory elementsFactory, WeaponsFactory weaponFactory, SpellBookFactory bookFactory) {
		for (int z = 0; z < world.getDepth(); z++) {
			for (int i = 0; i < 20; i++) {
				elementsFactory.newRock(z);
			}
			armorFactory.randomArmor(z);
			weaponFactory.randomWeapon(z);
			weaponFactory.randomWeapon(z);
			weaponFactory.newBow(z);
			for (int i = 0; i < 10; i++){
				potionFactory.randomPotion(z);
				bookFactory.randomSpellBook(z);
			}
		}
		elementsFactory.newVictoryItem(world.getDepth() - 1);
	}

	private void createWorld() {
		world = new WorldBuilder(90, 31, depth).makeCaves().build();
	}

	public int getScrollX() { 
		return Math.max(0, Math.min(player.getX() - screenWidth / 2, world.getWidth() - screenWidth));
	}

	public int getScrollY() { 
		return Math.max(0, Math.min(player.getY() - screenHeight / 2, world.getHeight() - screenHeight));
	}

	private void displayMessages(AsciiPanel terminal, ArrayList<String> messages, JTextArea textArea) {
		for (int i = 0; i < messages.size(); i++) {			
			if (textManager.textArea2ReachedLimit()){
				textManager.removeFirstLineTextArea(2);
			}
			textManager.writeText(messages.get(i), 2);
		}
		messages.clear();
	}

	// TODO mejorar la funcion para que sea mas eficiente, tengo un apunte en la
	// libreta al respecto
	private void displayTiles(AsciiPanel terminal, int left, int top) {
		fov.update(player.getX(), player.getY(), player.getZ(), player.visionRadius());

		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;

				if (player.canSee(wx, wy, player.getZ()))
					terminal.write(world.glyph(wx, wy, player.getZ()), x, y, world.color(wx, wy, player.getZ()));
				else
					terminal.write(fov.tile(wx, wy, player.getZ()).glyph(), x, y, Color.darkGray);
			}
		}
	}

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {

		int left = getScrollX();
		int top = getScrollY();
		String stats;

		displayTiles(terminal, left, top);
		displayMessages(terminal, messages, textArea2); 

		terminal.write(player.glyph(), player.getX() - left, player.getY() - top, player.color());

		stats = String.format("HP: %3d/%3d\nMana: %d/%d", player.hp(), player.maxHp(), player.getMana(), player.getMaxMana());
		textManager.clearTextArea(1);
	    textManager.writeText(stats, 1); 

		
		
		if (subscreen != null)
			subscreen.displayOutput(terminal, textArea, textArea2);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int level = player.getLevel();
		boolean update = true;
		
		if (subscreen != null) {
			subscreen = subscreen.respondToUserInput(key);
		} else {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				return new LoseScreen();
			case KeyEvent.VK_ENTER:
				return new WinScreen();
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_H:
				player.moveBy(-1, 0, 0);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_L:
				player.moveBy(1, 0, 0);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_K:
				player.moveBy(0, -1, 0);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_J:
				player.moveBy(0, 1, 0);
				break;
			case KeyEvent.VK_Y:
				player.moveBy(-1, -1, 0);
				break;
			case KeyEvent.VK_U:
				player.moveBy(1, -1, 0);
				break;
			case KeyEvent.VK_B:
				player.moveBy(-1, 1, 0);
				break;
			case KeyEvent.VK_N:
				player.moveBy(1, 1, 0);
				break;
			case KeyEvent.VK_D:
				subscreen = new DropScreen(player);
				break;
			case KeyEvent.VK_E:
				subscreen = new ConsumeScreen(player);
				break;
			case KeyEvent.VK_W:
				subscreen = new EquipScreen(player);
				break;
			case KeyEvent.VK_X:
				subscreen = new ExamineScreen(player);
				break;
			//case KeyEvent.VK_MULTIPLY:
				//subscreen = new LookScreen(player, "Looking", player.getX() - getScrollX(), player.getY() - getScrollY());
				//break;
			case KeyEvent.VK_T:
				subscreen = new ThrowScreen(player, player.getX() - getScrollX(), player.getY() - getScrollY());
				break;
			case KeyEvent.VK_F:
				if (player.getWeapon() == null || player.getWeapon().getRangedAttackValue() == 0)
					player.notify("You don't have a ranged weapon equiped.");
				else
					subscreen = new FireWeaponScreen(player, player.getX() - getScrollX(), player.getY() - getScrollY());
				break;
			case KeyEvent.VK_Q:
				subscreen = new QuaffScreen(player);
				break;
			case KeyEvent.VK_R:
				subscreen = new ReadScreen(player, player.getX() - getScrollX(), player.getY() - getScrollY());
				break;
			case KeyEvent.VK_CONTROL:
				subscreen = new CheckEnviromentScreen(player, "enviroment", player.getX()-getScrollX(), player.getY()-getScrollY(), world);
			default:
				update = false;
			}

			switch (key.getKeyChar()) {
			case 'g':
			case ',':
				player.pickup();
				break;
			case '<':
				if (userIsTryingToExit()) {
					return userExits();
				} else {
					player.moveBy(0, 0, -1);
				}
				break;
			case '>':
				player.moveBy(0, 0, 1);
				break;
			case '?':
				update = true;
				subscreen = new HelpScreen();
				break;
			}
		}
		
		if(playerHasAmulet()&&this.newEnemies){ 
			createZombies(creatureFactory);
			this.newEnemies=false;
		}
		
		if(player.getLevel()>level){
			subscreen = new LevelUpScreen(player, player.getLevel()-level);
		}

		if ((subscreen == null)&&(update==true)) {
			world.update();
		}
		if (player.hp() < 1) {
			return new LoseScreen();
		}
		return this;
	}

	private void createZombies(CreatureFactory creatureFactory){
		for (int z = 0; z < world.getDepth(); z++) {
			for (int i = 0; i < 7; i++) {
				creatureFactory.newZombie(z, player);
			}
		}
		
	}
	
	private boolean playerHasAmulet(){
		for (Item item : player.inventory().getItems()) {
			if (item != null && item.getName().equals("Hamsun's amulet")) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean userIsTryingToExit() {
		return player.getZ() == 0 && world.tile(player.getX(), player.getY(), player.getZ()) == Tile.STAIRS_UP;
	}

	private Screen userExits() {
		if (playerHasAmulet()) {
			return new WinScreen();
		} else {
			return new LoseScreen();
		}
		
	}

	public static Creature getPlayer() {
		if (player != null)
			return player;
		else
			player = creatureFactory.newPlayer(messages, fov);
		return player;
	}
	

}
