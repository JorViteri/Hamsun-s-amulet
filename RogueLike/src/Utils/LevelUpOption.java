package Utils;

/**
 * Defines a leveling option for the LevelUpScreen
 */
import Elements.Creature;

public abstract class LevelUpOption {

	private String name;
	
	public String getName(){
		return name;
	}
	
	/**
	 * Constructor
	 * @param name of the option
	 */
	public LevelUpOption(String name){
		this.name = name;
	}
	
	/**
	 * With this function the option will call the change of stats in the creature
	 * @param creature that will change it's stats
	 */
	public abstract void invoke(Creature creature);
}
