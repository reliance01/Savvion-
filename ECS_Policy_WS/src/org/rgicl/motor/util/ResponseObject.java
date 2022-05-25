package org.rgicl.motor.util;

public class ResponseObject {
   static final long serialVersionUID = -6727544753963912707L;
   private String responseCode = null;
   private String[] resultStringArray = null;
   private WorkItemObject[] resultworkItemArray = null;
   private String message = null;

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

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

   public String toString() {
      return this.responseCode + ":" + this.resultStringArray + ":" + this.resultworkItemArray;
   }
}
