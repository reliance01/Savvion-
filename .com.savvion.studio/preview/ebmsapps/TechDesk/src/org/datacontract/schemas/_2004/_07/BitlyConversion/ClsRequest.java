/**
 * ClsRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.BitlyConversion;

public class ClsRequest  implements java.io.Serializable {
    private java.lang.String app_Event;

    private java.lang.String app_Process;

    private java.lang.String sourceUniqueID;

    private java.lang.String systemID;

    private java.lang.String long_url;

    public ClsRequest() {
    }

    public ClsRequest(
           java.lang.String app_Event,
           java.lang.String app_Process,
           java.lang.String sourceUniqueID,
           java.lang.String systemID,
           java.lang.String long_url) {
           this.app_Event = app_Event;
           this.app_Process = app_Process;
           this.sourceUniqueID = sourceUniqueID;
           this.systemID = systemID;
           this.long_url = long_url;
    }


    /**
     * Gets the app_Event value for this ClsRequest.
     * 
     * @return app_Event
     */
    public java.lang.String getApp_Event() {
        return app_Event;
    }


    /**
     * Sets the app_Event value for this ClsRequest.
     * 
     * @param app_Event
     */
    public void setApp_Event(java.lang.String app_Event) {
        this.app_Event = app_Event;
    }


    /**
     * Gets the app_Process value for this ClsRequest.
     * 
     * @return app_Process
     */
    public java.lang.String getApp_Process() {
        return app_Process;
    }


    /**
     * Sets the app_Process value for this ClsRequest.
     * 
     * @param app_Process
     */
    public void setApp_Process(java.lang.String app_Process) {
        this.app_Process = app_Process;
    }


    /**
     * Gets the sourceUniqueID value for this ClsRequest.
     * 
     * @return sourceUniqueID
     */
    public java.lang.String getSourceUniqueID() {
        return sourceUniqueID;
    }


    /**
     * Sets the sourceUniqueID value for this ClsRequest.
     * 
     * @param sourceUniqueID
     */
    public void setSourceUniqueID(java.lang.String sourceUniqueID) {
        this.sourceUniqueID = sourceUniqueID;
    }


    /**
     * Gets the systemID value for this ClsRequest.
     * 
     * @return systemID
     */
    public java.lang.String getSystemID() {
        return systemID;
    }


    /**
     * Sets the systemID value for this ClsRequest.
     * 
     * @param systemID
     */
    public void setSystemID(java.lang.String systemID) {
        this.systemID = systemID;
    }


    /**
     * Gets the long_url value for this ClsRequest.
     * 
     * @return long_url
     */
    public java.lang.String getLong_url() {
        return long_url;
    }


    /**
     * Sets the long_url value for this ClsRequest.
     * 
     * @param long_url
     */
    public void setLong_url(java.lang.String long_url) {
        this.long_url = long_url;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClsRequest)) return false;
        ClsRequest other = (ClsRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.app_Event==null && other.getApp_Event()==null) || 
             (this.app_Event!=null &&
              this.app_Event.equals(other.getApp_Event()))) &&
            ((this.app_Process==null && other.getApp_Process()==null) || 
             (this.app_Process!=null &&
              this.app_Process.equals(other.getApp_Process()))) &&
            ((this.sourceUniqueID==null && other.getSourceUniqueID()==null) || 
             (this.sourceUniqueID!=null &&
              this.sourceUniqueID.equals(other.getSourceUniqueID()))) &&
            ((this.systemID==null && other.getSystemID()==null) || 
             (this.systemID!=null &&
              this.systemID.equals(other.getSystemID()))) &&
            ((this.long_url==null && other.getLong_url()==null) || 
             (this.long_url!=null &&
              this.long_url.equals(other.getLong_url())));
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
        if (getApp_Event() != null) {
            _hashCode += getApp_Event().hashCode();
        }
        if (getApp_Process() != null) {
            _hashCode += getApp_Process().hashCode();
        }
        if (getSourceUniqueID() != null) {
            _hashCode += getSourceUniqueID().hashCode();
        }
        if (getSystemID() != null) {
            _hashCode += getSystemID().hashCode();
        }
        if (getLong_url() != null) {
            _hashCode += getLong_url().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClsRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/BitlyConversion", "ClsRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("app_Event");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/BitlyConversion", "App_Event"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("app_Process");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/BitlyConversion", "App_Process"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceUniqueID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/BitlyConversion", "SourceUniqueID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/BitlyConversion", "SystemID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("long_url");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/BitlyConversion", "long_url"));
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
