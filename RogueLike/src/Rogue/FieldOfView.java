package Rogue;

public class FieldOfView {

	private World world;
	private int depth;
	
	private boolean[][] visible;
	
	public boolean isVisible(int x, int y, int z){
        return (z == depth) && (x >= 0) && (y >= 0) && (x < visible.length) && (y < visible[0].length) && (visible[x][y]);
	}
	
	private Tile[][][] tiles;
	public Tile tile(int x, int y, int z){
		return tiles[x][y][z];
	}
	
	public FieldOfView(World world){
		this.world = world;
		this.visible = new boolean[world.width()][world.height()];
		this.tiles = new Tile[world.width()][world.height()][world.depth()];
		
		for (int i = 0; i < world.width(); i++){
			for (int j = 0; j<world.height(); j++){
				for (int z = 0; z< world.depth(); z++){
					tiles[i][j][z] = Tile.UNKNOWN;
				}
			}
		}
	}
	
	public void update(int wx, int wy, int wz, int r){
		depth = wz;
		visible = new boolean[world.width()][world.height()];
		
		for (int x = -r; x < r; x++){
			for (int y = -r; y < r; y++){
				if (x*x + y*y > r*r)
					continue;
				
				if (wx + x < 0 || wx + x >= world.width() || wy + y < 0 || wy + y >= world.height())
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
