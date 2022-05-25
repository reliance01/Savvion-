package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectSurveyorExpensePaymentUpdate {
   private String workItemName;
   private String proccessInstanceName;
   private String userId;
   private String approveDecision;
   private String newUserId;
   private String operationType;
   private String approverLimit;

   public String getApproverLimit() {
      return this.approverLimit;
   }

   public void setApproverLimit(String approverLimit) {
      this.approverLimit = approverLimit;
   }

   public String getWorkItemName() {
      return this.workItemName;
   }

   public void setWorkItemName(String workItemName) {
      this.workItemName = workItemName;
   }

   public String getProccessInstanceName() {
      return this.proccessInstanceName;
   }

   public void setProccessInstanceName(String proccessInstanceName) {
      this.proccessInstanceName = proccessInstanceName;
   }

   public String getUserId() {
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getApproveDecision() {
      return this.approveDecision;
   }

   public void setApproveDecision(String approveDecision) {
      this.approveDecision = approveDecision;
   }

   public String getNewUserId() {
      return this.newUserId;
   }

   public void setNewUserId(String newUserId) {
      this.newUserId = newUserId;
   }

   public String getOperationType() {
      return this.operationType;
   }

   public void setOperationType(String operationType) {
      this.operationType = operationType;
   }
}
