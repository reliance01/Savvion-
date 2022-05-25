package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetWorkItemFromIDResponse implements Serializable {
   private WorkItem getWorkItemFromIDReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetWorkItemFromIDResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getWorkItemFromIDResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getWorkItemFromIDReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getWorkItemFromIDReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetWorkItemFromIDResponse() {
   }

   public GetWorkItemFromIDResponse(WorkItem getWorkItemFromIDReturn) {
      this.getWorkItemFromIDReturn = getWorkItemFromIDReturn;
   }

   public WorkItem getGetWorkItemFromIDReturn() {
      return this.getWorkItemFromIDReturn;
   }

   public void setGetWorkItemFromIDReturn(WorkItem getWorkItemFromIDReturn) {
      this.getWorkItemFromIDReturn = getWorkItemFromIDReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetWorkItemFromIDResponse)) {
         return false;
      } else {
         GetWorkItemFromIDResponse other = (GetWorkItemFromIDResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getWorkItemFromIDReturn == null && other.getGetWorkItemFromIDReturn() == null || this.getWorkItemFromIDReturn != null && this.getWorkItemFromIDReturn.equals(other.getGetWorkItemFromIDReturn());
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
         if (this.getGetWorkItemFromIDReturn() != null) {
            _hashCode += this.getGetWorkItemFromIDReturn().hashCode();
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
