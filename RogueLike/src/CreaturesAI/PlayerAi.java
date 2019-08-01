package CreaturesAI;

import java.util.ArrayList;

import DungeonComponents.Tile;
import Elements.Creature;
import Elements.Item;
import Utils.FieldOfView;

/**
 * Implements the behaviors that the player can do
 */


public class PlayerAi extends CreatureAi {
	
	private ArrayList<String> messages;
	private FieldOfView fov;
	
	public PlayerAi(Creature creature, ArrayList<String> messages,  FieldOfView fov){
		super(creature);
		this.messages = messages;
		this.fov = fov;
	}
	
	@Override
	public void onEnter(int x, int y, int z, Tile tile){
		if (tile.isGround()){
			creature.setX(x);
			creature.setY(y);
			creature.setZ(z);
			Item item = creature.item(creature.getX(), creature.getY(), creature.getZ());
			if (item != null){
				creature.notify("There's a " + creature.nameOf(item) + " here.");
			}
			if (tile.isStair()){
				
				creature.notify("There're " + tile.getChairTypeString() + " here."); //tengo que hacer que se 
			}
				
		}/*else if (tile.isDiggable()) {
			creature.dig(x, y, z);
		}*/
	}
	
	public void onNotify(String message){
		messages.add(message);
	}
	
	public boolean canSee(int wx, int wy, int wz){
		return fov.isVisible(wx,wy,wz);
	}
	
	@Override
	public void onGainLevel(){
		
	}
	
	@Override
	public Tile rememberedTile(int wx, int wy, int wz) {
        return fov.tile(wx, wy, wz);
    }
}
