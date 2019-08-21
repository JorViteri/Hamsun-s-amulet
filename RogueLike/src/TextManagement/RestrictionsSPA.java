package TextManagement;

import java.util.HashMap;

public class RestrictionsSPA implements Restrictions {

	private HashMap<String, String> restrictions = new HashMap<>();

	public RestrictionsSPA(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen, String SujNum,
			String CDGen, String CDNum, String CIGen, String CINum, String CCGen, String CCNum, String AtrGenere, String AtrNumber) {
		restrictions.put("VbNum", VbNum);
		restrictions.put("VbPerson", VbPerson);
		restrictions.put("VbForm", VbForm);
		restrictions.put("VbTime", VbTime);
		restrictions.put("SujGen", SujGen);
		restrictions.put("SujNum", SujNum);
		restrictions.put("CDGen", CDGen);
		restrictions.put("CDNum", CDNum);
		restrictions.put("CIGen", CIGen);
		restrictions.put("CINum", CINum);
		restrictions.put("CCGen", CCGen);
		restrictions.put("CCNum", CCNum);
		restrictions.put("AtrGen", AtrGenere);
		restrictions.put("AtrNum", AtrNumber);
	}

	@Override
	public HashMap<String, String> getRestrictions() {
		return restrictions;
	}

}
