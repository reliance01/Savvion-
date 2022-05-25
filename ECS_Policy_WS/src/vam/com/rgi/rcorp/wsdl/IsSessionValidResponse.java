package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class IsSessionValidResponse implements Serializable {
   private boolean isSessionValidReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(IsSessionValidResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">isSessionValidResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("isSessionValidReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "isSessionValidReturn"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public IsSessionValidResponse() {
   }

   public IsSessionValidResponse(boolean isSessionValidReturn) {
      this.isSessionValidReturn = isSessionValidReturn;
   }

   public boolean isIsSessionValidReturn() {
      return this.isSessionValidReturn;
   }

   public void setIsSessionValidReturn(boolean isSessionValidReturn) {
      this.isSessionValidReturn = isSessionValidReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof IsSessionValidResponse)) {
         return false;
      } else {
         IsSessionValidResponse other = (IsSessionValidResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.isSessionValidReturn == other.isIsSessionValidReturn();
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
         int _hashCode = _hashCode + (this.isIsSessionValidReturn() ? Boolean.TRUE : Boolean.FALSE).hashCode();
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
}
