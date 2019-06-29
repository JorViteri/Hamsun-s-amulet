package Screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import Rogue.World;
import Rogue.WorldBuilder;
import TextManagement.TextManager;
import Utils.ElementsFactory;
import Utils.FieldOfView;
import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {

	private World world;
	private static Creature player;
	private static ArrayList<String> messages;
	private int screenWidth;
	private int screenHeight;
	private static ElementsFactory elementsFactory;
	private static FieldOfView fov;
	private Screen subscreen;
	private final static String newline = "\n";
	private final static int linelimit=28;
	private TextManager textManager = TextManager.getTextManager();
	

	public PlayScreen() {
		screenWidth = 80;
		screenHeight = 23;
		messages = new ArrayList<String>();
		createWorld();
		fov = new FieldOfView(world);
		elementsFactory = new ElementsFactory(world);
		createCreatures(elementsFactory);
		createItems(elementsFactory);
	}

	private void createCreatures(ElementsFactory creatureFactory) {
		player = creatureFactory.newPlayer(messages, fov);

		for (int z = 0; z < world.depth(); z++) {
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

	private void createItems(ElementsFactory factory) {
		for (int z = 0; z < world.depth(); z++) {
			for (int i = 0; i < 20; i++) {
				factory.newRock(z);
			}
			factory.randomArmor(z);
			factory.randomWeapon(z);
			factory.randomWeapon(z);
			factory.newBow(z);
			for (int i = 0; i < 20+z; i++){
				factory.randomPotion(z);
				factory.randomSpellBook(z);
			}
		}
		factory.newVictoryItem(world.depth() - 1);
	}

	private void createWorld() {
		world = new WorldBuilder(90, 31, 5).makeCaves().build();
	}

	public int getScrollX() {
		return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
	}

	public int getScrollY() {
		return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
	}

	private void displayMessages(AsciiPanel terminal, ArrayList<String> messages, JTextArea textArea) {
		int top = screenHeight - messages.size();
		int end=0;
		for (int i = 0; i < messages.size(); i++) {			
			//terminal.writeCenter(messages.get(i), top + i);
			if (textManager.textArea2ReachedLimit()){
				textManager.removeFirstLineTextArea(2);
			}
			textManager.writeText(messages.get(i), 2);
			
			/*
			if(textArea.getLineCount()==linelimit){
				try {
					end = textArea.getLineEndOffset(0);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				textArea.replaceRange("", 0, end);
			}
			textArea.append(messages.get(i)+newline);*/
			
		}
		messages.clear();
	}

	// TODO mejorar la funcion para que sea mas eficiente, tengo un apunte en la
	// libreta al respecto
	private void displayTiles(AsciiPanel terminal, int left, int top) {
		fov.update(player.x, player.y, player.z, player.visionRadius());

		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;

				if (player.canSee(wx, wy, player.z))
					terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
				else
					terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray);
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

		terminal.write(player.glyph(), player.x - left, player.y - top, player.color());

		stats = String.format("HP: %3d/%3d\nMana: %d/%d", player.hp(), player.maxHp(), player.getMana(), player.getMaxMana());
		//terminal.write(stats, 1, 23);
		textManager.clearTextArea(1);
	    textManager.writeText(stats, 1); 

		
		
		if (subscreen != null)
			subscreen.displayOutput(terminal, textArea, textArea2);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int level = player.getLevel();
		
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
			case KeyEvent.VK_MULTIPLY:
				subscreen = new LookScreen(player, "Looking", player.x - getScrollX(), player.y - getScrollY());
				break;
			case KeyEvent.VK_T:
				subscreen = new ThrowScreen(player, player.x - getScrollX(), player.y - getScrollY());
				break;
			case KeyEvent.VK_F:
				if (player.getWeapon() == null || player.getWeapon().getRangedAttackValue() == 0)
					player.notify("You don't have a ranged weapon equiped.");
				else
					subscreen = new FireWeaponScreen(player, player.x - getScrollX(), player.y - getScrollY());
				break;
			case KeyEvent.VK_Q:
				subscreen = new QuaffScreen(player);
				break;
			case KeyEvent.VK_R:
				subscreen = new ReadScreen(player, player.x - getScrollX(), player.y - getScrollY());
				break;
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
				subscreen = new HelpScreen();
				break;
			}
		}
		
		if(player.getLevel()>level){
			subscreen = new LevelUpScreen(player, player.getLevel()-level);
		}

		if (subscreen == null) {
			world.update();
		}
		if (player.hp() < 1) {
			return new LoseScreen();
		}
		return this;
	}

	private boolean userIsTryingToExit() {
		return player.z == 0 && world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP;
	}

	private Screen userExits() {
		for (Item item : player.inventory().getItems()) {
			if (item != null && item.getName().equals("Hamsun's amulet")) {
				return new WinScreen();
			}
		}
		return new LoseScreen();
	}

	public static Creature getPlayer() {
		if (player != null)
			return player;
		else
			player = elementsFactory.newPlayer(messages, fov);
		return player;
	}
	

}
