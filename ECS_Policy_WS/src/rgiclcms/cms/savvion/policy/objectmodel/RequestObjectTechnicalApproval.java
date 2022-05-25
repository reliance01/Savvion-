package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectTechnicalApproval {
   public String claimReferenceNumber;
   public String productId;
   public String hubId;
   public String zoneId;
   public String estimatedValue;
   public String approverLimit;
   public String surveyorType;
   public String initiatorRole;
   public String initiatorId;
   public String toleranceAmount;
   public String approverDecision;
   public String teamId;
   public String TLCount;
   private String processInstanceName;
   private String workItemName;
   private String userId;

   public String getTeamId() {
      return this.teamId;
   }

   public void setTeamId(String teamId) {
      this.teamId = teamId;
   }

   public String getClaimReferenceNumber() {
      return this.claimReferenceNumber;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.claimReferenceNumber = claimReferenceNumber;
   }

   public String getSurveyorType() {
      return this.surveyorType;
   }

   public void setSurveyorType(String surveyorType) {
      this.surveyorType = surveyorType;
   }

   public String getEstimatedValue() {
      return this.estimatedValue;
   }

   public void setEstimatedValue(String estimatedValue) {
      this.estimatedValue = estimatedValue;
   }

   public String getApproverDecision() {
      return this.approverDecision;
   }

   public void setApproverDecision(String approverDecision) {
      this.approverDecision = approverDecision;
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
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getProductId() {
      return this.productId;
   }

   public void setProductId(String productId) {
      this.productId = productId;
   }

   public String getHubId() {
      return this.hubId;
   }

   public void setHubId(String hubId) {
      this.hubId = hubId;
   }

   public String getZoneId() {
      return this.zoneId;
   }

   public void setZoneId(String zoneId) {
      this.zoneId = zoneId;
   }

   public String getToleranceAmount() {
      return this.toleranceAmount;
   }

   public void setToleranceAmount(String toleranceAmount) {
      this.toleranceAmount = toleranceAmount;
   }

   public String getApproverLimit() {
      return this.approverLimit;
   }

   public void setApproverLimit(String approverLimit) {
      this.approverLimit = approverLimit;
   }

   public String getInitiatorRole() {
      return this.initiatorRole;
   }

   public void setInitiatorRole(String initiatorRole) {
      this.initiatorRole = initiatorRole;
   }

   public String getInitiatorId() {
      return this.initiatorId;
   }

   public void setInitiatorId(String initiatorId) {
      this.initiatorId = initiatorId;
   }

   public String getTLCount() {
      return this.TLCount;
   }

   public void setTLCount(String TLcount) {
      this.TLCount = TLcount;
   }
}
