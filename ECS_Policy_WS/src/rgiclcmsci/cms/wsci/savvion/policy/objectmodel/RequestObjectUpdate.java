package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObjectUpdate {
   public String workItemName;
   public String processInstanceName;
   public String userId;
   public String newUserId;
   public String approveDecision;
   public String operationType;
   public String ApproverLtd;

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

   public String getUserId() {
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getNewUserId() {
      return this.newUserId;
   }

   public void setNewUserId(String newUserId) {
      this.newUserId = newUserId;
   }

   public String getApproveDecision() {
      return this.approveDecision;
   }

   public void setApproveDecision(String approveDecision) {
      this.approveDecision = approveDecision;
   }

   public String getOperationType() {
      return this.operationType;
   }

   public void setOperationType(String operationType) {
      this.operationType = operationType;
   }

   public String getApproverLtd() {
      return this.ApproverLtd;
   }

   public void setApproverLtd(String Approverlimit) {
      this.ApproverLtd = Approverlimit;
   }
}
