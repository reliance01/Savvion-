package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetProcessInstanceResponse implements Serializable {
   private ProcessInstance getProcessInstanceReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetProcessInstanceResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getProcessInstanceReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "ProcessInstance"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetProcessInstanceResponse() {
   }

   public GetProcessInstanceResponse(ProcessInstance getProcessInstanceReturn) {
      this.getProcessInstanceReturn = getProcessInstanceReturn;
   }

   public ProcessInstance getGetProcessInstanceReturn() {
      return this.getProcessInstanceReturn;
   }

   public void setGetProcessInstanceReturn(ProcessInstance getProcessInstanceReturn) {
      this.getProcessInstanceReturn = getProcessInstanceReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetProcessInstanceResponse)) {
         return false;
      } else {
         GetProcessInstanceResponse other = (GetProcessInstanceResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getProcessInstanceReturn == null && other.getGetProcessInstanceReturn() == null || this.getProcessInstanceReturn != null && this.getProcessInstanceReturn.equals(other.getGetProcessInstanceReturn());
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
         if (this.getGetProcessInstanceReturn() != null) {
            _hashCode += this.getGetProcessInstanceReturn().hashCode();
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
