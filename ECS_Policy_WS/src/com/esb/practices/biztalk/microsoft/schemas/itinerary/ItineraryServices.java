package com.esb.practices.biztalk.microsoft.schemas.itinerary;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class ItineraryServices implements Serializable {
   private ItineraryServicesService service;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ItineraryServices.class, true);

   static {
      typeDesc.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">>Itinerary>Services"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("service");
      elemField.setXmlName(new QName("", "Service"));
      elemField.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">>>Itinerary>Services>Service"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public ItineraryServices() {
   }

   public ItineraryServices(ItineraryServicesService service) {
      this.service = service;
   }

   public ItineraryServicesService getService() {
      return this.service;
   }

   public void setService(ItineraryServicesService service) {
      this.service = service;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ItineraryServices)) {
         return false;
      } else {
         ItineraryServices other = (ItineraryServices)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.service == null && other.getService() == null || this.service != null && this.service.equals(other.getService());
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
         if (this.getService() != null) {
            _hashCode += this.getService().hashCode();
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
