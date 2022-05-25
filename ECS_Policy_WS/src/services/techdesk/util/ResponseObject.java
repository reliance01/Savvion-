package services.techdesk.util;

public class ResponseObject {
   static final long serialVersionUID = -6727544753964953091L;
   private String responseCode;
   private String[] resultStringArray;
   private WorkItemObject[] resultworkItemArray;
   public String message = null;

   public String getResponseCode() {
      return this.responseCode;
   }

   public void setResponseCode(String s) {
      this.responseCode = s;
   }

   public String[] getResultStringArray() {
      return this.resultStringArray;
   }

   public void setResultStringArray(String[] as) {
      this.resultStringArray = as;
   }

   public WorkItemObject[] getResultworkItemArray() {
      return this.resultworkItemArray;
   }

   public void setResultworkItemArray(WorkItemObject[] aworkitemobject) {
      this.resultworkItemArray = aworkitemobject;
   }

   public String toString() {
      return this.responseCode + ":" + this.resultStringArray + ":" + this.resultworkItemArray;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
