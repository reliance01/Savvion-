package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetWorkItemResponse implements Serializable {
   private WorkItem getWorkItemReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetWorkItemResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getWorkItemResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getWorkItemReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getWorkItemReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetWorkItemResponse() {
   }

   public GetWorkItemResponse(WorkItem getWorkItemReturn) {
      this.getWorkItemReturn = getWorkItemReturn;
   }

   public WorkItem getGetWorkItemReturn() {
      return this.getWorkItemReturn;
   }

   public void setGetWorkItemReturn(WorkItem getWorkItemReturn) {
      this.getWorkItemReturn = getWorkItemReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetWorkItemResponse)) {
         return false;
      } else {
         GetWorkItemResponse other = (GetWorkItemResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getWorkItemReturn == null && other.getGetWorkItemReturn() == null || this.getWorkItemReturn != null && this.getWorkItemReturn.equals(other.getGetWorkItemReturn());
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
         if (this.getGetWorkItemReturn() != null) {
            _hashCode += this.getGetWorkItemReturn().hashCode();
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
