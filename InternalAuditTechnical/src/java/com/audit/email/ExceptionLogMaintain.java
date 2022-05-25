package com.audit.email;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import com.rel.internalaudit.InternalAuditUtil;

public class ExceptionLogMaintain {
	
	InternalAuditUtil obj = new InternalAuditUtil();

	protected void mailSendingError_Log(long piID, String errorGeneratorMethod,
			String callerMethod, String exceptionMessage,
			String exceptionStackTrace) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			if (checkString(errorGeneratorMethod) && checkString(callerMethod)
					&& checkString(exceptionMessage)
					&& checkString(exceptionStackTrace)) {
				connection = obj.getDBConnection();
				String qry = "INSERT INTO INTERNLAUDIT_MAILEXCEPTION_LOG VALUES (?,?,?,?,?,?)";
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
				pstmt.setDate(6, new Date(new java.util.Date().getTime()));
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			obj.releaseResources(connection, pstmt);
		}
	}

	protected void mailSent_Log(long piID, String receiverTo,
			String receiverCC, String methodName) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			if (checkString(receiverTo) && checkString(receiverCC)
					&& checkString(methodName)) {
				connection = obj.getDBConnection();
				String qry = "INSERT INTO INTERNALAUDIT_MAILSENT_LOG VALUES (?,?,?,?,?)";
				pstmt = connection.prepareStatement(qry);
				pstmt.setLong(1, piID);
				pstmt.setString(2, receiverTo.trim());
				pstmt.setString(3, receiverCC.trim());
				pstmt.setString(4, methodName.trim());
				pstmt.setDate(5, new Date(new java.util.Date().getTime()));
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			obj.releaseResources(connection, pstmt);
		}
	}

	private boolean checkString(String val) {
		if (val != null && val.trim().length() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
