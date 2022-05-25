package com.rgicl.techdesk.beans;

public class TDBean {
   private String processInstanceName;
   private String workitemName;
   private String workStepName;
   private String performer;
   private String CallEndDate;
   private String CallStartDate;
   private String AssignDate;
   private String DueDate;
   private String TicketNo;

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }

   public String getWorkitemName() {
      return this.workitemName;
   }

   public void setWorkitemName(String workitemName) {
      this.workitemName = workitemName;
   }

   public String getWorkStepName() {
      return this.workStepName;
   }

   public void setWorkStepName(String workStepName) {
      this.workStepName = workStepName;
   }

   public String getPerformer() {
      return this.performer;
   }

   public void setPerformer(String performer) {
      this.performer = performer;
   }

   public String getCallEndDate() {
      return this.CallEndDate;
   }

   public void setCallEndDate(String callEndDate) {
      this.CallEndDate = callEndDate;
   }

   public String getCallStartDate() {
      return this.CallStartDate;
   }

   public void setCallStartDate(String callStartDate) {
      this.CallStartDate = callStartDate;
   }

   public String getAssignDate() {
      return this.AssignDate;
   }

   public void setAssignDate(String assignDate) {
      this.AssignDate = assignDate;
   }

   public String getDueDate() {
      return this.DueDate;
   }

   public void setDueDate(String dueDate) {
      this.DueDate = dueDate;
   }

   public String getTicketNo() {
      return this.TicketNo;
   }

   public void setTicketNo(String ticketNo) {
      this.TicketNo = ticketNo;
   }
}
