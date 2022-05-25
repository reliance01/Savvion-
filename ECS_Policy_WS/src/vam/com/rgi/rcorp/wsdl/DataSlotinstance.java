package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DataSlotinstance implements Serializable {
   private String choices;
   private boolean isReadOnly;
   private boolean isWriteOnly;
   private String name;
   private long piid;
   private long ptid;
   private String type;
   private Object value;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(DataSlotinstance.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("choices");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "choices"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("isReadOnly");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "isReadOnly"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("isWriteOnly");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "isWriteOnly"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("name");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "name"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("piid");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "piid"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("ptid");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "ptid"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("type");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "type"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("value");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "value"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "anyType"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }

   public DataSlotinstance() {
   }

   public DataSlotinstance(String choices, boolean isReadOnly, boolean isWriteOnly, String name, long piid, long ptid, String type, Object value) {
      this.choices = choices;
      this.isReadOnly = isReadOnly;
      this.isWriteOnly = isWriteOnly;
      this.name = name;
      this.piid = piid;
      this.ptid = ptid;
      this.type = type;
      this.value = value;
   }

   public String getChoices() {
      return this.choices;
   }

   public void setChoices(String choices) {
      this.choices = choices;
   }

   public boolean isIsReadOnly() {
      return this.isReadOnly;
   }

   public void setIsReadOnly(boolean isReadOnly) {
      this.isReadOnly = isReadOnly;
   }

   public boolean isIsWriteOnly() {
      return this.isWriteOnly;
   }

   public void setIsWriteOnly(boolean isWriteOnly) {
      this.isWriteOnly = isWriteOnly;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public long getPiid() {
      return this.piid;
   }

   public void setPiid(long piid) {
      this.piid = piid;
   }

   public long getPtid() {
      return this.ptid;
   }

   public void setPtid(long ptid) {
      this.ptid = ptid;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof DataSlotinstance)) {
         return false;
      } else {
         DataSlotinstance other = (DataSlotinstance)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.choices == null && other.getChoices() == null || this.choices != null && this.choices.equals(other.getChoices())) && this.isReadOnly == other.isIsReadOnly() && this.isWriteOnly == other.isIsWriteOnly() && (this.name == null && other.getName() == null || this.name != null && this.name.equals(other.getName())) && this.piid == other.getPiid() && this.ptid == other.getPtid() && (this.type == null && other.getType() == null || this.type != null && this.type.equals(other.getType())) && (this.value == null && other.getValue() == null || this.value != null && this.value.equals(other.getValue()));
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
         if (this.getChoices() != null) {
            _hashCode += this.getChoices().hashCode();
         }

         _hashCode += (this.isIsReadOnly() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.isIsWriteOnly() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.getName() != null) {
            _hashCode += this.getName().hashCode();
         }

         _hashCode += (new Long(this.getPiid())).hashCode();
         _hashCode += (new Long(this.getPtid())).hashCode();
         if (this.getType() != null) {
            _hashCode += this.getType().hashCode();
         }

         if (this.getValue() != null) {
            _hashCode += this.getValue().hashCode();
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
