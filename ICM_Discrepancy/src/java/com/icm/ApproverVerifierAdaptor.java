package com.icm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.axis.AxisFault;

import com.savvion.util.NLog;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.*;
import com.savvion.sbm.bizlogic.util.*;
import com.savvion.sbm.util.*;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;

/**
 * Auto-generated
 */
public class ApproverVerifierAdaptor {
	private Connection conn = null;
	CallableStatement proc_stmt = null;
	ResultSet rs = null;

	public void commit() {
		try {
			conn = getConnectionToSQLDB();
			proc_stmt = conn.prepareCall("{ call usp_GetTRVerifier(?,?) }");
			proc_stmt.setString(1, branchCode);
			proc_stmt.setString(2, smCode);

			rs = proc_stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					tR_Verifier = rs.getString("VERTICALHEAD");
				}
			}

		} catch (Exception e) {
			NLog.ws.error("ICM_discrepancy::Error in ApproverVerifierAdaptor " + e.getMessage());
		} finally {
			try {
				if (proc_stmt != null) {
					try {
						proc_stmt.close();
					} catch (Exception ex) {
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception ex) {
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception ex) {
					}
				}
			} catch (Exception ex) {
			}
		}

	}

	private Connection getConnectionToSQLDB() throws AxisFault {
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("jdbc/sqlDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			throw new AxisFault("Error in getting a DB connection" + e.getMessage());
		}
		return conn;
	}

	public void PAKcallerID(String processInstanceName, String workstepName, java.util.Properties bizLogicHost) {
	}

	private String branchCode;
	private String smCode;

	public String getBranchCode() {
		return this.branchCode;
	}

	public String getSMCode() {
		return this.smCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setSMCode(String sMCode) {
		this.smCode = sMCode;
	}

	private String tR_Verifier;

	public String getTR_Verifier() {
		return this.tR_Verifier;
	}

	public void setTR_Verifier(String tR_Verifier) {
		this.tR_Verifier = tR_Verifier;
	}
}