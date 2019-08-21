package TextManagement;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class RestrictionsFactory {

	private String language;
	private static RestrictionsFactory factory;

	private RestrictionsFactory() {
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

	public static RestrictionsFactory getInstance() {
		if (factory == null) {
			factory = new RestrictionsFactory();
		}
		return factory;
	}

	public Restrictions getRestrictions(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen,
			String SujNum, String CDGen, String CDNum, String CIGen, String CINum, String CCIGen, String CCINum, String AtrGenere, String AtrNumber) {
		switch (language) {
		case "SPA":
			RestrictionsSPA result_spa = new RestrictionsSPA(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen,
					CDNum, CIGen, CINum, CCIGen, CCINum, AtrGenere, AtrNumber);
			return result_spa;
		case "ENG":
			RestrictionsENG result_eng = new RestrictionsENG(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, CDGen,
					CDNum, CIGen, CINum, CCIGen, CCINum,AtrGenere, AtrNumber);
			return result_eng;
		default:
			return null;
		}
	}

}
