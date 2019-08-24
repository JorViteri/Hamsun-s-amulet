package Rogue;

/**
 * Defines the World class which represents the dungeon
 */
import java.awt.Color;
import java.util.ArrayList;
import DungeonComponents.Corridor;
import DungeonComponents.Room;
import DungeonComponents.Staircase;
import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import TextManagement.WordDataGetter;
import Utils.Position;


public class World {

	private Tile[][][] tiles;
	
	private int width;
	public int getWidth() {
		return width;
	}

	private int height;
	public int getHeight() {
		return height;
	}
	
	private int depth;

	public int getDepth() {
		return depth;
	}

	private Item[][][] items;

	private Position exit;

	private ArrayList<Staircase> stairs_list;
	private ArrayList<Creature> creatures;
	private ArrayList<ArrayList<Room>> rooms_lists;
	private ArrayList<ArrayList<Corridor>> corridors_lists;
	private WordDataGetter getter;

	/**
	 * Constructor
	 * @param tiles The tiles that form the dungeon
	 * @param stairs The stairs located in the dungeon
	 * @param exit The position which is the exit of the dungeon(a stair)
	 * @param rooms_lists The list of rooms (square like areas with a widht bigger than 1 tile)
	 * @param corridors_list The list of corridors that unite rooms (1 tile of width)
	 */
	public World(Tile[][][] tiles, ArrayList<Staircase> stairs, Position exit, ArrayList<ArrayList<Room>> rooms_lists,
			ArrayList<ArrayList<Corridor>> corridors_list) {
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;
		this.creatures = new ArrayList<Creature>();
		this.stairs_list = stairs;
		this.items = new Item[width][height][depth];
		this.exit = exit;
		this.rooms_lists = rooms_lists;
		this.corridors_lists = corridors_list;
	}
	
	public ArrayList<ArrayList<Room>> getRoomLists(){
		return rooms_lists;
	}
	
	public ArrayList<ArrayList<Corridor>> getCorridorLists(){
		return corridors_lists;
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

	/**
	 * Adds a creature in the dungeon
	 * @param creature the creature thar is going to be added
	 * @param z the level of the dungeon in which will be placed
	 */
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
	
	/**
	 * Add an item to the dungeon
	 * @param item the Item that is going to be added
	 * @param depth the level of the dungeon in which will be placed
	 */
	public void addAtEmptyLocation(Item item, int depth){
		int x;
		int y;
		
		do{
			x = (int) (Math.random()*width);
			y = (int) (Math.random()*height);
		}
		while (!tile(x,y,depth).canPutItem() || item(x,y,depth) != null); 
		
	    items[x][y][depth] = item;
	    item.setX(x);
	    item.setY(y);
	    item.setZ(depth);
	}
	
	/**
	 * Add an item in an empty tile (when dropping an item for exaple)
	 * @param item item that will be placed
	 * @param x x of the position
	 * @param y y of the position
	 * @param z z of the position
	 * @return true if there was success, false on the contrary case
	 */
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

	public WordDataGetter getWordDataGetter() {
		return this.getter;
	}
}