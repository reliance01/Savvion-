package org.rgicl.motor.util;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.BSProcessInstance;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.filter.BSProcessInstanceFilter;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSDataSlotData;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSDataSlotRS;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSProcessInstanceData;
import com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSProcessInstanceRS;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.webservices.RGICL_MotorUW_AppSoapBindingStub;
import java.net.InetAddress;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.axis.client.Service;

public class UpdateIrpasSavvionImpl {
   private static Connection connection = null;
   private static PreparedStatement pstmt = null;
   private static ResultSet rs = null;
   private static CallableStatement proc_stmt = null;
   private static QueryService qs = null;
   private static Session blsession = null;
   private static BLServer blserver = null;
   private static final String ptName = "RGICL_MotorUW_App";
   private static String[] dsArrray = new String[]{"proposalNumber", "branchCode", "ApprovalAuthority", "ProductCode", "HealthProcess", "AgentCode", "BASCode", "CreatedBy", "UWStatus", "CUStatus", "LOB"};

   public static void main(String[] args) {
      String proposalNo = null;
      String branchCode = null;
      String ApprovalAuthority = null;
      String ProductCode = null;
      String HealthProcess = null;
      String AgentCode = null;
      String BASCode = null;
      String CreatedBy = null;
      String UWStatus = null;
      String CUStatus = null;
      String irpasStatus = null;
      String policyno = null;
      String lob = null;
      com.savvion.webservices.WorkItemObject obj = null;
      RGICL_MotorUW_AppSoapBindingStub stub = null;
      com.savvion.webservices.ResponseObject resObj = null;
      String url = "http://10.65.5.80/sbm/services/RGICL_MotorUW_App?wsdl";

      try {
         UpdateIrpasSavvionImpl.blserver = BLClientUtil.getBizLogicServer();
         UpdateIrpasSavvionImpl.blsession = UpdateIrpasSavvionImpl.blserver.connect("rgicl", "rgicl");
         ProcessTemplate pt = UpdateIrpasSavvionImpl.blserver.getProcessTemplate(UpdateIrpasSavvionImpl.blsession, "RGICL_MotorUW_App");
         if (qs != null) {
            QueryService.clean();
         }

         qs = new QueryService(UpdateIrpasSavvionImpl.blsession);
         BSProcessInstanceFilter bsf = new BSProcessInstanceFilter("Test");
         bsf.setFetchSize(0);
         Date da = new Date();
         Calendar cal = Calendar.getInstance();
         cal.setTime(da);
         cal.add(2, -1);
         da = cal.getTime();
         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         System.out.println(dateFormat.format(da));
         bsf.setCondition("BSPI.PROCESS_TEMPLATE_ID=" + pt.getID() + " AND BSPI.START_TIME > to_date('" + dateFormat.format(da) + "','yyyy-MM-dd HH24:mi')");
         BSProcessInstance bsp = qs.getBSProcessInstance();
         BSProcessInstanceRS bsprs = bsp.getCompletedInstances(bsf);
         BSProcessInstanceRS bsprs1 = bsp.getSuspendedInstances(bsf);
         List lst;
         int i;
         BSProcessInstanceData bspd;
         long piid;
         BSDataSlotRS bsrs;
         BSDataSlotData bsdata;
         String[] arr;
         BLServer blserver;
         Session blsession;
         long pid;
         ProcessInstance pi;
         if (bsprs != null) {
            lst = bsprs.getList();

            for(i = 0; i < lst.size(); ++i) {
               bspd = (BSProcessInstanceData)lst.get(i);
               piid = bspd.getProcessInstanceID();
               bsrs = bspd.getDataSlotList(dsArrray);
               bsdata = bsrs.get("proposalNumber");
               proposalNo = (String)bsdata.getValue();
               bsdata = bsrs.get("branchCode");
               branchCode = (String)bsdata.getValue();
               bsdata = bsrs.get("ApprovalAuthority");
               ApprovalAuthority = (String)bsdata.getValue();
               bsdata = bsrs.get("ProductCode");
               ProductCode = (String)bsdata.getValue();
               bsdata = bsrs.get("HealthProcess");
               HealthProcess = (String)bsdata.getValue();
               bsdata = bsrs.get("AgentCode");
               AgentCode = (String)bsdata.getValue();
               bsdata = bsrs.get("BASCode");
               BASCode = (String)bsdata.getValue();
               bsdata = bsrs.get("CreatedBy");
               CreatedBy = (String)bsdata.getValue();
               bsdata = bsrs.get("UWStatus");
               UWStatus = (String)bsdata.getValue();
               bsdata = bsrs.get("CUStatus");
               CUStatus = (String)bsdata.getValue();
               bsdata = bsrs.get("LOB");
               lob = (String)bsdata.getValue();
               arr = getIrpasStatus(proposalNo);
               irpasStatus = arr[0];
               policyno = arr[1];
               if (policyno == null) {
                  if (lob != null && lob.equalsIgnoreCase("MOTOR")) {
                     if (UWStatus != null && UWStatus.equalsIgnoreCase("Forward") && CUStatus != null && CUStatus.equalsIgnoreCase("Approved")) {
                        if (irpasStatus != null && !irpasStatus.equalsIgnoreCase(CUStatus) && !checkProposalExists(proposalNo)) {
                           System.out.println("Product Code " + ProductCode + " proposalNo " + proposalNo + " Savvion Status " + CUStatus + "  IRPAS Status " + irpasStatus);
                           System.out.println("trying to create process instance");
                           obj = new com.savvion.webservices.WorkItemObject();
                           obj.setApprovalAuthority(ApprovalAuthority);
                           obj.setProposalNumber(proposalNo);
                           obj.setBranchCode(branchCode);
                           obj.setProcess(HealthProcess);
                           obj.setAgentCode(AgentCode);
                           obj.setBASCode(BASCode);
                           obj.setProductCode(ProductCode);
                           obj.setUserId(CreatedBy);
                           obj.setBlazeRemarks("From Job");
                           stub = new RGICL_MotorUW_AppSoapBindingStub(new URL(url), new Service());
                           resObj = stub.initiateMotorIRPASFlow(obj);
                           if (resObj.getResponseCode() == "5000") {
                              System.out.println("Sucess");
                           }

                           System.out.println(resObj.getMessage());
                        }
                     } else if (UWStatus != null && UWStatus.equalsIgnoreCase("Approved")) {
                        if (irpasStatus != null && !irpasStatus.equalsIgnoreCase(UWStatus) && !checkProposalExists(proposalNo)) {
                           System.out.println("Product Code " + ProductCode + " proposalNo " + proposalNo + " Savvion Status " + CUStatus + "  IRPAS Status " + irpasStatus);
                           System.out.println("trying to create process instance");
                           obj = new com.savvion.webservices.WorkItemObject();
                           obj.setApprovalAuthority(ApprovalAuthority);
                           obj.setProposalNumber(proposalNo);
                           obj.setBranchCode(branchCode);
                           obj.setProcess(HealthProcess);
                           obj.setAgentCode(AgentCode);
                           obj.setBASCode(BASCode);
                           obj.setProductCode(ProductCode);
                           obj.setUserId(CreatedBy);
                           obj.setBlazeRemarks("From Job");
                           stub = new RGICL_MotorUW_AppSoapBindingStub(new URL(url), new Service());
                           resObj = stub.initiateMotorIRPASFlow(obj);
                           if (resObj.getResponseCode() == "5000") {
                              System.out.println("Sucess");
                           }

                           System.out.println(resObj.getMessage());
                        }
                     } else if ((UWStatus != null && UWStatus.equalsIgnoreCase("Rejected") || CUStatus != null && CUStatus.equalsIgnoreCase("Rejected")) && irpasStatus != null && irpasStatus.equalsIgnoreCase("Pending") && checkProposalExists(proposalNo)) {
                        System.out.println("Product Code " + ProductCode + " proposalNo " + proposalNo + " Savvion Status " + CUStatus + "  IRPAS Status " + irpasStatus);
                        System.out.println("trying to resume process instance");
                        blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect("rgicl", "rgicl");
                        pid = getProcessInstanceId(proposalNo);
                        if (pid != 0L && blserver.isProcessInstanceExist(blsession, pid)) {
                           pi = blserver.getProcessInstance(blsession, pid);
                           if (pi.isSuspended()) {
                              pi.resume();
                              pi.save();
                           }
                        }
                     }
                  } else if (irpasStatus != null && !irpasStatus.equalsIgnoreCase(CUStatus)) {
                     if (!checkProposalExists(proposalNo)) {
                        System.out.println("Product Code " + ProductCode + " proposalNo " + proposalNo + " Savvion Status " + CUStatus + "  IRPAS Status " + irpasStatus);
                        System.out.println("trying to create process instance");
                        obj = new com.savvion.webservices.WorkItemObject();
                        obj.setApprovalAuthority(ApprovalAuthority);
                        obj.setProposalNumber(proposalNo);
                        obj.setBranchCode(branchCode);
                        obj.setProcess(HealthProcess);
                        obj.setAgentCode(AgentCode);
                        obj.setBASCode(BASCode);
                        obj.setProductCode(ProductCode);
                        obj.setUserId(CreatedBy);
                        obj.setBlazeRemarks("From Job");
                        stub = new RGICL_MotorUW_AppSoapBindingStub(new URL(url), new Service());
                        resObj = stub.initiateMotorIRPASFlow(obj);
                        if (resObj.getResponseCode() == "5000") {
                           System.out.println("Sucess");
                        }

                        System.out.println(resObj.getMessage());
                     } else if (CUStatus != null && CUStatus.equalsIgnoreCase("Rejected") && irpasStatus != null && irpasStatus.equalsIgnoreCase("Pending") && checkProposalExists(proposalNo)) {
                        System.out.println("Product Code " + ProductCode + " proposalNo " + proposalNo + " Savvion Status " + CUStatus + "  IRPAS Status " + irpasStatus);
                        System.out.println("trying to resume process instance");
                        blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect("rgicl", "rgicl");
                        pid = getProcessInstanceId(proposalNo);
                        if (pid != 0L && blserver.isProcessInstanceExist(blsession, pid)) {
                           pi = blserver.getProcessInstance(blsession, pid);
                           if (pi.isSuspended()) {
                              pi.resume();
                              pi.save();
                           }
                        }
                     }
                  }
               }
            }
         }

         if (bsprs1 != null) {
            lst = bsprs1.getList();

            for(i = 0; i < lst.size(); ++i) {
               bspd = (BSProcessInstanceData)lst.get(i);
               piid = bspd.getProcessInstanceID();
               bsrs = bspd.getDataSlotList(dsArrray);
               bsdata = bsrs.get("proposalNumber");
               proposalNo = (String)bsdata.getValue();
               bsdata = bsrs.get("branchCode");
               branchCode = (String)bsdata.getValue();
               bsdata = bsrs.get("ApprovalAuthority");
               ApprovalAuthority = (String)bsdata.getValue();
               bsdata = bsrs.get("ProductCode");
               ProductCode = (String)bsdata.getValue();
               bsdata = bsrs.get("HealthProcess");
               HealthProcess = (String)bsdata.getValue();
               bsdata = bsrs.get("AgentCode");
               AgentCode = (String)bsdata.getValue();
               bsdata = bsrs.get("BASCode");
               BASCode = (String)bsdata.getValue();
               bsdata = bsrs.get("CreatedBy");
               CreatedBy = (String)bsdata.getValue();
               bsdata = bsrs.get("UWStatus");
               UWStatus = (String)bsdata.getValue();
               bsdata = bsrs.get("CUStatus");
               CUStatus = (String)bsdata.getValue();
               bsdata = bsrs.get("LOB");
               lob = (String)bsdata.getValue();
               arr = getIrpasStatus(proposalNo);
               irpasStatus = arr[0];
               policyno = arr[1];
               if (policyno == null) {
                  if (lob != null && lob.equalsIgnoreCase("MOTOR")) {
                     if ((UWStatus != null && UWStatus.equalsIgnoreCase("Rejected") || CUStatus != null && CUStatus.equalsIgnoreCase("Rejected")) && irpasStatus != null && irpasStatus.equalsIgnoreCase("Pending") && checkProposalExists(proposalNo)) {
                        System.out.println("Product Code " + ProductCode + " proposalNo " + proposalNo + " Savvion Status " + CUStatus + "  IRPAS Status " + irpasStatus);
                        System.out.println("trying to resume process instance");
                        blserver = BLClientUtil.getBizLogicServer();
                        blsession = blserver.connect("rgicl", "rgicl");
                        pid = getProcessInstanceId(proposalNo);
                        if (pid != 0L && blserver.isProcessInstanceExist(blsession, pid)) {
                           pi = blserver.getProcessInstance(blsession, pid);
                           if (pi.isSuspended()) {
                              pi.resume();
                              pi.save();
                           }
                        }
                     }
                  } else if (irpasStatus != null && !irpasStatus.equalsIgnoreCase(CUStatus) && CUStatus != null && CUStatus.equalsIgnoreCase("Rejected") && irpasStatus != null && irpasStatus.equalsIgnoreCase("Pending") && checkProposalExists(proposalNo)) {
                     System.out.println("Product Code " + ProductCode + " proposalNo " + proposalNo + " Savvion Status " + CUStatus + "  IRPAS Status " + irpasStatus);
                     System.out.println("trying to resume process instance");
                     blserver = BLClientUtil.getBizLogicServer();
                     blsession = blserver.connect("rgicl", "rgicl");
                     pid = getProcessInstanceId(proposalNo);
                     if (pid != 0L && blserver.isProcessInstanceExist(blsession, pid)) {
                        pi = blserver.getProcessInstance(blsession, pid);
                        if (pi.isSuspended()) {
                           pi.resume();
                           pi.save();
                        }
                     }
                  }
               }
            }
         }

         if (qs != null) {
            UpdateIrpasSavvionImpl.blserver.disConnect(UpdateIrpasSavvionImpl.blsession);
            QueryService.clean();
         }
      } catch (Exception var39) {
         System.out.println(var39.getMessage());
      }

   }

