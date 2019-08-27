package Utils;

/**
 * Defines the field of view of a creature
 */
import DungeonComponents.Line;
import DungeonComponents.Tile;
import Rogue.World;

public class FieldOfView {

	private World world;
	private int depth;
	
	private boolean[][] visible;
	
	/**
	 * Checks if a position is within the field of vision
	 * @param x coordinate in x
	 * @param y coordinate in y 
	 * @param z level of the dungeon
	 * @return true if it's within the field, else false
	 */
	public boolean isVisible(int x, int y, int z){
        return (z == depth) && (x >= 0) && (y >= 0) && (x < visible.length) && (y < visible[0].length) && (visible[x][y]);
	}
	
	private Tile[][][] tiles;
	public Tile tile(int x, int y, int z){
		return tiles[x][y][z];
	}
	
	/**
	 * Constructor
	 * @param world the dungeon so it can interact with it
	 */
	public FieldOfView(World world){
		this.world = world;
		this.visible = new boolean[world.getWidth()][world.getHeight()];
		this.tiles = new Tile[world.getWidth()][world.getHeight()][world.getDepth()];
		
		for (int i = 0; i < world.getWidth(); i++){
			for (int j = 0; j<world.getHeight(); j++){
				for (int z = 0; z< world.getDepth(); z++){
					tiles[i][j][z] = Tile.UNKNOWN;
				}
			}
		}
	}
	
	/**
	 * Updates the field of view
	 * @param wx coordinate in x 
	 * @param wy coordinate in y
	 * @param wz level of the dungeon
	 * @param r radius of the field
	 */
	public void update(int wx, int wy, int wz, int r){
		depth = wz;
		visible = new boolean[world.getWidth()][world.getHeight()];
		
		for (int x = -r; x < r; x++){
			for (int y = -r; y < r; y++){
				if (x*x + y*y > r*r)
					continue;
				
				if (wx + x < 0 || wx + x >= world.getWidth() || wy + y < 0 || wy + y >= world.getHeight())
					continue;
				
				for (Position p : new Line(wx, wy, wx + x, wy + y)){
					Tile tile = world.tile(p.getIntX(), p.getIntY(), wz);
					visible[p.getIntX()][p.getIntY()] = true;
					tiles[p.getIntX()][p.getIntY()][wz] = tile; 
					
					if (!tile.isGround())
						break;
				}
			}
		}
		
	}
	
}
