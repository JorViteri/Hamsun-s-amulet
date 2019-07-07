package Screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextArea;

import DungeonComponents.Corridor;
import DungeonComponents.Line;
import DungeonComponents.Room;
import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import Rogue.World;
import TextManagement.TextManager;
import Utils.Position;
import asciiPanel.AsciiPanel;

public class CheckEnviromentScreen implements Screen {
	
	private World world;
	private Creature player;
	private String caption;
	private int sx;
	private int sy;
	private int x;
	private int y;
	
	public CheckEnviromentScreen(Creature player, String caption, int sx, int sy, World world) {
		this.world = world;
		this.player=player;
		this.caption=caption;
		this.sx =sx;
		this.sy=sy;
	}
	
	
	//del estado actual de la partida... o hago dos pantallas para esot??? descricion rapida y chekear sala 
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		Room actualRoom = null;
		ArrayList<Room> room_list = new ArrayList<>();
		ArrayList<Corridor> corridor_list = new ArrayList<>();
		TextManager textManager = TextManager.getTextManager();
		String player_pos, pointer_pos = null, objective="";
		Creature objCreature = null;
		boolean roomdetails = false;
		Item objItem = null;
		int dimension = 0;
		int scrollx, scrolly;
		scrollx = this.player.getX()-this.sx;
		scrolly=this.player.getY()-this.sy;
		Position playerp = new Position(player.getX(), 30-player.getY(), player.getZ());
		
		
		if(isRoom(playerp)){ //TODO estoy en una habitacion y me ha dicho que no, que mentira
			room_list = world.getRoomLists().get(this.player.getZ());
			for(Room r: room_list){
				if (r.contais(playerp)){ //TODO esta funcion no esta implementada correcatmente
					actualRoom = r;
					break;
				}
			}
		} else{
			dimension = 1;
		}
		
		for (Position p : new Line(sx, sy, sx + x, sy + y)) {
			if (p.getIntX() < 0 || p.getIntX() >= 80 || p.getIntY() < 0 || p.getIntY() >= 24)
				continue;

			terminal.write('*', p.getIntX(), p.getIntY(), AsciiPanel.brightMagenta);
			pointer_pos = String.format("Pointer's Position: %d %d", p.getIntX()+scrollx, p.getIntY()+scrolly);
			objCreature = player.creature(p.getIntX()+scrollx, p.getIntY()+scrolly, this.player.getZ());
			objItem = player.item(p.getIntX()+scrollx, p.getIntY()+scrolly, this.player.getZ()); //TODO no estoy almacenando los posiciones de los items wtf
			
		}
		
		if (objCreature != null) {
			if (objCreature.equals(player)) {
				roomdetails = true;
			} else {
				objective = String.format("Objective: " + objCreature.name());
			}
		} else if (objItem != null) {
			objective = String.format("Objective: " + objItem.getName());
		} else {
			objective = String.format("Objective: nothing");
		}
		
		if(!roomdetails){
			textManager.clearTextArea(1);
			player_pos = String.format("Player's Position: %d, %d",this.sx, this.sy);
			textManager.writeText(player_pos, 1);
			textManager.writeText(pointer_pos, 1);
			textManager.writeText(objective, 1);
			textManager.writeText(caption, 1);
		}else{
			player_pos = String.format("Player's Position: %d, %d",this.sx, this.sy);
			textManager.writeText(player_pos, 1);
			if (dimension==1){
				textManager.writeText("You are in a corridor", 1);
			}else{
				String room_dim = String.format("Room is %d x %d",actualRoom.getWide(), actualRoom.getHeight());
				textManager.writeText(room_dim, 1);
			}
		}
		
		
	}
	
	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) { //este es el que le pasa el caption, lo que se debe mostrar creo
		Creature creature = player.creature(x, y, player.getZ());
		if (creature != null) {
			caption = creature.glyph() + " " + creature.name() + creature.getDetails();
			return;
		}

		Item item = player.getItem(x, y, player.getZ());
		if (item != null) {
			caption = item.getGlyph() + " " + player.nameOf(item) + item.getDetails();
			return;
		}

		Tile tile = player.tile(x, y, player.getZ());
		caption = tile.glyph() + " " + tile.getDetails();
	}
	

	private boolean isRoom(Position p){ //TODO Hace falta la posicion en la matriz real porque los vecinos se consiguen con esos valoresv 
		//Position newp = new Position(p.getX(),30-p.getY(), p.getZ());
		ArrayList<Position> neighbours = p.getNeighbors(8); //p tampoco tiene la Y correcta
		ArrayList<Position> ground_tiles = new ArrayList<>();
		Tile north, south, west, east;
		for (Position n : neighbours) {
			if (world.tile(n.getIntX(),30-n.getIntY(), n.getZ()).isGround()) { //TODO aqui habria que hacer la conversion a posiciones de la pantalla
				ground_tiles.add(n); //siguen siendo posiciones de la matriz real, ojo
			}
		}
		if (ground_tiles.size() == 2) {
			return false;
		} else if (ground_tiles.size() == 3) { 
			Double distance1 = ground_tiles.get(1).distance(ground_tiles.get(0));
			Double distance2 = ground_tiles.get(1).distance(ground_tiles.get(2));
			if (distance1.equals(distance2)) {
				return true;
			} else {
				return false;
			}
		} else if (ground_tiles.size() == 4) {
			north = world.tile(p.getPositionN().getIntX(), 30 - p.getPositionN().getIntY(), p.getZ());
			south = world.tile(p.getPositionS().getIntX(), 30 - p.getPositionS().getIntY(), p.getZ());
			if (!north.isGround() && !south.isGround()) {
				return false;
			} else {
				west = world.tile(p.getPositionW().getIntX(), 30 - p.getPositionW().getIntY(), p.getZ());
				east = world.tile(p.getPositionE().getIntX(), 30 - p.getPositionE().getIntY(), p.getZ());
				if (!west.isGround() && !east.isGround()) {
					return false;
				} else {
					return true;
				}
			}
		} else
			return true;
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
        case KeyEvent.VK_ESCAPE: return null;
        }
    
        if (!isAcceptable(player.getX() + x, player.getY() + y)){
            x = px;
            y = py;
        }
    
        enterWorldCoordinate(player.getX() + x, player.getY() + y, sx + x, sy + y);
    
        return this;
	}
	
	public boolean isAcceptable(int x, int y) {
        if (!player.canSee(x, y, player.getZ()))
            return false;
    
        for (Position p : new Line(player.getX(), player.getY(), x, y)){
            if (!player.realTile(p.getIntX(), p.getIntY(), player.getZ()).isGround())
                return false;
        }
    
        return true;
    }
	
}
