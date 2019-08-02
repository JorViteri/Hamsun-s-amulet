package Screens;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JTextArea;

import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {

	private WordDataGetter getter;
	
	public WinScreen(WordDataGetter getter){
		this.getter = getter;
	}
	
	
	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
        TextManager textManager = TextManager.getTextManager();
        textManager.clearTextArea(1);
        textManager.writeText("You won.", 1);
        textManager.writeText("-- press [enter] to restart --", 1);

	}

	@Override
	public Screen respondToUserInput(KeyEvent key)  {
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
