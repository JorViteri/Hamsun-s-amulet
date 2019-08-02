package TextManagement;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class WordDataGetterFactory {
	
	private String language; 
	
	public WordDataGetterFactory(){
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
	
	public WordDataGetter getWordDataGetter(){
		switch (language){
		case "SPA":
			WordDataGetterSPA result = new WordDataGetterSPA();
			return result;
		case "ENG":
			WordDataGetterENG result1 = new WordDataGetterENG();
			return result1;
		default:
			return null;
		}
	}
	
	public String getLanguage(){
		return language;
	}
}
