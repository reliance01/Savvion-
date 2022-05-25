/**
 * UtilBO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package app.icm;

public class UtilBO  implements java.io.Serializable {
    private java.lang.String coStatus;

    private java.lang.String createdBy;

    private java.util.Calendar createdDate;

    private java.lang.String deletedBy;

    private java.util.Calendar deletedDate;

    private java.util.Calendar maxInstrumentDate;

    private java.lang.String modifyedBy;

    private java.util.Calendar modifyedDate;

    private java.math.BigDecimal totalCollection;

    private java.util.Calendar transDate;

    private java.lang.String transactionID;

    private java.lang.String vendorType;

    public UtilBO() {
    }

    public UtilBO(
           java.lang.String coStatus,
           java.lang.String createdBy,
           java.util.Calendar createdDate,
           java.lang.String deletedBy,
           java.util.Calendar deletedDate,
           java.util.Calendar maxInstrumentDate,
           java.lang.String modifyedBy,
           java.util.Calendar modifyedDate,
           java.math.BigDecimal totalCollection,
           java.util.Calendar transDate,
           java.lang.String transactionID,
           java.lang.String vendorType) {
           this.coStatus = coStatus;
           this.createdBy = createdBy;
           this.createdDate = createdDate;
           this.deletedBy = deletedBy;
           this.deletedDate = deletedDate;
           this.maxInstrumentDate = maxInstrumentDate;
           this.modifyedBy = modifyedBy;
           this.modifyedDate = modifyedDate;
           this.totalCollection = totalCollection;
           this.transDate = transDate;
           this.transactionID = transactionID;
           this.vendorType = vendorType;
    }


    /**
     * Gets the coStatus value for this UtilBO.
     * 
     * @return coStatus
     */
    public java.lang.String getCoStatus() {
        return coStatus;
    }


    /**
     * Sets the coStatus value for this UtilBO.
     * 
     * @param coStatus
     */
    public void setCoStatus(java.lang.String coStatus) {
        this.coStatus = coStatus;
    }


    /**
     * Gets the createdBy value for this UtilBO.
     * 
     * @return createdBy
     */
    public java.lang.String getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this UtilBO.
     * 
     * @param createdBy
     */
    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdDate value for this UtilBO.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this UtilBO.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the deletedBy value for this UtilBO.
     * 
     * @return deletedBy
     */
    public java.lang.String getDeletedBy() {
        return deletedBy;
    }


    /**
     * Sets the deletedBy value for this UtilBO.
     * 
     * @param deletedBy
     */
    public void setDeletedBy(java.lang.String deletedBy) {
        this.deletedBy = deletedBy;
    }


    /**
     * Gets the deletedDate value for this UtilBO.
     * 
     * @return deletedDate
     */
    public java.util.Calendar getDeletedDate() {
        return deletedDate;
    }


    /**
     * Sets the deletedDate value for this UtilBO.
     * 
     * @param deletedDate
     */
    public void setDeletedDate(java.util.Calendar deletedDate) {
        this.deletedDate = deletedDate;
    }


    /**
     * Gets the maxInstrumentDate value for this UtilBO.
     * 
     * @return maxInstrumentDate
     */
    public java.util.Calendar getMaxInstrumentDate() {
        return maxInstrumentDate;
    }


    /**
     * Sets the maxInstrumentDate value for this UtilBO.
     * 
     * @param maxInstrumentDate
     */
    public void setMaxInstrumentDate(java.util.Calendar maxInstrumentDate) {
        this.maxInstrumentDate = maxInstrumentDate;
    }


    /**
     * Gets the modifyedBy value for this UtilBO.
     * 
     * @return modifyedBy
     */
    public java.lang.String getModifyedBy() {
        return modifyedBy;
    }


    /**
     * Sets the modifyedBy value for this UtilBO.
     * 
     * @param modifyedBy
     */
    public void setModifyedBy(java.lang.String modifyedBy) {
        this.modifyedBy = modifyedBy;
    }


    /**
     * Gets the modifyedDate value for this UtilBO.
     * 
     * @return modifyedDate
     */
    public java.util.Calendar getModifyedDate() {
        return modifyedDate;
    }


    /**
     * Sets the modifyedDate value for this UtilBO.
     * 
     * @param modifyedDate
     */
    public void setModifyedDate(java.util.Calendar modifyedDate) {
        this.modifyedDate = modifyedDate;
    }


    /**
     * Gets the totalCollection value for this UtilBO.
     * 
     * @return totalCollection
     */
    public java.math.BigDecimal getTotalCollection() {
        return totalCollection;
    }


    /**
     * Sets the totalCollection value for this UtilBO.
     * 
     * @param totalCollection
     */
    public void setTotalCollection(java.math.BigDecimal totalCollection) {
        this.totalCollection = totalCollection;
    }


    /**
     * Gets the transDate value for this UtilBO.
     * 
     * @return transDate
     */
    public java.util.Calendar getTransDate() {
        return transDate;
    }


    /**
     * Sets the transDate value for this UtilBO.
     * 
     * @param transDate
     */
    public void setTransDate(java.util.Calendar transDate) {
        this.transDate = transDate;
    }


    /**
     * Gets the transactionID value for this UtilBO.
     * 
     * @return transactionID
     */
    public java.lang.String getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this UtilBO.
     * 
     * @param transactionID
     */
    public void setTransactionID(java.lang.String transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the vendorType value for this UtilBO.
     * 
     * @return vendorType
     */
    public java.lang.String getVendorType() {
        return vendorType;
    }


    /**
     * Sets the vendorType value for this UtilBO.
     * 
     * @param vendorType
     */
    public void setVendorType(java.lang.String vendorType) {
        this.vendorType = vendorType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtilBO)) return false;
        UtilBO other = (UtilBO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.coStatus==null && other.getCoStatus()==null) || 
             (this.coStatus!=null &&
              this.coStatus.equals(other.getCoStatus()))) &&
            ((this.createdBy==null && other.getCreatedBy()==null) || 
             (this.createdBy!=null &&
              this.createdBy.equals(other.getCreatedBy()))) &&
            ((this.createdDate==null && other.getCreatedDate()==null) || 
             (this.createdDate!=null &&
              this.createdDate.equals(other.getCreatedDate()))) &&
            ((this.deletedBy==null && other.getDeletedBy()==null) || 
             (this.deletedBy!=null &&
              this.deletedBy.equals(other.getDeletedBy()))) &&
            ((this.deletedDate==null && other.getDeletedDate()==null) || 
             (this.deletedDate!=null &&
              this.deletedDate.equals(other.getDeletedDate()))) &&
            ((this.maxInstrumentDate==null && other.getMaxInstrumentDate()==null) || 
             (this.maxInstrumentDate!=null &&
              this.maxInstrumentDate.equals(other.getMaxInstrumentDate()))) &&
            ((this.modifyedBy==null && other.getModifyedBy()==null) || 
             (this.modifyedBy!=null &&
              this.modifyedBy.equals(other.getModifyedBy()))) &&
            ((this.modifyedDate==null && other.getModifyedDate()==null) || 
             (this.modifyedDate!=null &&
              this.modifyedDate.equals(other.getModifyedDate()))) &&
            ((this.totalCollection==null && other.getTotalCollection()==null) || 
             (this.totalCollection!=null &&
              this.totalCollection.equals(other.getTotalCollection()))) &&
            ((this.transDate==null && other.getTransDate()==null) || 
             (this.transDate!=null &&
              this.transDate.equals(other.getTransDate()))) &&
            ((this.transactionID==null && other.getTransactionID()==null) || 
             (this.transactionID!=null &&
              this.transactionID.equals(other.getTransactionID()))) &&
            ((this.vendorType==null && other.getVendorType()==null) || 
             (this.vendorType!=null &&
              this.vendorType.equals(other.getVendorType())));
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
        if (getCoStatus() != null) {
            _hashCode += getCoStatus().hashCode();
        }
        if (getCreatedBy() != null) {
            _hashCode += getCreatedBy().hashCode();
        }
        if (getCreatedDate() != null) {
            _hashCode += getCreatedDate().hashCode();
        }
        if (getDeletedBy() != null) {
            _hashCode += getDeletedBy().hashCode();
        }
        if (getDeletedDate() != null) {
            _hashCode += getDeletedDate().hashCode();
        }
        if (getMaxInstrumentDate() != null) {
            _hashCode += getMaxInstrumentDate().hashCode();
        }
        if (getModifyedBy() != null) {
            _hashCode += getModifyedBy().hashCode();
        }
        if (getModifyedDate() != null) {
            _hashCode += getModifyedDate().hashCode();
        }
        if (getTotalCollection() != null) {
            _hashCode += getTotalCollection().hashCode();
        }
        if (getTransDate() != null) {
            _hashCode += getTransDate().hashCode();
        }
        if (getTransactionID() != null) {
            _hashCode += getTransactionID().hashCode();
        }
        if (getVendorType() != null) {
            _hashCode += getVendorType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtilBO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "UtilBO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "CoStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "CreatedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "CreatedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deletedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "DeletedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deletedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "DeletedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxInstrumentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "MaxInstrumentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifyedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "ModifyedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifyedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "ModifyedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCollection");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "TotalCollection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "TransDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "TransactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vendorType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "VendorType"));
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