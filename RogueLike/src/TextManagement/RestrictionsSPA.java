package TextManagement;

import java.util.HashMap;

public class RestrictionsSPA implements Restrictions {

	private HashMap<String, String> restrictions;

	public RestrictionsSPA(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen, String SujNum,
			String CDGen, String CDNum, String CIGen, String CINum, String CCIGen, String CCINum) {
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
		restrictions.put("CCIGen", CCIGen);
		restrictions.put("CCINum", CCINum);
	}

	@Override
	public HashMap<String, String> getRestrictions() {
		return restrictions;
	}

	public String getVbNumRestriction() {
		return this.restrictions.get("VbNum");
	}

	public String getVbPersonRestriction() {
		return this.restrictions.get("VbPerson");
	}

	public String getVbFormRestriction() {
		return this.restrictions.get("VbForm");
	}

	public String getVbTimeRestriction() {
		return this.restrictions.get("VbTime");
	}

	public String getSujGenRestriction() {
		return this.restrictions.get("SujGen");
	}

	public String getSujNumRestriction() {
		return this.restrictions.get("SujNum");
	}

	public String getCDGenRestriction() {
		return this.restrictions.get("CDGen");
	}

	public String getCDNumRestriction() {
		return this.restrictions.get("CDNum");
	}
}
