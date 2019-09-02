package Screens;

import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {

	
	public WinScreen(){
	}
	
	
	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
		terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", 22);
        TextManager textManager = TextManager.getTextManager();
        textManager.clearTextArea(1);
        textManager.writeText(getter.getDirectTranslation("WinScreen", "win"), 1);
        textManager.writeText(getter.getDirectTranslation("WinScreen", "press"), 1);
        textManager.setCaretSimple(1);	

	}

	@Override
	public Screen respondToUserInput(KeyEvent key)  {
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
