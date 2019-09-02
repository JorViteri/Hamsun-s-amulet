package Screens;
/**
 * Screen that allows the player to select which stat imrpove whe leveling up
 */
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JTextArea;
import Elements.Creature;
import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import Utils.LevelUpController;
import asciiPanel.AsciiPanel;

public class LevelUpScreen implements Screen {

	private LevelUpController controller;
	private Creature player;
	private int picks;
	
	/**
	 * Constructor
	 * @param player creature that levels up
	 * @param picks value to control the selection
	 */
	public LevelUpScreen(Creature player, int picks){
		this.controller = new LevelUpController();
		this.player = player;
		this.picks = picks;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2){
		List<String> options  = controller.getLevelUpOptions();
		TextManager textManager = TextManager.getTextManager();
		WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();

	    textManager.clearTextArea(1);
	    textManager.writeText(getter.getDirectTranslation("LevelUpScreen", "title"), 1);
	    textManager.writeText("------------------------------", 1);
	    

	    for (int i = 0; i < options.size(); i++){
	      textManager.writeText(String.format("[%d] %s", i+1, options.get(i)),1);
	    }
	    textManager.setCaretSimple(1);	
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		List<String> options = controller.getLevelUpOptions();
		String chars = "";
		for (int i = 0; i < options.size(); i++) {
			chars = chars + Integer.toString(i + 1);
		}
		int i = chars.indexOf(key.getKeyChar());
		if (i < 0) {
			return this;
		}
		controller.getLevelUpOption(options.get(i)).invoke(player);

		if (--picks < 1) {
			return null;
		} else {
			return this;
		}
	}
}
