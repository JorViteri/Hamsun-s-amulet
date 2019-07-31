package TextManagement;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class WordDataGetterFactory {
	
	public WordDataGetterFactory(){
		
	}
	
	public WordDataGetter getWordDataGetter(){
		String language = null;
		Properties prop = new Properties();
		InputStream input;
		try {
			input =  new FileInputStream("language.properties");
			prop.load(input);
		} catch (Exception e){
			e.printStackTrace();
		}		
		language = prop.getProperty("language");
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
}
