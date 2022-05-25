package com.agent.ejb.mail;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.agent.ejb.initiateagent.AgentFlowProcess;
import com.agent.ejb.initiateagent.GetResource;
import com.agent.portal.SqlUtility;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;

public class Mailer {

	private final String blUser = "rgicl";
	private final String blPassword = "rgicl";
	private static final String FROM = "rgicl.applnsupport@reliancegeneral.co.in";
	BLServer blserver = null;
	Session blsession = null;
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	ArrayList<java.util.Date> holidayList = new ArrayList<java.util.Date>();

	public static void main(String args[]) throws ClassNotFoundException{
		Mailer m = new Mailer();
		m.spocEscalation();
	}
	
	public Date EscalationDate(int days) {

		Date generatedDate = new Date();
		int workingDays = 0;
		int day1 = 0;
		int day2 = 0;
		int day3 = 0;
		Date currentDate = new Date();

		Mailer obj = new Mailer();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, days);
		generatedDate = c.getTime();
		System.out.println(generatedDate);
		workingDays = obj.getWorkingDaysBetweenTwoDates(currentDate,
				generatedDate);

		int difference = days - workingDays;
		System.out.println("Actual working days first Time " + workingDays
				+ " Difference " + difference);
		if (difference != 0) {
			day1 = days + difference;
			Calendar c1 = Calendar.getInstance();
			c1.setTime(currentDate);
			c1.add(Calendar.DATE, day1);
			generatedDate = c1.getTime();
			df.format(generatedDate);
			System.out.println(df.format(generatedDate));
			workingDays = obj.getWorkingDaysBetweenTwoDates(currentDate,
					generatedDate);
			difference = days - workingDays;
			System.out.println("Actual working days Second Time " + workingDays
					+ " Difference " + difference);

		}
		if (difference != 0) {
			day2 = day1 + difference;
			Calendar c1 = Calendar.getInstance();
			c1.setTime(currentDate);
			c1.add(Calendar.DATE, day2);
			generatedDate = c1.getTime();
			df.format(generatedDate);
			System.out.println(df.format(generatedDate));
			workingDays = obj.getWorkingDaysBetweenTwoDates(currentDate,
					generatedDate);
			difference = days - workingDays;
			System.out.println("Actual working days Third Time " + workingDays
					+ " Difference " + difference);

		}
		if (difference != 0) {
			day3 = day2 + difference;
			Calendar c1 = Calendar.getInstance();
			c1.setTime(currentDate);
			c1.add(Calendar.DATE, day3);
			generatedDate = c1.getTime();
			df.format(generatedDate);
			System.out.println(df.format(generatedDate));
			workingDays = obj.getWorkingDaysBetweenTwoDates(currentDate,
					generatedDate);
			difference = days - workingDays;
			System.out.println("Actual working days fourth Time " + workingDays
					+ " Difference " + difference);

		}

