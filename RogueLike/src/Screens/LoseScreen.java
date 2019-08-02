package Screens;

import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {

	private WordDataGetter getter;
	
	public LoseScreen(WordDataGetter getter){
		this.getter = getter;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		terminal.write("You lost.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
        TextManager textManager = TextManager.getTextManager();
        textManager.clearTextArea(1);
        textManager.writeText("You lost.", 1);
        textManager.writeText("-- press [enter] to restart --", 1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		TextManager  textManager;
	    if(key.getKeyCode() == KeyEvent.VK_ENTER){
	    	textManager = TextManager.getTextManager();
	    	textManager.clearTextArea(2);
			return new PlayScreen(this.getter);
		} else{
			return  this;
		}
	}

}
