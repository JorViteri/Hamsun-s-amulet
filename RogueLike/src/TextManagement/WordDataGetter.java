package TextManagement;

import java.util.ArrayList;

public interface WordDataGetter {
	
	public ArrayList<String> getNounData(String noun);
	
	public ArrayList<String> getAdjData(String adj, String genere);
	
	public ArrayList<String> getVerbData(String verb);
	
	public ArrayList<String> getAdvData(String adv);

	public String getRandomSeed(String[] arr); //TODO anhadir implementacion default a esta 

}
