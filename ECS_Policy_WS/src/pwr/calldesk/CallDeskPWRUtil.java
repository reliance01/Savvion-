package pwr.calldesk;

import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CallDeskPWRUtil {
   private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";

   private Connection getSQLConnectionDB() {
      Connection con = null;
      String dbip = "";
      String dbuser = "";
      String dbpass = "";

      try {
         String ip = InetAddress.getLocalHost().getHostAddress();
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         /*if (ip.contains("10.62.182.42")) {
				dbip = "RGIUDEVLOPMENT.reliancegeneral.com";
				dbuser = "calldesk";
				dbpass = "calldesk";
			} else {
            dbip = "RGIRMSCDDB";
            dbuser = "calldesk";
            dbpass = "calldesk";
         }*/
      // Below Changes done on 16/01/2022 as found issue during DR. Above code couldn't identify Production DR IP.
      			if (ip.contains("10.62.182.42")) {
      				dbip = "10.65.15.148";
      				dbuser = "calldesk";
      				dbpass = "calldesk";
      			} else {
      				dbip = "RGIRMSCDDB";
      				dbuser = "calldesk";
      				dbpass = "calldesk";
      			}

         con = DriverManager.getConnection("jdbc:sqlserver://" + dbip + ":7359;databaseName=CalldeskManagement", dbuser, dbpass);
         return con;
      } catch (Exception var6) {
         throw new RuntimeException("Error in getting a DB connection  for db " + dbip + " \n" + var6.getMessage(), var6);
      }
   }

   private Connection getSBMConnectionDB() {
      Connection con = null;

      try {
         DataSource ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
         con = ds.getConnection();
      } catch (Exception var3) {
      }

      return con;
   }

   public String updateRCA(HashMap hm) {
      String retVal = "FALSE";
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      CallableStatement proc_stmt = null;
      if (hm != null && !hm.isEmpty()) {
         String ticketNo = (String)hm.get("ticket_no");
         String rcaType = (String)hm.get("rca_type");
         String rcaMember = (String)hm.get("rca_member");
         String rcaDate = (String)hm.get("rca_date");
         String rcaDetails = (String)hm.get("rca_details");
         String createdBy = (String)hm.get("createdby");
         System.out.println(ticketNo + "ticket" + createdBy + "byyyyy ");

         try {
            try {
               try {
                  connection = this.getSQLConnectionDB();
                  proc_stmt = connection.prepareCall("{ call USP_InsertRCA(?,?,?,?,?,?) }");
                  proc_stmt.setString(1, ticketNo);
                  proc_stmt.setString(2, rcaType);
                  proc_stmt.setString(3, rcaMember);
                  proc_stmt.setString(4, rcaDate);
                  proc_stmt.setString(5, rcaDetails);
                  proc_stmt.setString(6, createdBy);
                  proc_stmt.executeUpdate();
                  proc_stmt.close();
                  connection.close();
                  System.out.println("RCACalldesk");
               } catch (Exception var53) {
                  retVal = "FALSE";
                  System.out.println("Error While savion CalldeskRCA" + var53.getMessage());
                  String var15 = retVal;
                  return var15;
               } finally {
                  try {
                     if (proc_stmt != null) {
                        proc_stmt.close();
                     }
                  } catch (Exception var52) {
                  }

                  try {
                     if (connection != null) {
                        connection.close();
                     }
                  } catch (Exception var51) {
                  }

               }

               connection = this.getSBMConnectionDB();
               System.out.println("connection  -" + connection);
               String sql = "INSERT INTO TECHDESK_RCA VALUES (?,?,?,?,?,?,?)";
               pstmt = connection.prepareStatement(sql);
               pstmt.setString(1, ticketNo.trim());
               pstmt.setString(2, rcaType.trim());
               pstmt.setString(3, rcaMember.trim());
               DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
               Date date = formatter.parse(rcaDate);
               Timestamp timeStampDate = new Timestamp(date.getTime());
               pstmt.setTimestamp(4, timeStampDate);
               pstmt.setString(5, rcaDetails.trim());
               pstmt.setString(6, createdBy.trim());
               pstmt.setTimestamp(7, new Timestamp((new Date()).getTime()));
               pstmt.executeUpdate();
               pstmt.close();
               connection.close();
               retVal = "TRUE";
            } catch (Exception var55) {
               retVal = "FALSE";
               System.out.println("Error While SavingRCA " + var55.getMessage());
               var55.printStackTrace();
            }

            return retVal;
         } finally {
            try {
               if (pstmt != null) {
                  pstmt.close();
               }
            } catch (Exception var50) {
            }

            try {
               if (connection != null) {
                  connection.close();
               }
            } catch (Exception var49) {
            }

         }
      } else {
         return retVal;
      }
   }

   public List<ArrayList<String>> fetchRCAData(String ticketno) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      ArrayList allRCAData = new ArrayList();

      try {
         connection = this.getSBMConnectionDB();
         String query = "select * from techdesk_rca where ticketno = ? order by created_date desc";
         pstmt = connection.prepareStatement(query);
         pstmt.setString(1, ticketno.trim());
         rset = pstmt.executeQuery();
         if (rset != null) {
            while(rset.next()) {
               List<String> rcaData = new ArrayList();
               rcaData.add(rset.getString("RCA_TYPE"));
               rcaData.add(rset.getString("RCA_TEAMMEMBERS"));
               rcaData.add(rset.getString("RCA_DEPLOYMENTDATE"));
               rcaData.add(rset.getString("RCA_DETAIL"));
               rcaData.add(rset.getString("CREATED_BY"));
               rcaData.add(rset.getString("CREATED_DATE"));
               allRCAData.add((ArrayList)rcaData);
            }
         }
      } catch (Exception var16) {
         System.out.println("Error while fetching RCA Details" + var16.getMessage());
      } finally {
         try {
            if (rset != null) {
               rset = null;
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var15) {
            System.out.println("Error while closing connections method fetchRCA " + var15.getMessage());
         }

      }

      return allRCAData;
   }

   public boolean getApprover2StatusFlag(String ticketNo) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rSet = null;
      boolean flag = false;

      try {
         connection = this.getSQLConnectionDB();
         String sqlQuery = "Exec usp_IsDualApprovalRestricationCategory '" + ticketNo.trim() + "'";
         pstmt = connection.prepareStatement(sqlQuery);

         for(rSet = pstmt.executeQuery(); rSet.next(); flag = rSet.getBoolean(1)) {
         }

         System.out.println("DualApprover Flag in class is " + flag + " for ticketno " + ticketNo);
      } catch (Exception var15) {
         System.out.println("Error while fetching Approver2 Status Flag: " + var15.getMessage());
         var15.printStackTrace();
      } finally {
         try {
            if (rSet != null) {
               rSet.close();
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var14) {
            System.out.println("Error while closing connections method getApprover2StatusFlag ");
            var14.printStackTrace();
         }

      }

      return flag;
   }

   public ArrayList<String> getAppSupportSubStatus(String selectedIndexValue, String applicationName) {
      ArrayList<String> allOptions = new ArrayList();
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;

      try {
         connection = this.getSBMConnectionDB();
         String query = "SELECT CLOSURESUBCATEGORY FROM TECHDESK_CLOSURE_STATUS WHERE CLOSURECATEGORY = ? AND APPLICATIONNAME = ? ORDER BY CLOSURESUBCATEGORY ASC";
         pstmt = connection.prepareStatement(query);
         pstmt.setString(1, selectedIndexValue.trim());
         pstmt.setString(2, applicationName.trim());
         rset = pstmt.executeQuery();
         if (rset != null) {
            while(rset.next()) {
               allOptions.add(rset.getString("CLOSURESUBCATEGORY"));
            }
         }
      } catch (Exception var16) {
         System.out.println("Error while fetching drop down values" + var16.getMessage());
      } finally {
         try {
            if (rset != null) {
               rset = null;
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var15) {
            System.out.println("Error while closing connections method getDropDownValue " + var15.getMessage());
         }

      }

      return allOptions;
   }

   public Map<String, String> getReassignPerformer(String teamName) {
      Map<String, String> performerDetails = null;
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;

      try {
         connection = this.getSBMConnectionDB();
         String query = "select * from techdesk_admin_teamdetails where teamname = ?";
         pstmt = connection.prepareStatement(query);
         pstmt.setString(1, teamName.trim());
         rset = pstmt.executeQuery();
         performerDetails = new HashMap();
         System.out.println("3333333333333333333");
         if (rset != null) {
            while(rset.next()) {
               //performerDetails.put("groupName", rset.getString("groupname"));
            	performerDetails.put("l1performer",rset.getString("l1performer"));
            	performerDetails.put("l1PerformerEmail", rset.getString("l1performeremail"));
               performerDetails.put("l2Performer", rset.getString("l2performer"));
               performerDetails.put("l2PerformerEmail", rset.getString("l2performeremail"));
               performerDetails.put("l3Performer", rset.getString("l3performer"));
               performerDetails.put("l3PerformerEmail", rset.getString("l3performeremail"));
               performerDetails.put("l4Performer", rset.getString("l4performer"));
               performerDetails.put("l4PerformerEmail", rset.getString("l4performeremail"));
            }
         }
      } catch (Exception var15) {
         System.out.println("Error while fetching performer details values" + var15.getMessage());
      } finally {
         try {
            if (rset != null) {
               rset = null;
            }

            if (pstmt != null) {
               pstmt.close();
            }

            if (connection != null) {
               connection.close();
            }
         } catch (Exception var14) {
            System.out.println("Error while closing connections method getReassignPerformer " + var14.getMessage());
         }

      }

      return performerDetails;
   }
}
