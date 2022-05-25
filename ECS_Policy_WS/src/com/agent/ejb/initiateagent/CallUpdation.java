package com.agent.ejb.initiateagent;

import com.agent.ejb.mail.Mailer;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class CallUpdation {
   private final String blUser = "rgicl";
   private final String blPassword = "rgicl";
   private Connection con = null;
   private CallableStatement cstmt = null;
   BLServer blServer = null;
   Session blSession = null;
   PreparedStatement pstmt = null;
   GetResource rsc = null;

   public void updateEscalationComplete(long piid) {
      try {
         System.out.println("Inside updateEscalationComplete method !!");
         Connection con = null;
         PreparedStatement pstmt = null;
         String query = " UPDATE AGENT_ESCALATION_TBL SET STATUS = ? WHERE PROCESS_INSTANCE_ID = ? AND STATUS = 'Pending'";
         con = GetResource.getDBConnection();
         pstmt = con.prepareCall(query);
         pstmt.setString(1, "Complete");
         pstmt.setLong(2, piid);
         pstmt.executeUpdate();
      } catch (Exception var9) {
         System.out.println("Error while updating Performer escalation flag" + var9.getMessage());
      } finally {
         GetResource.releaseResource(this.con, this.pstmt);
      }

   }

   public void insertPerformerEscalation(long piid, String performer, String role, int days) {
      try {
         Connection con = null;
         PreparedStatement pstmt = null;
         System.out.println("Inside insertPerformerEscalation method");
         Mailer objmailer = new Mailer();
         Date currentDate = new Date();
         Date javaDate = objmailer.EscalationDate(days);
         java.sql.Date escalateDate = new java.sql.Date(javaDate.getTime());
         java.sql.Date assignedDate = new java.sql.Date(currentDate.getTime());
         String query = " INSERT INTO AGENT_ESCALATION_TBL VALUES (?,?,?,?,?,?,?) ";
         con = GetResource.getDBConnection();
         pstmt = con.prepareStatement(query);
         pstmt.setLong(1, piid);
         pstmt.setString(2, performer.trim());
         pstmt.setString(3, role.trim());
         pstmt.setDate(4, assignedDate);
         pstmt.setDate(5, escalateDate);
         pstmt.setInt(6, 0);
         pstmt.setString(7, "Pending");
         pstmt.executeUpdate();
      } catch (Exception var17) {
         System.out.println("Error while inserting Performer Escalation Matrix  ");
      } finally {
         GetResource.releaseResource(this.con, this.pstmt);
      }

   }

   public void setPerformerEscalation(long piID) {
      try {
         System.out.println("Inside setPerformerEscalation method!!");
         BLServer blserver = BLClientUtil.getBizLogicServer();
         Session blsession = blserver.connect("rgicl", "rgicl");
         ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
         String performer = pi.getDataSlot("bm").toString();
         String role = pi.getDataSlot("role").toString();
         AgentFlowProcess obj = new AgentFlowProcess();
         String defaultId = obj.getMandatoryInfo().split(",")[1].trim();
         if (!performer.equals(defaultId)) {
            this.insertPerformerEscalation(piID, performer, role, 5);
         }
      } catch (Exception var10) {
         System.out.println("Error wihle calling setPerformerEscalation");
      }

   }

   public void addBmRemark(long piID, String performer) {
      System.out.println("Inside addBmRemark Method !!!");

      try {
         this.blServer = BLClientUtil.getBizLogicServer();
         this.blSession = this.blServer.connect("rgicl", "rgicl");
         ProcessInstance pi = this.blServer.getProcessInstance(this.blSession, piID);
         this.con = GetResource.getDBConnection();
         this.cstmt = this.con.prepareCall("{call agent_setbmremarks(?,?,?,?)}");
         this.cstmt.setLong(1, piID);
         this.cstmt.setString(2, (String)pi.getDataSlotValue("bmRemark"));
         this.cstmt.setString(3, (String)pi.getDataSlotValue("immediateManagerRemarks"));
         this.cstmt.setString(4, performer);
         this.cstmt.executeUpdate();
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new RuntimeException("Error in adding BM Remark Details : Performer is " + performer + "Piid is" + piID + var11.getMessage());
      } finally {
         try {
            if (this.blSession != null) {
               this.blServer.disConnect(this.blSession);
            }
         } catch (Exception var12) {
            throw new RuntimeException("BLSession closing error : " + var12.getMessage());
         }

         GetResource.releaseResource(this.con, this.cstmt);
      }

   }
}
