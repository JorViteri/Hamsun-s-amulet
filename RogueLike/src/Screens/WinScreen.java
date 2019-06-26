package Screens;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JTextArea;

import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);

	}

	@Override
	public Screen respondToUserInput(KeyEvent key)  {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}

}
