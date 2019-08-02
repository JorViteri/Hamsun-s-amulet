package Screens;

import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterFactory;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {
	
	private WordDataGetter getter;
	
	public StartScreen(){
		WordDataGetterFactory getterFactory = new WordDataGetterFactory();
		this.getter = getterFactory.getWordDataGetter();
	}

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		terminal.write("r1 tutorial", 1, 1);
		terminal.writeCenter("-- press [enter] to start --", 22);
				
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(this.getter) : this;
	}
	

}
