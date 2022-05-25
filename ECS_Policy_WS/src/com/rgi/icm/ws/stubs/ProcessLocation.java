package com.rgi.icm.ws.stubs;

import java.io.Serializable;

public class ProcessLocation implements Serializable {
   private String pkProposalId;
   private String processedAt;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;

   public ProcessLocation() {
   }

   public ProcessLocation(String pkProposalId, String processedAt) {
      this.pkProposalId = pkProposalId;
      this.processedAt = processedAt;
   }

   public String getPkProposalId() {
      return this.pkProposalId;
   }

   public void setPkProposalId(String pkProposalId) {
      this.pkProposalId = pkProposalId;
   }

   public String getProcessedAt() {
      return this.processedAt;
   }

   public void setProcessedAt(String processedAt) {
      this.processedAt = processedAt;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ProcessLocation)) {
         return false;
      } else {
         ProcessLocation other = (ProcessLocation)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.pkProposalId == null && other.getPkProposalId() == null || this.pkProposalId != null && this.pkProposalId.equals(other.getPkProposalId())) && (this.processedAt == null && other.getProcessedAt() == null || this.processedAt != null && this.processedAt.equals(other.getProcessedAt()));
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
         if (this.getPkProposalId() != null) {
            _hashCode += this.getPkProposalId().hashCode();
         }

         if (this.getProcessedAt() != null) {
            _hashCode += this.getProcessedAt().hashCode();
         }

         this.__hashCodeCalc = false;
         return _hashCode;
      }
   }
}
