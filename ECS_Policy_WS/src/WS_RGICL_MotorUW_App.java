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
import java.io.FileInputStream;
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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.axis.AxisFault;
import org.rgicl.motor.util.RequestObject;
import org.rgicl.motor.util.ResponseObject;
import org.rgicl.motor.util.WorkItemObject;

public class WS_RGICL_MotorUW_App {
   private static BizLogicManager pak = null;
   private String ptName = "RGICL_MotorUW_App";
   private static Byte[] bytearray = new Byte[0];
   private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
   private DataSource ds = null;
   private Connection connection = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs;
   private String sqlString = "SELECT BRANCH_TYPE FROM MOTORUW_BRANCH_REGION_MAP WHERE BRANCH_ID = ?";
   private CallableStatement proc_stmt = null;
   private static String dbuser = null;
   private static String dbpass = null;
   private static String dburl = null;

   static {
      try {
         Properties prop = new Properties();
         new FileInputStream(System.getProperty("sbm.home") + "/conf/" + "sbmdb.properties");
         dburl = prop.getProperty("sbm.db.oracle.url");
         dbuser = prop.getProperty("sbm.db.oracle.user");
         dbpass = PService.self().decrypt(prop.getProperty("sbm.db.oracle.password"));
         Class.forName(prop.getProperty("sbm.db.oracle.driver"));
      } catch (Exception var2) {
      }

   }

   public WS_RGICL_MotorUW_App() {
      NLog.ws.debug("RGICL_MotorUW_App service invoked");
   }

   private Connection getConnectionToSQLDB() throws Exception {
      NLog.ws.debug("RGICL_MotorUW_App getConnectionToSQLDB() method in");
      String dbip = "";
      String dbuser = "";
      String dbpass = "";

      try {
         String ip = InetAddress.getLocalHost().getHostAddress();
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         if (ip.contains("10.62.182.42")) {
            dbip = "RGIUDEVLOPMENT.reliancegeneral.com";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         } else {
            dbip = "RGIRMSCDDB";
            dbuser = "savvionapp";
            dbpass = "sav$123";
         }

         this.connection = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=SavvionDB", dbuser, dbpass);
      } catch (Exception var5) {
         throw new RuntimeException("Error in getting a DB connection  for db " + dbip + " \n" + var5.getMessage(), var5);
      }

      NLog.ws.debug("RGICL_MotorUW_App getConnectionToSQLDB() method out");
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
      NLog.ws.debug("RGICL_MotorUW_App getUnderWriterDetails(String BranchCode) method in:" + BranchCode);
      String UWGroup = "";
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         if (BranchCode != null) {
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            connection = this.ds.getConnection();
            pstmt = connection.prepareStatement(this.sqlString);
            if (BranchCode.contains("-")) {
               String[] arr = BranchCode.split("-");
               if (arr != null) {
                  BranchCode = arr[0];
               }
            }

            pstmt.setString(1, BranchCode);
            rs = pstmt.executeQuery();

            String UWGroup1;
            for(UWGroup1 = null; rs.next(); UWGroup1 = rs.getString("BRANCH_TYPE")) {
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (rs != null) {
               rs.close();
            }

            if (connection != null) {
               connection.close();
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
      } catch (Exception var26) {
         throw new RuntimeException();
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var25) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var24) {
         }

         try {
            if (connection != null) {
               connection.close();
            }
         } catch (SQLException var23) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getUnderWriterDetails(String BranchCode) method out:" + UWGroup);
      return UWGroup;
   }

   private boolean checkBSMLimit(String ApprovalAuth) {
      NLog.ws.debug("RGICL_MotorUW_App checkBSMLimit(String ApprovalAuth) method in:" + ApprovalAuth);
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

      NLog.ws.debug("RGICL_MotorUW_App checkBSMLimit(String ApprovalAuth) method out:" + String.valueOf(bln));
      return bln;
   }

   private String[] getGroupUsers(String groupName) {
      NLog.ws.debug("RGICL_MotorUW_App getGroupUsers(String groupName) method in:" + groupName);
      String[] users = null;

      try {
         if (groupName != null) {
            new UserManager();
            JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
            JDBCGroup g = (JDBCGroup)r.getGroup(groupName);
            if (g != null) {
               users = g.getAllUserMemberNames();
            } else {
               JDBCUser u1 = (JDBCUser)r.getUser(groupName);
               if (u1 != null) {
                  users = new String[]{u1.userName};
               }
            }
         }
      } catch (Exception var7) {
         System.out.println("RGICL_MotorUW_App Error in getting group Details " + var7.getMessage());
      }

      NLog.ws.debug("RGICL_MotorUW_App getGroupUsers(String groupName) method out");
      return users;
   }

   private String[] getQueueUsers(String user) {
      NLog.ws.debug("RGICL_MotorUW_App getQueueUsers(String user) method:" + user);
      String[] details = null;
      List list = new ArrayList();
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         if (user != null) {
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            connection = this.ds.getConnection();
            pstmt = connection.prepareStatement("SELECT q.MEMBER_NAME FROM QUEUE_RESOURCE q, SBM_QUEUE s WHERE q.QUEUE_ID=s.QUEUE_ID AND s.QUEUE_NAME=?");
            pstmt.setString(1, user);
            rs = pstmt.executeQuery();
            if (rs != null) {
               while(rs.next()) {
                  list.add(rs.getString("MEMBER_NAME"));
               }
            }

            details = (String[])list.toArray();
            if (pstmt != null) {
               pstmt.close();
            }

            if (rs != null) {
               rs.close();
            }

            if (connection != null) {
               connection.close();
            }
         }
      } catch (Exception var24) {
         System.out.println("RGICL_MotorUW_App Error in getting Queue User Details" + var24.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var23) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var22) {
         }

         try {
            if (connection != null) {
               connection.close();
            }
         } catch (SQLException var21) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getQueueUsers(String user) method out");
      return details;
   }

