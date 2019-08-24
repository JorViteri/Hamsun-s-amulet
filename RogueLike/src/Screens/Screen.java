package Screens;
/**
 * Defines the interface for Screen
 */
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import asciiPanel.AsciiPanel;

public interface Screen {
	
	/**
	 * Displays the screen in the JTextAreas
	 * @param terminal ASCII terminal of the game
	 * @param textArea1 JTextArea of the game window
	 * @param textArea2v JTextArea of the game window
	 */
	public void displayOutput(AsciiPanel terminal, JTextArea textArea1, JTextArea textArea2);
	
	/**
	 * Deals with the keys pressed and ussually calls a new window
	 * @param key pressed
	 * @return a new screen
	 */
	public Screen respondToUserInput(KeyEvent key);

	
}
