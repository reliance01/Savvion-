/**
 * SingleMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 16, 2010 (05:58:21 PDT) WSDL2Java emitter.
 */

package sms.services;

public class SingleMessage  implements java.io.Serializable {
    private java.lang.String app_Process;

    private java.lang.String department;

    private java.lang.String message;

    private java.lang.String mobileNumber;

    private java.lang.String password;

    private java.lang.String ref_Name;

    private java.lang.String ref_Value;

    private java.lang.String SMS_Event;

    private java.lang.String source_RequestID;

    private java.lang.String userName;

    public SingleMessage() {
    }

    public SingleMessage(
           java.lang.String app_Process,
           java.lang.String department,
           java.lang.String message,
           java.lang.String mobileNumber,
           java.lang.String password,
           java.lang.String ref_Name,
           java.lang.String ref_Value,
           java.lang.String SMS_Event,
           java.lang.String source_RequestID,
           java.lang.String userName) {
           this.app_Process = app_Process;
           this.department = department;
           this.message = message;
           this.mobileNumber = mobileNumber;
           this.password = password;
           this.ref_Name = ref_Name;
           this.ref_Value = ref_Value;
           this.SMS_Event = SMS_Event;
           this.source_RequestID = source_RequestID;
           this.userName = userName;
    }


    /**
     * Gets the app_Process value for this SingleMessage.
     * 
     * @return app_Process
     */
    public java.lang.String getApp_Process() {
        return app_Process;
    }


    /**
     * Sets the app_Process value for this SingleMessage.
     * 
     * @param app_Process
     */
    public void setApp_Process(java.lang.String app_Process) {
        this.app_Process = app_Process;
    }


    /**
     * Gets the department value for this SingleMessage.
     * 
     * @return department
     */
    public java.lang.String getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this SingleMessage.
     * 
     * @param department
     */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }


    /**
     * Gets the message value for this SingleMessage.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this SingleMessage.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the mobileNumber value for this SingleMessage.
     * 
     * @return mobileNumber
     */
    public java.lang.String getMobileNumber() {
        return mobileNumber;
    }


    /**
     * Sets the mobileNumber value for this SingleMessage.
     * 
     * @param mobileNumber
     */
    public void setMobileNumber(java.lang.String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    /**
     * Gets the password value for this SingleMessage.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this SingleMessage.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the ref_Name value for this SingleMessage.
     * 
     * @return ref_Name
     */
    public java.lang.String getRef_Name() {
        return ref_Name;
    }


    /**
     * Sets the ref_Name value for this SingleMessage.
     * 
     * @param ref_Name
     */
    public void setRef_Name(java.lang.String ref_Name) {
        this.ref_Name = ref_Name;
    }


    /**
     * Gets the ref_Value value for this SingleMessage.
     * 
     * @return ref_Value
     */
    public java.lang.String getRef_Value() {
        return ref_Value;
    }


    /**
     * Sets the ref_Value value for this SingleMessage.
     * 
     * @param ref_Value
     */
    public void setRef_Value(java.lang.String ref_Value) {
        this.ref_Value = ref_Value;
    }


    /**
     * Gets the SMS_Event value for this SingleMessage.
     * 
     * @return SMS_Event
     */
    public java.lang.String getSMS_Event() {
        return SMS_Event;
    }


    /**
     * Sets the SMS_Event value for this SingleMessage.
     * 
     * @param SMS_Event
     */
    public void setSMS_Event(java.lang.String SMS_Event) {
        this.SMS_Event = SMS_Event;
    }


    /**
     * Gets the source_RequestID value for this SingleMessage.
     * 
     * @return source_RequestID
     */
    public java.lang.String getSource_RequestID() {
        return source_RequestID;
    }


    /**
     * Sets the source_RequestID value for this SingleMessage.
     * 
     * @param source_RequestID
     */
    public void setSource_RequestID(java.lang.String source_RequestID) {
        this.source_RequestID = source_RequestID;
    }


    /**
     * Gets the userName value for this SingleMessage.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this SingleMessage.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SingleMessage)) return false;
        SingleMessage other = (SingleMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.app_Process==null && other.getApp_Process()==null) || 
             (this.app_Process!=null &&
              this.app_Process.equals(other.getApp_Process()))) &&
            ((this.department==null && other.getDepartment()==null) || 
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.mobileNumber==null && other.getMobileNumber()==null) || 
             (this.mobileNumber!=null &&
              this.mobileNumber.equals(other.getMobileNumber()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.ref_Name==null && other.getRef_Name()==null) || 
             (this.ref_Name!=null &&
              this.ref_Name.equals(other.getRef_Name()))) &&
            ((this.ref_Value==null && other.getRef_Value()==null) || 
             (this.ref_Value!=null &&
              this.ref_Value.equals(other.getRef_Value()))) &&
            ((this.SMS_Event==null && other.getSMS_Event()==null) || 
             (this.SMS_Event!=null &&
              this.SMS_Event.equals(other.getSMS_Event()))) &&
            ((this.source_RequestID==null && other.getSource_RequestID()==null) || 
             (this.source_RequestID!=null &&
              this.source_RequestID.equals(other.getSource_RequestID()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getApp_Process() != null) {
            _hashCode += getApp_Process().hashCode();
        }
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getMobileNumber() != null) {
            _hashCode += getMobileNumber().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getRef_Name() != null) {
            _hashCode += getRef_Name().hashCode();
        }
        if (getRef_Value() != null) {
            _hashCode += getRef_Value().hashCode();
        }
        if (getSMS_Event() != null) {
            _hashCode += getSMS_Event().hashCode();
        }
        if (getSource_RequestID() != null) {
            _hashCode += getSource_RequestID().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SingleMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "SingleMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("app_Process");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "App_Process"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "Department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "MobileNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ref_Name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "Ref_Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ref_Value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "Ref_Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SMS_Event");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "SMS_Event"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source_RequestID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "Source_RequestID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/", "UserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
