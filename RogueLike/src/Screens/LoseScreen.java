package Screens;

import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {

	
	public LoseScreen(){
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
		terminal.write("You lost.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
        TextManager textManager = TextManager.getTextManager();
        textManager.clearTextArea(1);
        textManager.writeText(getter.getDirectTranslation("LoseScreen", "lose"), 1);
        textManager.writeText(getter.getDirectTranslation("LoseScreen", "press"), 1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		TextManager  textManager;
	    if(key.getKeyCode() == KeyEvent.VK_ENTER){
	    	textManager = TextManager.getTextManager();
	    	textManager.clearTextArea(2);
			return new PlayScreen();
		} else{
			return  this;
		}
	}

}
