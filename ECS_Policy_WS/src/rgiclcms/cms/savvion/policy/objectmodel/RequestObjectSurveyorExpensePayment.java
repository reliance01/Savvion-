package rgiclcms.cms.savvion.policy.objectmodel;

public class RequestObjectSurveyorExpensePayment {
   public String claimReferenceNumber;
   public String initiatorRole;
   public String initiatorId;
   public Long Actualamount;
   public Long CMApprovelimit;
   public Long TLApprovelimit;

   public String getClaimReferenceNumber() {
      return this.claimReferenceNumber;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.claimReferenceNumber = claimReferenceNumber;
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

   public Long getActualamount() {
      return this.Actualamount;
   }

   public void setActualamount(Long actualamount) {
      this.Actualamount = actualamount;
   }

   public Long getCMApprovelimit() {
      return this.CMApprovelimit;
   }

   public void setCMApprovelimit(Long cMApprovelimit) {
      this.CMApprovelimit = cMApprovelimit;
   }

   public Long getTLApprovelimit() {
      return this.TLApprovelimit;
   }

   public void setTLApprovelimit(Long tLApprovelimit) {
      this.TLApprovelimit = tLApprovelimit;
   }
}
