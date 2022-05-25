package services.techdesk.util;

public class RequestObject {
   static final long serialVersionUID = -6727544753964961539L;
   public String UserId = null;
   public String TicketNumber = null;
   public String Status = null;
   public String Status_Sub = null;
   public String Status_Other = null;
   public String Remarks = null;
   public byte[] FileBytes = null;
   public String FileName = null;
   public String AssignToUserId = null;
   public TechDeskObject techdesk = null;

   public String getUserId() {
      return this.UserId;
   }

   public void setUserId(String userId) {
      this.UserId = userId;
   }

   public String getTicketNumber() {
      return this.TicketNumber;
   }

   public void setTicketNumber(String ticketNumber) {
      this.TicketNumber = ticketNumber;
   }

   public String getStatus() {
      return this.Status;
   }

   public void setStatus(String status) {
      this.Status = status;
   }

   public String getStatus_Sub() {
      return this.Status_Sub;
   }

   public void setStatus_Sub(String statusSub) {
      this.Status_Sub = statusSub;
   }

   public String getStatus_Other() {
      return this.Status_Other;
   }

   public void setStatus_Other(String statusOther) {
      this.Status_Other = statusOther;
   }

   public String getRemarks() {
      return this.Remarks;
   }

   public void setRemarks(String remarks) {
      this.Remarks = remarks;
   }

   public byte[] getFileBytes() {
      return this.FileBytes;
   }

   public void setFileBytes(byte[] fileBytes) {
      this.FileBytes = fileBytes;
   }

   public String getAssignToUserId() {
      return this.AssignToUserId;
   }

   public void setAssignToUserId(String assignToUserId) {
      this.AssignToUserId = assignToUserId;
   }

   public TechDeskObject getTechdesk() {
      return this.techdesk;
   }

   public void setTechdesk(TechDeskObject techdesk) {
      this.techdesk = techdesk;
   }

   public String getFileName() {
      return this.FileName;
   }

   public void setFileName(String fileName) {
      this.FileName = fileName;
   }
}
