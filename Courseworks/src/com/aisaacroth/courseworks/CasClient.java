package com.aisaacroth.courseworks;

import org.jasig.cas.util.HttpClient;
import org.jasig.cas.web.support.CasArgumentExtractor;

public class CasClient {
	private static final String TAG = "CASCLIENT";
	private static final String CAS_LOGIN_URL_PART = "login";
	private static final String CAS_LOGOUT_URL_PART = "logout";
	private static final String CAS_SERVICE_VALIDATE_URL_PART = "serviceValidate";
	private static final String CAS_TICKET_BEGIN = "ticket=";
	private static final String CAS_LT_BEGIN = "name=\"lt\" value=\"";
	private static final String CAS_USER_BEGIN = "<cas:user>";
	private static final String CAS_USER_END = "</cas:user>";
}
