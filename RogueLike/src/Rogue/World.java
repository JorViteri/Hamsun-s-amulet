package Rogue;

import java.awt.Color;
import java.util.ArrayList;

import DungeonComponents.Staircase;
import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import Utils.Position;


public class World {

	private Tile[][][] tiles;
	
	private int width;
	public int width() {
		return width;
	}

	private int height;
	public int height() {
		return height;
	}
	
	private int depth;
	public int depth(){
		return depth;
	}
	
	private Item[][][] items;
	
	private Position exit;

	private ArrayList<Staircase> stairs_list;
	private ArrayList<Creature> creatures;
	
	public World(Tile[][][] tiles, ArrayList<Staircase> stairs, Position exit) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;
		this.creatures = new ArrayList<Creature>();
		this.stairs_list = stairs;
		this.items = new Item[width][height][depth];
		this.exit = exit;
	}
	
	public ArrayList<Staircase> getStairs(){
		return this.stairs_list;
	}
	
	public Position getExit(){
		return exit;
	}

	
	public Creature creature(int x, int y, int z){
		for (Creature c : creatures){
			if (c.getX() == x && c.getY() == y && c.getZ() == z)
				return c;
		}
		return null;
	}
	
	public void remove(Creature other){
		creatures.remove(other);
	}
	
	public void remove(int x, int y, int z) {
	    items[x][y][z] = null;
	}
	
	public Tile tile(int x, int y, int z) {
		if (x < 0 || x >= width || y < 0 || y >= height|| z < 0 || z >= depth)
			return Tile.BOUNDS;
		else
			return tiles[x][y][z];
	}

	public char glyph(int x, int y, int z){
	    Creature creature = creature(x, y, z);
	    if (creature != null)
	        return creature.glyph();
	    
	    if (item(x,y,z) != null)
	        return item(x,y,z).getGlyph();
	    
	    return tile(x, y, z).glyph();
	}


	public Color color(int x, int y, int z){
	    Creature creature = creature(x, y, z);
	    if (creature != null)
	        return creature.color();
	    
	    if (item(x,y,z) != null)
	        return item(x,y,z).getColor();
	    
	    return tile(x, y, z).color();
	}

	public void dig(int x, int y, int z) {
		if (tile(x, y, z).isDiggable())
			tiles[x][y] [z]= Tile.FLOOR;
	}
	
	public Item item(int x, int y, int z){
		return items[x][y][z];
	}
	
	public void remove(Item item) {
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                for (int z = 0; z < depth; z++){
                if (items[x][y][z] == item) {
                    items[x][y][z] = null;
                    return;
                }
            }
        }
    }
}

	public void addAtEmptyLocation(Creature creature, int z){
		int x, y;
		
		do {
			x = (int) (Math.random()*width);
			y = (int) (Math.random()*height);
		}
		while (!tile(x,y,z).isGround()||creature(x,y,z)!=null);

		creature.setX(x);
		creature.setY(y);
		creature.setZ(z);
		
		creatures.add(creature);
	}
	
	public void addAtEmptyLocation(Item item, int depth){
		int x;
		int y;
		
		do{
			x = (int) (Math.random()*width);
			y = (int) (Math.random()*height);
		}
		while (!tile(x,y,depth).canPutItem() || item(x,y,depth) != null); 
		
	    items[x][y][depth] = item;
	}
	
	
	public boolean addAtEmptySpace(Item item, int x, int y, int z){
	    if (item == null)
	        return true;
	    
	    ArrayList<Position> positions = new ArrayList<Position>();
	    ArrayList<Position> checked = new ArrayList<Position>();
	    
	    positions.add(new Position(x,y,z));
	    
	    while (!positions.isEmpty()){
	        Position p = positions.remove(0);
	        checked.add(p);
	        
	        if (!tile(p.getIntX(), p.getIntY(), p.getZ()).isGround())
	            continue;
	         
	        if (items[p.getIntX()][p.getIntY()][p.getZ()] == null){
	        	items[p.getIntX()][p.getIntY()][p.getZ()] = item;
	            Creature c = this.creature(p.getIntX(), p.getIntY(), p.getZ());
	            if (c != null)
	                c.notify("A %s lands between your feet.", c.nameOf(item));
	            return true;
	        } else {
	            ArrayList<Position> neighbors = p.getNeighbors(8);
	            neighbors.removeAll(checked);
	            positions.addAll(neighbors);
	        }
	    }
	    return false;
	}
	
	
	public void update(){
		ArrayList<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for (Creature creature: toUpdate){
			creature.update();
		}
	}
	

	public void addAtExitStairs(Creature player) {
		player.setX(exit.getIntX());
		player.setY((height-1)-exit.getIntY());
		player.setZ(exit.getZ());
		creatures.add(player);
	}
	
	public void add(Creature pet) {
		creatures.add(pet);
	}
}
