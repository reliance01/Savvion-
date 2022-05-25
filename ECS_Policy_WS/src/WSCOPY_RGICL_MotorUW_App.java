import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItem;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemFilter;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemRS;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstanceList;
import com.savvion.sbm.bizlogic.server.svo.QSWorkItemData;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstanceList;
import com.savvion.sbm.bizlogic.server.svo.XML;
import com.savvion.sbm.bizlogic.util.BLConstants;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.util.PService;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.util.NLog;
import com.tdiinc.BizLogic.Server.PAKClientWorkitem;
import com.tdiinc.BizLogic.Server.PAKException;
import com.tdiinc.userManager.JDBCGroup;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Map.Entry;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.axis.AxisFault;
import org.rgicl.motor.util.RequestObject;
import org.rgicl.motor.util.ResponseObject;
import org.rgicl.motor.util.WorkItemObject;

public class WSCOPY_RGICL_MotorUW_App {
   private static BizLogicManager pak = null;
   private String ptName = "RGICL_MotorUW_App";
   private static Byte[] bytearray = new Byte[0];
   final String SUCCESS = "5000";
   final String USER_ID_NULL = "5001";
   final String USER_NOT_AUTHORIZED = "5002";
   final String USER_NAME_NULL = "5003";
   final String USER_ROLE_NULL = "5004";
   final String CLM_NUM_NULL = "5005";
   final String CLM_LOB_NULL = "5006";
   final String CLM_FLAG_NULL = "5007";
   final String CLM_TYPE_NULL = "5008";
   final String CLM_REOPEN_NULL = "5009";
   final String TPA_FLAG_NULL = "5010";
   final String STP_FLAG_NULL = "5011";
   final String PI_NAME_NULL = "5012";
   final String PI_ID_NULL = "5013";
   final String DS_NAME_NULL = "5014";
   final String DS_VALUE_NULL = "5015";
   final String WINAME_NULL = "5016";
   final String REASSGN_BSM_NULL = "5017";
   final String APPROVER_USER_ID_NULL = "5018";
   final String RESERVE_APPR_FLAG_NULL = "5019";
   final String PYMT_APPR_FLAG_NULL = "5020";
   final String INPUT_VALUE_IS_NULL = "5021";
   final String USER_ALREADY_MAPPED = "5022";
   final String USER_NOT_MAPPED = "5023";
   final String USER_ID_INVALID = "5030-Null or Empty value passed for userId code";
   final String USER_ROLE_INVALID = "5031";
   final String PI_ID_INVALID = "5032";
   final String PI_NAME_INVALID = "5033";
   final String DS_NAME_INVALID = "5034";
   final String DS_VALUE_INVALID = "5035";
   final String APPROVER_USER_ID_INVALID = "5036";
   final String CLM_FLAG_INVALID = "5037";
   final String INVALID_INPUT = "5038";
   final String COMMON_INBOX_EMPTY = "5041";
   final String ASSIGNED_INBOX_EMPTY = "5042";
   final String FAILURE_EXCEPTION = "5050-Failure Exception";
   final String INVALID_WINAME = "5059";
   final String WINAME_NOT_FOUND = "5060";
   final String NEXT_WORKITEM_NOT_FOUND = "5043";
   final String PENDING_CLAIMS_EMPTY = "5044";
   final String DATABASE_ERROR = "5045";
   final String ECSGroup = "ECS";
   final String INVALID_REQUEST = "5047";
   final String REQUEST_OBJECT_IS_NULL = "5070";
   final String PROPOSAL_ALREADY_EXISTS = "6001-ProposalNumber Already Active in Savvion. Cannot create a duplicate Proposal";
   final String BRANCH_NULL_EMPTY = "6002-Null or Empty value passed for branch code";
   final String APPROVAL_AUTH_NULL = "6003-Null or Empty value passed for ApprovalAuthority code";
   final String PRODUCT_CODE_NULL = "6004-Null or Empty value passed for Product code";
   final String PRODUCT_CODE_FORMAT = "6005-Product code should be in the format ProductCode-ProductName format";
   final String MOTOR_BRANCHMAPPING_ERROR = "6006-Issue with Motor branch and UW mapping";
   final String AUDIT_FAILURE = "6007-AuditFailure";
   final String CANNOT_FORWARD = "6008-Corporate Cannot have Forward and Complete, Please use Approve/Reject option";
   private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
   private DataSource ds = null;
   private Connection connection;
   private PreparedStatement pstmt;
   private ResultSet rs;
   private String sqlString = "SELECT BRANCH_TYPE FROM MOTORUW_BRANCH_REGION_MAP WHERE BRANCH_ID = ?";
   private CallableStatement proc_stmt = null;

   public WSCOPY_RGICL_MotorUW_App() {
      NLog.ws.debug("RGICL_MotorUW_App service invoked");
   }

   private Connection getConnectionToSQLDB() throws Exception {
      String dbip = "";
      String dbuser = "";
      String dbpass = "";

      try {
         String ip = InetAddress.getLocalHost().getHostAddress();
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         if (ip.contains("10.65.15.")) {
            dbip = "rgiudb01.reliancegeneral.co.in";
            dbuser = "sa";
            dbpass = "sa123";
         } else {
            dbip = "RGIRMSCDDB";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         }

         this.connection = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=SavvionDB", dbuser, dbpass);
      } catch (Exception var5) {
         throw new RuntimeException("Error in getting a DB connection  for db " + dbip + " \n" + var5.getMessage(), var5);
      }

      return this.connection;
   }

   private void closeResources() {
      if (this.rs != null) {
         try {
            this.rs.close();
         } catch (SQLException var4) {
         }
      }

      if (this.proc_stmt != null) {
         try {
            this.proc_stmt.close();
         } catch (SQLException var3) {
         }
      }

      if (this.connection != null) {
         try {
            this.connection.close();
         } catch (SQLException var2) {
         }
      }

   }

   public String getUnderWriterDetails(String BranchCode) {
      String UWGroup = "";

      try {
         if (BranchCode != null) {
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            this.connection = this.ds.getConnection();
            this.pstmt = this.connection.prepareStatement(this.sqlString);
            if (BranchCode.contains("-")) {
               String[] arr = BranchCode.split("-");
               if (arr != null) {
                  BranchCode = arr[0];
               }
            }

            this.pstmt.setString(1, BranchCode);
            this.rs = this.pstmt.executeQuery();

            String UWGroup1;
            for(UWGroup1 = null; this.rs.next(); UWGroup1 = this.rs.getString("BRANCH_TYPE")) {
            }

            if (UWGroup1 != null && UWGroup1.length() > 0) {
               new UserManager();
               JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
               JDBCGroup g = (JDBCGroup)r.getGroup(UWGroup1);
               String[] users = g.getAllUserMemberNames();
               if (users != null && users.length > 0) {
                  for(int i = 0; i < users.length; ++i) {
                     JDBCUser u = (JDBCUser)r.getUser(users[i]);
                     UWGroup = UWGroup + u.userName + "-" + u.getAttribute("FIRSTNAME") + " " + u.getAttribute("LASTNAME") + "\n";
                  }
               }
            }
         }
      } catch (Exception var23) {
         throw new RuntimeException();
      } finally {
         try {
            if (this.rs != null) {
               this.rs.close();
            }
         } catch (SQLException var22) {
         }

         try {
            if (this.pstmt != null) {
               this.pstmt.close();
            }
         } catch (SQLException var21) {
         }

         try {
            if (this.connection != null) {
               this.connection.close();
            }
         } catch (SQLException var20) {
         }

      }

      return UWGroup;
   }

   private boolean checkBSMLimit(String ApprovalAuth) {
      boolean bln = false;
      String[] val = new String[]{"01", "02", "03", "04", "05", "06", "20", "21", "22"};
      if (ApprovalAuth != null && ApprovalAuth.length() > 0) {
         for(int i = 0; i < val.length; ++i) {
            if (ApprovalAuth.equalsIgnoreCase(val[i]) || ApprovalAuth == val[i]) {
               bln = true;
               return bln;
            }
         }
      }

      return bln;
   }

   private String getUserDetails(String user) {
      String details = user;

      try {
         if (user != null && details != null && details.length() > 0) {
            new UserManager();
            JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
            JDBCGroup g = (JDBCGroup)r.getGroup(user);
            if (g != null) {
               String[] users = g.getAllUserMemberNames();
               details = details + " | ";
               if (users != null && users.length > 0) {
                  for(int i = 0; i < users.length; ++i) {
                     JDBCUser u = (JDBCUser)r.getUser(users[i]);
                     details = details + u.userName + "-" + u.getAttribute("FIRSTNAME") + " " + u.getAttribute("LASTNAME") + " | ";
                  }
               }
            } else {
               JDBCUser u1 = (JDBCUser)r.getUser(user);
               if (u1 != null) {
                  details = u1.userName + "-" + u1.getAttribute("FIRSTNAME") + " " + u1.getAttribute("LASTNAME");
               } else {
                  details = user;
               }
            }
         }

         return details;
      } catch (Exception var9) {
         throw new RuntimeException();
      }
   }

