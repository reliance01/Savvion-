package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetStatusResponse implements Serializable {
   private String getStatusReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetStatusResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getStatusResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getStatusReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getStatusReturn"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetStatusResponse() {
   }

   public GetStatusResponse(String getStatusReturn) {
      this.getStatusReturn = getStatusReturn;
   }

   public String getGetStatusReturn() {
      return this.getStatusReturn;
   }

   public void setGetStatusReturn(String getStatusReturn) {
      this.getStatusReturn = getStatusReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetStatusResponse)) {
         return false;
      } else {
         GetStatusResponse other = (GetStatusResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getStatusReturn == null && other.getGetStatusReturn() == null || this.getStatusReturn != null && this.getStatusReturn.equals(other.getGetStatusReturn());
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
         if (this.getGetStatusReturn() != null) {
            _hashCode += this.getGetStatusReturn().hashCode();
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
}
