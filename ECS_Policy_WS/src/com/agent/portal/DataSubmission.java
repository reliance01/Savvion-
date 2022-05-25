package com.agent.portal;

import com.agent.ejb.mail.Mailer;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.calendar.SBMCalendar;
import com.savvion.sbm.calendar.svo.BizCalendar;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

public class DataSubmission {
   public String submitBMGrid(String piID, String reassignBy, HashMap<String, String> grid) {
      System.out.println("BM Grid is : ---   " + grid);
      Mailer objmailer = new Mailer();
      Date javaDate = objmailer.EscalationDate(5);
      java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
      Connection connection = null;
      CallableStatement cstmt = null;
      GetResource obj = new GetResource();

      try {
         if (grid != null && grid.size() > 0) {
            connection = obj.getDBConnection();
            cstmt = connection.prepareCall("{call agentAddBMGrid(?,?,?,?,?,?,?,?)}");
            Iterator var11 = grid.entrySet().iterator();

            while(var11.hasNext()) {
               Entry<String, String> entry = (Entry)var11.next();
               String value = (String)entry.getValue();
               System.out.println("value : " + value);
               if (value != null && value.trim().length() > 0 && value.contains("####")) {
                  System.out.println("Is not nulll");
                  String sbCtgy = value.split("####")[0].trim();
                  System.out.println("sbCtgy : " + sbCtgy);
                  String spocs = value.split("####")[1].trim();
                  System.out.println("spocs : " + spocs);
                  String remarks = value.split("####")[2].trim();
                  System.out.println("remarks : " + remarks + " : " + piID + " : " + (String)entry.getKey());
                  cstmt.setLong(1, Long.parseLong(piID));
                  System.out.println("1");
                  cstmt.setString(2, (String)entry.getKey());
                  System.out.println("2");
                  cstmt.setString(3, sbCtgy);
                  System.out.println("3");
                  cstmt.setString(4, spocs);
                  System.out.println("4");
                  cstmt.setString(5, remarks);
                  System.out.println("5");
                  cstmt.setString(6, reassignBy);
                  System.out.println("6");
                  cstmt.setDate(7, sqlDate);
                  cstmt.setString(8, "0");
                  System.out.println("before ");
                  cstmt.executeUpdate();
                  System.out.println("after");
               }
            }
         }
      } catch (Exception var19) {
         System.out.println("error" + var19.getMessage());
         throw new RuntimeException("Error while submitBMGrid : " + var19.getMessage());
      } finally {
         obj.releaseResource(connection, cstmt);
      }

      return "Submit";
   }

   public String setSpocRemarks(String piID, String spocId, String remarks, String status) {
      System.out.println("Inside setSpocRemark");
      Connection connection = null;
      CallableStatement cstmt = null;
      GetResource obj = new GetResource();

      try {
         if (piID != "" && spocId != "") {
            connection = obj.getDBConnection();
            cstmt = connection.prepareCall("{call agent_setspocremark(?,?,?,?)}");
            cstmt.setLong(1, Long.parseLong(piID));
            cstmt.setString(2, "%" + spocId.trim() + "%");
            cstmt.setString(3, remarks);
            cstmt.setString(4, status);
            cstmt.executeUpdate();
         }
      } catch (Exception var12) {
         throw new RuntimeException("Error while setSpocRemark : " + var12.getMessage());
      } finally {
         obj.releaseResource(connection, cstmt);
      }

      return "Submit";
   }

   public String updateCompleteStatus(String piID, String closureRmk) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      if (piID != null && piID.trim().length() > 0) {
         GetResource obj = new GetResource();

         try {
            connection = obj.getDBConnection();
            String qry = "  update AGENTRETENTIONMODEL_DETAIL  set STATUS = 'COMPLETED', end_date = ?, CLOSURE_REMARKS = ?   where process_instance_id = ? ";
            pstmt = connection.prepareStatement(qry);
            pstmt.setTimestamp(1, new Timestamp((new Date()).getTime()));
            pstmt.setString(2, closureRmk.trim());
            pstmt.setLong(3, Long.parseLong(piID.trim()));
            pstmt.executeUpdate();
         } catch (Exception var11) {
            throw new RuntimeException("Error while Updating Completed Flag : " + var11.getCause() + " : " + var11.getMessage() + " : " + var11.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, (ResultSet)rset);
         }
      }

