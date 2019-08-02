package TextManagement;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class WordDataGetterFactory {
	
	private static String language; 
	private static WordDataGetter getter;
	private static WordDataGetterFactory factory;
	
	private WordDataGetterFactory(){
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
	
	public static WordDataGetterFactory getInstance(){
		if (factory==null){
			factory = new WordDataGetterFactory();
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

	public String getLanguage() {
		return language;
	}
}
