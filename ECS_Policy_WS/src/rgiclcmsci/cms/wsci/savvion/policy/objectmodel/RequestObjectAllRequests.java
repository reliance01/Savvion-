package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObjectAllRequests {
   public String claimReferenceNumber;
   public String RequestTypeID;
   public String reissueRequestId;
   public String physicalCheck;

   public String getReissueRequestId() {
      return this.reissueRequestId;
   }

   public void setReissueRequestId(String reissueRequestId) {
      this.reissueRequestId = reissueRequestId;
   }

   public String getPhysicalCheck() {
      return this.physicalCheck;
   }

   public void setPhysicalCheck(String physicalCheck) {
      this.physicalCheck = physicalCheck;
   }

   public String getClaimReferenceNumber() {
      return this.claimReferenceNumber;
   }

   public void setClaimReferenceNumber(String claimReferenceNumber) {
      this.claimReferenceNumber = claimReferenceNumber;
   }

   public String getRequestTypeID() {
      return this.RequestTypeID;
   }

   public void setRequestTypeID(String requestTypeID) {
      this.RequestTypeID = requestTypeID;
   }
}
