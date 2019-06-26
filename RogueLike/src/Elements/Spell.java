package Elements;

public class Spell {
	
	private String name;
	private int manaCost;
	private Effect effect;
	private boolean selfTarget; //asi indico 
	
	public String getName(){
		return name;
	}
	
	public int getManaCost(){
		return manaCost;
	}
	
	public Effect getEffect(){
		return (Effect) effect.clone();
	}
	
	public boolean getSelfTarget(){
		return selfTarget;
	}
	
	public Spell(String name, int manaCost, Effect effect, boolean selfTarget){
		this.name = name;
		this.manaCost = manaCost;
		this.effect = effect;
		this.selfTarget = selfTarget;
	}

}