   private String getQueueUserDetails(String user) {
      String details = user;

      try {
         if (user != null) {
            if (user.toLowerCase().contains("corporate")) {
               details = "Corporate Underwriter";
            }

            if (user.toLowerCase().contains("tele")) {
               details = "Tele. Underwriter";
            }

            if (details != null && details.length() > 0) {
               this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
               this.connection = this.ds.getConnection();
               this.pstmt = this.connection.prepareStatement("SELECT q.MEMBER_NAME FROM QUEUE_RESOURCE q, SBM_QUEUE s WHERE q.QUEUE_ID=s.QUEUE_ID AND s.QUEUE_NAME=?");
               this.pstmt.setString(1, user);
               this.rs = this.pstmt.executeQuery();
               if (this.rs != null) {
                  while(this.rs.next()) {
                     details = details + " | " + this.getUserDetails(this.rs.getString("MEMBER_NAME"));
                  }
               }
            }
         }
      } catch (Exception var17) {
         throw new RuntimeException();
      } finally {
         try {
            if (this.rs != null) {
               this.rs.close();
            }
         } catch (SQLException var16) {
         }

         try {
            if (this.pstmt != null) {
               this.pstmt.close();
            }
         } catch (SQLException var15) {
         }

         try {
            if (this.connection != null) {
               this.connection.close();
            }
         } catch (SQLException var14) {
         }

      }

      return details;
   }

   private String START(HashMap dsValues, String username, String password, String piName, String priority) throws AxisFault {
      NLog.ws.debug("START method invoked");
      String sessionId = this.connect(username, password);
      HashMap dsTypeMap = new HashMap();
      dsTypeMap.put("proposalNumber", "STRING");
      dsTypeMap.put("branchCode", "STRING");
      dsTypeMap.put("UWGroup", "STRING");
      dsTypeMap.put("ApprovalAuthority", "STRING");
      dsTypeMap.put("CreatedBy", "STRING");
      dsTypeMap.put("AgentCode", "STRING");
      dsTypeMap.put("ProductCode", "STRING");
      dsTypeMap.put("LOB", "STRING");
      dsTypeMap.put("BASCode", "STRING");
      dsTypeMap.put("Channel", "STRING");
      dsTypeMap.put("BSMRemarks", "STRING");
      dsTypeMap.put("BSMBlazeRemark", "STRING");
      dsTypeMap.put("HealthProcess", "STRING");
      dsTypeMap.put("skipOPS", "STRING");
      dsTypeMap.put("HealthUW", "STRING");
      dsTypeMap.put("PA_UW_Queue", "STRING");
      dsTypeMap.put("TravelQueue", "STRING");
      dsTypeMap.put("PackageQueue", "STRING");
      dsTypeMap.put("MiscQueue", "STRING");
      dsTypeMap.put("WCQueue", "STRING");
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      String pi = this.createProcessInstance(sessionId, this.ptName, piName, priority, resolvedDSValues);
      this.disconnect(sessionId);
      return pi;
   }

