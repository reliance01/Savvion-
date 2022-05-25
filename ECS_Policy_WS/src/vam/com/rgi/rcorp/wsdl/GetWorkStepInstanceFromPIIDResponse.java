package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetWorkStepInstanceFromPIIDResponse implements Serializable {
   private WorkStepInstance getWorkStepInstanceFromPIIDReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetWorkStepInstanceFromPIIDResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceFromPIIDResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getWorkStepInstanceFromPIIDReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceFromPIIDReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkStepInstance"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetWorkStepInstanceFromPIIDResponse() {
   }

   public GetWorkStepInstanceFromPIIDResponse(WorkStepInstance getWorkStepInstanceFromPIIDReturn) {
      this.getWorkStepInstanceFromPIIDReturn = getWorkStepInstanceFromPIIDReturn;
   }

   public WorkStepInstance getGetWorkStepInstanceFromPIIDReturn() {
      return this.getWorkStepInstanceFromPIIDReturn;
   }

   public void setGetWorkStepInstanceFromPIIDReturn(WorkStepInstance getWorkStepInstanceFromPIIDReturn) {
      this.getWorkStepInstanceFromPIIDReturn = getWorkStepInstanceFromPIIDReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetWorkStepInstanceFromPIIDResponse)) {
         return false;
      } else {
         GetWorkStepInstanceFromPIIDResponse other = (GetWorkStepInstanceFromPIIDResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getWorkStepInstanceFromPIIDReturn == null && other.getGetWorkStepInstanceFromPIIDReturn() == null || this.getWorkStepInstanceFromPIIDReturn != null && this.getWorkStepInstanceFromPIIDReturn.equals(other.getGetWorkStepInstanceFromPIIDReturn());
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
         if (this.getGetWorkStepInstanceFromPIIDReturn() != null) {
            _hashCode += this.getGetWorkStepInstanceFromPIIDReturn().hashCode();
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
