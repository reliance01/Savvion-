package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectSurveyorAppointmentApproval {
   public String claimReferenceNumber;
   public String UserId;
   public String RequestTypeID;
   private String approverDecision;
   private String workItemName;
   private String processInstanceName;

   public String getClaimReferenceNumber() {
      return this.claimReferenceNumber;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.claimReferenceNumber = claimReferenceNumber;
   }

   public String getApproverDecision() {
      return this.approverDecision;
   }

   public void setApproverDecision(String approverDecision) {
      this.approverDecision = approverDecision;
   }

   public String getWorkItemName() {
      return this.workItemName;
   }

   public void setWorkItemName(String workItemName) {
      this.workItemName = workItemName;
   }

   public String getRequestTypeID() {
      return this.RequestTypeID;
   }

   public void setRequestTypeID(String requestTypeID) {
      this.RequestTypeID = requestTypeID;
   }

   public String getUserId() {
      return this.UserId;
   }

   public void setUserId(String userId) {
      this.UserId = userId;
   }

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }
}
