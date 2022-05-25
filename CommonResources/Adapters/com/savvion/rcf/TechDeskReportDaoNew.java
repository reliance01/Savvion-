package com.savvion.rcf;

import com.savvion.rcf.bean.DetailedReportDataBean;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

public class TechDeskReportDaoNew {

	private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
	public final String KEY_DATA = "data";
	public final String KEY_REPORT_NAME = "name";
	public final String KEY_COLUMN = "columns";
	public final String KEY_EXCEL_COLUMN = "ExcelColumn";
	private static String COLUMN_TICKET_NO = "Ticket No #";
	private static String COLUMN_ISSUEREQUEST_TYPE = "Issue Request Type";
	private static String COLUMN_ESCALATIONSTATUS = "Escalation Status";
	private static String COLUMN_APPLICATIONTYPE = "Application Type";
	private static String COLUMN_CALLCREATEDBY = "Call Registered by";
	private static String COLUMN_CALLTYPE = "Call Type";
	private static String COLUMN_CALLLOGDATE = "Call Date";
	private static String COLUMN_WORKSTEPNAME = "Suspended Item";
	private static String COLUMN_RESUME = "Resume Activity";
	private static String COLUMN_PID = "PID";
	private static String COLUMN_BRANCH = "Branch Name";
	private static String COLUMN_REOPENID = "Reopen ID";
	private static String COLUMN_UPLOADSCREEN = "User Document";
	private static String COLUMN_CATEGORY = "Category";
	private static String COLUMN_SUBCATEGORY = "Sub-Category";
	private static String COLUMN_CALLSTATUS = "Closure Category";
	private static String COLUMN_SUPPORTPERFORMER = "Resolved By / Assigned To";
	private static String COLUMN_APPSUPPORTCLOSEDTSTRING = "Resoved Date";
	private static String COLUMN_APPROVERNAME = "Approver Name";
	private static String COLUMN_APPROVERSTATUS = "Approver Status";
	private static String COLUMN_APPROVEDDATESTRING = "Approved Date";
	private static String COLUMN_APPSUPPORTSTATUS = "AppSupport Status";
	private static String COLUMN_PI_START_TIME = "Start Time";
	private static String COLUMN_WI_END_TIME = "Closed Date";
	private static String COLUMN_ELAPSED_TIME = "Total Elapsed Time";
	private static String COLUMN_TAT_TIME = "Total TAT";
	private static String COLUMN_USERREMARK = "User Remark";
	private static String COLUMN_APPSUPPORTREMARK = "AppSupport Remark";
	private static String COLUMN_APPROVERREMARK = "Approver Remark";
	private static String COLUMN_STATUS = "PI Status";
	private static String COLUMN_PERFORMER = "Current Performer";
	private static String COLUMN_APPROVERID = "Approver ID";
	private static String COLUMN_APPROVERID2 = "Approver ID2";
	private static String COLUMN_APPROVERDSGN = "Approver Designation";

	private boolean isValid(String value) {
		boolean b = false;
		if (value != null && value.trim().length() > 0) {
			b = true;
		}
		return b;
	}

