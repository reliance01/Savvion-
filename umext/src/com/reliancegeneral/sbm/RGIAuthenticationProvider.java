/**
 * @author Deepak Daneva
 * See <a href="https://linkedin.com/in/deepakdaneva">LinkedIn Profile</a>
 */
package com.reliancegeneral.sbm;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

import com.reliancegeneral.sbm.realm.RGIJDBCRealm;
import com.savvion.sbm.util.PService;
import com.savvion.usermanager.AuthenticationProvider;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import com.tdiinc.userManager.util.UMUtil;

/**
 * @author Deepak Daneva See
 *         <a href="https://linkedin.com/in/deepakdaneva">LinkedIn Profile</a>
 * 
 *         This class is used to authenticate user with External AD Service
 *         provided by RGI and if user is not present in SBM database then it
 *         will create it otherwise it will update the password
 */
public class RGIAuthenticationProvider implements AuthenticationProvider {

	private static final String AD_SERVICE_URL = "http://10.65.5.73:8081/api/Values/ID=%s/?value=%s";
	//private static final String AD_SERVICE_URL = "http://10.65.15.130:8081/api/Values/";
	private static final RestTemplate restTemplate;
	private static final int CONNECT_TIMEOUT_SECONDS = 60;
	private static final int READ_TIMEOUT_SECONDS = 60;
	
	static {
		SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
		s.setConnectTimeout(CONNECT_TIMEOUT_SECONDS * 1000);
		s.setReadTimeout(READ_TIMEOUT_SECONDS * 1000);
		restTemplate = new RestTemplate(s);
	}

	@Override
	public boolean authenticate(String username, String password) {
		/*
		 * If user is authenticated by AD Service then we have to check whether
		 * user present in our DB or not, if user do not present then we will
		 * create it and if user do present in our DB then we will check whether
		 * the user password is up to date or not, if user password is not
		 * updated then we will update the DB user password.
		 */

		JDBCRealm realm = (JDBCRealm) UserManager.getDefaultRealm();
		User user = realm.getUser(username);
		if (user != null) {		
			UMUtil.getLogger().info("User " + username + " present in database");
			if (PService.self().decrypt(user.getAttribute(Realm.PASSWD)).equals(password)) {
				UMUtil.getLogger().info("User " + username + " is successfully authenticated from database");
				return true;
			}
			else{
				boolean authenticatedByAD = RGIJDBCRealm.authenticateInLDAP(username, password);
				if (authenticatedByAD) {
					UMUtil.getLogger().info("AD User " + username + "'s is successfully authenticated from AD");
					return true;
				}
				return false;
			}
		} 
		return false;
	}

	private boolean authenticateInAD(String username, String password) {

		try {
			UMUtil.getLogger().info("Start---------------->>>>");
			String encryptedUsername = randomAlphanumeric(10) + base64Encode(username) + randomAlphanumeric(5);
			String encryptedPassword = randomAlphanumeric(10) + base64Encode(password) + randomAlphanumeric(5);
			
			String finalAdUrl = String.format(AD_SERVICE_URL, encryptedUsername, encryptedPassword);
			
			UMUtil.getLogger().info("URL = "+finalAdUrl);
			
			String response = restTemplate.getForObject(finalAdUrl, String.class);
			
			UMUtil.getLogger().info("RESPONSE::: "+response);
			
			return Boolean.valueOf(response.trim());
			
		} catch (Exception e) {
			UMUtil.getLogger().error("Error while calling external AD Service " + AD_SERVICE_URL + " for user authentication ", e);
		}
		return false;

	}

	/**
	 * Base64 Encode String
	 * @param text to encode
	 * @return encoded text
	 */
	private static String base64Encode(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * Generate random Alpha Numeric String
	 * @param number of characters
	 * @return random alpha numeric string
	 */
	private static String randomAlphanumeric(int chars) {
		return RandomStringUtils.randomAlphanumeric(chars);
	}
}
