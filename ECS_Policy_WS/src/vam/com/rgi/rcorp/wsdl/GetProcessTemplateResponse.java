package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetProcessTemplateResponse implements Serializable {
   private ProcessTemplate getProcessTemplateReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetProcessTemplateResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getProcessTemplateReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "ProcessTemplate"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetProcessTemplateResponse() {
   }

   public GetProcessTemplateResponse(ProcessTemplate getProcessTemplateReturn) {
      this.getProcessTemplateReturn = getProcessTemplateReturn;
   }

   public ProcessTemplate getGetProcessTemplateReturn() {
      return this.getProcessTemplateReturn;
   }

   public void setGetProcessTemplateReturn(ProcessTemplate getProcessTemplateReturn) {
      this.getProcessTemplateReturn = getProcessTemplateReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetProcessTemplateResponse)) {
         return false;
      } else {
         GetProcessTemplateResponse other = (GetProcessTemplateResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getProcessTemplateReturn == null && other.getGetProcessTemplateReturn() == null || this.getProcessTemplateReturn != null && this.getProcessTemplateReturn.equals(other.getGetProcessTemplateReturn());
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
         if (this.getGetProcessTemplateReturn() != null) {
            _hashCode += this.getGetProcessTemplateReturn().hashCode();
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
