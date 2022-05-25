package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class CreateProcessInstanceResponse implements Serializable {
   private ProcessInstance createProcessInstanceReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(CreateProcessInstanceResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">createProcessInstanceResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("createProcessInstanceReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "createProcessInstanceReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "ProcessInstance"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public CreateProcessInstanceResponse() {
   }

   public CreateProcessInstanceResponse(ProcessInstance createProcessInstanceReturn) {
      this.createProcessInstanceReturn = createProcessInstanceReturn;
   }

   public ProcessInstance getCreateProcessInstanceReturn() {
      return this.createProcessInstanceReturn;
   }

   public void setCreateProcessInstanceReturn(ProcessInstance createProcessInstanceReturn) {
      this.createProcessInstanceReturn = createProcessInstanceReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof CreateProcessInstanceResponse)) {
         return false;
      } else {
         CreateProcessInstanceResponse other = (CreateProcessInstanceResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.createProcessInstanceReturn == null && other.getCreateProcessInstanceReturn() == null || this.createProcessInstanceReturn != null && this.createProcessInstanceReturn.equals(other.getCreateProcessInstanceReturn());
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
         if (this.getCreateProcessInstanceReturn() != null) {
            _hashCode += this.getCreateProcessInstanceReturn().hashCode();
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
