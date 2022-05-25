package com.agent.portal;

import com.savvion.sbm.bizsolo.util.SBMConf;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;

public class DataRetrieval {
   DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
   ArrayList<Date> holidayList = new ArrayList();
   HSSFCellStyle cellStyleHeader = null;
   HSSFFont fontHeader = null;

   public LinkedHashMap<String, String> getClaim() {
      Connection connection = null;
      Statement stmt = null;
      ResultSet rset = null;
      LinkedHashMap<String, String> claimsMap = new LinkedHashMap();
      GetResource obj = new GetResource();

      try {
         connection = obj.getDBConnection();
         stmt = connection.createStatement();
         rset = stmt.executeQuery("{call GETAGENTCLAIM}");

         while(rset.next()) {
            claimsMap.put(rset.getString(1), rset.getString(2));
         }

         LinkedHashMap var8 = claimsMap;
         return var8;
      } catch (Exception var11) {
         throw new RuntimeException("Error while getting Claims : " + var11.getMessage());
      } finally {
         obj.releaseResource(connection, stmt, rset);
      }
   }

   public LinkedHashMap<String, String> getUW() {
      Connection connection = null;
      Statement stmt = null;
      ResultSet rset = null;
      LinkedHashMap<String, String> uwMap = new LinkedHashMap();
      GetResource obj = new GetResource();

      try {
         connection = obj.getDBConnection();
         stmt = connection.createStatement();
         rset = stmt.executeQuery("{call GETAGENTUW}");

         while(rset.next()) {
            uwMap.put(rset.getString(1), rset.getString(2));
         }

         LinkedHashMap var8 = uwMap;
         return var8;
      } catch (Exception var11) {
         throw new RuntimeException("Error while getting UW : " + var11.getMessage());
      } finally {
         obj.releaseResource(connection, stmt, rset);
      }
   }

   public LinkedHashMap<String, String> getOps() {
      Connection connection = null;
      Statement stmt = null;
      ResultSet rset = null;
      LinkedHashMap<String, String> opsMap = new LinkedHashMap();
      GetResource obj = new GetResource();

      try {
         connection = obj.getDBConnection();
         stmt = connection.createStatement();
         rset = stmt.executeQuery("{call GETAGENTOPS}");

         while(rset.next()) {
            opsMap.put(rset.getString(1), rset.getString(2));
         }

         LinkedHashMap var8 = opsMap;
         return var8;
      } catch (Exception var11) {
         throw new RuntimeException("Error while getting Ops : " + var11.getMessage());
      } finally {
         obj.releaseResource(connection, stmt, rset);
      }
   }

   public LinkedHashMap<String, String> getIT() {
      Connection connection = null;
      Statement stmt = null;
      ResultSet rset = null;
      LinkedHashMap<String, String> itMap = new LinkedHashMap();
      GetResource obj = new GetResource();

      try {
         connection = obj.getDBConnection();
         stmt = connection.createStatement();
         rset = stmt.executeQuery("{call GETAGENTIT}");

         while(rset.next()) {
            itMap.put(rset.getString(1), rset.getString(2));
         }

         LinkedHashMap var8 = itMap;
         return var8;
      } catch (Exception var11) {
         throw new RuntimeException("Error while getting IT : " + var11.getMessage());
      } finally {
         obj.releaseResource(connection, stmt, rset);
      }
   }

   public LinkedHashMap<String, String> getAccounts() {
      Connection connection = null;
      Statement stmt = null;
      ResultSet rset = null;
      LinkedHashMap<String, String> accountsMap = new LinkedHashMap();
      GetResource obj = new GetResource();

      try {
         connection = obj.getDBConnection();
         stmt = connection.createStatement();
         rset = stmt.executeQuery("{call getAgentAccounts}");

         while(rset.next()) {
            accountsMap.put(rset.getString(1), rset.getString(2));
         }

         LinkedHashMap var8 = accountsMap;
         return var8;
      } catch (Exception var11) {
         throw new RuntimeException("Error while getting Accounts : " + var11.getMessage());
      } finally {
         obj.releaseResource(connection, stmt, rset);
      }
   }

