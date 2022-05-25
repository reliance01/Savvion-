package com.rgic.pqms.webservices;

import com.rgic.pqms.common.exception.PQMSException;
import com.rgic.pqms.common.logger.PQMSLogger;
//import com.savvion.webservice.workflow.BizLogic1SoapBindingStub;
//import com.savvion.webservice.workflow.DataSlotinstance;
//import com.savvion.webservice.workflow.WorkItem;

import vam.com.rgi.rcorp.wsdl.BizLogic1SoapBindingStub;
import vam.com.rgi.rcorp.wsdl.DataSlotinstance;
import vam.com.rgi.rcorp.wsdl.WorkItem;

import java.net.URL;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

public class UpdateWorkflow {
   private static String session1;
   private static DataSlotinstance[] dsValues;
   private static WorkItem wi;
   private static String piName;
   private static String wiName;
   private static String username;
   private static String password;
   private static String userID;
   private static String userPassword;
   private static String ProposalID;
   private static String ProposalIDS;
   private static long wiid;
   private String actionTaken;
   private String userRole;
   private String isRSMEscalate;
   private String isBHEscalate;
   private String isRHEscalate;
   private String isSMEscalate;
   private String isVHEscalate;
   private String isZHEscalate;
   private String isRecommendToCU;
   private String isSubmitToRUM;
   private String isSubmitToZUM;
   private String isRecommendToZUM;
   private static DataSlotinstance dsiValue = new DataSlotinstance();
   private DataSlotinstance[] dsi;

   public UpdateWorkflow() {
      System.out.println("INSIDE UPDATEWF***************");
   }

