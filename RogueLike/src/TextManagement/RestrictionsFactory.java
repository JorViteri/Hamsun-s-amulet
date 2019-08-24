package TextManagement;

public class RestrictionsFactory {

	private static RestrictionsFactory factory;

	/**
	 * Constructor
	 */
	private RestrictionsFactory() {
	}

	/**
	 * Singleton function
	 * @return
	 */
	public static RestrictionsFactory getInstance() {
		if (factory == null) {
			factory = new RestrictionsFactory();
		}
		return factory;
	}

	/**
	 * Calls the Restrictions constructor for moments when only the subject and the verb are needed
	 * @param VbNum
	 * @param VbPerson
	 * @param VbForm
	 * @param VbTime
	 * @param SujGen
	 * @param SujNum
	 * @return
	 */
	public Restrictions getRestrictionsVbSuj(String VbNum, String VbPerson, String VbForm, String VbTime, String SujGen,
			String SujNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, null, null, null, null,
				null, null);

	}

	/**
	 * Calls the Restrictions constructor for moments when only the subject and the verb and the cc are needed
	 * @param VbNum
	 * @param VbPerson
	 * @param VbForm
	 * @param VbTime
	 * @param SujGen
	 * @param SujNum
	 * @param ccGen
	 * @param ccNum
	 * @return
	 */
	public Restrictions getRestrictionsVbSujCc(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String ccGen, String ccNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, null, null, ccGen, ccNum,
				null, null);
	}

	/**
	 * Calls the Restrictions constructor for moments when only the verb and the object are needed
	 * @param VbNum
	 * @param VbPerson
	 * @param VbForm
	 * @param VbTime
	 * @param cdGen
	 * @param cdNum
	 * @return
	 */
	public Restrictions getRestrictionsVbCd(String VbNum, String VbPerson, String VbForm, String VbTime, String cdGen,
			String cdNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, null, null, cdGen, cdNum, null, null, null, null, null,
				null);
	}

	/**
	 * Calls the Restrictions constructor for moments when only the subject, the verb and the object are needed
	 * @param VbNum
	 * @param VbPerson
	 * @param VbForm
	 * @param VbTime
	 * @param SujGen
	 * @param SujNum
	 * @param cdGen
	 * @param cdNum
	 * @return
	 */
	public Restrictions getRestrictionsVbSujCd(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String cdGen, String cdNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, cdGen, cdNum, null, null, null, null,
				null, null);
	}

	/**
	 * Calls the Restrictions constructor for moments when only the subject, the verb and the atribute are needed
	 * @param VbNum
	 * @param VbPerson
	 * @param VbForm
	 * @param VbTime
	 * @param SujGen
	 * @param SujNum
	 * @param atrGen
	 * @param atrNum
	 * @return
	 */
	public Restrictions getRestrictionsVbSujAtr(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String atrGen, String atrNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, null, null, null, null,
				atrGen, atrNum);
	}

	/**
	 * Calls the Restrictions constructor for moments when only the subject, the verb, the object and the indirect object are needed
	 * @param VbNum
	 * @param VbPerson
	 * @param VbForm
	 * @param VbTime
	 * @param SujGen
	 * @param SujNum
	 * @param cdGen
	 * @param cdNum
	 * @param ciGen
	 * @param ciNum
	 * @return
	 */
	public Restrictions getRestrictionsVbSujCdCi(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String cdGen, String cdNum, String ciGen, String ciNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, cdGen, cdNum, ciGen, ciNum, null, null,
				null, null);
	}

	/**
	 * Calls the Restrictions constructor for moments when only the subject, the verb, the cc and the indirect object are needed
	 * @param VbNum
	 * @param VbPerson
	 * @param VbForm
	 * @param VbTime
	 * @param SujGen
	 * @param SujNum
	 * @param ciGen
	 * @param ciNum
	 * @param ccGen
	 * @param ccNum
	 * @return
	 */
	public Restrictions getRestrictionsVbSujCiCc(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String ciGen, String ciNum, String ccGen, String ccNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, null, null, ciGen, ciNum, ccGen, ccNum,
				null, null);
	}

	/**
	 * Calls the Restrictions constructor for moments when only the subject, the verb, the object and the cc are needed
	 * @param VbNum
	 * @param VbPerson
	 * @param VbForm
	 * @param VbTime
	 * @param SujGen
	 * @param SujNum
	 * @param cdGen
	 * @param cdNum
	 * @param ccGen
	 * @param ccNum
	 * @return
	 */
	public Restrictions getRestrictionsVbSujCdCc(String VbNum, String VbPerson, String VbForm, String VbTime,
			String SujGen, String SujNum, String cdGen, String cdNum, String ccGen, String ccNum) {
		return new Restrictions(VbNum, VbPerson, VbForm, VbTime, SujGen, SujNum, cdGen, cdNum, null, null, ccGen, ccNum,
				null, null);
	}

}