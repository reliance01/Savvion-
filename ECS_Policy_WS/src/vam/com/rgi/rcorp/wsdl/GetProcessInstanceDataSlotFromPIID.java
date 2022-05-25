package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetProcessInstanceDataSlotFromPIID implements Serializable {
   private String session;
   private long piid;
   private String[] dsName;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(GetProcessInstanceDataSlotFromPIID.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceDataSlotFromPIID"));
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
      elemField.setFieldName("dsName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "dsName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      elemField.setMaxOccursUnbounded(true);
      typeDesc.addFieldDesc(elemField);
   }

   public GetProcessInstanceDataSlotFromPIID() {
   }

   public GetProcessInstanceDataSlotFromPIID(String session, long piid, String[] dsName) {
      this.session = session;
      this.piid = piid;
      this.dsName = dsName;
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

   public String[] getDsName() {
      return this.dsName;
   }

   public void setDsName(String[] dsName) {
      this.dsName = dsName;
   }

   public String getDsName(int i) {
      return this.dsName[i];
   }

   public void setDsName(int i, String _value) {
      this.dsName[i] = _value;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetProcessInstanceDataSlotFromPIID)) {
         return false;
      } else {
         GetProcessInstanceDataSlotFromPIID other = (GetProcessInstanceDataSlotFromPIID)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.session == null && other.getSession() == null || this.session != null && this.session.equals(other.getSession())) && this.piid == other.getPiid() && (this.dsName == null && other.getDsName() == null || this.dsName != null && Arrays.equals(this.dsName, other.getDsName()));
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
         if (this.getDsName() != null) {
            for(int i = 0; i < Array.getLength(this.getDsName()); ++i) {
               Object obj = Array.get(this.getDsName(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
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