      return "Sucess";
   }

   public void setReassignFlag(long wiid, String piID, String performer, String role) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      if (piID != null && piID.trim().length() > 0) {
         GetResource obj = new GetResource();

         try {
            connection = obj.getDBConnection();
            String qry = "  update AGENTRETENTIONMODEL_DETAIL  set BM = ?, start_date = ?, role = ? where process_instance_id = ? ";
            pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, performer.trim());
            pstmt.setTimestamp(2, new Timestamp((new Date()).getTime()));
            pstmt.setString(3, role.trim());
            pstmt.setLong(4, Long.parseLong(piID.trim()));
            pstmt.executeUpdate();
         } catch (Exception var14) {
            throw new RuntimeException("Error while Updating ReassignFlag Flag : " + var14.getCause() + " : " + var14.getMessage() + " : " + var14.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, (ResultSet)rset);
         }

         this.updateReassignDataslot(piID, performer, role);
      }

   }

   public String reAssign(String wiId, String performer, String piid) {
      String flag = "error";

      try {
         System.out.println("Inside method -> wiId is : " + wiId + " Performer is :" + performer);
         BLServer blServer = BLClientUtil.getBizLogicServer();
         Session blSession = blServer.connect("rgicl", "rgicl");
         WorkItem wi = blServer.getWorkItem(blSession, Long.parseLong(wiId));
         wi.reAssign(performer);
         wi.save();
         blServer.disConnect(blSession);
         Mailer mail = new Mailer();
         Long piID = Long.parseLong(piid);
         mail.reassignedSpocTaskNotify(piID, performer);
         System.out.println("Performer changed sucessfully !!!");
         flag = "Performer Changed";
      } catch (Throwable var10) {
         var10.printStackTrace();
         flag = "error";
         System.out.println("Getting error while reassign task!!! ");
      }

      return flag;
   }

   public String setPerformerEscalation(String piid, String performer) {
      Connection connection = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      long workItemId = 0L;
      if (piid != null && piid.trim().length() > 0) {
         GetResource obj = new GetResource();

         try {
            connection = obj.getDBConnection();
            String qry = "  select workitem_id from bizlogic_workitem where process_instance_id = ? and performer = ? ";
            pstmt = connection.prepareStatement(qry);
            pstmt.setLong(1, Long.parseLong(piid.trim()));
            pstmt.setString(2, performer.trim());
            System.out.println("before rset");
            rset = pstmt.executeQuery();
            if (rset != null) {
               while(rset.next()) {
                  workItemId = Long.parseLong(rset.getString(1));
                  System.out.println("inside result");
               }
            }

            if (workItemId != 0L) {
               System.out.println("inside condition");
               this.setDueDate(workItemId, 6);
               System.out.println("after due date!!");
               Mailer mail = new Mailer();
               long piID = Long.parseLong(piid);
               mail.bmTaskNotifyComplete(piID, "spocCompleted");
            }
         } catch (Exception var16) {
            throw new RuntimeException("Error while Updating Completed Flag : " + var16.getCause() + " : " + var16.getMessage() + " : " + var16.getStackTrace());
         } finally {
            obj.releaseResource(connection, pstmt, rset);
         }
      }

      return "Sucess";
   }

   public void updateReassignDataslot(String piid, String performer, String role) {
      long piID = Long.parseLong(piid);

      try {
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
         HashMap hm = new HashMap();
         hm.put("bm", performer);
         hm.put("role", role);
         pi.updateSlotValue(hm);
         pi.save();
         blserver.disConnect(blsession);
      } catch (Exception var10) {
         System.out.println("Error while updating reassigndataslots");
      }

      Mailer obj = new Mailer();
      obj.reassignedBmTaskNotify(piID, performer);
   }

   public long getDueDate(int days) {
      System.out.println("inside getDuedate");
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

      System.out.println("duedate " + tatInMili);
      return tatInMili;
   }

   public void setDueDate(long workItemId, int days) {
      try {
         System.out.println("Inside setDueDate Method!!");
         BLServer blServer = BLClientUtil.getBizLogicServer();
         Session blSession = blServer.connect("rgicl", "rgicl");
         WorkItem wi = blServer.getWorkItem(blSession, workItemId);
         System.out.println("before set due date");
         wi.setDueDate(this.getDueDate(days));
         wi.save();
         System.out.println("after save");
         blServer.disConnect(blSession);
      } catch (Exception var7) {
         System.out.println("Error while setting Duedate of an workitem !!!");
      }

   }
}
