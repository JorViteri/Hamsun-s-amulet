package Screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import DungeonComponents.Corridor;
import DungeonComponents.Line;
import DungeonComponents.Room;
import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import Rogue.World;
import TextManagement.TextManager;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
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
	private HashMap<String, String> uiPhrases = new HashMap<>();
	private WordDataGetter getter = null;
	
	public CheckEnviromentScreen(Creature player, String caption, int sx, int sy, World world) {
		this.world = world;
		this.player=player;
		this.caption=caption;
		this.sx =sx;
		this.sy=sy;
		this.getter = WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter();
		this.uiPhrases = this.generateUiPhrases();
	}
	
	private HashMap<String, String> generateUiPhrases(){
		HashMap<String, String> result = new HashMap<>();
		result.put("playerPosition", getter.getDirectTranslation("CheckEnviromentScreen", "playerPosition"));
		result.put("pointerPosition", getter.getDirectTranslation("CheckEnviromentScreen", "pointerPosition"));
		result.put("objective", getter.getDirectTranslation("CheckEnviromentScreen", "objective"));
		result.put("roomIs", getter.getDirectTranslation("CheckEnviromentScreen", "roomIs"));
		result.put("inCorrirdor", getter.getDirectTranslation("CheckEnviromentScreen", "inCorridor"));
		return result;
	}
	
	//TODO hacer MUCHO mas ligera esta funcion
	public void displayOutput(AsciiPanel terminal, JTextArea textArea, JTextArea textArea2) {
		Room actualRoom = null;
		ArrayList<Room> room_list = new ArrayList<>();
		ArrayList<Corridor> corridor_list = new ArrayList<>();
		ArrayList<String> aux_strings = new ArrayList<>();
		ArrayList<Integer> aux_int = new ArrayList<>();
		TextManager textManager = TextManager.getTextManager();
		String player_pos, pointer_pos = null, objective="";
		Creature objCreature = null;
		boolean roomdetails = false;
		Item objItem = null;
		int dimension = 0;
		int height = world.getHeight();
		int scrollx, scrolly;
		int index, i;
		
		scrollx = this.player.getX()-this.sx;
		scrolly=this.player.getY()-this.sy;
		Position playerp = new Position(player.getX(), (height-1)-player.getY(), player.getZ());
		
		
		if(isRoom(playerp)){ 
			room_list = world.getRoomLists().get(this.player.getZ());
			for(Room r: room_list){
				if (r.contais(playerp)){ 
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
			pointer_pos = String.format(uiPhrases.get("pointerPosition"), p.getIntX()+scrollx, p.getIntY()+scrolly);
			objCreature = player.creature(p.getIntX()+scrollx, p.getIntY()+scrolly, this.player.getZ());
			objItem = player.item(p.getIntX()+scrollx, p.getIntY()+scrolly, this.player.getZ()); //TODO no estoy almacenando los posiciones de los items wtf
			
		}
		
		if (objCreature != null) {
			if (objCreature.equals(player)) {
				roomdetails = true;
			} else {
				objective = String.format(uiPhrases.get("objective"), objCreature.name());
			}
		} else if (objItem != null) {
			objective = String.format(uiPhrases.get("objective"), objItem.getName());
		} else {
			objective = String.format(uiPhrases.get("objective"),""); 
		}
		
		if(!roomdetails){
			textManager.clearTextArea(1);
			player_pos = String.format(uiPhrases.get("playerPosition"), player.getX(), player.getY());
			textManager.writeText(player_pos, 1);
			textManager.writeText(pointer_pos, 1);
			textManager.writeText(objective, 1);
			textManager.writeText(caption, 1);
		}else{
			player_pos = String.format(uiPhrases.get("playerPosition"), player.getX(), player.getY());
			textManager.writeText(player_pos, 1);
			if (dimension==1){
				textManager.writeText(uiPhrases.get("inCorridor"), 1);
			}else{
				String room_dim = String.format(uiPhrases.get("roomIs"),actualRoom.getWide(), actualRoom.getHeight());
				Position dungCenter = new Position((world.getWidth()/2)-1, (world.getHeight()/2)-1, player.getZ());
				
				String cardinal = getter.getDirectTranslation("CheckEnviromentScreen", playerp.getRelativePosition(dungCenter));
				textManager.writeText(room_dim, 1);
				textManager.writeText(cardinal, 1);
			}
			
			HashMap<String, ArrayList<Position>> visible = player.getVisibleThings();
			ArrayList<Position> creatures = visible.get("Creatures");
			for(Position p : creatures){ 
				Creature c = world.creature(p.getIntX(), p.getIntY(), p.getZ()); //TODO esto deberia ser por keys
				if (aux_strings.contains(c.name())) {
					index = aux_strings.indexOf(c.name());
					aux_int.set(index, aux_int.get(index)+1);
				} else {
					aux_strings.add(c.name());
					index = aux_strings.indexOf(c.name());
					aux_int.add(index, 1);
				}
			}
			i = 0 ;
			for (String s : aux_strings) {
				String text = String.format(s + " : " + aux_int.get(i));
				textManager.writeText(text, 1);
				i++;
			}
			aux_strings.clear();
			aux_int.clear();
			ArrayList<Position> items = visible.get("Items");
			for (Position p : items) {
				Item item = world.item(p.getIntX(), p.getIntY(), p.getZ());
				if (aux_strings.contains(this.player.nameOf(item))) {
					index = aux_strings.indexOf(this.player.nameOf(item));
					aux_int.set(index, ((aux_int.get(index)) + 1)); 
				} else {
					aux_strings.add(this.player.nameOf(item));
					index = aux_strings.indexOf(this.player.nameOf(item));
					aux_int.add(index, 1);
				}
			}
			i = 0;
			for (String s : aux_strings) {
				String text = String.format(s + " : " + aux_int.get(i));
				textManager.writeText(text, 1);
				i++;
			}
			ArrayList<Position> stairs = visible.get("Stairs");
			for (Position p : stairs) {
				Tile t = world.tile(p.getIntX(), p.getIntY(), p.getZ());
				textManager.writeText(t.getDetails(), 1);
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
		ArrayList<Position> neighbours = p.getNeighbors(8); //p tampoco tiene la Y correcta
		ArrayList<Position> ground_tiles = new ArrayList<>();
		int height= this.world.getHeight();
		Tile north, south, west, east;
		for (Position n : neighbours) {
			if (world.tile(n.getIntX(),(height-1)-n.getIntY(), n.getZ()).isGround()) { //TODO aqui habria que hacer la conversion a posiciones de la pantalla
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
			north = world.tile(p.getPositionN().getIntX(), (height-1) - p.getPositionN().getIntY(), p.getZ());
			south = world.tile(p.getPositionS().getIntX(), (height-1) - p.getPositionS().getIntY(), p.getZ());
			if (!north.isGround() && !south.isGround()) {
				return false;
			} else {
				west = world.tile(p.getPositionW().getIntX(),(height-1) - p.getPositionW().getIntY(), p.getZ());
				east = world.tile(p.getPositionE().getIntX(), (height-1) - p.getPositionE().getIntY(), p.getZ());
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
