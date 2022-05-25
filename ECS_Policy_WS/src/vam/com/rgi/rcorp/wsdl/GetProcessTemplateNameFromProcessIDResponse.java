package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetProcessTemplateNameFromProcessIDResponse implements Serializable {
   private String getProcessTemplateNameFromProcessIDReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetProcessTemplateNameFromProcessIDResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateNameFromProcessIDResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getProcessTemplateNameFromProcessIDReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateNameFromProcessIDReturn"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetProcessTemplateNameFromProcessIDResponse() {
   }

   public GetProcessTemplateNameFromProcessIDResponse(String getProcessTemplateNameFromProcessIDReturn) {
      this.getProcessTemplateNameFromProcessIDReturn = getProcessTemplateNameFromProcessIDReturn;
   }

   public String getGetProcessTemplateNameFromProcessIDReturn() {
      return this.getProcessTemplateNameFromProcessIDReturn;
   }

   public void setGetProcessTemplateNameFromProcessIDReturn(String getProcessTemplateNameFromProcessIDReturn) {
      this.getProcessTemplateNameFromProcessIDReturn = getProcessTemplateNameFromProcessIDReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetProcessTemplateNameFromProcessIDResponse)) {
         return false;
      } else {
         GetProcessTemplateNameFromProcessIDResponse other = (GetProcessTemplateNameFromProcessIDResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getProcessTemplateNameFromProcessIDReturn == null && other.getGetProcessTemplateNameFromProcessIDReturn() == null || this.getProcessTemplateNameFromProcessIDReturn != null && this.getProcessTemplateNameFromProcessIDReturn.equals(other.getGetProcessTemplateNameFromProcessIDReturn());
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
         if (this.getGetProcessTemplateNameFromProcessIDReturn() != null) {
            _hashCode += this.getGetProcessTemplateNameFromProcessIDReturn().hashCode();
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
