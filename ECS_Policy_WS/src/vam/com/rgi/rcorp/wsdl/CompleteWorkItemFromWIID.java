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

public class CompleteWorkItemFromWIID implements Serializable {
   private String session;
   private long wiid;
   private DataSlotinstance[] dsi;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(CompleteWorkItemFromWIID.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">completeWorkItemFromWIID"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("session");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "session"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("wiid");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "wiid"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("dsi");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "dsi"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      elemField.setNillable(false);
      elemField.setMaxOccursUnbounded(true);
      typeDesc.addFieldDesc(elemField);
   }

   public CompleteWorkItemFromWIID() {
   }

   public CompleteWorkItemFromWIID(String session, long wiid, DataSlotinstance[] dsi) {
      this.session = session;
      this.wiid = wiid;
      this.dsi = dsi;
   }

   public String getSession() {
      return this.session;
   }

   public void setSession(String session) {
      this.session = session;
   }

   public long getWiid() {
      return this.wiid;
   }

   public void setWiid(long wiid) {
      this.wiid = wiid;
   }

   public DataSlotinstance[] getDsi() {
      return this.dsi;
   }

   public void setDsi(DataSlotinstance[] dsi) {
      this.dsi = dsi;
   }

   public DataSlotinstance getDsi(int i) {
      return this.dsi[i];
   }

   public void setDsi(int i, DataSlotinstance _value) {
      this.dsi[i] = _value;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof CompleteWorkItemFromWIID)) {
         return false;
      } else {
         CompleteWorkItemFromWIID other = (CompleteWorkItemFromWIID)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.session == null && other.getSession() == null || this.session != null && this.session.equals(other.getSession())) && this.wiid == other.getWiid() && (this.dsi == null && other.getDsi() == null || this.dsi != null && Arrays.equals(this.dsi, other.getDsi()));
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

         _hashCode += (new Long(this.getWiid())).hashCode();
         if (this.getDsi() != null) {
            for(int i = 0; i < Array.getLength(this.getDsi()); ++i) {
               Object obj = Array.get(this.getDsi(), i);
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
