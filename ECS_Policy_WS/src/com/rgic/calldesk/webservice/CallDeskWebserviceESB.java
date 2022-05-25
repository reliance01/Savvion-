package com.rgic.calldesk.webservice;

import com.rgic.esb.utility.ESBUtility;
import javax.xml.namespace.QName;

public class CallDeskWebserviceESB {
   private String ticketNo;
   private String identifier;
   private String appSupportCloseDtString;
   private String appSupportRemark;
   private String appSupportDocumentName;
   private String appSupportStatus;
   private String approverStatus;
   private String approvedDateString;
   private String approverRemark;
   private String approverDocumentName;
   private String escalationStatus;
   private String wIName;
   private String pIName;
   private String data = "";

   public void execute() throws Exception {
      try {
         System.out.println("Inside CallDeskWebserviceESB Class*********************");
         String xmlDataUpdAS = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><SetAppSupportStatus xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://tempuri.org/\"><TicketNumber>";
         xmlDataUpdAS = xmlDataUpdAS + this.ticketNo + "</TicketNumber><AppSupportStatus>";
         xmlDataUpdAS = xmlDataUpdAS + this.appSupportStatus + "</AppSupportStatus><AppSupportCallClosedDate>";
         xmlDataUpdAS = xmlDataUpdAS + this.appSupportCloseDtString + "</AppSupportCallClosedDate><AppSupportRemark>";
         xmlDataUpdAS = xmlDataUpdAS + this.appSupportRemark + "</AppSupportRemark></SetAppSupportStatus>";
         System.out.println("xmlDataUpdAS  " + xmlDataUpdAS);
         String xmlDataUpdApprover = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><SetApproverStatus xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://tempuri.org/\"><TicketNumber>";
         xmlDataUpdApprover = xmlDataUpdApprover + this.ticketNo + "</TicketNumber><ApproverStatus>";
         xmlDataUpdApprover = xmlDataUpdApprover + this.approverStatus + "</ApproverStatus><ApprovedDate>";
         xmlDataUpdApprover = xmlDataUpdApprover + this.approvedDateString + "</ApprovedDate><ApproverRemarks>";
         xmlDataUpdApprover = xmlDataUpdApprover + this.approverRemark + "</ApproverRemarks></SetApproverStatus>";
         String xmlDataUpdEscl = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><EscalationRemark xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://tempuri.org/\"><pkTicketNo>";
         xmlDataUpdEscl = xmlDataUpdEscl + this.ticketNo + "</pkTicketNo><EscationRemark>";
         xmlDataUpdEscl = xmlDataUpdEscl + this.escalationStatus + "</EscationRemark></EscalationRemark>";
         String xmlUpdAppWaiting = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><UpdateUserStatus xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://tempuri.org/\"><TicketNumber>";
         xmlUpdAppWaiting = xmlUpdAppWaiting + this.ticketNo + "</TicketNumber><ApproverStatus>";
         xmlUpdAppWaiting = xmlUpdAppWaiting + this.approverStatus + "</ApproverStatus><ApprovedDate>";
         xmlUpdAppWaiting = xmlUpdAppWaiting + this.approvedDateString + "</ApprovedDate><ApproverRemarks>";
         xmlUpdAppWaiting = xmlUpdAppWaiting + this.approverRemark + "</ApproverRemarks><ApproverDocumentName>";
         xmlUpdAppWaiting = xmlUpdAppWaiting + this.approverDocumentName + "</ApproverDocumentName><WIName>";
         xmlUpdAppWaiting = xmlUpdAppWaiting + this.wIName + "</WIName><PIName>";
         xmlUpdAppWaiting = xmlUpdAppWaiting + this.pIName + "</PIName></UpdateUserStatus>";
         String xmlUpdASWaiting = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><UpdateUserStatus xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://tempuri.org/\"><TicketNumber>";
         xmlUpdASWaiting = xmlUpdASWaiting + this.ticketNo + "</TicketNumber><AppSupportStatus>";
         xmlUpdASWaiting = xmlUpdASWaiting + this.appSupportStatus + "</AppSupportStatus><AppSupportCloseDtString>";
         xmlUpdASWaiting = xmlUpdASWaiting + this.appSupportCloseDtString + "</AppSupportCloseDtString><AppSupportRemark>";
         xmlUpdASWaiting = xmlUpdASWaiting + this.appSupportRemark + "</AppSupportRemark><AppSupportDocumentName>";
         xmlUpdASWaiting = xmlUpdASWaiting + this.appSupportDocumentName + "</AppSupportDocumentName><WIName>";
         xmlUpdASWaiting = xmlUpdASWaiting + this.wIName + "</WIName><PIName>";
         xmlUpdASWaiting = xmlUpdASWaiting + this.pIName + "</PIName></UpdateUserStatus>";
         System.out.println("identifier:~~~~~~~~~~:  " + this.identifier);
         if (this.identifier.equals("1")) {
            System.out.println("xmlDataUpdApprover : " + xmlDataUpdApprover);
            this.data = ESBUtility.invokeESBService("http://10.65.5.163/ESB.ItineraryServices.Java.Response/ProcessItinerary.asmx", new QName("http://microsoft.practices.esb", "Process"), xmlDataUpdApprover);
         } else if (this.identifier.equals("2")) {
            System.out.println("xmlDataUpdEscl: " + xmlDataUpdEscl);
            this.data = ESBUtility.invokeESBService("http://10.65.5.163/ESB.ItineraryServices.Java.Response/ProcessItinerary.asmx", new QName("http://microsoft.practices.esb", "Process"), xmlDataUpdEscl);
         } else if (this.identifier.equals("3")) {
            System.out.println("xmlUpdAppWaiting: " + xmlUpdAppWaiting);
         } else if (this.identifier.equals("4")) {
            System.out.println("xmlUpdASWaiting: " + xmlUpdASWaiting);
         } else if (this.identifier.equals("5")) {
            System.out.println("xmlDataUpdAS: " + xmlDataUpdAS);
            this.data = ESBUtility.invokeESBService("http://10.65.5.163/ESB.ItineraryServices.Java.Response/ProcessItinerary.asmx", new QName("http://microsoft.practices.esb", "Process"), xmlDataUpdAS);
         }

         System.out.println("Response: " + this.data);
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new Exception("Exception in Adapter");
      }
   }

