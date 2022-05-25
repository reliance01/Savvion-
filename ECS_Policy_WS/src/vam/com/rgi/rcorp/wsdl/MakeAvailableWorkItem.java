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

public class MakeAvailableWorkItem implements Serializable {
   private String session;
   private String wiName;
   private String[] performers;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(MakeAvailableWorkItem.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">makeAvailableWorkItem"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("session");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "session"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("wiName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "wiName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("performers");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "performers"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      elemField.setMaxOccursUnbounded(true);
      typeDesc.addFieldDesc(elemField);
   }

   public MakeAvailableWorkItem() {
   }

   public MakeAvailableWorkItem(String session, String wiName, String[] performers) {
      this.session = session;
      this.wiName = wiName;
      this.performers = performers;
   }

   public String getSession() {
      return this.session;
   }

   public void setSession(String session) {
      this.session = session;
   }

   public String getWiName() {
      return this.wiName;
   }

   public void setWiName(String wiName) {
      this.wiName = wiName;
   }

   public String[] getPerformers() {
      return this.performers;
   }

   public void setPerformers(String[] performers) {
      this.performers = performers;
   }

   public String getPerformers(int i) {
      return this.performers[i];
   }

   public void setPerformers(int i, String _value) {
      this.performers[i] = _value;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof MakeAvailableWorkItem)) {
         return false;
      } else {
         MakeAvailableWorkItem other = (MakeAvailableWorkItem)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.session == null && other.getSession() == null || this.session != null && this.session.equals(other.getSession())) && (this.wiName == null && other.getWiName() == null || this.wiName != null && this.wiName.equals(other.getWiName())) && (this.performers == null && other.getPerformers() == null || this.performers != null && Arrays.equals(this.performers, other.getPerformers()));
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

         if (this.getWiName() != null) {
            _hashCode += this.getWiName().hashCode();
         }

         if (this.getPerformers() != null) {
            for(int i = 0; i < Array.getLength(this.getPerformers()); ++i) {
               Object obj = Array.get(this.getPerformers(), i);
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
