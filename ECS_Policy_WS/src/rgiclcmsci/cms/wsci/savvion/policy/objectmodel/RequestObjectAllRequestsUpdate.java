package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObjectAllRequestsUpdate {
   private String operationType;
   private String userID;
   private String newUserID;
   private String approveDecision;
   private String workItemName;
   private String processInstanceName;

   public String getOperationType() {
      return this.operationType;
   }

   public void setOperationType(String operationType) {
      this.operationType = operationType;
   }

   public String getUserID() {
      return this.userID;
   }

   public void setUserID(String userID) {
      this.userID = userID;
   }

   public String getNewUserID() {
      return this.newUserID;
   }

   public void setNewUserID(String newUserID) {
      this.newUserID = newUserID;
   }

   public String getApproveDecision() {
      return this.approveDecision;
   }

   public void setApproveDecision(String approveDecision) {
      this.approveDecision = approveDecision;
   }

   public String getWorkItemName() {
      return this.workItemName;
   }

   public void setWorkItemName(String workItemName) {
      this.workItemName = workItemName;
   }

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }
}