   public String getAppSupportCloseDtString() {
      return this.appSupportCloseDtString;
   }

   public void setAppSupportCloseDtString(String appSupportCloseDtString) {
      this.appSupportCloseDtString = appSupportCloseDtString;
   }

   public String getAppSupportRemark() {
      return this.appSupportRemark;
   }

   public void setAppSupportRemark(String appSupportRemark) {
      appSupportRemark = appSupportRemark.replaceAll("&", "&amp;");
      appSupportRemark = appSupportRemark.replaceAll("<", "&lt;");
      appSupportRemark = appSupportRemark.replaceAll(">", "&gt;");
      this.appSupportRemark = appSupportRemark;
   }

   public String getAppSupportStatus() {
      return this.appSupportStatus;
   }

   public void setAppSupportStatus(String appSupportStatus) {
      this.appSupportStatus = appSupportStatus;
   }

   public String getTicketNo() {
      return this.ticketNo;
   }

   public void setTicketNo(String ticketNo) {
      this.ticketNo = ticketNo;
   }

   public String getIdentifier() {
      return this.identifier;
   }

   public void setIdentifier(String identifier) {
      this.identifier = identifier;
   }

   public String getApprovedDateString() {
      return this.approvedDateString;
   }

   public void setApprovedDateString(String approvedDateString) {
      this.approvedDateString = approvedDateString;
   }

   public String getApproverRemark() {
      return this.approverRemark;
   }

   public void setApproverRemark(String approverRemark) {
      approverRemark = approverRemark.replaceAll("&", "&amp;");
      approverRemark = approverRemark.replaceAll("<", "&lt;");
      approverRemark = approverRemark.replaceAll(">", "&gt;");
      this.approverRemark = approverRemark;
   }

   public String getApproverStatus() {
      return this.approverStatus;
   }

   public void setApproverStatus(String approverStatus) {
      this.approverStatus = approverStatus;
   }

   public String getPIName() {
      return this.pIName;
   }

   public void setPIName(String name) {
      this.pIName = name;
   }

   public String getWIName() {
      return this.wIName;
   }

   public void setWIName(String name) {
      this.wIName = name;
   }

   public String getEscalationStatus() {
      return this.escalationStatus;
   }

   public void setEscalationStatus(String escalationStatus) {
      this.escalationStatus = escalationStatus;
   }

   public String getApproverDocumentName() {
      return this.approverDocumentName;
   }

   public void setApproverDocumentName(String approverDocumentName) {
      this.approverDocumentName = approverDocumentName;
   }

   public String getAppSupportDocumentName() {
      return this.appSupportDocumentName;
   }

   public void setAppSupportDocumentName(String appSupportDocumentName) {
      this.appSupportDocumentName = appSupportDocumentName;
   }
}