   public ResponseObject initiateMotorIRPASFlow(WorkItemObject reqObj) throws AxisFault {
      new WorkItemObject();
      boolean bln = false;
      ResponseObject resObj = new ResponseObject();
      HashMap dsValues = new HashMap();
      ArrayList<String> ls = new ArrayList();
      String proposalNumber = null;
      String BranchCode = null;
      String userId = null;
      String ApprovalAuthority = null;
      String ProductCode = null;
      String LOB = null;
      String BASCode = null;
      String Channel = null;
      String AgentCode = null;
      String BSMRemarks = null;
      String BSMBlazeRemark = null;
      String processInstanceName = null;
      String QuoteNo = null;
      String UWGroup = null;
      String HealthUW = null;
      String PAQueue = null;
      String CIQueue = null;
      String travelQueue = null;
      String packageQueue = null;
      String miscQueue = null;
      String wcQueue = null;

      try {
         if (reqObj == null) {
            resObj.setResponseCode("5070");
            return resObj;
         }

         proposalNumber = reqObj.getProposalNumber();
         System.out.println("proposalNumber " + proposalNumber);
         if (proposalNumber != null && this.checkProposalExists(proposalNumber)) {
            resObj.setResponseCode("6001-ProposalNumber Already Active in Savvion. Cannot create a duplicate Proposal");
            resObj.setMessage("ProposalNumber Already Active in Savvion. Cannot create a duplicate Proposal");
            return resObj;
         }

         BranchCode = reqObj.getBranchCode();
         if (this.checkNull(BranchCode)) {
            resObj.setResponseCode("6002-Null or Empty value passed for branch code");
            resObj.setMessage("Branch Code is Null Or Empty");
            return resObj;
         }

         userId = reqObj.getUserId();
         if (this.checkNull(userId)) {
            resObj.setResponseCode("5030-Null or Empty value passed for userId code");
            resObj.setMessage("User Id is null or invalid");
            return resObj;
         }

         ApprovalAuthority = reqObj.getApprovalAuthority();
         if (this.checkNull(ApprovalAuthority)) {
            resObj.setResponseCode("6003-Null or Empty value passed for ApprovalAuthority code");
            resObj.setMessage("Approval Authority is Null Or Blank");
            return resObj;
         }

         AgentCode = reqObj.getAgentCode();
         Channel = reqObj.getChannel();
         BASCode = reqObj.getBASCode();
         ProductCode = reqObj.getProductCode();
         if (this.checkNull(ProductCode)) {
            resObj.setResponseCode("6004-Null or Empty value passed for Product code");
            resObj.setMessage("Product Code is Null Or Blank");
            return resObj;
         }

         System.out.println("ProductCode " + ProductCode);
         if (ProductCode != null && ProductCode.length() > 0) {
            if (!ProductCode.contains("-")) {
               resObj.setResponseCode("6005-Product code should be in the format ProductCode-ProductName format");
               resObj.setMessage("Product Code Format Error, Must be Product Code-Product Name");
               return resObj;
            }

            String[] arr = ProductCode.split("-");
            this.connection = this.getConnectionToSQLDB();
            this.proc_stmt = this.connection.prepareCall("{ call usp_Get_LOB_Details(?) }");
            this.proc_stmt.setString(1, arr[0]);
            this.rs = this.proc_stmt.executeQuery();
            if (this.rs != null) {
               while(this.rs.next()) {
                  LOB = this.rs.getString("LOB");
                  if (LOB != null) {
                     LOB = LOB.toUpperCase();
                  }
               }
            }

            this.closeResources();
         }

         BSMRemarks = reqObj.getRemarks();
         BSMBlazeRemark = reqObj.getBlazeRemarks();
         QuoteNo = reqObj.getQuoteNo();
         dsValues.put("proposalNumber", proposalNumber);
         dsValues.put("branchCode", BranchCode);
         dsValues.put("ApprovalAuthority", ApprovalAuthority);
         dsValues.put("CreatedBy", userId);
         dsValues.put("AgentCode", AgentCode);
         dsValues.put("Channel", Channel);
         dsValues.put("LOB", LOB);
         dsValues.put("BASCode", BASCode);
         dsValues.put("ProductCode", ProductCode);
         dsValues.put("BSMRemarks", BSMRemarks);
         dsValues.put("BSMBlazeRemark", BSMBlazeRemark);
         String HealthProcess = reqObj.getProcess();
         if (LOB.equalsIgnoreCase("package")) {
            packageQueue = "Irpas_PackageQueue";
            processInstanceName = "PACKAGE_" + proposalNumber + "_CASE";
            dsValues.put("PackageQueue", "Irpas_PackageQueue");
         }

         String[] arr;
         String[] arr1;
         if (LOB.equalsIgnoreCase("misc")) {
            miscQueue = "Irpas_Misc_UMQueue";
            processInstanceName = "MISC_" + proposalNumber + "_CASE";
            if (ProductCode != null && ProductCode.length() > 0) {
               if (!ProductCode.contains("-")) {
                  resObj.setResponseCode("6005-Product code should be in the format ProductCode-ProductName format");
                  resObj.setMessage("Product Code Format Error, Must be Product Code-Product Name");
                  return resObj;
               }

               arr = ProductCode.split("-");
               arr1 = BranchCode.split("-");
               this.connection = this.getConnectionToSQLDB();
               this.proc_stmt = this.connection.prepareCall("{ call usp_GetQueueDetails(?,?,?) }");
               this.proc_stmt.setString(1, arr[0]);
               this.proc_stmt.setString(2, arr1[0]);
               this.proc_stmt.setString(3, ApprovalAuthority);
               this.rs = this.proc_stmt.executeQuery();
               if (this.rs != null) {
                  while(this.rs.next()) {
                     miscQueue = this.rs.getString("QUEUE_NAME");
                  }
               }

               this.closeResources();
            }

            dsValues.put("MiscQueue", miscQueue);
         }

         if (LOB.equalsIgnoreCase("WC")) {
            wcQueue = "IRPAS_WC_ZUM";
            processInstanceName = "WC_" + proposalNumber + "_CASE";
            if (ProductCode != null && ProductCode.length() > 0) {
               if (!ProductCode.contains("-")) {
                  resObj.setResponseCode("6005-Product code should be in the format ProductCode-ProductName format");
                  resObj.setMessage("Product Code Format Error, Must be Product Code-Product Name");
                  return resObj;
               }

               arr = ProductCode.split("-");
               arr1 = BranchCode.split("-");
               this.connection = this.getConnectionToSQLDB();
               this.proc_stmt = this.connection.prepareCall("{ call usp_GetQueueDetails(?,?,?) }");
               this.proc_stmt.setString(1, arr[0]);
               this.proc_stmt.setString(2, arr1[0]);
               this.proc_stmt.setString(3, ApprovalAuthority);
               this.rs = this.proc_stmt.executeQuery();
               if (this.rs != null) {
                  while(this.rs.next()) {
                     miscQueue = this.rs.getString("QUEUE_NAME");
                  }
               }

               this.closeResources();
            }

            dsValues.put("WCQueue", wcQueue);
         }

         if (LOB.equalsIgnoreCase("travel")) {
            processInstanceName = "TRAVEL_" + proposalNumber + "_CASE";
            dsValues.put("TravelQueue", "Irpas_TravelQueue");
            travelQueue = "Irpas_TravelQueue";
         }

         if (LOB.equalsIgnoreCase("PERSONAL ACCIDENT")) {
            processInstanceName = "PA_" + proposalNumber + "_CASE";
            if (ApprovalAuthority.equalsIgnoreCase("31") || ApprovalAuthority.equalsIgnoreCase("32")) {
               dsValues.put("PA_UW_Queue", "PA_ZUW_Queue");
               PAQueue = "PA_ZUW_Queue";
            }

            if (ApprovalAuthority.equalsIgnoreCase("33")) {
               dsValues.put("PA_UW_Queue", "PA_CUW_Queue");
               PAQueue = "PA_CUW_Queue";
            }
         }

         if (LOB.equalsIgnoreCase("HEALTH")) {
            processInstanceName = "HEALTH_" + proposalNumber + "_CASE";
            dsValues.put("HealthProcess", HealthProcess);
            if (!this.checkNull(HealthProcess)) {
               if (HealthProcess.equalsIgnoreCase("2")) {
                  HealthUW = "IRPAS_Health_TeleUnderwriter";
                  System.out.println("Health UW is " + HealthUW);
                  dsValues.put("CallStatus", "Pending with Health Tele.UW");
                  dsValues.put("HealthUW", HealthUW);
               }

               if (HealthProcess.equalsIgnoreCase("1") || HealthProcess.equalsIgnoreCase("3") || HealthProcess.equalsIgnoreCase("4") || HealthProcess.equalsIgnoreCase("5")) {
                  HealthUW = "IRPAS_Corporate_Health_UW";
                  dsValues.put("HealthUW", HealthUW);
                  if (!HealthProcess.equalsIgnoreCase("1")) {
                     dsValues.put("CallStatus", "Pending with Health Co.UW");
                     System.out.println("Health UW is " + HealthUW);
                  } else {
                     dsValues.put("CallStatus", "Pending with TPA");
                  }
               }
            }

            if (!this.checkNull(HealthProcess) && HealthProcess.equalsIgnoreCase("4")) {
               dsValues.put("skipOPS", "FALSE");
               dsValues.put("CallStatus", "Pending with OPS Team");
            } else {
               dsValues.put("skipOPS", "TRUE");
            }
         }

         if (LOB.equalsIgnoreCase("MOTOR")) {
            processInstanceName = "MOTOR_" + proposalNumber + "_CASE";

            try {
               if (BranchCode != null) {
                  this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
                  this.connection = this.ds.getConnection();
                  this.pstmt = this.connection.prepareStatement(this.sqlString);
                  if (BranchCode.contains("-")) {
                     arr = BranchCode.split("-");
                     if (arr != null) {
                        BranchCode = arr[0];
                     }
                  }

                  this.pstmt.setString(1, BranchCode);

                  for(this.rs = this.pstmt.executeQuery(); this.rs.next(); UWGroup = this.rs.getString("BRANCH_TYPE")) {
                  }
               }

               dsValues.put("UWGroup", UWGroup);
            } catch (SQLException var47) {
               resObj.setResponseCode("6006-Issue with Motor branch and UW mapping" + var47.getMessage());
            } finally {
               try {
                  if (this.rs != null) {
                     this.rs.close();
                  }
               } catch (SQLException var46) {
               }

               try {
                  if (this.pstmt != null) {
                     this.pstmt.close();
                  }
               } catch (SQLException var45) {
               }

               try {
                  if (this.connection != null) {
                     this.connection.close();
                  }
               } catch (SQLException var44) {
               }

            }
         }

         HealthProcess = "medium";
         processInstanceName = this.START(dsValues, "rgicl", "rgicl", processInstanceName, HealthProcess);
         resObj.setResponseCode("5000");
         ls.add(processInstanceName);
         arr = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            arr[j] = (String)ls.get(j);
         }

         resObj.setResultStringArray(arr);
         if (resObj.getResponseCode().equalsIgnoreCase("5000")) {
            if (LOB.equalsIgnoreCase("MOTOR")) {
               resObj.setMessage("Sent To " + this.getUserDetails(UWGroup) + " For Underwriter Approval");
            } else if (LOB.equalsIgnoreCase("HEALTH")) {
               String HealthProcess = reqObj.getProcess();
               if (HealthProcess != null && HealthProcess.equals("1")) {
                  resObj.setMessage("Case is Pending with TPA and has been also sent To " + this.getQueueUserDetails(HealthUW) + " For Approval");
               } else {
                  resObj.setMessage("Sent To " + this.getQueueUserDetails(HealthUW) + " For Approval");
               }
            } else if (LOB.equalsIgnoreCase("PERSONAL ACCIDENT")) {
               resObj.setMessage("Sent To " + this.getQueueUserDetails(PAQueue) + " For Approval");
            } else if (LOB.equalsIgnoreCase("travel")) {
               resObj.setMessage("Sent To " + this.getQueueUserDetails(travelQueue) + " For Approval");
            } else if (LOB.equalsIgnoreCase("package")) {
               resObj.setMessage("Sent To " + this.getQueueUserDetails(packageQueue) + " For Approval");
            } else if (LOB.equalsIgnoreCase("misc")) {
               resObj.setMessage("Sent To " + this.getQueueUserDetails(miscQueue) + " For Approval");
            } else if (LOB.equalsIgnoreCase("WC")) {
               resObj.setMessage("Sent To " + this.getQueueUserDetails(wcQueue) + " For Approval");
            }
         }

         WorkItemObject obj = new WorkItemObject();
         obj.setStatus("Initiate Workflow");
         obj.setRemarks("Response code : " + resObj.getResponseCode() + "  Message > " + resObj.getMessage());
         obj.setBlazeRemarks(reqObj.getBlazeRemarks());
         obj.setApprovalAuthority(reqObj.getApprovalAuthority());
         obj.setProposalNumber(proposalNumber);
         obj.setProcessInstanceName(processInstanceName);
         obj.setWorkStepName("BSM");
         obj.setUserId(userId);
         obj.setQuoteNo(reqObj.getQuoteNo());
         this.addRemarks(obj);
      } catch (Exception var49) {
         resObj.setResponseCode("5050-Failure Exception" + var49.getMessage());
      }

