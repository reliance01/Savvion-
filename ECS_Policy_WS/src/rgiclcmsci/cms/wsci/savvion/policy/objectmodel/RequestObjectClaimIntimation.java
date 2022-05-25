package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObjectClaimIntimation {
   public String claimReferenceNumber;
   public String riskScoreLimitForOD;
   public String surveyorType;
   public String IsCommon;
   public String IsOrphan;
   public String numberOfCM;
   public String productId;
   public String hubId;
   public String zoneId;
   public String policy_no;
   public String RiskScore;
   public String isPremiumVerificationRequired;
   public String isSpotSurveyorRequired;
   public String isPolicyDetailsAvailable;
   public String isExternalSurveyorRequired;
   public String externalSurveyor;
   public String spotSurveyor;
   public String primaryNatureOfLoss;
   private String CurrentCMId;
   private String NewHubId;
   private String CurrentHubId;
   private String userId;
   private String workItemName;
   private String processInstanceName;

   public String getIsPolicyDetailsAvailable() {
      return this.isPolicyDetailsAvailable;
   }

   public void setIsPolicyDetailsAvailable(String isPolicyDetailsAvailable) {
      this.isPolicyDetailsAvailable = isPolicyDetailsAvailable;
   }

   public String getPrimaryNatureOfLoss() {
      return this.primaryNatureOfLoss;
   }

   public void setPrimaryNatureOfLoss(String primaryNatureOfLoss) {
      this.primaryNatureOfLoss = primaryNatureOfLoss;
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

   public String getClaimReferenceNumber() {
      return this.claimReferenceNumber;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.claimReferenceNumber = claimReferenceNumber;
   }

   public String getriskScoreLimitForOD() {
      return this.riskScoreLimitForOD;
   }

   public void setriskScoreLimitForOD(String riskScoreLimitForOD) {
      this.riskScoreLimitForOD = riskScoreLimitForOD;
   }

   public String getSurveyorType() {
      return this.surveyorType;
   }

   public String getCurrentCMId() {
      return this.CurrentCMId;
   }

   public void setCurrentCMId(String currentCMId) {
      this.CurrentCMId = currentCMId;
   }

   public String getNewHubId() {
      return this.NewHubId;
   }

   public void setNewHubId(String newHubId) {
      this.NewHubId = newHubId;
   }

   public String getCurrentHubId() {
      return this.CurrentHubId;
   }

   public void setCurrentHubId(String currentHubId) {
      this.CurrentHubId = currentHubId;
   }

   public void setSurveyorType(String surveyorType) {
      this.surveyorType = surveyorType;
   }

   public String getIsCommon() {
      return this.IsCommon;
   }

   public void setIsCommon(String isCommon) {
      this.IsCommon = isCommon;
   }

   public String getIsOrphan() {
      return this.IsOrphan;
   }

   public void setIsOrphan(String isOrphan) {
      this.IsOrphan = isOrphan;
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

   public String getPolicy_no() {
      return this.policy_no;
   }

   public void setPolicy_no(String policyNo) {
      this.policy_no = policyNo;
   }

   public String getIsPremiumVerificationRequired() {
      return this.isPremiumVerificationRequired;
   }

   public void setIsPremiumVerificationRequired(String isPremiumVerificationRequired) {
      this.isPremiumVerificationRequired = isPremiumVerificationRequired;
   }

   public String getIsSpotSurveyorRequired() {
      return this.isSpotSurveyorRequired;
   }

   public void setIsSpotSurveyorRequired(String isSpotSurveyorRequired) {
      this.isSpotSurveyorRequired = isSpotSurveyorRequired;
   }

   public String getNumberOfCM() {
      return this.numberOfCM;
   }

   public void setNumberOfCM(String numberOfCM) {
      this.numberOfCM = numberOfCM;
   }

   public String getRiskScore() {
      return this.RiskScore;
   }

   public void setRiskScore(String riskScore) {
      this.RiskScore = riskScore;
   }

   public String getIsExternalSurveyorRequired() {
      return this.isExternalSurveyorRequired;
   }

   public void setIsExternalSurveyorRequired(String isExternalSurveyorRequired) {
      this.isExternalSurveyorRequired = isExternalSurveyorRequired;
   }

   public String getExternalSurveyor() {
      return this.externalSurveyor;
   }

   public void setExternalSurveyor(String externalSurveyor) {
      this.externalSurveyor = externalSurveyor;
   }

   public String getSpotSurveyor() {
      return this.spotSurveyor;
   }

   public void setSpotSurveyor(String spotSurveyor) {
      this.spotSurveyor = spotSurveyor;
   }
}
