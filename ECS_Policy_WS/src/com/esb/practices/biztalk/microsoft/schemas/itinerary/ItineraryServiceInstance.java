package com.esb.practices.biztalk.microsoft.schemas.itinerary;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.AttributeDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class ItineraryServiceInstance implements Serializable {
   private String uuid;
   private String name;
   private String type;
   private String state;
   private int position;
   private boolean isRequestResponse;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ItineraryServiceInstance.class, true);

   static {
      typeDesc.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">>Itinerary>ServiceInstance"));
      AttributeDesc attrField = new AttributeDesc();
      attrField.setFieldName("uuid");
      attrField.setXmlName(new QName("", "uuid"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("name");
      attrField.setXmlName(new QName("", "name"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("type");
      attrField.setXmlName(new QName("", "type"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("state");
      attrField.setXmlName(new QName("", "state"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("position");
      attrField.setXmlName(new QName("", "position"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("isRequestResponse");
      attrField.setXmlName(new QName("", "isRequestResponse"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      typeDesc.addFieldDesc(attrField);
   }

   public ItineraryServiceInstance() {
   }

   public ItineraryServiceInstance(String uuid, String name, String type, String state, int position, boolean isRequestResponse) {
      this.uuid = uuid;
      this.name = name;
      this.type = type;
      this.state = state;
      this.position = position;
      this.isRequestResponse = isRequestResponse;
   }

   public String getUuid() {
      return this.uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getState() {
      return this.state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public int getPosition() {
      return this.position;
   }

   public void setPosition(int position) {
      this.position = position;
   }

   public boolean isIsRequestResponse() {
      return this.isRequestResponse;
   }

   public void setIsRequestResponse(boolean isRequestResponse) {
      this.isRequestResponse = isRequestResponse;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ItineraryServiceInstance)) {
         return false;
      } else {
         ItineraryServiceInstance other = (ItineraryServiceInstance)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.uuid == null && other.getUuid() == null || this.uuid != null && this.uuid.equals(other.getUuid())) && (this.name == null && other.getName() == null || this.name != null && this.name.equals(other.getName())) && (this.type == null && other.getType() == null || this.type != null && this.type.equals(other.getType())) && (this.state == null && other.getState() == null || this.state != null && this.state.equals(other.getState())) && this.position == other.getPosition() && this.isRequestResponse == other.isIsRequestResponse();
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
         if (this.getUuid() != null) {
            _hashCode += this.getUuid().hashCode();
         }

         if (this.getName() != null) {
            _hashCode += this.getName().hashCode();
         }

         if (this.getType() != null) {
            _hashCode += this.getType().hashCode();
         }

         if (this.getState() != null) {
            _hashCode += this.getState().hashCode();
         }

         _hashCode += this.getPosition();
         _hashCode += (this.isIsRequestResponse() ? Boolean.TRUE : Boolean.FALSE).hashCode();
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
