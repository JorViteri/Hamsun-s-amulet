package Screens;
/**
 * Defines Screens that show a list of items
 */
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextArea;

import Elements.Creature;
import Elements.Item;
import TextManagement.TextManager;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

public abstract class InventoryBasedScreen implements Screen {

	protected Creature player;
	private String letters;

	/**
	 * Gets a verb that represents the action used in the screen
	 * @return string that's the verb
	 */
	protected abstract String getVerb();

	/**
	 * Checks if the item is acceptable for use in the actual screen
	 * @param item to check
	 * @return true if it is, false in the other case
	 */
	protected abstract boolean isAcceptable(Item item);

	/**
	 * Uses the item and returns a new screen
	 * @param item the item used
	 * @return  the new screen
	 */
	protected abstract Screen use(Item item);

	/**
	 * Constructor
	 * @param player creature that called the function
	 */
	public InventoryBasedScreen(Creature player) {
		this.player = player;
		this.letters = "abcdefghijklmnopqrstuvwxyz";
	}


	public void displayOutput(AsciiPanel terminal, JTextArea textArea1, JTextArea textArea2) {
		ArrayList<String> lines = getList();
		TextManager textManager = TextManager.getTextManager();
		textManager.clearTextArea(1);
		
		String phrase = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter()
				.getDirectTranslation("InventoryBasedScreen", "basePhrase");
		
		String verb = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter()
				.getDirectTranslation("InventoryBasedScreen", getVerb());
		
		String finalPhrase = String.format(phrase, verb);
		textManager.writeText(finalPhrase, 1);
		
		for (String line : lines) {
			textManager.writeText(line, 1);
		}
		textManager.setCaretSimple(1);	
	}

	private ArrayList<String> getList() {
		ArrayList<String> lines = new ArrayList<String>();
		Item[] inventory = player.inventory().getItems();

		for (int i = 0; i < inventory.length; i++) {
			Item item = inventory[i];

			if (item == null || !isAcceptable(item))
				continue;

			String line = letters.charAt(i) + " - " + item.getGlyph() + " " + player.nameOf(item);

			if (item == player.getWeapon() || item == player.getArmor()) {
				line += " (equipped)";
			}

			lines.add(line);
		}
		return lines;
	}

	public Screen respondToUserInput(KeyEvent key) {
		char c = key.getKeyChar();

		Item[] items = player.inventory().getItems();

		if (letters.indexOf(c) > -1 && items.length > letters.indexOf(c) && items[letters.indexOf(c)] != null
				&& isAcceptable(items[letters.indexOf(c)])) {
			return use(items[letters.indexOf(c)]);
		} else if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			return null;
		} else {
			return this;
		}
	}

}
