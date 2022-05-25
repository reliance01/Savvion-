package rgiclcms.cms.savvion.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {
   public boolean isUserValid(String userId, String productId, String zoneId, String hubId) {
      boolean result = false;
      boolean isUserProductValid = this.isUserValidForProduct(userId, productId);
      boolean isUserZoneValid = this.isUserValidForZone(userId, zoneId);
      boolean isUserHubValid = this.isUserValidForHub(userId, hubId);
      if (isUserProductValid && isUserZoneValid && isUserHubValid) {
         result = true;
      }

      return result;
   }

   private boolean isUserValidForHub(String userId, String hubId) {
      boolean result = false;
      String connectionUrl = "jdbc:sqlserver://10.65.15.210:7359;databaseName=NewCMS;user=cms;password=cms~123";
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         con = DriverManager.getConnection(connectionUrl);
         String sql = "select * From Hub_User_Mapping HUM where HUM.UserID=? and HUM.HubID=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, userId);
         pstmt.setString(2, hubId);
         rs = pstmt.executeQuery();
         int count = 0;
         if (rs != null) {
            while(rs.next()) {
               ++count;
            }
         }

         if (count > 0) {
            result = true;
         }
      } catch (Exception var23) {
         var23.printStackTrace();
         throw new RuntimeException("Error in DB Activity " + var23.getMessage());
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var22) {
            }
         }

         if (pstmt != null) {
            try {
               pstmt.close();
            } catch (Exception var21) {
            }
         }

         if (con != null) {
            try {
               con.close();
            } catch (Exception var20) {
            }
         }

      }

      return result;
   }

   private boolean isUserValidForZone(String userId, String zoneId) {
      boolean result = false;
      String connectionUrl = "jdbc:sqlserver://10.65.15.210:7359;databaseName=NewCMS;user=cms;password=cms~123";
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         con = DriverManager.getConnection(connectionUrl);
         String sql = "Select * From Zone_User_Mapping ZUM where ZUM.UserID=? and ZUM.ZoneID=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, userId);
         pstmt.setString(2, zoneId);
         rs = pstmt.executeQuery();
         int count = 0;
         if (rs != null) {
            while(rs.next()) {
               ++count;
            }
         }

         if (count > 0) {
            result = true;
         }
      } catch (Exception var23) {
         var23.printStackTrace();
         throw new RuntimeException("Error in DB Activity " + var23.getMessage());
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var22) {
            }
         }

         if (pstmt != null) {
            try {
               pstmt.close();
            } catch (Exception var21) {
            }
         }

         if (con != null) {
            try {
               con.close();
            } catch (Exception var20) {
            }
         }

      }

      return result;
   }

   private boolean isUserValidForProduct(String userId, String productId) {
      boolean result = false;
      String connectionUrl = "jdbc:sqlserver://10.65.15.210:7359;databaseName=NewCMS;user=cms;password=cms~123";
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         con = DriverManager.getConnection(connectionUrl);
         String sql = "Select * From User_ApprovalLimit_Mapping UAM Join  Lnk_Product_ApprovalLimit LPA on UAM.UserApprovalId=LPA.UserApprovalId where UAM.UserID=? and LPA.Product_Code=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, userId);
         pstmt.setString(2, productId);
         rs = pstmt.executeQuery();
         int count = 0;
         if (rs != null) {
            while(rs.next()) {
               ++count;
            }
         }

         if (count > 0) {
            result = true;
         }
      } catch (Exception var23) {
         var23.printStackTrace();
         throw new RuntimeException("Error in DB Activity " + var23.getMessage());
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var22) {
            }
         }

         if (pstmt != null) {
            try {
               pstmt.close();
            } catch (Exception var21) {
            }
         }

         if (con != null) {
            try {
               con.close();
            } catch (Exception var20) {
            }
         }

      }

      return result;
   }

   public void loadApproverRoleName() {
      String connectionUrl = "jdbc:sqlserver://10.65.15.210:7359;databaseName=NewCMS;user=cms;password=cms~123";
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         con = DriverManager.getConnection(connectionUrl);
         String sql = "select MR.RoleCode from Mas_Role MR join Approver_Mapping AM  on MR.RoleID=AM.AproverRoleID  join Mas_Service_Request MSR  on AM.RequestTypeID=MSR.SR_Type_ID  where MSR.SR_Type='SurveyorAppointment'  and SYSDATETIME() BETWEEN AM.FromDate and AM.ToDate ";
         pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if (rs != null) {
            while(rs.next()) {
            }
         }
      } catch (Exception var19) {
         var19.printStackTrace();
         throw new RuntimeException("Error in Adapter code " + var19.getMessage());
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var18) {
            }
         }

         if (pstmt != null) {
            try {
               pstmt.close();
            } catch (Exception var17) {
            }
         }

         if (con != null) {
            try {
               con.close();
            } catch (Exception var16) {
            }
         }

      }

   }
}
