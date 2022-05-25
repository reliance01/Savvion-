package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetProcessTemplateFromIDResponse implements Serializable {
   private ProcessTemplate getProcessTemplateFromIDReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetProcessTemplateFromIDResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateFromIDResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getProcessTemplateFromIDReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateFromIDReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "ProcessTemplate"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetProcessTemplateFromIDResponse() {
   }

   public GetProcessTemplateFromIDResponse(ProcessTemplate getProcessTemplateFromIDReturn) {
      this.getProcessTemplateFromIDReturn = getProcessTemplateFromIDReturn;
   }

   public ProcessTemplate getGetProcessTemplateFromIDReturn() {
      return this.getProcessTemplateFromIDReturn;
   }

   public void setGetProcessTemplateFromIDReturn(ProcessTemplate getProcessTemplateFromIDReturn) {
      this.getProcessTemplateFromIDReturn = getProcessTemplateFromIDReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetProcessTemplateFromIDResponse)) {
         return false;
      } else {
         GetProcessTemplateFromIDResponse other = (GetProcessTemplateFromIDResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getProcessTemplateFromIDReturn == null && other.getGetProcessTemplateFromIDReturn() == null || this.getProcessTemplateFromIDReturn != null && this.getProcessTemplateFromIDReturn.equals(other.getGetProcessTemplateFromIDReturn());
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
         if (this.getGetProcessTemplateFromIDReturn() != null) {
            _hashCode += this.getGetProcessTemplateFromIDReturn().hashCode();
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
