package app.cms;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class IsClaimExistResponse implements Serializable {
   private String isClaimExistResult;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(IsClaimExistResponse.class, true);

   public IsClaimExistResponse() {
   }

   public IsClaimExistResponse(String isClaimExistResult) {
      this.isClaimExistResult = isClaimExistResult;
   }

   public String getIsClaimExistResult() {
      return this.isClaimExistResult;
   }

   public void setIsClaimExistResult(String isClaimExistResult) {
      this.isClaimExistResult = isClaimExistResult;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof IsClaimExistResponse)) {
         return false;
      } else {
         IsClaimExistResponse other = (IsClaimExistResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.isClaimExistResult == null && other.getIsClaimExistResult() == null || this.isClaimExistResult != null && this.isClaimExistResult.equals(other.getIsClaimExistResult());
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
         if (this.getIsClaimExistResult() != null) {
            _hashCode += this.getIsClaimExistResult().hashCode();
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
      typeDesc.setXmlType(new QName("http://tempuri.org/", ">isClaimExistResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("isClaimExistResult");
      elemField.setXmlName(new QName("http://tempuri.org/", "isClaimExistResult"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }
}
