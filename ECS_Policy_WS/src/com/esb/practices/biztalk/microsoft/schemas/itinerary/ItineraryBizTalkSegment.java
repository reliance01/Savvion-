package com.esb.practices.biztalk.microsoft.schemas.itinerary;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.AttributeDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class ItineraryBizTalkSegment implements Serializable {
   private String interchangeId;
   private String epmRRCorrelationToken;
   private String receiveInstanceId;
   private String messageId;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ItineraryBizTalkSegment.class, true);

   static {
      typeDesc.setXmlType(new QName("http://schemas.microsoft.biztalk.practices.esb.com/itinerary", ">>Itinerary>BizTalkSegment"));
      AttributeDesc attrField = new AttributeDesc();
      attrField.setFieldName("interchangeId");
      attrField.setXmlName(new QName("", "interchangeId"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("epmRRCorrelationToken");
      attrField.setXmlName(new QName("", "epmRRCorrelationToken"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("receiveInstanceId");
      attrField.setXmlName(new QName("", "receiveInstanceId"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
      attrField = new AttributeDesc();
      attrField.setFieldName("messageId");
      attrField.setXmlName(new QName("", "messageId"));
      attrField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      typeDesc.addFieldDesc(attrField);
   }

   public ItineraryBizTalkSegment() {
   }

   public ItineraryBizTalkSegment(String interchangeId, String epmRRCorrelationToken, String receiveInstanceId, String messageId) {
      this.interchangeId = interchangeId;
      this.epmRRCorrelationToken = epmRRCorrelationToken;
      this.receiveInstanceId = receiveInstanceId;
      this.messageId = messageId;
   }

   public String getInterchangeId() {
      return this.interchangeId;
   }

   public void setInterchangeId(String interchangeId) {
      this.interchangeId = interchangeId;
   }

   public String getEpmRRCorrelationToken() {
      return this.epmRRCorrelationToken;
   }

   public void setEpmRRCorrelationToken(String epmRRCorrelationToken) {
      this.epmRRCorrelationToken = epmRRCorrelationToken;
   }

   public String getReceiveInstanceId() {
      return this.receiveInstanceId;
   }

   public void setReceiveInstanceId(String receiveInstanceId) {
      this.receiveInstanceId = receiveInstanceId;
   }

   public String getMessageId() {
      return this.messageId;
   }

   public void setMessageId(String messageId) {
      this.messageId = messageId;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ItineraryBizTalkSegment)) {
         return false;
      } else {
         ItineraryBizTalkSegment other = (ItineraryBizTalkSegment)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.interchangeId == null && other.getInterchangeId() == null || this.interchangeId != null && this.interchangeId.equals(other.getInterchangeId())) && (this.epmRRCorrelationToken == null && other.getEpmRRCorrelationToken() == null || this.epmRRCorrelationToken != null && this.epmRRCorrelationToken.equals(other.getEpmRRCorrelationToken())) && (this.receiveInstanceId == null && other.getReceiveInstanceId() == null || this.receiveInstanceId != null && this.receiveInstanceId.equals(other.getReceiveInstanceId())) && (this.messageId == null && other.getMessageId() == null || this.messageId != null && this.messageId.equals(other.getMessageId()));
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
         if (this.getInterchangeId() != null) {
            _hashCode += this.getInterchangeId().hashCode();
         }

         if (this.getEpmRRCorrelationToken() != null) {
            _hashCode += this.getEpmRRCorrelationToken().hashCode();
         }

         if (this.getReceiveInstanceId() != null) {
            _hashCode += this.getReceiveInstanceId().hashCode();
         }

         if (this.getMessageId() != null) {
            _hashCode += this.getMessageId().hashCode();
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
