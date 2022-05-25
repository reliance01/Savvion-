package services.techdesk.util;

public class WorkItemObject {
   private String processInstanceName = null;
   private String workitemName = null;
   private String workStepName = null;
   private String performer = null;
   private String CallEndDate = null;
   private String CallStartDate = null;
   private String AssignDate = null;
   private String DueDate = null;
   private String TicketNo = null;
   private String userId = null;
   private String status = null;
   private String statusOther;
   private String remarks;
   private String otherRemarks;

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

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getStatusOther() {
      return this.statusOther;
   }

   public void setStatusOther(String statusOther) {
      this.statusOther = statusOther;
   }

   public String getRemarks() {
      return this.remarks;
   }

   public void setRemarks(String remarks) {
      this.remarks = remarks;
   }

   public String getOtherRemarks() {
      return this.otherRemarks;
   }

   public void setOtherRemarks(String otherRemarks) {
      this.otherRemarks = otherRemarks;
   }

   public String getUserId() {
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }
}
