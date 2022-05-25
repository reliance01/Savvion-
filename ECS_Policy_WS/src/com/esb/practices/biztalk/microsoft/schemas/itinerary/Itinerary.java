package com.esb.practices.biztalk.microsoft.schemas.itinerary;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.axis.description.AttributeDesc;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class Itinerary implements Serializable {
   private ItineraryBizTalkSegment bizTalkSegment;
   private ItineraryServiceInstance serviceInstance;
   private ItineraryServices[] services;
   private ArrayOfItineraryResolversResolvers[] resolverGroups;
   private String uuid;
   private String beginTime;
   private String completeTime;
   private String state;
   private boolean isRequestResponse;
   private int servicecount;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(Itinerary.class, true);

   static {
      typeDesc.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">Itinerary"));
      AttributeDesc attrField = new AttributeDesc();
      attrField.setFieldName("uuid");
      attrField.setXmlName(new QName("", "uuid"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("beginTime");
      attrField.setXmlName(new QName("", "beginTime"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("completeTime");
      attrField.setXmlName(new QName("", "completeTime"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("state");
      attrField.setXmlName(new QName("", "state"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("isRequestResponse");
      attrField.setXmlName(new QName("", "isRequestResponse"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("servicecount");
      attrField.setXmlName(new QName("", "servicecount"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      typeDesc.addFieldDesc(attrField);
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("bizTalkSegment");
      elemField.setXmlName(new QName("", "BizTalkSegment"));
      elemField.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">>Itinerary>BizTalkSegment"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("serviceInstance");
      elemField.setXmlName(new QName("", "ServiceInstance"));
      elemField.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">>Itinerary>ServiceInstance"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("services");
      elemField.setXmlName(new QName("", "Services"));
      elemField.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">>Itinerary>Services"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      elemField.setMaxOccursUnbounded(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("resolverGroups");
      elemField.setXmlName(new QName("", "ResolverGroups"));
      elemField.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">ArrayOfItineraryResolvers>Resolvers"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      elemField.setItemQName(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", "Resolvers"));
      typeDesc.addFieldDesc(elemField);
   }

   public Itinerary() {
   }

   public Itinerary(ItineraryBizTalkSegment bizTalkSegment, ItineraryServiceInstance serviceInstance, ItineraryServices[] services, ArrayOfItineraryResolversResolvers[] resolverGroups, String uuid, String beginTime, String completeTime, String state, boolean isRequestResponse, int servicecount) {
      this.bizTalkSegment = bizTalkSegment;
      this.serviceInstance = serviceInstance;
      this.services = services;
      this.resolverGroups = resolverGroups;
      this.uuid = uuid;
      this.beginTime = beginTime;
      this.completeTime = completeTime;
      this.state = state;
      this.isRequestResponse = isRequestResponse;
      this.servicecount = servicecount;
   }

   public ItineraryBizTalkSegment getBizTalkSegment() {
      return this.bizTalkSegment;
   }

   public void setBizTalkSegment(ItineraryBizTalkSegment bizTalkSegment) {
      this.bizTalkSegment = bizTalkSegment;
   }

   public ItineraryServiceInstance getServiceInstance() {
      return this.serviceInstance;
   }

   public void setServiceInstance(ItineraryServiceInstance serviceInstance) {
      this.serviceInstance = serviceInstance;
   }

   public ItineraryServices[] getServices() {
      return this.services;
   }

   public void setServices(ItineraryServices[] services) {
      this.services = services;
   }

   public ItineraryServices getServices(int i) {
      return this.services[i];
   }

   public void setServices(int i, ItineraryServices _value) {
      this.services[i] = _value;
   }

   public ArrayOfItineraryResolversResolvers[] getResolverGroups() {
      return this.resolverGroups;
   }

   public void setResolverGroups(ArrayOfItineraryResolversResolvers[] resolverGroups) {
      this.resolverGroups = resolverGroups;
   }

   public String getUuid() {
      return this.uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }

   public String getBeginTime() {
      return this.beginTime;
   }

   public void setBeginTime(String beginTime) {
      this.beginTime = beginTime;
   }

   public String getCompleteTime() {
      return this.completeTime;
   }

   public void setCompleteTime(String completeTime) {
      this.completeTime = completeTime;
   }

   public String getState() {
      return this.state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public boolean isIsRequestResponse() {
      return this.isRequestResponse;
   }

   public void setIsRequestResponse(boolean isRequestResponse) {
      this.isRequestResponse = isRequestResponse;
   }

   public int getServicecount() {
      return this.servicecount;
   }

   public void setServicecount(int servicecount) {
      this.servicecount = servicecount;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof Itinerary)) {
         return false;
      } else {
         Itinerary other = (Itinerary)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.bizTalkSegment == null && other.getBizTalkSegment() == null || this.bizTalkSegment != null && this.bizTalkSegment.equals(other.getBizTalkSegment())) && (this.serviceInstance == null && other.getServiceInstance() == null || this.serviceInstance != null && this.serviceInstance.equals(other.getServiceInstance())) && (this.services == null && other.getServices() == null || this.services != null && Arrays.equals(this.services, other.getServices())) && (this.resolverGroups == null && other.getResolverGroups() == null || this.resolverGroups != null && Arrays.equals(this.resolverGroups, other.getResolverGroups())) && (this.uuid == null && other.getUuid() == null || this.uuid != null && this.uuid.equals(other.getUuid())) && (this.beginTime == null && other.getBeginTime() == null || this.beginTime != null && this.beginTime.equals(other.getBeginTime())) && (this.completeTime == null && other.getCompleteTime() == null || this.completeTime != null && this.completeTime.equals(other.getCompleteTime())) && (this.state == null && other.getState() == null || this.state != null && this.state.equals(other.getState())) && this.isRequestResponse == other.isIsRequestResponse() && this.servicecount == other.getServicecount();
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
         if (this.getBizTalkSegment() != null) {
            _hashCode += this.getBizTalkSegment().hashCode();
         }

         if (this.getServiceInstance() != null) {
            _hashCode += this.getServiceInstance().hashCode();
         }

         int i;
         Object obj;
         if (this.getServices() != null) {
            for(i = 0; i < Array.getLength(this.getServices()); ++i) {
               obj = Array.get(this.getServices(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
         }

         if (this.getResolverGroups() != null) {
            for(i = 0; i < Array.getLength(this.getResolverGroups()); ++i) {
               obj = Array.get(this.getResolverGroups(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
         }

         if (this.getUuid() != null) {
            _hashCode += this.getUuid().hashCode();
         }

         if (this.getBeginTime() != null) {
            _hashCode += this.getBeginTime().hashCode();
         }

         if (this.getCompleteTime() != null) {
            _hashCode += this.getCompleteTime().hashCode();
         }

         if (this.getState() != null) {
            _hashCode += this.getState().hashCode();
         }

         _hashCode += (this.isIsRequestResponse() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += this.getServicecount();
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
