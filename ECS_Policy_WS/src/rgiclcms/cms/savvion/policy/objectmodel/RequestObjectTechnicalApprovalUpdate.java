package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectTechnicalApprovalUpdate {
   private String processInstanceName;
   private String workItemName;
   private String userId;
   private String toleranceAmount;
   private String roleName;
   private String newUserId;
   private String operationType;
   private String approveDecision;
   private String approverLimit;

   public String getApproverLimit() {
      return this.approverLimit;
   }

   public void setApproverLimit(String approverLimit) {
      this.approverLimit = approverLimit;
   }

   public String getApproveDecision() {
      return this.approveDecision;
   }

   public void setApproveDecision(String approveDecision) {
      this.approveDecision = approveDecision;
   }

   public String getRoleName() {
      return this.roleName;
   }

   public void setRoleName(String roleName) {
      this.roleName = roleName;
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

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }

   public String getWorkItemName() {
      return this.workItemName;
   }

   public void setWorkItemName(String workItemName) {
      this.workItemName = workItemName;
   }

   public String getUserId() {
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getToleranceAmount() {
      return this.toleranceAmount;
   }

   public void setToleranceAmount(String toleranceAmount) {
      this.toleranceAmount = toleranceAmount;
   }
}
