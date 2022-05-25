package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObjectAssignTaskToUser {
   private String workitemName;
   private String toBeAssignedUserId;
   private String taskName;

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

   public String getTaskName() {
      return this.taskName;
   }

   public void setTaskName(String TaskName) {
      this.taskName = TaskName;
   }

   public String getProcessInstanceName() {
      return null;
   }
}
