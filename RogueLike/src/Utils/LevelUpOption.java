package Utils;

import Elements.Creature;

public abstract class LevelUpOption {

	private String name;
	
	public String getName(){
		return name;
	}
	
	public LevelUpOption(String name){
		this.name = name;
	}
	
	public abstract void invoke(Creature creature);
}
