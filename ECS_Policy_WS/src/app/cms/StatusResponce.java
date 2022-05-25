package app.cms;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class StatusResponce implements Serializable {
   private String isActiveClaim;
   private String isActiveTheftClaim;
   private String isClaimEverPaid;
   private String isTheftEverClaimPaid;
   private String isTotalLoss;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(StatusResponce.class, true);

   public StatusResponce() {
   }

   public StatusResponce(String isActiveClaim, String isActiveTheftClaim, String isClaimEverPaid, String isTheftEverClaimPaid, String isTotalLoss) {
      this.isActiveClaim = isActiveClaim;
      this.isActiveTheftClaim = isActiveTheftClaim;
      this.isClaimEverPaid = isClaimEverPaid;
      this.isTheftEverClaimPaid = isTheftEverClaimPaid;
      this.isTotalLoss = isTotalLoss;
   }

   public String getIsActiveClaim() {
      return this.isActiveClaim;
   }

   public void setIsActiveClaim(String isActiveClaim) {
      this.isActiveClaim = isActiveClaim;
   }

   public String getIsActiveTheftClaim() {
      return this.isActiveTheftClaim;
   }

   public void setIsActiveTheftClaim(String isActiveTheftClaim) {
      this.isActiveTheftClaim = isActiveTheftClaim;
   }

   public String getIsClaimEverPaid() {
      return this.isClaimEverPaid;
   }

   public void setIsClaimEverPaid(String isClaimEverPaid) {
      this.isClaimEverPaid = isClaimEverPaid;
   }

   public String getIsTheftEverClaimPaid() {
      return this.isTheftEverClaimPaid;
   }

   public void setIsTheftEverClaimPaid(String isTheftEverClaimPaid) {
      this.isTheftEverClaimPaid = isTheftEverClaimPaid;
   }

   public String getIsTotalLoss() {
      return this.isTotalLoss;
   }

   public void setIsTotalLoss(String isTotalLoss) {
      this.isTotalLoss = isTotalLoss;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof StatusResponce)) {
         return false;
      } else {
         StatusResponce other = (StatusResponce)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.isActiveClaim == null && other.getIsActiveClaim() == null || this.isActiveClaim != null && this.isActiveClaim.equals(other.getIsActiveClaim())) && (this.isActiveTheftClaim == null && other.getIsActiveTheftClaim() == null || this.isActiveTheftClaim != null && this.isActiveTheftClaim.equals(other.getIsActiveTheftClaim())) && (this.isClaimEverPaid == null && other.getIsClaimEverPaid() == null || this.isClaimEverPaid != null && this.isClaimEverPaid.equals(other.getIsClaimEverPaid())) && (this.isTheftEverClaimPaid == null && other.getIsTheftEverClaimPaid() == null || this.isTheftEverClaimPaid != null && this.isTheftEverClaimPaid.equals(other.getIsTheftEverClaimPaid())) && (this.isTotalLoss == null && other.getIsTotalLoss() == null || this.isTotalLoss != null && this.isTotalLoss.equals(other.getIsTotalLoss()));
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
         if (this.getIsActiveClaim() != null) {
            _hashCode += this.getIsActiveClaim().hashCode();
         }

         if (this.getIsActiveTheftClaim() != null) {
            _hashCode += this.getIsActiveTheftClaim().hashCode();
         }

         if (this.getIsClaimEverPaid() != null) {
            _hashCode += this.getIsClaimEverPaid().hashCode();
         }

         if (this.getIsTheftEverClaimPaid() != null) {
            _hashCode += this.getIsTheftEverClaimPaid().hashCode();
         }

         if (this.getIsTotalLoss() != null) {
            _hashCode += this.getIsTotalLoss().hashCode();
         }

         this.__hashCodeCalc = false;
         return _hashCode;
      }
   }

   public static TypeDesc getTypeDesc() {
      return typeDesc;
   }

   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
      return new BeanSerializer(_javaType, _xmlType, typeDesc);
   }

   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
      return new BeanDeserializer(_javaType, _xmlType, typeDesc);
   }

   static {
      typeDesc.setXmlType(new QName("http://tempuri.org/", "StatusResponce"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("isActiveClaim");
      elemField.setXmlName(new QName("http://tempuri.org/", "IsActiveClaim"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("isActiveTheftClaim");
      elemField.setXmlName(new QName("http://tempuri.org/", "IsActiveTheftClaim"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("isClaimEverPaid");
      elemField.setXmlName(new QName("http://tempuri.org/", "IsClaimEverPaid"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("isTheftEverClaimPaid");
      elemField.setXmlName(new QName("http://tempuri.org/", "IsTheftEverClaimPaid"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("isTotalLoss");
      elemField.setXmlName(new QName("http://tempuri.org/", "IsTotalLoss"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }
}
