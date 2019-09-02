package Screens;
/**
 *Defines a screen with a pointer that allows the player to target things in the dungeon 
 */
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import DungeonComponents.Line;
import Elements.Creature;
import Elements.Item;
import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
import Utils.Position;
import asciiPanel.AsciiPanel;

public class TargetBasedScreen implements Screen {

	protected Creature player;
	protected String caption;
	private int sx;
	private int sy;
	private int x;
	private int y;

	/**
	 * Cosntructor
	 * @param player creature that called this screen
	 * @param caption string with some info about the action
	 * @param sx coordinate in x
	 * @param sy coordinate in y
	 */
	public TargetBasedScreen(Creature player, String caption, int sx, int sy) {
		this.player = player;
		this.caption = caption;
		this.sx = sx;
		this.sy = sy;
	}

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		WordDataGetter getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
		TextManager textManager = TextManager.getTextManager();
		String player_pos, pointer_pos = null, objective="";
		Creature objCreature = null;
		Item objItem = null;
		int scrollx, scrolly;
		scrollx = this.player.getX()-this.sx;
		scrolly=this.player.getY()-this.sy;
		
		for (Position p : new Line(sx, sy, sx + x, sy + y)) {
			if (p.getIntX() < 0 || p.getIntX() >= 80 || p.getIntY() < 0 || p.getIntY() >= 24)
				continue;

			terminal.write('*', p.getIntX(), p.getIntY(), AsciiPanel.brightMagenta);
			pointer_pos = String.format(getter.getDirectTranslation("TargetBasedScreen", "pointerPosition"), p.getIntX()+scrollx, p.getIntY()+scrolly); //entiendo que, haciendo esto, la posicion se adapta a la de la partida porque sino es solo en la terminal
			objCreature = player.creature(p.getIntX()+scrollx, p.getIntY()+scrolly, this.player.getZ());
			objItem = player.item(p.getIntX()+scrollx, p.getIntY()+scrolly, this.player.getZ()); 
			
		}
		
		if (objCreature!=null){ 
			objective= String.format(getter.getDirectTranslation("TargetBasedScreen", "objective"),objCreature.name());
		}else if (objItem!=null){
			objective= String.format(getter.getDirectTranslation("TargetBasedScreen", "objective"),objItem.getName());
		} else{
			objective= String.format(getter.getDirectTranslation("TargetBasedScreen", "objective")," ").trim();
		}
		
		
		terminal.clear(' ', 0, 23, 80, 1);
		terminal.write(caption, 0, 23);
		textManager.clearTextArea(1);
		player_pos = String.format(getter.getDirectTranslation("TargetBasedScreen", "playerPosition"),player.getX(), player.getY());
		textManager.writeText(player_pos, 1);
		textManager.writeText(pointer_pos, 1);
		textManager.writeText(objective, 1);
		textManager.writeText(caption, 1);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int px = x;
        int py = y;

        switch (key.getKeyCode()){
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_H: x--; break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_K: x++; break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_U: y--; break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_J: y++; break;
        case KeyEvent.VK_Y: x--; y--; break;
        case KeyEvent.VK_I: x++; y--; break;
        case KeyEvent.VK_B: x--; y++; break;
        case KeyEvent.VK_N: x++; y++; break;
        case KeyEvent.VK_ENTER: selectWorldCoordinate(player.getX() + x, player.getY() + y, sx + x, sy + y); return null;
        case KeyEvent.VK_ESCAPE: return null;
        }
    
        if (!isAcceptable(player.getX() + x, player.getY() + y)){
            x = px;
            y = py;
        }
    
        enterWorldCoordinate(player.getX() + x, player.getY() + y, sx + x, sy + y);
    
        return this;
	}
	
	/**
	 * Chech if the position is valid
	 * @param x coordinate in x
	 * @param y coordinate in y
	 * @return true if it's valid, else false
	 */
	public boolean isAcceptable(int x, int y) {
		return true;
	}

	/**
	 * Defines what to do when moving the pointer to a new coordinate
	 * @param x coordinate in x
	 * @param y coordinate in y
	 * @param screenX valor of the coordinate x in screen
	 * @param screenY valor of the coordinate y in screen
	 */
	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
		
	}

	/**
	 * Calls the action to do with to the selected target
	 * @param x coordinate in x
	 * @param y coordinate in y
	 * @param screenX coordinate x in screen
	 * @param screenY coordinate y in screen
	 */
	public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
		
	}
}
