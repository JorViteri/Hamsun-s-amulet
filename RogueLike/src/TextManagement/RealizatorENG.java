package TextManagement;

import java.util.HashMap;

public class RealizatorENG implements Realizator{
	
	private WordDataGetter getter;
	private static RealizatorENG realizator;
	
	private RealizatorENG(){
		WordDataGetterAndRealizatorFactory factory = WordDataGetterAndRealizatorFactory.getInstance();
		this.getter = factory.getWordDataGetter();
	}
	
	public static RealizatorENG getInstance(){
		if (realizator == null){
			realizator = new RealizatorENG();
		}
		return realizator;
	}
	

	@Override
	public String realizatePhrase(String actionType, HashMap<String, String> Subject, HashMap<String, String> CD,
			HashMap<String, String> CI, HashMap<String, String> CII, Restrictions restrictions, String templateType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Integer> getTemplate(String actionType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String constructNounAppareance(String noun, String appareance) {
		return appareance + " " + noun;
	}

	@Override
	public String constructNounAndNoun(String noun, String descrpNoun) {
		return descrpNoun + " " + noun;
	}

}
