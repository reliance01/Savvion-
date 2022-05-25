package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObjectClaimIntimationUpdate {
   private String workItemName;
   private String processInstanceName;
   private String currentCMId;
   private String currentHubId;
   private String newHubId;
   private String newCMId;
   private String userId;
   private String operationType;

   public String getUserId() {
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getOperationType() {
      return this.operationType;
   }

   public void setOperationType(String operationType) {
      this.operationType = operationType;
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

   public String getCurrentCMId() {
      return this.currentCMId;
   }

   public void setCurrentCMId(String currentCMId) {
      this.currentCMId = currentCMId;
   }

   public String getCurrentHubId() {
      return this.currentHubId;
   }

   public void setCurrentHubId(String currentHubId) {
      this.currentHubId = currentHubId;
   }

   public String getNewHubId() {
      return this.newHubId;
   }

   public void setNewHubId(String newHubId) {
      this.newHubId = newHubId;
   }

   public String getNewCMId() {
      return this.newCMId;
   }

   public void setNewCMId(String newCMId) {
      this.newCMId = newCMId;
   }
}
