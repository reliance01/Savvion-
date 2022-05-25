package rgiclcms.cms.savvion.policy.objectmodel;

public class ResponseObject {
   private int responseCode = 0;
   private String processInstanceName;
   private WorkItemObject[] workItemObjects = null;
   private boolean isInstanceCompleted;
   private String status;

   public ResponseObject() {
      this.workItemObjects = null;
      this.status = null;
   }

   public WorkItemObject[] getWorkItemObjects() {
      return this.workItemObjects;
   }

   public void setWorkItemObjects(WorkItemObject[] workItemObjects) {
      this.workItemObjects = workItemObjects;
   }

   public int getResponseCode() {
      return this.responseCode;
   }

   public void setResponseCode(int responseCode) {
      this.responseCode = responseCode;
   }

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }

   public boolean isInstanceCompleted() {
      return this.isInstanceCompleted;
   }

   public void setInstanceCompleted(boolean isInstanceCompleted) {
      this.isInstanceCompleted = isInstanceCompleted;
   }

   public String toString() {
      return "responseCode:" + this.responseCode + " ,processInstanceName: " + this.processInstanceName + " isInstanceCompleted: " + this.isInstanceCompleted + "workItemArray: " + this.workItemObjects;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