		return generatedDate;

	}

	public int getWorkingDaysBetweenTwoDates(java.util.Date startDate,
			java.util.Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		startCal.set(Calendar.MILLISECOND, 1);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.MILLISECOND, 1);

		int workDays = -1;

		if (df.format(startDate).equals(df.format(endDate))) {
			return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}

		try {
			do {
				if (!checkHoliday(startCal.getTime())
						&& !checkSecondSaturday(startCal.getTime())
						&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					workDays++;
				}
				startCal.add(Calendar.DAY_OF_MONTH, 1);
			} while (startCal.getTime().before(endCal.getTime())
					|| startCal.getTime().equals(endCal.getTime()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (workDays == -1) {
			workDays = 0;
		}
		return workDays;
	}

	public boolean checkSecondSaturday(java.util.Date date) {
		boolean isSecondSat = false;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY) {
			int weekOfMonth = c.get(Calendar.WEEK_OF_MONTH);
			if (weekOfMonth == 2 || weekOfMonth == 4) {
				isSecondSat = true;
			}
		}
		return isSecondSat;
	}

	public boolean checkHoliday(java.util.Date date) {
		boolean isHoliday = false;
		for (int i = 0; i < holidayList.size(); i++) {
			if (df.format(date).equals(df.format(holidayList.get(i)))) {
				isHoliday = true;
				break;
			}
		}
		return isHoliday;
	}

	public void getHolidays() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Calendar cal = Calendar.getInstance();
		try {
			connection = GetResource.getDBConnection();
			pstmt = connection
					.prepareCall("SELECT HOLIDAYDATE FROM RGICL_HOLIDAYS WHERE HOLIDAYYEAR = (SELECT TO_NUMBER(EXTRACT(YEAR FROM SYSDATE)) FROM DUAL)");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				java.util.Date holiday = new java.util.Date(rset.getDate(1)
						.getTime());
				Calendar holidayCal = Calendar.getInstance();
				holidayCal.setTime(holiday);
				cal.set(Calendar.YEAR, holidayCal.get(Calendar.YEAR));
				cal.set(Calendar.MONTH, holidayCal.get(Calendar.MONTH));
				cal.set(Calendar.DAY_OF_MONTH,
						holidayCal.get(Calendar.DAY_OF_MONTH));
				holidayList.add(cal.getTime());
			}
		} catch (Exception e) {
			System.out.println("Error in getting holiday list : "
					+ e.getMessage());
		} finally {
			GetResource.releaseResource(connection, pstmt);
			try {
				if (rset != null) {
					rset.close();
				}

			} catch (Exception e) {
				System.out
						.println("Error while closing the connetion -- method name = getMandatoryInfo() --"
								+ e.getMessage());
			}
		}
	}

	/**
	 * 
	 * This method will provide duedate for savvion Duedate Utility
	 * 
	 * @return
	 */
	public long getDueDate(int days) {
		Calendar duedate = null;
		long tatInMili = 0;
		try {
			Date dt = new Date();
			long TAT = days * 8 * 60 * 60 * 1000;
			SBMCalendar.init(null);
			SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
			BizCalendar bcal = new BizCalendar(dt.getTime(), TAT);
			duedate = sbmcal.getDueDate(bcal);
			Date calculatedDate = duedate.getTime();
			tatInMili = calculatedDate.getTime();
			
		} catch (Throwable th) {
			System.out.println("Error while getting duedate \n"
					+ th.getMessage());
		}
		return tatInMili;
	}

	/**
	 * This method will update 2nd dueDate and escalation count for SPOC
	 * Escalation
	 */
	@SuppressWarnings("resource")
	public void updateSpocEscalationFlag(int escalationCount, Long piid,
			String spoc) {
		System.out.println("Inside updateSpocEscalationFlag method.");
		Connection connection = null;
		PreparedStatement pstmt = null;
		connection = getJDBCConnection();
		try {
			System.out.println("Escalation count is " + escalationCount);
			if (escalationCount == 0) {
				System.out.println("Inside count 1");
				java.util.Date javaDate = EscalationDate(2);
				java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
				String query = "UPDATE AGENTBMGRID SET DUE_DATE = to_date( ? ,'dd-MM-yyyy'), ESCALATE_COUNT = ? WHERE PROCESS_INSTANCE_ID = ? AND STATUS = 'Pending' AND SPOC = ?";
				pstmt = connection.prepareCall(query);
				pstmt.setDate(1, sqlDate);
				pstmt.setInt(2, 1);
				pstmt.setLong(3, piid);
				pstmt.setString(4, spoc);
			}
			if (escalationCount == 1) {
				System.out.println("Inside count 2");
				String query = "UPDATE AGENTBMGRID SET ESCALATE_COUNT = ? WHERE PROCESS_INSTANCE_ID = ? AND STATUS = 'Pending' AND SPOC = ?";
				pstmt = connection.prepareCall(query);
				pstmt.setInt(1, 2);
				pstmt.setLong(2, piid);
				pstmt.setString(3, spoc);
			}
			pstmt.executeUpdate();
			System.out
					.println("Method called sucessfully updateSpocEscalationFlag");
		} catch (Exception e) {
			System.out
					.println("Error while updating updateSpocEscalationFlag()");
		} finally {
			//GetResource.releaseResource(connection, pstmt);
			try
			{
			if(connection != null)
			{
				connection.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
			}
			catch(Exception e)
			{
				System.out.println("Error while closing connection !!" + e.getMessage());
			}
		}

	}

	/*
	 * This method will update 2nd dueDate and escalation count for performer(BM,CM,RM&RH)
	 * */
	@SuppressWarnings("resource")
	public void updatePerformerEscalationFlag(int escalationCount, Long piid,
			String performer)
	{
		System.out.println("Inside updatePerformerEscalationFlag method.");
		Connection connection = null;
		PreparedStatement pstmt = null;
		connection = GetResource.getDBConnection();
		try {
			System.out.println("Escalation count is " + escalationCount);
			if (escalationCount == 0) {
				System.out.println("Inside count 1");
				java.util.Date javaDate = EscalationDate(2);
				java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
				String query = "UPDATE AGENT_ESCALATION_TBL SET ESCALATE_DATE = to_date( ? ,'dd-MM-yyyy'), COUNT = ? WHERE PROCESS_INSTANCE_ID = ? AND STATUS = 'Pending' AND PERFORMER = ?";
				pstmt = connection.prepareCall(query);
				pstmt.setDate(1, sqlDate);
				pstmt.setInt(2, 1);
				pstmt.setLong(3, piid);
				pstmt.setString(4, performer);
				pstmt.executeUpdate();
			}
			if (escalationCount == 1) {
				System.out.println("Inside count 2");
				String query = "UPDATE AGENT_ESCALATION_TBL SET COUNT = ? WHERE PROCESS_INSTANCE_ID = ? AND STATUS = 'Pending' AND PERFORMER = ?";
				pstmt = connection.prepareCall(query);
				pstmt.setInt(1, 2);
				pstmt.setLong(2, piid);
				pstmt.setString(3, performer);
				pstmt.executeUpdate();
			}
			
			System.out
					.println("Method called sucessfully updateSpocEscalationFlag");
		} catch (Exception e) {
			System.out
					.println("Error while updating updatePerformerEscalationFlag()");
		} finally {
			GetResource.releaseResource(connection, pstmt);
		}

	}
	
	public Connection getJDBCConnection()
	{
		Connection connection = null;
		try
		{
		System.out.println("Before class load");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("After class load & before DriverManager");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@10.65.5.44:1865:RGIORA12C", "ebms","ebms");	
		System.out.println("After Driver Manager " + connection);
		}catch (Exception e)
		{
			System.out.println("Error while getting Connection from method getJDBCConnection" + e.getMessage());
		}
		return connection;
	}

	
	public void spocEscalation(){
		System.out.println("Inside spocEscalation method");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		java.util.Date utilDate = new java.util.Date();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		String sqlDate = formatter.format(utilDate);

		try {

			
			connection = getJDBCConnection();
			String query = " select process_instance_id,spoc,bm_submit_date,escalate_count from agentbmgrid where due_date <= to_date( ? ,'dd-MM-yyyy') and status = 'Pending' and escalate_count in (0,1)";
			pstmt = connection.prepareStatement(query);

			pstmt.setString(1, sqlDate);
			rset = pstmt.executeQuery();

			if (rset != null) {

				while (rset.next()) {
					String piid = rset.getString("process_instance_id");
					String spoc = rset.getString("spoc");
					String assignedDate = formatter.format(rset
							.getDate("bm_submit_date"));
					int escalationCount = rset.getInt(4);
					spocEscalationMail(piid, spoc, assignedDate,
							escalationCount);

				}
			}

		} catch (SQLException e) {
			System.out.println("Error while calling spocEscalation method"
					+ e.getMessage());
		} finally {
			//GetResource.releaseResource(connection, pstmt);
			try {
				if (rset != null) {
					rset.close();
				}
				if (connection != null) {
					connection.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}


			} catch (Exception e) {
				System.out
						.println("Error while closing the connetion -- method name = spocEscalation() --"
								+ e.getMessage());
			}

		}

	}

	/**
	 * This method will use to get SPOC Escalation matrix.
	 * 
	 * @param spocId
	 * @return
	 */
	public HashMap<String, String> getSpocEscalationMatrix(String spocId) {

		System.out.println("Inside getSpocEscalationMatrix method !!!");
		HashMap<String, String> spocEscalationMatrix = new HashMap<String, String>();
		try {
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			if (spocId != null) {
				try {
					connection = getJDBCConnection();
					String query = "select first_escalation_name, first_escalation_mailid,second_escalation_name, second_escalation_mailid "
							+ "from agent_spoc_escalation_matrix where spoc_id = ? ";
					pstmt = connection.prepareCall(query);
					pstmt.setString(1, spocId);
					rset = pstmt.executeQuery();
					while (rset.next()) {
						spocEscalationMatrix.put("first_escalation_name",
								rset.getString("first_escalation_name"));
						spocEscalationMatrix.put("first_escalation_mailid",
								rset.getString("first_escalation_mailid"));
						spocEscalationMatrix.put("second_escalation_name",
								rset.getString("second_escalation_name"));
						spocEscalationMatrix.put("second_escalation_mailid",
								rset.getString("second_escalation_mailid"));
					}
				} catch (Exception e) {
					System.out
							.println("Error while getting spocEscalationMatrix!! "
									+ e.getMessage());
				} finally {
					//GetResource.releaseResource(connection, pstmt);
					try {
						if (rset != null) {
							rset.close();
						}
						if (connection != null) {
							connection.close();
						}
						if (pstmt != null) {
							pstmt.close();
						}

					} catch (Exception e) {
						System.out
								.println("Error while closing the connetion -- method name = getMandatoryInfo() --"
										+ e.getMessage());
					}

				}
			}

		} catch (Exception e) {
			System.out.println("Error in escalation method" + e.getMessage());
		}

		return spocEscalationMatrix;
	}

	/**
	 * This method will send SPOC Escalation Mail
	 * 
	 * @param piid
	 * @param spoc
	 * @param assignedDate
	 * @param escalationCount
	 */
	public void spocEscalationMail(String piid, String spoc,
			String assignedDate, int escalationCount) {

		try {
			System.out.println("Inside spocEscalationMail Method !!!");
			String spocId = spoc.split("-")[1].trim();
			String spocName = spoc.split("-")[0].trim();
			HashMap<String, String> escalationInfo = getSpocEscalationMatrix(spocId);
			System.out.println("after escalation method run fine!!!");
			String firstEscalationMailID = escalationInfo
					.get("first_escalation_mailid");
			String firstEscalationName = escalationInfo
					.get("first_escalation_name");
			String secondEscalationMailID = escalationInfo
					.get("second_escalation_mailid");
			String secondEscalationName = escalationInfo
					.get("second_escalation_name");

			AgentFlowProcess obj = new AgentFlowProcess();
			String mandatoryCCID = obj.getMandatoryInfo().split(",")[0].trim();
			System.out.println("before if condition count is "
					+ escalationCount);
			if (escalationCount == 0) {
				System.out
						.println("Inside if condition of first escalation count !!!");
				if (firstEscalationMailID != null
						&& firstEscalationName != null) {
					HashMap<String, String> prop = new HashMap<String, String>();
					prop.put("TO", firstEscalationMailID.trim());
					prop.put("CC", mandatoryCCID);
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Agent Retention SPOC Escalation");
					prop.put(
							"BODY",
							spocFirstEscalationBody(piid, firstEscalationName,
									spocName, assignedDate));
					 EmailSender objemailsender = new EmailSender();
					 objemailsender.send(prop);
					long process_instance_id = Long.parseLong(piid);

					updateSpocEscalationFlag(escalationCount,
							process_instance_id, spoc);
					new ExceptionLogMaintain().mailSent_Log(
							process_instance_id, firstEscalationMailID.trim(),
							"-", "spoc_first_escalation");

				}
			}
			if (escalationCount == 1) {
				System.out
						.println("Inside if condition of second escalation count !!!");
				if (secondEscalationMailID != null
						&& secondEscalationName != null) {
					HashMap<String, String> prop = new HashMap<String, String>();
					prop.put("TO", secondEscalationMailID.trim());
					prop.put("CC", mandatoryCCID);
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Agent Retention SPOC Escalation");
					prop.put(
							"BODY",
							spocSecondEscalationBody(piid,
									secondEscalationName, spocName,
									assignedDate));
					 EmailSender objemailsender = new EmailSender();
					objemailsender.send(prop);
					long process_instance_id = Long.parseLong(piid);
					updateSpocEscalationFlag(escalationCount,
							process_instance_id, spoc);
					new ExceptionLogMaintain().mailSent_Log(
							process_instance_id, firstEscalationMailID.trim(),
							"-", "spoc_second_escalation");
				}

			}

		} catch (Exception e) {
			System.out.println("Error while sending SPOC Escalation Mail "
					+ e.getMessage());
			StringWriter errors = new StringWriter();
			long piID = Long.parseLong(piid);
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(e), "spocEscalationMail", e.getMessage(),
					errors.toString());
		}
	}

	/**
	 * This method will trigger Performer Escalation (BM,CM,RM & RH ) mail Method
	 */
	public void performerEscalation()
	{
		System.out.println("Inside performerEscalation method");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		java.util.Date utilDate = new java.util.Date();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		String sqlDate = formatter.format(utilDate);

		try {

			connection = GetResource.getDBConnection();
			String query = " select process_instance_id,performer,role,assigned_date,count from agent_escalation_tbl where escalate_date = to_date( ? ,'dd-MM-yyyy') and status = 'Pending' and count in (0,1)";
			pstmt = connection.prepareStatement(query);

			pstmt.setString(1, sqlDate);
			rset = pstmt.executeQuery();

			if (rset != null) {

				while (rset.next()) {
					String piid = rset.getString("process_instance_id");
					String performer = rset.getString("performer");
					String role = rset.getString("role");
					String assignedDate = formatter.format(rset
							.getDate("assigned_date"));
					int escalationCount = rset.getInt(5);
					performerEscalationMail(piid, performer, role, assignedDate,
							escalationCount);

				}
			}

		} catch (SQLException e) {
			System.out.println("Error while calling performerEscalation method"
					+ e.getMessage());
		} finally {
			GetResource.releaseResource(connection, pstmt);
			try {
				if (rset != null) {
					rset.close();
				}

			} catch (Exception e) {
				System.out
						.println("Error while closing the connetion -- method name = performerEscalation() --"
								+ e.getMessage());
			}

		}

		
	}
	/**
	 * This method will send BM/CM and RM/RH Escalation Mail
	 * 
	 * @param piid
	 * @param performer( BM/CM & RM/RH)
	 * @param escalationCount
	 */
	public void performerEscalationMail(String piid, String performer, String role, String assigned_date
			,int escalationCount) {

		try {
			System.out.println("Inside performerEscalationMail Method !!!");
			SqlUtility obj = new SqlUtility();
			
			String performerName = obj.getEmployeeDetails(performer).split(",")[0];
			
			HashMap<String, String> escalationInfo = obj.getPerformerEscalationDetail(performer);
		
			String firstEscalationMailID = escalationInfo
					.get("firstMailId");
			String firstEscalationName = escalationInfo
					.get("firstName");
			String secondEscalationMailID = escalationInfo
					.get("secondIdMailId");
			String secondEscalationName = escalationInfo
					.get("secondName");

			AgentFlowProcess obj2 = new AgentFlowProcess();
			String mandatoryCCID = obj2.getMandatoryInfo().split(",")[0].trim();
			System.out.println("before if condition count is "
					+ escalationCount);
			
			if (escalationCount == 0) {
				System.out
						.println("Inside if condition of first escalation count !!!");
				if (firstEscalationMailID != null
						&& firstEscalationName != null) {
					HashMap<String, String> prop = new HashMap<String, String>();
					prop.put("TO", firstEscalationMailID.trim());
					prop.put("CC", mandatoryCCID);
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Agent Retention Escalation");
					prop.put(
							"BODY",
							performerFirstEscalationBody(piid, firstEscalationName,
									performerName));
					 EmailSender objemailsender = new EmailSender();
					 objemailsender.send(prop);
					long process_instance_id = Long.parseLong(piid);
					updatePerformerEscalationFlag(escalationCount, process_instance_id,
							performer);
					
					new ExceptionLogMaintain().mailSent_Log(
							process_instance_id, firstEscalationMailID.trim(),
							"-", "performer_first_escalation");

				}
			}
			if (escalationCount == 1 && role.equals("BMCM")) {
				System.out
						.println("Inside if condition of second escalation count !!!");
				if (secondEscalationMailID != null
						&& secondEscalationName != null) {
					HashMap<String, String> prop = new HashMap<String, String>();
					prop.put("TO", secondEscalationMailID.trim());
					prop.put("CC", mandatoryCCID);
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Agent Retention Escalation");
					prop.put(
							"BODY",
							performerSecondEscalationBody(piid,
									secondEscalationName, performerName));
					EmailSender objemailsender = new EmailSender();
					objemailsender.send(prop);
					
					long process_instance_id = Long.parseLong(piid);
					updatePerformerEscalationFlag(escalationCount, process_instance_id,
							performer);
					new ExceptionLogMaintain().mailSent_Log(
							process_instance_id, secondEscalationMailID.trim(),
							"-", "performer_second_escalation");
				}

			}

		} catch (Exception e) {
			System.out.println("Error while sending BM/CM & RM/RH Escalation Mail "
					+ e.getMessage());
			StringWriter errors = new StringWriter();
			long piID = Long.parseLong(piid);
			new ExceptionLogMaintain().mailSendingError_Log(piID,
					getErrorGeneratorMethod(e), "performerEscalationMail", e.getMessage(),
					errors.toString());
		}
	}
	
	/**
	 * This method will send a notification Email to BM/CM & RM/RH when task
	 * assigned.
	 */
	public void bmTaskNotify(Long piid, String mailBody) {
		String userMailID = null;
		String userName = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(blUser, blPassword);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
			String bmId = (String) pi.getDataSlotValue("bm");
			if (bmId != null && bmId.trim().length() > 0) {
				AgentFlowProcess objagentflowprocess = new AgentFlowProcess();
				String userDetail = objagentflowprocess
						.getEmployeeDetails(bmId);

				userName = userDetail.trim().split(",")[0];
				userMailID = userDetail.trim().split(",")[1];
				if (userDetail != null && userDetail.length() > 0) {
					HashMap<String, String> prop = new HashMap<String, String>();
					prop.put("TO", userMailID.trim());
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Agent Retention Task Assiged");
					if (mailBody.equals("callupdation")) {
						prop.put("BODY", taskAssignedMailBody(userName, piid));
					} else if (mailBody.equals("immediatemanager")) {
						prop.put(
								"BODY",
								immediateManagerReplyNotifyBMMailBody(userName,
										piid));
					} else {
						prop.put("BODY",
								spocResolutionNotifyBMMailBody(userName, piid));
					}
					 EmailSender objemailsender = new EmailSender();
					 objemailsender.send(prop);
					new ExceptionLogMaintain().mailSent_Log(piid,
							userMailID.trim(), "-", "bmTaskNotify");

				}
			}
		} catch (Exception e) {
			System.out.println("Error in bmTaskNotify" + e.getMessage());
			StringWriter errors = new StringWriter();
			new ExceptionLogMaintain().mailSendingError_Log(piid,
					getErrorGeneratorMethod(e), "bmTaskNotify", e.getMessage(),
					errors.toString());

		}

	}

	/**
	 * This method will send a notification Email to BM/CM & RM/RH after all
	 * SPOC Submitted Their Remarks.
	 */
	public void bmTaskNotifyComplete(Long piid, String mailBody) {
		String userMailID = null;
		String userName = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(blUser, blPassword);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
			String bmId = (String) pi.getDataSlotValue("bm");
			if (bmId != null && bmId.trim().length() > 0) {

				SqlUtility objsqlutility = new SqlUtility();
				String userDetail = objsqlutility.getEmployeeDetails(bmId);

				userName = userDetail.trim().split(",")[0];
				userMailID = userDetail.trim().split(",")[1];
				if (userDetail != null && userDetail.length() > 0) {
					HashMap<String, String> prop = new HashMap<String, String>();
					prop.put("TO", userMailID.trim());
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Agent Retention Task Assiged");
					if (mailBody.equals("callupdation")) {
						prop.put("BODY", taskAssignedMailBody(userName, piid));
					} else if (mailBody.equals("immediatemanager")) {
						prop.put(
								"BODY",
								immediateManagerReplyNotifyBMMailBody(userName,
										piid));
					} else {
						prop.put("BODY",
								spocResolutionNotifyBMMailBody(userName, piid));
					}
					 EmailSender objemailsender = new EmailSender();
					 objemailsender.send(prop);
					new ExceptionLogMaintain().mailSent_Log(piid,
							userMailID.trim(), "-", "bmTaskNotify");

				}
			}
		} catch (Exception e) {
			System.out.println("Error in bmTaskNotify" + e.getMessage());
			StringWriter errors = new StringWriter();
			new ExceptionLogMaintain().mailSendingError_Log(piid,
					getErrorGeneratorMethod(e), "bmTaskNotify", e.getMessage(),
					errors.toString());

		}

	}

	/**
	 * This method will send a notification Email to ImmediateManager when task
	 * assigned.
	 * 
	 * @param piid
	 */
	public void immediateManagerTaskNotify(Long piid) {
		String userMailID = null;
		String userName = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(blUser, blPassword);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
			String bmId = (String) pi.getDataSlotValue("bm");
			if (bmId != null && bmId.trim().length() > 0) {
				AgentFlowProcess objagentflowprocess = new AgentFlowProcess();
				String immediateMgrId = objagentflowprocess
						.getImmediateMgr(bmId);
				String userDetail = objagentflowprocess
						.getEmployeeDetails(immediateMgrId);
				userName = userDetail.trim().split(",")[0];
				userMailID = userDetail.trim().split(",")[1];
				if (userDetail != null && userDetail.length() > 0) {
					HashMap<String, String> prop = new HashMap<String, String>();
					prop.put("TO", userMailID.trim());
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Agent Retention Task Assiged");
					prop.put("BODY", taskAssignedMailBody(userName, piid));
					 EmailSender objemailsender = new EmailSender();
					 objemailsender.send(prop);
					new ExceptionLogMaintain().mailSent_Log(piid,
							userMailID.trim(), "-",
							"immediateManagerTaskNotify");

				}
			}
		} catch (Exception e) {
			System.out.println("Error in immediateManagerTaskNotify"
					+ e.getMessage());
			StringWriter errors = new StringWriter();
			new ExceptionLogMaintain().mailSendingError_Log(piid,
					getErrorGeneratorMethod(e), "immediateManagerTaskNotify",
					e.getMessage(), errors.toString());

		}

	}

	/**
	 * This method will send a notification mail to all selected spoc by BM
	 * 
	 * @param piid
	 */
	public void spocTaskNotify(Long piid) {

		String userName = null;
		try {
			blserver = BLClientUtil.getBizLogicServer();
			blsession = blserver.connect(blUser, blPassword);
			ProcessInstance pi = blserver.getProcessInstance(blsession, piid);
			String spocId = (String) pi.getDataSlotValue("spocs");

			spocId = spocId.substring(0, spocId.length() - 9);
			String[] spocIdArray = spocId.split(",");
			if (spocId != null && spocId.trim().length() > 0) {
				String[] mailIds = getMailIds(spocIdArray);
				if (mailIds != null) {
					HashMap<String, Object> prop = new HashMap<String, Object>();
					prop.put("TO", mailIds);
					prop.put("FROM", FROM);
					prop.put("SUBJECT", "Agent Retention Task Assiged");
					prop.put("BODY", taskAssignedMailBody("SPOC", piid));
					 EmailSender objemailsender = new EmailSender();
					objemailsender.sendMutliple_To(prop);
					new ExceptionLogMaintain().mailSent_Log(piid,
							convertToCommaDelimited(mailIds), "-",
							"spocTaskNotify");

				}
			}
		} catch (Exception e) {
			System.out.println("Error in spocTaskNotify" + e.getMessage());
			StringWriter errors = new StringWriter();
			new ExceptionLogMaintain().mailSendingError_Log(piid,
					"spocTaskNotify method", "spocTaskNotify", e.getMessage(),
					errors.toString());

		}

	}

	/**
	 * This method will send notification mail when a SPOC task reassign to another SPOC
	 * @param piid
	 * @param userId
	 */
	public void reassignedSpocTaskNotify(Long piid, String userId) {
		String userMailID = null;
		String userName = null;
		try {

			SqlUtility objsqlutility = new SqlUtility();
			String userDetail = objsqlutility.getEmployeeDetails(userId);
			userName = userDetail.trim().split(",")[0];
			userMailID = userDetail.trim().split(",")[1];
			if (userDetail != null && userDetail.length() > 0) {
				HashMap<String, String> prop = new HashMap<String, String>();
				prop.put("TO", userMailID.trim());
				prop.put("FROM", FROM);
				prop.put("SUBJECT", "Agent Retention Task Assiged");
				prop.put("BODY", taskAssignedMailBody(userName, piid));
				 EmailSender objemailsender = new EmailSender();
				 objemailsender.send(prop);
				new ExceptionLogMaintain().mailSent_Log(piid,
						userMailID.trim(), "-", "reassignedspocTaskNotify");

			}

		} catch (Exception e) {
			System.out.println("Error in spocreassignTaskNotify" + e.getMessage());
			StringWriter errors = new StringWriter();
			new ExceptionLogMaintain().mailSendingError_Log(piid,
					getErrorGeneratorMethod(e), "reassignedspocTaskNotify",
					e.getMessage(), errors.toString());

		}

	}

	public void reassignedBmTaskNotify(Long piid, String userId) {
		String userMailID = null;
		String userName = null;
		try {

			SqlUtility objsqlutility = new SqlUtility();
			String userDetail = objsqlutility.getEmployeeDetails(userId);
			userName = userDetail.trim().split(",")[0];
			userMailID = userDetail.trim().split(",")[1];
			if (userDetail != null && userDetail.length() > 0) {
				HashMap<String, String> prop = new HashMap<String, String>();
				prop.put("TO", userMailID.trim());
				prop.put("FROM", FROM);
				prop.put("SUBJECT", "Agent Retention Task Assiged");
				prop.put("BODY", taskAssignedMailBody(userName, piid));
				 EmailSender objemailsender = new EmailSender();
				 objemailsender.send(prop);
				new ExceptionLogMaintain().mailSent_Log(piid,
						userMailID.trim(), "-", "reassignedBmTaskNotify");

			}

		} catch (Exception e) {
			System.out.println("Error in bmTaskNotify" + e.getMessage());
			StringWriter errors = new StringWriter();
			new ExceptionLogMaintain().mailSendingError_Log(piid,
					getErrorGeneratorMethod(e), "reassignedBmTaskNotify",
					e.getMessage(), errors.toString());

		}

	}

	/**
	 * This method will return method name in which error occurred.
	 * 
	 * @param cause
	 * @return
	 */
	private String getErrorGeneratorMethod(final Throwable cause) {
		Throwable rootCause = cause;
		if (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
			rootCause = rootCause.getCause();
		}
		if (rootCause != null) {
			return rootCause.getStackTrace()[0].getMethodName();
		} else
			return "";
	}

	/**
	 * This method will insert Unsent Mail with body in custom table
	 * @param TO
	 * @param CC
	 * @param Subject
	 * @param Body
	 * @param error
	 */
	public void saveUnsentMail(String TO, String CC, String Subject, String Body, String error)
	{
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		try
		{
			connection = getJDBCConnection();
			String query = " INSERT INTO AGENT_UNSENT_MAIL VALUES (?,?,?,?,?)";
			pstmt = connection.prepareCall(query);
			pstmt.setString(1, TO);
			pstmt.setString(2, CC);
			pstmt.setString(3, Subject);
			pstmt.setString(4, Body);
			pstmt.setString(5, error);
			pstmt.executeUpdate();
			
			
		}catch(Exception e)
		{
			System.out.println("Error while saving the unsent mails" + e.getMessage());
		}finally {
			//GetResource.releaseResource(connection, pstmt);
			try {
				if(connection != null)
				{
					connection.close();
				}
				if (pstmt != null)
				{
					pstmt.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * This method will provide array of Email
	 * 
	 * @param userArr
	 * @return
	 */
	private String[] getMailIds(String[] userArr) {
		String[] membersMailID = null;
		if (userArr != null && userArr.length > 0) {
			AgentFlowProcess objagentflowprocess = new AgentFlowProcess();
			ArrayList<String> mailIDs = new ArrayList<String>();
			for (int i = 0; i < userArr.length; i++) {
				if (userArr[i] != null && userArr[i].trim().length() > 0) {
					String mailID = objagentflowprocess
							.getEmployeeDetails(userArr[i].trim());
					if (mailID != null && mailID.trim().length() > 0
							&& mailID.contains("@")) {
						mailIDs.add(mailID.trim().split(",")[1]);
					}
				}
			}
			membersMailID = new String[mailIDs.size()];
			membersMailID = mailIDs.toArray(membersMailID);
		}
		return membersMailID;
	}

	private String convertToCommaDelimited(String[] list) {
		StringBuffer ret = new StringBuffer("");
		for (int i = 0; list != null && i < list.length; i++) {
			ret.append(list[i]);
			if (i < list.length - 1) {
				ret.append(',');
			}
		}
		return ret.toString();
	}

	/**
	 * This method will return Task Assigned Mail Body.
	 * 
	 * @param user
	 * @param piid
	 * @return
	 */
	private String taskAssignedMailBody(String user, Long piid) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/>Please find herewith the Ticket No. "+piid+" Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
		sb.append("<br/><br/>Please reply / Complete reference ticket in Savvion.");
		sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.80/sbm/bpmportal/login.jsp\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> Team Agency CO");
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	/**
	 * This method will return SPOC First Escalation MailBody
	 */
	public String spocFirstEscalationBody(String piid, String user,
			String spoc, String assignedDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/>Please find herewith the Ticket No. "+piid+" Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
		sb.append("<br/>Same is pending for action with "+ spoc +" Level since last 5 Days.");
		sb.append("<br/><br/>You are requested to take necessary action for closure on priority.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> Team Agency CO");
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();

	}
	
	/**
	 * This method will return BM/CM RM/RH as performer First Escalation MailBody
	 */
	public String performerFirstEscalationBody(String piid, String user,
			String performer) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/>Please find herewith the Ticket No. "+piid+" Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
		sb.append("<br/>Same is pending for action with "+ performer +" Level since last 5 Days.");
		sb.append("<br/><br/>You are requested to take necessary action for closure on priority.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> Team Agency CO");
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}
	
	/**
	 * This method will return BM/CM RM/RH as performer Second Escalation MailBody
	 * @param piid
	 * @param user
	 * @param performer
	 * @return
	 */
	public String performerSecondEscalationBody(String piid, String user,
			String performer) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/>Please find herewith the Ticket No. "+piid+" Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
		sb.append("<br/>Same is pending for action with "+ performer +" Level since last 7 Days.");
		sb.append("<br/><br/>You are requested to take necessary action for closure on priority.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> Team Agency CO");
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	/**
	 * This method will return SPOC Second Escalation MailBody
	 * 
	 * @param piid
	 * @param user
	 * @param spoc
	 * @param assignedDate
	 * @return
	 */
	public String spocSecondEscalationBody(String piid, String user,
			String spoc, String assignedDate) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/>Please find herewith the Ticket No. "+piid+" Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
		sb.append("<br/>Same is pending for action with "+ spoc +" Level since last 7 Days.");
		sb.append("<br/><br/>You are requested to take necessary action for closure on priority.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> Team Agency CO");
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}

	/**
	 * This method will return Task Assigned Mail Body when All spoc submit
	 * their remarks .
	 * 
	 * @param user
	 * @param piid
	 * @return
	 */
	private String spocResolutionNotifyBMMailBody(String user, Long piid) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/>Agent Retention Unique Reference No. "
				+ piid
				+ " has been assigned to your ID for action after SPOC resolution.");
		sb.append("<br/>See below link to open Process");
		sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.80/sbm/bpmportal/login.jsp\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> Team Agency CO");;
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		System.out.println("inside spocResolutionNotifyBMMailBody");
		return sb.toString();
		
	}

	/**
	 * This method will return Task Assigned Mail Body when All spoc submit
	 * their remarks .
	 * 
	 * @param user
	 * @param piid
	 * @return
	 */
	private String immediateManagerReplyNotifyBMMailBody(String user, Long piid) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
		sb.append("Dear " + user + ",");
		sb.append("<br/><br/>Agent Retention Unique Reference No. "
				+ piid
				+ " has been assigned to your ID for action after Immediatemanager resolution.");
		sb.append("<br/>See below link to open Process");
		sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.80/sbm/bpmportal/login.jsp\">here</a> </b> to login into the system.");
		sb.append("<br/><br/>Regards,");
		sb.append("<br/> Team Agency CO");
		sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<br/></body></html>");
		return sb.toString();
	}
}
