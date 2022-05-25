package com.savvion.usermanager;

import com.savvion.sbm.util.logger.SBMLogger;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.LDAPRealm;
import com.tdiinc.userManager.UserManager;
import com.tdiinc.userManager.util.UMConfig;
import com.tdiinc.userManager.util.UMUtil;

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class LdapJdbcAuthenticationProvider implements AuthenticationProvider {

	private static String ldap_server = null;
	private static String ldap_user = null;
	private static String ldap_password = null;
	private static String ldap_domain = null;
	private static final boolean isInfoEnabled = UMUtil.isInfoEnabled();
	private static final boolean isDebugAPIEnabled = UMUtil.isDebugAPIEnabled();
	private static final boolean isDebugTimeEnabled = UMUtil.isDebugTimeEnabled();
	private static final boolean isDebugMiscEnabled = UMUtil.isDebugMiscEnabled();
	
	static
	{
		try{			
			ldap_server = UMConfig.self().getLDAPServerLocation();
			System.out.println("ldap_server " + ldap_server);
			ldap_user = UMConfig.self().getLDAPUserName();
			System.out.println("ldap_user " + ldap_user);
			ldap_password = UMConfig.self().getLDAPUserPassword();
			System.out.println("ldap_password " + ldap_password);
			ldap_domain = UMConfig.self().getLDAPUserSearchRoot();
			System.out.println("ldap_domain " + ldap_domain);
			System.out.println("AuthProvider " + UMConfig.self().getAuthProvider());
		}catch(Exception ex){
			UMUtil.getLogger().error("Error in loading custom auth " + ex.getMessage());
		}
	}

	@Override
	public boolean authenticate(String username, String password) {
		// TODO Auto-generated method stub
        boolean authenticate = false;
        boolean isLdapUser = false;
        
        isLdapUser = checkUserExisitsinLDAP(username);
        UMUtil.getLogger().info("User <" + username + "> exists in LDAP " + isLdapUser);
        if(isLdapUser){
        	authenticate = isUserLDAPAuthenticate(username, password);
        	UMUtil.getLogger().info("User <" + username + "> authenticated in LDAP " + authenticate);
        	if(!authenticate){
        		JDBCRealm realm = (JDBCRealm)UserManager.getDefaultRealm();
            	JDBCUser user = (JDBCUser)realm.getUser(username);
            	if(user!=null){
            		authenticate = user.passwordOK(password);
            		UMUtil.getLogger().info("LDAP User <" + username + "> authenticated in JDBC " + authenticate);
                	return authenticate;
            	}
        	}
        	return authenticate;
        }
        else{
        	JDBCRealm realm = (JDBCRealm)UserManager.getDefaultRealm();
        	JDBCUser user = (JDBCUser)realm.getUser(username);
        	if(user!=null){
        		authenticate = user.passwordOK(password);
        		UMUtil.getLogger().info("User <" + username + "> authenticated in JDBC " + authenticate);
            	return authenticate;
        	}
        }
		return authenticate;
	}

	public LdapJdbcAuthenticationProvider()
	{
		
	}

	private boolean isUserLDAPAuthenticate(String username, String password) {
		boolean isUser = false;
		try {
			UMUtil.getLogger().info("isUserLDAPAuthenticate Start");
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, "reliancecapital\\" + username);
			env.put(Context.SECURITY_CREDENTIALS, password);
			env.put(Context.PROVIDER_URL, ldap_server);
			InitialDirContext initialContext = new InitialDirContext(env);
			isUser = true;
			System.out.println("isUserLDAPAuthenticate " + username + " "
					+ isUser);
			return isUser;
		} catch (Exception nex) {
			System.out.print("LDAP Connection: FAILED  :" + nex.getMessage());
			return isUser;
		}
	}

	private boolean checkUserExisitsinLDAP(String userName) {
		boolean isExists = false;

		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, ldap_user);
			env.put(Context.SECURITY_CREDENTIALS, ldap_password);
			env.put(Context.PROVIDER_URL, ldap_server);
			InitialDirContext initialContext = new InitialDirContext(env);
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String[] attrIDs = { "distinguishedName", "sn", "givenname", "mail" };
			constraints.setReturningAttributes(attrIDs);
			NamingEnumeration answer = initialContext.search(ldap_domain, "sAMAccountName=" + userName,	constraints);
			if (answer.hasMore()) {
				isExists = true;
			}
			System.out.println("Authentication isAuthenticate : "
					+ isExists);
		} catch (Exception e) {
			System.out.println("Authentication failed: " + e.getMessage());
			return isExists;
		}
		return isExists;
	}

}
