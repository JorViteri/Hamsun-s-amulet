package Rogue.Screens;

import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import asciiPanel.AsciiPanel;

public interface Screen {

	//public void displayOutput(SGPane terminal);
	public void displayOutput(AsciiPanel terminal, JTextArea textArea1, JTextArea textArea2);
	
	public Screen respondToUserInput(KeyEvent key);

	
}
