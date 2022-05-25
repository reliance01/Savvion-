package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class SetProcessInstanceDataSlotValue implements Serializable {
   private String session;
   private String piName;
   private DataSlotinstance dsi;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(SetProcessInstanceDataSlotValue.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">setProcessInstanceDataSlotValue"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("session");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "session"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("piName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "piName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("dsi");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "dsi"));
      elemField.setXmlType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public SetProcessInstanceDataSlotValue() {
   }

   public SetProcessInstanceDataSlotValue(String session, String piName, DataSlotinstance dsi) {
      this.session = session;
      this.piName = piName;
      this.dsi = dsi;
   }

   public String getSession() {
      return this.session;
   }

   public void setSession(String session) {
      this.session = session;
   }

   public String getPiName() {
      return this.piName;
   }

   public void setPiName(String piName) {
      this.piName = piName;
   }

   public DataSlotinstance getDsi() {
      return this.dsi;
   }

   public void setDsi(DataSlotinstance dsi) {
      this.dsi = dsi;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof SetProcessInstanceDataSlotValue)) {
         return false;
      } else {
         SetProcessInstanceDataSlotValue other = (SetProcessInstanceDataSlotValue)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.session == null && other.getSession() == null || this.session != null && this.session.equals(other.getSession())) && (this.piName == null && other.getPiName() == null || this.piName != null && this.piName.equals(other.getPiName())) && (this.dsi == null && other.getDsi() == null || this.dsi != null && this.dsi.equals(other.getDsi()));
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

         if (this.getPiName() != null) {
            _hashCode += this.getPiName().hashCode();
         }

         if (this.getDsi() != null) {
            _hashCode += this.getDsi().hashCode();
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
