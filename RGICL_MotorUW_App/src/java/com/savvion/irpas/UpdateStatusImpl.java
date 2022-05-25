package com.savvion.irpas;


import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
import com.savvion.sbm.bizlogic.server.svo.*;
import com.savvion.sbm.bizlogic.util.*;
import com.savvion.sbm.util.*;

/**
 * Auto-generated
 */
public class UpdateStatusImpl {
	private Connection con = null;
	private CallableStatement proc_stmt = null;
	private ResultSet rs = null;
		
	public void execute() throws Exception{
		if(lob.equalsIgnoreCase("health") || lob.equalsIgnoreCase("Travel") || 
				lob.equalsIgnoreCase("PACKAGE") || lob.equalsIgnoreCase("MISC")){
			if(cUStatus.equalsIgnoreCase("Approved")){
				//UpdateStatusImpl u = new UpdateStatusImpl();
				//u.Usp_UpdateSavvionStatus(proposalNumber, cUStatus, cUApprover);
			}
		}
	}
	
	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception ex) {
				throw new RuntimeException();			
		}
	}

	private Connection getConnectionToSQLDB() throws Exception {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			String dbip = "";
			String dbuser = "";
			String dbpass = "";
			if (ip.contains("10.65.15.")) {
				dbip = "rgidmossdb.reliancegeneral.co.in";
				dbuser = "savvion";
				dbpass = "pass@123";
			} else {
				dbip = "RGIIRPASDB01";
				dbuser = "savvion";
				dbpass = "pass@123";
			}
			con = DriverManager.getConnection("jdbc:sqlserver://" + dbip
					+ ":7359;databaseName=IRPAS", dbuser, dbpass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("RGICL Motor UWAppp UpdateStatusImpl : getConnectionToSQLDB ", e);

		}
		return con;
	}

	private void closeResources() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (proc_stmt != null) {
			try {
				proc_stmt.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}
	
	private boolean isNull(String str)
	{
		boolean isNull = true;
		if(str!=null && str.length()>0)
		{
			isNull = false;
		}		
		return isNull;
	}
	
	private boolean Usp_UpdateSavvionStatus(String proposalNumber, String status, String uwUserId) throws Exception {
		boolean isUpdated = false;
		try {
			
			if (!isNull(proposalNumber) && !isNull(status) && !isNull(uwUserId)) {
				con = getConnectionToSQLDB();
				proc_stmt = con.prepareCall("{ call Usp_UpdateSavvionStatus(?,?,?) }");
				proc_stmt.setString(1, proposalNumber);
				proc_stmt.setString(2, status);
				proc_stmt.setString(3, uwUserId);
				rs = proc_stmt.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						isUpdated = rs.getBoolean("Output");
						System.out.println("Output is " + isUpdated);
					}
				}
			}
			else{
				throw new RuntimeException("RGICL Motor UWAppp UpdateStatusImpl : Usp_UpdateSavvionStatus, proposal or status or uw id is null");
			}
			closeResources();
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException("RGICL Motor UWAppp UpdateStatusImpl : Usp_UpdateSavvionStatus",  e);
		} finally {
			closeResources();
		}
		return isUpdated;
	}


	public void PAKcallerID(String processInstanceName, String workstepName,
			java.util.Properties bizLogicHost) {
	}


	private String cUApprover;
	private String cUStatus;
	private String productCode;
	private String proposalNumber;
	public String getCUApprover() {
		return this.cUApprover;
	}

	public String getCUStatus() {
		return this.cUStatus;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public String getProposalNumber() {
		return this.proposalNumber;
	}

	public void setCUApprover(String cUApprover) {
		this.cUApprover = cUApprover;
	}

	public void setCUStatus(String cUStatus) {
		this.cUStatus = cUStatus;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProposalNumber(String proposalNumber) {
		this.proposalNumber = proposalNumber;
	}


	private String lob;

	public String getLOB() {
		return this.lob;
	}

	public void setLOB(String lob) {
		this.lob = lob;
	}
}