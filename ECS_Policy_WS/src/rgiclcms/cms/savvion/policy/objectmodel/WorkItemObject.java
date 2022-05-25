package rgiclcms.cms.savvion.policy.objectmodel;

public class WorkItemObject {
   private String workItemId;
   private String workItemName;
   private String workStepName;
   private String performer;
   private String roleId;
   private String roleName;
   private String claimReferenceNumber;
   private String productId;
   private String hubId;
   private String zoneId;

   public String getClaimReferenceNumber() {
      return this.claimReferenceNumber;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.claimReferenceNumber = claimReferenceNumber;
   }

   public String getProductId() {
      return this.productId;
   }

   public void setProductId(String productId) {
      this.productId = productId;
   }

   public String getHubId() {
      return this.hubId;
   }

   public void setHubId(String hubId) {
      this.hubId = hubId;
   }

   public String getZoneId() {
      return this.zoneId;
   }

   public void setZoneId(String zoneId) {
      this.zoneId = zoneId;
   }

   public String getWorkItemName() {
      return this.workItemName;
   }

   public void setWorkItemName(String workItemName) {
      this.workItemName = workItemName;
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

   public String getWorkItemId() {
      return this.workItemId;
   }

   public void setWorkItemId(String workItemId) {
      this.workItemId = workItemId;
   }

   public String getRoleId() {
      return this.roleId;
   }

   public void setRoleId(String roleId) {
      this.roleId = roleId;
   }

   public String getRoleName() {
      return this.roleName;
   }

   public void setRoleName(String roleName) {
      this.roleName = roleName;
   }

   public String toString() {
      return "workItemId: " + this.workItemId + ", workItemName: " + this.workItemName + ", workStepName: " + this.workStepName + ", performer:" + this.performer;
   }
}
