package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class ResponseObject {
   private int responseCode = 0;
   private String processInstanceName;
   private WorkItemObject[] workItemObjects = null;
   private boolean isInstanceCompleted;
   private String Status;

   public ResponseObject() {
      this.workItemObjects = null;
      this.Status = null;
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
      return this.Status;
   }

   public void setStatus(String Status) {
      this.Status = Status;
   }
}
