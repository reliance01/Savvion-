package com.rmf.savvion.usermanagement;
//
//import com.savvion.acl.ACLManager;
//import com.savvion.acl.ACLPermission;
//import com.tdiinc.userManager.AdvanceGroup;
//import com.tdiinc.userManager.JDBCRealm;
//import com.tdiinc.userManager.LDAPRealm;
//import com.tdiinc.userManager.PAKRealm;
//import com.tdiinc.userManager.Realm;
//import com.tdiinc.userManager.User;
//import com.tdiinc.userManager.UserManager;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map.Entry;
//
public class SBMUserManager {
	
}
//   public static final String DOB = "dob";
//   public static final String DOJ = "doj";
//   public static final String DEPARTMENT = "department";
//   public static final String COMPANY = "company";
//   public static final String EMPLOYEE_TYPE = "employeetype";
//   public static final String DESIGNATION = "designation";
//   public static final String ZONE = "zone";
//   public static final String LOCATION = "location";
//   public static final String REGION = "region";
//   public static final String MOBILE = "mobile";
//   public static final String KEY_EMPLOYEE = "keyemployee";
//   public static final String ACTIVE = "active";
//   public static final String COMP_MGMT = "Management";
//   public static final String COMP_ADMIN = "Administration";
//
//   public String getApproverDetails(String userName) {
//      StringBuffer approverDetails = new StringBuffer();
//      Realm realm = UserManager.getDefaultRealm();
//      User user = realm.getUser(userName);
//      if (user != null) {
//         approverDetails.append(userName);
//         approverDetails.append(", ");
//         approverDetails.append(user.getAttribute("firstname"));
//         approverDetails.append(" ");
//         approverDetails.append(user.getAttribute("lastname"));
//         return approverDetails.toString();
//      } else {
//         return null;
//      }
//   }
//
//   public static void main(String[] args) {
//      SBMUserManager sbmUserManager = new SBMUserManager();
//      boolean result = validateUserPermission("Management", "Emp_Allotment_Intimation_Report_Permission", "70001001");
//      System.out.println(result);
//      System.exit(1);
//      String[] users = sbmUserManager.getGroupUsers("Management");
//      System.out.println(users.length);
//
//      for(int i = 0; i < users.length; ++i) {
//         User user = sbmUserManager.getSBMUser(users[i]);
//         System.out.println(user.getAttribute("email"));
//      }
//
//      System.exit(1);
//      System.out.println("SBM User Manager");
//      HashMap<String, String> attrMap = new HashMap();
//      attrMap.put("dob", "12/12/2008");
//      attrMap.put("doj", "12/12/2008");
//      attrMap.put("department", "IT");
//      attrMap.put("company", "RMF");
//      saveRMFUserDetails("ashutosh", attrMap);
//      RMFUserDetails userDetails = getRMFUserDetails("ashutosh");
//      System.out.println(userDetails.toString());
//      System.exit(1);
//   }
//
//   public static boolean saveRMFUserDetails(String userName, HashMap<String, String> attrMap) {
//      Realm realm = UserManager.getDefaultRealm();
//      boolean saved = false;
//      if (realm != null && userName != null && userName.trim().length() != 0) {
//         User user = realm.getUser(userName);
//         if (user != null) {
//            Iterator iter = attrMap.entrySet().iterator();
//
//            while(iter.hasNext()) {
//               Entry entry = (Entry)((Entry)iter.next());
//               user.setAttribute((String)entry.getKey(), (String)entry.getValue());
//            }
//
//            saved = true;
//         }
//      }
//
//      return saved;
//   }
//
//   public static RMFUserDetails getRMFUserDetails(String userName) {
//      Realm realm = UserManager.getDefaultRealm();
//      RMFUserDetails userDetails = new RMFUserDetails();
//      if (realm != null && userName != null && userName.trim().length() != 0) {
//         User user = realm.getUser(userName);
//         if (user != null) {
//            userDetails.setDob(user.getAttribute("dob"));
//            userDetails.setDoj(user.getAttribute("doj"));
//            userDetails.setDepartment(user.getAttribute("department"));
//            userDetails.setCompany(user.getAttribute("company"));
//            userDetails.setEmployeeType(user.getAttribute("employeetype"));
//            userDetails.setDesignation(user.getAttribute("designation"));
//            userDetails.setZone(user.getAttribute("zone"));
//            userDetails.setLocation(user.getAttribute("location"));
//            userDetails.setRegion(user.getAttribute("region"));
//            userDetails.setMobile(user.getAttribute("mobile"));
//            userDetails.setKeyEmployee(user.getAttribute("keyemployee"));
//            userDetails.setActive(user.getAttribute("active"));
//         }
//      }
//
//      return userDetails;
//   }
//
//   public User getSBMUser(String userName) {
//      Realm realm = UserManager.getDefaultRealm();
//      User user = null;
//      if (realm instanceof JDBCRealm) {
//         JDBCRealm jdbcRealm = (JDBCRealm)realm;
//         user = jdbcRealm.getUser(userName);
//      } else if (realm instanceof LDAPRealm) {
//         LDAPRealm ldapRealm = (LDAPRealm)realm;
//         user = ldapRealm.getUser(userName);
//      } else if (realm instanceof PAKRealm) {
//         PAKRealm pakRealm = (PAKRealm)realm;
//         user = pakRealm.getUser(userName);
//      }
//
//      return user;
//   }
//
//   public String[] getGroupUsers(String groupName) {
//      Realm realm = UserManager.getDefaultRealm();
//      String[] userMembers = (String[])null;
//      AdvanceGroup gp = null;
//      if (realm instanceof JDBCRealm) {
//         JDBCRealm jdbcRealm = (JDBCRealm)realm;
//         gp = (AdvanceGroup)jdbcRealm.getGroup(groupName);
//      } else if (realm instanceof LDAPRealm) {
//         LDAPRealm ldapRealm = (LDAPRealm)realm;
//         gp = (AdvanceGroup)ldapRealm.getGroup(groupName);
//      } else if (realm instanceof PAKRealm) {
//         PAKRealm pakRealm = (PAKRealm)realm;
//         gp = (AdvanceGroup)pakRealm.getGroup(groupName);
//      }
//
//      if (gp != null) {
//         userMembers = gp.getAllUserMemberNames();
//      }
//
//      return userMembers;
//   }
//
//   public String getDisplayName(String userName) {
//      Realm realm = UserManager.getDefaultRealm();
//      String fullName = null;
//      User user = null;
//      if (realm instanceof JDBCRealm) {
//         JDBCRealm jdbcRealm = (JDBCRealm)realm;
//         user = jdbcRealm.getUser(userName);
//      } else if (realm instanceof LDAPRealm) {
//         LDAPRealm ldapRealm = (LDAPRealm)realm;
//         user = ldapRealm.getUser(userName);
//      } else if (realm instanceof PAKRealm) {
//         PAKRealm pakRealm = (PAKRealm)realm;
//         user = pakRealm.getUser(userName);
//      }
//
//      if (user != null) {
//         fullName = user.getAttribute("firstname") + " " + user.getAttribute("lastname");
//      }
//
//      return fullName;
//   }
//
//   public static boolean validateUserPermission(String component, String permission, String userName) {
//      boolean result = false;
//      ACLPermission[] permissions = ACLManager.pManager.getACLPermissionsOfUser(component, userName);
//
//      for(int i = 0; i < permissions.length; ++i) {
//         if (permission.equalsIgnoreCase(permissions[i].getName())) {
//            result = true;
//            break;
//         }
//      }
//
//      if (!result) {
//         Realm realm = UserManager.getDefaultRealm();
//         String fullName = null;
//         User user = null;
//         if (!(realm instanceof JDBCRealm)) {
//            if (realm instanceof LDAPRealm) {
//               LDAPRealm ldapRealm = (LDAPRealm)realm;
//               user = ldapRealm.getUser(userName);
//            } else if (realm instanceof PAKRealm) {
//               PAKRealm pakRealm = (PAKRealm)realm;
//               user = pakRealm.getUser(userName);
//            }
//         } else {
//            JDBCRealm jdbcRealm = (JDBCRealm)realm;
//            user = jdbcRealm.getUser(userName);
//            String[] grpNames = user.getAllGroupNames();
//
//            for(int i = 0; i < grpNames.length; ++i) {
//               ACLPermission[] grpPermissions = ACLManager.pManager.getACLPermissionsOfGroup(component, grpNames[i]);
//
//               for(int j = 0; j < grpPermissions.length; ++j) {
//                  if (permission.equalsIgnoreCase(grpPermissions[j].getName())) {
//                     result = true;
//                     break;
//                  }
//               }
//
//               if (result) {
//                  break;
//               }
//            }
//         }
//
//         if (user != null) {
//            (new StringBuilder(String.valueOf(user.getAttribute("firstname")))).append(" ").append(user.getAttribute("lastname")).toString();
//         }
//      }
//
//      return result;
//   }
//}
