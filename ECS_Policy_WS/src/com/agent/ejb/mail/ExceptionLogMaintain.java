package com.agent.ejb.mail;

import com.agent.ejb.initiateagent.GetResource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class ExceptionLogMaintain {
   protected void mailSendingError_Log(long piID, String errorGeneratorMethod, String callerMethod, String exceptionMessage, String exceptionStackTrace) {
      Connection connection = null;
      PreparedStatement pstmt = null;

      try {
         if (this.checkString(errorGeneratorMethod) && this.checkString(callerMethod) && this.checkString(exceptionMessage) && this.checkString(exceptionStackTrace)) {
            connection = GetResource.getDBConnection();
            String qry = "INSERT INTO AGENT_MAILEXCEPTION_LOG VALUES (?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setLong(1, piID);
            pstmt.setString(2, errorGeneratorMethod.trim());
            pstmt.setString(3, callerMethod.trim());
            if (exceptionMessage.length() > 500) {
               exceptionMessage = exceptionMessage.substring(0, 499);
            }

            pstmt.setString(4, exceptionMessage.trim());
            if (exceptionStackTrace.length() > 1000) {
               exceptionStackTrace = exceptionStackTrace.substring(0, 999);
            }

            pstmt.setString(5, exceptionStackTrace.trim());
            pstmt.setDate(6, new Date((new java.util.Date()).getTime()));
            pstmt.executeUpdate();
         }
      } catch (Exception var13) {
         System.out.println("Error while saving AGENT_MAILEXCEPTION_LOG  " + var13.getMessage());
         throw new RuntimeException(var13);
      } finally {
         GetResource.releaseResource(connection, pstmt);
      }

   }

   protected void mailSent_Log(long piID, String receiverTo, String receiverCC, String methodName) {
      Connection connection = null;
      PreparedStatement pstmt = null;

      try {
         if (this.checkString(receiverTo) && this.checkString(methodName)) {
            if (receiverCC == null || receiverCC.trim().length() == 0) {
               receiverCC = "-";
            }

            connection = GetResource.getDBConnection();
            String qry = "INSERT INTO AGENT_MAILSENT_LOG VALUES (?,?,?,?,?)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setLong(1, piID);
            pstmt.setString(2, receiverTo.trim());
            pstmt.setString(3, receiverCC.trim());
            pstmt.setString(4, methodName.trim());
            pstmt.setTimestamp(5, new Timestamp((new java.util.Date()).getTime()));
            pstmt.executeUpdate();
         }
      } catch (Exception var12) {
         throw new RuntimeException(var12);
      } finally {
         GetResource.releaseResource(connection, pstmt);
      }

   }

   private boolean checkString(String val) {
      return val != null && val.trim().length() > 0;
   }
}