	public HashMap getCallStatusReport(String sbmUser, String startDate,
			String endDate, String _status) throws Exception {
		HashMap hm = new HashMap();
		ArrayList completeData = new ArrayList();
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String reportName = "TeckDesk_TicketReport_";
		try {
			if (isValid(sbmUser) && isValid(startDate) && isValid(endDate)
					&& isValid(_status)) {
				SimpleDateFormat format = new SimpleDateFormat(
						"MMM dd, yyyy hh:mm a");
				String query = "{Call TECHDESK_OPEN_CLOSE_TICKET_DTL(?,?,?,?,?,?)}";
				DataSource ds = (DataSource) (new InitialContext())
				//		.lookup("jdbc/SBMCommonDB");
						.lookup("jdbc/CustomDB");
				conn = ds.getConnection();
				cstmt = conn.prepareCall(query);
				cstmt.setString(1, sbmUser);
				cstmt.setLong(2, format.parse(startDate).getTime());
				cstmt.setLong(3, format.parse(endDate).getTime());
				cstmt.setString(4, _status);
				cstmt.registerOutParameter(5, OracleTypes.CURSOR);
				cstmt.registerOutParameter(6, OracleTypes.CURSOR);
				cstmt.executeUpdate();
				rs = (ResultSet) cstmt.getObject(5);
				while (rs.next()) {
					DetailedReportDataBean bean1 = new DetailedReportDataBean();
					bean1.setIS_L1TEAMDATA("Y");
					bean1.setTICKETNO(rs.getString("TICKETNO"));
					bean1.setCALLTYPE(rs.getString("CALLTYPE"));
					bean1.setCALLCREATEDBY(rs.getString("CALLCREATEDBY"));
					bean1.setCALLLOGDATE(rs.getString("CALLLOGDATE"));
					bean1.setBRANCH(rs.getString("BRANCH"));
					bean1.setAPPLICATIONTYPE(rs.getString("APPLICATIONTYPE"));
					bean1.setISSUEREQUESTID(rs.getString("ISSUEREQUESTID"));
					bean1.setISSUEREQUESTSUBID(rs
							.getString("ISSUEREQSUBTYPEID"));
					bean1.setCALLSTATUS(rs.getString("CALLSTATUS"));
					bean1.setREOPENID(rs.getString("REOPENID"));
					bean1.setUPLOADSCREEN(rs.getString("UPLOADSCREEN"));
					bean1.setAPPROVALFINALSTATUS(rs
							.getString("APPROVALFINALSTATUS"));
					bean1.setAPPROVERFINALREMARK1(rs
							.getString("APPROVERFINALREMARK1"));
					bean1.setFINALAPPROVALDATE(rs
							.getString("FINALAPPROVALDATE"));
					bean1.setAPPSUPPORTPERFORMER(rs.getString("PERFORMER"));
					bean1.setAPPSUPPORTFINALSTATUS(rs
							.getString("APPSUPPORTFINALSTATUS"));
					bean1.setUSERREMARK(rs.getString("USERREMARK"));
					bean1.setAPPSUPPORTREMARK(rs.getString("APPSUPPORTREMARK"));
					bean1.setAPPSUPPORTCLOSEDTSTRING(rs
							.getString("APPSUPPORTCLOSEDTSTRING"));
					bean1.setAPPROVERNAME(rs.getString("approver1"));
					bean1.setAPPROVER2NAME(rs.getString("approver2"));
					completeData.add(bean1);
				}

				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException sqe) {
						throw new RuntimeException((new StringBuilder(
								"Resultset not Closed...")).append(
								sqe.getMessage()).toString());
					}
				}
				rs = (ResultSet) cstmt.getObject(6);
				while (rs.next()) {
					DetailedReportDataBean bean1 = new DetailedReportDataBean();
					bean1.setIS_L1TEAMDATA("N");
					bean1.setTICKETNO(rs.getString("TICKETNO"));
					bean1.setCALLTYPE(rs.getString("CALLTYPE"));
					bean1.setCALLCREATEDBY(rs.getString("CALLCREATEDBY"));
					bean1.setCALLLOGDATE(rs.getString("CALLLOGDATE"));
					bean1.setBRANCH(rs.getString("BRANCH"));
					bean1.setAPPLICATIONTYPE(rs.getString("APPLICATIONTYPE"));
					bean1.setISSUEREQUESTID(rs.getString("ISSUEREQUESTID"));
					bean1.setISSUEREQUESTSUBID(rs
							.getString("ISSUEREQSUBTYPEID"));
					bean1.setCALLSTATUS(rs.getString("CALLSTATUS"));
					bean1.setREOPENID(rs.getString("REOPENID"));
					bean1.setUPLOADSCREEN(rs.getString("UPLOADSCREEN"));
					bean1.setAPPROVALFINALSTATUS(rs
							.getString("APPROVALFINALSTATUS"));
					bean1.setAPPROVERFINALREMARK1(rs
							.getString("APPROVERFINALREMARK1"));
					bean1.setFINALAPPROVALDATE(rs
							.getString("FINALAPPROVALDATE"));
					bean1.setAPPSUPPORTPERFORMER(rs.getString("PERFORMER"));
					bean1.setAPPSUPPORTFINALSTATUS(rs
							.getString("APPSUPPORTFINALSTATUS"));
					bean1.setUSERREMARK(rs.getString("USERREMARK"));
					bean1.setAPPSUPPORTREMARK(rs.getString("APPSUPPORTREMARK"));
					bean1.setAPPSUPPORTCLOSEDTSTRING(rs
							.getString("APPSUPPORTCLOSEDTSTRING"));
					bean1.setAPPROVERNAME(rs.getString("approver1"));
					bean1.setAPPROVER2NAME(rs.getString("approver2"));
					completeData.add(bean1);
				}

				ArrayList columnName = new ArrayList();
				columnName.add(COLUMN_TICKET_NO);
				columnName.add(COLUMN_CALLTYPE);
				columnName.add(COLUMN_CALLCREATEDBY);
				columnName.add(COLUMN_CALLLOGDATE);
				columnName.add(COLUMN_BRANCH);
				columnName.add(COLUMN_APPLICATIONTYPE);
				columnName.add(COLUMN_CATEGORY);
				columnName.add(COLUMN_SUBCATEGORY);
				columnName.add(COLUMN_CALLSTATUS);
				columnName.add(COLUMN_REOPENID);
				columnName.add(COLUMN_UPLOADSCREEN);
				columnName.add(COLUMN_APPROVERSTATUS);
				columnName.add(COLUMN_APPROVERREMARK);
				columnName.add(COLUMN_APPROVEDDATESTRING);
				columnName.add(COLUMN_SUPPORTPERFORMER);
				columnName.add(COLUMN_APPSUPPORTSTATUS);
				columnName.add(COLUMN_USERREMARK);
				columnName.add(COLUMN_APPSUPPORTREMARK);
				columnName.add(COLUMN_APPSUPPORTCLOSEDTSTRING);
				columnName.add(COLUMN_APPROVERID);
				columnName.add(COLUMN_APPROVERID2);
				LinkedHashMap columnsExcel = new LinkedHashMap();
				columnsExcel.put(COLUMN_TICKET_NO, COLUMN_TICKET_NO);
				columnsExcel.put(COLUMN_CALLTYPE, COLUMN_CALLTYPE);
				columnsExcel.put(COLUMN_CALLCREATEDBY, COLUMN_CALLCREATEDBY);
				columnsExcel.put(COLUMN_CALLLOGDATE, COLUMN_CALLLOGDATE);
				columnsExcel.put(COLUMN_BRANCH, COLUMN_BRANCH);
				columnsExcel
						.put(COLUMN_APPLICATIONTYPE, COLUMN_APPLICATIONTYPE);
				columnsExcel.put(COLUMN_CATEGORY, COLUMN_CATEGORY);
				columnsExcel.put(COLUMN_SUBCATEGORY, COLUMN_SUBCATEGORY);
				columnsExcel.put(COLUMN_CALLSTATUS, COLUMN_CALLSTATUS);
				columnsExcel.put(COLUMN_REOPENID, COLUMN_REOPENID);
				columnsExcel.put(COLUMN_UPLOADSCREEN,COLUMN_UPLOADSCREEN);
				columnsExcel.put(COLUMN_APPROVERSTATUS, COLUMN_APPROVERSTATUS);
				columnsExcel.put(COLUMN_APPROVERREMARK, COLUMN_APPROVERREMARK);
				columnsExcel.put(COLUMN_APPROVEDDATESTRING,
						COLUMN_APPROVEDDATESTRING);
				columnsExcel.put(COLUMN_SUPPORTPERFORMER,
						COLUMN_SUPPORTPERFORMER);
				columnsExcel.put(COLUMN_APPSUPPORTSTATUS,
						COLUMN_APPSUPPORTSTATUS);
				columnsExcel.put(COLUMN_USERREMARK,
						COLUMN_USERREMARK);
				columnsExcel.put(COLUMN_APPSUPPORTREMARK,
						COLUMN_APPSUPPORTREMARK);
				columnsExcel.put(COLUMN_APPSUPPORTCLOSEDTSTRING,
						COLUMN_APPSUPPORTCLOSEDTSTRING);
				columnsExcel.put(COLUMN_APPROVERID, COLUMN_APPROVERID);
				columnsExcel.put(COLUMN_APPROVERID2, COLUMN_APPROVERID2);
				hm.put("columns", columnName);
				hm.put("ExcelColumn", columnsExcel);
				hm.put("data", completeData);
				hm.put("name", reportName);
			}
		} catch (Exception e) {
			System.out.println("Error while getting ---- : "+e.getMessage());
			throw new RuntimeException(
					"Error while getting getCompletedDataByUser", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlexception) {
				}
			}
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException sqlexception1) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlexception2) {
				}
			}
		}
		return hm;
	}

}
