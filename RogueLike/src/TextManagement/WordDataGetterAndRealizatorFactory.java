package TextManagement;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class WordDataGetterAndRealizatorFactory {
	
	private static String language; 
	private static WordDataGetter getter;
	private static Realizator realizator;
	private static WordDataGetterAndRealizatorFactory factory;
	
	private WordDataGetterAndRealizatorFactory(){
		Properties prop = new Properties();
		InputStream input;
		try {
			input =  new FileInputStream("language.properties");
			prop.load(input);
		} catch (Exception e){
			e.printStackTrace();
		}	
		this.language = prop.getProperty("language");
	}
	
	public static WordDataGetterAndRealizatorFactory getInstance(){
		if (factory==null){
			factory = new WordDataGetterAndRealizatorFactory();
			language = factory.getLanguage();
		}
		return factory;
	}

	public WordDataGetter getWordDataGetter() {
		if (getter == null) {
			switch (language) {
			case "SPA":
				getter = WordDataGetterSPA.getSingletonInstance();
				break;
			case "ENG":
				getter = WordDataGetterENG.getSingletonInstance();
				break;
			default:
				return null;
			}
		}
		return getter;
	}

	public Realizator getRealizator(){
		if (realizator == null) {
			switch (language) {
			case "SPA":
				realizator = RealizatorSPA.getInstance();
				break;
			case "ENG":
				realizator = RealizatorENG.getInstance();
				break;
			default:
				return null;
			}
		}
		return realizator;
	}

	public String getLanguage() {
		return language;
	}
}
