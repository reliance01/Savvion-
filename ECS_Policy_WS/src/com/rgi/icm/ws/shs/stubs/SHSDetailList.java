package com.rgi.icm.ws.shs.stubs;

import java.io.Serializable;

public class SHSDetailList implements Serializable {
   private String proposalId;
   private String transactionID;
   private String businessType;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;

   public SHSDetailList() {
   }

   public SHSDetailList(String proposalId, String transactionID, String businessType) {
      this.proposalId = proposalId;
      this.transactionID = transactionID;
      this.businessType = businessType;
   }

   public String getProposalId() {
      return this.proposalId;
   }

   public void setProposalId(String proposalId) {
      this.proposalId = proposalId;
   }

   public String getTransactionID() {
      return this.transactionID;
   }

   public void setTransactionID(String transactionID) {
      this.transactionID = transactionID;
   }

   public String getBusinessType() {
      return this.businessType;
   }

   public void setBusinessType(String businessType) {
      this.businessType = businessType;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof SHSDetailList)) {
         return false;
      } else {
         SHSDetailList other = (SHSDetailList)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.proposalId == null && other.getProposalId() == null || this.proposalId != null && this.proposalId.equals(other.getProposalId())) && (this.transactionID == null && other.getTransactionID() == null || this.transactionID != null && this.transactionID.equals(other.getTransactionID())) && (this.businessType == null && other.getBusinessType() == null || this.businessType != null && this.businessType.equals(other.getBusinessType()));
            this.__equalsCalc = null;
            return _equals;
         }
      }
   }

   public synchronized int hashCode() {
      if (this.__hashCodeCalc) {
         return 0;
      } else {
         this.__hashCodeCalc = true;
         int _hashCode = 1;
         if (this.getProposalId() != null) {
            _hashCode += this.getProposalId().hashCode();
         }

         if (this.getTransactionID() != null) {
            _hashCode += this.getTransactionID().hashCode();
         }

         if (this.getBusinessType() != null) {
            _hashCode += this.getBusinessType().hashCode();
         }

         this.__hashCodeCalc = false;
         return _hashCode;
      }
   }
}
