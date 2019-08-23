package TextManagement;

import java.util.HashMap;

public class Restrictions {

	private HashMap<String, String> restrictions = new HashMap<>();

	public Restrictions(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen, String SujNum,
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

	
	public HashMap<String, String> getRestrictions() {
		return restrictions;
	}
	
	//Hacer funciones de este estilo
	public Restrictions getRestrictionsSujVb(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen,
			String SujNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, null, null, null, null,
				null, null);

	}

	public Restrictions getRestrictionsVbSujCc(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String ccGen, String ccNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, null, null, ccGen, ccNum,
				null, null);
	}

	public Restrictions getRestrictionsVbCd(String VbNum, String VbPerson, String VbForm, String VbTime, String cdGen,
			String cdNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, null, null, cdGen, cdNum, null, null, null, null, null,
				null);
	}

	public Restrictions getRestrictionsVbSujCd(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String cdGen, String cdNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, cdGen, cdNum, null, null, null, null,
				null, null);
	}
	
	public Restrictions getRestrictionsVbSujAtr(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String atrGen, String atrNum){
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, null, null, null, null,
				atrGen, atrNum);
	}
	
	public Restrictions getRestrictionsVbSujCdCi(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String cdGen, String cdNum, String ciGen, String ciNum){
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, cdGen, cdNum, ciGen, ciNum, null, null,
				null, null);
	}
	
	public Restrictions getRestrictionsVbSujCicC(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String ciGen, String ciNum, String ccGen, String ccNum){
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, ciGen, ciNum, ccGen, ccNum,
				null, null);
	}
	
	public Restrictions getRestrictionsVbSujcDcC(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String cdGen, String cdNum, String ccGen, String ccNum){
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, cdGen, cdNum, null, null, ccGen, ccNum,
				null, null);
	}

}
