package Elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Item {

	private char glyph;
	private Color color;
	private String key;
	private String name;
	private int foodValue;
	private int attackValue;
	private int defenseValue;
	private int thrownAttackValue;
	private int rangedAttackValue;
	private Effect quaffEffect;
	private List<Spell> writtenSpells;
	private String appearance;
	private int x;
	private int y;
	private int z;
	
	public Item(char glyph, Color color, String key,String name, String appearance) {
		this.glyph = glyph;
		this.color = color;
		this.key = key;
		this.name = name;
		this.thrownAttackValue = 1;
		this.writtenSpells = new ArrayList<Spell>();
		this.appearance = appearance == null ? name : appearance;
	}
	
	

	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public int getZ() {
		return z;
	}



	public void setZ(int z) {
		this.z = z;
	}



	public char getGlyph() {
		return glyph;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}
	
	public String getKey(){
		return key;
	}

	public int getFoodValue() {
		return foodValue;
	}
	
	public Effect getQuaffEffect(){
		return quaffEffect;
	}

	public void setQuaffEffect(Effect effect){
		this.quaffEffect = effect;
	}
	
	public void modifyFoodValue(int amount) {
		foodValue += amount;
	}

	public int getAttackValue() {
		return attackValue;
	}

	public int getThrownAttackValue() {
		return thrownAttackValue;
	}
	
	public int getRangedAttackValue(){
		return rangedAttackValue;
	}

	public void modifyAttackValue(int amount) {
		attackValue += amount;
	}

	public void modifyThrownAttackValue(int amount) {
		thrownAttackValue += amount;
	}
	
	public void modifyRangedAttackValue(int amount) {
		rangedAttackValue += amount;
	}

	public int getDefenseValue() {
		return defenseValue;
	}
	
	public List<Spell> getWrittenSpells(){
		return writtenSpells;
	}
	
	public String getAppearance(){
		if(appearance==null){
			return name;
		} else{
			return appearance;
		}
	}

	public void addWrittenSpell(String name, int manaCost, Effect effect, Boolean selfTarget){
		writtenSpells.add(new Spell(name, manaCost, effect, selfTarget));
	}
	public void modifyDefenseValue(int amount) {
		defenseValue += amount;
	}

	public String getDetails() {
		String details = "";

		if (attackValue != 0)
			details += "     attack:" + attackValue;
		
		if (thrownAttackValue != 1)
			details += "  thrown:" + thrownAttackValue;
		
		if (rangedAttackValue > 0)
			details += "  ranged:" + rangedAttackValue;
		
		if (defenseValue != 0)
			details += "     defense:" + defenseValue;

		if (foodValue != 0)
			details += "     food:" + foodValue;

		return details;
	}

}