   public LinkedHashMap<String, String> getStrategy() {
      Connection connection = null;
      Statement stmt = null;
      ResultSet rset = null;
      LinkedHashMap<String, String> strategyMap = new LinkedHashMap();
      GetResource obj = new GetResource();

      try {
         connection = obj.getDBConnection();
         stmt = connection.createStatement();
         rset = stmt.executeQuery("{call getAgentStrategy}");

         while(rset.next()) {
            strategyMap.put(rset.getString(1), rset.getString(2));
         }

         LinkedHashMap var8 = strategyMap;
         return var8;
      } catch (Exception var11) {
         throw new RuntimeException("Error while getting Strategy : " + var11.getMessage());
      } finally {
         obj.releaseResource(connection, stmt, rset);
      }
   }

   public ArrayList<String> getClaimSpocs(String id) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<String> spocList = null;
      if (id != null && id.trim().length() > 0) {
         GetResource obj = new GetResource();
         spocList = new ArrayList();

         try {
            connection = obj.getDBConnection();
            String qry = "select spocs from tbl_agentclaim_spocs where claim_id in (select to_number(regexp_substr(?,'[^,]+', 1, level)) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, id.trim());
            pstmt.setString(2, id.trim());
            rset = pstmt.executeQuery();

            while(rset.next()) {
               spocList.add(rset.getString(1));
            }
         } catch (Exception var11) {
            throw new RuntimeException("Error while getting Claim Spoc : " + var11.getCause() + " : " + var11.getMessage() + " : " + var11.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, rset);
         }
      }

