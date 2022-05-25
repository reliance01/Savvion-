package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetProcessTemplateAppName implements Serializable {
   private String session;
   private String ptName;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetProcessTemplateAppName.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateAppName"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("session");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "session"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("ptName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "ptName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetProcessTemplateAppName() {
   }

   public GetProcessTemplateAppName(String session, String ptName) {
      this.session = session;
      this.ptName = ptName;
   }

   public String getSession() {
      return this.session;
   }

   public void setSession(String session) {
      this.session = session;
   }

   public String getPtName() {
      return this.ptName;
   }

   public void setPtName(String ptName) {
      this.ptName = ptName;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetProcessTemplateAppName)) {
         return false;
      } else {
         GetProcessTemplateAppName other = (GetProcessTemplateAppName)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.session == null && other.getSession() == null || this.session != null && this.session.equals(other.getSession())) && (this.ptName == null && other.getPtName() == null || this.ptName != null && this.ptName.equals(other.getPtName()));
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
         if (this.getSession() != null) {
            _hashCode += this.getSession().hashCode();
         }

         if (this.getPtName() != null) {
            _hashCode += this.getPtName().hashCode();
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
