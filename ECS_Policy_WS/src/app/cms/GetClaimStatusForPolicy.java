package app.cms;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetClaimStatusForPolicy implements Serializable {
   private String policyNo;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetClaimStatusForPolicy.class, true);

   public GetClaimStatusForPolicy() {
   }

   public GetClaimStatusForPolicy(String policyNo) {
      this.policyNo = policyNo;
   }

   public String getPolicyNo() {
      return this.policyNo;
   }

   public void setPolicyNo(String policyNo) {
      this.policyNo = policyNo;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetClaimStatusForPolicy)) {
         return false;
      } else {
         GetClaimStatusForPolicy other = (GetClaimStatusForPolicy)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.policyNo == null && other.getPolicyNo() == null || this.policyNo != null && this.policyNo.equals(other.getPolicyNo());
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
         if (this.getPolicyNo() != null) {
            _hashCode += this.getPolicyNo().hashCode();
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
      typeDesc.setXmlType(new QName("http://tempuri.org/", ">getClaimStatusForPolicy"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("policyNo");
      elemField.setXmlName(new QName("http://tempuri.org/", "PolicyNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }
}
