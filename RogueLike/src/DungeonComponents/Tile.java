package DungeonComponents;

import java.awt.Color;
import asciiPanel.AsciiPanel;
/**
 * Defines the tile class win which the dungeon is divided
 * @author comec
 *
 */
public enum Tile {

	FLOOR((char) 250, AsciiPanel.yellow, "A dirt and rock cave floor."),
	WALL((char)177, AsciiPanel.yellow, "A dirt and rock cave wall."),
	BOUNDS('x', AsciiPanel.brightBlack, "Beyond the edge of the world."),
	STAIRS_DOWN('>', AsciiPanel.white, "A stone staircase that goes down."), 
	STAIRS_UP('<', AsciiPanel.white, "A stone staircase that goes up."),
	UNKNOWN(' ', AsciiPanel.white, "(unknown)");
	
	private char glyph;
	public char glyph(){
		return glyph;
	}
	
	private Color color;
	public Color color() {
		return color;
	}

	private String details;
	public String getDetails() {
		return details;
	}

	Tile(char glyph, Color color, String details){
		this.glyph= glyph;
		this.color=color;
		this.details = details;
	}
	
	public boolean isDiggable(){
		return this == Tile.WALL;
	}
	
	public boolean isGround(){
		return this !=WALL && this != BOUNDS; 
	}
	
	public boolean canPutItem(){
		return this !=WALL && this != BOUNDS && this !=STAIRS_UP && this != STAIRS_DOWN;
	}

	public boolean isStair(){
		if ((this == STAIRS_DOWN)||(this == STAIRS_UP)){
			return true;
		}	
		else{
			return false;
		}
	}
	
	public String getChairTypeString(){ //TODO this one will be tricky in the translation thing
		switch(this){
		case STAIRS_DOWN:
			return "stairs going down";
		case STAIRS_UP:
			return "stairs going up";
		default:
			return "";
		}
	}
	
}
