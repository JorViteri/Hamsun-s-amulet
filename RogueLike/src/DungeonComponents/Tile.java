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

	FLOOR((char) 250, '·',AsciiPanel.yellow,WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter().getDirectTranslation("Tile", "Floor")), 
	WALL((char) 177, '▒',AsciiPanel.yellow, WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter().getDirectTranslation("Tile", "Wall")), 
	BOUNDS('x', 'x',AsciiPanel.brightBlack, WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter().getDirectTranslation("Tile", "Bounds")), 
	STAIRS_DOWN('>', '>',AsciiPanel.white, WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter().getDirectTranslation("Tile", "StairsDown")), 
	STAIRS_UP('<', '<',AsciiPanel.white, WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter().getDirectTranslation("Tile", "StairsUp")), 
	UNKNOWN(' ', ' ',AsciiPanel.white, WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter().getDirectTranslation("Tile", "Unknown"));	
	
	private char glyph;
	public char glyph(){
		return glyph;
	}
	
	private char utf;
	public char utf(){
		return utf;
	}
	
	private Color color;
	public Color color() {
		return color;
	}

	private String details;

	public String getDetails() {
		return details;
	}

	Tile(char glyph, char utf, Color color, String details) {
		this.glyph = glyph;
		this.utf = utf;
		this.color = color;
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
	
	public HashMap<String, String> getStairsNounAndType(String number){
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		WordDataGetter getter = factory.getWordDataGetter();
		HashMap<String, String> data = new HashMap<>();
		HashMap<String, String> nameData = getter.getNounData("stairs");
		HashMap<String, String> typeData = getter.getAdjData(this.getChairTypeString(), nameData.get("genere"));
		
		data.put("name", nameData.get(number));
		data.put("characteristic", typeData.get(number));  
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
