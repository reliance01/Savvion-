package org.rgicl.motor.util;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.util.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class DBUtility {
   Connection connection = null;
   PreparedStatement prepareStatement = null;
   Statement statement = null;
   ResultSet resultSet = null;
   String query = null;
   Properties config = null;
   DBManager dbManager = null;
   private BLClientUtil blc;
   private BLServer blserver;

   public void createConnection() throws Exception {
      this.dbManager = new DBManager();
      this.connection = this.dbManager.getDBConnection();
   }

   public String getProcessTemplateId(String s) {
      long l = 0L;

      try {
         if (s != null) {
            this.blserver = BLClientUtil.getBizLogicServer();
            Session session = this.blserver.connect("rgicl", "rgicl");
            ProcessTemplate processtemplate = this.blserver.getProcessTemplate(session, s);
            l = processtemplate.getID();
            if (session != null) {
               this.blserver.disConnect(session);
            }
         }
      } catch (Exception var6) {
      }

      return (new Long(l)).toString();
   }

   public void closeConnection() {
      try {
         this.connection.close();
      } catch (Exception var2) {
      }

   }

   public ArrayList getMyInboxTaskObjects(String user, String ptName, String proposalNo) {
      String userLocation = null;
      new ArrayList();
      ArrayList workItemList = new ArrayList();
      Object var7 = null;

      try {
         this.dbManager = new DBManager();
         if (ptName.equalsIgnoreCase("RGICL_MotorUW_App")) {
            this.query = "SELECT B.WORKITEM_NAME, B.PROCESS_INSTANCE_ID, SUBSTR(B.WORKITEM_NAME,0,INSTR(B.WORKITEM_NAME,'::')-1) AS PINAME,(SELECT  DISTINCT (PI.WORKSTEP_NAME) FROM PROCESSWORKSTEPINFO PI WHERE PI.WORKSTEP_ID=B.WORKSTEP_ID AND PI.PROCESS_TEMPLATE_ID=B.PROCESS_TEMPLATE_ID) AS WORKSTEPNAME, B.PERFORMER, R.APPROVALAUTHORITY, R.BRANCHCODE, R.CALLENDDATE, R.CALLSTARTDATE, R.CREATEDBY, R.CUAPPROVER, R.CUENDDATE, R.CUGROUP, R.CUHOLDDTSTR,R.CUREMARKS, R.CUSTARTDATE, R.CUSTATUS, R.PROPOSALNUMBER, R.SUSPENDDATE, R.SUSPENDSTATUS, R.UWAPPROVER, R.UWENDDATE, R.UWGROUP, R.UWHOLDDTSTR, R.UWREMARKS, R.UWSTARTDATE, R.UWSTATUS FROM BIZLOGIC_DS_" + this.getProcessTemplateId(ptName) + " R, BIZLOGIC_WORKITEM B, DUAL WHERE R.PROCESS_INSTANCE_ID=B.PROCESS_INSTANCE_ID AND B.PERFORMER='" + user + "'";
            if (proposalNo != null && proposalNo.length() > 0) {
               this.query = this.query + " AND R.PROPOSALNUMBER='" + proposalNo + "'";
            }

            this.query = this.query + " ORDER BY B.PROCESS_INSTANCE_ID";
         }

         this.connection = this.dbManager.getDBConnection();
         this.prepareStatement = this.connection.prepareStatement(this.query);
         this.resultSet = this.prepareStatement.executeQuery();

         while(this.resultSet.next()) {
            WorkItemObject workitem = new WorkItemObject();
            workitem.setPerformer(this.resultSet.getString("PERFORMER"));
            workitem.setWorkStepName(this.resultSet.getString("WORKSTEPNAME"));
            workitem.setProcessInstanceName(this.resultSet.getString("PINAME"));
            workItemList.add(workitem);
         }
      } catch (Exception var20) {
         var20.printStackTrace();

         try {
            if (this.resultSet != null) {
               this.resultSet.close();
            }

            if (this.prepareStatement != null) {
               this.prepareStatement.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }
         } catch (Exception var19) {
            var19.printStackTrace();
         }
      } finally {
         try {
            if (this.resultSet != null) {
               this.resultSet.close();
            }

            if (this.prepareStatement != null) {
               this.prepareStatement.close();
            }

            if (this.connection != null) {
               this.connection.close();
            }
         } catch (Exception var18) {
         }

      }

      return workItemList;
   }
}
