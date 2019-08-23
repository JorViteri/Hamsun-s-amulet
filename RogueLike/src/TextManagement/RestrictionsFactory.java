package TextManagement;

public class RestrictionsFactory {

	private static RestrictionsFactory factory;

	private RestrictionsFactory() {
	}

	public static RestrictionsFactory getInstance() {
		if (factory == null) {
			factory = new RestrictionsFactory();
		}
		return factory;
	}

	public Restrictions getRestrictionsVbSuj(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen,
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
			String SujGen, String SujNum, String atrGen, String atrNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, null, null, null, null,
				atrGen, atrNum);
	}

	public Restrictions getRestrictionsVbSujCdCi(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String cdGen, String cdNum, String ciGen, String ciNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, cdGen, cdNum, ciGen, ciNum, null, null,
				null, null);
	}

	public Restrictions getRestrictionsVbSujCiCc(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String ciGen, String ciNum, String ccGen, String ccNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, ciGen, ciNum, ccGen, ccNum,
				null, null);
	}

	public Restrictions getRestrictionsVbSujCdCc(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String cdGen, String cdNum, String ccGen, String ccNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, cdGen, cdNum, null, null, ccGen, ccNum,
				null, null);
	}

}