   public void process() throws PQMSException {
      try {
         Logger logger = PQMSLogger.getLogger();
         logger.debug("Start of process()::: Inside Try/Catch");
         PQMSLogger.debug(UpdateWorkflow.class, "Got the environment.......");
         Service service = new Service();
         String endPoint1 = "http://10.65.5.121:18793/sbm/services/BizLogic1?wsdl";
         BizLogic1SoapBindingStub bindingStub1 = new BizLogic1SoapBindingStub(new URL(endPoint1), service);
         session1 = bindingStub1.connect(userID, userPassword);
         logger.info("USER ROLE************** " + this.userRole);
         logger.info("ACTION TAKEN*********** " + this.actionTaken);
         logger.error("ProposalID*********** " + ProposalID);
         logger.warn("userID******** " + userID);
         logger.warn("userPassword******** " + userPassword);
         WorkItem[] wiArray = bindingStub1.getAssignedWorkItemList(session1);
         logger.debug("LEN of WIARRAY***: " + wiArray.length);

         for(int i = 0; i < wiArray.length; ++i) {
            wi = wiArray[i];
            piName = wi.getPiName();
            wiName = wi.getName();
            wiid = wi.getId();
            dsValues = bindingStub1.getWorkItemDataSlotsFromWIID(session1, wiid);
            logger.debug("dsValues   LENGTH************ " + dsValues.length);

            for(int k = 0; k < dsValues.length; ++k) {
               dsiValue = dsValues[k];
               if (dsiValue.getName().equalsIgnoreCase("isRSMEscalate")) {
                  this.isRSMEscalate = dsiValue.getValue().toString();
                  logger.debug("isRSMEscalate************** " + this.isRSMEscalate);
               } else if (dsiValue.getName().equalsIgnoreCase("isBHEscalate")) {
                  this.isBHEscalate = dsiValue.getValue().toString();
                  logger.debug("isBHEscalate************** " + this.isBHEscalate);
               } else if (dsiValue.getName().equalsIgnoreCase("isRHEscalate")) {
                  this.isRHEscalate = dsiValue.getValue().toString();
                  logger.debug("isRHEscalate*********** " + this.isRHEscalate);
               } else if (dsiValue.getName().equalsIgnoreCase("isSMEscalate")) {
                  this.isSMEscalate = dsiValue.getValue().toString();
                  logger.debug("isSMEscalate************** " + this.isSMEscalate);
               } else if (dsiValue.getName().equalsIgnoreCase("isVHEscalate")) {
                  this.isVHEscalate = dsiValue.getValue().toString();
                  logger.debug("isVHEscalate*********** " + this.isVHEscalate);
               } else if (dsiValue.getName().equalsIgnoreCase("isZHEscalate")) {
                  this.isZHEscalate = dsiValue.getValue().toString();
                  logger.debug("isZHEscalate************** " + this.isZHEscalate);
               } else if (dsiValue.getName().equalsIgnoreCase("isRecommendToCU")) {
                  this.isRecommendToCU = dsiValue.getValue().toString();
                  logger.debug("isRecommendToCU from WorkItem*********** " + this.isRecommendToCU);
               } else if (dsiValue.getName().equalsIgnoreCase("isRecommendToZUM")) {
                  this.isRecommendToZUM = dsiValue.getValue().toString();
                  System.out.println("isRecommendToZUM from WorkItem*********** " + this.isRecommendToZUM);
               } else if (dsiValue.getName().equalsIgnoreCase("isSubmitToRUM")) {
                  this.isSubmitToRUM = dsiValue.getValue().toString();
                  System.out.println("isSubmitToRUM from WorkItem*********** " + this.isSubmitToRUM);
               } else if (dsiValue.getName().equalsIgnoreCase("isSubmitToZUM")) {
                  this.isSubmitToZUM = dsiValue.getValue().toString();
                  logger.debug("isSubmitToZUM from WorkItem*********** " + this.isSubmitToZUM);
               } else if (dsiValue.getName().equalsIgnoreCase("ProposalID")) {
                  ProposalIDS = dsiValue.getValue().toString();
                  logger.debug("Proposal ID from WorkItem*********** " + ProposalIDS);
                  logger.debug("ProposalID passed to Adapter*********** " + ProposalID);
               }
            }

            if (ProposalIDS.equalsIgnoreCase(ProposalID)) {
               logger.debug("Proposal IDs MATCHING***********");
               DataSlotinstance dsi4;
               DataSlotinstance dsi2;
               DataSlotinstance dsi5;
               DataSlotinstance dsi6;
               DataSlotinstance dsi7;
               DataSlotinstance dsi65;
               DataSlotinstance dsi66;
               DataSlotinstance[] dsi;
               if (this.userRole.equalsIgnoreCase("9")) {
                  if (this.actionTaken.equalsIgnoreCase("1")) {
                     logger.info("NEW PROPOSAL CREATED************** @ UM INBOX");
                  } else if (this.actionTaken.equalsIgnoreCase("5")) {
                     logger.info("PROPOSAL CLOSED**************");
                     dsi = new DataSlotinstance[11];
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("skipUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("skipRUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(true));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("skipZUM");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(true));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("skipCU");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(true));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isApprovedByCU");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(false));
                     dsi[4] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                     dsi65 = new DataSlotinstance();
                     dsi65.setName("isRejectedByCU");
                     dsi65.setType("Boolean");
                     dsi65.setValue(new Boolean(false));
                     dsi[5] = dsi65;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi65);
                     dsi66 = new DataSlotinstance();
                     dsi66.setName("isRejectedByCUToZH");
                     dsi66.setType("Boolean");
                     dsi66.setValue(new Boolean(false));
                     dsi[6] = dsi66;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi66);
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isSMEscalate");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(false));
                     dsi[7] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                     DataSlotinstance dsi8 = new DataSlotinstance();
                     dsi8.setName("isRecommendToRUM");
                     dsi8.setType("Boolean");
                     dsi8.setValue(new Boolean(true));
                     dsi[8] = dsi8;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi8);
                     DataSlotinstance dsi9 = new DataSlotinstance();
                     dsi9.setName("isRecommendToZUM");
                     dsi9.setType("Boolean");
                     dsi9.setValue(new Boolean(true));
                     dsi[9] = dsi9;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi9);
                     DataSlotinstance dsi10 = new DataSlotinstance();
                     dsi10.setName("isRecommendToCU");
                     dsi10.setType("Boolean");
                     dsi10.setValue(new Boolean(true));
                     dsi[10] = dsi10;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi10);
                  } else if (this.actionTaken.equalsIgnoreCase("7")) {
                     logger.debug("EscalateToRSM*******************SM");
                     dsi = new DataSlotinstance[2];
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isSMEscalate");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     logger.debug("isSMEscalate is set*******************");
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRSMEscalate");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(true));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     logger.debug("isRSMEscalate is set*******************");
                  } else if (this.actionTaken.equalsIgnoreCase("8")) {
                     logger.debug("EscalateToBH*******************SM");
                     dsi = new DataSlotinstance[2];
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isSMEscalate");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     logger.debug("isSMEscalate is set*******************");
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRSMEscalate");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     logger.debug("isRSMEscalate is set*******************");
                  }

                  bindingStub1.completeWorkItemFromWIID(session1, wiid, this.dsi);
                  logger.debug("COMPLETED*******");
               } else if (this.userRole.equalsIgnoreCase("2")) {
                  dsi = new DataSlotinstance[2];
                  if (this.actionTaken.equalsIgnoreCase("11")) {
                     logger.debug("UM*******************Recommend");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRecommendToRUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                  } else if (this.actionTaken.equalsIgnoreCase("2")) {
                     logger.debug("UM*******************Reject");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToRUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                  } else if (this.actionTaken.equalsIgnoreCase("4")) {
                     logger.debug("UM*******************Approve");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(false));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToRUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                  }

                  bindingStub1.completeWorkItemFromWIID(session1, wiid, dsi);
                  logger.debug("COMPLETED*******");
               } else if (this.userRole.equalsIgnoreCase("3")) {
                  if (this.actionTaken.equalsIgnoreCase("11")) {
                     logger.debug("RUM*******************Recommend");
                     dsi = new DataSlotinstance[2];
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRecommendToZUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByRUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isSMEscalate.equalsIgnoreCase("false") && this.isRSMEscalate.equalsIgnoreCase("false")) {
                     logger.debug("RUM*******************Reject");
                     dsi = new DataSlotinstance[3];
                     logger.debug("6*******Normal Rejection");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByRUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToZUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByRUMToBH");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRSMEscalate.equalsIgnoreCase("true") && this.isSMEscalate.equalsIgnoreCase("true")) {
                     logger.debug("RUM*******************Reject");
                     dsi = new DataSlotinstance[4];
                     logger.debug("6******Rejection To RSM");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByRUMToRSM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToZUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByRUMToBH");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(true));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByRUM");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRSMEscalate.equalsIgnoreCase("false") && this.isSMEscalate.equalsIgnoreCase("true")) {
                     logger.debug("RUM*******************Reject");
                     dsi = new DataSlotinstance[4];
                     logger.debug("6******Rejection To BH");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByRUMToBH");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToZUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByRUMToRSM");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     logger.debug("66*******RUM Rejection");
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByRUM");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                  } else if (this.actionTaken.equalsIgnoreCase("4")) {
                     logger.debug("RUM*******************Approve");
                     dsi = new DataSlotinstance[2];
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByRUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(false));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToZUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                  }

                  bindingStub1.completeWorkItemFromWIID(session1, wiid, this.dsi);
                  logger.debug("COMPLETED*******RUM");
               } else if (this.userRole.equalsIgnoreCase("13")) {
                  if (this.actionTaken.equalsIgnoreCase("11")) {
                     logger.debug("ZUM*******************Recommend");
                     dsi = new DataSlotinstance[2];
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRecommendToCU");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByZUM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRHEscalate.equalsIgnoreCase("false") && this.isSubmitToRUM.equalsIgnoreCase("false")) {
                     logger.debug("ZUM*******************Reject");
                     dsi = new DataSlotinstance[3];
                     logger.debug("8*************NORMAL REJECTION");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByZUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToCU");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByZUMToRH");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRHEscalate.equalsIgnoreCase("true")) {
                     logger.debug("ZUM*******************Reject");
                     dsi = new DataSlotinstance[4];
                     logger.debug("8******************* Rejection to RH");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByZUMToRH");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToCU");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByZUM");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByZUMToRSMBH");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRSMEscalate.equalsIgnoreCase("true") && this.isSMEscalate.equalsIgnoreCase("true") && this.isRecommendToZUM.equalsIgnoreCase("true")) {
                     dsi = new DataSlotinstance[5];
                     logger.debug("6******Rejection To RSM");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByRUMToRSM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByZUMToRH");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(true));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRecommendToCU");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByZUM");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isRejectedByZUMToRSMBH");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(true));
                     dsi[4] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRSMEscalate.equalsIgnoreCase("false") && this.isSMEscalate.equalsIgnoreCase("true") && this.isRecommendToZUM.equalsIgnoreCase("true")) {
                     logger.debug("RUM*******************Reject");
                     dsi = new DataSlotinstance[4];
                     logger.debug("6******Rejection To BH");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByZUMToRH");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByRUMToRSM");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[2] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRecommendToCU");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByZUM");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isRejectedByZUMToRSMBH");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(true));
                     dsi[4] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                  } else if (this.actionTaken.equalsIgnoreCase("4")) {
                     logger.debug("ZUM*******************Approve");
                     dsi = new DataSlotinstance[2];
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByZUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(false));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRecommendToCU");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                  }

                  bindingStub1.completeWorkItemFromWIID(session1, wiid, this.dsi);
                  logger.debug("COMPLETED*******ZUM");
               } else if (this.userRole.equalsIgnoreCase("14")) {
                  logger.debug("INSIDE CU***************");
                  if (this.actionTaken.equalsIgnoreCase("2") && this.isRecommendToCU.equalsIgnoreCase("true") && this.isSubmitToRUM.equalsIgnoreCase("false") && this.isSubmitToZUM.equalsIgnoreCase("false") && this.isVHEscalate.equalsIgnoreCase("false") && this.isZHEscalate.equalsIgnoreCase("false")) {
                     logger.debug("CU*******************Reject");
                     dsi = new DataSlotinstance[3];
                     logger.debug("9*******************NORMAL REJECT");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByCU");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isApprovedByCU");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByCUToZH");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isVHEscalate.equalsIgnoreCase("true") && this.isZHEscalate.equalsIgnoreCase("false")) {
                     logger.debug("CU*******************Reject");
                     dsi = new DataSlotinstance[5];
                     logger.debug("6******Rejection To VH");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByCUToVH");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     logger.debug("62*******RUM Rejection");
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isApprovedByCU");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByCUToZH");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(true));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     logger.debug("66*******RUM Rejection");
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByCU");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isRejectedByCUToRH");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(false));
                     dsi[4] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isVHEscalate.equalsIgnoreCase("false") && this.isZHEscalate.equalsIgnoreCase("true")) {
                     logger.debug("CU*******************Reject");
                     dsi = new DataSlotinstance[5];
                     logger.debug("6******Rejection To ZH");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByCUToZH");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     logger.debug("62*******RUM Rejection");
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isApprovedByCU");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByCUToVH");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByCUToRH");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                     logger.debug("66*******RUM Rejection");
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isRejectedByCU");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(false));
                     dsi[4] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isSubmitToZUM.equalsIgnoreCase("true") && this.isRecommendToCU.equalsIgnoreCase("true")) {
                     logger.debug("CU*******************Reject");
                     dsi = new DataSlotinstance[6];
                     logger.debug("6******Rejection To RH");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByCUToZH");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByCUToRH");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(true));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     logger.debug("62*******RUM Rejection");
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isApprovedByCU");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     logger.debug("66*******RUM Rejection");
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByCU");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isRejectedByCUToVH");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(false));
                     dsi[4] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                     dsi65 = new DataSlotinstance();
                     dsi65.setName("isRejectedByZUMToRSMBH");
                     dsi65.setType("Boolean");
                     dsi65.setValue(new Boolean(false));
                     dsi[5] = dsi65;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi65);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRSMEscalate.equalsIgnoreCase("true") && this.isSMEscalate.equalsIgnoreCase("true") && this.isRecommendToZUM.equalsIgnoreCase("true") && this.isRecommendToCU.equalsIgnoreCase("true")) {
                     dsi = new DataSlotinstance[7];
                     logger.debug("6******Rejection To RSM");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByCUToZH");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByCUToRH");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(true));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByZUMToRSMBH");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(true));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByRUMToRSM");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(true));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isRejectedByCUToVH");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(false));
                     dsi[4] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                     dsi65 = new DataSlotinstance();
                     dsi65.setName("isRejectedByCU");
                     dsi65.setType("Boolean");
                     dsi65.setValue(new Boolean(false));
                     dsi[5] = dsi65;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi65);
                     dsi66 = new DataSlotinstance();
                     dsi66.setName("isApprovedByCU");
                     dsi66.setType("Boolean");
                     dsi66.setValue(new Boolean(false));
                     dsi[6] = dsi66;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi66);
                  } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRSMEscalate.equalsIgnoreCase("false") && this.isSMEscalate.equalsIgnoreCase("true") && this.isRecommendToZUM.equalsIgnoreCase("true") && this.isRecommendToCU.equalsIgnoreCase("true")) {
                     dsi = new DataSlotinstance[7];
                     logger.debug("6******Rejection To BH");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isRejectedByCUToZH");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByCUToRH");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(true));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByZUMToRSMBH");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(true));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     dsi6 = new DataSlotinstance();
                     dsi6.setName("isRejectedByRUMToRSM");
                     dsi6.setType("Boolean");
                     dsi6.setValue(new Boolean(false));
                     dsi[3] = dsi6;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                     dsi7 = new DataSlotinstance();
                     dsi7.setName("isRejectedByCUToVH");
                     dsi7.setType("Boolean");
                     dsi7.setValue(new Boolean(false));
                     dsi[4] = dsi7;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                     dsi65 = new DataSlotinstance();
                     dsi65.setName("isRejectedByCU");
                     dsi65.setType("Boolean");
                     dsi65.setValue(new Boolean(false));
                     dsi[5] = dsi65;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi65);
                     dsi66 = new DataSlotinstance();
                     dsi66.setName("isApprovedByCU");
                     dsi66.setType("Boolean");
                     dsi66.setValue(new Boolean(false));
                     dsi[6] = dsi66;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi66);
                  } else if (this.actionTaken.equalsIgnoreCase("4")) {
                     logger.debug("CU*******************Approve");
                     dsi = new DataSlotinstance[3];
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isApprovedByCU");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(true));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRejectedByCUToZH");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     dsi5 = new DataSlotinstance();
                     dsi5.setName("isRejectedByCU");
                     dsi5.setType("Boolean");
                     dsi5.setValue(new Boolean(false));
                     dsi[2] = dsi5;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                  }

                  bindingStub1.completeWorkItemFromWIID(session1, wiid, this.dsi);
                  logger.debug("COMPLETED*******");
               } else if (!this.userRole.equalsIgnoreCase("10") && !this.userRole.equalsIgnoreCase("6")) {
                  if (this.userRole.equalsIgnoreCase("7")) {
                     if (!this.actionTaken.equalsIgnoreCase("9") && !this.actionTaken.equalsIgnoreCase("12")) {
                        if (!this.actionTaken.equalsIgnoreCase("10") && !this.actionTaken.equalsIgnoreCase("13")) {
                           if (this.actionTaken.equalsIgnoreCase("3")) {
                              logger.debug("RH*******************Submit");
                              dsi = new DataSlotinstance[3];
                              logger.debug("2*******************SubmittoZUM");
                              dsi4 = new DataSlotinstance();
                              dsi4.setName("isSubmitToZUM");
                              dsi4.setType("Boolean");
                              dsi4.setValue(new Boolean(true));
                              dsi[0] = dsi4;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                              dsi2 = new DataSlotinstance();
                              dsi2.setName("isVHEscalate");
                              dsi2.setType("Boolean");
                              dsi2.setValue(new Boolean(false));
                              dsi[1] = dsi2;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                              dsi5 = new DataSlotinstance();
                              dsi5.setName("isClosedByRH");
                              dsi5.setType("Boolean");
                              dsi5.setValue(new Boolean(false));
                              dsi[2] = dsi5;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                           } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRSMEscalate.equalsIgnoreCase("true")) {
                              logger.debug("RH*******************RejectToRSM");
                              dsi = new DataSlotinstance[5];
                              dsi4 = new DataSlotinstance();
                              dsi4.setName("isRejectedByRHToRSM");
                              dsi4.setType("Boolean");
                              dsi4.setValue(new Boolean(true));
                              dsi[0] = dsi4;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                              dsi2 = new DataSlotinstance();
                              dsi2.setName("isRejectedByRHToBH");
                              dsi2.setType("Boolean");
                              dsi2.setValue(new Boolean(false));
                              dsi[1] = dsi2;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                              dsi5 = new DataSlotinstance();
                              dsi5.setName("isSubmitToZUM");
                              dsi5.setType("Boolean");
                              dsi5.setValue(new Boolean(false));
                              dsi[2] = dsi5;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                              dsi6 = new DataSlotinstance();
                              dsi6.setName("isVHEscalate");
                              dsi6.setType("Boolean");
                              dsi6.setValue(new Boolean(false));
                              dsi[3] = dsi6;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                              dsi7 = new DataSlotinstance();
                              dsi7.setName("isClosedByRH");
                              dsi7.setType("Boolean");
                              dsi7.setValue(new Boolean(true));
                              dsi[4] = dsi7;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                           } else if (this.actionTaken.equalsIgnoreCase("2") && this.isRSMEscalate.equalsIgnoreCase("false")) {
                              logger.debug("RH*******************RejecttoBH");
                              dsi = new DataSlotinstance[5];
                              dsi4 = new DataSlotinstance();
                              dsi4.setName("isRejectedByRHToBH");
                              dsi4.setType("Boolean");
                              dsi4.setValue(new Boolean(true));
                              dsi[0] = dsi4;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                              dsi2 = new DataSlotinstance();
                              dsi2.setName("isRejectedByRHToRSM");
                              dsi2.setType("Boolean");
                              dsi2.setValue(new Boolean(false));
                              dsi[1] = dsi2;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                              dsi5 = new DataSlotinstance();
                              dsi5.setName("isSubmitToZUM");
                              dsi5.setType("Boolean");
                              dsi5.setValue(new Boolean(false));
                              dsi[2] = dsi5;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                              dsi6 = new DataSlotinstance();
                              dsi6.setName("isVHEscalate");
                              dsi6.setType("Boolean");
                              dsi6.setValue(new Boolean(false));
                              dsi[3] = dsi6;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                              dsi7 = new DataSlotinstance();
                              dsi7.setName("isClosedByRH");
                              dsi7.setType("Boolean");
                              dsi7.setValue(new Boolean(true));
                              dsi[4] = dsi7;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                           } else if (this.actionTaken.equalsIgnoreCase("5")) {
                              logger.debug("RH*******************Close");
                              dsi = new DataSlotinstance[5];
                              logger.debug("2*******************SubmittoZUM");
                              dsi4 = new DataSlotinstance();
                              dsi4.setName("isSubmitToZUM");
                              dsi4.setType("Boolean");
                              dsi4.setValue(new Boolean(false));
                              dsi[0] = dsi4;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                              dsi2 = new DataSlotinstance();
                              dsi2.setName("isVHEscalate");
                              dsi2.setType("Boolean");
                              dsi2.setValue(new Boolean(false));
                              dsi[1] = dsi2;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                              dsi5 = new DataSlotinstance();
                              dsi5.setName("isRejectedByRHToBH");
                              dsi5.setType("Boolean");
                              dsi5.setValue(new Boolean(false));
                              dsi[2] = dsi5;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                              dsi6 = new DataSlotinstance();
                              dsi6.setName("isRejectedByRHToRSM");
                              dsi6.setType("Boolean");
                              dsi6.setValue(new Boolean(false));
                              dsi[3] = dsi6;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi6);
                              dsi7 = new DataSlotinstance();
                              dsi7.setName("isClosedByRH");
                              dsi7.setType("Boolean");
                              dsi7.setValue(new Boolean(true));
                              dsi[4] = dsi7;
                              bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi7);
                           }
                        } else {
                           logger.debug("RH*******************Escalate/RecommendToZH");
                           dsi = new DataSlotinstance[3];
                           dsi4 = new DataSlotinstance();
                           dsi4.setName("isVHEscalate");
                           dsi4.setType("Boolean");
                           dsi4.setValue(new Boolean(false));
                           dsi[0] = dsi4;
                           bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                           dsi2 = new DataSlotinstance();
                           dsi2.setName("isSubmitToZUM");
                           dsi2.setType("Boolean");
                           dsi2.setValue(new Boolean(false));
                           dsi[1] = dsi2;
                           bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                           dsi5 = new DataSlotinstance();
                           dsi5.setName("isClosedByRH");
                           dsi5.setType("Boolean");
                           dsi5.setValue(new Boolean(false));
                           dsi[2] = dsi5;
                           bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                        }
                     } else {
                        logger.debug("RH*******************Escalate/RecommendToVH");
                        dsi = new DataSlotinstance[3];
                        logger.debug("2*******************VH");
                        dsi4 = new DataSlotinstance();
                        dsi4.setName("isVHEscalate");
                        dsi4.setType("Boolean");
                        dsi4.setValue(new Boolean(true));
                        dsi[0] = dsi4;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                        dsi2 = new DataSlotinstance();
                        dsi2.setName("isSubmitToZUM");
                        dsi2.setType("Boolean");
                        dsi2.setValue(new Boolean(false));
                        dsi[1] = dsi2;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                        dsi5 = new DataSlotinstance();
                        dsi5.setName("isClosedByRH");
                        dsi5.setType("Boolean");
                        dsi5.setValue(new Boolean(false));
                        dsi[2] = dsi5;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi5);
                     }

                     bindingStub1.completeWorkItemFromWIID(session1, wiid, this.dsi);
                     logger.debug("COMPLETED*******");
                  } else if (this.userRole.equalsIgnoreCase("15") || this.userRole.equalsIgnoreCase("12")) {
                     dsi = new DataSlotinstance[2];
                     if (this.actionTaken.equalsIgnoreCase("2")) {
                        logger.debug("VH || ZH *******************Reject");
                        dsi4 = new DataSlotinstance();
                        dsi4.setName("isRejectedByVHZH");
                        dsi4.setType("Boolean");
                        dsi4.setValue(new Boolean(true));
                        dsi[1] = dsi4;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                        dsi2 = new DataSlotinstance();
                        dsi2.setName("isSubmitToCU");
                        dsi2.setType("Boolean");
                        dsi2.setValue(new Boolean(false));
                        dsi[0] = dsi2;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     } else if (this.actionTaken.equalsIgnoreCase("3")) {
                        logger.debug("VH || ZH *******************Submit");
                        dsi4 = new DataSlotinstance();
                        dsi4.setName("isSubmitToCU");
                        dsi4.setType("Boolean");
                        dsi4.setValue(new Boolean(true));
                        dsi[0] = dsi4;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                        dsi2 = new DataSlotinstance();
                        dsi2.setName("isRejectedByVHZH");
                        dsi2.setType("Boolean");
                        dsi2.setValue(new Boolean(false));
                        dsi[1] = dsi2;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     } else if (this.actionTaken.equalsIgnoreCase("5")) {
                        logger.debug("VH || ZH *******************Close");
                        dsi4 = new DataSlotinstance();
                        dsi4.setName("isSubmitToCU");
                        dsi4.setType("Boolean");
                        dsi4.setValue(new Boolean(false));
                        dsi[0] = dsi4;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                        dsi2 = new DataSlotinstance();
                        dsi2.setName("isRejectedByVHZH");
                        dsi2.setType("Boolean");
                        dsi2.setValue(new Boolean(false));
                        dsi[1] = dsi2;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     }

                     bindingStub1.completeWorkItemFromWIID(session1, wiid, dsi);
                     logger.debug("COMPLETED*******");
                  }
               } else {
                  dsi = new DataSlotinstance[2];
                  if (!this.actionTaken.equalsIgnoreCase("2") && !this.actionTaken.equalsIgnoreCase("5")) {
                     if (!this.actionTaken.equalsIgnoreCase("6") && !this.actionTaken.equalsIgnoreCase("11")) {
                        if (this.actionTaken.equalsIgnoreCase("3")) {
                           logger.debug("RSM || BH*******************Submit");
                           dsi4 = new DataSlotinstance();
                           dsi4.setName("isSubmitToRUM");
                           dsi4.setType("Boolean");
                           dsi4.setValue(new Boolean(true));
                           dsi[0] = dsi4;
                           bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                           dsi2 = new DataSlotinstance();
                           dsi2.setName("isRHEscalate");
                           dsi2.setType("Boolean");
                           dsi2.setValue(new Boolean(false));
                           dsi[1] = dsi2;
                           bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                        }
                     } else {
                        logger.debug("RSM || BH*******************Escalate");
                        dsi4 = new DataSlotinstance();
                        dsi4.setName("isRHEscalate");
                        dsi4.setType("Boolean");
                        dsi4.setValue(new Boolean(true));
                        dsi[0] = dsi4;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                        dsi2 = new DataSlotinstance();
                        dsi2.setName("isSubmitToRUM");
                        dsi2.setType("Boolean");
                        dsi2.setValue(new Boolean(false));
                        dsi[1] = dsi2;
                        bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                     }
                  } else {
                     logger.debug("RSM || BH*******************Reject");
                     dsi4 = new DataSlotinstance();
                     dsi4.setName("isSubmitToRUM");
                     dsi4.setType("Boolean");
                     dsi4.setValue(new Boolean(false));
                     dsi[0] = dsi4;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi4);
                     dsi2 = new DataSlotinstance();
                     dsi2.setName("isRHEscalate");
                     dsi2.setType("Boolean");
                     dsi2.setValue(new Boolean(false));
                     dsi[1] = dsi2;
                     bindingStub1.setWorkItemDataSlotValue(session1, wiName, dsi2);
                  }

                  bindingStub1.completeWorkItemFromWIID(session1, wiid, dsi);
                  logger.debug("COMPLETED*******");
               }
            }
         }
      } catch (Exception var22) {
         var22.printStackTrace();
      } finally {
         session1 = null;
      }

   }

   public static String getPassword() {
      return password;
   }

   public static void setPassword(String password) {
      UpdateWorkflow.password = password;
   }

   public static String getUsername() {
      return username;
   }

   public static void setUsername(String username) {
      UpdateWorkflow.username = username;
   }

   public static String getProposalID() {
      return ProposalID;
   }

   public static void setProposalID(String proposalID) {
      ProposalID = proposalID;
   }

   public String getActionTaken() {
      return this.actionTaken;
   }

   public void setActionTaken(String actionTaken) {
      this.actionTaken = actionTaken;
   }

   public String getUserRole() {
      return this.userRole;
   }

   public void setUserRole(String userRole) {
      this.userRole = userRole;
   }

   public static String getUserID() {
      return userID;
   }

   public static void setUserID(String userID) {
      UpdateWorkflow.userID = userID;
   }

   public static String getUserPassword() {
      return userPassword;
   }

   public static void setUserPassword(String userPassword) {
      UpdateWorkflow.userPassword = userPassword;
   }
}