   private static Connection getConnectionToSQLDB() throws Exception {
      String dbip = "";
      String dbuser = "";
      String dbpass = "";

      try {
         String ip = InetAddress.getLocalHost().getHostAddress();
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         if (ip.contains("10.62.182.42")) {
            dbip = "rgiuirpasdb.reliancecapital.com";
            dbuser = "esbuser";
            dbpass = "pass@1234";
         } else {
            dbip = "RGIiRPasDbAG.reliancegeneral.co.in";
            dbuser = "esbuser";
            dbpass = "pass@1234";
         }

         connection = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=IRPAS", dbuser, dbpass);
      } catch (Exception var4) {
         throw new RuntimeException("Error in getting a DB connection  for db " + dbip + " \n" + var4.getMessage(), var4);
      }

      return connection;
   }

   private static void closeResources() {
      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var3) {
         }
      }

      if (proc_stmt != null) {
         try {
            proc_stmt.close();
         } catch (SQLException var2) {
         }
      }

      if (connection != null) {
         try {
            connection.close();
         } catch (SQLException var1) {
         }
      }

   }

   private static String[] getIrpasStatus(String proposalNumber) {
      String QuoteStatus = null;
      String policyno = null;

      try {
         if (proposalNumber != null && proposalNumber.length() > 0) {
            connection = getConnectionToSQLDB();
            proc_stmt = connection.prepareCall("{ call USP_GetSavvionIRPAS_Status(?) }");
            proc_stmt.setString(1, proposalNumber);
            rs = proc_stmt.executeQuery();
            if (rs != null) {
               while(rs.next()) {
                  QuoteStatus = rs.getString("QuoteStatus");
                  policyno = rs.getString("policyno");
               }
            }

            closeResources();
         }
      } catch (Exception var12) {
         System.out.println("Error while getting QuoteStatus " + var12.toString());
      } finally {
         try {
            closeResources();
         } catch (Exception var11) {
         }

      }

      return new String[]{QuoteStatus, policyno};
   }

   public static boolean checkProposalExists(String proposalNumber) {
      boolean bln = false;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, "RGICL_MotorUW_App").getID();
         blserver.disConnect(blsession);
         String URL = "jdbc:oracle:thin:@RGIORCLDB:1863:EBMS12C";
         conn = DriverManager.getConnection(URL, "savvion2015", "Rel1ance");
         pstmt = conn.prepareStatement("Select count(*) as COUNT from BIZLOGIC_DS_" + String.valueOf(ptid) + " where proposalNumber=?");
         pstmt.setString(1, proposalNumber);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            int count = rs.getInt("COUNT");
            if (count > 0) {
               bln = true;
               System.out.println("ProposalExists in Savvion" + proposalNumber);
            }
         }

         rs.close();
         pstmt.close();
         conn.close();
      } catch (Exception var27) {
         System.out.println("Error in fecthing Proposal Details" + var27.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var26) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var25) {
         }

         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException var24) {
         }

      }

      return bln;
   }

   public static long getProcessInstanceId(String proposalNumber) {
      boolean bln = false;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      long piid = 0L;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, "RGICL_MotorUW_App").getID();
         blserver.disConnect(blsession);
         String URL = "jdbc:oracle:thin:@RGIORCLDB:1863:EBMS12C";
         conn = DriverManager.getConnection(URL, "savvion2015", "Rel1ance");
         pstmt = conn.prepareStatement("Select PROCESS_INSTANCE_ID from BIZLOGIC_DS_" + String.valueOf(ptid) + " where proposalNumber=?");
         pstmt.setString(1, proposalNumber);

         for(rs = pstmt.executeQuery(); rs.next(); piid = rs.getLong("PROCESS_INSTANCE_ID")) {
         }

         rs.close();
         pstmt.close();
         conn.close();
      } catch (Exception var28) {
         System.out.println("Error in fecthing Proposal Details" + var28.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var27) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var26) {
         }

         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException var25) {
         }

      }

      return piid;
   }
}
