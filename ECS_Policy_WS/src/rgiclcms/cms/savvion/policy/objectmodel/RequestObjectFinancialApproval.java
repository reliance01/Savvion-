package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectFinancialApproval {
   public String riskScore;
   public String finalLossAmount;
   public String riskScoreLimitForFA;
   public String finalLossAmountLimitForFA;
   public String userAppearsInFAMaster;
   public String riskScoreLimitForCA;
   public String finalLossAmountLimitForCA;
   public String userAppearsInCAMaster;
   public String lastTechnicalApproverRole;
   public String initiatorRole;
   public String initiatorId;
   public String HubId;
   public String ZoneId;
   public String ProductId;
   public String ClaimReferenceNumber;
   public String jobTypeCA;
   public String jobTypeFA;
   public String teamId;
   public String TLCount;
   private String approverLimitFA;
   private String approverLimitCA;

   public String getTeamId() {
      return this.teamId;
   }

   public void setTeamId(String teamId) {
      this.teamId = teamId;
   }

   public String getJobTypeCA() {
      return this.jobTypeCA;
   }

   public void setJobTypeCA(String jobTypeCA) {
      this.jobTypeCA = jobTypeCA;
   }

   public String getJobTypeFA() {
      return this.jobTypeFA;
   }

   public void setJobTypeFA(String jobTypeFA) {
      this.jobTypeFA = jobTypeFA;
   }

   public String getApproverLimitFA() {
      return this.approverLimitFA;
   }

   public void setApproverLimitFA(String approverLimitFA) {
      this.approverLimitFA = approverLimitFA;
   }

   public String getApproverLimitCA() {
      return this.approverLimitCA;
   }

   public void setApproverLimitCA(String approverLimitCA) {
      this.approverLimitCA = approverLimitCA;
   }

   public String getRiskScore() {
      return this.riskScore;
   }

   public void setRiskScore(String riskScore) {
      this.riskScore = riskScore;
   }

   public String getFinalLossAmount() {
      return this.finalLossAmount;
   }

   public void setFinalLossAmount(String finalLossAmount) {
      this.finalLossAmount = finalLossAmount;
   }

   public String getRiskScoreLimitForFA() {
      return this.riskScoreLimitForFA;
   }

   public void setRiskScoreLimitForFA(String riskScoreLimitForFA) {
      this.riskScoreLimitForFA = riskScoreLimitForFA;
   }

   public String getFinalLossAmountLimitForFA() {
      return this.finalLossAmountLimitForFA;
   }

   public void setFinalLossAmountLimitForFA(String finalLossAmountLimitForFA) {
      this.finalLossAmountLimitForFA = finalLossAmountLimitForFA;
   }

   public String getUserAppearsInFAMaster() {
      return this.userAppearsInFAMaster;
   }

   public void setUserAppearsInFAMaster(String userAppearsInFAMaster) {
      this.userAppearsInFAMaster = userAppearsInFAMaster;
   }

   public String getRiskScoreLimitForCA() {
      return this.riskScoreLimitForCA;
   }

   public void setRiskScoreLimitForCA(String riskScoreLimitForCA) {
      this.riskScoreLimitForCA = riskScoreLimitForCA;
   }

   public String getFinalLossAmountLimitForCA() {
      return this.finalLossAmountLimitForCA;
   }

   public void setFinalLossAmountLimitForCA(String finalLossAmountLimitForCA) {
      this.finalLossAmountLimitForCA = finalLossAmountLimitForCA;
   }

   public String getUserAppearsInCAMaster() {
      return this.userAppearsInCAMaster;
   }

   public void setUserAppearsInCAMaster(String userAppearsInCAMaster) {
      this.userAppearsInCAMaster = userAppearsInCAMaster;
   }

   public String getLastTechnicalApproverRole() {
      return this.lastTechnicalApproverRole;
   }

   public void setLastTechnicalApproverRole(String lastTechnicalApproverRole) {
      this.lastTechnicalApproverRole = lastTechnicalApproverRole;
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

   public String getHubId() {
      return this.HubId;
   }

   public void setHubId(String hubId) {
      this.HubId = hubId;
   }

   public String getZoneId() {
      return this.ZoneId;
   }

   public void setZoneId(String zoneId) {
      this.ZoneId = zoneId;
   }

   public String getProductId() {
      return this.ProductId;
   }

   public void setProductId(String productId) {
      this.ProductId = productId;
   }

   public String getClaimReferenceNumber() {
      return this.ClaimReferenceNumber;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.ClaimReferenceNumber = claimReferenceNumber;
   }

   public String getTLCount() {
      return this.TLCount;
   }

   public void setTLCount(String TLcount) {
      this.TLCount = TLcount;
   }
}
