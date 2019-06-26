package Utils;

import java.util.ArrayList;
import java.util.List;

import Elements.Creature;

public class LevelUpController {

	private static LevelUpOption[] options =
	new LevelUpOption[] { new LevelUpOption("Increased hit points") {
		public void invoke(Creature creature) {
			creature.gainMaxHp();
		}
	}, new LevelUpOption("Increased attack value") {
		public void invoke(Creature creature) {
			creature.gainAttackValue();
		}
	}, new LevelUpOption("Increased defense value") {
		public void invoke(Creature creature) {
			creature.gainDefenseValue();
		}
	}, new LevelUpOption("Increased vision") {
		public void invoke(Creature creature) {
			creature.gainVision();
		}
	}, new LevelUpOption("Increased hp regeneration") {
		public void invoke(Creature creature) {
			creature.modifyRegenHpPer1000(3);
		}

	}, new LevelUpOption("Increased mana") {
		public void invoke(Creature creature) {
			creature.gainMaxMana();
		}
	}, new LevelUpOption("Increased mana regeneration") {
		public void invoke(Creature creature) {
			creature.gainRegenMana();
		}
	} };
	
	public void autoLevelUp(Creature creature){
		options[(int)(Math.random()*options.length)].invoke(creature);
	}
	
	public List<String> getLevelUpOptions(){
		List<String> names = new ArrayList<String>();
		for(LevelUpOption option : options){
			names.add(option.getName());
		}
		return names;
	}
	
	public LevelUpOption getLevelUpOption(String name){
		for(LevelUpOption option : options){
			if(option.getName().equals(name)){
				return option;
			}
		}
		return null;
	}
}
