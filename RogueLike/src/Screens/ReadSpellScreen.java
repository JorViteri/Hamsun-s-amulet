package Screens;
/**
 * Defines the Screen used to sellect which spell use
 */
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextArea;

import Elements.Creature;
import Elements.Item;
import Elements.Spell;
import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

public class ReadSpellScreen implements Screen {
	
	protected Creature player;
	private String letters;
	private Item item;
	private int sx;
	private int sy;
	
	/**
	 * Constructor
	 * @param player creature that calls this screen
	 * @param sx position in x 
	 * @param sy position in y
	 * @param book being used
	 */
	public ReadSpellScreen(Creature player, int sx, int sy, Item item){
		this.player = player;
		this.letters = "abcdefghijklmnopqrstuvwxyz";
		this.item = item;
		this.sx = sx;
		this.sy = sy;
	}

	
	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
		TextManager textManager = TextManager.getTextManager();
		textManager.clearTextArea(1);
		ArrayList<String> lines = getList();

		//int y = 23 - lines.size();
		//int x = 4;

		//if (lines.size() > 0)
			//terminal.clear(' ', x, y, 20, lines.size());

		for (String line : lines) {
			textManager.writeText(line, 1);
			//terminal.write(line, x, y++);
			
		}

		//terminal.clear(' ', 0, 23, 80, 1);
		//terminal.write("What would you like to read?", 2, 23);
		
		textManager.writeText(getter.getDirectTranslation("ReadSpellScreen", "title"), 1);
		//terminal.repaint();

	}
	
	/**
	 *Obtains the list of spells that the item has 
	 * @return The list with the spells as strings
	 */
	private ArrayList<String> getList() {
		ArrayList<String> lines = new ArrayList<String>();

		for (int i = 0; i < item.getWrittenSpells().size(); i++) {
			Spell spell = item.getWrittenSpells().get(i);

			String line = letters.charAt(i) + " - " + spell.getName() + " (" + spell.getManaCost() + " mana)";

			lines.add(line);
		}
		return lines;
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		char c = key.getKeyChar();
		
		ArrayList<Spell> spells = new ArrayList<Spell>(this.item.getWrittenSpells());

		if (letters.indexOf(c) > -1 && spells.size() > letters.indexOf(c) &&spells.get(letters.indexOf(c))!= null) {
			return use(item.getWrittenSpells().get(letters.indexOf(c)));
		} else if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			return null;
		} else {
			return this;
		}
	}

	/**
	 * Enters the CastSpellScreen in which the player must choose the objective
	 * @param spell spell that is going to be used
	 * @return the new scree
	 */
	protected Screen use(Spell spell) {
		return new CastSpellScreen(player, "", sx, sy, spell);
	}
}
