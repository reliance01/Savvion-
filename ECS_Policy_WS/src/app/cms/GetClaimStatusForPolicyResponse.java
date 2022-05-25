package app.cms;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetClaimStatusForPolicyResponse implements Serializable {
   private StatusResponce getClaimStatusForPolicyResult;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetClaimStatusForPolicyResponse.class, true);

   public GetClaimStatusForPolicyResponse() {
   }

   public GetClaimStatusForPolicyResponse(StatusResponce getClaimStatusForPolicyResult) {
      this.getClaimStatusForPolicyResult = getClaimStatusForPolicyResult;
   }

   public StatusResponce getGetClaimStatusForPolicyResult() {
      return this.getClaimStatusForPolicyResult;
   }

   public void setGetClaimStatusForPolicyResult(StatusResponce getClaimStatusForPolicyResult) {
      this.getClaimStatusForPolicyResult = getClaimStatusForPolicyResult;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetClaimStatusForPolicyResponse)) {
         return false;
      } else {
         GetClaimStatusForPolicyResponse other = (GetClaimStatusForPolicyResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getClaimStatusForPolicyResult == null && other.getGetClaimStatusForPolicyResult() == null || this.getClaimStatusForPolicyResult != null && this.getClaimStatusForPolicyResult.equals(other.getGetClaimStatusForPolicyResult());
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
         if (this.getGetClaimStatusForPolicyResult() != null) {
            _hashCode += this.getGetClaimStatusForPolicyResult().hashCode();
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
      typeDesc.setXmlType(new QName("http://tempuri.org/", ">getClaimStatusForPolicyResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getClaimStatusForPolicyResult");
      elemField.setXmlName(new QName("http://tempuri.org/", "getClaimStatusForPolicyResult"));
      elemField.setXmlType(new QName("http://tempuri.org/", "StatusResponce"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }
}
