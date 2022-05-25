package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetWorkStepTemplate implements Serializable {
   private String session;
   private String ptName;
   private String wsName;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetWorkStepTemplate.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getWorkStepTemplate"));
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
      elemField = new ElementDesc();
      elemField.setFieldName("wsName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "wsName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetWorkStepTemplate() {
   }

   public GetWorkStepTemplate(String session, String ptName, String wsName) {
      this.session = session;
      this.ptName = ptName;
      this.wsName = wsName;
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

   public String getWsName() {
      return this.wsName;
   }

   public void setWsName(String wsName) {
      this.wsName = wsName;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetWorkStepTemplate)) {
         return false;
      } else {
         GetWorkStepTemplate other = (GetWorkStepTemplate)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.session == null && other.getSession() == null || this.session != null && this.session.equals(other.getSession())) && (this.ptName == null && other.getPtName() == null || this.ptName != null && this.ptName.equals(other.getPtName())) && (this.wsName == null && other.getWsName() == null || this.wsName != null && this.wsName.equals(other.getWsName()));
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

         if (this.getWsName() != null) {
            _hashCode += this.getWsName().hashCode();
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
