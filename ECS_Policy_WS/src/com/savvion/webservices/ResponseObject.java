package com.savvion.webservices;

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

public class ResponseObject implements Serializable {
   private String message;
   private String responseCode;
   private String[] resultStringArray;
   private WorkItemObject[] resultworkItemArray;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ResponseObject.class, true);

   static {
      typeDesc.setXmlType(new QName("http://rgicl.motor.savvion.com", "ResponseObject"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("message");
      elemField.setXmlName(new QName("", "message"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("responseCode");
      elemField.setXmlName(new QName("", "responseCode"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("resultStringArray");
      elemField.setXmlName(new QName("", "resultStringArray"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("resultworkItemArray");
      elemField.setXmlName(new QName("", "resultworkItemArray"));
      elemField.setXmlType(new QName("http://rgicl.motor.savvion.com", "WorkItemObject"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }

   public ResponseObject() {
   }

   public ResponseObject(String message, String responseCode, String[] resultStringArray, WorkItemObject[] resultworkItemArray) {
      this.message = message;
      this.responseCode = responseCode;
      this.resultStringArray = resultStringArray;
      this.resultworkItemArray = resultworkItemArray;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getResponseCode() {
      return this.responseCode;
   }

   public void setResponseCode(String responseCode) {
      this.responseCode = responseCode;
   }

   public String[] getResultStringArray() {
      return this.resultStringArray;
   }

   public void setResultStringArray(String[] resultStringArray) {
      this.resultStringArray = resultStringArray;
   }

   public WorkItemObject[] getResultworkItemArray() {
      return this.resultworkItemArray;
   }

   public void setResultworkItemArray(WorkItemObject[] resultworkItemArray) {
      this.resultworkItemArray = resultworkItemArray;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ResponseObject)) {
         return false;
      } else {
         ResponseObject other = (ResponseObject)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.message == null && other.getMessage() == null || this.message != null && this.message.equals(other.getMessage())) && (this.responseCode == null && other.getResponseCode() == null || this.responseCode != null && this.responseCode.equals(other.getResponseCode())) && (this.resultStringArray == null && other.getResultStringArray() == null || this.resultStringArray != null && Arrays.equals(this.resultStringArray, other.getResultStringArray())) && (this.resultworkItemArray == null && other.getResultworkItemArray() == null || this.resultworkItemArray != null && Arrays.equals(this.resultworkItemArray, other.getResultworkItemArray()));
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
         if (this.getMessage() != null) {
            _hashCode += this.getMessage().hashCode();
         }

         if (this.getResponseCode() != null) {
            _hashCode += this.getResponseCode().hashCode();
         }

         int i;
         Object obj;
         if (this.getResultStringArray() != null) {
            for(i = 0; i < Array.getLength(this.getResultStringArray()); ++i) {
               obj = Array.get(this.getResultStringArray(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
         }

         if (this.getResultworkItemArray() != null) {
            for(i = 0; i < Array.getLength(this.getResultworkItemArray()); ++i) {
               obj = Array.get(this.getResultworkItemArray(), i);
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
