package Screens;

import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

	
	public StartScreen(){
	}

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
		terminal.write("r1 tutorial", 1, 1);
		terminal.writeCenter("-- press [enter] to start --", 22);
		TextManager manager = TextManager.getSingletonInstance(textArea, textArea2);
		manager.writeText(getter.getDirectTranslation("StartScreen", "start"), 1);
				
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
	

}
