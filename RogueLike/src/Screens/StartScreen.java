package Screens;

import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import TextManagement.TextManager;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

	
	public StartScreen(){
	}

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		terminal.write("r1 tutorial", 1, 1);
		terminal.writeCenter("-- press [enter] to start --", 22);
		TextManager manager = TextManager.getSingletonInstance(textArea, textArea2);
		manager.writeText("r1 tutorial", 1);
		manager.writeText("-- press [enter] to start --", 1);
				
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
	

}
