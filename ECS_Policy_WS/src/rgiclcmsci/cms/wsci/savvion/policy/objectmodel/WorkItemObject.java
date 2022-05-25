package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class WorkItemObject {
   private String workItemId;
   private String workItemName;
   private String workStepName;
   private String performer;
   private String roleId;
   private String roleName;

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

   public String getroleName() {
      return this.roleName;
   }

   public void setroleName(String roleName) {
      this.roleName = roleName;
   }

   public String toString() {
      return "workItemId: " + this.workItemId + ", workItemName: " + this.workItemName + ", workStepName: " + this.workStepName + ", performer:" + this.performer;
   }
}
