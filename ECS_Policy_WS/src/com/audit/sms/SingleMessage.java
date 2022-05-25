package com.audit.sms;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class SingleMessage implements Serializable {
   private String department;
   private String message;
   private String mobileNumber;
   private String password;
   private String userName;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(SingleMessage.class, true);

   static {
      typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/", "SingleMessage"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("department");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/", "Department"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("message");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/", "Message"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("mobileNumber");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/", "MobileNumber"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("password");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/", "Password"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("userName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/", "UserName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }

   public SingleMessage() {
   }

   public SingleMessage(String department, String message, String mobileNumber, String password, String userName) {
      this.department = department;
      this.message = message;
      this.mobileNumber = mobileNumber;
      this.password = password;
      this.userName = userName;
   }

   public String getDepartment() {
      return this.department;
   }

   public void setDepartment(String department) {
      this.department = department;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getMobileNumber() {
      return this.mobileNumber;
   }

   public void setMobileNumber(String mobileNumber) {
      this.mobileNumber = mobileNumber;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getUserName() {
      return this.userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof SingleMessage)) {
         return false;
      } else {
         SingleMessage other = (SingleMessage)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.department == null && other.getDepartment() == null || this.department != null && this.department.equals(other.getDepartment())) && (this.message == null && other.getMessage() == null || this.message != null && this.message.equals(other.getMessage())) && (this.mobileNumber == null && other.getMobileNumber() == null || this.mobileNumber != null && this.mobileNumber.equals(other.getMobileNumber())) && (this.password == null && other.getPassword() == null || this.password != null && this.password.equals(other.getPassword())) && (this.userName == null && other.getUserName() == null || this.userName != null && this.userName.equals(other.getUserName()));
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
         if (this.getDepartment() != null) {
            _hashCode += this.getDepartment().hashCode();
         }

         if (this.getMessage() != null) {
            _hashCode += this.getMessage().hashCode();
         }

         if (this.getMobileNumber() != null) {
            _hashCode += this.getMobileNumber().hashCode();
         }

         if (this.getPassword() != null) {
            _hashCode += this.getPassword().hashCode();
         }

         if (this.getUserName() != null) {
            _hashCode += this.getUserName().hashCode();
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
