package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetProcessInstanceFromIDResponse implements Serializable {
   private ProcessInstance getProcessInstanceFromIDReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetProcessInstanceFromIDResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceFromIDResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getProcessInstanceFromIDReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceFromIDReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "ProcessInstance"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetProcessInstanceFromIDResponse() {
   }

   public GetProcessInstanceFromIDResponse(ProcessInstance getProcessInstanceFromIDReturn) {
      this.getProcessInstanceFromIDReturn = getProcessInstanceFromIDReturn;
   }

   public ProcessInstance getGetProcessInstanceFromIDReturn() {
      return this.getProcessInstanceFromIDReturn;
   }

   public void setGetProcessInstanceFromIDReturn(ProcessInstance getProcessInstanceFromIDReturn) {
      this.getProcessInstanceFromIDReturn = getProcessInstanceFromIDReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetProcessInstanceFromIDResponse)) {
         return false;
      } else {
         GetProcessInstanceFromIDResponse other = (GetProcessInstanceFromIDResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getProcessInstanceFromIDReturn == null && other.getGetProcessInstanceFromIDReturn() == null || this.getProcessInstanceFromIDReturn != null && this.getProcessInstanceFromIDReturn.equals(other.getGetProcessInstanceFromIDReturn());
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
         if (this.getGetProcessInstanceFromIDReturn() != null) {
            _hashCode += this.getGetProcessInstanceFromIDReturn().hashCode();
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
