package rgicl.ecsehc.savvion.policy.objectmodel;

public class WorkItemObject {
   static final long serialVersionUID = -6727544753963912195L;
   private String name = null;
   private String pId = null;
   private String piName = null;
   private String workStepName = null;
   private String performer = null;
   private String queue;
   private String caseStatus;
   private String type = null;
   private String user_name = null;
   private String policyNumber = null;
   private String policyStatus = null;
   private String transactionType;
   private String[] PolicyCheckerUser;
   private String[] PolicyMakerUser;
   private String ProposalNumber;
   private String PolicyName;
   private String isPolicyCheckerAccepted;
   private String transactionId;
   private String isServiceEndorsement;
   private String StartDate;
   private String EndDate;
   private String Branch;
   private String City;
   private String Ageing;
   private String[] PolicyCheckerRemark;
   private String[] PolicyMakerRemark;
   private String pmuser;
   private String pchuser;
   private String[] EnrollmentMakerRemarks;
   private String[] EnrollmentCheckerRemarks;
   private String[] EndorsementMakerRemarks;
   private String[] EndorsementCheckerRemarks;
   private String[] PolicyConfiguratorRemarks;
   private String IsEndorsementCheckerAccepted;

   public String[] getPolicyConfiguratorRemarks() {
      return this.PolicyConfiguratorRemarks;
   }

   public void setPolicyConfiguratorRemarks(String[] policyConfiguratorRemarks) {
      this.PolicyConfiguratorRemarks = policyConfiguratorRemarks;
   }

   public String getIsEndorsementCheckerAccepted() {
      return this.IsEndorsementCheckerAccepted;
   }

   public void setIsEndorsementCheckerAccepted(String isEndorsementCheckerAccepted) {
      this.IsEndorsementCheckerAccepted = isEndorsementCheckerAccepted;
   }

   public String[] getEnrollmentMakerRemarks() {
      return this.EnrollmentMakerRemarks;
   }

   public void setEnrollmentMakerRemarks(String[] enrollementMakerRemarks) {
      this.EnrollmentMakerRemarks = enrollementMakerRemarks;
   }

   public String[] getEnrollmentCheckerRemarks() {
      return this.EnrollmentCheckerRemarks;
   }

   public void setEnrollmentCheckerRemarks(String[] enrollementCheckerRemarks) {
      this.EnrollmentCheckerRemarks = enrollementCheckerRemarks;
   }

   public String[] getEndorsementMakerRemarks() {
      return this.EndorsementMakerRemarks;
   }

   public void setEndorsementMakerRemarks(String[] endorsementMakerRemarks) {
      this.EndorsementMakerRemarks = endorsementMakerRemarks;
   }

   public String[] getEndorsementCheckerRemarks() {
      return this.EndorsementCheckerRemarks;
   }

   public void setEndorsementCheckerRemarks(String[] endorsementCheckerRemarks) {
      this.EndorsementCheckerRemarks = endorsementCheckerRemarks;
   }

   public String getTransactionType() {
      return this.transactionType;
   }

   public void setTransactionType(String transactionType) {
      this.transactionType = transactionType;
   }

   public String getPmuser() {
      return this.pmuser;
   }

   public void setPmuser(String pmuser) {
      this.pmuser = pmuser;
   }

   public String getPchuser() {
      return this.pchuser;
   }

   public void setPchuser(String pchuser) {
      this.pchuser = pchuser;
   }

   public String[] getPolicyCheckerUser() {
      return this.PolicyCheckerUser;
   }

   public void setPolicyCheckerUser(String[] policyCheckerUser) {
      this.PolicyCheckerUser = policyCheckerUser;
   }

   public String[] getPolicyMakerUser() {
      return this.PolicyMakerUser;
   }

   public void setPolicyMakerUser(String[] policyMakerUser) {
      this.PolicyMakerUser = policyMakerUser;
   }

   public String getIsPolicyCheckerAccepted() {
      return this.isPolicyCheckerAccepted;
   }

   public void setIsPolicyCheckerAccepted(String isPolicyCheckerAccepted) {
      this.isPolicyCheckerAccepted = isPolicyCheckerAccepted;
   }

   public String[] getPolicyCheckerRemark() {
      return this.PolicyCheckerRemark;
   }

   public void setPolicyCheckerRemark(String[] policyCheckerRemark) {
      this.PolicyCheckerRemark = policyCheckerRemark;
   }

   public String[] getPolicyMakerRemark() {
      return this.PolicyMakerRemark;
   }

   public void setPolicyMakerRemark(String[] policyMakerRemark) {
      this.PolicyMakerRemark = policyMakerRemark;
   }

   public String getProposalNumber() {
      return this.ProposalNumber;
   }

   public void setProposalNumber(String proposalNumber) {
      this.ProposalNumber = proposalNumber;
   }

   public String getPolicyName() {
      return this.PolicyName;
   }

   public void setPolicyName(String policyName) {
      this.PolicyName = policyName;
   }

   public String getpId() {
      return this.pId;
   }

   public void setpId(String pId) {
      this.pId = pId;
   }

   public String getStartDate() {
      return this.StartDate;
   }

   public void setStartDate(String startDate) {
      this.StartDate = startDate;
   }

   public String getEndDate() {
      return this.EndDate;
   }

   public void setEndDate(String endDate) {
      this.EndDate = endDate;
   }

   public String getBranch() {
      return this.Branch;
   }

   public void setBranch(String branch) {
      this.Branch = branch;
   }

   public String getCity() {
      return this.City;
   }

   public void setCity(String city) {
      this.City = city;
   }

   public String getAgeing() {
      return this.Ageing;
   }

   public void setAgeing(String ageing) {
      this.Ageing = ageing;
   }

   public String getUser_name() {
      return this.user_name;
   }

   public void setUser_name(String userName) {
      this.user_name = userName;
   }

   public String getPolicyNumber() {
      return this.policyNumber;
   }

   public void setPolicyNumber(String policyNumber) {
      this.policyNumber = policyNumber;
   }

   public String getPolicyStatus() {
      return this.policyStatus;
   }

   public void setPolicyStatus(String policyStatus) {
      this.policyStatus = policyStatus;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPerformer() {
      return this.performer;
   }

   public void setPerformer(String performer) {
      this.performer = performer;
   }

   public String getPId() {
      return this.pId;
   }

   public void setPId(String id) {
      this.pId = id;
   }

   public String getPiName() {
      return this.piName;
   }

   public void setPiName(String piName) {
      this.piName = piName;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getWorkStepName() {
      return this.workStepName;
   }

   public void setWorkStepName(String workStepName) {
      this.workStepName = workStepName;
   }

   public String getQueue() {
      return this.queue;
   }

   public void setQueue(String queue) {
      this.queue = queue;
   }

   public String getCaseStatus() {
      return this.caseStatus;
   }

   public void setCaseStatus(String caseStatus) {
      this.caseStatus = caseStatus;
   }

   public String getTransactionId() {
      return this.transactionId;
   }

   public void setTransactionId(String transactionId) {
      this.transactionId = transactionId;
   }

   public String getIsServiceEndorsement() {
      return this.isServiceEndorsement;
   }

   public void setIsServiceEndorsement(String isServiceEndorsement) {
      this.isServiceEndorsement = isServiceEndorsement;
   }

   public String toString() {
      return "WORKITEMNAME:-" + this.name + " && PINAME:-" + this.piName + " && WORKSTEPNAME:-" + this.workStepName + " && QUEUE:-" + this.queue + " && PERFORMER:-" + this.performer + " && CASESTATUS:-" + this.caseStatus;
   }
}
