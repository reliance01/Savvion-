package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetWorkStepInstanceResponse implements Serializable {
   private WorkStepInstance getWorkStepInstanceReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetWorkStepInstanceResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getWorkStepInstanceReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkStepInstance"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetWorkStepInstanceResponse() {
   }

   public GetWorkStepInstanceResponse(WorkStepInstance getWorkStepInstanceReturn) {
      this.getWorkStepInstanceReturn = getWorkStepInstanceReturn;
   }

   public WorkStepInstance getGetWorkStepInstanceReturn() {
      return this.getWorkStepInstanceReturn;
   }

   public void setGetWorkStepInstanceReturn(WorkStepInstance getWorkStepInstanceReturn) {
      this.getWorkStepInstanceReturn = getWorkStepInstanceReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetWorkStepInstanceResponse)) {
         return false;
      } else {
         GetWorkStepInstanceResponse other = (GetWorkStepInstanceResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getWorkStepInstanceReturn == null && other.getGetWorkStepInstanceReturn() == null || this.getWorkStepInstanceReturn != null && this.getWorkStepInstanceReturn.equals(other.getGetWorkStepInstanceReturn());
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
         if (this.getGetWorkStepInstanceReturn() != null) {
            _hashCode += this.getGetWorkStepInstanceReturn().hashCode();
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
