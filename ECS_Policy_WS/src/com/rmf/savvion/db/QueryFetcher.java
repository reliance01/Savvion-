package com.rmf.savvion.db;

import com.rmf.common.util.PropertyReader;
import com.rmf.savvion.util.StaticFuncs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;

public class QueryFetcher {
   private static final String CLASS_NAME = "QueryFetcher";
   private static final String SBM_DB_JNDI = PropertyReader.getDataSourceName();
   private static final Logger logger = PropertyReader.getDlLogger();
   private static final Object PIID = "PIID";
   private static final String USER_STATUS_AVAILABLE = "AVAILABLE";
   private static final String USER_STATUS_NOT_AVAILABLE = "NOT_AVAILABLE";
   private String agentName;

   public static Connection getConnection() {
      try {
         System.out.println("Inside DAO Class");
         Connection con = null;
         con = ConnectionManager.getDBConnection("CRM_DS");
         System.out.println("Connection Estaiblished");
         return con;
      } catch (SQLException var1) {
         throw new RuntimeException(var1);
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public Map getAgentNames() {
      String METHOD_NAME = "getAgentNames";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentNames", 1));
      Map<String, String> hMap = new LinkedHashMap();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "SELECT top 10 AGENTCODE,AGENTNAME FROM RMF_INTERNAL_SAVVION_AGENTDETAILS order by AGENTNAME";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentNames", 0, "GET Agent MAP SQL ", sqlStmt));
         conn = getConnection();
         System.out.println("Got connection in queryFetcher");
         pstmt = conn.prepareStatement(sqlStmt);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);

         while(rs.next()) {
            hMap.put(rs.getString("AGENTCODE"), rs.getString("AGENTNAME"));
            System.out.println("AGENTCODE:" + rs.getString("AGENTCODE"));
         }
      } catch (SQLException var10) {
         System.out.println("Exception is:" + var10);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentNames", 0, "SQL Exception Occured."), var10);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentNames", 2));
      return hMap;
   }

   public static Vector getSchemeNames() {
      String METHOD_NAME = "getSchemeNames";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getSchemeNames", 1));
      Vector schemeList = new Vector();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "select SchemeName from RMF_INTERNAL_SAVVION_PRODUCT order by SchemeName";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getSchemeNames", 0, "GET Scheme MAP SQL ", sqlStmt));
         conn = getConnection();
         pstmt = conn.prepareStatement(sqlStmt);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);

         while(rs.next()) {
            schemeList.add(rs.getString("SchemeName"));
         }
      } catch (SQLException var9) {
         System.out.println("Exception is:" + var9);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getSchemeNames", 0, "SQL Exception Occured."), var9);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getSchemeNames", 2));
      return schemeList;
   }

   public static Vector getAssetClasses() {
      String METHOD_NAME = "getAssetClasses";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAssetClasses", 1));
      Vector AssetClassList = new Vector();
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;

      try {
         String sqlStmt = "select distinct AssetClass from RMF_INTERNAL_SAVVION_PRODUCT";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getAssetClasses", 0, "GET Asset MAP SQL ", sqlStmt));
         conn = getConnection();
         pstmt = conn.prepareStatement(sqlStmt);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);

         while(rs.next()) {
            AssetClassList.add(rs.getString("AssetClass"));
         }

         System.out.println("AssetClasses: ---" + AssetClassList.toString());
      } catch (SQLException var9) {
         System.out.println("Exception is:" + var9);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAssetClasses", 0, "SQL Exception Occured."), var9);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAssetClasses", 2));
      return AssetClassList;
   }

   public Map getAgent(String agentCode) {
      String METHOD_NAME = "getAgentNames";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentNames", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      LinkedHashMap hMap = new LinkedHashMap();

      try {
         String sqlStmt = "Select AgentName from RMF_INTERNAL_SAVVION_AGENTDETAILS where AgentCode = ?";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentNames", 0, "GET Agent MAP SQL ", sqlStmt));
         conn = getConnection();
         System.out.println("Got connection inside getAgent in queryFetcher");
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, agentCode);
         rs = pstmt.executeQuery();
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);

         while(rs.next()) {
            this.agentName = rs.getString("AGENTNAME");
            hMap.put("1", this.agentName);
            System.out.println("AGENTNAME:" + rs.getString("agentName"));
         }
      } catch (SQLException var11) {
         System.out.println("Exception is:" + var11);
         var11.printStackTrace();
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentNames", 0, "SQL Exception Occured."), var11);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentNames", 2));
      return hMap;
   }

   public Map getAgentFullName(String userName) {
      String METHOD_NAME = "getAgentFullName";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentFullName", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      Map hMap = new HashMap();
      String fullName = null;
      String status = null;
      String user = "";

      try {
         String sqlStmt = "Select AgentName from RMF_INTERNAL_SAVVION_AGENTDETAILS where AgentCode =?";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentFullName", 0, "GET Agent MAP SQL ", sqlStmt));
         conn = getConnection();
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, userName);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);
         System.out.println("Here user is:" + userName);

         while(rs.next()) {
            System.out.println("1");
            System.out.println("ResultSet.Next");
            user = rs.getString("AGENTNAME");
            System.out.println("AGENTNAME:" + rs.getString("AGENTNAME"));
            if (user != "") {
               status = "AVAILABLE";
               fullName = user;
            } else {
               status = "NOT_AVAILABLE";
               fullName = "";
            }

            hMap.put("STATUS", status);
            hMap.put("FULL_NAME", fullName);
            System.out.println("HMAP: " + hMap);
         }
      } catch (SQLException var14) {
         System.out.println("Exception is:" + var14);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentFullName", 0, "SQL Exception Occured."), var14);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentFullName", 0, "Full Name found as ", hMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getAgentFullName", 2));
      return hMap;
   }

   public Map getFolioByAgentCode(String agentCode, String FolioNumber) {
      String METHOD_NAME = "getFolioByAgentCode";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getFolioByAgentCode", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      Map hMap = new HashMap();
      String fullName = null;
      String status = null;
      String folioNo = "";

      try {
         String sqlStmt = "select FolioNo from RMF_INTERNAL_SAVVION_FOLIOAGENTMAPPING where AgentCode = ? and FolioNo = ?";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getFolioByAgentCode", 0, "GET Agent MAP SQL ", sqlStmt));
         conn = getConnection();
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, agentCode);
         pstmt.setString(2, FolioNumber);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);
         System.out.println("Here user is:" + agentCode);
         System.out.println("Here FolioNumber is:" + FolioNumber);

         while(rs.next()) {
            System.out.println("1");
            System.out.println("ResultSet.Next");
            folioNo = rs.getString("FolioNo");
            System.out.println("AGENTNAME:" + rs.getString("FolioNo"));
            if (folioNo != "") {
               status = "AVAILABLE";
               folioNo = folioNo;
            } else {
               status = "NOT_AVAILABLE";
               folioNo = "";
            }

            hMap.put("STATUS", status);
            hMap.put("FolioNo", folioNo);
            System.out.println("HMAP: " + hMap);
         }
      } catch (SQLException var15) {
         System.out.println("Exception is:" + var15);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getFolioByAgentCode", 0, "SQL Exception Occured."), var15);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getFolioByAgentCode", 0, "folioNo  found as ", hMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getFolioByAgentCode", 2));
      return hMap;
   }

   public static Map getLocationName(String user) {
      String METHOD_NAME = "getLocationName";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getLocationName", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      String location = null;
      String userName = "";
      HashMap hMap = new HashMap();

      try {
         String sqlStmt = "Select Location from RMF_INTERNAL_SAVVION_USERDETAILS where SAPID =?";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getLocationName", 0, "GET Agent MAP SQL ", sqlStmt));
         conn = getConnection();
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, user);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);
         System.out.println("Here user is:" + user);

         for(; rs.next(); hMap.put("location", location)) {
            location = rs.getString("Location");
            System.out.println("Location:" + rs.getString("Location"));
            if (location != "") {
               location = location;
            } else {
               location = "";
            }
         }

         System.out.println("location ---" + location);
      } catch (SQLException var12) {
         System.out.println("Exception is:" + var12);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getLocationName", 0, "SQL Exception Occured."), var12);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      return hMap;
   }

   public Map getZonalSAPID(String user) {
      String METHOD_NAME = "getZonalSAPID";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getZonalSAPID", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      Map hMap = new HashMap();
      String SAPID = null;
      String status = null;
      String zonalSAPID = "";

      try {
         String sqlStmt = "Select risu.SAPID, risu.username, risu.location, risl.zonalSAPID from RMF_INTERNAL_SAVVION_USERDETAILS risu join RMF_INTERNAL_SAVVION_LOCATIONMAPPING risl on risu.location = risl.location where SAPID =?";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getZonalSAPID", 0, "GET ZonalSAPID SQL ", sqlStmt));
         conn = getConnection();
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, user);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);
         System.out.println("Here user1 is:" + user);

         while(rs.next()) {
            zonalSAPID = rs.getString("ZONALSAPID");
            System.out.println("zonalSAPID:" + rs.getString("zonalSAPID"));
            if (zonalSAPID != "") {
               status = "AVAILABLE";
               zonalSAPID = zonalSAPID;
            } else {
               status = "NOT_AVAILABLE";
               zonalSAPID = "";
            }

            hMap.put("STATUS", status);
            hMap.put("ZONALSAPID", zonalSAPID);
            System.out.println("HMAP: " + hMap);
         }
      } catch (SQLException var14) {
         System.out.println("Exception is:" + var14);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getZonalSAPID", 0, "SQL Exception Occured."), var14);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getZonalSAPID", 0, "ZonalHead found as ", hMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getZonalSAPID", 2));
      return hMap;
   }

   public Map getSegmentName(String user) {
      String METHOD_NAME = "getSegmentName";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getSegmentName", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      Map hMap = new HashMap();
      String SAPID = null;
      String status = null;
      String segment = "";

      try {
         String sqlStmt = "select segment from RMF_INTERNAL_SAVVION_AGENTDETAILS where  agentcode=?";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getSegmentName", 0, "GET SegmentName SQL ", sqlStmt));
         conn = getConnection();
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, user);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);
         System.out.println("Here user1 is:" + user);

         while(rs.next()) {
            segment = rs.getString("segment");
            System.out.println("segment:" + rs.getString("segment"));
            hMap.put("SEGMENT", segment);
            System.out.println("HMAP: " + hMap);
         }
      } catch (SQLException var14) {
         System.out.println("Exception is:" + var14);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getSegmentName", 0, "SQL Exception Occured."), var14);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getSegmentName", 0, "Segment found as ", hMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getSegmentName", 2));
      return hMap;
   }

   public Map getRegionalSAPID(String user) {
      String METHOD_NAME = "getRegionalSAPID";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getRegionalSAPID", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      Map hMap = new HashMap();
      String SAPID = null;
      String status = null;
      String regionalSAPID = "";

      try {
         String sqlStmt = "Select risu.SAPID, risu.username, risu.location, risl.regionalSAPID from RMF_INTERNAL_SAVVION_USERDETAILS risu join RMF_INTERNAL_SAVVION_LOCATIONMAPPING risl on risu.location = risl.location where SAPID =?";
         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getRegionalSAPID", 0, "GET ZonalSAPID SQL ", sqlStmt));
         conn = getConnection();
         pstmt = conn.prepareStatement(sqlStmt);
         pstmt.setString(1, user);
         System.out.println("Pstmt: " + pstmt);
         rs = pstmt.executeQuery();
         System.out.println("ResultSet:" + rs);
         System.out.println("Here user1 is:" + user);

         while(rs.next()) {
            regionalSAPID = rs.getString("REGIONALSAPID");
            System.out.println("regionalSAPID:" + rs.getString("regionalSAPID"));
            if (regionalSAPID != "") {
               status = "AVAILABLE";
               regionalSAPID = regionalSAPID;
            } else {
               status = "NOT_AVAILABLE";
               regionalSAPID = "";
            }

            hMap.put("STATUS", status);
            hMap.put("REGIONALSAPID", regionalSAPID);
            System.out.println("HMAP: " + hMap);
         }
      } catch (SQLException var14) {
         System.out.println("Exception is:" + var14);
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getRegionalSAPID", 0, "SQL Exception Occured."), var14);
      } finally {
         closeResources(rs, pstmt, conn);
      }

      logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "getRegionalSAPID", 0, "REGIONALHead found as ", hMap.toString()));
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getRegionalSAPID", 2));
      return hMap;
   }

   public Map getUserDetails(Map inputMap) throws Exception {
      String METHOD_NAME = "getDesignation";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getDesignation", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      Map outputMap = new HashMap();
      String SAPID = null;
      String status = null;
      String var9 = "";

      try {
         String agentCode = (String)inputMap.get("setAgentCode");
         String location = (String)inputMap.get("setLocation");
         String loginUserId = (String)inputMap.get("setUser");
         String UserDesignation = "";
         String reportingMangId = "";
         String zonalSAPId = "";
         String regionalSAPId = "";
         String branchSAPId = "";
         String segmentHeadId = "";
         conn = getConnection();
         String segmentHeadQuery;
         String getIdQuery;
         if ("mumbai - co".equalsIgnoreCase(location)) {
            segmentHeadQuery = "SELECT ReportingSAPId,Segment FROM RMF_INTERNAL_SAVVION_USERDETAILS WHERE SAPID=?";
            pstmt = conn.prepareStatement(segmentHeadQuery);
            pstmt.setString(1, loginUserId);
            rs = pstmt.executeQuery();
            getIdQuery = "";
            if (rs.next()) {
               reportingMangId = rs.getString(1);
               getIdQuery = rs.getString(2);
            }

            System.out.println("reportingId ---" + reportingMangId + "---segment ---" + getIdQuery);
            rs.close();
            rs = null;
            pstmt.close();
            pstmt = null;
            if (reportingMangId.length() > 0) {
               String roleQuery = "SELECT RoleName FROM RMF_INTERNAL_SAVVION_USERROLEMASTER WHERE EmployeeCode = ? AND UserSegment = ?";
               pstmt = conn.prepareStatement(roleQuery);
               pstmt.setString(1, reportingMangId);
               pstmt.setString(2, getIdQuery);
               rs = pstmt.executeQuery();
               String roleName = "";
               if (rs.next()) {
                  roleName = rs.getString(1);
               }

               System.out.println("roleName---" + roleName);
               if ("Segment Head".equalsIgnoreCase(roleName)) {
                  UserDesignation = "ReportingManager";
               } else {
                  UserDesignation = "CorporateUser";
               }

               System.out.println("UserDesignation ---" + UserDesignation);
               rs.close();
               rs = null;
               pstmt.close();
               pstmt = null;
            }
         } else {
            segmentHeadQuery = "SELECT RoleName FROM RMF_INTERNAL_SAVVION_USERROLEMASTER WHERE EmployeeCode = ?";
            pstmt = conn.prepareStatement(segmentHeadQuery);
            pstmt.setString(1, loginUserId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
               UserDesignation = rs.getString(1);
            }

            System.out.println("UserDesignation ---" + UserDesignation);
            rs.close();
            rs = null;
            pstmt.close();
            pstmt = null;
            getIdQuery = "";
            if ("Regional Head".equalsIgnoreCase(UserDesignation)) {
               getIdQuery = "SELECT ZONALSAPID,REGIONALSAPID,BRANCHSAPID FROM RMF_INTERNAL_SAVVION_LOCATIONMAPPING WHERE REGIONALSAPID=? AND LOCATION=?";
            } else if ("Branch Manager".equalsIgnoreCase(UserDesignation)) {
               getIdQuery = "SELECT ZONALSAPID,REGIONALSAPID,BRANCHSAPID FROM RMF_INTERNAL_SAVVION_LOCATIONMAPPING WHERE BRANCHSAPID=? AND LOCATION=?";
            } else {
               getIdQuery = "SELECT risl.ZONALSAPID, risl.REGIONALSAPID, risl.BRANCHSAPID FROM RMF_INTERNAL_SAVVION_USERDETAILS risu join RMF_INTERNAL_SAVVION_LOCATIONMAPPING risl ON risu.location = risl.location where SAPID =? AND risl.LOCATION =?";
            }

            pstmt = conn.prepareStatement(getIdQuery);
            pstmt.setString(1, loginUserId);
            pstmt.setString(2, location);
            rs = pstmt.executeQuery();
            if (rs.next()) {
               zonalSAPId = rs.getString(1);
               regionalSAPId = rs.getString(2);
               branchSAPId = rs.getString(3);
            }

            System.out.println("zonalSAPId ---" + zonalSAPId);
            System.out.println("regionalSAPId ---" + regionalSAPId);
            System.out.println("branchSAPId ---" + branchSAPId);
            rs.close();
            rs = null;
            pstmt.close();
            pstmt = null;
         }

         segmentHeadQuery = "select EmployeeCode from RMF_INTERNAL_SAVVION_USERROLEMASTER where usersegment=  (select segment from RMF_INTERNAL_SAVVION_AGENTDETAILS where  agentcode=?)  and rolename='segment head'";
         pstmt = conn.prepareStatement(segmentHeadQuery);
         pstmt.setString(1, agentCode);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            segmentHeadId = rs.getString(1);
         }

         System.out.println("segmentHeadId ---" + segmentHeadId);
         outputMap.put("getUserDesignation", UserDesignation);
         outputMap.put("getReportingManagerSAPID", reportingMangId);
         outputMap.put("getZonalHeadSAPID", zonalSAPId);
         outputMap.put("getRegionalHeadSAPID", regionalSAPId);
         outputMap.put("getBranchHeadSAPID", branchSAPId);
         outputMap.put("getSegmentHeadSAPID", segmentHeadId);
         System.out.println("Return Map --" + outputMap);
      } catch (SQLException var26) {
         System.out.println("Exception is:" + var26);
         throw var26;
      } finally {
         closeResources(rs, pstmt, conn);
      }

      return outputMap;
   }

   public static void main(String[] args) {
      QueryFetcher qf = new QueryFetcher();
      qf.getAgentNames();
   }

   private static void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
      String METHOD_NAME = "closeResources";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "closeResources", 1));

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

         logger.debug(StaticFuncs.buildLogStatement("QueryFetcher", "closeResources", 0, "DB Resources closed."));
      } catch (SQLException var5) {
         logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "closeResources", 0, "SQL Exception Occured."), var5);
      }

      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "closeResources", 2));
   }

   public static String fetchRegionalManager(String currentUser, String location) {
      System.out.println("QueryFetcher.fetchRegionalManager() - Start");
      String regionalManager = null;

      try {
         Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement("select regionalsapid from RMF_INTERNAL_SAVVION_LOCATIONMAPPING where branchsapid = ? and location = ?");
         pstmt.setString(1, currentUser);
         pstmt.setString(2, location);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
            regionalManager = rs.getString(1);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      System.out.println("Fetched Regional Manager = " + regionalManager);
      System.out.println("QueryFetcher.fetchRegionalManager() - End");
      return regionalManager;
   }

   public static String fetchZonalHead(String currentUser, String location) {
      System.out.println("QueryFetcher.fetchZonalHead() - Start");
      String zonalHead = null;

      try {
         Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement("select zonalsapid from RMF_INTERNAL_SAVVION_LOCATIONMAPPING where regionalsapid = ? and location = ?");
         pstmt.setString(1, currentUser);
         pstmt.setString(2, location);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
            zonalHead = rs.getString(1);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      System.out.println("Fetched Zonal Head = " + zonalHead);
      System.out.println("QueryFetcher.fetchZonalHead() - End");
      return zonalHead;
   }

   public static String fetchSegmentGroup(String agentCode) {
      System.out.println("QueryFetcher.fetchSegmentGroup() - Start");
      String segmentGroup = null;

      try {
         Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement("select segment from RMF_INTERNAL_SAVVION_AGENTDETAILS where agentcode = ?");
         pstmt.setString(1, agentCode);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
            segmentGroup = "SegmentGroup" + rs.getString(1);
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      System.out.println("Fetched Segment Group = " + segmentGroup);
      System.out.println("QueryFetcher.fetchSegmentGroup() - End");
      return segmentGroup;
   }

   public static String fetchSegmentHead(String agentCode) {
      System.out.println("QueryFetcher.fetchSegmentHead() - Start");
      String segmentHead = null;

      try {
         Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement("select EmployeeCode from RMF_INTERNAL_SAVVION_USERROLEMASTER where usersegment = ( select segment from RMF_INTERNAL_SAVVION_AGENTDETAILS where agentcode = ? ) and rolename= 'segment head'");
         pstmt.setString(1, agentCode);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
            segmentHead = rs.getString(1);
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      System.out.println("Fetched Segment Head = " + segmentHead);
      System.out.println("QueryFetcher.fetchSegmentHead() - End");
      return segmentHead;
   }

   public Map getApproverDetails(Map inputMap) throws SQLException {
      String METHOD_NAME = "getDesignation";
      logger.error(StaticFuncs.buildLogStatement("QueryFetcher", "getDesignation", 1));
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      Connection conn = null;
      HashMap outputMap = new HashMap();

      try {
         String agentCode = (String)inputMap.get("setAgentCode");
         String loginUserId = (String)inputMap.get("setUser");
         String segmentHeadQuery = "select EmployeeCode from RMF_INTERNAL_SAVVION_USERROLEMASTER where usersegment=  (select segment from RMF_INTERNAL_SAVVION_AGENTDETAILS where  agentcode=?)  and rolename='segment head'";
         String segmentHeadId = null;
         pstmt = ((Connection)conn).prepareStatement(segmentHeadQuery);
         pstmt.setString(1, agentCode);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            segmentHeadId = rs.getString(1);
         }

         System.out.println("segmentHeadId ---" + segmentHeadId);
         System.out.println("Return Map --" + outputMap);
      } catch (SQLException var14) {
         System.out.println("Exception is:" + var14);
         throw var14;
      } finally {
         closeResources(rs, pstmt, (Connection)conn);
      }

      return outputMap;
   }

   public Map getSchemeDetails() {
      System.out.println("QueryFetcher.getSchemeDetails() - Start");
      HashMap hMap = new HashMap();

      try {
         Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement("select schemeCode,schemeName from RMF_INTERNAL_SAVVION_PRODUCT");
         ResultSet rs = pstmt.executeQuery();

         while(rs.next()) {
            hMap.put(rs.getString(1), rs.getString(2));
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      System.out.println("Fetched SchemeDetails = " + hMap);
      System.out.println("QueryFetcher.SchemeDetails() - End");
      return hMap;
   }
}
