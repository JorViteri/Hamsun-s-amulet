package Screens;

import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import DungeonComponents.Line;
import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import TextManagement.TextManager;
import Utils.Position;
import asciiPanel.AsciiPanel;

public class TargetBasedScreen implements Screen {

	protected Creature player;
	protected String caption;
	private int sx;
	private int sy;
	private int x;
	private int y;

	public TargetBasedScreen(Creature player, String caption, int sx, int sy) {
		this.player = player;
		this.caption = caption;
		this.sx = sx;
		this.sy = sy;
	}

	@Override
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		TextManager textManager = TextManager.getTextManager();
		String player_pos, pointer_pos = null, objective="";
		Creature objCreature = null;
		Item objItem = null;
		int scrollx, scrolly;
		scrollx = this.player.x-this.sx;
		scrolly=this.player.y-this.sy;
		
		for (Position p : new Line(sx, sy, sx + x, sy + y)) {
			if (p.getIntX() < 0 || p.getIntX() >= 80 || p.getIntY() < 0 || p.getIntY() >= 24)
				continue;

			terminal.write('*', p.getIntX(), p.getIntY(), AsciiPanel.brightMagenta);
			pointer_pos = String.format("Pointer's Position: %d %d", p.getIntX(), p.getIntY());
			objCreature = player.creature(p.getIntX()+scrollx, p.getIntY()+scrolly, this.player.z);
			objItem = player.item(p.getIntX()+scrollx, p.getIntY()+scrolly, this.player.z); //TODO no estoy almacenando los posiciones de los items wtf
			
		}
		
		if (objCreature!=null){ 
			objective= String.format("Objective: "+objCreature.name());
		}else if (objItem!=null){
			objective= String.format("Objective: "+objItem.getName());
		} else{
			objective= String.format("Objective: nothing");
		}
		
		
		terminal.clear(' ', 0, 23, 80, 1);
		terminal.write(caption, 0, 23);
		textManager.clearTextArea(1);
		player_pos = String.format("Player's Position: %d, %d",this.sx, this.sy);
		textManager.writeText(player_pos, 1);
		textManager.writeText(pointer_pos, 1);
		textManager.writeText(objective, 1);
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int px = x;
        int py = y;

        switch (key.getKeyCode()){
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_H: x--; break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_L: x++; break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_J: y--; break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_K: y++; break;
        case KeyEvent.VK_Y: x--; y--; break;
        case KeyEvent.VK_U: x++; y--; break;
        case KeyEvent.VK_B: x--; y++; break;
        case KeyEvent.VK_N: x++; y++; break;
        case KeyEvent.VK_ENTER: selectWorldCoordinate(player.x + x, player.y + y, sx + x, sy + y); return null;
        case KeyEvent.VK_ESCAPE: return null;
        }
    
        if (!isAcceptable(player.x + x, player.y + y)){
            x = px;
            y = py;
        }
    
        enterWorldCoordinate(player.x + x, player.y + y, sx + x, sy + y);
    
        return this;
	}
	
	public boolean isAcceptable(int x, int y) {
		return true;
	}

	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
		
	}

	public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
		
	}
}
