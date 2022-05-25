package com.agent.ejb.mail;

import com.agent.ejb.initiateagent.AgentFlowProcess;
import com.agent.ejb.initiateagent.GetResource;
import com.agent.portal.SqlUtility;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class Mailer {
   private final String blUser = "rgicl";
   private final String blPassword = "rgicl";
   private static final String FROM = "rgicl.applnsupport@reliancegeneral.co.in";
   BLServer blserver = null;
   Session blsession = null;
   DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
   ArrayList<Date> holidayList = new ArrayList();

   public Date EscalationDate(int days) {
      new Date();
      int workingDays = 0;
      int day1 = 0;
      int day2 = 0;
      int day3 = 0;
      Date currentDate = new Date();
      Mailer obj = new Mailer();
      Calendar c = Calendar.getInstance();
      c.setTime(currentDate);
      c.add(5, days);
      Date generatedDate = c.getTime();
      System.out.println(generatedDate);
      workingDays = obj.getWorkingDaysBetweenTwoDates(currentDate, generatedDate);
      int difference = days - workingDays;
      System.out.println("Actual working days first Time " + workingDays + " Difference " + difference);
      Calendar c1;
      if (difference != 0) {
         day1 = days + difference;
         c1 = Calendar.getInstance();
         c1.setTime(currentDate);
         c1.add(5, day1);
         generatedDate = c1.getTime();
         this.df.format(generatedDate);
         System.out.println(this.df.format(generatedDate));
         workingDays = obj.getWorkingDaysBetweenTwoDates(currentDate, generatedDate);
         difference = days - workingDays;
         System.out.println("Actual working days Second Time " + workingDays + " Difference " + difference);
      }

      if (difference != 0) {
         day2 = day1 + difference;
         c1 = Calendar.getInstance();
         c1.setTime(currentDate);
         c1.add(5, day2);
         generatedDate = c1.getTime();
         this.df.format(generatedDate);
         System.out.println(this.df.format(generatedDate));
         workingDays = obj.getWorkingDaysBetweenTwoDates(currentDate, generatedDate);
         difference = days - workingDays;
         System.out.println("Actual working days Third Time " + workingDays + " Difference " + difference);
      }

      if (difference != 0) {
         day3 = day2 + difference;
         c1 = Calendar.getInstance();
         c1.setTime(currentDate);
         c1.add(5, day3);
         generatedDate = c1.getTime();
         this.df.format(generatedDate);
         System.out.println(this.df.format(generatedDate));
         workingDays = obj.getWorkingDaysBetweenTwoDates(currentDate, generatedDate);
         difference = days - workingDays;
         System.out.println("Actual working days fourth Time " + workingDays + " Difference " + difference);
      }

      return generatedDate;
   }

   public int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
      Calendar startCal = Calendar.getInstance();
      startCal.setTime(startDate);
      startCal.set(11, 0);
      startCal.set(12, 0);
      startCal.set(13, 0);
      startCal.set(14, 1);
      Calendar endCal = Calendar.getInstance();
      endCal.setTime(endDate);
      endCal.set(11, 0);
      endCal.set(12, 0);
      endCal.set(13, 0);
      endCal.set(14, 1);
      int workDays = -1;
      if (this.df.format(startDate).equals(this.df.format(endDate))) {
         return 0;
      } else {
         if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
         }

         try {
            do {
               do {
                  if (!this.checkHoliday(startCal.getTime()) && !this.checkSecondSaturday(startCal.getTime()) && startCal.get(7) != 1) {
                     ++workDays;
                  }

                  startCal.add(5, 1);
               } while(startCal.getTime().before(endCal.getTime()));
            } while(startCal.getTime().equals(endCal.getTime()));
         } catch (Exception var7) {
            System.out.println(var7.getMessage());
         }

         if (workDays == -1) {
            workDays = 0;
         }

         return workDays;
      }
   }

   public boolean checkSecondSaturday(Date date) {
      boolean isSecondSat = false;
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      int dayOfWeek = c.get(7);
      if (dayOfWeek == 7) {
         int weekOfMonth = c.get(4);
         if (weekOfMonth == 2 || weekOfMonth == 4) {
            isSecondSat = true;
         }
      }

      return isSecondSat;
   }

   public boolean checkHoliday(Date date) {
      boolean isHoliday = false;

      for(int i = 0; i < this.holidayList.size(); ++i) {
         if (this.df.format(date).equals(this.df.format((Date)this.holidayList.get(i)))) {
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
         pstmt = connection.prepareCall("SELECT HOLIDAYDATE FROM RGICL_HOLIDAYS WHERE HOLIDAYYEAR = (SELECT TO_NUMBER(EXTRACT(YEAR FROM SYSDATE)) FROM DUAL)");
         rset = pstmt.executeQuery();

         while(rset.next()) {
            Date holiday = new Date(rset.getDate(1).getTime());
            Calendar holidayCal = Calendar.getInstance();
            holidayCal.setTime(holiday);
            cal.set(1, holidayCal.get(1));
            cal.set(2, holidayCal.get(2));
            cal.set(5, holidayCal.get(5));
            this.holidayList.add(cal.getTime());
         }
      } catch (Exception var15) {
         System.out.println("Error in getting holiday list : " + var15.getMessage());
      } finally {
         GetResource.releaseResource(connection, pstmt);

         try {
            if (rset != null) {
               rset.close();
            }
         } catch (Exception var14) {
            System.out.println("Error while closing the connetion -- method name = getMandatoryInfo() --" + var14.getMessage());
         }

      }

   }

   public long getDueDate(int days) {
      Calendar duedate = null;
      long tatInMili = 0L;

      try {
         Date dt = new Date();
         long TAT = (long)(days * 8 * 60 * 60 * 1000);
         SBMCalendar.init((Properties)null);
         SBMCalendar sbmcal = new SBMCalendar("RGICL_CD_CALENDAR");
         BizCalendar bcal = new BizCalendar(dt.getTime(), TAT);
         duedate = sbmcal.getDueDate(bcal);
         Date calculatedDate = duedate.getTime();
         tatInMili = calculatedDate.getTime();
      } catch (Throwable var11) {
         System.out.println("Error while getting duedate \n" + var11.getMessage());
      }

      return tatInMili;
   }

   public void updateSpocEscalationFlag(int escalationCount, Long piid, String spoc) {
      System.out.println("Inside updateSpocEscalationFlag method.");
      Connection connection = null;
      PreparedStatement pstmt = null;
      connection = GetResource.getDBConnection();

      try {
         System.out.println("Escalation count is " + escalationCount);
         if (escalationCount == 0) {
            System.out.println("Inside count 1");
            Date javaDate = this.EscalationDate(2);
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
         System.out.println("Method called sucessfully updateSpocEscalationFlag");
      } catch (Exception var12) {
         System.out.println("Error while updating updateSpocEscalationFlag()");
      } finally {
         GetResource.releaseResource(connection, pstmt);
      }

   }

   public void updatePerformerEscalationFlag(int escalationCount, Long piid, String performer) {
      System.out.println("Inside updatePerformerEscalationFlag method.");
      Connection connection = null;
      PreparedStatement pstmt = null;
      connection = GetResource.getDBConnection();

      try {
         System.out.println("Escalation count is " + escalationCount);
         if (escalationCount == 0) {
            System.out.println("Inside count 1");
            Date javaDate = this.EscalationDate(2);
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

         System.out.println("Method called sucessfully updateSpocEscalationFlag");
      } catch (Exception var12) {
         System.out.println("Error while updating updatePerformerEscalationFlag()");
      } finally {
         GetResource.releaseResource(connection, pstmt);
      }

   }

   public void spocEscalation() {
      System.out.println("Inside spocEscalation method");
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      Date utilDate = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      String sqlDate = formatter.format(utilDate);

      try {
         connection = GetResource.getDBConnection();
         String query = " select process_instance_id,spoc,bm_submit_date,escalate_count from agentbmgrid where due_date = to_date( ? ,'dd-MM-yyyy') and status = 'Pending' and escalate_count in (0,1)";
         pstmt = connection.prepareStatement(query);
         pstmt.setString(1, sqlDate);
         rset = pstmt.executeQuery();
         if (rset != null) {
            while(rset.next()) {
               String piid = rset.getString("process_instance_id");
               String spoc = rset.getString("spoc");
               String assignedDate = formatter.format(rset.getDate("bm_submit_date"));
               int escalationCount = rset.getInt(4);
               this.spocEscalationMail(piid, spoc, assignedDate, escalationCount);
            }
         }
      } catch (SQLException var20) {
         System.out.println("Error while calling spocEscalation method" + var20.getMessage());
      } finally {
         GetResource.releaseResource(connection, pstmt);

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
         } catch (Exception var19) {
            System.out.println("Error while closing the connetion -- method name = spocEscalation() --" + var19.getMessage());
         }

      }

   }

   public HashMap<String, String> getSpocEscalationMatrix(String spocId) {
      System.out.println("Inside getSpocEscalationMatrix method !!!");
      HashMap spocEscalationMatrix = new HashMap();

      try {
         Connection connection = null;
         PreparedStatement pstmt = null;
         ResultSet rset = null;
         if (spocId != null) {
            try {
               connection = GetResource.getDBConnection();
               String query = "select first_escalation_name, first_escalation_mailid,second_escalation_name, second_escalation_mailid from agent_spoc_escalation_matrix where spoc_id = ? ";
               pstmt = connection.prepareCall(query);
               pstmt.setString(1, spocId);
               rset = pstmt.executeQuery();

               while(rset.next()) {
                  spocEscalationMatrix.put("first_escalation_name", rset.getString("first_escalation_name"));
                  spocEscalationMatrix.put("first_escalation_mailid", rset.getString("first_escalation_mailid"));
                  spocEscalationMatrix.put("second_escalation_name", rset.getString("second_escalation_name"));
                  spocEscalationMatrix.put("second_escalation_mailid", rset.getString("second_escalation_mailid"));
               }
            } catch (Exception var16) {
               System.out.println("Error while getting spocEscalationMatrix!! " + var16.getMessage());
            } finally {
               GetResource.releaseResource(connection, pstmt);

               try {
                  if (rset != null) {
                     rset.close();
                  }
               } catch (Exception var15) {
                  System.out.println("Error while closing the connetion -- method name = getMandatoryInfo() --" + var15.getMessage());
               }

            }
         }
      } catch (Exception var18) {
         System.out.println("Error in escalation method" + var18.getMessage());
      }

      return spocEscalationMatrix;
   }

   public void spocEscalationMail(String piid, String spoc, String assignedDate, int escalationCount) {
      try {
         System.out.println("Inside spocEscalationMail Method !!!");
         String spocId = spoc.split("-")[1].trim();
         String spocName = spoc.split("-")[0].trim();
         HashMap<String, String> escalationInfo = this.getSpocEscalationMatrix(spocId);
         System.out.println("after escalation method run fine!!!");
         String firstEscalationMailID = (String)escalationInfo.get("first_escalation_mailid");
         String firstEscalationName = (String)escalationInfo.get("first_escalation_name");
         String secondEscalationMailID = (String)escalationInfo.get("second_escalation_mailid");
         String secondEscalationName = (String)escalationInfo.get("second_escalation_name");
         AgentFlowProcess obj = new AgentFlowProcess();
         String mandatoryCCID = obj.getMandatoryInfo().split(",")[0].trim();
         System.out.println("before if condition count is " + escalationCount);
         HashMap prop;
         EmailSender objemailsender;
         long process_instance_id;
         if (escalationCount == 0) {
            System.out.println("Inside if condition of first escalation count !!!");
            if (firstEscalationMailID != null && firstEscalationName != null) {
               prop = new HashMap();
               prop.put("TO", firstEscalationMailID.trim());
               prop.put("CC", mandatoryCCID);
               prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
               prop.put("SUBJECT", "Agent Retention SPOC Escalation");
               prop.put("BODY", this.spocFirstEscalationBody(piid, firstEscalationName, spocName, assignedDate));
               objemailsender = new EmailSender();
               objemailsender.send(prop);
               process_instance_id = Long.parseLong(piid);
               this.updateSpocEscalationFlag(escalationCount, process_instance_id, spoc);
               (new ExceptionLogMaintain()).mailSent_Log(process_instance_id, firstEscalationMailID.trim(), "-", "spoc_first_escalation");
            }
         }

         if (escalationCount == 1) {
            System.out.println("Inside if condition of second escalation count !!!");
            if (secondEscalationMailID != null && secondEscalationName != null) {
               prop = new HashMap();
               prop.put("TO", secondEscalationMailID.trim());
               prop.put("CC", mandatoryCCID);
               prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
               prop.put("SUBJECT", "Agent Retention SPOC Escalation");
               prop.put("BODY", this.spocSecondEscalationBody(piid, secondEscalationName, spocName, assignedDate));
               objemailsender = new EmailSender();
               objemailsender.send(prop);
               process_instance_id = Long.parseLong(piid);
               this.updateSpocEscalationFlag(escalationCount, process_instance_id, spoc);
               (new ExceptionLogMaintain()).mailSent_Log(process_instance_id, firstEscalationMailID.trim(), "-", "spoc_second_escalation");
            }
         }
      } catch (Exception var18) {
         System.out.println("Error while sending SPOC Escalation Mail " + var18.getMessage());
         StringWriter errors = new StringWriter();
         long piID = Long.parseLong(piid);
         (new ExceptionLogMaintain()).mailSendingError_Log(piID, this.getErrorGeneratorMethod(var18), "spocEscalationMail", var18.getMessage(), errors.toString());
      }

   }

   public void performerEscalation() {
      System.out.println("Inside performerEscalation method");
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      Date utilDate = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      String sqlDate = formatter.format(utilDate);

      try {
         connection = GetResource.getDBConnection();
         String query = " select process_instance_id,performer,role,assigned_date,count from agent_escalation_tbl where escalate_date = to_date( ? ,'dd-MM-yyyy') and status = 'Pending' and count in (0,1)";
         pstmt = connection.prepareStatement(query);
         pstmt.setString(1, sqlDate);
         rset = pstmt.executeQuery();
         if (rset != null) {
            while(rset.next()) {
               String piid = rset.getString("process_instance_id");
               String performer = rset.getString("performer");
               String role = rset.getString("role");
               String assignedDate = formatter.format(rset.getDate("assigned_date"));
               int escalationCount = rset.getInt(5);
               this.performerEscalationMail(piid, performer, role, assignedDate, escalationCount);
            }
         }
      } catch (SQLException var21) {
         System.out.println("Error while calling performerEscalation method" + var21.getMessage());
      } finally {
         GetResource.releaseResource(connection, pstmt);

         try {
            if (rset != null) {
               rset.close();
            }
         } catch (Exception var20) {
            System.out.println("Error while closing the connetion -- method name = performerEscalation() --" + var20.getMessage());
         }

      }

   }

   public void performerEscalationMail(String piid, String performer, String role, String assigned_date, int escalationCount) {
      try {
         System.out.println("Inside performerEscalationMail Method !!!");
         SqlUtility obj = new SqlUtility();
         String performerName = obj.getEmployeeDetails(performer).split(",")[0];
         HashMap<String, String> escalationInfo = obj.getPerformerEscalationDetail(performer);
         String firstEscalationMailID = (String)escalationInfo.get("firstMailId");
         String firstEscalationName = (String)escalationInfo.get("firstName");
         String secondEscalationMailID = (String)escalationInfo.get("secondIdMailId");
         String secondEscalationName = (String)escalationInfo.get("secondName");
         AgentFlowProcess obj2 = new AgentFlowProcess();
         String mandatoryCCID = obj2.getMandatoryInfo().split(",")[0].trim();
         System.out.println("before if condition count is " + escalationCount);
         HashMap prop;
         EmailSender objemailsender;
         long process_instance_id;
         if (escalationCount == 0) {
            System.out.println("Inside if condition of first escalation count !!!");
            if (firstEscalationMailID != null && firstEscalationName != null) {
               prop = new HashMap();
               prop.put("TO", firstEscalationMailID.trim());
               prop.put("CC", mandatoryCCID);
               prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
               prop.put("SUBJECT", "Agent Retention Escalation");
               prop.put("BODY", this.performerFirstEscalationBody(piid, firstEscalationName, performerName));
               objemailsender = new EmailSender();
               objemailsender.send(prop);
               process_instance_id = Long.parseLong(piid);
               this.updatePerformerEscalationFlag(escalationCount, process_instance_id, performer);
               (new ExceptionLogMaintain()).mailSent_Log(process_instance_id, firstEscalationMailID.trim(), "-", "performer_first_escalation");
            }
         }

         if (escalationCount == 1 && role.equals("BMCM")) {
            System.out.println("Inside if condition of second escalation count !!!");
            if (secondEscalationMailID != null && secondEscalationName != null) {
               prop = new HashMap();
               prop.put("TO", secondEscalationMailID.trim());
               prop.put("CC", mandatoryCCID);
               prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
               prop.put("SUBJECT", "Agent Retention Escalation");
               prop.put("BODY", this.performerSecondEscalationBody(piid, secondEscalationName, performerName));
               objemailsender = new EmailSender();
               objemailsender.send(prop);
               process_instance_id = Long.parseLong(piid);
               this.updatePerformerEscalationFlag(escalationCount, process_instance_id, performer);
               (new ExceptionLogMaintain()).mailSent_Log(process_instance_id, secondEscalationMailID.trim(), "-", "performer_second_escalation");
            }
         }
      } catch (Exception var19) {
         System.out.println("Error while sending BM/CM & RM/RH Escalation Mail " + var19.getMessage());
         StringWriter errors = new StringWriter();
         long piID = Long.parseLong(piid);
         (new ExceptionLogMaintain()).mailSendingError_Log(piID, this.getErrorGeneratorMethod(var19), "performerEscalationMail", var19.getMessage(), errors.toString());
      }

   }

   public void bmTaskNotify(Long piid, String mailBody) {
      String userMailID = null;
      String userName = null;

      try {
         this.blserver = BLClientUtil.getBizLogicServer();
         this.blsession = this.blserver.connect("rgicl", "rgicl");
         ProcessInstance pi = this.blserver.getProcessInstance(this.blsession, piid);
         String bmId = (String)pi.getDataSlotValue("bm");
         if (bmId != null && bmId.trim().length() > 0) {
            AgentFlowProcess objagentflowprocess = new AgentFlowProcess();
            String userDetail = objagentflowprocess.getEmployeeDetails(bmId);
            userName = userDetail.trim().split(",")[0];
            userMailID = userDetail.trim().split(",")[1];
            if (userDetail != null && userDetail.length() > 0) {
               HashMap<String, String> prop = new HashMap();
               prop.put("TO", userMailID.trim());
               prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
               prop.put("SUBJECT", "Agent Retention Task Assigned");
               if (mailBody.equals("callupdation")) {
                  prop.put("BODY", this.taskAssignedMailBody(userName, piid));
               } else if (mailBody.equals("immediatemanager")) {
                  prop.put("BODY", this.immediateManagerReplyNotifyBMMailBody(userName, piid));
               } else {
                  prop.put("BODY", this.spocResolutionNotifyBMMailBody(userName, piid));
               }

               EmailSender objemailsender = new EmailSender();
               objemailsender.send(prop);
               (new ExceptionLogMaintain()).mailSent_Log(piid, userMailID.trim(), "-", "bmTaskNotify");
            }
         }
      } catch (Exception var11) {
         System.out.println("Error in bmTaskNotify" + var11.getMessage());
         StringWriter errors = new StringWriter();
         (new ExceptionLogMaintain()).mailSendingError_Log(piid, this.getErrorGeneratorMethod(var11), "bmTaskNotify", var11.getMessage(), errors.toString());
      }

   }

   public void bmTaskNotifyComplete(Long piid, String mailBody) {
      String userMailID = null;
      String userName = null;

      try {
         this.blserver = BLClientUtil.getBizLogicServer();
         this.blsession = this.blserver.connect("rgicl", "rgicl");
         ProcessInstance pi = this.blserver.getProcessInstance(this.blsession, piid);
         String bmId = (String)pi.getDataSlotValue("bm");
         if (bmId != null && bmId.trim().length() > 0) {
            SqlUtility objsqlutility = new SqlUtility();
            String userDetail = objsqlutility.getEmployeeDetails(bmId);
            userName = userDetail.trim().split(",")[0];
            userMailID = userDetail.trim().split(",")[1];
            if (userDetail != null && userDetail.length() > 0) {
               HashMap<String, String> prop = new HashMap();
               prop.put("TO", userMailID.trim());
               prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
               prop.put("SUBJECT", "Agent Retention Task Assigned");
               if (mailBody.equals("callupdation")) {
                  prop.put("BODY", this.taskAssignedMailBody(userName, piid));
               } else if (mailBody.equals("immediatemanager")) {
                  prop.put("BODY", this.immediateManagerReplyNotifyBMMailBody(userName, piid));
               } else {
                  prop.put("BODY", this.spocResolutionNotifyBMMailBody(userName, piid));
               }

               EmailSender objemailsender = new EmailSender();
               objemailsender.send(prop);
               (new ExceptionLogMaintain()).mailSent_Log(piid, userMailID.trim(), "-", "bmTaskNotify");
            }
         }
      } catch (Exception var11) {
         System.out.println("Error in bmTaskNotify" + var11.getMessage());
         StringWriter errors = new StringWriter();
         (new ExceptionLogMaintain()).mailSendingError_Log(piid, this.getErrorGeneratorMethod(var11), "bmTaskNotify", var11.getMessage(), errors.toString());
      }

   }

   public void immediateManagerTaskNotify(Long piid) {
      String userMailID = null;
      String userName = null;

      try {
         this.blserver = BLClientUtil.getBizLogicServer();
         this.blsession = this.blserver.connect("rgicl", "rgicl");
         ProcessInstance pi = this.blserver.getProcessInstance(this.blsession, piid);
         String bmId = (String)pi.getDataSlotValue("bm");
         if (bmId != null && bmId.trim().length() > 0) {
            AgentFlowProcess objagentflowprocess = new AgentFlowProcess();
            String immediateMgrId = objagentflowprocess.getImmediateMgr(bmId);
            String userDetail = objagentflowprocess.getEmployeeDetails(immediateMgrId);
            userName = userDetail.trim().split(",")[0];
            userMailID = userDetail.trim().split(",")[1];
            if (userDetail != null && userDetail.length() > 0) {
               HashMap<String, String> prop = new HashMap();
               prop.put("TO", userMailID.trim());
               prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
               prop.put("SUBJECT", "Agent Retention Task Assigned");
               prop.put("BODY", this.taskAssignedMailBody(userName, piid));
               EmailSender objemailsender = new EmailSender();
               objemailsender.send(prop);
               (new ExceptionLogMaintain()).mailSent_Log(piid, userMailID.trim(), "-", "immediateManagerTaskNotify");
            }
         }
      } catch (Exception var11) {
         System.out.println("Error in immediateManagerTaskNotify" + var11.getMessage());
         StringWriter errors = new StringWriter();
         (new ExceptionLogMaintain()).mailSendingError_Log(piid, this.getErrorGeneratorMethod(var11), "immediateManagerTaskNotify", var11.getMessage(), errors.toString());
      }

   }

   public void spocTaskNotify(Long piid) {
      Object var2 = null;

      try {
         this.blserver = BLClientUtil.getBizLogicServer();
         this.blsession = this.blserver.connect("rgicl", "rgicl");
         ProcessInstance pi = this.blserver.getProcessInstance(this.blsession, piid);
         String spocId = (String)pi.getDataSlotValue("spocs");
         spocId = spocId.substring(0, spocId.length() - 9);
         String[] spocIdArray = spocId.split(",");
         if (spocId != null && spocId.trim().length() > 0) {
            String[] mailIds = this.getMailIds(spocIdArray);
            if (mailIds != null) {
               HashMap<String, Object> prop = new HashMap();
               prop.put("TO", mailIds);
               prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
               prop.put("SUBJECT", "Agent Retention Task Assigned");
               prop.put("BODY", this.taskAssignedMailBody("SPOC", piid));
               EmailSender objemailsender = new EmailSender();
               objemailsender.sendMutliple_To(prop);
               (new ExceptionLogMaintain()).mailSent_Log(piid, this.convertToCommaDelimited(mailIds), "-", "spocTaskNotify");
            }
         }
      } catch (Exception var9) {
         System.out.println("Error in spocTaskNotify" + var9.getMessage());
         StringWriter errors = new StringWriter();
         (new ExceptionLogMaintain()).mailSendingError_Log(piid, "spocTaskNotify method", "spocTaskNotify", var9.getMessage(), errors.toString());
      }

   }

   public void reassignedSpocTaskNotify(Long piid, String userId) {
      String userMailID = null;
      String userName = null;

      try {
         SqlUtility objsqlutility = new SqlUtility();
         String userDetail = objsqlutility.getEmployeeDetails(userId);
         userName = userDetail.trim().split(",")[0];
         userMailID = userDetail.trim().split(",")[1];
         if (userDetail != null && userDetail.length() > 0) {
            HashMap<String, String> prop = new HashMap();
            prop.put("TO", userMailID.trim());
            prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
            prop.put("SUBJECT", "Agent Retention Task Assigned");
            prop.put("BODY", this.taskAssignedMailBody(userName, piid));
            EmailSender objemailsender = new EmailSender();
            objemailsender.send(prop);
            (new ExceptionLogMaintain()).mailSent_Log(piid, userMailID.trim(), "-", "reassignedspocTaskNotify");
         }
      } catch (Exception var9) {
         System.out.println("Error in spocreassignTaskNotify" + var9.getMessage());
         StringWriter errors = new StringWriter();
         (new ExceptionLogMaintain()).mailSendingError_Log(piid, this.getErrorGeneratorMethod(var9), "reassignedspocTaskNotify", var9.getMessage(), errors.toString());
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
            HashMap<String, String> prop = new HashMap();
            prop.put("TO", userMailID.trim());
            prop.put("FROM", "rgicl.applnsupport@reliancegeneral.co.in");
            prop.put("SUBJECT", "Agent Retention Task Assigned");
            prop.put("BODY", this.taskAssignedMailBody(userName, piid));
            EmailSender objemailsender = new EmailSender();
            objemailsender.send(prop);
            (new ExceptionLogMaintain()).mailSent_Log(piid, userMailID.trim(), "-", "reassignedBmTaskNotify");
         }
      } catch (Exception var9) {
         System.out.println("Error in bmTaskNotify" + var9.getMessage());
         StringWriter errors = new StringWriter();
         (new ExceptionLogMaintain()).mailSendingError_Log(piid, this.getErrorGeneratorMethod(var9), "reassignedBmTaskNotify", var9.getMessage(), errors.toString());
      }

   }

   private String getErrorGeneratorMethod(Throwable cause) {
      Throwable rootCause = cause;
      if (cause.getCause() != null && cause.getCause() != cause) {
         rootCause = cause.getCause();
      }

      return rootCause != null ? rootCause.getStackTrace()[0].getMethodName() : "";
   }

   public void saveUnsentMail(String TO, String CC, String Subject, String Body, String error) {
      Connection connection = null;
      CallableStatement pstmt = null;

      try {
         connection = GetResource.getDBConnection();
         String query = " INSERT INTO AGENT_UNSENT_MAIL VALUES (?,?,?,?,?)";
         pstmt = connection.prepareCall(query);
         pstmt.setString(1, TO);
         pstmt.setString(2, CC);
         pstmt.setString(3, Subject);
         pstmt.setString(4, Body);
         pstmt.setString(5, error);
         pstmt.executeUpdate();
      } catch (Exception var12) {
         System.out.println("Error while saving the unsent mails" + var12.getMessage());
      } finally {
         GetResource.releaseResource(connection, pstmt);
      }

   }

   private String[] getMailIds(String[] userArr) {
      String[] membersMailID = null;
      if (userArr != null && userArr.length > 0) {
         AgentFlowProcess objagentflowprocess = new AgentFlowProcess();
         ArrayList<String> mailIDs = new ArrayList();

         for(int i = 0; i < userArr.length; ++i) {
            if (userArr[i] != null && userArr[i].trim().length() > 0) {
               String mailID = objagentflowprocess.getEmployeeDetails(userArr[i].trim());
               if (mailID != null && mailID.trim().length() > 0 && mailID.contains("@")) {
                  mailIDs.add(mailID.trim().split(",")[1]);
               }
            }
         }

         membersMailID = new String[mailIDs.size()];
         membersMailID = (String[])mailIDs.toArray(membersMailID);
      }

      return membersMailID;
   }

   private String convertToCommaDelimited(String[] list) {
      StringBuffer ret = new StringBuffer("");

      for(int i = 0; list != null && i < list.length; ++i) {
         ret.append(list[i]);
         if (i < list.length - 1) {
            ret.append(',');
         }
      }

      return ret.toString();
   }

   private String taskAssignedMailBody(String user, Long piid) {
      StringBuffer sb = new StringBuffer();
      sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
      sb.append("Dear " + user + ",");
      sb.append("<br/><br/>Please find herewith the Ticket No. " + piid + " Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
      sb.append("<br/><br/>Please reply / Complete reference ticket in Savvion.");
      sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.80/sbm/bpmportal/login.jsp\">here</a> </b> to login into the system.");
      sb.append("<br/><br/>Regards,");
      sb.append("<br/> Team Agency CO");
      sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      sb.append("<br/></body></html>");
      return sb.toString();
   }

   public String spocFirstEscalationBody(String piid, String user, String spoc, String assignedDate) {
      StringBuffer sb = new StringBuffer();
      sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
      sb.append("Dear " + user + ",");
      sb.append("<br/><br/>Please find herewith the Ticket No. " + piid + " Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
      sb.append("<br/>Same is pending for action with " + spoc + " Level since last 5 Days.");
      sb.append("<br/><br/>You are requested to take necessary action for closure on priority.");
      sb.append("<br/><br/>Regards,");
      sb.append("<br/> Team Agency CO");
      sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      sb.append("<br/></body></html>");
      return sb.toString();
   }

   public String performerFirstEscalationBody(String piid, String user, String performer) {
      StringBuffer sb = new StringBuffer();
      sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
      sb.append("Dear " + user + ",");
      sb.append("<br/><br/>Please find herewith the Ticket No. " + piid + " Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
      sb.append("<br/>Same is pending for action with " + performer + " Level since last 5 Days.");
      sb.append("<br/><br/>You are requested to take necessary action for closure on priority.");
      sb.append("<br/><br/>Regards,");
      sb.append("<br/> Team Agency CO");
      sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      sb.append("<br/></body></html>");
      return sb.toString();
   }

   public String performerSecondEscalationBody(String piid, String user, String performer) {
      StringBuffer sb = new StringBuffer();
      sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
      sb.append("Dear " + user + ",");
      sb.append("<br/><br/>Please find herewith the Ticket No. " + piid + " Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
      sb.append("<br/>Same is pending for action with " + performer + " Level since last 7 Days.");
      sb.append("<br/><br/>You are requested to take necessary action for closure on priority.");
      sb.append("<br/><br/>Regards,");
      sb.append("<br/> Team Agency CO");
      sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      sb.append("<br/></body></html>");
      return sb.toString();
   }

   public String spocSecondEscalationBody(String piid, String user, String spoc, String assignedDate) {
      StringBuffer sb = new StringBuffer();
      sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
      sb.append("Dear " + user + ",");
      sb.append("<br/><br/>Please find herewith the Ticket No. " + piid + " Raised under Agent Retention Program with the elaborated queries raised by Stake Holder.");
      sb.append("<br/>Same is pending for action with " + spoc + " Level since last 7 Days.");
      sb.append("<br/><br/>You are requested to take necessary action for closure on priority.");
      sb.append("<br/><br/>Regards,");
      sb.append("<br/> Team Agency CO");
      sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      sb.append("<br/></body></html>");
      return sb.toString();
   }

   private String spocResolutionNotifyBMMailBody(String user, Long piid) {
      StringBuffer sb = new StringBuffer();
      sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
      sb.append("Dear " + user + ",");
      sb.append("<br/><br/>Agent Retention Unique Reference No. " + piid + " has been assigned to your ID for action after SPOC resolution.");
      sb.append("<br/>See below link to open Process");
      sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.80/sbm/bpmportal/login.jsp\">here</a> </b> to login into the system.");
      sb.append("<br/><br/>Regards,");
      sb.append("<br/> Team Agency CO");
      sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      sb.append("<br/></body></html>");
      System.out.println("inside spocResolutionNotifyBMMailBody");
      return sb.toString();
   }

   private String immediateManagerReplyNotifyBMMailBody(String user, Long piid) {
      StringBuffer sb = new StringBuffer();
      sb.append("<html><head><style type=\"text/css\" >.mailbody {font-family: Calibri, Helvetica,sans-serif;font-size:14px}<!--.tabletitle {width:200px;font-weight:bold;color:#000080;}--><!--.tablecell {width:200px;}--><!--.tablecls {width:800px; cellpadding:15px}--></style><body class=\"mailbody\"><br/>");
      sb.append("Dear " + user + ",");
      sb.append("<br/><br/>Agent Retention Unique Reference No. " + piid + " has been assigned to your ID for action after Immediatemanager resolution.");
      sb.append("<br/>See below link to open Process");
      sb.append("<br/><br/>Click <b><a href=\"http://10.65.5.80/sbm/bpmportal/login.jsp\">here</a> </b> to login into the system.");
      sb.append("<br/><br/>Regards,");
      sb.append("<br/> Team Agency CO");
      sb.append("<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ************* THIS IS SYSTEM GENERATED MAIL. PLEASE DO NOT REPLY. **************&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      sb.append("<br/></body></html>");
      return sb.toString();
   }
}