      return spocList;
   }

   public ArrayList<String> getUWSpocs(String id) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<String> spocList = null;
      if (id != null && id.trim().length() > 0) {
         GetResource obj = new GetResource();
         spocList = new ArrayList();

         try {
            connection = obj.getDBConnection();
            String qry = "select uw_spoc from tbl_agentuw_spoc where uw_id in (select to_number(regexp_substr(?,'[^,]+', 1, level)) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, id.trim());
            pstmt.setString(2, id.trim());
            rset = pstmt.executeQuery();

            while(rset.next()) {
               spocList.add(rset.getString(1));
            }
         } catch (Exception var11) {
            throw new RuntimeException("Error while getting UW Spoc : " + var11.getCause() + " : " + var11.getMessage() + " : " + var11.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, rset);
         }
      }

      return spocList;
   }

   public ArrayList<String> getOpsSpocs(String id) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<String> spocList = null;
      if (id != null && id.trim().length() > 0) {
         GetResource obj = new GetResource();
         spocList = new ArrayList();

         try {
            connection = obj.getDBConnection();
            String qry = "select ops_spoc from tbl_agentops_spocs where ops_id in (select to_number(regexp_substr(?,'[^,]+', 1, level)) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, id.trim());
            pstmt.setString(2, id.trim());
            rset = pstmt.executeQuery();

            while(rset.next()) {
               spocList.add(rset.getString(1));
            }
         } catch (Exception var11) {
            throw new RuntimeException("Error while getting Ops Spoc : " + var11.getCause() + " : " + var11.getMessage() + " : " + var11.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, rset);
         }
      }

      return spocList;
   }

   public ArrayList<String> getITSpocs(String id) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<String> spocList = null;
      if (id != null && id.trim().length() > 0) {
         GetResource obj = new GetResource();
         spocList = new ArrayList();

         try {
            connection = obj.getDBConnection();
            String qry = "select it_spoc from tbl_agentit_spoc where it_id in (select to_number(regexp_substr(?,'[^,]+', 1, level)) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, id.trim());
            pstmt.setString(2, id.trim());
            rset = pstmt.executeQuery();

            while(rset.next()) {
               spocList.add(rset.getString(1));
            }
         } catch (Exception var11) {
            throw new RuntimeException("Error while getting IT Spoc : " + var11.getCause() + " : " + var11.getMessage() + " : " + var11.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, rset);
         }
      }

      return spocList;
   }

   public ArrayList<String> getAccountsSpocs(String id) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<String> spocList = null;
      if (id != null && id.trim().length() > 0) {
         GetResource obj = new GetResource();
         spocList = new ArrayList();

         try {
            connection = obj.getDBConnection();
            String qry = "select account_spoc from tbl_agentaccounts_spoc where account_id in (select to_number(regexp_substr(?,'[^,]+', 1, level)) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, id.trim());
            pstmt.setString(2, id.trim());
            rset = pstmt.executeQuery();

            while(rset.next()) {
               spocList.add(rset.getString(1));
            }
         } catch (Exception var11) {
            throw new RuntimeException("Error while getting Account Spoc : " + var11.getCause() + " : " + var11.getMessage() + " : " + var11.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, rset);
         }
      }

      return spocList;
   }

   public ArrayList<String> getStrategySpocs(String id) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<String> spocList = null;
      if (id != null && id.trim().length() > 0) {
         GetResource obj = new GetResource();
         spocList = new ArrayList();

         try {
            connection = obj.getDBConnection();
            String qry = "select strategy_spoc from tbl_agentstrategy_spoc where strategy_id in (select to_number(regexp_substr(?,'[^,]+', 1, level)) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, id.trim());
            pstmt.setString(2, id.trim());
            rset = pstmt.executeQuery();

            while(rset.next()) {
               spocList.add(rset.getString(1));
            }
         } catch (Exception var11) {
            throw new RuntimeException("Error while getting Strategy Spoc : " + var11.getCause() + " : " + var11.getMessage() + " : " + var11.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, rset);
         }
      }

      return spocList;
   }

   public String getBmRemarks(String piID, String spocId) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      String bmRemark = null;
      if (spocId != null && spocId.trim().length() > 0) {
         GetResource obj = new GetResource();

         try {
            connection = obj.getDBConnection();
            String qry = " select remarks||'*123#'||reassignby from agentbmgrid where process_instance_id = ?  and spoc like ? and spoc_remark='Pending' ";
            pstmt = connection.prepareStatement(qry);
            pstmt.setLong(1, Long.parseLong(piID.trim()));
            pstmt.setString(2, "%" + spocId.trim() + "%");

            for(rset = pstmt.executeQuery(); rset.next(); bmRemark = rset.getString(1)) {
            }
         } catch (Exception var12) {
            throw new RuntimeException("Error while getting BM Remarks : " + var12.getCause() + " : " + var12.getMessage() + " : " + var12.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, rset);
         }
      }

      return bmRemark;
   }

   public ArrayList<HashMap<String, String>> getSpocResolution(String piID) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
      ArrayList<HashMap<String, String>> spocResolutionList = null;
      GetResource obj = new GetResource();
      spocResolutionList = new ArrayList();

      try {
         connection = obj.getDBConnection();
         String qry = " select CATEGORY, SUB_CATEGORY, SPOC, REMARKS,SPOC_REMARK,REASSIGNBY,SPOC_SUBMIT_DATE,BM_SUBMIT_DATE,STATUS   from agentbmgrid where process_instance_id = ? order by BM_SUBMIT_DATE";
         pstmt = connection.prepareStatement(qry);
         pstmt.setLong(1, Long.parseLong(piID));
         rset = pstmt.executeQuery();

         while(rset.next()) {
            HashMap<String, String> map = new HashMap();
            map.put("CATEGORY", rset.getString(1));
            map.put("SUB_CATEGORY", rset.getString(2));
            map.put("SPOC", rset.getString(3));
            map.put("REMARKS", rset.getString(4));
            map.put("SPOC_REMARK", rset.getString(5));
            map.put("REASSIGNBY", rset.getString(6));
            String spocDate = "";
            if (rset.getDate(7) != null) {
               spocDate = df.format(rset.getDate(7));
            } else {
               spocDate = "N/A";
            }

            map.put("SPOC_SUBMIT_DATE", spocDate);
            String bmDate = df.format(rset.getDate(8));
            map.put("BM_SUBMIT_DATE", bmDate);
            map.put("STATUS", rset.getString(9));
            spocResolutionList.add(map);
         }
      } catch (Exception var15) {
         throw new RuntimeException("Error while getting SPOC Resolution : " + var15.getCause() + " : " + var15.getMessage() + " : " + var15.getStackTrace());
      } finally {
         obj.releaseResource(connection, pstmt, rset);
      }

      return spocResolutionList;
   }

   public ArrayList<String> getAssignedSpoc(String piID) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList<String> spocList = null;
      GetResource obj = new GetResource();

      try {
         connection = obj.getDBConnection();
         spocList = new ArrayList();
         String query = " select spoc from agentbmgrid where spoc_remark = 'Pending' and process_instance_id = ? ";
         pstmt = connection.prepareStatement(query);
         pstmt.setLong(1, Long.parseLong(piID.trim()));
         rset = pstmt.executeQuery();

         while(rset.next()) {
            spocList.add(rset.getString(1));
         }
      } catch (Exception var11) {
         throw new RuntimeException("Error while getting Strategy Spoc : " + var11.getCause() + " : " + var11.getMessage() + " : " + var11.getStackTrace());
      } finally {
         obj.releaseResource(connection, pstmt, rset);
      }

      return spocList;
   }

   public ArrayList<HashMap<String, String>> getRemarks(String piID) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
      ArrayList<HashMap<String, String>> remarkList = null;
      GetResource obj = new GetResource();
      remarkList = new ArrayList();

      try {
         connection = obj.getDBConnection();
         String query = "Select * from agent_immediatemanagerremark where process_instance_id = ? order by bm_submit_date";
         pstmt = connection.prepareStatement(query);
         pstmt.setLong(1, Long.parseLong(piID));
         rs = pstmt.executeQuery();

         while(rs.next()) {
            HashMap<String, String> map = new HashMap();
            map.put("PROCESS_INSTANCE_ID", rs.getString(1));
            map.put("BM_REMARKS", rs.getString(2));
            map.put("IMMEDIATEMANAGER_REMARKS", rs.getString(3));
            String immediateManagerDate;
            if (rs.getDate(4) != null) {
               immediateManagerDate = df.format(rs.getDate(4));
            } else {
               immediateManagerDate = "N/A";
            }

            map.put("IM_SUBMIT_DATE", immediateManagerDate);
            map.put("BM_SUBMIT_DATE", df.format(rs.getDate(5)));
            remarkList.add(map);
         }
      } catch (Exception var14) {
         throw new RuntimeException("Error while getting BM Remarks : " + var14.getCause() + " : " + var14.getMessage() + " : " + var14.getStackTrace());
      } finally {
         obj.releaseResource(connection, pstmt, rs);
      }

      return remarkList;
   }

   public HashMap getReportData(String startDate, String endDate, String status) throws Exception {
      Connection connection = null;
      CallableStatement cstmt = null;
      HashMap hm = new HashMap();
      ArrayList<ArrayList<Object>> callData = new ArrayList();
      ArrayList<ArrayList<Object>> immediateMgrData = new ArrayList();
      ArrayList<ArrayList<Object>> spocData = new ArrayList();
      DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
      DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      ResultSet rset = null;
      DBUtility obj = new DBUtility();

      try {
         String query = "{call AGENT_OPEN_CLOSE_DTL(?,?,?,?,?,?)}";
         connection = obj.getDBConnection();
         cstmt = connection.prepareCall(query);
         cstmt.setString(1, startDate.trim());
         cstmt.setString(2, endDate.trim());
         cstmt.setString(3, status.trim());
         cstmt.registerOutParameter(4, -10);
         cstmt.registerOutParameter(5, -10);
         cstmt.registerOutParameter(6, -10);
         cstmt.executeQuery();
         rset = (ResultSet)cstmt.getObject(4);
         ArrayList spocSolution;
         String date;
         String spocRmk;
         String date1;
         Date dd1;
         if (rset != null) {
            for(; rset.next(); callData.add(spocSolution)) {
               spocSolution = new ArrayList();
               spocSolution.add(rset.getString(1));
               spocSolution.add(rset.getString(2));
               spocSolution.add(rset.getString(3));
               spocSolution.add(rset.getString(4));
               spocSolution.add(rset.getString(5));
               spocSolution.add(rset.getString(6));
               spocSolution.add(df.format(rset.getDate(7)));
               date = rset.getString(8);
               if (date != null) {
                  spocSolution.add(df.format(rset.getDate(8)));
               } else {
                  spocSolution.add("-");
               }

               spocSolution.add(rset.getString(9));
               spocSolution.add(rset.getString(10));
               if (status.equals("CLOSE")) {
                  spocRmk = df1.format(rset.getDate(7));
                  date1 = df1.format(rset.getDate(8));
                  Date dd1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(spocRmk);
                  dd1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date1);
                  String TAT = Integer.toString(this.getWorkingDaysBetweenTwoDates(dd1, dd1));
                  spocSolution.add(TAT);
               } else {
                  spocSolution.add("-");
               }
            }
         }

         if (rset != null) {
            try {
               rset.close();
            } catch (SQLException var32) {
               throw new RuntimeException("Resultset not Closed..." + var32.getMessage());
            }
         }

         rset = (ResultSet)cstmt.getObject(5);
         String d1;
         Date dd1;
         if (rset != null) {
            for(; rset.next(); immediateMgrData.add(spocSolution)) {
               spocSolution = new ArrayList();
               spocSolution.add(rset.getString(1));
               spocSolution.add(rset.getString(2));
               date = rset.getString(3);
               if (date != null) {
                  spocSolution.add(df.format(rset.getDate(3)));
               } else {
                  spocSolution.add("-");
               }

               spocSolution.add(rset.getString(4));
               spocRmk = rset.getString(5);
               if (spocRmk != null) {
                  spocSolution.add(df.format(rset.getDate(5)));
               } else {
                  spocSolution.add("-");
               }

               if (status.equals("CLOSE")) {
                  date1 = df1.format(rset.getDate(3));
                  d1 = df1.format(rset.getDate(5));
                  dd1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date1);
                  dd1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(d1);
                  String TAT = Integer.toString(this.getWorkingDaysBetweenTwoDates(dd1, dd1));
                  spocSolution.add(TAT);
               } else {
                  spocSolution.add("-");
               }
            }
         }

         if (rset != null) {
            try {
               rset.close();
            } catch (SQLException var31) {
               throw new RuntimeException("Resultset not Closed..." + var31.getMessage());
            }
         }

         rset = (ResultSet)cstmt.getObject(6);
         if (rset != null) {
            for(; rset.next(); spocData.add(spocSolution)) {
               spocSolution = new ArrayList();
               spocSolution.add(rset.getString(1));
               spocSolution.add(rset.getString(2));
               spocSolution.add(rset.getString(3));
               spocSolution.add(rset.getString(4));
               date = rset.getString(5);
               if (date != null) {
                  spocSolution.add(df.format(rset.getDate(5)));
               } else {
                  spocSolution.add("-");
               }

               spocSolution.add(rset.getString(6));
               spocRmk = rset.getString(7).trim();
               if (spocRmk.equals("Pending")) {
                  spocSolution.add("-");
               } else {
                  spocSolution.add(rset.getString(7));
               }

               date1 = rset.getString(8);
               if (date1 != null) {
                  spocSolution.add(df.format(rset.getDate(8)));
               } else {
                  spocSolution.add("-");
               }

               spocSolution.add(rset.getString(9));
               if (status.equals("CLOSE")) {
                  d1 = df1.format(rset.getDate(5));
                  String d2 = df1.format(rset.getDate(8));
                  dd1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(d1);
                  Date dd2 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(d2);
                  String TAT = Integer.toString(this.getWorkingDaysBetweenTwoDates(dd1, dd2));
                  spocSolution.add(TAT);
               } else {
                  spocSolution.add("-");
               }
            }
         }

         if (rset != null) {
            try {
               rset.close();
            } catch (SQLException var30) {
               throw new RuntimeException("Resultset not Closed..." + var30.getMessage());
            }
         }

         hm.put("callData", callData);
         hm.put("immediateMgrData", immediateMgrData);
         hm.put("spocData", spocData);
      } catch (SQLException var33) {
         throw new RuntimeException("Error while getting Report Data --- : " + var33.getCause() + " : " + var33.getMessage() + " : " + var33.getStackTrace());
      } finally {
         obj.releaseResource(connection, cstmt, rset);
      }

      System.out.println(hm);
      return hm;
   }

   public int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
      Calendar startCal = Calendar.getInstance();
      startCal.setTime(startDate);
      startCal.set(11, 0);
      startCal.set(12, 0);
      startCal.set(13, 0);
      startCal.set(14, 1);
      Calendar endCal = Calendar.getInstance();
      endCal.setTime(endDate);
      endCal.set(11, 0);
      endCal.set(12, 0);
      endCal.set(13, 0);
      endCal.set(14, 1);
      int workDays = -1;
      if (this.df.format(startDate).equals(this.df.format(endDate))) {
         return 0;
      } else {
         if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
         }

         try {
            do {
               do {
                  if (!this.checkHoliday(startCal.getTime()) && !this.checkSecondSaturday(startCal.getTime()) && startCal.get(7) != 1) {
                     ++workDays;
                  }

                  startCal.add(5, 1);
               } while(startCal.getTime().before(endCal.getTime()));
            } while(startCal.getTime().equals(endCal.getTime()));
         } catch (Exception var7) {
            System.out.println(var7.getMessage());
         }

         if (workDays == -1) {
            workDays = 0;
         }

         return workDays;
      }
   }

   public boolean checkSecondSaturday(Date date) {
      boolean isSecondSat = false;
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      int dayOfWeek = c.get(7);
      if (dayOfWeek == 7) {
         int weekOfMonth = c.get(4);
         if (weekOfMonth == 2 || weekOfMonth == 4) {
            isSecondSat = true;
         }
      }

      return isSecondSat;
   }

   public boolean checkHoliday(Date date) {
      boolean isHoliday = false;

      for(int i = 0; i < this.holidayList.size(); ++i) {
         if (this.df.format(date).equals(this.df.format((Date)this.holidayList.get(i)))) {
            isHoliday = true;
            break;
         }
      }

      return isHoliday;
   }

   public void getHolidays() {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      Calendar cal = Calendar.getInstance();
      DBUtility obj = new DBUtility();

      try {
         connection = obj.getDBConnection();
         pstmt = connection.prepareCall("SELECT HOLIDAYDATE FROM RGICL_HOLIDAYS WHERE HOLIDAYYEAR = (SELECT TO_NUMBER(EXTRACT(YEAR FROM SYSDATE)) FROM DUAL)");
         rset = pstmt.executeQuery();

         while(rset.next()) {
            Date holiday = new Date(rset.getDate(1).getTime());
            Calendar holidayCal = Calendar.getInstance();
            holidayCal.setTime(holiday);
            cal.set(1, holidayCal.get(1));
            cal.set(2, holidayCal.get(2));
            cal.set(5, holidayCal.get(5));
            this.holidayList.add(cal.getTime());
         }
      } catch (Exception var16) {
         System.out.println("Error in getting holiday list : " + var16.getMessage());
      } finally {
         try {
            if (rset != null) {
               rset.close();
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var15) {
            var15.printStackTrace();
         }

      }

   }

   private String exportToExcelCDComplete(Map reports) {
      String reportName = null;
      FileOutputStream fos = null;
      HSSFWorkbook workbook = new HSSFWorkbook();
      String time = (new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")).format(new Date()).toString();

      try {
         HSSFSheet sheet = workbook.createSheet("Ticket Detail");
         HSSFSheet sheet1 = workbook.createSheet("Immediate Manager Resolution");
         HSSFSheet sheet2 = workbook.createSheet("SPOC Resolution");
         String[] HeaderList = new String[]{"REFERENCE NUMBER", "AGENT CODE", "STATUS", "BRANCH CODE", "BRANCH NAME", "BM", "START DATE", "END DATE", "CLOSUE REMARK", "PENDING WITH", "TAT"};
         String[] HeaderList1 = new String[]{"REFERENCE NUMBER", "BM REMARK", "BM SUBMIT DATE", "IMMEDIATE MANAGER REMARK", "IM SUBMIT DATE", "TAT"};
         String[] HeaderList2 = new String[]{"REFERENCE NUMBER", "CATEGORY", "SUB CATEGORY", "BM REMARKS", "BM SUBMIT DATE", "SPOC", "SPOC REMARK", "SPOC SUBMIT DATE", "STATUS", "TAT"};
         ArrayList<ArrayList<Object>> callData = (ArrayList)reports.get("callData");
         ArrayList<ArrayList<Object>> immediateMgrData = (ArrayList)reports.get("immediateMgrData");
         ArrayList<ArrayList<Object>> spocData = (ArrayList)reports.get("spocData");
         HSSFRow headerRow1;
         int i;
         HSSFCell cell;
         HSSFRow rowA;
         if (callData != null && !callData.isEmpty() && callData.size() > 0) {
            this.setHeaderStyle(workbook);
            headerRow1 = sheet.createRow(1);

            for(i = 0; i < HeaderList.length; ++i) {
               cell = headerRow1.createCell((short)i);
               cell.setCellValue(HeaderList[i]);
               cell.setCellStyle(this.cellStyleHeader);
            }

            for(i = 0; i < callData.size(); ++i) {
               rowA = sheet.createRow(i + 3);
               rowA.createCell((short)0).setCellValue((String)((ArrayList)callData.get(i)).get(0));
               rowA.createCell((short)1).setCellValue((String)((ArrayList)callData.get(i)).get(1));
               rowA.createCell((short)2).setCellValue((String)((ArrayList)callData.get(i)).get(2));
               rowA.createCell((short)3).setCellValue((String)((ArrayList)callData.get(i)).get(3));
               rowA.createCell((short)4).setCellValue((String)((ArrayList)callData.get(i)).get(4));
               rowA.createCell((short)5).setCellValue((String)((ArrayList)callData.get(i)).get(5));
               rowA.createCell((short)6).setCellValue((String)((ArrayList)callData.get(i)).get(6));
               rowA.createCell((short)7).setCellValue((String)((ArrayList)callData.get(i)).get(7));
               rowA.createCell((short)8).setCellValue((String)((ArrayList)callData.get(i)).get(8));
               rowA.createCell((short)9).setCellValue((String)((ArrayList)callData.get(i)).get(9));
               rowA.createCell((short)10).setCellValue((String)((ArrayList)callData.get(i)).get(10));
            }

            for(i = 0; i < HeaderList.length; ++i) {
               sheet.setColumnWidth((short)i, (short)5000);
            }
         }

         if (immediateMgrData != null && !immediateMgrData.isEmpty() && immediateMgrData.size() > 0) {
            this.setHeaderStyle(workbook);
            headerRow1 = sheet1.createRow(1);

            for(i = 0; i < HeaderList1.length; ++i) {
               cell = headerRow1.createCell((short)i);
               cell.setCellValue(HeaderList1[i]);
               cell.setCellStyle(this.cellStyleHeader);
            }

            for(i = 0; i < immediateMgrData.size(); ++i) {
               rowA = sheet1.createRow(i + 3);
               rowA.createCell((short)0).setCellValue((String)((ArrayList)immediateMgrData.get(i)).get(0));
               rowA.createCell((short)1).setCellValue((String)((ArrayList)immediateMgrData.get(i)).get(1));
               rowA.createCell((short)2).setCellValue((String)((ArrayList)immediateMgrData.get(i)).get(2));
               rowA.createCell((short)3).setCellValue((String)((ArrayList)immediateMgrData.get(i)).get(3));
               rowA.createCell((short)4).setCellValue((String)((ArrayList)immediateMgrData.get(i)).get(4));
               rowA.createCell((short)5).setCellValue((String)((ArrayList)immediateMgrData.get(i)).get(5));
            }

            for(i = 0; i < HeaderList1.length; ++i) {
               sheet1.setColumnWidth((short)i, (short)5000);
            }
         }

         if (spocData != null && !spocData.isEmpty() && spocData.size() > 0) {
            this.setHeaderStyle(workbook);
            headerRow1 = sheet2.createRow(1);

            for(i = 0; i < HeaderList2.length; ++i) {
               cell = headerRow1.createCell((short)i);
               cell.setCellValue(HeaderList2[i]);
               cell.setCellStyle(this.cellStyleHeader);
            }

            for(i = 0; i < spocData.size(); ++i) {
               rowA = sheet2.createRow(i + 3);
               rowA.createCell((short)0).setCellValue((String)((ArrayList)spocData.get(i)).get(0));
               rowA.createCell((short)1).setCellValue((String)((ArrayList)spocData.get(i)).get(1));
               rowA.createCell((short)2).setCellValue((String)((ArrayList)spocData.get(i)).get(2));
               rowA.createCell((short)3).setCellValue((String)((ArrayList)spocData.get(i)).get(3));
               rowA.createCell((short)4).setCellValue((String)((ArrayList)spocData.get(i)).get(4));
               rowA.createCell((short)5).setCellValue((String)((ArrayList)spocData.get(i)).get(5));
               rowA.createCell((short)6).setCellValue((String)((ArrayList)spocData.get(i)).get(6));
               rowA.createCell((short)7).setCellValue((String)((ArrayList)spocData.get(i)).get(7));
               rowA.createCell((short)8).setCellValue((String)((ArrayList)spocData.get(i)).get(8));
               rowA.createCell((short)9).setCellValue((String)((ArrayList)spocData.get(i)).get(9));
            }

            for(i = 0; i < HeaderList2.length; ++i) {
               sheet2.setColumnWidth((short)i, (short)5000);
            }
         }

         fos = new FileOutputStream(new File(SBMConf.SBM_WEBAPPDIR + "/bpmportal/AgentRetentionReport/reports/TrackHistory_" + time + ".xls"));
         workbook.write(fos);
         reportName = "/sbm/bpmportal/AgentRetentionReport/reports/TrackHistory_" + time + ".xls";
      } catch (Exception var26) {
         System.out.println("Error gererating report : " + var26.getMessage());
      } finally {
         if (fos != null) {
            try {
               fos.flush();
               fos.close();
            } catch (Exception var25) {
               var25.printStackTrace();
            }
         }

      }

      return reportName;
   }

   public void setHeaderStyle(HSSFWorkbook workbook) {
      this.cellStyleHeader = workbook.createCellStyle();
      this.fontHeader = workbook.createFont();
      this.cellStyleHeader.setFillForegroundColor((short)24);
      this.cellStyleHeader.setFillPattern((short)1);
      this.cellStyleHeader.setBorderLeft((short)1);
      this.cellStyleHeader.setBorderRight((short)1);
      this.cellStyleHeader.setBorderTop((short)1);
      this.cellStyleHeader.setBorderBottom((short)1);
      this.cellStyleHeader.setBottomBorderColor(IndexedColors.WHITE.getIndex());
      this.cellStyleHeader.setTopBorderColor(IndexedColors.WHITE.getIndex());
      this.cellStyleHeader.setLeftBorderColor(IndexedColors.WHITE.getIndex());
      this.cellStyleHeader.setRightBorderColor(IndexedColors.WHITE.getIndex());
      this.fontHeader.setColor((short)9);
      this.fontHeader.setBoldweight((short)700);
      this.cellStyleHeader.setAlignment((short)6);
      this.cellStyleHeader.setVerticalAlignment((short)1);
      this.cellStyleHeader.setFont(this.fontHeader);
   }
}
