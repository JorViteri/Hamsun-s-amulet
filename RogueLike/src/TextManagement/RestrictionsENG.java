package TextManagement;

import java.util.HashMap;

public class RestrictionsENG implements Restrictions {

	private HashMap<String, String> restrictions;

	public RestrictionsENG(String VbNum, String VbPerson, String VbForm, String VbTime, String SujNum, String CDNum) {
		restrictions.put("VbNum", VbNum);
		restrictions.put("VbPerson", VbPerson);
		restrictions.put("VbForm", VbForm);
		restrictions.put("VbTime", VbTime);
		restrictions.put("SujNum", SujNum);
		restrictions.put("CDNum", CDNum);
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

	public String getSujNumRestriction() {
		return this.restrictions.get("SujNum");
	}

	public String getCDNumRestriction() {
		return this.restrictions.get("CDNum");
	}
}
