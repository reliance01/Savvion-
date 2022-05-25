package pwr.calldesk;

import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
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

	private final static String TREE_DATASOURCE = "jdbc/SBMCommonDB";

	private Connection getSQLConnectionDB() {
		Connection con = null;
		String dbip = "";
		String dbuser = "";
		String dbpass = "";
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if (ip.contains("10.62.182.42")) {
				dbip = "RGIUDEVLOPMENT.reliancegeneral.com";
				dbuser = "calldesk";
				dbpass = "calldesk";
			} else {
				dbip = "RGIRMSCDDB";
				dbuser = "calldesk";
				dbpass = "calldesk";
			}
			con = DriverManager.getConnection("jdbc:sqlserver://" + dbip
					+ ":7359;databaseName=CalldeskManagement", dbuser, dbpass);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error in getting a DB connection  for db " + dbip + " \n"
							+ e.getMessage(), e);
		}
		return con;
	}

	private Connection getSBMConnectionDB() {
		Connection con = null;
		try {
			DataSource ds = (DataSource) new InitialContext()
					.lookup(TREE_DATASOURCE);
			con = ds.getConnection();
		} catch (Exception ex) {
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

			String ticketNo = (String) hm.get("ticket_no");
			String rcaType = (String) hm.get("rca_type");
			String rcaMember = (String) hm.get("rca_member");
			String rcaDate = (String) hm.get("rca_date");
			String rcaDetails = (String) hm.get("rca_details");
			String createdBy = (String) hm.get("createdby");
			System.out.println(ticketNo + "ticket" + createdBy + "byyyyy ");
			try {
				// update in calldesk database
				try {
					connection = getSQLConnectionDB();
					proc_stmt = connection
							.prepareCall("{ call USP_InsertRCA(?,?,?,?,?,?) }");
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
				} catch (Exception ex) {
					retVal = "FALSE";
					System.out.println("Error While savion CalldeskRCA" + ex.getMessage());
					return retVal;
				} finally {
					try {
						if (proc_stmt != null) {
							proc_stmt.close();
						}
					} catch (Exception ex) {
					}
					try {
						if (connection != null) {
							connection.close();
						}
					} catch (Exception ex) {
					}
				}
				// update in savvion database
				connection = getSBMConnectionDB();
				System.out.println("connection  -" + connection);
				String sql = "INSERT INTO TECHDESK_RCA VALUES (?,?,?,?,?,?,?)";
				pstmt = connection.prepareStatement(sql);

				pstmt.setString(1, ticketNo.trim());

				pstmt.setString(2, rcaType.trim());

				pstmt.setString(3, rcaMember.trim());

				DateFormat formatter;
				formatter = new SimpleDateFormat("MM-dd-yyyy");

				Date date = (Date) formatter.parse(rcaDate);
				java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
				pstmt.setTimestamp(4, timeStampDate);

				pstmt.setString(5, rcaDetails.trim());

				pstmt.setString(6, createdBy.trim());

				pstmt.setTimestamp(7, new java.sql.Timestamp(
						new java.util.Date().getTime()));

				pstmt.executeUpdate();

				pstmt.close();
				connection.close();
				retVal = "TRUE";
			}

			catch (Exception e) {
				retVal = "FALSE";
				System.out.println("Error While SavingRCA " + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (Exception ex) {
				}
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (Exception ex) {
				}
			}
		}
		return retVal;
	}
	
	/**
	 * This method will fetch RCA details from Savvion Database
	 * @param ticketno
	 * @return Arraylist of Arraylist of String type
	 * @author Dev Khatri
	 */
	public List<ArrayList<String>> fetchRCAData(String ticketno) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		List<ArrayList<String>> allRCAData = new ArrayList<ArrayList<String>>();
		try {
			connection = getSBMConnectionDB();
			String query = "select * from techdesk_rca where ticketno = ? order by created_date desc";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, ticketno.trim());

			rset = pstmt.executeQuery();
			if (rset != null) {
				while (rset.next()) {
					List<String> rcaData = new ArrayList<String>();
					rcaData.add(rset.getString("RCA_TYPE"));
					rcaData.add(rset.getString("RCA_TEAMMEMBERS"));
					rcaData.add(rset.getString("RCA_DEPLOYMENTDATE"));
					rcaData.add(rset.getString("RCA_DETAIL"));
					rcaData.add(rset.getString("CREATED_BY"));
					rcaData.add(rset.getString("CREATED_DATE"));
					allRCAData.add((ArrayList<String>) rcaData);
				}
				
			}

		} catch (Exception e) {
			System.out.println("Error while fetching RCA Details"
					+ e.getMessage());
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
			} catch (Exception e) {
				System.out
						.println("Error while closing connections method fetchRCA "
								+ e.getMessage());
			}
		}

		return allRCAData;

	}
	
	/**
	 * This method will get the dual approver flag for the ticketno from the database
	 * @param ticketno
	 * @return boolean 
	 * @author Deepak Daneva
	 */
	public boolean getApprover2StatusFlag(String ticketNo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		boolean flag = false;
		try {
			connection = getSQLConnectionDB();			
			String sqlQuery = "Exec usp_IsDualApprovalRestricationCategory '" + ticketNo.trim()+"'";
			pstmt = connection.prepareStatement(sqlQuery);
			rSet = pstmt.executeQuery();			
			while(rSet.next()){
				flag = rSet.getBoolean(1);
			}		
			System.out.println("DualApprover Flag in class is "+flag+" for ticketno "+ticketNo);
		} catch (Exception e) {
			System.out.println("Error while fetching Approver2 Status Flag: "
					+ e.getMessage());
			e.printStackTrace();
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
			} catch (Exception e) {
				System.out
						.println("Error while closing connections method getApprover2StatusFlag ");
				e.printStackTrace();
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
			connection = getSBMConnectionDB();
			String query = "SELECT CLOSURESUBCATEGORY FROM TECHDESK_CLOSURE_STATUS WHERE CLOSURECATEGORY = ? AND APPLICATIONNAME = ? ORDER BY CLOSURESUBCATEGORY ASC";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, selectedIndexValue.trim());
			pstmt.setString(2, applicationName.trim());

			rset = pstmt.executeQuery();
			if (rset != null) {
				while (rset.next()) {
					allOptions.add(rset.getString("CLOSURESUBCATEGORY"));
				}
				
			}

		} catch (Exception e) {
			System.out.println("Error while fetching drop down values"
					+ e.getMessage());
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
			} catch (Exception e) {
				System.out
						.println("Error while closing connections method getDropDownValue "
								+ e.getMessage());
			}
		}
		return allOptions;
	}

	public Map<String,String> getReassignPerformer(String teamName) {
		Map<String, String> performerDetails = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			connection = getSBMConnectionDB();
			String query = "select * from techdesk_admin_teamdetails where teamname = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, teamName.trim());

			rset = pstmt.executeQuery();
			System.out.println("11111111111111111111");
			performerDetails = new HashMap<String, String>();
			if (rset != null) {
				while (rset.next()) {
					performerDetails.put("l1performer",rset.getString("l1performer"));					
					performerDetails.put("l1PerformerEmail", rset.getString("l1performeremail"));
					performerDetails.put("l2Performer",rset.getString("l2performer"));
					performerDetails.put("l2PerformerEmail",rset.getString("l2performeremail"));
					performerDetails.put("l3Performer",rset.getString("l3performer"));
					performerDetails.put("l3PerformerEmail",rset.getString("l3performeremail"));
					performerDetails.put("l4Performer",rset.getString("l4performer"));
					performerDetails.put("l4PerformerEmail",rset.getString("l4performeremail"));
					
				}
				System.out.println("L1 :::::: performer :::"+performerDetails.get("l1performer"));
				
			}

		} catch (Exception e) {
			System.out.println("Error while fetching performer details values"
					+ e.getMessage());
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
			} catch (Exception e) {
				System.out
						.println("Error while closing connections method getReassignPerformer "
								+ e.getMessage());
			}
		}
		
		return performerDetails;
	}


}
