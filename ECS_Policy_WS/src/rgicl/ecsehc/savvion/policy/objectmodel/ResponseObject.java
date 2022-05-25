package rgicl.ecsehc.savvion.policy.objectmodel;

import java.util.Arrays;

public class ResponseObject {
   static final long serialVersionUID = -6727544753963912707L;
   private String responseCode = null;
   private String responseMessage = null;
   private String[] resultStringArray = null;
   private WorkItemObject[] resultworkItemArray = null;
   private String workItemCaseStatus = null;
   private boolean isInstanceCompleted = false;
   private WorkItemObject[] completedWorkItemArray = null;
   private boolean isFromSR = false;

   public String getResponseCode() {
      return this.responseCode;
   }

   public void setResponseCode(String responseCode) {
      this.responseCode = responseCode;
   }

   public String[] getResultStringArray() {
      return this.resultStringArray;
   }

   public void setResultStringArray(String[] resultStringArray) {
      this.resultStringArray = resultStringArray;
   }

   public WorkItemObject[] getResultworkItemArray() {
      return this.resultworkItemArray;
   }

   public void setResultworkItemArray(WorkItemObject[] resultworkItemArray) {
      this.resultworkItemArray = resultworkItemArray;
   }

   public String getWorkItemCaseStatus() {
      return this.workItemCaseStatus;
   }

   public void setWorkItemCaseStatus(String workItemCaseStatus) {
      this.workItemCaseStatus = workItemCaseStatus;
   }

   public boolean isInstanceCompleted() {
      return this.isInstanceCompleted;
   }

   public void setInstanceCompleted(boolean isInstanceCompleted) {
      this.isInstanceCompleted = isInstanceCompleted;
   }

   public String getResponseMessage() {
      return this.responseMessage;
   }

   public void setResponseMessage(String responseMessage) {
      this.responseMessage = responseMessage;
   }

   public WorkItemObject[] getCompletedWorkItemArray() {
      return this.completedWorkItemArray;
   }

   public void setCompletedWorkItemArray(WorkItemObject[] completedWorkItemArray) {
      this.completedWorkItemArray = completedWorkItemArray;
   }

   public boolean isFromSR() {
      return this.isFromSR;
   }

   public void setFromSR(boolean isFromSR) {
      this.isFromSR = isFromSR;
   }

   public String toString() {
      return "ResponseCode::" + this.responseCode + " && ResponseMessage::" + this.responseMessage + " && WorkItemCaseStatus::" + this.workItemCaseStatus + " && IsInstanceCompleted::" + this.isInstanceCompleted + " && ResultStringArray::" + Arrays.toString(this.resultStringArray) + " && ResultworkItemArray::" + Arrays.toString(this.resultworkItemArray) + " && CompletedWorkItemArray::" + Arrays.toString(this.completedWorkItemArray);
   }
}
