package com.rgic.pqms.webservices;

import com.rgic.pqms.common.exception.PQMSException;
import com.rgic.pqms.common.logger.PQMSLogger;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstanceList;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
import com.savvion.sbm.bizlogic.util.Session;
//import com.savvion.webservice.workflow.BizLogic1SoapBindingStub;
//import com.savvion.webservice.workflow.DataSlotinstance;
//import com.savvion.webservice.workflow.WorkItem;
import vam.com.rgi.rcorp.wsdl.BizLogic1SoapBindingStub;
import vam.com.rgi.rcorp.wsdl.DataSlotinstance;
import vam.com.rgi.rcorp.wsdl.WorkItem;

import java.net.URL;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

public class InboxPQMSData {
   Service service;
   private String endpoint = "";
   private String userID;
   private String userPassword;
   private String empID;
   private String empPassword;
   private String ProductType;
   private String ProposalList;
   private Session sess = null;
   private DataSlotinstance dataSlotinstance1 = new DataSlotinstance();
   private String wiName = "";
   private String piName = "";
   private DataSlotinstance[] dsValues = null;
   private StringBuffer proposalListbf = new StringBuffer();
   private String prodType = "";

   public InboxPQMSData() {
      System.out.println("************************ Inside InboxPQMSData.java *****************");
      this.service = new Service();
      this.endpoint = "http://10.65.15.79:18793/sbm/services/BizLogic1?wsdl";
   }

   public void process() throws AxisFault, PQMSException {
      try {
         Logger logger = PQMSLogger.getLogger();
         logger.debug("Start of process()::: Inside Try/Catch");
         PQMSLogger.debug(InboxPQMSData.class, "Got the environment.......");
         logger.debug("************ User ID: " + this.userID);
         logger.debug("************ User Password: " + this.userPassword);
         logger.debug("************ ProductType : " + this.ProductType);
         BizLogic1SoapBindingStub bindingStub = new BizLogic1SoapBindingStub(new URL(this.endpoint), this.service);
         String session = bindingStub.connect(this.userID, this.userPassword);
         logger.debug("Session " + session);
         PQMSLogger.debug(InboxPQMSData.class, "Got the environment.......  " + session);
         WorkItem[] wiArray = bindingStub.getAssignedWorkItemList(session);
         logger.debug("wiArray :  " + wiArray.length);
         WorkItem wi = null;
         long wiid = 0L;
         wiArray = bindingStub.getAssignedWorkItemList(session);

         for(int i = 0; i < wiArray.length; ++i) {
            wi = wiArray[i];
            wiid = wi.getId();
            this.wiName = wi.getName();
            this.piName = wi.getPiName();
            logger.debug("WorkItem ID: " + wiid);
            logger.debug("session to get ds values::::  " + session);
            this.dsValues = bindingStub.getWorkItemDataSlotsFromWIID(session, wiid);
            logger.debug("dsValues : ~~~~~~~~~~~ : " + this.dsValues);

            for(int l = 0; l < this.dsValues.length; ++l) {
               this.dataSlotinstance1 = this.dsValues[l];
               logger.debug("dataSlotinstance1.getName():" + this.dataSlotinstance1.getName());
               if (this.dataSlotinstance1.getName().equals("ProductType")) {
                  this.prodType = (String)this.dataSlotinstance1.getValue();
                  logger.debug("Product Type: " + this.prodType);
               }

               logger.debug("Got Product Type *************");
               if (this.prodType.equals(this.ProductType)) {
                  logger.debug("compared producttype*********************");
                  if (this.dataSlotinstance1.getName().equals("ProposalID")) {
                     logger.debug("Value: dataSlotinstanceA[" + l + "]: " + this.dataSlotinstance1.getValue());
                     this.proposalListbf.append(this.dataSlotinstance1.getValue());
                     if (l != this.dsValues.length - 1) {
                        this.proposalListbf.append(",");
                     } else if (l == this.dsValues.length) {
                        this.proposalListbf.append("");
                     }
                  }
               }
            }
         }

         this.ProposalList = this.proposalListbf.toString();
         logger.debug("proposalList: " + this.ProposalList);
         BLServer blServer = BLClientUtil.getBizLogicServer();
         logger.debug("Got Server:....." + blServer);
         Session blSession = blServer.connect("ram", "ram");
         logger.debug("Session:....." + blSession);
         ProcessTemplate pt = blServer.getProcessTemplate(blSession, "ProcessPQMS");
         long ptID = pt.getID();
         logger.debug("ProcessTemplate ID:..." + ptID);
         ProcessInstanceList piList = blServer.getProcessInstanceList(blSession, ptID);
         ProcessInstance pi = (ProcessInstance)piList.next();
         long pid = pi.getID();
         logger.debug("PID:..." + pid);
         pi.updateSlotValue("ProposalList", this.ProposalList);
         pi.save();
         DataSlot dsValue = blServer.getDataSlot(blSession, pid, "ProposalList");
         logger.debug("DS Value:..." + dsValue);
         logger.debug("DS value is set");
      } catch (Exception var18) {
         System.out.println("Exception in file");
         var18.printStackTrace();
         throw new AxisFault("SBM Web services error :" + var18.getMessage());
      }
   }

   public String getProposalList() {
      return this.ProposalList;
   }

   public void setProposalList(String proposalList) {
      this.ProposalList = proposalList;
   }

   public String getUserID() {
      return this.userID;
   }

   public void setUserID(String userID) {
      this.userID = userID;
   }

   public String getUserPassword() {
      return this.userPassword;
   }

   public void setUserPassword(String userPassword) {
      this.userPassword = userPassword;
   }

   public String getProductType() {
      return this.ProductType;
   }

   public void setProductType(String productType) {
      this.ProductType = productType;
   }

   public String getEmpID() {
      return this.empID;
   }

   public void setEmpID(String empID) {
      this.empID = empID;
   }

   public String getEmpPassword() {
      return this.empPassword;
   }

   public void setEmpPassword(String empPassword) {
      this.empPassword = empPassword;
   }
}
