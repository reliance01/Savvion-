package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectMakerChecker {
   public String masterId;
   public String initiatorRole;
   public String initiatorId;
   private String approverDecision;
   private String workItemName;
   private String userId;
   private String processInstanceName;

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

   public String getApproverDecision() {
      return this.approverDecision;
   }

   public void setApproverDecision(String approverDecision) {
      this.approverDecision = approverDecision;
   }

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }

   public void setMasterId(String masterId) {
      this.masterId = masterId;
   }

   public String getMasterId() {
      return this.masterId;
   }

   public String getInitiatorRole() {
      return this.initiatorRole;
   }

   public void setInitiatorRole(String initiatorRole) {
      this.initiatorRole = initiatorRole;
   }

   public String getInitiatorId() {
      return this.initiatorId;
   }

   public void setInitiatorId(String initiatorId) {
      this.initiatorId = initiatorId;
   }
}
