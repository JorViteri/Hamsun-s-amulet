package TextManagement;


public class RestrictionsFactory {
	
	private String language;
	
	public RestrictionsFactory(String language){
		this.language = language;
	}
	
	public Restrictions getRestrictions(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen, String SujNum, String CDGen, String CDNum){ //TODO le paso todas las entradas posiblemente necesarias
		switch(language){
		case "SPA":
			RestrictionsSPA  result_spa = new RestrictionsSPA( VbNum, VbPerson,VbForm,  VbTime,SujGen,  SujNum,  CDGen, CDNum);
			return result_spa;
		case "ENG":
			RestrictionsENG result_eng = new RestrictionsENG(VbNum, VbPerson,  VbForm,VbTime, SujNum, CDNum);
			return result_eng;
		default:
			return null;
		}
	}

}
