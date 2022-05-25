package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectAssignTaskToUser {
   private String processInstanceName;
   private String workitemName;
   private String toBeAssignedUserId;

   public String getToBeAssignedUserId() {
      return this.toBeAssignedUserId;
   }

   public void setToBeAssignedUserId(String toBeAssignedUserId) {
      this.toBeAssignedUserId = toBeAssignedUserId;
   }

   public String getWorkitemName() {
      return this.workitemName;
   }

   public void setWorkitemName(String workitemName) {
      this.workitemName = workitemName;
   }

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }
}
