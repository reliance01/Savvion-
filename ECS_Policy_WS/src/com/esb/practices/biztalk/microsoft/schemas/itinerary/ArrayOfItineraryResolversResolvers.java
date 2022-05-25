package com.esb.practices.biztalk.microsoft.schemas.itinerary;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.AttributeDesc;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.SimpleType;
import org.apache.axis.encoding.ser.SimpleDeserializer;
import org.apache.axis.encoding.ser.SimpleSerializer;

public class ArrayOfItineraryResolversResolvers implements Serializable, SimpleType {
   private String _value;
   private String serviceId;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ArrayOfItineraryResolversResolvers.class, true);

   static {
      typeDesc.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">ArrayOfItineraryResolvers>Resolvers"));
      AttributeDesc attrField = new AttributeDesc();
      attrField.setFieldName("serviceId");
      attrField.setXmlName(new QName("", "serviceId"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("_value");
      elemField.setXmlName(new QName("", "_value"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public ArrayOfItineraryResolversResolvers() {
   }

   public ArrayOfItineraryResolversResolvers(String _value) {
      this._value = _value;
   }

   public String toString() {
      return this._value;
   }

   public String get_value() {
      return this._value;
   }

   public void set_value(String _value) {
      this._value = _value;
   }

   public String getServiceId() {
      return this.serviceId;
   }

   public void setServiceId(String serviceId) {
      this.serviceId = serviceId;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ArrayOfItineraryResolversResolvers)) {
         return false;
      } else {
         ArrayOfItineraryResolversResolvers other = (ArrayOfItineraryResolversResolvers)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this._value == null && other.get_value() == null || this._value != null && this._value.equals(other.get_value())) && (this.serviceId == null && other.getServiceId() == null || this.serviceId != null && this.serviceId.equals(other.getServiceId()));
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
         if (this.get_value() != null) {
            _hashCode += this.get_value().hashCode();
         }

         if (this.getServiceId() != null) {
            _hashCode += this.getServiceId().hashCode();
         }

         this.__hashCodeCalc = false;
         return _hashCode;
      }
   }

   public static TypeDesc getTypeDesc() {
      return typeDesc;
   }

   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
      return new SimpleSerializer(_javaType, _xmlType, typeDesc);
   }

   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
      return new SimpleDeserializer(_javaType, _xmlType, typeDesc);
   }
}
