package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObjectCorrectionPayeeName {
   public String claimReferenceNumber;
   public String hubId;
   public String ZoneId;
   public String productId;
   public String initiatorRole;
   public String initiatorId;

   public String getZoneId() {
      return this.ZoneId;
   }

   public void setZoneId(String zoneId) {
      this.ZoneId = zoneId;
   }

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
}