   private String getUserDetails(String user) {
      NLog.ws.debug("RGICL_MotorUW_App getUserDetails(String user) method:" + user);
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
                     String firstName = u.getAttribute("FIRSTNAME");
                     if (firstName == null) {
                        firstName = "";
                     }

                     String lastName = u.getAttribute("LASTNAME");
                     if (lastName == null) {
                        lastName = "";
                     }

                     details = details + u.userName + "-" + firstName + " " + lastName + " | ";
                  }
               }
            } else {
               JDBCUser u1 = (JDBCUser)r.getUser(user);
               if (u1 != null) {
                  String firstName = u1.getAttribute("FIRSTNAME");
                  if (firstName == null) {
                     firstName = "";
                  }

                  String lastName = u1.getAttribute("LASTNAME");
                  if (lastName == null) {
                     lastName = "";
                  }

                  details = u1.userName + "-" + firstName + " " + lastName;
               } else {
                  details = user;
               }
            }
         }
      } catch (Exception var11) {
         System.out.println("RGICL_MotorUW_App Error in getting User Details " + var11.getMessage());
      }

      NLog.ws.debug("RGICL_MotorUW_App getUserDetails(String user) method out:" + details);
      return details;
   }

   private String getQueueUserDetails(String user) {
      NLog.ws.debug("RGICL_MotorUW_App getQueueUserDetails(String user) method:" + user);
      String details = "";
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         if (user != null && user.length() > 0) {
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            connection = this.ds.getConnection();
            pstmt = connection.prepareStatement("SELECT q.MEMBER_NAME FROM QUEUE_RESOURCE q, SBM_QUEUE s WHERE q.QUEUE_ID=s.QUEUE_ID AND s.QUEUE_NAME=?");
            pstmt.setString(1, user);
            rs = pstmt.executeQuery();
            if (rs != null) {
               while(rs.next()) {
                  details = details + " | " + this.getUserDetails(rs.getString("MEMBER_NAME"));
               }
            }
         }

         if (details != null && details.length() < 1) {
            details = this.getUserDetails(user);
         }

         if (pstmt != null) {
            pstmt.close();
         }

         if (rs != null) {
            rs.close();
         }

         if (connection != null) {
            connection.close();
         }
      } catch (Exception var23) {
         System.out.println("RGICL_MotorUW_App Error in getting Queue User Details" + var23.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var22) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var21) {
         }

         try {
            if (connection != null) {
               connection.close();
            }
         } catch (SQLException var20) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getQueueUserDetails(String user) method out:" + details);
      return details;
   }

   private String START(HashMap dsValues, String username, String password, String piName, String priority) throws AxisFault {
      NLog.ws.debug("RGICL_MotorUW_App START(...) method in:username-" + username + ":piName-" + piName);
      String pi = "";
      try{
    	  String sessionId = this.connect(username, password);
          HashMap<String, String> dsTypeMap = new HashMap();
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
          dsTypeMap.put("QuoteNo", "STRING");
          dsTypeMap.put("IRPAS_MARINE_SCUW_QUEUE", "STRING");
          dsTypeMap.put("BusinessType", "STRING");
          dsTypeMap.put("EndorsementQueue", "STRING");
          Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
          String createdBy = (String) resolvedDSValues.get("CreatedBy");
          System.out.println("userId----"+createdBy);
          NLog.ws.debug("userId----"+createdBy);
          if(createdBy != "100002" || !createdBy.equalsIgnoreCase("100002")){
        	  pi = this.createProcessInstance(sessionId, this.ptName, piName, priority, resolvedDSValues);
        	  NLog.ws.debug("RGICL_MotorUW_App createProcessInstance(sessionId, ptName, piName, priority,\tresolvedDSValues) method:pi-" + pi);
          }else{        	  
        	  System.out.println("Instance creation is blocked as USER ID is: "+createdBy);
        	  NLog.ws.debug("Instance creation is blocked for USER ID :::: " + createdBy);
          }
          
          this.disconnect(sessionId);
      }catch(Exception ex){
    	  System.out.println("Exception while creating IRPAS FLOW INSTANCE:: "+ex.getMessage());
      }      
      NLog.ws.debug("RGICL_MotorUW_App START(...) method out:" + pi);
      return pi;
   }

   private String getLobDetails(String ProductCode, ResponseObject resObj) {
      NLog.ws.debug("RGICL_MotorUW_App getLobDetails(String ProductCode, ResponseObject resObj) method in:");
      String LOB = "";
      Connection connection = null;
      CallableStatement proc_stmt = null;
      ResultSet rs = null;

      try {
         if (ProductCode != null && ProductCode.length() > 0) {
            if (ProductCode.contains("-")) {
               String[] arr = ProductCode.split("-");
               connection = this.getConnectionToSQLDB();
               proc_stmt = connection.prepareCall("{ call usp_Get_LOB_Details(?) }");
               proc_stmt.setString(1, arr[0]);
               rs = proc_stmt.executeQuery();
               if (rs != null) {
                  while(rs.next()) {
                     LOB = rs.getString("LOB");
                     if (LOB != null) {
                        LOB = LOB.toUpperCase();
                     }
                  }
               }
               if (proc_stmt != null) {
                  proc_stmt.close();
               }

               if (rs != null) {
                  rs.close();
               }

               if (connection != null) {
                  connection.close();
               }
               
            } else {
               resObj.setResponseCode("6005-Product code should be in the format ProductCode-ProductName format");
               resObj.setMessage("Product Code Format Error, Must be Product Code-Product Name");
            }
         }
      } catch (Exception var28) {
    	  NLog.ws.debug("RGICL_MotorUW_App Error in getting LOB Details" + var28.toString());
      } finally {
         try {
            if (proc_stmt != null) {
               try {
                  proc_stmt.close();
               } catch (Exception var26) {
               }
            }

            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var25) {
               }
            }

            if (connection != null) {
               try {
                  connection.close();
               } catch (Exception var24) {
               }
            }
         } catch (Exception var27) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getLobDetails(String ProductCode, ResponseObject resObj) method out:" + LOB);
      return LOB;
   }

   private String[] getEmailIds(String users) {
      NLog.ws.debug("RGICL_MotorUW_App getEmailIds(String users) method:" + users);
      String[] emailIds = null;
      List list = new ArrayList();
      Connection connection = null;
      CallableStatement proc_stmt = null;
      ResultSet rs = null;

      try {
         if (users != null && users.length() > 0) {
            connection = this.getConnectionToSQLDB();
            proc_stmt = connection.prepareCall("{ call usp_GetEmployeeDetailsBulk(?) }");
            proc_stmt.setString(1, users);
            rs = proc_stmt.executeQuery();
            if (rs != null) {
               while(rs.next()) {
                  list.add(rs.getString("EMAIL"));
               }
            }

            emailIds = (String[])list.toArray();
            if (proc_stmt != null) {
               proc_stmt.close();
            }

            if (rs != null) {
               rs.close();
            }

            if (connection != null) {
               connection.close();
            }

            String[] var9 = emailIds;
            return var9;
         }
      } catch (Exception var33) {
         System.out.println("RGICL_MotorUW_App Error in getting LOB Details" + var33.toString());
      } finally {
         try {
            if (proc_stmt != null) {
               try {
                  proc_stmt.close();
               } catch (Exception var31) {
               }
            }

            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var30) {
               }
            }

            if (connection != null) {
               try {
                  connection.close();
               } catch (Exception var29) {
               }
            }
         } catch (Exception var32) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getEmailIds(String users) method out:");
      return emailIds;
   }

   private String getQueueDetails(String ProductCode, String BranchCode, String ApprovalAuthority, String BusinessType, ResponseObject resObj) {
      NLog.ws.debug("RGICL_MotorUW_App getQueueDetails method in:");
      String QueueName = "";
      Connection connection = null;
      CallableStatement proc_stmt = null;
      ResultSet rs = null;

      try {
         if (ProductCode != null && ProductCode.length() > 0) {
            if (ProductCode.contains("-")) {
               String[] arr = ProductCode.split("-");
               String[] arr1 = BranchCode.split("-");
               connection = this.getConnectionToSQLDB();
               proc_stmt = connection.prepareCall("{ call usp_GetQueueDetails(?,?,?,?) }");
               proc_stmt.setString(1, arr[0]);
               proc_stmt.setString(2, arr1[0]);
               proc_stmt.setString(3, ApprovalAuthority);
               proc_stmt.setString(4, BusinessType);
               rs = proc_stmt.executeQuery();
               if (rs != null) {
                  while(rs.next()) {
                     QueueName = rs.getString("QUEUE_NAME");
                  }
               }

               if (proc_stmt != null) {
                  proc_stmt.close();
               }

               if (rs != null) {
                  rs.close();
               }

               if (connection != null) {
                  connection.close();
               }
            } else {
               resObj.setResponseCode("5030-Null or Empty value passed for userId code");
               resObj.setMessage("Inccorect Approver ID List");
            }
         }
      } catch (Exception var32) {
         System.out.println("RGICL_MotorUW_App Error while getting queue details" + var32.toString());
      } finally {
         try {
            if (proc_stmt != null) {
               try {
                  proc_stmt.close();
               } catch (Exception var30) {
               }
            }

            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var29) {
               }
            }

            if (connection != null) {
               try {
                  connection.close();
               } catch (Exception var28) {
               }
            }
         } catch (Exception var31) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getQueueDetails method out:" + QueueName);
      return QueueName;
   }

   public ResponseObject initiateMotorIRPASFlow(WorkItemObject reqObj) throws AxisFault {
      NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) method in:");
      new WorkItemObject();
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
      String travelQueue = null;
      String packageQueue = null;
      String miscQueue = null;
      String wcQueue = null;
      String grpMembers = null;
      String emailIds = null;
      String marineQueue = null;
      String BusinessType = null;
      String EndorsementQueue = null;

      WorkItemObject obj;
      try {
         obj = new WorkItemObject();
         obj.setStatus(reqObj.getStatus());
         obj.setRemarks("Initiate Method Called");
         obj.setBlazeRemarks("");
         obj.setApprovalAuthority(reqObj.getApprovalAuthority());
         obj.setProposalNumber(reqObj.getProposalNumber());
         obj.setProcessInstanceName("dummy#124");
         obj.setWorkStepName("");
         obj.setUserId(reqObj.getUserId());
         obj.setQuoteNo(reqObj.getQuoteNo());
         this.addRemarks(obj);
         ResponseObject var36;
         if (reqObj == null) {
            resObj.setResponseCode("5070");
            NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) method out: reqObject = null");
            var36 = resObj;
            return var36;
         }

         proposalNumber = reqObj.getProposalNumber();
         if (proposalNumber != null && this.checkProposalExists(proposalNumber)) {
            int pistat = this.checkProposalStatus(proposalNumber, true);
            if (pistat == 8) {
               resObj.setResponseCode("5000");
               resObj.setMessage("Previously Rejected Proposal has been referred to UW");
               NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) method: resObj:" + proposalNumber + " SUCCESS,Previously Rejected Proposal has been referred to UW");
            } else if (pistat == 7) {
               resObj.setResponseCode("5000");
               resObj.setMessage("Case Already in Approvers bucket for Approval, still initiate method called");
               NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) method: resObj:" + proposalNumber + " SUCCESS,Case Already in Approvers bucket for Approval, still initiate method called");
            }

            NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj)" + proposalNumber + " method out");
            var36 = resObj;
            return var36;
         }

         BranchCode = reqObj.getBranchCode();
         if (this.checkNull(BranchCode)) {
            resObj.setResponseCode("6002-Null or Empty value passed for branch code");
            resObj.setMessage("Branch Code is Null Or Empty");
            NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":Branch Code is Null Or Empty");
            var36 = resObj;
            return var36;
         }

         userId = reqObj.getUserId();
         if (this.checkNull(userId)) {
            resObj.setResponseCode("5030-Null or Empty value passed for userId code");
            resObj.setMessage("User Id is null or invalid");
            NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":User Id is null or invalid");
            var36 = resObj;
            return var36;
         }

         ApprovalAuthority = reqObj.getApprovalAuthority();
         if (this.checkNull(ApprovalAuthority)) {
            resObj.setResponseCode("6003-Null or Empty value passed for ApprovalAuthority code");
            resObj.setMessage("Approval Authority is Null Or Blank");
            NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":Approval Authority is Null Or Blank");
            var36 = resObj;
            return var36;
         }

         AgentCode = reqObj.getAgentCode();
         Channel = reqObj.getChannel();
         BASCode = reqObj.getBASCode();
         ProductCode = reqObj.getProductCode();
         BusinessType = reqObj.getBusinessType();
         LOB = this.getLobDetails(ProductCode, resObj);
         if (LOB == "" || LOB.length() <= 1) {
            NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":No LOB");
            var36 = resObj;
            return var36;
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
         dsValues.put("QuoteNo", QuoteNo);
         if (BusinessType == null) {
            BusinessType = "Policy";
         }

         dsValues.put("BusinessType", BusinessType);
         String HealthProcess = reqObj.getProcess();
         if (!BusinessType.equalsIgnoreCase("Endorsement")) {
            if (LOB.equalsIgnoreCase("package")) {
               packageQueue = "Irpas_PackageQueue";
               processInstanceName = "PACKAGE_" + proposalNumber + "_CASE";
               packageQueue = this.getQueueDetails(ProductCode, BranchCode, ApprovalAuthority, BusinessType, resObj);
               if (packageQueue == "" || packageQueue.length() <= 1) {
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":Not endorsement, is package: no queue");
                  var36 = resObj;
                  return var36;
               }

               dsValues.put("PackageQueue", packageQueue);
            }

            if (LOB.equalsIgnoreCase("misc")) {
               miscQueue = "Irpas_Misc_UMQueue";
               processInstanceName = "MISC_" + proposalNumber + "_CASE";
               miscQueue = this.getQueueDetails(ProductCode, BranchCode, ApprovalAuthority, BusinessType, resObj);
               if (miscQueue == "" || miscQueue.length() <= 1) {
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":Not endorsement, is misc: no queue");
                  var36 = resObj;
                  return var36;
               }

               dsValues.put("MiscQueue", miscQueue);
            }

            if (LOB.equalsIgnoreCase("WC")) {
               wcQueue = "IRPAS_WC_ZUM";
               processInstanceName = "WC_" + proposalNumber + "_CASE";
               wcQueue = this.getQueueDetails(ProductCode, BranchCode, ApprovalAuthority, BusinessType, resObj);
               if (wcQueue == "" || wcQueue.length() <= 1) {
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":Not endorsement, is wc: no queue");
                  var36 = resObj;
                  return var36;
               }

               dsValues.put("WCQueue", wcQueue);
            }

            if (LOB.equalsIgnoreCase("MARINE")) {
               processInstanceName = "MARINE_" + proposalNumber + "_CASE";
               marineQueue = this.getQueueDetails(ProductCode, BranchCode, ApprovalAuthority, BusinessType, resObj);
               if (marineQueue == "" || marineQueue.length() <= 1) {
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":Not endorsement, is marine: no queue");
                  var36 = resObj;
                  return var36;
               }

               dsValues.put("IRPAS_MARINE_SCUW_QUEUE", marineQueue);
            }

            if (LOB.equalsIgnoreCase("travel")) {
               processInstanceName = "TRAVEL_" + proposalNumber + "_CASE";
               dsValues.put("TravelQueue", "Irpas_TravelQueue");
               travelQueue = "Irpas_TravelQueue";
            }

            if (LOB.equalsIgnoreCase("PERSONAL ACCIDENT")) {
               processInstanceName = "PA_" + proposalNumber + "_CASE";
               if (ApprovalAuthority.equalsIgnoreCase("33")) {
                  dsValues.put("PA_UW_Queue", "PA_CUW_Queue");
                  PAQueue = "PA_CUW_Queue";
               } else {
                  dsValues.put("PA_UW_Queue", "PA_ZUW_Queue");
                  PAQueue = "PA_ZUW_Queue";
               }
            }

            if (LOB.equalsIgnoreCase("HEALTH")) {
               processInstanceName = "HEALTH_" + proposalNumber + "_CASE";
               dsValues.put("HealthProcess", HealthProcess);
               if (!this.checkNull(HealthProcess)) {
                  if (HealthProcess.equalsIgnoreCase("2")) {
                     HealthUW = "IRPAS_Health_TeleUnderwriter";
                     dsValues.put("CallStatus", "Pending with Health Tele.UW");
                     dsValues.put("HealthUW", HealthUW);
                  }

                  if (HealthProcess.equalsIgnoreCase("1") || HealthProcess.equalsIgnoreCase("3") || HealthProcess.equalsIgnoreCase("4") || HealthProcess.equalsIgnoreCase("5")) {
                     HealthUW = "IRPAS_Corporate_Health_UW";
                     dsValues.put("HealthUW", HealthUW);
                     if (!HealthProcess.equalsIgnoreCase("1")) {
                        dsValues.put("CallStatus", "Pending with Health Co.UW");
                     } else {
                        dsValues.put("CallStatus", "Pending with TPA");
                     }
                  }
                  
                  if (HealthProcess.equalsIgnoreCase("6")) {
                      HealthUW = "IRPAS_Health_SC_UW";
                      dsValues.put("CallStatus", "Pending with Health SC UW");
                      dsValues.put("HealthUW", HealthUW);
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
               UWGroup = this.getQueueDetails(ProductCode, BranchCode, ApprovalAuthority, BusinessType, resObj);
               dsValues.put("UWGroup", UWGroup);
            }
         } else if (BusinessType != null && BusinessType.equalsIgnoreCase("Endorsement")) {
            processInstanceName = "END_" + proposalNumber + "_CASE";
            EndorsementQueue = this.getQueueDetails(ProductCode, BranchCode, ApprovalAuthority, BusinessType, resObj);
            if (EndorsementQueue == null) {
               NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow(WorkItemObject reqObj) out:" + proposalNumber + ":endorsement: no queue");
               var36 = resObj;
               return var36;
            }

            dsValues.put("EndorsementQueue", EndorsementQueue);
         }

         String priority = "medium";
         processInstanceName = this.START(dsValues, "rgicl", "rgicl", processInstanceName, priority);
         resObj.setResponseCode("5000");
         ls.add(processInstanceName);
         String[] resultArray = new String[ls.size()];

         for(int j = 0; j < ls.size(); ++j) {
            resultArray[j] = (String)ls.get(j);
         }

         resObj.setResultStringArray(resultArray);
         if (resObj.getResponseCode().equalsIgnoreCase("5000")) {
            if (BusinessType != null && !BusinessType.equalsIgnoreCase("Endorsement")) {
               if (LOB.equalsIgnoreCase("MOTOR")) {
                  resObj.setMessage("Sent To Approvers " + this.getUserDetails(UWGroup) + " For Approval");
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: sent to approvers .. for approval");
               } else if (LOB.equalsIgnoreCase("HEALTH")) {
                  HealthProcess = reqObj.getProcess();
                  if (HealthProcess != null && HealthProcess.equals("1")) {
                     resObj.setMessage("Case is Pending with TPA and has been also sent to approvers " + this.getQueueUserDetails(HealthUW) + " For Approval");
                     NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: Case is Pending with TPA and has been also sent to approvers ");
                  } else {
                     resObj.setMessage("Sent To Approvers" + this.getQueueUserDetails(HealthUW) + " For Approval");
                     NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: Sent To Approvers");
                  }
               } else if (LOB.equalsIgnoreCase("PERSONAL ACCIDENT")) {
                  resObj.setMessage("Sent To Approvers " + this.getQueueUserDetails(PAQueue) + " For Approval");
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: Sent To Approvers");
               } else if (LOB.equalsIgnoreCase("travel")) {
                  resObj.setMessage("Sent To Approvers " + this.getQueueUserDetails(travelQueue) + " For Approval");
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: Sent To Approvers");
               } else if (LOB.equalsIgnoreCase("package")) {
                  resObj.setMessage("Sent To Approvers " + this.getQueueUserDetails(packageQueue) + " For Approval");
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: Sent To Approvers");
               } else if (LOB.equalsIgnoreCase("misc")) {
                  resObj.setMessage("Sent To Approvers " + this.getQueueUserDetails(miscQueue) + " For Approval");
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: Sent To Approvers");
               } else if (LOB.equalsIgnoreCase("WC")) {
                  resObj.setMessage("Sent To Approvers " + this.getQueueUserDetails(wcQueue) + " For Approval");
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: Sent To Approvers");
               } else if (LOB.equalsIgnoreCase("MARINE")) {
                  resObj.setMessage("Sent To Approvers " + this.getQueueUserDetails(marineQueue) + " For Approval");
                  NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":not endorsement: setting response: Sent To Approvers");
               }
            } else if (BusinessType != null && BusinessType.equalsIgnoreCase("Endorsement")) {
               resObj.setMessage("Sent To Approvers For Approval");
               NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow:" + proposalNumber + ":endorsement: setting response: Sent To Approvers");
            }
         }
      } catch (Exception var55) {
         resObj.setResponseCode("5050-Failure Exception" + var55.getMessage());
         var55.printStackTrace();
      } finally {
         try {
            obj = new WorkItemObject();
            obj.setStatus("Initiate Workflow");
            if (BusinessType.equalsIgnoreCase("Endorsement")) {
               obj.setRemarks("Response code : " + resObj.getResponseCode() + "  Message > " + "Sent To Approvers For Approval " + this.getQueueUserDetails(EndorsementQueue) + "strArry[0] : " + processInstanceName);
               NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow finally:" + proposalNumber + ":endorsement: Initiated Workflow:");
            } else {
               obj.setRemarks("Response code : " + resObj.getResponseCode() + "  Message > " + resObj.getMessage() + "strArry[0] : " + processInstanceName);
               NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow finally:" + proposalNumber + ":not endorsement: Initiated Workflow:");
            }

            obj.setBlazeRemarks(reqObj.getBlazeRemarks());
            obj.setApprovalAuthority(reqObj.getApprovalAuthority());
            obj.setProposalNumber(proposalNumber);
            obj.setProcessInstanceName(processInstanceName);
            obj.setWorkStepName("BSM");
            obj.setUserId(userId);
            obj.setQuoteNo(reqObj.getQuoteNo());
            this.addRemarks(obj);
         } catch (Exception var54) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App initiateMotorIRPASFlow out method exit:");
      return resObj;
   }

   public ResponseObject getIRPASMotorTaskList(WorkItemObject reqObj) throws AxisFault {
      NLog.ws.debug("RGICL_MotorUW_App getIRPASMotorTaskList(WorkItemObject reqObj) in");
      ResponseObject resObj = new ResponseObject();
      DataSource ds1 = null;
      Connection conn = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      String responseCode = null;
      ArrayList resultTaskList = new ArrayList();

      try {
         WorkItemObject[] workItemObject = null;
         QueryService qs = null;
         String sessionId = null;
         Session sess = null;
         if (reqObj == null) {
            resObj.setResponseCode("5070");
            NLog.ws.debug("RGICL_MotorUW_App getIRPASMotorTaskList(WorkItemObject reqObj): requestObj null");
         } else {
            String userId = reqObj.getUserId();
            if (this.checkNull(userId)) {
               responseCode = "5001";
               NLog.ws.debug("RGICL_MotorUW_App getIRPASMotorTaskList(WorkItemObject reqObj): userid null");
            } else {
               WorkItemObject workitem = null;
               String sqlQuery = "SELECT MAPP.PROPOSALNUMBER,MAPP.CALLSTATUS,WI.START_TIME FROM RGICL_MOTORUW_APP MAPP,PROCESSINSTANCE PI,WORKITEM WI WHERE PI.PROCESS_TEMPLATE_ID = 8758 AND PI.PROCESS_INSTANCE_ID = MAPP.PROCESS_INSTANCE_ID AND WI.PROCESS_INSTANCE_ID = PI.PROCESS_INSTANCE_ID AND WI.PERFORMER IN (SELECT GROUP_NAME FROM UMGROUP WHERE GROUP_ID IN (SELECT GROUP_ID FROM GROUPUSERROLE WHERE USER_ID = (SELECT USER_ID FROM UMUSER WHERE USER_NAME = ?)) UNION SELECT QUEUE_NAME FROM SBM_QUEUE WHERE QUEUE_ID IN (SELECT QUEUE_ID FROM QUEUE_RESOURCE WHERE MEMBER_NAME = ?) UNION SELECT ? FROM DUAL) AND PI.STATUS = 'PI_ACTIVATED' AND WI.STATUS IN ('I_ASSIGNED','I_AVAILABLE') AND PI.PROCESS_INSTANCE_ID NOT IN (SELECT MAPPP.PROCESS_INSTANCE_ID FROM RGICL_MOTORUW_APP MAPPP WHERE ((SELECT TRUNC(MAX(CREATE_DATE)) FROM MOTOR_IRPAS_FLOW_HISTORY WHERE PROPOSAL_NO = MAPPP.PROPOSALNUMBER) <= (CURRENT_DATE - 30)) AND MAPPP.PRODUCTCODE IN (SELECT PRODUCT FROM IRPAS_COMMERCIAL_Q_PRODUCTS))";
               Context jndiCntx = new InitialContext();
               ds1 = (DataSource)jndiCntx.lookup("jdbc/SBMCommonDB");
               conn = ds1.getConnection();
               preparedStatement = conn.prepareStatement(sqlQuery);
               preparedStatement.setString(1, userId);
               preparedStatement.setString(2, userId);
               preparedStatement.setString(3, userId);
               resultSet = preparedStatement.executeQuery();
               SBMCalendar.init((Properties)null);
               SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR", conn);
               long l = 0L;
               long l1 = 0L;
               long l2 = 0L;
               if (resultSet != null) {
                  while(resultSet.next()) {
                     workitem = new WorkItemObject();
                     workitem.setProposalNumber(resultSet.getString(1));
                     workitem.setStatus(resultSet.getString(2));
                     l = resultSet.getTimestamp(3).getTime();
                     l1 = (new Date()).getTime();
                     l2 = (long)Math.round((float)(sbmcal.getDuration(l, l1) / 3600000L));
                     workitem.setTAT((new Long(l2)).toString() + " Hours");
                     resultTaskList.add(workitem);
                  }
               }

               responseCode = "5000";
               if (resultTaskList != null && resultTaskList.size() != 0) {
                  workItemObject = this.getResultWorkitems(resultTaskList);
                  resObj.setResultworkItemArray(workItemObject);
                  responseCode = "5000";
                  resObj.setResponseCode(responseCode);
                  resObj.setMessage("Inbox Loaded with tickets successfully " + resultTaskList.size());
                  NLog.ws.debug("RGICL_MotorUW_App getIRPASMotorTaskList(WorkItemObject reqObj):" + userId + ": INbox loaded with tickets" + resultTaskList.size());
               } else {
                  responseCode = "5042";
                  NLog.ws.debug("RGICL_MotorUW_App getIRPASMotorTaskList(WorkItemObject reqObj): INBOX empty");
               }

               if (resultSet != null) {
                  resultSet.close();
               }

               if (preparedStatement != null) {
                  preparedStatement.close();
               }

               if (conn != null) {
                  conn.close();
               }
            }
         }
      } catch (Exception var32) {
         System.out.println("RGICL_MotorUW_App getIRPASMotorTaskList() -  Error occured: " + var32.getMessage());
         resObj.setResponseCode(responseCode);
         resObj.setMessage("Error loading inbox 5050-Failure Exception" + var32.getMessage());
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception var31) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getIRPASMotorTaskList(WorkItemObject reqObj) out");
      return resObj;
   }

   private WorkItemObject[] getResultWorkitems(ArrayList workitemlist) {
      NLog.ws.debug("RGICL_MotorUW_App getResultWorkitems(ArrayList workitemlist) in-out");
      WorkItemObject[] resultWorkitems = null;
      resultWorkitems = new WorkItemObject[workitemlist.size()];
      workitemlist.toArray(resultWorkitems);
      return resultWorkitems;
   }

   private void assignWorkitem(String sessionId, String wiName) throws AxisFault {
      NLog.ws.debug("RGICL_MotorUW_App assignWorkitem(String sessionId, String wiName) in-out");

      try {
         PAKClientWorkitem pwi = getBizLogicManager().getWorkitem(sessionId, wiName);
         getBizLogicManager().assignWorkitemPerformer(sessionId, pwi);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private String suspendProposalQuote(String processInstanceName, String userId) throws AxisFault {
      NLog.ws.debug("RGICL_MotorUW_App suspendProposalQuote(String processInstanceName,String userId) method:" + processInstanceName + ":" + userId);
      String status = null;

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
            NLog.ws.debug("RGICL_MotorUW_App " + processInstanceName + ":pi.suspended():Sent again To BSM");
            status = "Success";
         }

         return status;
      } catch (Exception var11) {
         throw new RuntimeException(var11);
      }
   }

   private boolean resumeProposalQuote(String processInstanceName, String userId) throws AxisFault {
      NLog.ws.debug("RGICL_MotorUW_App resumeProposalQuote(String processInstanceName,String userId) in:" + processInstanceName + ":" + userId);
      String status = "";
      boolean isResumed = false;

      try {
         if (processInstanceName != null && userId != null) {
            BLServer blserver = BLClientUtil.getBizLogicServer();
            Session blsession = blserver.connect("rgicl", "rgicl");
            ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
            if (pi.isSuspended()) {
               NLog.ws.debug("RGICL_MotorUW_App processInstanceName:" + processInstanceName + " isSuspended");
               pi.resume();
               pi.save();
               pi.refresh();
               NLog.ws.debug("RGICL_MotorUW_App processInstanceName:" + processInstanceName + " resumed");
               HashMap hm = new HashMap();
               hm.put("SuspendStatus", "Resumed");
               Date date2 = new Date();
               SimpleDateFormat Currdateformat2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
               String dt = Currdateformat2.format(date2);
               hm.put("SuspendDate", dt);
               hm.put("CallStatus", "Sent again To UW");
               NLog.ws.debug("RGICL_MotorUW_App processInstanceName:" + processInstanceName + " Sent again To UW");
               hm.put("UWStatus", "<NULL>");
               hm.put("CUStatus", "<NULL>");
               pi.updateSlotValue(hm);
               pi.save();
               status = "Instance Resumed Sucessfully";
               isResumed = true;
            }
         }
      } catch (Exception var12) {
         throw new RuntimeException(var12);
      }

      NLog.ws.debug("RGICL_MotorUW_App resumeProposalQuote(String processInstanceName,String userId)out:" + processInstanceName + ":" + userId);
      return isResumed;
   }

   public boolean checkProposalExists(String proposalNumber) {
      NLog.ws.debug("RGICL_MotorUW_App checkProposalExists(proposalNumber) in:" + proposalNumber);
      boolean bln = false;
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         blserver.disConnect(blsession);
         this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
         connection = this.ds.getConnection();
         pstmt = connection.prepareStatement("Select count(*) as COUNT from BIZLOGIC_DS_" + String.valueOf(ptid) + " where proposalNumber=?");
         pstmt.setString(1, proposalNumber);
         NLog.ws.debug("RGICL_MotorUW_App checking proposal in Table BIZLOGIC_DS_" + String.valueOf(ptid) + "proposal:" + proposalNumber);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            int count = rs.getInt("COUNT");
            if (count > 0) {
               bln = true;
               NLog.ws.debug("RGICL_MotorUW_App checking proposal out:proposal:" + proposalNumber + ":" + bln);
               boolean var12 = bln;
               return var12;
            }
         }

         if (pstmt != null) {
            pstmt.close();
         }

         if (rs != null) {
            rs.close();
         }

         if (connection != null) {
            connection.close();
         }
      } catch (Exception var31) {
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var30) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var29) {
         }

         try {
            if (connection != null) {
               connection.close();
            }
         } catch (SQLException var28) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App checking proposal out:proposal:" + proposalNumber + ":" + bln);
      return bln;
   }

   private long getProposalProcessInstanceId(String proposalNumber) {
      NLog.ws.debug("RGICL_MotorUW_App getProposalProcessInstanceId(String proposalNumber) in:proposal:" + proposalNumber);
      long piid = 0L;
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
         connection = this.ds.getConnection();
         pstmt = connection.prepareStatement("Select MAX(PROCESS_INSTANCE_ID) as PROCESS_INSTANCE_ID from BIZLOGIC_DS_" + String.valueOf(ptid) + " where proposalNumber=?");
         NLog.ws.debug("RGICL_MotorUW_App getProposalProcessInstanceId(String proposalNumber) checking MAX PIID in BIZLOGIC_DS_:proposal:" + proposalNumber);
         pstmt.setString(1, proposalNumber);

         for(rs = pstmt.executeQuery(); rs.next(); piid = rs.getLong("PROCESS_INSTANCE_ID")) {
         }

         blserver.disConnect(blsession);
         if (pstmt != null) {
            pstmt.close();
         }

         if (rs != null) {
            rs.close();
         }

         if (connection != null) {
            connection.close();
         }
      } catch (Exception var24) {
         throw new RuntimeException("Error in getProposalProcessInstanceId" + var24.getMessage(), var24);
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var23) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var22) {
         }

         try {
            if (connection != null) {
               connection.close();
            }
         } catch (SQLException var21) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getProposalProcessInstanceId(String proposalNumber) checking MAX PIID in BIZLOGIC_DS_ out:proposal:" + proposalNumber + ":" + piid);
      return piid;
   }

   private int checkProposalStatus(String proposalNumber, boolean doResume) {
      NLog.ws.debug("RGICL_MotorUW_App checkProposalStatus(String proposalNumber, boolean doResume) in:" + proposalNumber + ":" + doResume);
      int status = 0;
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
         connection = this.ds.getConnection();
         pstmt = connection.prepareStatement("Select MAX(PROCESS_INSTANCE_ID) as PROCESS_INSTANCE_ID from BIZLOGIC_DS_" + String.valueOf(ptid) + " where proposalNumber=?");
         pstmt.setString(1, proposalNumber);
         rs = pstmt.executeQuery();

         long piid;
         for(piid = 0L; rs.next(); piid = rs.getLong("PROCESS_INSTANCE_ID")) {
         }

         if (piid > 0L) {
            pstmt = connection.prepareStatement("Select STATUS FROM BIZLOGIC_PROCESSINSTANCE where PROCESS_INSTANCE_ID=?");
            pstmt.setLong(1, piid);
            rs = pstmt.executeQuery();

            while(rs.next()) {
               status = rs.getInt("STATUS");
               if (status == 8) {
                  ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
                  if (doResume) {
                     this.resumeProposalQuote(pi.getName(), "rgicl");
                     NLog.ws.debug("RGICL_MotorUW_App checkProposalStatus-> resuming pi out:" + proposalNumber + ":" + status);
                  }

                  NLog.ws.debug("RGICL_MotorUW_App checkProposalStatus(String proposalNumber, boolean doResume) out:" + proposalNumber + ":" + status);
                  int var15 = status;
                  return var15;
               }
            }
         }

         blserver.disConnect(blsession);
         if (pstmt != null) {
            pstmt.close();
         }

         if (rs != null) {
            rs.close();
         }

         if (connection != null) {
            connection.close();
         }
      } catch (Exception var31) {
         throw new RuntimeException("Error in checkProposalStatus" + var31.getMessage(), var31);
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var30) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var29) {
         }

         try {
            if (connection != null) {
               connection.close();
            }
         } catch (SQLException var28) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App checkProposalStatus(String proposalNumber, boolean doResume) out:" + String.valueOf(status));
      return status;
   }

   public String deleteProposalQuoteFromSavvion(String proposalNumber) {
      NLog.ws.debug("RGICL_MotorUW_App deleteProposalQuoteFromSavvion(String proposalNumber) in:" + proposalNumber);
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
                     msg = "MTRConstants.SUCCESS";
                     NLog.ws.debug("RGICL_MotorUW_App deleteProposalQuoteFromSavvion(String proposalNumber) out:" + proposalNumber + ":" + msg);
                     return msg;
                  }
               }
            }
         }

         blserver.disConnect(blsession);
      } catch (Exception var13) {
         throw new RuntimeException("Error in fecthing Proposal Details" + var13.getMessage(), var13);
      }

      NLog.ws.debug("RGICL_MotorUW_App deleteProposalQuoteFromSavvion(String proposalNumber) out:" + proposalNumber + ":" + msg);
      return msg;
   }

   public String getActionDetails(String proposalNumber) {
      NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) in:" + proposalNumber);
      String action = null;
      String CUStatus = null;
      String UWStatus = null;
      String ApprovalAuthority = null;
      String sqlString = null;
      String wsName = null;
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         sqlString = "select b.uwstatus as UWSTATUS, b.custatus as CUSTATUS, b.approvalauthority as approvalauthority, w.workstep_name as WORKSTEP_NAME from bizlogic_ds_" + (new Long(ptid)).toString() + " b, bizlogic_workitem w " + "where b.process_instance_id=w.process_instance_id and b.proposalnumber=?";
         this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
         connection = this.ds.getConnection();
         pstmt = connection.prepareStatement(sqlString);
         pstmt.setString(1, proposalNumber);
         rs = pstmt.executeQuery();
         NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) in:" + proposalNumber + ":" + sqlString);

         while(rs.next()) {
            ApprovalAuthority = rs.getString("approvalauthority");
            CUStatus = rs.getString("CUSTATUS");
            UWStatus = rs.getString("UWSTATUS");
            wsName = rs.getString("WORKSTEP_NAME").toUpperCase();
         }

         if (pstmt != null) {
            pstmt.close();
         }

         if (rs != null) {
            rs.close();
         }

         if (connection != null) {
            connection.close();
         }

         if (wsName != null && wsName.length() > 0) {
            String var16;
            if (wsName.equalsIgnoreCase("UWACTIVITY")) {
               action = "2";
               NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) out:" + proposalNumber + ":action:" + action);
               var16 = action;
               return var16;
            }

            if (wsName.equalsIgnoreCase("CUACTIVITY")) {
               action = "2";
               NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) out:" + proposalNumber + ":action:" + action);
               var16 = action;
               return var16;
            }

            if (!wsName.equalsIgnoreCase("HEALTH UW") && !wsName.equalsIgnoreCase("OPS TEAM") && !wsName.equalsIgnoreCase("PA UWACTIVITY") && !wsName.equalsIgnoreCase("TRAVELACTIVITY")) {
               if (!wsName.equalsIgnoreCase("MISCPRODUCTS") && !wsName.contains("MARINE")) {
                  if (wsName.equalsIgnoreCase("PACKAGE PRODUCTS")) {
                     action = "4";
                     if (ApprovalAuthority.equals("33") || CUStatus != null && CUStatus.equalsIgnoreCase("forward") || UWStatus != null && UWStatus.equalsIgnoreCase("forward")) {
                        action = "2";
                     }

                     NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) out:" + proposalNumber + ":action:" + action);
                     var16 = action;
                     return var16;
                  }

                  if (wsName.equalsIgnoreCase("Endorsement")) {
                     action = "4";
                     if (!ApprovalAuthority.equalsIgnoreCase("05")) {
                        action = "2";
                     }

                     NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) out:" + proposalNumber + ":action:" + action);
                     var16 = action;
                     return var16;
                  }

                  action = "4";
                  if (ApprovalAuthority.equals("33")) {
                     action = "2";
                  }

                  NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) out:" + proposalNumber + ":action:" + action);
                  var16 = action;
                  return var16;
               }

               action = "4";
               if (ApprovalAuthority.equals("33")) {
                  action = "2";
               }

               NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) out:" + proposalNumber + ":action:" + action);
               var16 = action;
               return var16;
            }

            action = "3";
            NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) out:" + proposalNumber + ":action:" + action);
            var16 = action;
            return var16;
         }

         blserver.disConnect(blsession);
      } catch (Exception var50) {
         throw new RuntimeException("Error in getting action details " + var50.getMessage(), var50);
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException var49) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (SQLException var48) {
         }

         try {
            if (connection != null) {
               connection.close();
            }
         } catch (SQLException var47) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getActionDetails(String proposalNumber) out:" + proposalNumber + ":action:" + action);
      return action;
   }

   private String getActionDetailsold(String proposalNumber) {
      NLog.ws.debug("RGICL_MotorUW_App getActionDetailsold(String proposalNumber) in:" + proposalNumber);
      String action = null;
      String CUStatus = null;
      String UWStatus = null;
      long piid = 0L;
      String ApprovalAuthority = null;

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
                  CUStatus = (String)pi.getDataSlotValue("CUStatus");
                  UWStatus = (String)pi.getDataSlotValue("UWStatus");
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
                              NLog.ws.debug("RGICL_MotorUW_App getActionDetailsold(String proposalNumber) out:" + proposalNumber + ":" + action);
                              return action;
                           }

                           if (wsName.equals("UWACTIVITY") && ApprovalAuthority.equals("33")) {
                              action = "0";
                              NLog.ws.debug("RGICL_MotorUW_App getActionDetailsold(String proposalNumber) out:" + proposalNumber + ":" + action);
                              return action;
                           }

                           if (wsName.equals("CUACTIVITY")) {
                              action = "2";
                              NLog.ws.debug("RGICL_MotorUW_App getActionDetailsold(String proposalNumber) out:" + proposalNumber + ":" + action);
                              return action;
                           }

                           if (wsName.equals("HEALTH UW") || wsName.equals("OPS TEAM") || wsName.equals("PA UWACTIVITY") || wsName.equals("TRAVELACTIVITY")) {
                              action = "3";
                              NLog.ws.debug("RGICL_MotorUW_App getActionDetailsold(String proposalNumber) out:" + proposalNumber + ":" + action);
                              return action;
                           }

                           if (!wsName.contains("MISCPRODUCTS") && !wsName.contains("MARINE")) {
                              action = "2";
                              if (wsName.contains("MARINE")) {
                                 action = "0";
                              }
                           } else {
                              action = "4";
                              if (ApprovalAuthority.equals("33")) {
                                 action = "2";
                              }
                           }

                           if (wsName.contains("PACKAGE")) {
                              action = "4";
                              if (ApprovalAuthority.equals("33") || CUStatus != null && CUStatus.equalsIgnoreCase("forward") || UWStatus != null && UWStatus.equalsIgnoreCase("forward")) {
                                 action = "2";
                              }

                              NLog.ws.debug("RGICL_MotorUW_App getActionDetailsold(String proposalNumber) out:" + proposalNumber + ":" + action);
                              return action;
                           }
                        }
                     }
                  }
               }
            }
         }

         blserver.disConnect(blsession);
      } catch (Exception var23) {
         throw new RuntimeException("Error in getting action details " + var23.getMessage(), var23);
      }

      NLog.ws.debug("RGICL_MotorUW_App getActionDetailsold(String proposalNumber) out:" + proposalNumber + ":" + action);
      return action;
   }

   public String getRuntimeActionDetails(String proposalNumber, String newAuthCode) {
      NLog.ws.debug("RGICL_MotorUW_App getRuntimeActionDetails(String proposalNumber,String newAuthCode) in:" + proposalNumber + ":" + newAuthCode);
      String action = null;
      long piid = 0L;
      String ApprovalAuthority = null;
      String CUStatus = null;
      String UWStatus = null;
      String wsName = null;
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         int newAuth = Integer.parseInt(newAuthCode);
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
         this.sqlString = "select b.uwstatus as UWSTATUS, b.custatus as CUSTATUS, b.approvalauthority as approvalauthority, w.workstep_name as WORKSTEP_NAME from bizlogic_ds_" + (new Long(ptid)).toString() + " b, bizlogic_workitem w " + "where b.process_instance_id=w.process_instance_id and b.proposalnumber=?";
         this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
         connection = this.ds.getConnection();
         pstmt = connection.prepareStatement(this.sqlString);
         pstmt.setString(1, proposalNumber);
         rs = pstmt.executeQuery();
         NLog.ws.debug("RGICL_MotorUW_App getRuntimeActionDetails(String proposalNumber,String newAuthCode) in:" + proposalNumber + ":" + newAuthCode + ":" + this.sqlString);

         while(rs.next()) {
            ApprovalAuthority = rs.getString("approvalauthority");
            CUStatus = rs.getString("CUSTATUS");
            UWStatus = rs.getString("UWSTATUS");
            wsName = rs.getString("WORKSTEP_NAME").toUpperCase();
         }

         if (pstmt != null) {
            pstmt.close();
         }

         if (rs != null) {
            rs.close();
         }

         if (connection != null) {
            connection.close();
         }

         if (!wsName.contains("UWACTIVITY") && !wsName.contains("CUACTIVITY")) {
            if (!wsName.contains("HEALTH UW") && !wsName.contains("OPS TEAM") && !wsName.contains("PA UWActivity") && !wsName.contains("TravelActivity")) {
               if (!wsName.contains("PACKAGE PRODUCTS")) {
                  if (!wsName.contains("MISCPRODUCTS") && !wsName.contains("MARINE")) {
                     String var19;
                     if (!wsName.equalsIgnoreCase("Endorsement")) {
                        action = "4";
                        if (ApprovalAuthority.equals("33")) {
                           action = "2";
                        }

                        if (wsName.contains("MARINE")) {
                           action = "0";
                        }

                        var19 = action;
                        return var19;
                     }

                     action = "4";
                     if (!ApprovalAuthority.equalsIgnoreCase("05")) {
                        action = "2";
                     }

                     var19 = action;
                     return var19;
                  }

                  action = "4";
                  if (newAuth == 33) {
                     action = "2";
                  }
               } else {
                  action = "4";
                  if (newAuth == 33 || CUStatus != null && CUStatus.equalsIgnoreCase("forward") || UWStatus != null && UWStatus.equalsIgnoreCase("forward")) {
                     action = "2";
                  }
               }
            } else {
               action = "3";
            }
         } else {
            action = "2";
         }

         blserver.disConnect(blsession);
      } catch (Exception var43) {
         throw new RuntimeException("Error in getting action details " + var43.getMessage(), var43);
      } finally {
         try {
            if (pstmt != null) {
               try {
                  pstmt.close();
               } catch (Exception var41) {
               }
            }

            if (rs != null) {
               try {
                  rs.close();
               } catch (Exception var40) {
               }
            }

            if (connection != null) {
               try {
                  connection.close();
               } catch (Exception var39) {
               }
            }
         } catch (Exception var42) {
         }

      }

      NLog.ws.debug("RGICL_MotorUW_App getRuntimeActionDetails out:" + proposalNumber + ":" + newAuthCode + ":action:" + action);
      return action;
   }

   private ResponseObject Suspend_OR_Resume_Proposal(WorkItemObject reqObj) {
      NLog.ws.debug("RGICL_MotorUW_App Suspend_OR_Resume_Proposal(WorkItemObject reqObj) in");
      ResponseObject resObj = new ResponseObject();
      String proposalNumber = null;
      Session blsession = null;
      BLServer blserver = null;
      String ApprovalAuthority = null;
      String responseCode = null;
      String userId = null;
      String status = null;

      try {
         if (reqObj == null) {
            resObj.setResponseCode("5070");
         } else {
            userId = reqObj.getUserId();
            status = reqObj.getStatus();
            proposalNumber = reqObj.getProposalNumber();
            ApprovalAuthority = reqObj.getApprovalAuthority();
           
            boolean isResumed = false;
            if (status == null || status == "" || status.equalsIgnoreCase("")) {
               blserver = BLClientUtil.getBizLogicServer();
               blsession = blserver.connect("rgicl", "rgicl");
               boolean isProposalExists = this.checkProposalExists(proposalNumber);
               int _status = this.checkProposalStatus(proposalNumber, false);
               if (!isProposalExists) {
                  resObj.setResponseCode("5047");
                  resObj.setMessage("Fresh Case, Please Refer to U/W First using Initiate/BSM Submit Quote");
                  NLog.ws.debug("RGICL_MotorUW_App Suspend_OR_Resume_Proposal(WorkItemObject reqObj) out:proposal:" + proposalNumber + ":Fresh Case, Please Refer to U/W First using Initiate/BSM Submit Quote");
                  return resObj;
               }

               String perf = "";
               long ptid = blserver.getProcessTemplate(blsession, this.ptName).getID();
               if (!isProposalExists || _status != 8) {
                  if (isProposalExists && _status == 7) {
                     responseCode = "5000";
                     resObj.setResponseCode(responseCode);
                     resObj.setMessage("Case Already sent to underwriter(s)");
                     NLog.ws.debug("RGICL_MotorUW_App Suspend_OR_Resume_Proposal(WorkItemObject reqObj) out:proposal:" + proposalNumber + ":Case Already sent to underwriter");
                     return resObj;
                  }

                  responseCode = "5060";
                  resObj.setResponseCode(responseCode);
                  resObj.setMessage("Case Not Found or Already Completed");
                  NLog.ws.debug("RGICL_MotorUW_App Suspend_OR_Resume_Proposal(WorkItemObject reqObj) out:proposal:" + proposalNumber + ":Case Not Found or Already Completed");
                  return resObj;
               }

               long piid = this.getProposalProcessInstanceId(proposalNumber);
               boolean isPiExists = blserver.isProcessInstanceExist(blsession, piid);
               if (isPiExists) {
                  ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
                  if (pi.isSuspended()) {
                     isResumed = this.resumeProposalQuote(pi.getName(), userId);
                     if (isResumed) {
                        responseCode = "5000";
                        resObj.setResponseCode(responseCode);
                        resObj.setMessage("Case sent again to underwriter");
                        NLog.ws.debug("RGICL_MotorUW_App Suspend_OR_Resume_Proposal(WorkItemObject reqObj) out:proposal:" + proposalNumber + ":Case sent again to underwriter:pi " + piid + " resumed");
                        return resObj;
                     }
                  }
               }
            }
         }
      } catch (Exception var20) {
         resObj.setResponseCode("5050-Failure Exception");
         resObj.setMessage("Workflow error in resuming the case in system");
         var20.printStackTrace();
      }

      NLog.ws.debug("RGICL_MotorUW_App Suspend_OR_Resume_Proposal(WorkItemObject reqObj) out");
      return resObj;
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
      Connection connection = null;
      CallableStatement pstmt = null;
      ResultSet rs = null;
      if (reqObj == null) {
         resObj.setResponseCode("5070");
         NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots in: requestObj null");
      } else {
         userId = reqObj.getUserId();
         status = reqObj.getStatus();
         proposalNumber = reqObj.getProposalNumber();
         quoteNo = reqObj.getQuoteNo();
         ApprovalAuthority = reqObj.getApprovalAuthority();
         WorkItemObject obj = new WorkItemObject();
         obj.setStatus(status);
         obj.setRemarks("Update Method Called");
         obj.setBlazeRemarks("");
         obj.setApprovalAuthority(ApprovalAuthority);
         obj.setProposalNumber(proposalNumber);
         obj.setProcessInstanceName("dummy#124");
         obj.setWorkStepName("");
         obj.setUserId(userId);
         obj.setQuoteNo(quoteNo);
         this.addRemarks(obj);
         NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots in: Update Method Called:proposal:" + proposalNumber);
         if (quoteNo == null) {
            quoteNo = "";
         }

         String wiName = "";

         try {
            ResponseObject var41;
            label2205:
            if (this.checkNull(proposalNumber) || this.checkNull(userId)) {
               if (this.checkNull(userId)) {
                  resObj.setResponseCode("5030-Null or Empty value passed for userId code");
                  NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots in:proposal:" + proposalNumber + ":user id null");
                  var41 = resObj;
                  return var41;
               }
            } else {
               BLServer blserver = BLClientUtil.getBizLogicServer();
               Session blsession = blserver.connect("rgicl", "rgicl");
               if (status == null || status.equalsIgnoreCase("")) {
                  NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots in:proposal:" + proposalNumber + ":before suspend or resume method got request.status empty");
                  resObj = this.Suspend_OR_Resume_Proposal(reqObj);
                  NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":after suspend or resume method got request.status empty");
                  var41 = resObj;
                  return var41;
               }

               QueryService qs = null;
               String sessionId1 = this.connect(userId, this.getUserPassword(userId));
               Session sess = getBizLogicManager().getSession(sessionId1);
               QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
               if (proposalNumber != null && proposalNumber.length() > 0) {
                  wifil.setCondition("BLIDS.proposalNumber='" + proposalNumber + "'");
               }

               String toUser = reqObj.getAssignToUserId();
               qs = new QueryService(sess);
               QSWorkItem qswi = qs.getWorkItem();
               wifil.setFetchSize(0);
               int[] var10000 = new int[2];
               BLConstants.single();
               var10000[0] = 27;
               BLConstants.single();
               var10000[1] = 28;
               int[] states = var10000;
               WorkItemList wiList = qswi.getList(wifil, states);
               String branchCode;
               String appAuth;
               String productCode;
               if (wiList != null) {
                  List list = wiList.getList();
                  if (list != null && !list.isEmpty()) {
                     for(int i = 0; i < list.size(); ++i) {
                        WorkItem wi = (WorkItem)list.get(i);
                        wiName = wi.getName();
                        String approvalAuth;
                        if (wi.isAvailable()) {
                           isAvailable = true;
                           if (status != null && status.equalsIgnoreCase("forward")) {
                              branchCode = (String)wi.getDataSlot("branchCode").getValue();
                              appAuth = (String)wi.getDataSlot("ApprovalAuthority").getValue();
                              productCode = (String)wi.getDataSlot("ProductCode").getValue();
                              Vector v;
                              if (wiName.toUpperCase().contains("PA UWACTIVITY")) {
                                 toUser = "PA_CUW_Queue";
                                 v = new Vector();
                                 v.add(toUser);
                                 wi.makeAvailable(v);
                                 wi.save();
                                 resObj.setMessage("Case Forwarded to CU/W " + this.getQueueUserDetails(toUser));
                                 resObj.setResponseCode("5000");
                                 NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":Case Forwarded to CU/W");
                                 var41 = resObj;
                                 return var41;
                              }

                              if (wiName.toUpperCase().contains("MISCPRODUCTS") || wiName.toUpperCase().contains("PACKAGE") || wiName.toUpperCase().contains("MARINE")) {
                                 approvalAuth = "";
                                 if (appAuth.equalsIgnoreCase("31")) {
                                    approvalAuth = "32";
                                 }

                                 if (appAuth.equalsIgnoreCase("32") || wiName.toUpperCase().contains("PACKAGE") || wiName.toUpperCase().contains("MARINE")) {
                                    approvalAuth = "33";
                                 }

                                 toUser = this.getQueueDetails(productCode, branchCode, approvalAuth, "", resObj);
                                 v = new Vector();
                                 v.add(toUser);
                                 wi.makeAvailable(v);
                                 wi.save();
                                 HashMap hm = new HashMap();
                                 ProcessInstance pi1 = blserver.getProcessInstance(sess, wi.getProcessInstanceID());
                                 if (appAuth.equalsIgnoreCase("31")) {
                                    hm.put("ApprovalAuthority", new String("32"));
                                 }

                                 if (appAuth.equalsIgnoreCase("32") || wiName.toUpperCase().contains("PACKAGE") || wiName.toUpperCase().contains("MARINE")) {
                                    hm.put("ApprovalAuthority", new String("33"));
                                 }

                                 hm.put("CUStatus", new String("Forward"));
                                 pi1.updateSlotValue(hm);
                                 pi1.save();
                                 pi1.refresh();
                                 resObj.setMessage("Case Forwarded to " + this.getQueueUserDetails(toUser));
                                 resObj.setResponseCode("5000");
                                 NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":Case Forwarded");
                                 var41 = resObj;
                                 return var41;
                              }

                              HashMap hm;
                              ProcessInstance pi1;
                              if (wiName.toUpperCase().contains("WC_ACTIVITY")) {
                                 toUser = this.getQueueDetails(productCode, branchCode, "33", "", resObj);
                                 v = new Vector();
                                 v.add(toUser);
                                 wi.makeAvailable(v);
                                 wi.save();
                                 hm = new HashMap();
                                 pi1 = blserver.getProcessInstance(sess, wi.getProcessInstanceID());
                                 if (appAuth.equalsIgnoreCase("31")) {
                                    hm.put("ApprovalAuthority", new String("32"));
                                 }

                                 if (appAuth.equalsIgnoreCase("32")) {
                                    hm.put("ApprovalAuthority", new String("33"));
                                 }

                                 hm.put("CUStatus", new String("Forward"));
                                 pi1.updateSlotValue(hm);
                                 pi1.save();
                                 resObj.setMessage("Case Forwarded to " + this.getQueueUserDetails(toUser));
                                 resObj.setResponseCode("5000");
                                 NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":Case Forwarded");
                                 var41 = resObj;
                                 return var41;
                              }

                              if (wiName.toUpperCase().contains("END_")) {
                                 if (ApprovalAuthority != "" && ApprovalAuthority != null && !ApprovalAuthority.equalsIgnoreCase("05")) {
                                    toUser = this.getQueueDetails(productCode, branchCode, ApprovalAuthority, "Endorsement", resObj);
                                    v = new Vector();
                                    v.add(toUser);
                                    wi.makeAvailable(v);
                                    wi.save();
                                    hm = new HashMap();
                                    pi1 = blserver.getProcessInstance(sess, wi.getProcessInstanceID());
                                    hm.put("ApprovalAuthority", ApprovalAuthority);
                                    hm.put("CUStatus", new String("Forward"));
                                    pi1.updateSlotValue(hm);
                                    pi1.save();
                                    resObj.setMessage("Case Forwarded to " + this.getQueueUserDetails(toUser));
                                    resObj.setResponseCode("5000");
                                    NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":Forwarded");
                                    var41 = resObj;
                                    return var41;
                                 }

                                 resObj.setMessage("Forward case should have Authority code 31, 32, 33");
                                 resObj.setResponseCode("5035");
                                 NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":Forward case should have Authority code 31, 32, 33");
                                 var41 = resObj;
                                 return var41;
                              }
                           }
                        }

                        if (wi.isAssigned()) {
                           isAvailable = false;
                           ProcessInstance pi1 = blserver.getProcessInstance(sess, wi.getProcessInstanceID());
                           appAuth = (String)wi.getDataSlot("branchCode").getValue();
                           productCode = (String)wi.getDataSlot("ApprovalAuthority").getValue();
                           approvalAuth = (String)wi.getDataSlot("ProductCode").getValue();
                           String BusinessType = (String)wi.getDataSlot("BusinessType").getValue();
                           toUser = this.getQueueDetails(approvalAuth, appAuth, ApprovalAuthority, BusinessType, resObj);
                           if (toUser != null && status != null && status.equalsIgnoreCase("forward")) {
                              if (wiName.toUpperCase().contains("PA UWACTIVITY")) {
                                 toUser = "PA_CUW_Queue";
                                 Vector v = new Vector();
                                 v.add(toUser);
                                 wi.makeAvailable(v);
                                 wi.save();
                                 resObj.setMessage("Case Forwarded to CU/W " + this.getQueueUserDetails(toUser));
                                 resObj.setResponseCode("5000");
                                 NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":Case Forwarded to CU/W");
                                 var41 = resObj;
                                 return var41;
                              }

                              if (wiName.toUpperCase().contains("MISCPRODUCTS") || wiName.toUpperCase().contains("PACKAGE") || wiName.toUpperCase().contains("MARINE")) {
                                 toUser = this.getQueueDetails(approvalAuth, appAuth, ApprovalAuthority, "", resObj);
                                 wi.reAssign(toUser);
                                 wi.save();
                                 wi.refresh();
                                 if (ApprovalAuthority.equalsIgnoreCase("31")) {
                                    pi1.updateSlotValue("ApprovalAuthority", new String("32"));
                                 }

                                 if (ApprovalAuthority.equalsIgnoreCase("32")) {
                                    pi1.updateSlotValue("ApprovalAuthority", new String("33"));
                                 }

                                 pi1.updateSlotValue("CUStatus", "Forward");
                                 pi1.save();
                                 resObj.setMessage("Case Forwarded to user " + this.getUserDetails(toUser));
                                 resObj.setResponseCode("5000");
                                 NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":Case Forwarded to user");
                                 var41 = resObj;
                                 return var41;
                              }
                           }

                           if (status != null && status.equalsIgnoreCase("Forward") && wiName.toUpperCase().contains("CUACTIVITY")) {
                              String approvalAuthority = reqObj.getApprovalAuthority();
                              if (approvalAuthority.equalsIgnoreCase("33")) {
                                 wi.makeAvailable();
                                 wi.save();
                                 responseCode = "5000";
                                 resObj.setResponseCode(responseCode);
                                 resObj.setMessage("Case sent to underwriter " + this.getUserDetails("CU"));
                                 NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":Case sent to underwriter");
                                 var41 = resObj;
                                 return var41;
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
               ProcessInstance pi = blserver.getProcessInstance(blsession, processInstanceName);
               HashMap hm = new HashMap();
               String uwstatus = "";
               if (wiName != null && wiName.toUpperCase().contains("UWACTIVITY")) {
                  isMotor = true;
                  uwstatus = reqObj.getStatus();
                  if (uwstatus != null && (uwstatus.equals("Approved") || uwstatus.equals("Rejected") || uwstatus.equals("Forward") || uwstatus.toLowerCase().contains("onhold"))) {
                     hm.put("UWStatus", uwstatus);
                  }

                  branchCode = reqObj.getRemarks();
                  if (!this.checkNull(branchCode)) {
                     hm.put("UWRemarks", branchCode);
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

                  branchCode = reqObj.getRemarks();
                  if (!this.checkNull(branchCode)) {
                     hm.put("CURemarks", branchCode);
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

               if (wiName != null && (wiName.toUpperCase().contains("HEALTH UW") || wiName.toUpperCase().contains("PA UWACTIVITY")) || wiName.toUpperCase().contains("TRAVELACTIVITY") || wiName.toUpperCase().contains("PACKAGE") || wiName.toUpperCase().contains("MISCPRODUCTS") || wiName.toUpperCase().contains("WC_ACTIVITY") || wiName.toUpperCase().contains("MARINE") || wiName.toUpperCase().contains("END_")) {
                  uwstatus = reqObj.getStatus();
                  isMotor = false;
                  if (uwstatus != null && (uwstatus.equals("Forward") || uwstatus.equals("Approved") || uwstatus.equals("Rejected") || uwstatus.toLowerCase().contains("onhold"))) {
                     hm.put("CUStatus", uwstatus);
                  }

                  branchCode = reqObj.getRemarks();
                  if (!this.checkNull(branchCode)) {
                     hm.put("CURemarks", branchCode);
                  }

                  appAuth = reqObj.getBlazeRemarks();
                  if (!this.checkNull(appAuth)) {
                     hm.put("CUBlazeRemark", appAuth);
                  }
               }

               if (wiName != null && wiName.toUpperCase().contains("OPS TEAM")) {
                  uwstatus = reqObj.getStatus();
                  isMotor = false;
                  if (uwstatus != null && (uwstatus.equals("Forward") || uwstatus.equals("Approved") || uwstatus.equals("Rejected") || uwstatus.toLowerCase().contains("onhold"))) {
                     hm.put("CUStatus", uwstatus);
                  }

                  branchCode = reqObj.getRemarks();
                  if (!this.checkNull(branchCode)) {
                     hm.put("CURemarks", branchCode);
                  }

                  appAuth = reqObj.getBlazeRemarks();
                  if (!this.checkNull(appAuth)) {
                     hm.put("CUBlazeRemark", appAuth);
                  }

                  productCode = reqObj.getApprovalAuthority();
                  if (!this.checkNull(productCode)) {
                     hm.put("ApprovalAuthority", productCode);
                  }
               }

               if (blsession == null) {
                  resObj.setResponseCode("5030-Null or Empty value passed for userId code");
                  NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":invalid userid");
               } else {
                  pi.updateSlotValue(hm);
                  pi.save();
                  NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":dataslots updated");
                  if (!this.checkNull(uwstatus) && isMotor) {
                     if (uwstatus.equalsIgnoreCase("Approved") || uwstatus.equalsIgnoreCase("Forward")) {
                        if (wiName != null && wiName.toUpperCase().contains("CUACTIVITY") && (uwstatus == null || uwstatus.equalsIgnoreCase("forward"))) {
                           resObj.setMessage("6008-Corporate Cannot have Forward and Complete, Please use Approve/Reject option");
                           resObj.setResponseCode("5050");
                           NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":can not forward");
                           var41 = resObj;
                           return var41;
                        }

                        if (isAvailable) {
                           this.assignWorkitem((new Long(blsession.getID())).toString(), wiName);
                           NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":assigned:" + wiName);
                        }

                        this.completeWorkitem((new Long(blsession.getID())).toString(), wiName);
                        NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":completed:" + wiName);
                        if (!uwstatus.equalsIgnoreCase("Forward")) {
                           resObj.setMessage("Proposal " + uwstatus + " Sucessfully");
                           resObj.setResponseCode("5000");
                           NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":Proposal " + uwstatus);
                           var41 = resObj;
                           return var41;
                        }

                        resObj.setMessage("Case Forwarded to Corporate Underwriter(s) " + this.getUserDetails("CU"));
                        resObj.setResponseCode("5000");
                        NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":Case Forwarded to Corporate Underwriter");
                        var41 = resObj;
                        return var41;
                     }

                     if (uwstatus.equalsIgnoreCase("Rejected")) {
                        this.suspendProposalQuote(processInstanceName, userId);
                        resObj.setResponseCode("5000");
                        resObj.setMessage("Proposal " + uwstatus + " Sucessfully");
                        NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":Proposal " + uwstatus);
                        var41 = resObj;
                        return var41;
                     }

                     if (uwstatus.equalsIgnoreCase("onhold")) {
                        resObj.setResponseCode("5000");
                        resObj.setMessage("Proposal put on " + uwstatus + " Sucessfully");
                        NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":Proposal " + uwstatus);
                        var41 = resObj;
                        return var41;
                     }
                  }

                  if (!this.checkNull(uwstatus) && !isMotor) {
                     if (!uwstatus.equalsIgnoreCase("Approved") && !uwstatus.equalsIgnoreCase("Approve") && !uwstatus.toLowerCase().contains("accept")) {
                        if (!uwstatus.equalsIgnoreCase("Rejected") && !uwstatus.toLowerCase().contains("reject")) {
                           if (!uwstatus.equalsIgnoreCase("Cancelled") && !uwstatus.toLowerCase().contains("cancel")) {
                              if (uwstatus.equalsIgnoreCase("onhold")) {
                                 resObj.setMessage("Proposal put on hold Sucessfully");
                                 resObj.setResponseCode("5000");
                                 NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":onhold:" + uwstatus);
                                 var41 = resObj;
                                 return var41;
                              }
                              break label2205;
                           }

                           pi.remove();
                           resObj.setMessage("Proposal Removed Sucessfully");
                           resObj.setResponseCode("5000");
                           NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":removed:" + uwstatus);
                           var41 = resObj;
                           return var41;
                        }

                        this.suspendProposalQuote(processInstanceName, userId);
                        resObj.setMessage("Proposal " + uwstatus + " Sucessfully");
                        resObj.setResponseCode("5000");
                        NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":" + uwstatus);
                        var41 = resObj;
                        return var41;
                     }

                     if (isAvailable) {
                        this.assignWorkitem((new Long(blsession.getID())).toString(), wiName);
                        NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":assignWorkitem " + wiName);
                     }

                     this.completeWorkitem((new Long(blsession.getID())).toString(), wiName);
                     resObj.setMessage("Proposal " + uwstatus + " Sucessfully");
                     resObj.setResponseCode("5000");
                     NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots :proposal:" + proposalNumber + ":" + uwstatus);
                     var41 = resObj;
                     return var41;
                  }
               }
            }
         } catch (Exception var137) {
            resObj.setResponseCode("5050-Failure Exception" + var137.getMessage());
            resObj.setMessage(var137.getMessage());
            var137.printStackTrace();
         } finally {
            try {
               obj = new WorkItemObject();
               obj.setStatus("Update or complete Workflow: " + status);
               if (resObj != null) {
                  if (resObj.getResponseCode() != null && resObj.getResponseCode().length() < 2000) {
                     obj.setRemarks("Response code : " + resObj.getResponseCode() + "  Message > " + resObj.getMessage());
                  }

                  if (resObj.getResponseCode() != null && resObj.getResponseCode().length() > 2000) {
                     obj.setRemarks("Response code : " + resObj.getResponseCode().substring(0, 1600) + "  Message > ");
                  }
               }

               obj.setBlazeRemarks(reqObj.getBlazeRemarks());
               obj.setApprovalAuthority(reqObj.getApprovalAuthority());
               obj.setProposalNumber(reqObj.getProposalNumber());
               if (processInstanceName != null && processInstanceName.contains("#")) {
                  obj.setProcessInstanceName(processInstanceName);
               } else {
                  obj.setProcessInstanceName("dummy#1234");
               }

               if (wiName != null && wiName.length() > 0) {
                  obj.setWorkStepName(wiName);
               }

               obj.setUserId(reqObj.getUserId());
               obj.setQuoteNo(reqObj.getQuoteNo());
               this.addRemarks(obj);
               this.disconnect((String)sessionId);
               if (pstmt != null) {
                  try {
                     ((CallableStatement)pstmt).close();
                  } catch (Exception var135) {
                  }
               }

               if (rs != null) {
                  try {
                     ((ResultSet)rs).close();
                  } catch (Exception var134) {
                  }
               }

               if (connection != null) {
                  try {
                     ((Connection)connection).close();
                  } catch (Exception var133) {
                  }
               }
            } catch (Exception var136) {
               var136.printStackTrace();
            }

         }
      }

      NLog.ws.debug("RGICL_MotorUW_App UpdateMotorIRPASSetUpDataSlots out:proposal:" + proposalNumber + ":" + resObj.getResponseCode() + ":" + resObj.getMessage());
      return resObj;
   }

   private boolean addRemarks(WorkItemObject obj) {
      boolean bln = false;
      DataSource ds1 = null;
      Connection conn = null;
      PreparedStatement ps = null;

      boolean var12;
      try {
         if (obj == null) {
            return bln;
         }

         ds1 = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
         conn = ds1.getConnection();
         String sqlString1 = "insert into MOTOR_IRPAS_FLOW_HISTORY values(?,?,?,?,?,?,?,?,?,?)";
         ps = conn.prepareStatement(sqlString1);
         String piName = obj.getProcessInstanceName();
         String[] arr = new String[]{"", ""};
         if (piName != null && piName.length() > 0) {
            arr = piName.split("#");
         }

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
         if (ps != null) {
            ps.close();
         }

         if (conn != null) {
            conn.close();
         }

         var12 = bln;
      } catch (Exception var26) {
         System.out.println("RGICL_MotorUW_App Error in adding remarks and status \n" + var26.getMessage());
         return bln;
      } finally {
         try {
            if (ps != null) {
               ps.close();
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

      return var12;
   }

   public String getHistory(String proposalNo) {
      NLog.ws.debug("RGICL_MotorUW_App getHistory(String proposalNo) in:proposal:" + proposalNo);
      String hist = null;
      StringBuffer sb = new StringBuffer();
      sb.append("<DATA>");
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         if (proposalNo != null && proposalNo.length() > 0) {
            String sql = "select * from MOTOR_IRPAS_FLOW_HISTORY where proposal_no=? or quote_no=? order by create_date";
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            conn = this.ds.getConnection();
            pstmt = conn.prepareStatement(sql);
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

            if (pstmt != null) {
               pstmt.close();
            }

            if (rs != null) {
               rs.close();
            }

            if (conn != null) {
               conn.close();
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
               conn.close();
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
      boolean bln = false;

      try {
         if (sessionId != null && getBizLogicManager().isSessionValid(sessionId)) {
            getBizLogicManager().disconnect(sessionId);
            bln = true;
         }

         return bln;
      } catch (Exception var4) {
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
