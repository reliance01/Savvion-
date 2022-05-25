package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetWorkStepTemplateResponse implements Serializable {
   private WorkSteptemplate getWorkStepTemplateReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetWorkStepTemplateResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getWorkStepTemplateResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getWorkStepTemplateReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplateReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkSteptemplate"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetWorkStepTemplateResponse() {
   }

   public GetWorkStepTemplateResponse(WorkSteptemplate getWorkStepTemplateReturn) {
      this.getWorkStepTemplateReturn = getWorkStepTemplateReturn;
   }

   public WorkSteptemplate getGetWorkStepTemplateReturn() {
      return this.getWorkStepTemplateReturn;
   }

   public void setGetWorkStepTemplateReturn(WorkSteptemplate getWorkStepTemplateReturn) {
      this.getWorkStepTemplateReturn = getWorkStepTemplateReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetWorkStepTemplateResponse)) {
         return false;
      } else {
         GetWorkStepTemplateResponse other = (GetWorkStepTemplateResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getWorkStepTemplateReturn == null && other.getGetWorkStepTemplateReturn() == null || this.getWorkStepTemplateReturn != null && this.getWorkStepTemplateReturn.equals(other.getGetWorkStepTemplateReturn());
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
         if (this.getGetWorkStepTemplateReturn() != null) {
            _hashCode += this.getGetWorkStepTemplateReturn().hashCode();
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
