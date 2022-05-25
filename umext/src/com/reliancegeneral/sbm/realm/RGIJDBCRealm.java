/**
 * RGI_GLOBAL_PROJECTS
 * 07-Jun-2020 11:55:36 pm
 * @author Deepak Daneva
 */
package com.reliancegeneral.sbm.realm;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang3.RandomStringUtils;

import com.tdiinc.userManager.Group;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.LDAPRealm;
import com.tdiinc.userManager.LDAPUser;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.cache.UMCacheRefreshService;
import com.tdiinc.userManager.cache.UMCacheService;
import com.tdiinc.userManager.util.UMConfig;
import com.tdiinc.userManager.util.UMUtil;

public class RGIJDBCRealm extends JDBCRealm {
	private static final String ATTRIBUTE_SEPARATOR = ": ";
	private static final String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private static final String SECURITY_AUTHENTICATION = "simple";
	private Hashtable<String, String> ldapProperties = new Hashtable<>();

	public RGIJDBCRealm() {
		super(null);
		if (!UMConfig.self().isJdbcRealm())
			initLdapProps();
	}

	public RGIJDBCRealm(String orgName) {
		super(orgName);
		if (!UMConfig.self().isJdbcRealm())
			initLdapProps();
	}

	private void initLdapProps() {
		ldapProperties.clear();
		ldapProperties.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		ldapProperties.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
		ldapProperties.put(Context.SECURITY_PRINCIPAL, UMConfig.self().getDefaultDomain()+"\\"+UMConfig.self().getLDAPUserName());
		ldapProperties.put(Context.SECURITY_CREDENTIALS, UMConfig.self().getLDAPUserPassword());
		ldapProperties.put(Context.PROVIDER_URL, UMConfig.self().getLDAPServerLocation());
	}

	@Override
	public User getUser(String username) {
		if (username == null || username.trim().length() == 0)
			return null;
		if (!UMConfig.self().isCaseSensitive())
			username = username.toLowerCase();

		User user = UMCacheService.getUser(username, this.organizationName);
		if (user != null) {
			return user;
		}
		
		if(!UMConfig.self().isJdbcRealm()) {
			ADUser aUser = getUserFromLDAP(username);
			if(aUser != null) {
				User jUser = super.getUser(username);
				if(jUser != null) {
					return jUser;
				} else {
					Hashtable<String, String> userDetails = new Hashtable<>();
					userDetails.put(Realm.USERNAME, username);
					userDetails.put(Realm.PASSWD, aUser.getPassword());
					userDetails.put(Realm.EMAIL, aUser.getMail());
					userDetails.put(Realm.PHONE, aUser.getPhone());
					userDetails.put(Realm.FIRSTNAME, aUser.getFirstname());
					userDetails.put(Realm.LASTNAME, aUser.getLastname());
					UMUtil.getLogger().info("Creating AD user "+username+" in JDBC Realm with default password");
					if(super.addUser(username, userDetails)) {
						return super.getUser(username);
					} else {
						UMUtil.getLogger().error("Error while creating AD User in JDBC Realm");
						return null;
					}
				}
			} else {
				return super.getUser(username);
			}
		} else {
			return super.getUser(username);
		}
	}
	
	public static boolean authenticateInLDAP(String username, String password) {
		Hashtable<String, String> p = new Hashtable<>();
		p.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		p.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
		p.put(Context.SECURITY_PRINCIPAL, UMConfig.self().getDefaultDomain()+"\\"+username);
		p.put(Context.SECURITY_CREDENTIALS, password);
		p.put(Context.PROVIDER_URL, UMConfig.self().getLDAPServerLocation());
		try {
			getLdapContext(p);
			return true;
		} catch (AuthenticationException e) {
			UMUtil.getLogger().debug("User "+username+" did not get authenticated in AD",e);
		} catch (Exception e) {
			UMUtil.getLogger().error("Error while authenticating user "+username+" from LDAP server",e);
		}
		return false;
	}
	
	private ADUser getUserFromLDAP(String username) {
		try {
			DirContext initialContext = getLdapContext(ldapProperties);
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String[] userAttributes = { UMConfig.self().getUserName(), UMConfig.self().getUserEmail(), UMConfig.self().getUserPhone(), UMConfig.self().getUserFirstName(), UMConfig.self().getUserLastName() };
			constraints.setReturningAttributes(userAttributes);
			NamingEnumeration<SearchResult> namingEnum = initialContext.search(UMConfig.self().getLDAPUserSearchRoot(), String.format("%s=%s", UMConfig.self().getUserName(), username), constraints);
			if(namingEnum.hasMore()) {  
                Attributes attribs = namingEnum.next().getAttributes();
                ADUser aUser = new ADUser();
                aUser.setUsername(attribs.get(UMConfig.self().getUserName()).toString().split(ATTRIBUTE_SEPARATOR)[1]);
                aUser.setMail(attribs.get(UMConfig.self().getUserEmail()).toString().split(ATTRIBUTE_SEPARATOR)[1]);
                aUser.setPhone(attribs.get(UMConfig.self().getUserPhone()).toString().split(ATTRIBUTE_SEPARATOR)[1]);
                aUser.setFirstname(attribs.get(UMConfig.self().getUserFirstName()).toString().split(ATTRIBUTE_SEPARATOR)[1]);
                aUser.setLastname(attribs.get(UMConfig.self().getUserLastName()).toString().split(ATTRIBUTE_SEPARATOR)[1]);
                namingEnum.close();
                initialContext.close();
                return aUser;
            }
		} catch (PartialResultException e) {
			UMUtil.getLogger().debug("Partial Result Exception for User "+username,e);
		}catch (Exception e) {
			UMUtil.getLogger().error("Error while getting user "+username+" from LDAP server",e);
		}
		return null;
	}

	private static DirContext getLdapContext(Hashtable<String, String> contextProperties) throws NamingException {
		return new InitialDirContext(contextProperties);
	}
}

class ADUser {
	private String username;
	private String firstname;
	private String lastname;
	private String mail;
	private String phone;
	private String password = RandomStringUtils.randomAlphanumeric(10);
	
	public ADUser() {}

	public ADUser(String username, String firstname, String lastname, String mail, String phone, String password) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.mail = mail;
		this.phone = phone;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
