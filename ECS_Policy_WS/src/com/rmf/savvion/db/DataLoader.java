package com.rmf.savvion.db;

import com.rmf.common.savvion.utils.BizLogicUtil;
import com.rmf.common.util.PropertyReader;
//import com.rmf.qa.savvion.util.BizLogicUtil;
import com.rmf.savvion.util.StaticFuncs;
import com.savvion.common.databases.DatabaseManager;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplateList;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.ServiceLocator;
import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.LDAPRealm;
import com.tdiinc.userManager.PAKRealm;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import javax.naming.Context;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class DataLoader {
   private static final String CLASS_NAME = "DataLoader";
   private static final String SBM_DB_JNDI = PropertyReader.getDataSourceName();
   private static final Logger logger = PropertyReader.getDlLogger();
   private static final Object PIID = "PIID";
   private static final Object STATUS = "STATUS";
   private static final Object DS_INITIATOR = "INITIATOR";
   private static final Object DS_INITIATORNAME = "INITIATORNAME";
   private static final Object DS_COMPANY = "COMPANY";
   private static final Object DS_DEPARTMENT = "DEPARTMENT";
   private static final Object DS_AUDITPROCESSNAME = "AUDITPROCESSNAME";
   private static final Object DS_AUDITPROCESS = "AUDITPROCESS";
   private static final Object DS_AUDIT_LOCATION = "AUDITLOCATION";
   private static final String USER_STATUS_AVAILABLE = "AVAILABLE";
   private static final String USER_STATUS_NOT_AVAILABLE = "NOT_AVAILABLE";
   private static final SimpleDateFormat effDateFormat = new SimpleDateFormat("MMM dd, yyyy");

   public static void main(String[] args) {
      String METHOD_NAME = "main";
      System.out.println("Going...");
      DataLoader dLoader = new DataLoader();
      Map grpMap = dLoader.getAllGroups();
      System.out.println(grpMap);
      System.exit(1);
      Vector users = dLoader.getGroupUsers("QualityAuditTeam", (String)null);
      System.out.println(users);
      System.exit(1);
      dLoader.getQualityAuditPIInfo("RCAM", "1", "RCAM_1_58432", "1");
      System.exit(1);
      Map dsMap = dLoader.getFullName("as");
      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "main", 0, (String)dsMap.get("STATUS") + (String)dsMap.get("FULL_NAME")));
      Map tMap = dLoader.getRQSPOCLocationDetails("RCAM", "1", "PROC_1_49881", "1");
      Iterator ite1 = tMap.entrySet().iterator();

      while(ite1.hasNext()) {
         Entry pairs = (Entry)((Entry)ite1.next());
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "main", 0, pairs.getKey() + " = " + pairs.getValue()));
      }

      System.exit(1);
      Map companyMap = dLoader.getCompanyMap();
      Map zoneMap = dLoader.getZoneMap();
      Map locationMap = dLoader.getLocationMap();
      Map departmentMap = dLoader.getDepartmentMap();
      Map regionMap = dLoader.getRegionMap();
      Map AssetTypeMap = dLoader.getAssetTypeMap();
      Iterator it = companyMap.entrySet().iterator();

      while(it.hasNext()) {
         Entry pairs = (Entry)((Entry)it.next());
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "main", 0, pairs.getKey() + " = " + pairs.getValue()));
      }

      Iterator it1 = zoneMap.entrySet().iterator();

      while(it1.hasNext()) {
         Entry pairs = (Entry)((Entry)it1.next());
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "main", 0, pairs.getKey() + " = " + pairs.getValue()));
      }

      Iterator it2 = locationMap.entrySet().iterator();

      while(it2.hasNext()) {
         Entry pairs = (Entry)((Entry)it2.next());
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "main", 0, pairs.getKey() + " = " + pairs.getValue()));
      }

      Iterator it3 = departmentMap.entrySet().iterator();

      while(it3.hasNext()) {
         Entry pairs = (Entry)((Entry)it3.next());
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "main", 0, pairs.getKey() + " = " + pairs.getValue()));
      }

      Iterator it4 = regionMap.entrySet().iterator();

      while(it4.hasNext()) {
         Entry pairs = (Entry)((Entry)it4.next());
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "main", 0, pairs.getKey() + " = " + pairs.getValue()));
      }

      Iterator it5 = AssetTypeMap.entrySet().iterator();

      while(it5.hasNext()) {
         Entry pairs = (Entry)((Entry)it5.next());
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "main", 0, pairs.getKey() + " = " + pairs.getValue()));
      }

   }

   private Connection getConnection(String jndiName) throws SQLException {
      String METHOD_NAME = "getConnection";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getConnection", 1));
      Connection conn = DatabaseManager.getConnection(jndiName);
      if (conn != null) {
         DatabaseMetaData metaData = conn.getMetaData();
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getConnection", 0, "Got Connection. URL ", metaData.getURL(), " User Name", metaData.getUserName()));
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getConnection", 2));
      return conn;
   }

   public Map getCompanyMap() {
      String METHOD_NAME = "getCompanyMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompanyMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "SELECT COMPANY_ID,COMPANY_NAME FROM RMF_COMPANY ORDER BY COMPANY_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompanyMap", 0, "GET COMPANY MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("COMPANY_ID"), rs.getString("COMPANY_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompanyMap", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompanyMap", 2));
      return hMap;
   }

   public Map getAssetTypeMap() {
      String METHOD_NAME = "getAssetTypeMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAssetTypeMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "SELECT COMPANY_ID,COMPANY_NAME FROM RMF_COMPANY ORDER BY COMPANY_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getAssetTypeMap", 0, "GET COMPANY MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("COMPANY_ID"), rs.getString("COMPANY_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAssetTypeMap", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAssetTypeMap", 2));
      return hMap;
   }

   public Map getDepartmentMap() {
      String METHOD_NAME = "getDepartmentMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getDepartmentMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT DEPARTMENT_ID,DEPARTMENT_NAME FROM RMF_DEPARTMENT ORDER BY DEPARTMENT_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getDepartmentMap", 0, "GET DEPARTMENT MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("DEPARTMENT_ID"), rs.getString("DEPARTMENT_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getDepartmentMap", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getDepartmentMap", 2));
      return hMap;
   }

   public Map getZoneMap() {
      String METHOD_NAME = "getZoneMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getZoneMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT ZONE_ID,ZONE_NAME FROM RMF_ZONE ORDER BY ZONE_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getZoneMap", 0, "GET ZONE MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("ZONE_ID"), rs.getString("ZONE_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getZoneMap", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getZoneMap", 2));
      return hMap;
   }

   public Map getRegionMap() {
      String METHOD_NAME = "getRegionMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRegionMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT REGION_ID,REGION_NAME FROM RMF_REGION ORDER BY REGION_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRegionMap", 0, "GET REGION MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("REGION_ID"), rs.getString("REGION_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRegionMap", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRegionMap", 2));
      return hMap;
   }

   public Map getLocationMap() {
      String METHOD_NAME = "getLocationMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getLocationMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT LOCATION_ID, LOCATION_NAME FROM RMF_LOCATION ORDER BY LOCATION_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getLocationMap", 0, "GET LOCATION MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("LOCATION_ID"), rs.getString("LOCATION_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getLocationMap", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getLocationMap", 2));
      return hMap;
   }

   public String getHOD(String userName) {
      String METHOD_NAME = "getHOD";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getHOD", 1));
      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getHOD", 0, "GET HOD FOR USER ", userName));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      String hod = null;

      try {
         String sqlStmt = "SELECT hod FROM rmf_department rmfd, (SELECT attr_value AS department FROM umuser uu, userattr ua WHERE uu.user_name like ? and uu.user_id=ua.user_id AND  Lower(attr_name)='department') A WHERE rmfd.department_id=A.department";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getHOD", 0, "GET HOD SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, userName);
         rs = pstmt.executeQuery();
         rs.next();
         hod = rs.getString("hod");
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getHOD", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getHOD", 0, "HOD FOUND AS ", hod));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getHOD", 2));
      return hod;
   }

   public String getReportingPerson(String userName, String processTemplateName) {
      String METHOD_NAME = "getReportingPerson";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getReportingPerson", 1));
      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getReportingPerson", 0, "GET REPORTING PERSON FOR USER", userName, "PROCESS TEMPLATE", processTemplateName));
      Realm realm = UserManager.getDefaultRealm();
      User user = realm.getUser(userName);
      String[] grpNames = user.getAllGroupNames();
      StringBuffer grpString = new StringBuffer();
      if (grpNames != null && grpNames.length != 0) {
         for(int i = 0; i < grpNames.length; ++i) {
            if (i > 0) {
               grpString.append(",");
            }

            grpString.append("'");
            grpString.append(grpNames[i]);
            grpString.append("'");
         }
      }

      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      String reportingPerson = null;
      System.out.println("grpString = " + grpString);

      try {
         String sqlStmt = "SELECT REPORTING_PERSON FROM RMF_PROCESS_GROUP_REP_PERSON WHERE PROCESS_TEMPLATE_NAME LIKE ? AND GROUP_NAME IN (" + grpString.toString() + ")";
         System.out.println("sqlStmt = " + sqlStmt);
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getReportingPerson", 0, "GET REPORTING PERSON SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, processTemplateName);
         rs = pstmt.executeQuery();
         rs.next();
         reportingPerson = rs.getString("REPORTING_PERSON");
      } catch (SQLException var16) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getReportingPerson", 0, "SQL Exception Occured."), var16);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getReportingPerson", 0, "REPORTING PERSON FOUND AS ", reportingPerson));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getReportingPerson", 2));
      return reportingPerson;
   }

   public Map getEmployeeTypeMap() {
      String METHOD_NAME = "getEmployeeTypeMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getEmployeeTypeMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "SELECT EMPLOYEE_TYPE_ID,EMPLOYEE_TYPE_NAME FROM RMF_EMPLOYEE_TYPE ORDER BY EMPLOYEE_TYPE_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getEmployeeTypeMap", 0, "GET EMPLOYEE TYPE MAP SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("EMPLOYEE_TYPE_ID"), rs.getString("EMPLOYEE_TYPE_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getEmployeeTypeMap", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getEmployeeTypeMap", 2));
      return hMap;
   }

   public Vector<String> getUserInfo(String userName) {
      String METHOD_NAME = "getUserInfo";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getUserInfo", 1));
      Vector<String> result = new Vector();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "select A.email as EMAIL, B.firstname as FIRSTNAME, C.lastname as LASTNAME, D.mobile as  MOBILE, E.dob as DOB, F.doj as DOJ FROM (SELECT attr_value AS email FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='email' and uu.user_name='" + userName + "') A," + "(SELECT attr_value AS firstname FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='firstname' and uu.user_name='" + userName + "') B," + "(SELECT attr_value AS lastname FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='lastname' and uu.user_name='" + userName + "') C, " + "(SELECT attr_value AS mobile FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='mobile' and uu.user_name='" + userName + "') D, " + "(SELECT attr_value AS dob FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='dob' and uu.user_name='" + userName + "') E, " + "(SELECT attr_value AS doj FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='doj' and uu.user_name='" + userName + "') F";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getUserInfo", 0, "GET USER INFO SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            result.add(rs.getString("EMAIL"));
            result.add(rs.getString("FIRSTNAME"));
            result.add(rs.getString("LASTNAME"));
            result.add(rs.getString("MOBILE"));
            result.add(rs.getString("DOB"));
            result.add(rs.getString("DOJ"));
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getUserInfo", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getUserInfo", 0, "User Info found as ", result.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getUserInfo", 2));
      return result;
   }

   private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
      String METHOD_NAME = "closeResources";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "closeResources", 1));

      try {
         if (rs != null) {
            rs.close();
            rs = null;
         }

         if (pstmt != null) {
            pstmt.close();
            pstmt = null;
         }

         if (conn != null) {
            conn.close();
            conn = null;
         }

         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "closeResources", 0, "DB Resources closed."));
      } catch (SQLException var6) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "closeResources", 0, "SQL Exception Occured."), var6);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "closeResources", 2));
   }

   /** @deprecated */
   public Map<String, String> getRMFDepartmentProcesses(String deptId) {
      String METHOD_NAME = "getRMFDepartmentProcesses";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRMFDepartmentProcesses", 1));
      Map<String, String> deptProcesses = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT QUALITY_PROCESS_ID, QUALITY_PROCESS_NAME, EFFECTIVE_DATE FROM RMF_QUALITY_PROCESS RQP WHERE DEPARTMENT_ID=? AND QUALITY_PROCESS_ID IN (SELECT DISTINCT(QUALITY_PROCESS_ID) FROM RMF_QUALITY_PROCESS) AND EFFECTIVE_DATE=(SELECT Max(EFFECTIVE_DATE) FROM RMF_QUALITY_PROCESS WHERE QUALITY_PROCESS_ID=RQP.QUALITY_PROCESS_ID) AND UPDATE_TIMESTAMP=(SELECT Max(UPDATE_TIMESTAMP) FROM RMF_QUALITY_PROCESS WHERE QUALITY_PROCESS_ID=RQP.QUALITY_PROCESS_ID)";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRMFDepartmentProcesses", 0, "GET RMF DEPARTMENT PROCESSES SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, deptId);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            deptProcesses.put(rs.getString("QUALITY_PROCESS_ID"), rs.getString("QUALITY_PROCESS_NAME"));
         }

         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRMFDepartmentProcesses", 0, "RMF DEPARTMENT PROCESSES FOUND AS ", deptProcesses.toString()));
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRMFDepartmentProcesses", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRMFDepartmentProcesses", 2));
      return deptProcesses;
   }

   public String getAuditProcessName(String auditProcessId) {
      String METHOD_NAME = "getAuditProcessName";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAuditProcessName", 1));
      String auditProcessName = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT PROCESS_ID, PROCESS_NAME, EFFECTIVE_DATE, UPDATE_TIMESTAMP FROM (SELECT RQP.PROCESS_ID, RQP.PROCESS_NAME, RQP.EFFECTIVE_DATE, RQP.UPDATE_TIMESTAMP FROM R_QUALITY_PROCESS RQP WHERE RQP.PROCESS_ID=? ORDER BY RQP.EFFECTIVE_DATE DESC, RQP.UPDATE_TIMESTAMP DESC) A WHERE ROWNUM=1";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getAuditProcessName", 0, "GET AUDIT PROCESS NAME SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, auditProcessId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            auditProcessName = rs.getString("PROCESS_NAME");
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAuditProcessName", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getAuditProcessName", 0, "AUDIT PROCESS NAME FOUND AS ", auditProcessName));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAuditProcessName", 2));
      return auditProcessName;
   }

   public Map<String, String> getExistingProcessDetails(String auditProcessId) {
      String METHOD_NAME = "getExistingProcessDetails";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetails", 1));
      Map<String, String> processDetails = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT PROCESS_ID, PROCESS_NAME, PROCESS_DESCRIPTION, EFFECTIVE_DATE, UPDATE_TIMESTAMP FROM (SELECT RQP.PROCESS_ID, RQP.PROCESS_NAME, RQP.PROCESS_DESCRIPTION, RQP.EFFECTIVE_DATE, RQP.UPDATE_TIMESTAMP FROM R_QUALITY_PROCESS RQP WHERE PROCESS_ID=? ORDER BY RQP.EFFECTIVE_DATE DESC, RQP.UPDATE_TIMESTAMP DESC) WHERE ROWNUM=1";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetails", 0, "GET EXISTING PROCESS DETAILS SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, auditProcessId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            processDetails.put("PROCESS_NAME", rs.getString("PROCESS_NAME"));
            processDetails.put("PROCESS_DESCRIPTION", rs.getString("PROCESS_DESCRIPTION"));
            String effDate = rs.getString("EFFECTIVE_DATE");
            if (effDate != null && effDate.trim().length() != 0) {
               effDate = effDateFormat.format(new Date(Long.parseLong(effDate)));
            }

            processDetails.put("EFFECTIVE_DATE", effDate);
            processDetails.put("UPDATE_TIMESTAMP", rs.getString("UPDATE_TIMESTAMP"));
         }
      } catch (SQLException var12) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetails", 0, "SQL Exception Occured."), var12);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetails", 0, "Existing Process Details found as ", processDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetails", 2));
      return processDetails;
   }

   public Map<String, String> getExistingProcessDetailsQA(String auditProcessId, String effectiveDate) {
      String METHOD_NAME = "getExistingProcessDetailsQA";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetailsQA", 1));
      Map<String, String> processDetails = new HashMap();
      long dtValue = this.parseDate(effectiveDate);
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT PROCESS_ID, PROCESS_NAME, PROCESS_DESCRIPTION, EFFECTIVE_DATE, UPDATE_TIMESTAMP FROM ( SELECT RQP.PROCESS_ID, RQP.PROCESS_NAME, RQP.PROCESS_DESCRIPTION, RQP.EFFECTIVE_DATE, RQP.UPDATE_TIMESTAMP FROM R_QUALITY_PROCESS RQP WHERE RQP.PROCESS_ID=? AND RQP.EFFECTIVE_DATE <=? ORDER BY RQP.EFFECTIVE_DATE DESC, RQP.UPDATE_TIMESTAMP DESC ) WHERE ROWNUM=1";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetailsQA", 0, "GET EXISTING PROCESS DETAILS QA SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, auditProcessId);
         pstmt.setLong(2, dtValue);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            processDetails.put("PROCESS_NAME", rs.getString("PROCESS_NAME"));
            processDetails.put("PROCESS_DESCRIPTION", rs.getString("PROCESS_DESCRIPTION"));
            String effDate = rs.getString("EFFECTIVE_DATE");
            if (effDate != null && effDate.trim().length() != 0) {
               effDate = effDateFormat.format(new Date(Long.parseLong(effDate)));
            }

            processDetails.put("EFFECTIVE_DATE", effDate);
            processDetails.put("UPDATE_TIMESTAMP", rs.getString("UPDATE_TIMESTAMP"));
         }
      } catch (SQLException var15) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetailsQA", 0, "SQL Exception Occured."), var15);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetailsQA", 0, "Process Details found as ", processDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessDetailsQA", 2));
      return processDetails;
   }

   public Map<String, String> getQDeptDetails(String deptId) {
      String METHOD_NAME = "getQDeptDetails";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetails", 1));
      Map<String, String> deptDetails = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT  DEPARTMENT_ID, DEPARTMENT_NAME, HOD, EFFECTIVE_DATE, UPDATE_TIMESTAMP FROM ( SELECT RQD.QUALITY_DEPARTMENT_ID DEPARTMENT_ID, RQD.HOD, RQD.EFFECTIVE_DATE, RQD.UPDATE_TIMESTAMP, RD.DEPARTMENT_NAME FROM R_QUALITY_DEPARTMENT RQD, RMF_DEPARTMENT RD WHERE QUALITY_DEPARTMENT_ID=? AND RQD.QUALITY_DEPARTMENT_ID=RD.DEPARTMENT_ID ORDER BY EFFECTIVE_DATE DESC, UPDATE_TIMESTAMP DESC ) WHERE ROWNUM=1";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetails", 0, "GET Q DEPT DETAILS SQL", sqlStmt));
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetails", 0, sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, deptId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            deptDetails.put("DEPARTMENT_ID", rs.getString("DEPARTMENT_ID"));
            deptDetails.put("DEPARTMENT_NAME", rs.getString("DEPARTMENT_NAME"));
            deptDetails.put("HOD", rs.getString("HOD"));
            String effDate = rs.getString("EFFECTIVE_DATE");
            if (effDate != null && effDate.trim().length() != 0) {
               effDate = effDateFormat.format(new Date(Long.parseLong(effDate)));
            }

            deptDetails.put("EFFECTIVE_DATE", effDate);
            deptDetails.put("UPDATE_TIMESTAMP", rs.getString("UPDATE_TIMESTAMP"));
         }
      } catch (SQLException var12) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetails", 0, "SQL Exception Occured."), var12);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetails", 0, "Department Details found as", deptDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetails", 2));
      return deptDetails;
   }

   public Map<String, String> getQDeptDetailsQA(String compId, String deptId, String effectiveDate) {
      String METHOD_NAME = "getQDeptDetailsQA";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetailsQA", 1));
      Map<String, String> deptDetails = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;
      long dtValue = this.parseDate(effectiveDate);

      try {
         String sqlStmt = "SELECT RQD.QUALITY_DEPARTMENT_ID DEPARTMENT_ID, RQD.HOD HOD, RQD.EFFECTIVE_DATE EFFECTIVE_DATE, RQD.UPDATE_TIMESTAMP UPDATE_TIMESTAMP, RD.DEPARTMENT_NAME DEPARTMENT_NAME FROM R_QUALITY_DEPARTMENT RQD, RMF_DEPARTMENT RD WHERE RQD.COMPANY_ID=? AND RQD.QUALITY_DEPARTMENT_ID=? AND RQD.EFFECTIVE_DATE=(SELECT MAX(EFFECTIVE_DATE) FROM R_QUALITY_DEPARTMENT WHERE COMPANY_ID=? AND QUALITY_DEPARTMENT_ID=? AND EFFECTIVE_DATE<=?) AND RQD.UPDATE_TIMESTAMP=(SELECT MAX(UPDATE_TIMESTAMP) FROM R_QUALITY_DEPARTMENT WHERE COMPANY_ID=? AND QUALITY_DEPARTMENT_ID=? AND EFFECTIVE_DATE<=?) AND RQD.QUALITY_DEPARTMENT_ID=RD.DEPARTMENT_ID";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetailsQA", 0, "GET Q DEPT DETAILS QA SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, compId);
         pstmt.setString(4, deptId);
         pstmt.setLong(5, dtValue);
         pstmt.setString(6, compId);
         pstmt.setString(7, deptId);
         pstmt.setLong(8, dtValue);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            deptDetails.put("DEPARTMENT_ID", rs.getString("DEPARTMENT_ID"));
            deptDetails.put("DEPARTMENT_NAME", rs.getString("DEPARTMENT_NAME"));
            deptDetails.put("HOD", rs.getString("HOD"));
            String effDate = rs.getString("EFFECTIVE_DATE");
            if (effDate != null && effDate.trim().length() != 0) {
               effDate = effDateFormat.format(new Date(Long.parseLong(effDate)));
            }

            deptDetails.put("EFFECTIVE_DATE", effDate);
            deptDetails.put("UPDATE_TIMESTAMP", rs.getString("UPDATE_TIMESTAMP"));
         }
      } catch (SQLException var16) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetailsQA", 0, "SQL Exception Occured."), var16);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetailsQA", 0, "Department Details found as ", deptDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQDeptDetailsQA", 2));
      return deptDetails;
   }

   public Map<String, String> getEAgencyDetails(String eAgencyId) {
      String METHOD_NAME = "getEAgencyDetails";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getEAgencyDetails", 1));
      Map<String, String> eAgencyDetails = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT EXTERNAL_AGENCY_ID, EXTERNAL_AGENCY_NAME FROM R_QUALITY_EXTERNAL_AGENCY WHERE EXTERNAL_AGENCY_ID=?";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getEAgencyDetails", 0, "GET EXERTANL AGENCY DETAILS SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, eAgencyId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            eAgencyDetails.put("EXTERNAL_AGENCY_ID", rs.getString("EXTERNAL_AGENCY_ID"));
            eAgencyDetails.put("EXTERNAL_AGENCY_NAME", rs.getString("EXTERNAL_AGENCY_NAME"));
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getEAgencyDetails", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getEAgencyDetails", 0, "External Agency Details found as ", eAgencyDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getEAgencyDetails", 2));
      return eAgencyDetails;
   }

   public Map getCompanyDepts(String compId) {
      String METHOD_NAME = "getCompanyDepts";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompanyDepts", 1));
      Map<String, String> depts = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT DEPARTMENT_ID, DEPARTMENT_NAME FROM RMF_DEPARTMENT WHERE COMPANY_ID=? AND DEPARTMENT_ID NOT IN (SELECT  DEPARTMENT_ID FROM RMF_DEPARTMENT WHERE DEPARTMENT_ID IN ( SELECT QUALITY_DEPARTMENT_ID FROM R_QUALITY_DEPARTMENT RQD WHERE COMPANY_ID=? AND QUALITY_DEPARTMENT_ID IN (SELECT DISTINCT(QUALITY_DEPARTMENT_ID) FROM R_QUALITY_DEPARTMENT) AND EFFECTIVE_DATE=(SELECT Max(EFFECTIVE_DATE) FROM R_QUALITY_DEPARTMENT WHERE QUALITY_DEPARTMENT_ID=RQD.QUALITY_DEPARTMENT_ID) AND UPDATE_TIMESTAMP=(SELECT Max(UPDATE_TIMESTAMP) FROM R_QUALITY_DEPARTMENT WHERE QUALITY_DEPARTMENT_ID=RQD.QUALITY_DEPARTMENT_ID))) ORDER BY DEPARTMENT_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompanyDepts", 0, "GET COMPANY DEPTS SQL : ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, compId);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            depts.put(rs.getString("DEPARTMENT_ID"), rs.getString("DEPARTMENT_NAME"));
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompanyDepts", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompanyDepts", 0, "Departments found as ", depts.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompanyDepts", 2));
      return depts;
   }

   public Map getCompQDepts(String compId) {
      String METHOD_NAME = "getCompQDepts";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompQDepts", 1));
      Map<String, String> depts = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT  DEPARTMENT_ID, DEPARTMENT_NAME FROM RMF_DEPARTMENT WHERE DEPARTMENT_ID IN ( SELECT QUALITY_DEPARTMENT_ID FROM R_QUALITY_DEPARTMENT RQD WHERE COMPANY_ID=? AND QUALITY_DEPARTMENT_ID IN (SELECT DISTINCT(QUALITY_DEPARTMENT_ID) FROM R_QUALITY_DEPARTMENT) AND EFFECTIVE_DATE=(SELECT Max(EFFECTIVE_DATE) FROM R_QUALITY_DEPARTMENT WHERE QUALITY_DEPARTMENT_ID=RQD.QUALITY_DEPARTMENT_ID) AND UPDATE_TIMESTAMP=(SELECT Max(UPDATE_TIMESTAMP) FROM R_QUALITY_DEPARTMENT WHERE QUALITY_DEPARTMENT_ID=RQD.QUALITY_DEPARTMENT_ID)) ORDER BY DEPARTMENT_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompQDepts", 0, "GET COMP Q DEPARTMENTS SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            depts.put(rs.getString("DEPARTMENT_ID"), rs.getString("DEPARTMENT_NAME"));
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompQDepts", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompQDepts", 0, "Departments found as ", depts.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompQDepts", 2));
      return depts;
   }

   public Map getCompQDeptsQA(String compId, String effectiveDate) {
      String METHOD_NAME = "getCompQDeptsQA";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompQDeptsQA", 1));
      Map<String, String> depts = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;
      long dtValue = this.parseDate(effectiveDate);

      try {
         String sqlStmt = "SELECT  DEPARTMENT_ID, DEPARTMENT_NAME FROM RMF_DEPARTMENT WHERE DEPARTMENT_ID IN ( SELECT QUALITY_DEPARTMENT_ID FROM R_QUALITY_DEPARTMENT RQD WHERE COMPANY_ID=? AND QUALITY_DEPARTMENT_ID IN (SELECT DISTINCT(QUALITY_DEPARTMENT_ID) FROM R_QUALITY_DEPARTMENT) AND EFFECTIVE_DATE=(SELECT Max(EFFECTIVE_DATE) FROM R_QUALITY_DEPARTMENT WHERE QUALITY_DEPARTMENT_ID=RQD.QUALITY_DEPARTMENT_ID AND EFFECTIVE_DATE <=?) AND UPDATE_TIMESTAMP=(SELECT Max(UPDATE_TIMESTAMP) FROM R_QUALITY_DEPARTMENT WHERE QUALITY_DEPARTMENT_ID=RQD.QUALITY_DEPARTMENT_ID AND EFFECTIVE_DATE <=?)) ORDER BY DEPARTMENT_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompQDeptsQA", 0, "GET COMP Q DEPTS QA SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setLong(2, dtValue);
         pstmt.setLong(3, dtValue);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            depts.put(rs.getString("DEPARTMENT_ID"), rs.getString("DEPARTMENT_NAME"));
         }
      } catch (SQLException var14) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompQDeptsQA", 0, "SQL Exception Occured."), var14);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompQDeptsQA", 0, "Departments found as ", depts.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompQDeptsQA", 2));
      return depts;
   }

   public String getDeptName(String deptId) {
      String METHOD_NAME = "getDeptName";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getDeptName", 1));
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;
      String deptName = null;

      try {
         String sqlStmt = "SELECT DEPARTMENT_NAME FROM RMF_DEPARTMENT WHERE DEPARTMENT_ID=?";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getDeptName", 0, "GET DEPT NAME SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, deptId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            deptName = rs.getString("DEPARTMENT_NAME");
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getDeptName", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getDeptName", 0, "Department Name found as ", deptName));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getDeptName", 2));
      return deptName;
   }

   public String getCompName(String compId) {
      String METHOD_NAME = "getCompName";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompName", 1));
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;
      String compName = null;

      try {
         String sqlStmt = "SELECT COMPANY_NAME FROM RMF_COMPANY WHERE COMPANY_ID=?";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompName", 0, "GET COMP NAME ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            compName = rs.getString("COMPANY_NAME");
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompName", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getCompName", 0, "Company Name found as ", compName));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getCompName", 2));
      return compName;
   }

   public String getAuditLocationName(String auditLocationId) {
      String METHOD_NAME = "getAuditLocationName";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAuditLocationName", 1));
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;
      String auditLocationName = null;

      try {
         String sqlStmt = "SELECT LOCATION_NAME FROM RMF_LOCATION WHERE LOCATION_ID=?";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getAuditLocationName", 0, "GET AUDIT LOCATION NAME SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, auditLocationId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            auditLocationName = rs.getString("LOCATION_NAME");
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAuditLocationName", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getAuditLocationName", 0, "Audit Location Name found as ", auditLocationName));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAuditLocationName", 2));
      return auditLocationName;
   }

   public String getLatestQDEffDate(String compId, String deptId) {
      String METHOD_NAME = "getLatestQDEffDate";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getLatestQDEffDate", 1));
      String latestEffDate = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT EFFECTIVE_DATE FROM (SELECT  EFFECTIVE_DATE FROM R_QUALITY_DEPARTMENT WHERE COMPANY_ID=? AND QUALITY_DEPARTMENT_ID=? ORDER BY EFFECTIVE_DATE DESC, UPDATE_TIMESTAMP DESC) A WHERE ROWNUM=1";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getLatestQDEffDate", 0, "GET LATEEST QD EFFECTIVE DATE SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            latestEffDate = rs.getString("EFFECTIVE_DATE");
            if (latestEffDate != null && latestEffDate.trim().length() != 0) {
               latestEffDate = effDateFormat.format(new Date(Long.parseLong(latestEffDate)));
            }
         }
      } catch (SQLException var12) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getLatestQDEffDate", 0, "SQL Exception Occured."), var12);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getLatestQDEffDate", 0, "Latest Effective Date found as", latestEffDate));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getLatestQDEffDate", 2));
      return latestEffDate;
   }

   public String getOldestQDEffDate(String compId, String deptId) {
      String METHOD_NAME = "getOldestQDEffDate";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getOldestQDEffDate", 1));
      String oldestEffDate = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT EFFECTIVE_DATE FROM (SELECT  EFFECTIVE_DATE FROM R_QUALITY_DEPARTMENT WHERE COMPANY_ID=? AND QUALITY_DEPARTMENT_ID=? ORDER BY EFFECTIVE_DATE ASC, UPDATE_TIMESTAMP ASC)A WHERE ROWNUM=1";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getOldestQDEffDate", 0, "GET OLDEST QD EFFECTIVE DATE SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            oldestEffDate = rs.getString("EFFECTIVE_DATE");
            if (oldestEffDate != null && oldestEffDate.trim().length() != 0) {
               oldestEffDate = effDateFormat.format(new Date(Long.parseLong(oldestEffDate)));
            }
         }
      } catch (SQLException var12) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getOldestQDEffDate", 0, "SQL Exception Occured."), var12);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getOldestQDEffDate", 0, "Oldest Effective date found as ", oldestEffDate));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getOldestQDEffDate", 2));
      return oldestEffDate;
   }

   public Map getExternalAgencies() {
      String METHOD_NAME = "getExternalAgencies";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExternalAgencies", 1));
      Map<String, String> eAgencies = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT EXTERNAL_AGENCY_ID, EXTERNAL_AGENCY_NAME FROM R_QUALITY_EXTERNAL_AGENCY ORDER BY EXTERNAL_AGENCY_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExternalAgencies", 0, "GET EXTERNAL AGENCIES SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            eAgencies.put(rs.getString("EXTERNAL_AGENCY_ID"), rs.getString("EXTERNAL_AGENCY_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExternalAgencies", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExternalAgencies", 0, "Extenal Agencies found as ", eAgencies.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExternalAgencies", 2));
      return eAgencies;
   }

   public Map<String, String> getExistingProcesses(String compId, String deptId) {
      String METHOD_NAME = "getExistingProcesses";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcesses", 1));
      Map<String, String> deptProcesses = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT PROCESS_ID, PROCESS_NAME, EFFECTIVE_DATE FROM R_QUALITY_PROCESS RQP WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID IN (SELECT DISTINCT(PROCESS_ID) FROM R_QUALITY_PROCESS WHERE COMPANY_ID=?AND DEPARTMENT_ID=? ) AND EFFECTIVE_DATE=(SELECT Max(EFFECTIVE_DATE) FROM R_QUALITY_PROCESS WHERE PROCESS_ID=RQP.PROCESS_ID AND COMPANY_ID=?  AND  DEPARTMENT_ID=? ) AND UPDATE_TIMESTAMP=(SELECT Max(UPDATE_TIMESTAMP) FROM R_QUALITY_PROCESS WHERE PROCESS_ID=RQP.PROCESS_ID AND COMPANY_ID=?  AND  DEPARTMENT_ID=?) ORDER BY PROCESS_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcesses", 0, "GET EXISTING PROCESSES SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, compId);
         pstmt.setString(4, deptId);
         pstmt.setString(5, compId);
         pstmt.setString(6, deptId);
         pstmt.setString(7, compId);
         pstmt.setString(8, deptId);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            deptProcesses.put(rs.getString("PROCESS_ID"), rs.getString("PROCESS_NAME"));
         }
      } catch (SQLException var12) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcesses", 0, "SQL Exception Occured."), var12);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcesses", 0, "Existing process found as ", deptProcesses.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcesses", 2));
      return deptProcesses;
   }

   public Map<String, String> getExistingProcessesQA(String compId, String deptId, String effectiveDate) {
      String METHOD_NAME = "getExistingProcessesQA";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessesQA", 1));
      Map<String, String> deptProcesses = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;
      long dtValue = this.parseDate(effectiveDate);

      try {
         String sqlStmt = "SELECT PROCESS_ID, PROCESS_NAME, EFFECTIVE_DATE FROM R_QUALITY_PROCESS RQP WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID IN (SELECT DISTINCT(PROCESS_ID) FROM R_QUALITY_PROCESS WHERE COMPANY_ID=? AND DEPARTMENT_ID=? ) AND EFFECTIVE_DATE=(SELECT Max(EFFECTIVE_DATE) FROM R_QUALITY_PROCESS WHERE PROCESS_ID=RQP.PROCESS_ID AND COMPANY_ID=?  AND  DEPARTMENT_ID=? AND EFFECTIVE_DATE<=?) AND UPDATE_TIMESTAMP=(SELECT Max(UPDATE_TIMESTAMP) FROM R_QUALITY_PROCESS WHERE PROCESS_ID=RQP.PROCESS_ID AND COMPANY_ID=?  AND  DEPARTMENT_ID=?  AND EFFECTIVE_DATE<=?) ORDER BY PROCESS_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessesQA", 0, "GET EXISTING PROCESSES QA SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, compId);
         pstmt.setString(4, deptId);
         pstmt.setString(5, compId);
         pstmt.setString(6, deptId);
         pstmt.setLong(7, dtValue);
         pstmt.setString(8, compId);
         pstmt.setString(9, deptId);
         pstmt.setLong(10, dtValue);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            deptProcesses.put(rs.getString("PROCESS_ID"), rs.getString("PROCESS_NAME"));
         }
      } catch (SQLException var15) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessesQA", 0, "SQL Exception Occured."), var15);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessesQA", 0, "Existing Processes found as ", deptProcesses.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExistingProcessesQA", 2));
      return deptProcesses;
   }

   public String getQPLastEffDate(String processId, String compId, String deptId) {
      String METHOD_NAME = "getQPLastEffDate";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQPLastEffDate", 1));
      String lastEffDate = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT  EFFECTIVE_DATE FROM R_QUALITY_PROCESS WHERE PROCESS_ID=? AND DEPARTMENT_ID=? AND COMPANY_ID=? ORDER BY EFFECTIVE_DATE DESC, UPDATE_TIMESTAMP DESC";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQPLastEffDate", 0, "GET QP LAST EFFECTIVE DATE SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, processId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, compId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            lastEffDate = rs.getString("EFFECTIVE_DATE");
            if (lastEffDate != null && lastEffDate.trim().length() != 0) {
               lastEffDate = effDateFormat.format(new Date(Long.parseLong(lastEffDate)));
            }
         }
      } catch (SQLException var13) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQPLastEffDate", 0, "SQL Exception Occured."), var13);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQPLastEffDate", 0, "Last Effective date found as", lastEffDate));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQPLastEffDate", 2));
      return lastEffDate;
   }

   public String getRQSPOCLocationLastEffDate(String compId, String deptId, String processId) {
      String METHOD_NAME = "getRQSPOCLocationLastEffDate";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationLastEffDate", 1));
      String lastEffDate = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT EFFECTIVE_DATE FROM R_QUALITY_SPOC_LOCATION   RQSL WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID=? AND EFFECTIVE_DATE=(SELECT MAX(EFFECTIVE_DATE) FROM R_QUALITY_SPOC_LOCATION WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID=?) AND UPDATE_TIMESTAMP=(SELECT MAX(UPDATE_TIMESTAMP) FROM R_QUALITY_SPOC_LOCATION WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID=?)";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationLastEffDate", 0, "GET RQ SPOC LOCATION LAST EFFECTIVE DATE SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, processId);
         pstmt.setString(4, compId);
         pstmt.setString(5, deptId);
         pstmt.setString(6, processId);
         pstmt.setString(7, compId);
         pstmt.setString(8, deptId);
         pstmt.setString(9, processId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            lastEffDate = rs.getString("EFFECTIVE_DATE");
            if (lastEffDate != null && lastEffDate.trim().length() != 0) {
               lastEffDate = effDateFormat.format(new Date(Long.parseLong(lastEffDate)));
            }
         }
      } catch (SQLException var13) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationLastEffDate", 0, "SQL Exception Occured."), var13);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationLastEffDate", 0, "Last effective date found as ", lastEffDate));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationLastEffDate", 2));
      return lastEffDate;
   }

   public Map<String, String> getRQSPOCLocations(String processId, String compId, String deptId) {
      String METHOD_NAME = "getRQSPOCLocations";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocations", 1));
      Map<String, String> locations = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT RL.LOCATION_ID, RL.LOCATION_NAME FROM RMF_LOCATION RL WHERE RL.LOCATION_ID IN ( SELECT RQSL.LOCATION_ID FROM R_QUALITY_SPOC_LOCATION RQSL WHERE RQSL.COMPANY_ID=? AND RQSL.DEPARTMENT_ID=? AND RQSL.PROCESS_ID=? AND RQSL.EFFECTIVE_DATE=(SELECT Max(EFFECTIVE_DATE) FROM R_QUALITY_SPOC_LOCATION WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID=? ) AND RQSL.UPDATE_TIMESTAMP=(SELECT Max(UPDATE_TIMESTAMP) FROM R_QUALITY_SPOC_LOCATION WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID=?)) ORDER BY RL.LOCATION_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocations", 0, "GET RQ SPOC LOCATIONS SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, processId);
         pstmt.setString(4, compId);
         pstmt.setString(5, deptId);
         pstmt.setString(6, processId);
         pstmt.setString(7, compId);
         pstmt.setString(8, deptId);
         pstmt.setString(9, processId);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            locations.put(rs.getString("LOCATION_ID"), rs.getString("LOCATION_NAME"));
         }
      } catch (SQLException var13) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocations", 0, "SQL Exception Occured."), var13);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocations", 0, "SPOC Locations found as ", locations.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocations", 2));
      return locations;
   }

   public Map<String, String> getRQSPOCLocationsQA(String processId, String compId, String deptId, String effectiveDate) {
      String METHOD_NAME = "getRQSPOCLocationsQA";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationsQA", 1));
      Map<String, String> locations = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;
      long dtValue = this.parseDate(effectiveDate);

      try {
         String sqlStmt = "SELECT LOCATION_ID, LOCATION_NAME FROM RMF_LOCATION WHERE LOCATION_ID IN (SELECT DISTINCT(RQSL.LOCATION_ID) FROM R_QUALITY_SPOC_LOCATION RQSL WHERE RQSL.COMPANY_ID=? AND RQSL.DEPARTMENT_ID=? AND RQSL.PROCESS_ID=? AND RQSL.EFFECTIVE_DATE=(SELECT MAX(EFFECTIVE_DATE) FROM R_QUALITY_SPOC_LOCATION WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID=?  AND EFFECTIVE_DATE<=?) AND RQSL.UPDATE_TIMESTAMP=(SELECT MAX(UPDATE_TIMESTAMP) FROM R_QUALITY_SPOC_LOCATION WHERE COMPANY_ID=? AND DEPARTMENT_ID=? AND PROCESS_ID=?  AND EFFECTIVE_DATE<=?) ) ORDER BY LOCATION_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationsQA", 0, "GET RQ SPOC LOCATIONS QA SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, processId);
         pstmt.setString(4, compId);
         pstmt.setString(5, deptId);
         pstmt.setString(6, processId);
         pstmt.setLong(7, dtValue);
         pstmt.setString(8, compId);
         pstmt.setString(9, deptId);
         pstmt.setString(10, processId);
         pstmt.setLong(11, dtValue);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            locations.put(rs.getString("LOCATION_ID"), rs.getString("LOCATION_NAME"));
         }
      } catch (SQLException var16) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationsQA", 0, "SQL Exception Occured."), var16);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationsQA", 0, "Locations found as ", locations.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationsQA", 2));
      return locations;
   }

   public Map<String, String> getRQSPOCLocationDetails(String compId, String deptId, String processId, String locationId) {
      String METHOD_NAME = "getRQSPOCLocationDetails";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetails", 1));
      Map<String, String> locDetails = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT  RQSL.LOCATION_ID, RL.LOCATION_NAME, RQSL.REPORTING_PERSON, RQSL.PRIMARY_SPOC, RQSL.SECONDARY_SPOC, RQSL.SPOC_LOCATION_DESCRIPTION, RQSL.EFFECTIVE_DATE , RQSL.UPDATE_TIMESTAMP FROM  R_QUALITY_SPOC_LOCATION RQSL, RMF_LOCATION RL WHERE  RQSL.COMPANY_ID=? AND RQSL.DEPARTMENT_ID=?  AND RQSL.PROCESS_ID=?  AND RQSL.LOCATION_ID=?  AND RQSL.LOCATION_ID=RL.LOCATION_ID ORDER BY  RQSL.EFFECTIVE_DATE DESC, RQSL.UPDATE_TIMESTAMP  DESC";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetails", 0, "GET RQ SPOC LOCATION DETAILS SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, processId);
         pstmt.setString(4, locationId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            locDetails.put("LOCATION_ID", rs.getString("LOCATION_ID"));
            locDetails.put("LOCATION_NAME", rs.getString("LOCATION_NAME"));
            locDetails.put("REPORTING_PERSON", rs.getString("REPORTING_PERSON"));
            locDetails.put("PRIMARY_SPOC", rs.getString("PRIMARY_SPOC"));
            locDetails.put("SECONDARY_SPOC", rs.getString("SECONDARY_SPOC"));
            locDetails.put("SPOC_LOCATION_DESCRIPTION", rs.getString("SPOC_LOCATION_DESCRIPTION"));
         }
      } catch (SQLException var14) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetails", 0, "SQL Exception Occured."), var14);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetails", 0, "Locations Details found as ", locDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetails", 2));
      return locDetails;
   }

   public Map<String, String> getRQSPOCLocationDetailsQA(String compId, String deptId, String processId, String locationId, String effectiveDate) {
      String METHOD_NAME = "getRQSPOCLocationDetailsQA";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetailsQA", 1));
      Map<String, String> locDetails = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;
      long dtValue = this.parseDate(effectiveDate);

      try {
         String sqlStmt = "SELECT  RQSL.LOCATION_ID, RL.LOCATION_NAME, RQSL.REPORTING_PERSON, RQSL.PRIMARY_SPOC, RQSL.SECONDARY_SPOC, RQSL.SPOC_LOCATION_DESCRIPTION, RQSL.EFFECTIVE_DATE , RQSL.UPDATE_TIMESTAMP FROM  R_QUALITY_SPOC_LOCATION RQSL, RMF_LOCATION RL WHERE  RQSL.COMPANY_ID=? AND RQSL.DEPARTMENT_ID=?  AND RQSL.PROCESS_ID=?  AND RQSL.LOCATION_ID=?  AND RQSL.EFFECTIVE_DATE=(SELECT MAX(EFFECTIVE_DATE) FROM R_QUALITY_SPOC_LOCATION WHERE COMPANY_ID=? AND DEPARTMENT_ID=?  AND PROCESS_ID=?  AND LOCATION_ID=? AND EFFECTIVE_DATE<=?) AND RQSL.UPDATE_TIMESTAMP=(SELECT MAX(UPDATE_TIMESTAMP) FROM R_QUALITY_SPOC_LOCATION WHERE COMPANY_ID=? AND DEPARTMENT_ID=?  AND PROCESS_ID=?  AND LOCATION_ID=? AND EFFECTIVE_DATE<=?) AND RQSL.LOCATION_ID=RL.LOCATION_ID";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetailsQA", 0, "GET RQ SPOC LOCATION DETAILS QA SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, processId);
         pstmt.setString(4, locationId);
         pstmt.setString(5, compId);
         pstmt.setString(6, deptId);
         pstmt.setString(7, processId);
         pstmt.setString(8, locationId);
         pstmt.setLong(9, dtValue);
         pstmt.setString(10, compId);
         pstmt.setString(11, deptId);
         pstmt.setString(12, processId);
         pstmt.setString(13, locationId);
         pstmt.setLong(14, dtValue);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            locDetails.put("LOCATION_ID", rs.getString("LOCATION_ID"));
            locDetails.put("LOCATION_NAME", rs.getString("LOCATION_NAME"));
            locDetails.put("REPORTING_PERSON", rs.getString("REPORTING_PERSON"));
            locDetails.put("PRIMARY_SPOC", rs.getString("PRIMARY_SPOC"));
            locDetails.put("SECONDARY_SPOC", rs.getString("SECONDARY_SPOC"));
            locDetails.put("SPOC_LOCATION_DESCRIPTION", rs.getString("SPOC_LOCATION_DESCRIPTION"));
         }
      } catch (SQLException var17) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetailsQA", 0, "SQL Exception Occured."), var17);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetailsQA", 0, "Location Details found as ", locDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getRQSPOCLocationDetailsQA", 2));
      return locDetails;
   }

   public Map<String, String> getQualityClause() {
      String METHOD_NAME = "getQualityClause";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityClause", 1));
      Map<String, String> qClauses = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT ID, CLAUSE_NAME FROM R_QUALITY_CLAUSE ORDER BY CLAUSE_NAME ";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQualityClause", 0, "GET QUALITY CLAUSE SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            qClauses.put(rs.getString("ID"), rs.getString("CLAUSE_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityClause", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQualityClause", 0, "Quality Clause found as", qClauses.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityClause", 2));
      return qClauses;
   }

   public Map<String, String> getQualityClauseDetails(String clauseId) {
      String METHOD_NAME = "getQualityClauseDetails";
      System.out.println("CLAUSE VALUE PASSED********* " + clauseId);
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityClauseDetails", 1));
      Map<String, String> qClauseDetails = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT ID, CLAUSE_NAME, DESCRIPTION FROM R_QUALITY_CLAUSE WHERE ID=?";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQualityClauseDetails", 0, "GET QUALITY CLAUSE DETAILS SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, clauseId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            qClauseDetails.put("ID", rs.getString("ID"));
            qClauseDetails.put("CLAUSE_NAME", rs.getString("CLAUSE_NAME"));
            qClauseDetails.put("DESCRIPTION", rs.getString("DESCRIPTION"));
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityClauseDetails", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQualityClauseDetails", 0, "Quality Clause Details found as ", qClauseDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityClauseDetails", 2));
      return qClauseDetails;
   }

   private Map getProcessInstanceInfo(String appName, List getDSName, Map dsMap) {
      String METHOD_NAME = "getProcessInstanceInfo";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 1));
      Map piInfoMap = new HashMap();
      BLServer server = null;
      Session session = null;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      BizLogicUtil blUtil = null;

      try {
         blUtil = BizLogicUtil.getInstance();
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "BL Util " + blUtil));
         server = blUtil.getBLServer();
         session = blUtil.getBLSession();
         conn = this.getConnection(SBM_DB_JNDI);
         ProcessTemplateList ptl = server.getProcessTemplateVersions(session, appName);
         List ptlList = ptl.getList();
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "Found [" + ptlList.size() + "] ProcessTemplates against Application " + appName));
         StringBuilder condition = new StringBuilder();
         Iterator mapIter = dsMap.entrySet().iterator();

         for(boolean multipleDS = false; mapIter.hasNext(); multipleDS = true) {
            if (multipleDS) {
               condition.append(" AND ");
            }

            Entry pairs = (Entry)((Entry)mapIter.next());
            condition.append("BLIDS.");
            condition.append(pairs.getKey());
            condition.append("='");
            condition.append(pairs.getValue());
            condition.append("'");
         }

         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "Condition", condition.toString()));
         boolean piFound = false;
         int j = 0;

         for(int ptlListSize = ptlList.size(); j < ptlListSize && !piFound; ++j) {
            ProcessTemplate pt = (ProcessTemplate)ptlList.get(j);
            logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "App Name", pt.getAppName()));
            logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "PT Id |" + pt.getID()));
            logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "Name", pt.getName()));
            StringBuilder sqlStmt = new StringBuilder();
            sqlStmt.append("SELECT BLPI.PROCESS_INSTANCE_ID, DECODE(BLPI.STATUS, 7, 'Active', '8', 'Suspended') STATUS ");
            Iterator iter = getDSName.iterator();

            String dsName;
            while(iter.hasNext()) {
               dsName = (String)iter.next();
               sqlStmt.append(", BLIDS.");
               sqlStmt.append(dsName);
            }

            sqlStmt.append(" FROM BIZLOGIC_PROCESSINSTANCE BLPI, BIZLOGIC_DS_" + pt.getID() + " BLIDS WHERE   BLPI.PROCESS_TEMPLATE_ID=BLIDS.PROCESS_TEMPLATE_ID AND BLPI.PROCESS_INSTANCE_ID=BLIDS.PROCESS_INSTANCE_ID ");
            if (condition != null && condition.length() != 0) {
               sqlStmt.append(" AND ");
               sqlStmt.append(condition);
            }

            logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "GET PROCESS INSTANCE SQL", sqlStmt.toString()));
            pstmt = conn.prepareStatement(sqlStmt.toString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
               piFound = true;
               piInfoMap.put(PIID, rs.getString("PROCESS_INSTANCE_ID"));
               piInfoMap.put(STATUS, rs.getString("STATUS"));
               iter = getDSName.iterator();

               while(iter.hasNext()) {
                  dsName = (String)iter.next();
                  logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "Add DataSlot with Name ", dsName));
                  if (dsName.equalsIgnoreCase("AUDITDATE")) {
                     Timestamp ad = rs.getTimestamp(dsName);
                     System.out.println("Audit Date Timestamp value " + ad);
                     if (ad != null) {
                        String auditDate = effDateFormat.format(new Date(ad.getTime()));
                        piInfoMap.put(dsName, auditDate);
                     }
                  } else {
                     piInfoMap.put(dsName, rs.getString(dsName));
                  }
               }
            }

            logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "Template Name", pt.getName()));
            if (piFound) {
               logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "PI found..."));
               String piId = (String)piInfoMap.get(PIID);
               ProcessInstance pInst = server.getProcessInstance(session, Long.parseLong(piId));
               String[] wsNames = pInst.getActivatedWorkStepNames();
               if (wsNames != null && wsNames.length <= 0) {
                  wsNames = pInst.getSuspendedWorkStepNames();
               }

               StringBuilder wsNamesString = new StringBuilder();
               String[] var29 = wsNames;
               int var27 = 0;

               for(int var28 = wsNames.length; var27 < var28; ++var27) {
                  String str = var29[var27];
                  if (wsNamesString.length() > 0) {
                     wsNamesString.append(", ");
                  }

                  wsNamesString.append(str);
               }

               piInfoMap.put("WSNAME", wsNamesString.toString());
               break;
            }
         }
      } catch (RemoteException var35) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "Remote Exception Occured...."), var35);
      } catch (SQLException var36) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "SQL Exception Occured...."), var36);
      } catch (Exception var37) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "Exception Occured...."), var37);
      } finally {
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "Disconnecting BLSession..."));
         blUtil.disconnectBLServer();
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 0, "PI Info found as ", piInfoMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getProcessInstanceInfo", 2));
      return piInfoMap;
   }

   public Map<String, String> isQualityProcessNameExist(String compId, String deptId, String qProcessName) {
      String METHOD_NAME = "isQualityProcessNameExist";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "isQualityProcessNameExist", 1));
      Map<String, String> status = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT RQP.PROCESS_NAME, RQP.DEPARTMENT_ID, RQP.COMPANY_ID, RQP.EFFECTIVE_DATE FROM R_QUALITY_PROCESS RQP WHERE RQP.COMPANY_ID=? AND RQP.DEPARTMENT_ID=? AND RQP.PROCESS_NAME=?";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "isQualityProcessNameExist", 0, "IS QUALITY PROCESS NAME EXIST SQL", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, compId);
         pstmt.setString(2, deptId);
         pstmt.setString(3, qProcessName);

         String effDate;
         for(rs = pstmt.executeQuery(); rs.next(); status.put("EFFECTIVE_DATE", effDate)) {
            status.put("PROCESS_NAME", rs.getString("PROCESS_NAME"));
            status.put("DEPARTMENT_ID", rs.getString("DEPARTMENT_ID"));
            status.put("COMPANY_ID", rs.getString("COMPANY_ID"));
            effDate = rs.getString("EFFECTIVE_DATE");
            if (effDate != null && effDate.trim().length() != 0) {
               effDate = effDateFormat.format(new Date(Long.parseLong(effDate)));
            }
         }
      } catch (SQLException var14) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "isQualityProcessNameExist", 0, "SQL Exception Occured."), var14);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "isQualityProcessNameExist", 0, "Quality Process Status found as ", status.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "isQualityProcessNameExist", 2));
      return status;
   }

   public Map getQualityDeptManagementPIInfo(String compId, String deptId) {
      String METHOD_NAME = "getQualityDeptManagementPIInfo";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityDeptManagementPIInfo", 1));
      Map dsMap = new HashMap();
      dsMap.put(DS_COMPANY, compId);
      dsMap.put(DS_DEPARTMENT, deptId);
      List getDSName = new ArrayList();
      getDSName.add(DS_INITIATOR);
      getDSName.add(DS_INITIATORNAME);
      Map piInfoMap = this.getProcessInstanceInfo("QualityDeptManagement", getDSName, dsMap);
      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQualityDeptManagementPIInfo", 0, "Quality Dept Management PI Info found as ", piInfoMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityDeptManagementPIInfo", 2));
      return piInfoMap;
   }

   public Map getQualityProcessManagementPIInfo(String compId, String deptId, String processName) {
      String METHOD_NAME = "getQualityProcessManagementPIInfo";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityProcessManagementPIInfo", 1));
      Map dsMap = new HashMap();
      dsMap.put(DS_COMPANY, compId);
      dsMap.put(DS_DEPARTMENT, deptId);
      dsMap.put(DS_AUDITPROCESSNAME, processName);
      List getDSName = new ArrayList();
      getDSName.add(DS_INITIATOR);
      getDSName.add(DS_INITIATORNAME);
      Map piInfoMap = this.getProcessInstanceInfo("QualityProcessManagement", getDSName, dsMap);
      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQualityProcessManagementPIInfo", 0, "Quality Process Management PI Info found as ", piInfoMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityProcessManagementPIInfo", 2));
      return piInfoMap;
   }

   public Map getQualityAuditPIInfo(String compId, String deptId, String processId, String locationId) {
      String METHOD_NAME = "getQualityAuditPIInfo";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityAuditPIInfo", 1));
      Map dsMap = new HashMap();
      dsMap.put(DS_COMPANY, compId);
      dsMap.put(DS_DEPARTMENT, deptId);
      dsMap.put(DS_AUDITPROCESS, processId);
      dsMap.put(DS_AUDIT_LOCATION, locationId);
      List getDSName = new ArrayList();
      getDSName.add("AUDITINITIATOR");
      getDSName.add("AUDITINITIATORNAME");
      getDSName.add("AUDITTYPE");
      getDSName.add("AUDITDATE");
      Map piInfoMap = this.getProcessInstanceInfo("QualityAudit", getDSName, dsMap);
      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getQualityAuditPIInfo", 0, "Quality Audit PI Info found as ", piInfoMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getQualityAuditPIInfo", 2));
      return piInfoMap;
   }

   private long parseDate(String dateString) {
      String METHOD_NAME = "parseDate";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "parseDate", 1));
      long timeValue = 0L;

      try {
         SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa");
         Date dt = sdf.parse(dateString);
         timeValue = dt.getTime();
      } catch (ParseException var7) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "parseDate", 0, "Parse Exception Occured."), var7);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "parseDate", 2));
      return timeValue;
   }

   public Map getFullName(String userName) {
      String METHOD_NAME = "getFullName";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getFullName", 1));
      Map hMap = new HashMap();
      Realm realm = UserManager.getDefaultRealm();
      String fullName = null;
      String status = null;
      User user = null;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         user = jdbcRealm.getUser(userName);
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm ldapRealm = (LDAPRealm)realm;
         user = ldapRealm.getUser(userName);
      } else if (realm instanceof PAKRealm) {
         PAKRealm pakRealm = (PAKRealm)realm;
         user = pakRealm.getUser(userName);
      }

      if (user != null) {
         status = "AVAILABLE";
         fullName = user.getAttribute("firstname") + " " + user.getAttribute("lastname");
      } else {
         status = "NOT_AVAILABLE";
         fullName = "";
      }

      hMap.put("STATUS", status);
      hMap.put("FULL_NAME", fullName);
      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getFullName", 0, "Full Name found as ", hMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getFullName", 2));
      return hMap;
   }

   public Vector<String> getGroupUsers(String groupName, String role) {
      String METHOD_NAME = "getGroupUsers";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getGroupUsers", 1));
      Realm realm = UserManager.getDefaultRealm();
      Vector<String> members = new Vector();
      String[] userMembers = (String[])null;
      AdvanceGroup gp = null;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         gp = (AdvanceGroup)jdbcRealm.getGroup(groupName);
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm ldapRealm = (LDAPRealm)realm;
         gp = (AdvanceGroup)ldapRealm.getGroup(groupName);
      } else if (realm instanceof PAKRealm) {
         PAKRealm pakRealm = (PAKRealm)realm;
         gp = (AdvanceGroup)pakRealm.getGroup(groupName);
      }

      if (gp != null) {
         if (role != null && role.trim().length() > 0) {
            userMembers = gp.getAllUserMemberNames(role);
         } else {
            userMembers = gp.getAllUserMemberNames();
         }

         if (userMembers != null && userMembers.length > 0) {
            for(int i = 0; i < userMembers.length; ++i) {
               members.add(userMembers[i]);
            }

            logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getGroupUsers", 0, "User found for given Group and Role", groupName, role, members.toString()));
         }
      }

      if (members.size() == 0) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getGroupUsers", 0, "No user found with given Group and Role", groupName, role));
         members.add("");
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getGroupUsers", 2));
      return members;
   }

   public Map<String, String> getIdeaExpressTheme() {
      String METHOD_NAME = "getIdeaExpressTheme";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressTheme", 1));
      Map<String, String> ieTheme = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT THEME_ID, THEME_NAME FROM R_IE_THEME ORDER BY THEME_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressTheme", 0, "GET IDEA EXPRESS THEME SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            ieTheme.put(rs.getString("THEME_ID"), rs.getString("THEME_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressTheme", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressTheme", 0, "Idea Express Theme found as", ieTheme.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressTheme", 2));
      return ieTheme;
   }

   public Map<String, String> getIdeaExpressStatus() {
      String METHOD_NAME = "getIdeaExpressStatus";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatus", 1));
      Map<String, String> ieStatus = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT STATUS_ID, STATUS_NAME FROM R_IE_STATUS ORDER BY STATUS_NAME";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatus", 0, "GET IDEA EXPRESS STATUS SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            ieStatus.put(rs.getString("STATUS_ID"), rs.getString("STATUS_NAME"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatus", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatus", 0, "Idea Express Staus found as", ieStatus.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatus", 2));
      return ieStatus;
   }

   public Map<String, String> getIdeaExpressThemeDetails(String themeId) {
      String METHOD_NAME = "getIdeaExpressThemeDetails";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressThemeDetails", 1));
      Map<String, String> ieThemeDetails = new HashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT THEME_ID, THEME_NAME FROM R_IE_THEME WHERE THEME_ID=?";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressThemeDetails", 0, "GET IDEA EXPRESS THEME DETAILS SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, themeId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            ieThemeDetails.put("THEME_ID", rs.getString("THEME_ID"));
            ieThemeDetails.put("THEME_NAME", rs.getString("THEME_NAME"));
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressThemeDetails", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressThemeDetails", 0, "Idea Express Theme Details found as", ieThemeDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressThemeDetails", 2));
      return ieThemeDetails;
   }

   public Map<String, String> getIdeaExpressStatusDetails(String statusId) {
      String METHOD_NAME = "getIdeaExpressStatusDetails";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatusDetails", 1));
      Map<String, String> ieStatusDetails = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT STATUS_ID, STATUS_NAME FROM R_IE_STATUS WHERE STATUS_ID=?";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatusDetails", 0, "GET IDEA EXPRESS STATUS DETAILS SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, statusId);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            ieStatusDetails.put("STATUS_ID", rs.getString("STATUS_ID"));
            ieStatusDetails.put("STATUS_NAME", rs.getString("STATUS_NAME"));
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatusDetails", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatusDetails", 0, "Idea Express Staus found as", ieStatusDetails.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getIdeaExpressStatusDetails", 2));
      return ieStatusDetails;
   }

   public Map<String, String> getUserInfoMap(String userName) {
      String METHOD_NAME = "getUserInfoMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getUserInfoMap", 1));
      Map<String, String> result = new HashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "select A.email as EMAIL, B.firstname as FIRSTNAME, C.lastname as LASTNAME, D.mobile as  MOBILE, E.dob as DOB, F.company AS COMPANY, G.zone as ZONE, H.location as LOCATION, I.designation as DESIGNATION, J.doj as DOJ, K.department as DEPARTMENT FROM (SELECT attr_value AS email FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='email' and uu.user_name='" + userName + "') A," + "(SELECT attr_value AS firstname FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='firstname' and uu.user_name='" + userName + "') B," + "(SELECT attr_value AS lastname FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='lastname' and uu.user_name='" + userName + "') C, " + "(SELECT attr_value AS mobile FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='mobile' and uu.user_name='" + userName + "') D, " + "(SELECT attr_value AS dob FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='dob' and uu.user_name='" + userName + "') E, " + "(SELECT attr_value AS company FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='company' and uu.user_name='" + userName + "') F, " + "(SELECT attr_value AS zone FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='zone' and uu.user_name='" + userName + "') G, " + "(SELECT attr_value AS location FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='location' and uu.user_name='" + userName + "') H, " + "(SELECT attr_value AS designation FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='designation' and uu.user_name='" + userName + "') I, " + "(SELECT attr_value AS doj FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='doj' and uu.user_name='" + userName + "') J," + "(SELECT attr_value AS department FROM umuser uu, userattr ua WHERE uu.user_id=ua.user_id AND  Lower(attr_name)='department' and uu.user_name='" + userName + "') K";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getUserInfoMap", 0, "GET USER INFO MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            result.put("EMAIL", rs.getString("EMAIL"));
            result.put("FIRSTNAME", rs.getString("FIRSTNAME"));
            result.put("LASTNAME", rs.getString("LASTNAME"));
            result.put("MOBILE", rs.getString("MOBILE"));
            result.put("DOB", rs.getString("DOB"));
            result.put("COMPANY", rs.getString("COMPANY"));
            result.put("ZONE", rs.getString("ZONE"));
            result.put("LOCATION", rs.getString("LOCATION"));
            result.put("DESIGNATION", rs.getString("DESIGNATION"));
            result.put("DOJ", rs.getString("DOJ"));
            result.put("DEPARTMENT", rs.getString("DEPARTMENT"));
         }
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getUserInfoMap", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getUserInfoMap", 0, "User Info Map found as ", result.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getUserInfoMap", 2));
      return result;
   }

   public Map getAllGroups() {
      String METHOD_NAME = "getAllGroups";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAllGroups", 1));
      Map hMap = new HashMap();
      Realm realm = UserManager.getDefaultRealm();
      String fullName = null;
      String status = null;
      User user = null;
      String[] grpNames = (String[])null;
      if (realm instanceof JDBCRealm) {
         JDBCRealm jdbcRealm = (JDBCRealm)realm;
         grpNames = jdbcRealm.getGroupNames();
      } else if (realm instanceof LDAPRealm) {
         LDAPRealm ldapRealm = (LDAPRealm)realm;
         grpNames = ldapRealm.getGroupNames();
      } else if (realm instanceof PAKRealm) {
         PAKRealm pakRealm = (PAKRealm)realm;
         grpNames = pakRealm.getGroupNames();
      }

      if (grpNames != null && grpNames.length > 0) {
         String[] var11 = grpNames;
         int var9 = 0;

         for(int var10 = grpNames.length; var9 < var10; ++var9) {
            String grp = var11[var9];
            if (grp != null && grp.trim().length() > 0) {
               hMap.put(grp, grp);
            }
         }
      }

      logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getAllGroups", 0, "Group Names found as ", hMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAllGroups", 2));
      return hMap;
   }

   public Map getTieupTypeMap() {
      String METHOD_NAME = "getTieupTypeMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getTieupTypeMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "SELECT Tieup_Type_Id,Tieup_Type_Name FROM TieupType ORDER BY Tieup_Type_Name";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getTieupTypeMap", 0, "GET Tieup MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("Tieup_Type_Id"), rs.getString("Tieup_Type_Name"));
         }
      } catch (SQLException var10) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getTieupTypeMap", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getTieupTypeMap", 2));
      return hMap;
   }

   public Map getTieupOnMap(int tieupTypeName) {
      String METHOD_NAME = "getAssetOnMap";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAssetOnMap", 1));
      Map<String, String> hMap = new LinkedHashMap();
      PreparedStatement pstmt = null;
      Connection conn = null;
      ResultSet rs = null;

      try {
         String sqlStmt = "SELECT Tieup_ON_Name FROM TieupOn WHERE Tieup_On_Id IN (SELECT Tieup_On_Id FROM TieupType_TieupOn WHERE Tieup_Type_Id=?)";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getAssetOnMap", 0, "GET TieupOnMap  SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         System.out.println("sqlStmt for tieup: " + sqlStmt);
         System.out.println("tieupTypeName: " + tieupTypeName);
         pstmt = conn.prepareStatement(sqlStmt);
         System.out.println("conn: " + conn);
         pstmt.setInt(1, tieupTypeName);
         System.out.println("PSt:---" + pstmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("Tieup_ON_Name"), rs.getString("Tieup_ON_Name"));
            System.out.println("Tieup_ON_Name: " + rs.getString(1));
         }

         System.out.println("hMap for TieupOn:" + hMap);
      } catch (SQLException var11) {
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAssetOnMap", 0, "SQL Exception Occured."), var11);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getAssetOnMap", 2));
      return hMap;
   }

   public void insertBrokerageData(Map inputMap) throws Exception {
      System.out.println("DataLoader.insertBrokerageData() - Start");
      System.out.println("input Map = " + inputMap);
      String brokerageIdQuery = "SELECT BROKERAGE_SEQUENCE.NEXTVAL FROM DUAL";
      String schemeQuery = "INSERT INTO SCHEMES_DETAILS VALUES(?,?)";
      String assetQuery = "INSERT INTO ASSET_DETAILS VALUES(?,?)";
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String assetValues = (String)inputMap.get("setTestAssetClasses");
      String schemeValues = (String)inputMap.get("setTestSchemeNames");
      String assetorscheme = (String)inputMap.get("setIsAssetClassOrScheme");
      String brokerageType = (String)inputMap.get("setBrokerageType");
      String agentCode = (String)inputMap.get("setAgentCode");
      String agentName = (String)inputMap.get("setAgentName");
      String location = (String)inputMap.get("setLocation");
      String category = "";
      if ("Mumbai - CO".equalsIgnoreCase(location)) {
         category = "Corporate";
      } else {
         category = "Branch";
      }

      String schemeCoverage = (String)inputMap.get("setIsAssetClassOrScheme");
      String folioNo = (String)inputMap.get("setFolio");
      String tieupType = (String)inputMap.get("setTieupType");
      String tieupOn = (String)inputMap.get("setTieupOn");
      String brokType = (String)inputMap.get("setBrokerageType");
      String userId = (String)inputMap.get("setUser");
      String reportingSAPId = (String)inputMap.get("setReportingManagerSAPID");
      String regionalHeadSAPId = (String)inputMap.get("setRegionalHeadSAPID");
      String branchHeadSAPId = (String)inputMap.get("setBranchHeadSAPID");
      String zonalHeadSAPId = (String)inputMap.get("setZonalHeadSAPID");
      String segmentHeadId = (String)inputMap.get("setSegmentHeadSAPID");
      String salesHeadId = (String)inputMap.get("setSalesHeadSAPID");
      String zonalHeadApproval = (String)inputMap.get("setZonalHeadApproval");
      String segmentHeadApproval = (String)inputMap.get("setSegmentHeadApproval");
      String segmentGroupApproval = (String)inputMap.get("setSegmentGroupApproval");
      String salesHeadApproval = (String)inputMap.get("setSalesHeadApproval");
      String reportingManagerApproval = (String)inputMap.get("setReportingManagerApproval");
      String regionalManagerApproval = (String)inputMap.get("setRegionalManagerApproval");
      String branchManagerApproval = (String)inputMap.get("setBranchManagerApproval");
      String masterQuery = "INSERT INTO  BROKERAGE_MASTER(TIEUP_ID,Agent_Code,Agent_Name,Tieup_Location,Category,Scheme_Coverage,FOLIO_NUMBER,Tieup_Type,Tieup_On,Brokerage_Type,User_SAPID,ReportingManager_SAPID,RegioanlHead_SAPID,BranchManager_SAPID,ZonalHead_SAPID,SegmentUser_SAPID,SalesHead_SAPID,ReportingManager_Approval,RegionalManager_Approval,BranchManager_Approval,ZonalHead_Approval,SegmentHead_Approval,SalesHead_Approval)  VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      System.out.println("Executing Query 1");

      try {
         System.out.println("Trying to fetch Connection  = " + SBM_DB_JNDI);
         Context jndiCntx = ServiceLocator.self().getInitialContext();
         DataSource ds = (DataSource)jndiCntx.lookup(SBM_DB_JNDI);
         conn = ds.getConnection();
         System.out.println("Connection = " + conn);
         System.out.println("Connection Details = " + conn.getMetaData().getURL() + " " + conn.getMetaData().getUserName());
         pstmt = conn.prepareStatement(brokerageIdQuery);
         rs = pstmt.executeQuery();
         int brokerageId = 0;
         if (rs.next()) {
            brokerageId = rs.getInt(1);
         }

         System.out.println("brokerageId --- " + brokerageId);
         this.closeResources(rs, pstmt, (Connection)null);
         pstmt = conn.prepareStatement(masterQuery);
         pstmt.setInt(1, brokerageId);
         pstmt.setString(2, agentCode);
         pstmt.setString(3, agentName);
         pstmt.setString(4, location);
         pstmt.setString(5, category);
         pstmt.setString(6, schemeCoverage);
         pstmt.setString(7, folioNo);
         pstmt.setString(8, tieupType);
         pstmt.setString(9, tieupOn);
         pstmt.setString(10, brokType);
         pstmt.setString(11, userId);
         pstmt.setString(12, reportingSAPId);
         pstmt.setString(13, regionalHeadSAPId);
         pstmt.setString(14, branchHeadSAPId);
         pstmt.setString(15, zonalHeadSAPId);
         pstmt.setString(16, segmentHeadId);
         pstmt.setString(17, salesHeadId);
         pstmt.setString(18, reportingManagerApproval);
         pstmt.setString(19, regionalManagerApproval);
         pstmt.setString(20, branchManagerApproval);
         pstmt.setString(21, zonalHeadApproval);
         pstmt.setString(22, segmentHeadApproval);
         pstmt.setString(23, salesHeadApproval);
         pstmt.execute();
         System.out.println("master Records Inserted");
         this.closeResources((ResultSet)null, pstmt, (Connection)null);
         System.out.println("Asset or Scheme: " + assetorscheme + ":" + assetValues + ":" + schemeValues);
         String[] schemeArrays;
         int i;
         if ("AssetClass".equalsIgnoreCase(assetorscheme)) {
            pstmt = conn.prepareStatement(assetQuery);
            schemeArrays = assetValues.split(",");
            System.out.println("Length of AssetArrays: " + schemeArrays.length);

            for(i = 0; i < schemeArrays.length; ++i) {
               if (schemeArrays[i] != null && !schemeArrays[i].equals("")) {
                  pstmt.setInt(1, brokerageId);
                  pstmt.setString(2, schemeArrays[i]);
                  pstmt.execute();
               }
            }
         } else if ("Scheme".equalsIgnoreCase(assetorscheme)) {
            pstmt = conn.prepareStatement(schemeQuery);
            schemeArrays = schemeValues.split(",");
            System.out.println("Length of schemeArrays: " + schemeArrays.length);

            for(i = 0; i < schemeArrays.length; ++i) {
               if (schemeArrays[i] != null && !schemeArrays[i].equals("")) {
                  pstmt.setInt(1, brokerageId);
                  pstmt.setString(2, schemeArrays[i]);
                  pstmt.execute();
               }
            }
         }

         this.closeResources((ResultSet)null, pstmt, (Connection)null);
         String fixedQuery;
         if (brokerageType.equalsIgnoreCase("Fixed Percentage")) {
            fixedQuery = "INSERT INTO BROKERAGE_TYPE(TIEUP_ID,PERCENTAGE) VALUES(?,?)";
            String fixedPerc = (String)inputMap.get("setBokeragePercentage");
            System.out.println("fixedPerc: " + fixedPerc);
            if (fixedPerc != null && !fixedPerc.equals("")) {
               double fixedValue = Double.parseDouble(fixedPerc);
               pstmt = conn.prepareStatement(fixedQuery);
               pstmt.setInt(1, brokerageId);
               pstmt.setDouble(2, fixedValue);
               pstmt.execute();
            }
         } else if (brokerageType.equalsIgnoreCase("Slabs")) {
            fixedQuery = "INSERT INTO BROKERAGE_TYPE VALUES(?,?,?,?,?)";
            Vector slabsList = (Vector)((Vector)inputMap.get("setSlabList"));
            System.out.println("slabsList size ---" + slabsList.size());
            pstmt = conn.prepareStatement(fixedQuery);

            for(i = 0; i < slabsList.size(); ++i) {
               String slabs = (String)slabsList.get(i);
               System.out.println("slabs ---" + slabs);
               String[] slabsArray = slabs.split(",");
               int fromSlab = slabsArray[0].equals("") ? 0 : Integer.parseInt(slabsArray[0]);
               int toSlab = slabsArray[1].equals("") ? 0 : Integer.parseInt(slabsArray[1]);
               double percen = slabsArray[2].equals("") ? 0.0D : Double.parseDouble(slabsArray[2]);
               double amount = slabsArray[3].equals("") ? 0.0D : Double.parseDouble(slabsArray[3]);
               pstmt.setInt(1, brokerageId);
               pstmt.setInt(2, fromSlab);
               pstmt.setInt(3, toSlab);
               pstmt.setDouble(4, percen);
               pstmt.setDouble(5, amount);
               pstmt.execute();
            }
         }
      } catch (Exception var53) {
         var53.printStackTrace();
         throw var53;
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      System.out.println("DataLoader.insertBrokerageData() - End");
   }

   public Map getApplicationDetails(String applicationName) {
      Map applicationDetails = new HashMap();
      System.out.println("DataLoader.getApplicationDetails() - Start");

      try {
         System.out.println("Trying to fetch Connection  = " + SBM_DB_JNDI);
         Context jndiCntx = ServiceLocator.self().getInitialContext();
         DataSource ds = (DataSource)jndiCntx.lookup(SBM_DB_JNDI);
         Connection conn = ds.getConnection();
         System.out.println("Connection = " + conn);
         System.out.println("Connection Details = " + conn.getMetaData().getURL() + " " + conn.getMetaData().getUserName());
         PreparedStatement pstmt = conn.prepareStatement("select * from rmf_user_application_mapping where application_name = ? ");
         pstmt.setString(1, applicationName);
         ResultSet rs = pstmt.executeQuery();
         String approverGroup = null;
         String implementorGroup = null;

         String applicationLink;
         for(applicationLink = null; rs.next(); applicationLink = rs.getString("APPLICATION_LINK")) {
            approverGroup = rs.getString("APPROVER_GROUP");
            implementorGroup = rs.getString("IMPLEMENTOR_GROUP");
         }

         Realm realm = UserManager.getDefaultRealm();
         AdvanceGroup advanceGroup = (AdvanceGroup)realm.getGroup(approverGroup);
         System.out.println("advanceGroup = " + advanceGroup);
         if (advanceGroup != null) {
            String[] groupUsers = advanceGroup.getAllUserMemberNames();
            System.out.println("groupUsers = " + groupUsers.length);
            if (groupUsers != null && groupUsers.length != 0) {
               applicationDetails.put("GROUP_MEMBERS_AVAILABLE", "TRUE");
            } else {
               applicationDetails.put("GROUP_MEMBERS_AVAILABLE", "FALSE");
            }
         } else {
            applicationDetails.put("GROUP_MEMBERS_AVAILABLE", "FALSE");
         }

         applicationDetails.put("APPROVER_GROUP", approverGroup);
         applicationDetails.put("IMPLEMENTOR_GROUP", implementorGroup);
         applicationDetails.put("APPLICATION_LINK", applicationLink);
         applicationDetails.put("APPLICATION_NAME", applicationName);
      } catch (Exception var14) {
         var14.printStackTrace();
      }

      System.out.println("applicationDetails = " + applicationDetails);
      System.out.println("DataLoader.getApplicationDetails() - End");
      return applicationDetails;
   }

   public String getTieupTypeDescription(String tieupTypeId) {
      System.out.println("DataLoader.getTieupTypeDescription() - Start");
      String tieupTypeDesc = "";

      try {
         Context jndiCntx = ServiceLocator.self().getInitialContext();
         DataSource ds = (DataSource)jndiCntx.lookup(SBM_DB_JNDI);
         Connection conn = ds.getConnection();
         System.out.println("Connection = " + conn);
         System.out.println("tieupTypeId = " + tieupTypeId);
         System.out.println("Connection Details = " + conn.getMetaData().getURL() + " " + conn.getMetaData().getUserName());
         PreparedStatement pstmt = conn.prepareStatement("SELECT Tieup_Type_Description FROM TieupType WHERE tieup_type_id = ? ORDER BY Tieup_Type_Name");
         pstmt.setString(1, tieupTypeId);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()) {
            tieupTypeDesc = rs.getString(1);
            System.out.println("fetched tieupTypeDesc = " + tieupTypeDesc);
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      System.out.println("DataLoader.getTieupTypeDescription() - End");
      return tieupTypeDesc;
   }

   public String getTieupOnDescription(String typeOnId) {
      System.out.println("DataLoader.getTieupOnDescription() - Start");
      String tieupOnDesc = "";

      try {
         Context jndiCntx = ServiceLocator.self().getInitialContext();
         DataSource ds = (DataSource)jndiCntx.lookup(SBM_DB_JNDI);
         Connection conn = ds.getConnection();
         System.out.println("Connection = " + conn);
         System.out.println("typeOnId = " + typeOnId);
         System.out.println("Connection Details = " + conn.getMetaData().getURL() + " " + conn.getMetaData().getUserName());
         PreparedStatement pstmt = conn.prepareStatement("SELECT tieup_on_description FROM TieupOn WHERE tieup_on_name = ? ORDER BY tieup_on_id");
         pstmt.setString(1, typeOnId);
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()) {
            tieupOnDesc = rs.getString(1);
            System.out.println("fetched tieupOnDesc = " + tieupOnDesc);
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      System.out.println("DataLoader.getTieupOnDescription() - End");
      return tieupOnDesc;
   }

   public String validateReportingPerson(String reportingPerson) {
      System.out.println("DataLoader.validateReportingPerson() - Start");
      String result = "FALSE";

      try {
         System.out.println("Trying to get object for user = " + reportingPerson);
         Realm realm = UserManager.getDefaultRealm();
         User user = realm.getUser(reportingPerson);
         if (user != null) {
            result = "TRUE";
         }
      } catch (Exception var5) {
         System.out.println("Object not found for user = " + reportingPerson);
         var5.printStackTrace();
      }

      System.out.println("DataLoader.validateReportingPerson() - End");
      return result;
   }

   public Map getExpenseTypes() {
      String METHOD_NAME = "getExpenseTypes";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExpenseTypes", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      System.out.println("DataLoader.getExpenseTypes()");

      try {
         String sqlStmt = "SELECT expense_type_id,expense_name FROM rmf_expense_type ORDER BY expense_name";
         logger.debug(StaticFuncs.buildLogStatement("DataLoader", "getExpenseTypes", 0, "GET COMPANY MAP SQL ", sqlStmt));
         conn = this.getConnection(SBM_DB_JNDI);
         System.out.println("conn:::::::::" + conn);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("expense_type_id"), rs.getString("expense_name"));
         }
      } catch (SQLException var10) {
         var10.printStackTrace();
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExpenseTypes", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getExpenseTypes", 2));
      return hMap;
   }

   public Map getBudgetTypes() {
      String METHOD_NAME = "getBudgetTypes";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getBudgetTypes", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      System.out.println("DataLoader.getBudgetTypes()");

      try {
         String sqlStmt = "SELECT budget_type_id,budget_type_name FROM rmf_budget_type ORDER BY budget_type_name";
         conn = this.getConnection(SBM_DB_JNDI);
         System.out.println("conn:::::::::" + conn);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("budget_type_id"), rs.getString("budget_type_name"));
         }
      } catch (SQLException var10) {
         var10.printStackTrace();
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getBudgetTypes", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getBudgetTypes", 2));
      return hMap;
   }

   public Map getSegmentTypes() {
      String METHOD_NAME = "getSegmentTypes";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getSegmentTypes", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      System.out.println("DataLoader.getSegmentTypes()");

      try {
         String sqlStmt = "SELECT segment_id,segment_name FROM rmf_segment_details ORDER BY segment_name";
         conn = this.getConnection(SBM_DB_JNDI);
         System.out.println("conn:::::::::" + conn);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("segment_id"), rs.getString("segment_name"));
         }
      } catch (SQLException var10) {
         var10.printStackTrace();
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getSegmentTypes", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getSegmentTypes", 2));
      return hMap;
   }

   public Map getSubSegmentTypes() {
      String METHOD_NAME = "getSubSegmentTypes";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getSubSegmentTypes", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      System.out.println("DataLoader.getSubSegmentTypes()");

      try {
         String sqlStmt = "SELECT subsegment_id,subsegment_name FROM rmf_subsegment_details seg,rmf_segment_details subseg WHERE seg.segment_id = subseg.segment_id AND seg.segment_id='1'";
         conn = this.getConnection(SBM_DB_JNDI);
         System.out.println("conn:::::::::" + conn);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("subsegment_id"), rs.getString("subsegment_name"));
         }
      } catch (SQLException var10) {
         var10.printStackTrace();
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getSubSegmentTypes", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getSubSegmentTypes", 2));
      return hMap;
   }

   public Map getDistributorDetails(String agentCode) {
      String METHOD_NAME = "getDistributorDetails";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getDistributorDetails", 1));
      QueryFetcher dao = new QueryFetcher();
      Map distributorNameMap = dao.getAgent(agentCode);
      System.out.println("DataLoader.getDistributorDetails()");
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getDistributorDetails", 2));
      return distributorNameMap;
   }

   public Map getSchemes() {
      String METHOD_NAME = "getSchemes";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getSchemes", 1));
      QueryFetcher dao = new QueryFetcher();
      Map schemeMap = dao.getSchemeDetails();
      System.out.println("DataLoader.getSchemes()");
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getSchemes", 2));
      return schemeMap;
   }

   public Map getPaymentTypes() {
      String METHOD_NAME = "getPaymentTypes";
      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getPaymentTypes", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      System.out.println("DataLoader.getPaymentTypes()");

      try {
         String sqlStmt = "SELECT DISTINCT payment_type_id,payment_type_name FROM rmf_payment_type";
         conn = this.getConnection(SBM_DB_JNDI);
         System.out.println("conn:::::::::" + conn);
         pstmt = conn.prepareStatement(sqlStmt);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString("payment_type_id"), rs.getString("payment_type_name"));
         }
      } catch (SQLException var10) {
         var10.printStackTrace();
         logger.error(StaticFuncs.buildLogStatement("DataLoader", "getPaymentTypes", 0, "SQL Exception Occured."), var10);
      } finally {
         this.closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("DataLoader", "getPaymentTypes", 2));
      return hMap;
   }
}
