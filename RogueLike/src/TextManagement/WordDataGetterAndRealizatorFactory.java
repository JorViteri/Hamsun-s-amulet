package TextManagement;
/**
 * Factory that allows to obtain the WordDataGetter and the Realizator for the language indicated in the language.configuration file
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class WordDataGetterAndRealizatorFactory {
	
	private static String language; 
	private static WordDataGetter getter;
	private static Realizator realizator;
	private static WordDataGetterAndRealizatorFactory factory;
	
	/**
	 * Constructor 
	 */
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
	
	/**
	 * Singleton function
	 * @return the instance of this class
	 */
	public static WordDataGetterAndRealizatorFactory getInstance(){
		if (factory==null){
			factory = new WordDataGetterAndRealizatorFactory();
			language = factory.getLanguage();
		}
		return factory;
	}

	/**
	 * Obtains or generates the instance of the WordDataGetter
	 * @return the instance of the getter
	 */
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

	/**
	 * Obtains or creates the instance of the Realizator
	 * @return
	 */
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

	/**
	 * Returns the language defined in the language.configuration file
	 * @return the language as a string
	 */
	public String getLanguage() {
		return language;
	}
}
