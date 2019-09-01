package Screens;
/**
 * Display helpful information about how to play the game
 */
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

public class HelpScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea1, JTextArea textArea2) {
		WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
		TextManager textManager = TextManager.getTextManager();
		textManager.clearTextArea(1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "title"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "gameDescription"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "pickUp"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "drop"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "eat"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "wearEquip"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "help"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "examine"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "lookAround"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "fire"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "throw"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "quaff"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "read"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "up"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "down"), 1);
		textManager.writeText(getter.getDirectTranslation("HelpScreen", "pressContinue"), 1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}

}
