package rgicl.ecsehc.savvion.policy.objectmodel;

public class HcsLogObject {
   String PROCESSINSTANCE_NAME = null;
   String USER_ID = null;
   String WORKITEM_NAME = null;
   String APPROVER_DECISION = null;
   String RESPONSE_CODE = null;
   String RESPONSE_MESSAGE = null;
   long SETRESULT_WORKITEMARRAY = 0L;
   long COMPLETED_WORKITEMARRAY = 0L;
   String WORKITEM_CASESTATUS = null;
   int ISINSTANCE_COMPLETED = 0;

   public String getPROCESSINSTANCE_NAME() {
      return this.PROCESSINSTANCE_NAME;
   }

   public void setPROCESSINSTANCE_NAME(String pROCESSINSTANCE_NAME) {
      this.PROCESSINSTANCE_NAME = pROCESSINSTANCE_NAME;
   }

   public String getUSER_ID() {
      return this.USER_ID;
   }

   public void setUSER_ID(String uSER_ID) {
      this.USER_ID = uSER_ID;
   }

   public String getWORKITEM_NAME() {
      return this.WORKITEM_NAME;
   }

   public void setWORKITEM_NAME(String wORKITEM_NAME) {
      this.WORKITEM_NAME = wORKITEM_NAME;
   }

   public String getAPPROVER_DECISION() {
      return this.APPROVER_DECISION;
   }

   public void setAPPROVER_DECISION(String aPPROVER_DECISION) {
      this.APPROVER_DECISION = aPPROVER_DECISION;
   }

   public String getRESPONSE_CODE() {
      return this.RESPONSE_CODE;
   }

   public void setRESPONSE_CODE(String rESPONSE_CODE) {
      this.RESPONSE_CODE = rESPONSE_CODE;
   }

   public String getRESPONSE_MESSAGE() {
      return this.RESPONSE_MESSAGE;
   }

   public void setRESPONSE_MESSAGE(String rESPONSE_MESSAGE) {
      this.RESPONSE_MESSAGE = rESPONSE_MESSAGE;
   }

   public long getSETRESULT_WORKITEMARRAY() {
      return this.SETRESULT_WORKITEMARRAY;
   }

   public void setSETRESULT_WORKITEMARRAY(long sETRESULT_WORKITEMARRAY) {
      this.SETRESULT_WORKITEMARRAY = sETRESULT_WORKITEMARRAY;
   }

   public long getCOMPLETED_WORKITEMARRAY() {
      return this.COMPLETED_WORKITEMARRAY;
   }

   public void setCOMPLETED_WORKITEMARRAY(long cOMPLETED_WORKITEMARRAY) {
      this.COMPLETED_WORKITEMARRAY = cOMPLETED_WORKITEMARRAY;
   }

   public String getWORKITEM_CASESTATUS() {
      return this.WORKITEM_CASESTATUS;
   }

   public void setWORKITEM_CASESTATUS(String wORKITEM_CASESTATUS) {
      this.WORKITEM_CASESTATUS = wORKITEM_CASESTATUS;
   }

   public int getISINSTANCE_COMPLETED() {
      return this.ISINSTANCE_COMPLETED;
   }

   public void setISINSTANCE_COMPLETED(int iSINSTANCE_COMPLETED) {
      this.ISINSTANCE_COMPLETED = iSINSTANCE_COMPLETED;
   }
}
