package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObjectOnAccountApprovalPayment {
   public String claimReferenceNumber;
   public String hubId;
   public String zoneId;
   public String productId;
   public String initiatorRole;
   public String initiatorId;
   public String expenseType;
   public String amount;
   public String approverLimit;

   public String getClaimReferenceNumber() {
      return this.claimReferenceNumber;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.claimReferenceNumber = claimReferenceNumber;
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

   public String getProductId() {
      return this.productId;
   }

   public void setProductId(String productId) {
      this.productId = productId;
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

   public String getExpenseType() {
      return this.expenseType;
   }

   public void setExpenseType(String expenseType) {
      this.expenseType = expenseType;
   }

   public String getAmount() {
      return this.amount;
   }

   public void setAmount(String amount) {
      this.amount = amount;
   }

   public String getApproverLimit() {
      return this.approverLimit;
   }

   public void setApproverLimit(String approverLimit) {
      this.approverLimit = approverLimit;
   }
}
