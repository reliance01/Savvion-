package com.rgi.icm.ws.stubs;

import java.io.Serializable;

public class ProposalPolicy implements Serializable {
   private String proposalId;
   private String policyNo;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;

   public ProposalPolicy() {
   }

   public ProposalPolicy(String proposalId, String policyNo) {
      this.proposalId = proposalId;
      this.policyNo = policyNo;
   }

   public String getProposalId() {
      return this.proposalId;
   }

   public void setProposalId(String proposalId) {
      this.proposalId = proposalId;
   }

   public String getPolicyNo() {
      return this.policyNo;
   }

   public void setPolicyNo(String policyNo) {
      this.policyNo = policyNo;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ProposalPolicy)) {
         return false;
      } else {
         ProposalPolicy other = (ProposalPolicy)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.proposalId == null && other.getProposalId() == null || this.proposalId != null && this.proposalId.equals(other.getProposalId())) && (this.policyNo == null && other.getPolicyNo() == null || this.policyNo != null && this.policyNo.equals(other.getPolicyNo()));
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

         if (this.getPolicyNo() != null) {
            _hashCode += this.getPolicyNo().hashCode();
         }

         this.__hashCodeCalc = false;
         return _hashCode;
      }
   }
}
