package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetStartWorkStepTemplateResponse implements Serializable {
   private WorkSteptemplate getStartWorkStepTemplateReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetStartWorkStepTemplateResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getStartWorkStepTemplateResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("getStartWorkStepTemplateReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "getStartWorkStepTemplateReturn"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkSteptemplate"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetStartWorkStepTemplateResponse() {
   }

   public GetStartWorkStepTemplateResponse(WorkSteptemplate getStartWorkStepTemplateReturn) {
      this.getStartWorkStepTemplateReturn = getStartWorkStepTemplateReturn;
   }

   public WorkSteptemplate getGetStartWorkStepTemplateReturn() {
      return this.getStartWorkStepTemplateReturn;
   }

   public void setGetStartWorkStepTemplateReturn(WorkSteptemplate getStartWorkStepTemplateReturn) {
      this.getStartWorkStepTemplateReturn = getStartWorkStepTemplateReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetStartWorkStepTemplateResponse)) {
         return false;
      } else {
         GetStartWorkStepTemplateResponse other = (GetStartWorkStepTemplateResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.getStartWorkStepTemplateReturn == null && other.getGetStartWorkStepTemplateReturn() == null || this.getStartWorkStepTemplateReturn != null && this.getStartWorkStepTemplateReturn.equals(other.getGetStartWorkStepTemplateReturn());
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
         if (this.getGetStartWorkStepTemplateReturn() != null) {
            _hashCode += this.getGetStartWorkStepTemplateReturn().hashCode();
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
