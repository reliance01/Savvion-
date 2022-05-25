import com.tdiinc.userManager.JDBCGroup;
import com.tdiinc.userManager.JDBCRealm;
import com.tdiinc.userManager.JDBCUser;
import com.tdiinc.userManager.UserManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.axis.AxisFault;

public class TechDeskUtil {
   private Connection getDBConnection() throws AxisFault {
      Connection connection = null;

      try {
         connection = ((DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB")).getConnection();
         return connection;
      } catch (Exception var3) {
         System.out.println("Error Getting DBConnection : " + var3.getMessage());
         throw new AxisFault("SBM Web services error :" + var3.getMessage());
      }
   }

   private void releaseResouce(ResultSet rset, PreparedStatement pstmt, Connection conn) throws AxisFault {
      try {
         if (rset != null) {
            rset.close();
         }

         if (pstmt != null) {
            pstmt.close();
         }

         if (conn != null) {
            conn.close();
         }

      } catch (Exception var5) {
         System.out.println("Error while ReleaseResouce : " + var5.getMessage());
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   public String getGrpMembers(String teamName) throws AxisFault {
      String members = "-";
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      String query = "SELECT GROUPNAME FROM CALLDESK_SUPPORTESC WHERE TEAMNAME = ?";

      try {
         if (teamName != null && teamName.trim().length() > 0) {
            String grpName = "";
            connection = this.getDBConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, teamName.trim());

            for(rset = pstmt.executeQuery(); rset.next(); grpName = rset.getString(1)) {
            }

            if (grpName != null && grpName.trim().length() > 0) {
               if (grpName.trim().equalsIgnoreCase("CDITGrp")) {
                  members = grpName.trim();
               } else {
                  UserManager um = new UserManager();
                  JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
                  JDBCGroup g = (JDBCGroup)r.getGroup(grpName.trim());
                  String[] users = g.getAllUserMemberNames();
                  if (users != null && users.length > 0) {
                     for(int i = 0; i < users.length; ++i) {
                        JDBCUser u = (JDBCUser)r.getUser(users[i]);
                        members = members + u.userName + "-" + u.getAttribute("FIRSTNAME") + " " + u.getAttribute("LASTNAME") + ",";
                     }

                     if (members.trim().length() > 0) {
                        members = members.substring(1, members.length() - 1);
                     }
                  }
               }
            }
         }
      } catch (Exception var17) {
         System.out.println("error in getting Grp members : " + var17.getMessage());
         throw new AxisFault("SBM Web services error :" + var17.getMessage());
      } finally {
         this.releaseResouce(rset, pstmt, connection);
      }

      return members;
   }

   public String getUserMailID(String userName) throws AxisFault {
      String mailID = "-";

      try {
         if (userName != null && userName.trim().length() > 0) {
            UserManager um = new UserManager();
            JDBCRealm r = (JDBCRealm)UserManager.getDefaultRealm();
            JDBCUser u = (JDBCUser)r.getUser(userName.trim());
            if (u != null) {
               mailID = u.getAttribute("EMAIL");
            }
         }

         return mailID;
      } catch (Exception var6) {
         System.out.println("error in getting User Mail ID : " + var6.getMessage());
         throw new AxisFault("TechDesk Util Web services error :" + var6.getMessage());
      }
   }
}
