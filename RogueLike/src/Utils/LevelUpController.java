package Utils;

/**
 * Class that manages the options of the LevelUpScreen
 */
import java.util.ArrayList;
import java.util.List;

import Elements.Creature;
import TextManagement.WordDataGetter;
import TextManagement.WordDataGetterAndRealizatorFactory;

public class LevelUpController {

	private static LevelUpOption[] options = new LevelUpOption[] { new LevelUpOption(WordDataGetterAndRealizatorFactory
			.getInstance().getWordDataGetter().getDirectTranslation("LevelUpController", "hitPoints")) {
		public void invoke(Creature creature) {
			creature.gainMaxHp();
		}
	}, new LevelUpOption(WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter()
			.getDirectTranslation("LevelUpController", "attackValue")) {
		public void invoke(Creature creature) {
			creature.gainAttackValue();
		}
	}, new LevelUpOption(WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter()
			.getDirectTranslation("LevelUpController", "defenseValue")) {
		public void invoke(Creature creature) {
			creature.gainDefenseValue();
		}
	}, new LevelUpOption(WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter()
			.getDirectTranslation("LevelUpController", "awarenessValue")) {
		public void invoke(Creature creature) {
			creature.gainVision();
		}
	}, new LevelUpOption(WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter()
			.getDirectTranslation("LevelUpController", "hpRegeneration")) {
		public void invoke(Creature creature) {
			creature.modifyRegenHpPer1000(3);
		}

	}, new LevelUpOption(WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter()
			.getDirectTranslation("LevelUpController", "manaPoints")) {
		public void invoke(Creature creature) {
			creature.gainMaxMana();
		}
	}, new LevelUpOption(WordDataGetterAndRealizatorFactory.getInstance().getWordDataGetter()
			.getDirectTranslation("LevelUpController", "manaRegeneration")) {
		public void invoke(Creature creature) {
			creature.gainRegenMana();
		}
	} };

	public void autoLevelUp(Creature creature) {
		options[(int) (Math.random() * options.length)].invoke(creature);
	}

	public List<String> getLevelUpOptions() {
		List<String> names = new ArrayList<String>();
		for (LevelUpOption option : options) {
			names.add(option.getName());
		}
		return names;
	}

	public LevelUpOption getLevelUpOption(String name) {
		for (LevelUpOption option : options) {
			if (option.getName().equals(name)) {
				return option;
			}
		}
		return null;
	}
}