      return resObj;
   }

   public ResponseObject getIRPASMotorTaskList(WorkItemObject reqObj) throws AxisFault {
      ResponseObject resObj = new ResponseObject();

      try {
         String responseCode = null;
         String taskListArray = "5042";
         ArrayList resultTaskList = new ArrayList();
         WorkItemObject[] workItemObject = (WorkItemObject[])null;
         if (reqObj == null) {
            resObj.setResponseCode("5070");
         } else {
            String userId = reqObj.getUserId();
            String _ptName = this.ptName;
            String proposalNumber = reqObj.getProposalNumber();
            String BranchCode = reqObj.getBranchCode();
            String AgentCode = reqObj.getAgentCode();
            String Channel = reqObj.getChannel();
            String LOB = reqObj.getLOB();
            String BASCode = reqObj.getBASCode();
            String var15 = reqObj.getProductCode();

            try {
               if (this.checkNull(userId)) {
                  responseCode = "5001";
                  throw new RuntimeException("User id is Null");
               }

               QueryService qs = null;
               QSWorkItemRS wirs = null;
               String sessionId = this.connect(userId, this.getUserPassword(userId));
               Session sess = getBizLogicManager().getSession(sessionId);
               QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
               qs = new QueryService(sess);
               QSWorkItem qswi = qs.getWorkItem();
               wifil.setFetchSize(0);
               int[] var10000 = new int[2];
               BLConstants.single().getClass();
               var10000[0] = 27;
               BLConstants.single().getClass();
               var10000[1] = 28;
               int[] states = var10000;
               WorkItemList wiList = qswi.getList(wifil, states);
               if (wiList != null) {
                  List list = wiList.getList();
                  if (list != null && !list.isEmpty()) {
                     for(int i = 0; i < list.size(); ++i) {
                        WorkItem wi = (WorkItem)list.get(i);
                        String value = "";
                        WorkItemObject workitem = new WorkItemObject();
                        DataSlot ds = null;
                        ds = wi.getDataSlot("proposalNumber");
                        value = (String)ds.getValue();
                        workitem.setProposalNumber(value);
                        value = wi.getPerformer();
                        if (!wi.getName().contains("PA UWActivity") && !wi.getName().contains("Health UW") && !wi.getName().contains("Ops Team")) {
                           workitem.setPerformer(this.getUserDetails(value));
                        } else {
                           workitem.setPerformer(this.getQueueUserDetails(value));
                        }

                        ds = wi.getDataSlot("CallStatus");
                        value = (String)ds.getValue();
                        workitem.setStatus(value);
                        long l = wi.getStartTime();
                        long l1 = (new Date()).getTime();
                        SBMCalendar.init((Properties)null);
                        SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
                        long l2 = (long)Math.round((float)(sbmcal.getDuration(l, l1) / 3600000L));
                        workitem.setTAT((new Long(l2)).toString() + " Hours");
                        resultTaskList.add(workitem);
                     }
                  }
               }

               getBizLogicManager().disconnect(sessionId);
               QueryService.clean();
               responseCode = "5000";
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
               } else {
                  responseCode = "5042";
               }
            } catch (Exception var37) {
               responseCode = "5030-Null or Empty value passed for userId code";
               throw new RuntimeException("Error in getting Proposals from Savvion " + var37.getMessage());
            }

            resObj.setResponseCode(responseCode);
         }

         return resObj;
      } catch (Exception var38) {
         throw new RuntimeException("Error in getting Proposals from Savvion " + var38.getMessage());
      }
   }

   private WorkItemObject[] getResultWorkitems(ArrayList workitemlist) {
      WorkItemObject[] resultWorkitems = (WorkItemObject[])null;
      resultWorkitems = new WorkItemObject[workitemlist.size()];
      workitemlist.toArray(resultWorkitems);
      return resultWorkitems;
   }

   private void assignWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         PAKClientWorkitem pwi = getBizLogicManager().getWorkitem(sessionId, wiName);
         getBizLogicManager().assignWorkitemPerformer(sessionId, pwi);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private String suspendProposalQuote(String processInstanceName, String userId) throws AxisFault {
      String success = null;

      try {
         if (processInstanceName != null && userId != null) {
            BLServer blserver = BLClientUtil.getBizLogicServer();
            Session blsession = blserver.connect("rgicl", "rgicl");
            ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
            HashMap hm = new HashMap();
            hm.put("SuspendStatus", "Suspended");
            Date date2 = new Date();
            SimpleDateFormat Currdateformat2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
            String dt = Currdateformat2.format(date2);
            hm.put("SuspendDate", dt);
            hm.put("CallStatus", "Sent again To BSM");
            pi.updateSlotValue(hm);
            pi.save();
            pi.suspend();
            success = "5000";
         }

         return success;
      } catch (Exception var11) {
         throw new RuntimeException(var11);
      }
   }

   private String resumeProposalQuote(String processInstanceName, String userId) throws AxisFault {
      String success = "";

      try {
         if (processInstanceName != null && userId != null) {
            BLServer blserver = BLClientUtil.getBizLogicServer();
            Session blsession = blserver.connect("rgicl", "rgicl");
            ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
            if (pi.isSuspended()) {
               pi.resume();
               pi.save();
               WorkItemList wilist = pi.getWorkItemList();
               if (wilist != null) {
                  List ls = wilist.getList();
                  if (ls != null && !ls.isEmpty()) {
                     for(int i = 0; i < ls.size(); ++i) {
                        WorkItem wi = (WorkItem)ls.get(i);
                        success = wi.getPerformer();
                     }
                  }
               }

               HashMap hm = new HashMap();
               hm.put("SuspendStatus", "Resumed");
               Date date2 = new Date();
               SimpleDateFormat Currdateformat2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
               String dt = Currdateformat2.format(date2);
               hm.put("SuspendDate", dt);
               hm.put("CallStatus", "Sent again To UW");
               pi.updateSlotValue(hm);
               pi.save();
            }
         }

         return success;
      } catch (Exception var12) {
         throw new RuntimeException(var12);
      }
   }

   public boolean checkProposalExists(String proposalNumber) {
      boolean bln = false;

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         ProcessInstanceList piList = blserver.getProcessInstanceList(blsession, ptid);
         List l1 = piList.getList();
         if (l1 != null && !l1.isEmpty()) {
            for(int d = 0; d < l1.size(); ++d) {
               ProcessInstance pi = (ProcessInstance)l1.get(d);
               DataSlot ds1 = pi.getDataSlot("proposalNumber");
               String p1 = (String)ds1.getValue();
               if (p1 != null && p1.equalsIgnoreCase(proposalNumber)) {
                  bln = true;
                  return bln;
               }
            }
         }

         blserver.disConnect(blsession);
         return bln;
      } catch (Exception var13) {
         throw new RuntimeException("Error in fecthing Proposal Details" + var13.getMessage(), var13);
      }
   }

   public String deleteProposalQuoteFromSavvion(String proposalNumber) {
      String msg = "failure";

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         ProcessInstanceList piList = blserver.getProcessInstanceList(blsession, ptid);
         List l1 = piList.getList();
         if (l1 != null && !l1.isEmpty()) {
            for(int d = 0; d < l1.size(); ++d) {
               ProcessInstance pi = (ProcessInstance)l1.get(d);
               if (pi.isActivated() || pi.isSuspended()) {
                  DataSlot ds1 = pi.getDataSlot("proposalNumber");
                  String p1 = (String)ds1.getValue();
                  if (p1 != null && p1.equalsIgnoreCase(proposalNumber)) {
                     pi.remove();
                     pi.save();
                     msg = "Success";
                     return msg;
                  }
               }
            }
         }

         blserver.disConnect(blsession);
         return msg;
      } catch (Exception var13) {
         throw new RuntimeException("Error in fecthing Proposal Details" + var13.getMessage(), var13);
      }
   }

   public String getActionDetails(String proposalNumber) {
      String action = null;
      long piid = 0L;
      String ApprovalAuthority = null;
      System.out.println("Inside getAction details");

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         ProcessInstanceList piList = blserver.getProcessInstanceList(blsession, ptid);
         List l1 = piList.getList();
         if (l1 != null && !l1.isEmpty()) {
            for(int d = 0; d < l1.size(); ++d) {
               ProcessInstance pi = (ProcessInstance)l1.get(d);
               DataSlot ds1 = pi.getDataSlot("proposalNumber");
               String p1 = (String)ds1.getValue();
               if (p1 != null && p1.equalsIgnoreCase(proposalNumber)) {
                  piid = pi.getID();
                  ApprovalAuthority = pi.getDataSlotValue("ApprovalAuthority").toString();
                  System.out.println("Process Instance Id " + piid);
               }
            }
         }

         WorkStepInstanceList wsi = blserver.getActivatedWorkStepInstanceList(blsession, piid, true);
         if (wsi != null) {
            List wsList = wsi.getList();
            if (wsList != null && !wsList.isEmpty()) {
               for(int j = 0; j < wsList.size(); ++j) {
                  WorkStepInstance ws = (WorkStepInstance)wsList.get(j);
                  WorkItemList wiList = ws.getWorkItemList();
                  if (wiList != null) {
                     List list = wiList.getList();
                     if (list != null && !list.isEmpty()) {
                        for(int i = 0; i < list.size(); ++i) {
                           WorkItem wi = (WorkItem)list.get(i);
                           String wsName = wi.getWorkStepName().toUpperCase();
                           if (wsName.equals("UWACTIVITY") && ApprovalAuthority.equals("32")) {
                              action = "1";
                              return action;
                           }

                           if (wsName.equals("UWACTIVITY") && ApprovalAuthority.equals("33")) {
                              action = "0";
                              return action;
                           }

                           if (wsName.equals("CUACTIVITY")) {
                              action = "2";
                              return action;
                           }

                           if (wsName.equals("HEALTH UW") || wsName.equals("OPS TEAM") || wsName.equals("PA UWACTIVITY") || wsName.equals("TRAVELACTIVITY") || wsName.equals("MISCPRODUCTS")) {
                              action = "3";
                              return action;
                           }

                           if (wsName.equals("PACKAGE PRODUCTS")) {
                              action = "2";
                              return action;
                           }
                        }
                     }
                  }
               }
            }
         }

         blserver.disConnect(blsession);
         return action;
      } catch (Exception var21) {
         throw new RuntimeException("Error in getting action details " + var21.getMessage(), var21);
      }
   }

   public String getRuntimeActionDetails(String proposalNumber, String newAuthCode) {
      String action = null;
      long piid = 0L;
      String ApprovalAuthority = null;

      try {
         int newAuth = Integer.parseInt(newAuthCode);
         System.out.println("New Auth Code is >> " + newAuth);
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         ProcessInstanceList piList = blserver.getProcessInstanceList(blsession, ptid);
         List l1 = piList.getList();
         int authCode;
         if (l1 != null && !l1.isEmpty()) {
            for(authCode = 0; authCode < l1.size(); ++authCode) {
               ProcessInstance pi = (ProcessInstance)l1.get(authCode);
               DataSlot ds1 = pi.getDataSlot("proposalNumber");
               String p1 = (String)ds1.getValue();
               if (p1 != null && p1.equalsIgnoreCase(proposalNumber)) {
                  piid = pi.getID();
                  ApprovalAuthority = pi.getDataSlotValue("ApprovalAuthority").toString();
               }
            }
         }

         if (piid == 0L) {
            throw new RuntimeException("Proposal does not exist or action already taken on the proposal");
         } else {
            authCode = Integer.parseInt(ApprovalAuthority);
            WorkStepInstanceList wsi = blserver.getActivatedWorkStepInstanceList(blsession, piid, true);
            if (wsi != null) {
               List wsList = wsi.getList();
               if (wsList != null && !wsList.isEmpty()) {
                  for(int j = 0; j < wsList.size(); ++j) {
                     WorkStepInstance ws = (WorkStepInstance)wsList.get(j);
                     WorkItemList wiList = ws.getWorkItemList();
                     if (wiList != null) {
                        List list = wiList.getList();
                        if (list != null && !list.isEmpty()) {
                           for(int i = 0; i < list.size(); ++i) {
                              WorkItem wi = (WorkItem)list.get(i);
                              String wsName = wi.getWorkStepName().toUpperCase();
                              System.out.println("WS NAME >> " + wsName + "  Proposal # " + proposalNumber + "  New Auth Code " + newAuth + " Old Auth " + ApprovalAuthority);
                              if (wsName.contains("UWACTIVITY") && ApprovalAuthority.equals("32")) {
                                 if (newAuth == 32) {
                                    action = "1";
                                 } else {
                                    action = "0";
                                 }
                              }

                              if (wsName.contains("UWACTIVITY") && ApprovalAuthority.equals("33")) {
                                 if (newAuth == 33) {
                                    action = "0";
                                 } else if (newAuth == 32) {
                                    action = "1";
                                 }
                              }

                              if (wsName.contains("UWACTIVITY") && this.checkBSMLimit(newAuthCode)) {
                                 action = "1";
                              }

                              if (wsName.contains("CUACTIVITY")) {
                                 if (newAuth == 33) {
                                    action = "2";
                                 }

                                 if (newAuth == 32) {
                                    action = "1";
                                 }
                              }

                              if (wsName.contains("HEALTH UW") || wsName.contains("OPS TEAM") || wsName.contains("PA UWActivity") || wsName.contains("TravelActivity")) {
                                 action = "3";
                              }

                              if (wsName.contains("PACKAGE PRODUCTS")) {
                                 action = "2";
                              }
                           }
                        }
                     }
                  }
               }
            }

            blserver.disConnect(blsession);
            return action;
         }
      } catch (Exception var24) {
         throw new RuntimeException("Error in getting action details " + var24.getMessage(), var24);
      }
   }

   public ResponseObject UpdateMotorIRPASSetUpDataSlots(WorkItemObject reqObj) {
      String sessionId = null;
      String responseCode = null;
      boolean isMotor = false;
      boolean isAvailable = true;
      String processInstanceName = "";
      String userId = null;
      String status = null;
      String proposalNumber = null;
      String quoteNo = null;
      String ApprovalAuthority = null;
      boolean bln = false;
      ResponseObject resObj = new ResponseObject();
      String workStepName = null;
      new WorkItemObject();
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         return resObj;
      } else {
         userId = reqObj.getUserId();
         status = reqObj.getStatus();
         proposalNumber = reqObj.getProposalNumber();
         quoteNo = reqObj.getQuoteNo();
         ApprovalAuthority = reqObj.getApprovalAuthority();
         if (quoteNo == null) {
            quoteNo = "";
         }

         String wiName = "";

         try {
            try {
               WorkItemObject obj;
               if (!this.checkNull(proposalNumber) && !this.checkNull(userId)) {
                  BLServer blserver = BLClientUtil.getBizLogicServer();
                  Session blsession = null;
                  JDBCRealm wirs;
                  ResponseObject var42;
                  ProcessInstance pi;
                  String uwstatus;
                  if (status == null || status.equalsIgnoreCase("")) {
                     new UserManager();
                     wirs = (JDBCRealm)UserManager.getDefaultRealm();
                     blsession = blserver.connect("rgicl", "rgicl");
                     boolean isSuspended = false;
                     long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
                     ProcessInstanceList piList = blserver.getSuspendedProcessInstanceList(blsession, ptid);
                     List l1 = piList.getList();
                     String perf = "";
                     if (l1 != null && !l1.isEmpty()) {
                        for(int d = 0; d < l1.size(); ++d) {
                           pi = (ProcessInstance)l1.get(d);
                           DataSlot ds1 = pi.getDataSlot("proposalNumber");
                           uwstatus = (String)ds1.getValue();
                           if (uwstatus != null && uwstatus.equalsIgnoreCase(proposalNumber) && pi.isSuspended()) {
                              perf = this.resumeProposalQuote(pi.getName(), userId);
                              System.out.println("When resuming authode is >>" + ApprovalAuthority);
                              if (ApprovalAuthority != null && ApprovalAuthority.length() > 0) {
                                 pi.updateSlotValue("ApprovalAuthority", ApprovalAuthority);
                                 pi.save();
                              }

                              WorkStepInstanceList wsi = blserver.getActivatedWorkStepInstanceList(blsession, pi.getID(), true);
                              if (wsi != null) {
                                 List wsList = wsi.getList();
                                 if (wsList != null && !wsList.isEmpty()) {
                                    for(int j = 0; j < wsList.size(); ++j) {
                                       WorkStepInstance ws = (WorkStepInstance)wsList.get(j);
                                       WorkItemList wiList = ws.getWorkItemList();
                                       if (wiList != null) {
                                          List list = wiList.getList();
                                          if (list != null && !list.isEmpty()) {
                                             for(int i = 0; i < list.size(); ++i) {
                                                WorkItem wi = (WorkItem)list.get(i);
                                                String wsName = wi.getWorkStepName().toUpperCase();
                                                if (wsName.contains("CUACTIVITY") && ApprovalAuthority.equals("32")) {
                                                   String uwapp = (String)pi.getDataSlotValue("UWApprover");
                                                   if (wi.isAvailable()) {
                                                      wi.assign(uwapp);
                                                      wi.save();
                                                      pi.save();
                                                   } else {
                                                      wi.reAssign(uwapp);
                                                      wi.save();
                                                      pi.save();
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }

                              responseCode = "5000";
                              resObj.setResponseCode(responseCode);
                              resObj.setMessage("Case sent again to underwriter " + this.getUserDetails(perf));
                              obj = new WorkItemObject();
                              obj.setStatus("Sent again To UW");
                              obj.setRemarks(resObj.getResponseCode() + resObj.getMessage());
                              obj.setBlazeRemarks(reqObj.getBlazeRemarks());
                              obj.setApprovalAuthority(reqObj.getAgentCode());
                              obj.setProposalNumber(proposalNumber);
                              obj.setProcessInstanceName(pi.getName());
                              obj.setWorkStepName("BSM");
                              obj.setUserId(userId);
                              obj.setQuoteNo(quoteNo);
                              this.addRemarks(obj);
                              var42 = resObj;
                              return var42;
                           }
                        }
                     }

                     resObj.setResponseCode("5000");
                     resObj.setMessage("Case sent again to underwriter " + this.getUserDetails(perf));
                     System.out.println("Resume Ended 2");
                     var42 = resObj;
                     return var42;
                  }

                  QueryService qs = null;
                  wirs = null;
                  System.out.println("Started getting workitems");
                  String sessionId1 = this.connect(userId, this.getUserPassword(userId));
                  Session sess = getBizLogicManager().getSession(sessionId1);
                  QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
                  if (proposalNumber != null && proposalNumber.length() > 0) {
                     wifil.setCondition("BLIDS.proposalNumber='" + proposalNumber + "'");
                  }

                  String toUser = reqObj.getAssignToUserId();
                  System.out.println("TO user Id" + toUser);
                  qs = new QueryService(sess);
                  QSWorkItem qswi = qs.getWorkItem();
                  wifil.setFetchSize(0);
                  int[] var10000 = new int[2];
                  BLConstants.single().getClass();
                  var10000[0] = 27;
                  BLConstants.single().getClass();
                  var10000[1] = 28;
                  int[] states = var10000;
                  WorkItemList wiList = qswi.getList(wifil, states);
                  String uwremarks;
                  String appAuth;
                  String productCode;
                  if (wiList != null) {
                     List list = wiList.getList();
                     System.out.println(list.size());
                     if (list != null && !list.isEmpty()) {
                        for(int i = 0; i < list.size(); ++i) {
                           WorkItem wi = (WorkItem)list.get(i);
                           System.out.println(wi.getName() + wi.getState() + wi.getStateDescription());
                           wiName = wi.getName();
                           Vector v;
                           if (wi.isAvailable()) {
                              isAvailable = true;
                              System.out.println("WorkItem is available");
                              if ((wiName.toUpperCase().contains("PA UWACTIVITY") || wiName.toUpperCase().contains("HEALTH UW") || wiName.toUpperCase().contains("OPS TEAM") || wiName.toUpperCase().contains("MISCPRODUCTS") || wiName.toUpperCase().contains("WC_ACTIVITY")) && status != null && status.equalsIgnoreCase("forward")) {
                                 System.out.println("Status " + status + "winame " + wiName);
                                 if (wiName.toUpperCase().contains("PA UWACTIVITY")) {
                                    toUser = "PA_CUW_Queue";
                                    v = new Vector();
                                    v.add(toUser);
                                    wi.makeAvailable(v);
                                    wi.save();
                                    resObj.setMessage("Case Forwarded to CU/W " + this.getQueueUserDetails(toUser));
                                 } else {
                                    String[] arr;
                                    String[] arr1;
                                    Vector v;
                                    ProcessInstance pi1;
                                    if (wiName.toUpperCase().contains("MISCPRODUCTS")) {
                                       uwremarks = (String)wi.getDataSlot("branchCode").getValue();
                                       System.out.println(uwremarks);
                                       appAuth = (String)wi.getDataSlot("ApprovalAuthority").getValue();
                                       System.out.println(appAuth);
                                       productCode = (String)wi.getDataSlot("ProductCode").getValue();
                                       System.out.println(productCode);
                                       arr = uwremarks.split("-");
                                       arr1 = productCode.split("-");
                                       if (appAuth.equalsIgnoreCase("31")) {
                                          appAuth = "32";
                                       }

                                       if (appAuth.equalsIgnoreCase("32")) {
                                          appAuth = "33";
                                       }

                                       if (productCode != null && productCode.length() > 0 && productCode.contains("-")) {
                                          this.connection = this.getConnectionToSQLDB();
                                          this.proc_stmt = this.connection.prepareCall("{ call usp_GetQueueDetails(?,?,?) }");
                                          this.proc_stmt.setString(1, arr1[0]);
                                          this.proc_stmt.setString(2, arr[0]);
                                          this.proc_stmt.setString(3, appAuth);
                                          this.rs = this.proc_stmt.executeQuery();
                                          if (this.rs != null) {
                                             while(this.rs.next()) {
                                                toUser = this.rs.getString("QUEUE_NAME");
                                                System.out.println("touser " + toUser);
                                             }
                                          }

                                          this.closeResources();
                                       }

                                       v = new Vector();
                                       v.add(toUser);
                                       wi.makeAvailable(v);
                                       wi.save();
                                       System.out.println("sDones");
                                       pi1 = blserver.getProcessInstance(sess, wi.getProcessInstanceID());
                                       pi1.updateSlotValue("ApprovalAuthority", appAuth);
                                       pi1.save();
                                       System.out.println("Saved");
                                       resObj.setMessage("Case Forwarded to " + this.getQueueUserDetails(toUser));
                                    } else if (wiName.toUpperCase().contains("WC_ACTIVITY")) {
                                       uwremarks = (String)wi.getDataSlot("branchCode").getValue();
                                       System.out.println(uwremarks);
                                       appAuth = (String)wi.getDataSlot("ApprovalAuthority").getValue();
                                       System.out.println(appAuth);
                                       productCode = (String)wi.getDataSlot("ProductCode").getValue();
                                       System.out.println(productCode);
                                       arr = uwremarks.split("-");
                                       arr1 = productCode.split("-");
                                       if (appAuth.equalsIgnoreCase("31")) {
                                          appAuth = "32";
                                       }

                                       if (appAuth.equalsIgnoreCase("32")) {
                                          appAuth = "33";
                                       }

                                       if (productCode != null && productCode.length() > 0 && productCode.contains("-")) {
                                          this.connection = this.getConnectionToSQLDB();
                                          this.proc_stmt = this.connection.prepareCall("{ call usp_GetQueueDetails(?,?,?) }");
                                          this.proc_stmt.setString(1, arr1[0]);
                                          this.proc_stmt.setString(2, arr[0]);
                                          this.proc_stmt.setString(3, appAuth);
                                          this.rs = this.proc_stmt.executeQuery();
                                          if (this.rs != null) {
                                             while(this.rs.next()) {
                                                toUser = this.rs.getString("QUEUE_NAME");
                                                System.out.println("touser " + toUser);
                                             }
                                          }

                                          this.closeResources();
                                       }

                                       v = new Vector();
                                       v.add(toUser);
                                       wi.makeAvailable(v);
                                       wi.save();
                                       System.out.println("sDones");
                                       pi1 = blserver.getProcessInstance(sess, wi.getProcessInstanceID());
                                       pi1.updateSlotValue("ApprovalAuthority", appAuth);
                                       pi1.save();
                                       System.out.println("Saved");
                                       resObj.setMessage("Case Forwarded to " + this.getQueueUserDetails(toUser));
                                    }
                                 }

                                 resObj.setResponseCode("5000");
                                 System.out.println("Case Forwarded to user " + toUser);
                                 var42 = resObj;
                                 return var42;
                              }
                           }

                           if (wi.isAssigned()) {
                              isAvailable = false;
                              if (wiName.toUpperCase().contains("PA UWACTIVITY")) {
                                 toUser = "PA_CUW_Queue";
                              }

                              if (toUser != null && (wiName.toUpperCase().contains("PA UWACTIVITY") || wiName.toUpperCase().contains("HEALTH UW") || wiName.toUpperCase().contains("OPS TEAM")) && status != null && status.equalsIgnoreCase("forward")) {
                                 if (wiName.toUpperCase().contains("PA UWACTIVITY")) {
                                    toUser = "PA_CUW_Queue";
                                    v = new Vector();
                                    v.add(toUser);
                                    wi.makeAvailable(v);
                                    wi.save();
                                    resObj.setMessage("Case Forwarded to CU/W " + this.getQueueUserDetails(toUser));
                                 } else {
                                    wi.reAssign(toUser);
                                    wi.save();
                                    wi.refresh();
                                    System.out.println("Done");
                                    resObj.setMessage("Case Forwarded to user " + this.getUserDetails(toUser));
                                 }

                                 resObj.setResponseCode("5000");
                                 System.out.println("Case Forwarded to user " + toUser);
                                 var42 = resObj;
                                 return var42;
                              }

                              if (status != null && status.equalsIgnoreCase("Forward") && wiName.toUpperCase().contains("CUACTIVITY")) {
                                 uwremarks = reqObj.getApprovalAuthority();
                                 if (uwremarks.equalsIgnoreCase("33")) {
                                    wi.makeAvailable();
                                    wi.save();
                                    responseCode = "5000";
                                    resObj.setResponseCode(responseCode);
                                    resObj.setMessage("Case sent to underwriter " + this.getUserDetails("CU"));
                                    System.out.println("Resume Ended 2");
                                    var42 = resObj;
                                    return var42;
                                 }
                              }
                           }

                           DataSlot ds = wi.getDataSlot("proposalNumber");
                           appAuth = (String)ds.getValue();
                           if (appAuth != null && appAuth.equalsIgnoreCase(proposalNumber)) {
                              processInstanceName = wi.getProcessInstanceName();
                              wiName = wi.getName();
                           }
                        }
                     }
                  }

                  blsession = blserver.connect(userId, this.getUserPassword(userId));
                  pi = blserver.getProcessInstance(blsession, processInstanceName);
                  HashMap hm = new HashMap();
                  uwstatus = "";
                  if (wiName != null && wiName.toUpperCase().contains("UWACTIVITY")) {
                     isMotor = true;
                     uwstatus = reqObj.getStatus();
                     if (uwstatus != null && (uwstatus.equals("Approved") || uwstatus.equals("Rejected") || uwstatus.equals("Forward") || uwstatus.toLowerCase().contains("onhold"))) {
                        hm.put("UWStatus", uwstatus);
                     }

                     uwremarks = reqObj.getRemarks();
                     if (!this.checkNull(uwremarks)) {
                        hm.put("UWRemarks", uwremarks);
                     }

                     appAuth = reqObj.getBlazeRemarks();
                     if (!this.checkNull(appAuth)) {
                        hm.put("UWBlazeRemark", appAuth);
                     }

                     productCode = reqObj.getApprovalAuthority();
                     if (!this.checkNull(productCode)) {
                        hm.put("ApprovalAuthority", productCode);
                     }

                     workStepName = "SC-UW";
                  }

                  if (wiName != null && wiName.toUpperCase().contains("CUACTIVITY")) {
                     isMotor = true;
                     uwstatus = reqObj.getStatus();
                     if (uwstatus != null && (uwstatus.equalsIgnoreCase("Approved") || uwstatus.equalsIgnoreCase("Rejected") || uwstatus.toLowerCase().contains("onhold"))) {
                        hm.put("CUStatus", uwstatus);
                     }

                     uwremarks = reqObj.getRemarks();
                     if (!this.checkNull(uwremarks)) {
                        hm.put("CURemarks", uwremarks);
                     }

                     appAuth = reqObj.getBlazeRemarks();
                     if (!this.checkNull(appAuth)) {
                        hm.put("CUBlazeRemark", appAuth);
                     }

                     productCode = reqObj.getApprovalAuthority();
                     if (!this.checkNull(productCode)) {
                        hm.put("ApprovalAuthority", productCode);
                     }

                     workStepName = "Co-UW";
                  }

                  if (wiName != null && (wiName.toUpperCase().contains("HEALTH UW") || wiName.toUpperCase().contains("PA UWACTIVITY")) || wiName.toUpperCase().contains("TRAVELACTIVITY") || wiName.toUpperCase().contains("PACKAGE PRODUCTS") || wiName.toUpperCase().contains("MISCPRODUCTS") || wiName.toUpperCase().contains("WC_ACTIVITY")) {
                     uwstatus = reqObj.getStatus();
                     isMotor = false;
                     if (uwstatus != null && (uwstatus.equals("Forward") || uwstatus.equals("Approved") || uwstatus.equals("Rejected") || uwstatus.toLowerCase().contains("onhold"))) {
                        hm.put("CUStatus", uwstatus);
                     }

                     uwremarks = reqObj.getRemarks();
                     if (!this.checkNull(uwremarks)) {
                        hm.put("CURemarks", uwremarks);
                     }

                     appAuth = reqObj.getBlazeRemarks();
                     if (!this.checkNull(appAuth)) {
                        hm.put("CUBlazeRemark", appAuth);
                     }

                     productCode = reqObj.getApprovalAuthority();
                     if (!this.checkNull(productCode)) {
                        hm.put("ApprovalAuthority", productCode);
                     }

                     obj = new WorkItemObject();
                     obj.setStatus(uwstatus);
                     obj.setRemarks(uwremarks);
                     obj.setBlazeRemarks(appAuth);
                     obj.setApprovalAuthority(productCode);
                     obj.setProposalNumber(proposalNumber);
                     obj.setProcessInstanceName(processInstanceName);
                     if (wiName.contains("Health UW")) {
                        obj.setWorkStepName("Health UW");
                     } else if (wiName.contains("PA Activity")) {
                        obj.setWorkStepName("PA UWActivity");
                     } else if (wiName.contains("TravelActivity")) {
                        obj.setWorkStepName("Travel Activity");
                     } else if (wiName.contains("Package Products")) {
                        obj.setWorkStepName("Package Activity");
                     } else if (wiName.contains("MiscProducts")) {
                        obj.setWorkStepName("Misc Activity");
                     } else if (wiName.contains("WC_ACTIVITY")) {
                        obj.setWorkStepName("Workmens Comp Activity");
                     }

                     obj.setUserId(userId);
                     obj.setQuoteNo(quoteNo);
                     this.addRemarks(obj);
                  }

                  if (wiName != null && wiName.toUpperCase().contains("OPS TEAM")) {
                     uwstatus = reqObj.getStatus();
                     isMotor = false;
                     if (uwstatus != null && (uwstatus.equals("Forward") || uwstatus.equals("Approved") || uwstatus.equals("Rejected") || uwstatus.toLowerCase().contains("onhold"))) {
                        hm.put("CUStatus", uwstatus);
                     }

                     uwremarks = reqObj.getRemarks();
                     if (!this.checkNull(uwremarks)) {
                        hm.put("CURemarks", uwremarks);
                     }

                     appAuth = reqObj.getBlazeRemarks();
                     if (!this.checkNull(appAuth)) {
                        hm.put("CUBlazeRemark", appAuth);
                     }

                     productCode = reqObj.getApprovalAuthority();
                     if (!this.checkNull(productCode)) {
                        hm.put("ApprovalAuthority", productCode);
                     }

                     obj = new WorkItemObject();
                     obj.setStatus(uwstatus);
                     obj.setRemarks(uwremarks);
                     obj.setBlazeRemarks(appAuth);
                     obj.setApprovalAuthority(productCode);
                     obj.setProposalNumber(proposalNumber);
                     obj.setProcessInstanceName(processInstanceName);
                     obj.setWorkStepName("Ops Team");
                     obj.setUserId(userId);
                     obj.setQuoteNo(quoteNo);
                     this.addRemarks(obj);
                  }

                  if (blsession != null) {
                     pi.updateSlotValue(hm);
                     pi.save();
                     if (!this.checkNull(uwstatus) && isMotor) {
                        if (uwstatus.equalsIgnoreCase("Approved") || uwstatus.equalsIgnoreCase("Forward")) {
                           if (wiName == null || !wiName.toUpperCase().contains("CUACTIVITY") || uwstatus != null && !uwstatus.equalsIgnoreCase("forward")) {
                              if (isAvailable) {
                                 this.assignWorkitem((new Long(blsession.getID())).toString(), wiName);
                              }

                              this.completeWorkitem((new Long(blsession.getID())).toString(), wiName);
                              resObj.setResponseCode("5000");
                              resObj.setMessage("Proposal " + uwstatus + " Sucessfully");
                              var42 = resObj;
                              return var42;
                           }

                           resObj.setResponseCode("6008-Corporate Cannot have Forward and Complete, Please use Approve/Reject option");
                           var42 = resObj;
                           return var42;
                        }

                        if (uwstatus.equalsIgnoreCase("Rejected")) {
                           this.suspendProposalQuote(processInstanceName, userId);
                           resObj.setResponseCode("5000");
                           resObj.setMessage("Proposal " + uwstatus + " Sucessfully");
                           var42 = resObj;
                           return var42;
                        }

                        if (uwstatus.equalsIgnoreCase("onhold")) {
                           resObj.setResponseCode("5000");
                           resObj.setMessage("Proposal put on " + uwstatus + " Sucessfully");
                           var42 = resObj;
                           return var42;
                        }
                     }

                     if (!this.checkNull(uwstatus) && !isMotor) {
                        if (!uwstatus.equalsIgnoreCase("Approved") && !uwstatus.equalsIgnoreCase("Approve") && !uwstatus.toLowerCase().contains("accept")) {
                           if (!uwstatus.equalsIgnoreCase("Rejected") && !uwstatus.toLowerCase().contains("reject")) {
                              if (uwstatus.equalsIgnoreCase("Cancelled") || uwstatus.toLowerCase().contains("cancel")) {
                                 pi.remove();
                                 resObj.setMessage("Proposal Removed Sucessfully");
                                 resObj.setResponseCode("5000");
                                 var42 = resObj;
                                 return var42;
                              }

                              if (uwstatus.equalsIgnoreCase("onhold")) {
                                 resObj.setMessage("Proposal put on hold Sucessfully");
                                 resObj.setResponseCode("5000");
                                 var42 = resObj;
                                 return var42;
                              }
                           } else {
                              this.suspendProposalQuote(processInstanceName, userId);
                              resObj.setMessage("Proposal " + uwstatus + " Sucessfully");
                              resObj.setResponseCode("5000");
                           }
                        } else {
                           if (isAvailable) {
                              this.assignWorkitem((new Long(blsession.getID())).toString(), wiName);
                           }

                           this.completeWorkitem((new Long(blsession.getID())).toString(), wiName);
                           resObj.setMessage("Proposal " + uwstatus + " Sucessfully");
                           resObj.setResponseCode("5000");
                        }
                     }
                  } else {
                     resObj.setResponseCode("5030-Null or Empty value passed for userId code");
                  }
               } else if (this.checkNull(userId)) {
                  resObj.setResponseCode("5030-Null or Empty value passed for userId code");
               }

               obj = new WorkItemObject();
               obj.setStatus("Update or complete Workflow: " + reqObj.getStatus());
               obj.setRemarks("Response code : " + resObj.getResponseCode() + "  Message > " + resObj.getMessage());
               obj.setBlazeRemarks(reqObj.getBlazeRemarks());
               obj.setApprovalAuthority(reqObj.getApprovalAuthority());
               obj.setProposalNumber(proposalNumber);
               obj.setProcessInstanceName(processInstanceName);
               obj.setWorkStepName(workStepName);
               obj.setUserId(userId);
               obj.setQuoteNo(reqObj.getQuoteNo());
               this.addRemarks(obj);
            } catch (Exception var61) {
               resObj.setResponseCode("5050-Failure Exception" + var61.getMessage());
            }

            return resObj;
         } finally {
            try {
               this.disconnect((String)sessionId);
            } catch (Exception var60) {
               var60.printStackTrace();
            }

         }
      }
   }

   private boolean addRemarks(WorkItemObject obj) {
      boolean bln = false;
      DataSource ds1 = null;
      Connection conn = null;
      PreparedStatement ps = null;

      boolean var11;
      try {
         if (obj == null) {
            return bln;
         }

         ds1 = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
         conn = ds1.getConnection();
         String sqlString1 = "insert into MOTOR_IRPAS_FLOW_HISTORY values(?,?,?,?,?,?,?,?,?,?)";
         ps = conn.prepareStatement(sqlString1);
         String[] arr = obj.getProcessInstanceName().split("#");
         long piid = Long.parseLong(arr[1]);
         ps.setLong(1, piid);
         ps.setString(2, obj.getProposalNumber());
         ps.setString(3, obj.getUserId());
         ps.setString(4, obj.getWorkStepName());
         ps.setString(5, obj.getRemarks());
         ps.setTimestamp(6, new Timestamp((new Date()).getTime()));
         ps.setString(7, obj.getApprovalAuthority());
         ps.setString(8, obj.getBlazeRemarks());
         ps.setString(9, obj.getStatus());
         ps.setString(10, obj.getQuoteNo());
         ps.executeUpdate();
         bln = true;
         var11 = bln;
      } catch (Exception var25) {
         System.out.println("Error in adding remarks and status \n" + var25.getMessage());
         return bln;
      } finally {
         try {
            if (ps != null) {
               ps.close();
            }
         } catch (SQLException var24) {
         }

         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException var23) {
         }

      }

      return var11;
   }

   public String getHistory(String proposalNo) {
      String hist = null;
      StringBuffer sb = new StringBuffer();
      sb.append("<DATA>");
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         if (proposalNo != null && proposalNo.length() > 0) {
            String sql = "select * from MOTOR_IRPAS_FLOW_HISTORY where proposal_no=? or quote_no=? order by create_date";
            pstmt = ((Connection)conn).prepareStatement(sql);
            pstmt.setString(1, proposalNo);
            pstmt.setString(2, proposalNo);
            rs = pstmt.executeQuery();
            if (rs != null) {
               while(rs.next()) {
                  sb.append("<ROW>");
                  String user_id = rs.getString("USER_ID");
                  String workstep_name = rs.getString("WORKSTEP_NAME");
                  String create_date = rs.getString("CREATE_DATE");
                  String blaze_remark = rs.getString("BLAZEREMARK");
                  String remarks = rs.getString("REMARKS");
                  String status = rs.getString("status");
                  sb.append("</ROW>");
               }
            }
         }

         sb.append("</DATA>");
         hist = sb.toString();
      } catch (Exception var27) {
         throw new RuntimeException("Error in getting History details " + var27.getMessage(), var27);
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var26) {
            }
         }

         if (pstmt != null) {
            try {
               pstmt.close();
            } catch (SQLException var25) {
            }
         }

         if (pstmt != null) {
            try {
               ((Connection)conn).close();
            } catch (SQLException var24) {
            }
         }

      }

      return hist;
   }

   private String getUserPassword(String userId) {
      String password = "";

      try {
         User userObject = UserManager.getUser(userId);
         password = userObject.getAttribute("password");
         PService p = PService.self();
         password = p.decrypt(password);
      } catch (Exception var5) {
      }

      return password;
   }

   private void CUACTIVITY(String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("CUACTIVITY method invoked");
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   private void UWACTIVITY(String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("UWACTIVITY method invoked");
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   private void activateProcessInstance(String sessionId, String piName) throws AxisFault {
      try {
         getBizLogicManager().activateProcessInstance(sessionId, piName);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      } catch (PAKException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private void completeWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         getBizLogicManager().completeWorkitem(sessionId, wiName);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private static BizLogicManager getBizLogicManager() throws AxisFault {
      if (pak == null) {
         synchronized(bytearray) {
            if (pak == null) {
               try {
                  String appserver = ServiceLocator.self().getAppServerID();
                  BizLogicManagerHome blmhome = (BizLogicManagerHome)SBMHomeFactory.self().lookupHome(appserver, "BizLogicManager", BizLogicManagerHome.class);
                  pak = blmhome.create();
               } catch (Throwable var3) {
                  throw new AxisFault("SBM Web services error :" + var3.getMessage());
               }
            }
         }
      }

      return pak;
   }

   private String connect(String userId, String password) throws AxisFault {
      String sessionId = null;

      try {
         sessionId = getBizLogicManager().connect(userId, password);
         return sessionId;
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private String createProcessInstance(String sessionId, String ptName, String piName, String priority, Hashtable h) throws AxisFault {
      String pi = null;

      try {
         pi = getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
         return pi;
      } catch (RemoteException var8) {
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }
   }

   private void setProcessDataslotValues(String sessionId, String pName, Hashtable h) throws AxisFault {
      try {
         getBizLogicManager().setProcessDataslotValues(sessionId, pName, h);
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private boolean disconnect(String sessionId) {
      try {
         getBizLogicManager().disconnect(sessionId);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   private String[] getAssignedWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, false);
   }

   private String[] getAvailableWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, true);
   }

   private String getWorkitemDataslotValue(String sessionId, String wiName, String dsName) throws AxisFault {
      Object obj = null;

      try {
         obj = getBizLogicManager().getWorkitemDataslotValue(sessionId, wiName, dsName);
      } catch (RemoteException var6) {
         throw new AxisFault("SBM Web services error :" + var6.getMessage());
      } catch (PAKException var7) {
         throw new AxisFault("SBM Web services error :" + var7.getMessage());
      }

      return obj instanceof String ? (String)obj : "NST";
   }

   private boolean checkNull(Object dsValue) {
      return dsValue == null || dsValue instanceof String && (dsValue.equals("<NULL>") || dsValue.equals(""));
   }

   private Hashtable getDSValues(HashMap dsTypes, HashMap valueMap) {
      Hashtable resolvedValues = new Hashtable();
      if (valueMap != null && valueMap.size() != 0) {
         Iterator sIterator = dsTypes.entrySet().iterator();

         while(true) {
            while(sIterator.hasNext()) {
               Entry sEntry = (Entry)sIterator.next();
               String key = (String)sEntry.getKey();
               String type = (String)sEntry.getValue();
               Object dsvalue = valueMap.get(key);
               if (this.checkNull(dsvalue)) {
                  resolvedValues.put(key, "<NULL>");
               } else if (!type.equals("DOCUMENT")) {
                  String[] data;
                  if (type.equals("LIST")) {
                     data = (String[])dsvalue;
                     int size = data.length;
                     Vector v = new Vector(size);

                     for(int i = 0; i < size; ++i) {
                        v.add(data[i]);
                     }

                     resolvedValues.put(key, v);
                  } else if (type.equals("XML")) {
                     XML xml = new XML((String)dsvalue);
                     resolvedValues.put(key, xml);
                  } else if (type.equals("DECIMAL")) {
                     Decimal dec = new Decimal((BigDecimal)dsvalue);
                     resolvedValues.put(key, dec);
                  } else if (type.equals("DATETIME")) {
                     Calendar cal = (Calendar)dsvalue;
                     DateTime dt = new DateTime(cal.getTimeInMillis());
                     resolvedValues.put(key, dt);
                  } else if (!type.equals("MAP")) {
                     resolvedValues.put(key, dsvalue);
                  } else {
                     data = (String[])dsvalue;
                     Map hm = new HashMap();

                     for(int i = 0; i < data.length; ++i) {
                        StringTokenizer st = new StringTokenizer(data[i], "=");
                        hm.put(st.nextToken(), st.nextToken());
                     }

                     resolvedValues.put(key, hm);
                  }
               }
            }

            return resolvedValues;
         }
      } else {
         return resolvedValues;
      }
   }

   private String[] getWorkItemNames(String sessionId, boolean available) throws AxisFault {
      QueryService qs = null;
      QSWorkItemRS wirs = null;

      try {
         Session sess = getBizLogicManager().getSession(sessionId);
         QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
         qs = new QueryService(sess);
         QSWorkItem qswi = qs.getWorkItem();
         wifil.setFetchSize(400);
         if (available) {
            wirs = qswi.getAvailableList(wifil);
         } else {
            wirs = qswi.getAssignedList(wifil);
         }

         List wi = wirs.getSVOList();
         String[] winames = new String[wi.size()];

         for(int i = 0; i < wi.size(); ++i) {
            winames[i] = ((QSWorkItemData)wi.get(i)).getName();
         }

         String[] var12 = winames;
         return var12;
      } catch (Exception var19) {
         throw new AxisFault("SBM Web services error :" + var19.getMessage());
      } finally {
         try {
            wirs.close();
         } catch (Exception var18) {
         }

      }
   }

   private HashMap getMap(RequestObject[] reqObj) {
      HashMap hm = new HashMap();
      int arrayObjLength = reqObj.length;

      try {
         if (arrayObjLength != 0) {
            for(int i = 0; i < arrayObjLength; ++i) {
               String key = reqObj[i].getKey();
               if (key != null) {
                  Object value = null;
                  if (reqObj[i].getValue() != null) {
                     value = reqObj[i].getValue();
                  }

                  if (reqObj[i].getValueArray() != null) {
                     value = reqObj[i].getValueArray();
                  }

                  hm.put(key, value);
               }
            }
         }

         return hm;
      } catch (Exception var7) {
         throw new RuntimeException("SBM Web services error :" + var7.getMessage());
      }
   }
}
