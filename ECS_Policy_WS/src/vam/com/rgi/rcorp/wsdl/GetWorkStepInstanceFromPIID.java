package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetWorkStepInstanceFromPIID implements Serializable {
   private String session;
   private long piid;
   private String wsName;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetWorkStepInstanceFromPIID.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceFromPIID"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("session");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "session"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("piid");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "piid"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("wsName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "wsName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public GetWorkStepInstanceFromPIID() {
   }

   public GetWorkStepInstanceFromPIID(String session, long piid, String wsName) {
      this.session = session;
      this.piid = piid;
      this.wsName = wsName;
   }

   public String getSession() {
      return this.session;
   }

   public void setSession(String session) {
      this.session = session;
   }

   public long getPiid() {
      return this.piid;
   }

   public void setPiid(long piid) {
      this.piid = piid;
   }

   public String getWsName() {
      return this.wsName;
   }

   public void setWsName(String wsName) {
      this.wsName = wsName;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetWorkStepInstanceFromPIID)) {
         return false;
      } else {
         GetWorkStepInstanceFromPIID other = (GetWorkStepInstanceFromPIID)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.session == null && other.getSession() == null || this.session != null && this.session.equals(other.getSession())) && this.piid == other.getPiid() && (this.wsName == null && other.getWsName() == null || this.wsName != null && this.wsName.equals(other.getWsName()));
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

         _hashCode += (new Long(this.getPiid())).hashCode();
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
