package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectOEOM {
   public String ClaimRefNo;
   public String InitiatorRole;
   public String InitiatorID;
   public String ApproveDecision;
   private String processInstanceName;
   private String workItemName;
   private String UserID;
   private String operationType;
   private String newUserId;
   private boolean IsOEOnline;
   private boolean IsEliteCases;
   private boolean IsBillCheck;
   private String HubID;
   private String ZonID;

   public String getClaimReferenceNumber() {
      return this.ClaimRefNo;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.ClaimRefNo = claimReferenceNumber;
   }

   public String getApproverDecision() {
      return this.ApproveDecision;
   }

   public void setApproverDecision(String approverDecision) {
      this.ApproveDecision = approverDecision;
   }

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }

   public String getWorkItemName() {
      return this.workItemName;
   }

   public void setWorkItemName(String workItemName) {
      this.workItemName = workItemName;
   }

   public String getUserId() {
      return this.UserID;
   }

   public void setUserId(String userId) {
      this.UserID = userId;
   }

   public String getInitiatorRole() {
      return this.InitiatorRole;
   }

   public void setInitiatorRole(String initiatorRole) {
      this.InitiatorRole = initiatorRole;
   }

   public String getInitiatorId() {
      return this.InitiatorID;
   }

   public void setInitiatorId(String initiatorId) {
      this.InitiatorID = initiatorId;
   }

   public String getOperationType() {
      return this.operationType;
   }

   public void setOperationType(String operationType) {
      this.operationType = operationType;
   }

   public String getNewUserId() {
      return this.newUserId;
   }

   public void setNewUserId(String newUserId) {
   }

   public boolean getIsOEOnline() {
      return this.IsOEOnline;
   }

   public void setIsOEOnline(boolean IsOeOnline) {
      this.IsOEOnline = IsOeOnline;
   }

   public boolean getIsEliteCases() {
      return this.IsEliteCases;
   }

   public void setIsEliteCases(boolean IsEliteCAses) {
      this.IsEliteCases = IsEliteCAses;
   }

   public boolean getIsBillCheck() {
      return this.IsBillCheck;
   }

   public void setIsBillCheck(boolean IsBillcheckpending) {
      this.IsBillCheck = IsBillcheckpending;
   }

   public String getHubID() {
      return this.HubID;
   }

   public void setHubID(String HuBID) {
      this.HubID = HuBID;
   }

   public String getZonID() {
      return this.ZonID;
   }

   public void setZonID(String ZoneID) {
      this.ZonID = ZoneID;
   }
}
