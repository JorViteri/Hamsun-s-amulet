package DungeonComponents;

import java.awt.Color;
import java.util.HashMap;

import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;
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
	
	//TODO debo suponer que esto solo se llama en caso de que el Tile sea una escalera, lo mismo el siguiente
	public HashMap<String, String> getMorfStairs(){
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> data = new HashMap<>();
		HashMap<String, String> nameData = getter.getNounData("stairs");
		data.put("genere", nameData.get("genere")); 
		data.put("number", "plural");
		return data;
	}
	
	public HashMap<String, String> getStairsNounAndType(){
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> data = new HashMap<>();
		HashMap<String, String> nameData = getter.getNounData("stairs");
		HashMap<String, String> typeData = getter.getAdjData(this.getChairTypeString(), nameData.get("genere"));
		
		data.put("name", nameData.get("baseNoun"));
		data.put("characteristic", typeData.get("singular"));  //TODO wtf was this?
		return data;
	}
	
	public String getChairTypeString(){ //TODO this one will be tricky in the translation thing
		switch(this){
		case STAIRS_DOWN:
			return "descending";
		case STAIRS_UP:
			return "upward";
		default:
			return "";
		}
	}
	
}
