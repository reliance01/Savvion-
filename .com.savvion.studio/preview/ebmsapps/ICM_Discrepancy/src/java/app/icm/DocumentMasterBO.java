/**
 * DocumentMasterBO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package app.icm;

public class DocumentMasterBO  extends app.icm.UtilBO  implements java.io.Serializable {
    private java.lang.String _BusinessType;

    private int _DocumentID;

    private java.lang.String _DocumentName;

    private boolean _IsDeleted;

    private boolean _IsMandatory;

    private boolean _IsSubmited;

    private java.lang.String _ProductCode;

    private java.lang.String _ProposalID;

    private java.lang.String deletedBy;

    private java.lang.String updatedBy;

    public DocumentMasterBO() {
    }

    public DocumentMasterBO(
           java.lang.String coStatus,
           java.lang.String createdBy,
           java.util.Calendar createdDate,
           java.lang.String _deletedBy,
           java.util.Calendar deletedDate,
           java.util.Calendar maxInstrumentDate,
           java.lang.String modifyedBy,
           java.util.Calendar modifyedDate,
           java.math.BigDecimal totalCollection,
           java.util.Calendar transDate,
           java.lang.String transactionID,
           java.lang.String vendorType,
           java.lang.String _BusinessType,
           int _DocumentID,
           java.lang.String _DocumentName,
           boolean _IsDeleted,
           boolean _IsMandatory,
           boolean _IsSubmited,
           java.lang.String _ProductCode,
           java.lang.String _ProposalID,
           java.lang.String deletedBy,
           java.lang.String updatedBy) {
        super(
            coStatus,
            createdBy,
            createdDate,
            _deletedBy,
            deletedDate,
            maxInstrumentDate,
            modifyedBy,
            modifyedDate,
            totalCollection,
            transDate,
            transactionID,
            vendorType);
        this._BusinessType = _BusinessType;
        this._DocumentID = _DocumentID;
        this._DocumentName = _DocumentName;
        this._IsDeleted = _IsDeleted;
        this._IsMandatory = _IsMandatory;
        this._IsSubmited = _IsSubmited;
        this._ProductCode = _ProductCode;
        this._ProposalID = _ProposalID;
        this.deletedBy = deletedBy;
        this.updatedBy = updatedBy;
    }


    /**
     * Gets the _BusinessType value for this DocumentMasterBO.
     * 
     * @return _BusinessType
     */
    public java.lang.String get_BusinessType() {
        return _BusinessType;
    }


    /**
     * Sets the _BusinessType value for this DocumentMasterBO.
     * 
     * @param _BusinessType
     */
    public void set_BusinessType(java.lang.String _BusinessType) {
        this._BusinessType = _BusinessType;
    }


    /**
     * Gets the _DocumentID value for this DocumentMasterBO.
     * 
     * @return _DocumentID
     */
    public int get_DocumentID() {
        return _DocumentID;
    }


    /**
     * Sets the _DocumentID value for this DocumentMasterBO.
     * 
     * @param _DocumentID
     */
    public void set_DocumentID(int _DocumentID) {
        this._DocumentID = _DocumentID;
    }


    /**
     * Gets the _DocumentName value for this DocumentMasterBO.
     * 
     * @return _DocumentName
     */
    public java.lang.String get_DocumentName() {
        return _DocumentName;
    }


    /**
     * Sets the _DocumentName value for this DocumentMasterBO.
     * 
     * @param _DocumentName
     */
    public void set_DocumentName(java.lang.String _DocumentName) {
        this._DocumentName = _DocumentName;
    }


    /**
     * Gets the _IsDeleted value for this DocumentMasterBO.
     * 
     * @return _IsDeleted
     */
    public boolean is_IsDeleted() {
        return _IsDeleted;
    }


    /**
     * Sets the _IsDeleted value for this DocumentMasterBO.
     * 
     * @param _IsDeleted
     */
    public void set_IsDeleted(boolean _IsDeleted) {
        this._IsDeleted = _IsDeleted;
    }


    /**
     * Gets the _IsMandatory value for this DocumentMasterBO.
     * 
     * @return _IsMandatory
     */
    public boolean is_IsMandatory() {
        return _IsMandatory;
    }


    /**
     * Sets the _IsMandatory value for this DocumentMasterBO.
     * 
     * @param _IsMandatory
     */
    public void set_IsMandatory(boolean _IsMandatory) {
        this._IsMandatory = _IsMandatory;
    }


    /**
     * Gets the _IsSubmited value for this DocumentMasterBO.
     * 
     * @return _IsSubmited
     */
    public boolean is_IsSubmited() {
        return _IsSubmited;
    }


    /**
     * Sets the _IsSubmited value for this DocumentMasterBO.
     * 
     * @param _IsSubmited
     */
    public void set_IsSubmited(boolean _IsSubmited) {
        this._IsSubmited = _IsSubmited;
    }


    /**
     * Gets the _ProductCode value for this DocumentMasterBO.
     * 
     * @return _ProductCode
     */
    public java.lang.String get_ProductCode() {
        return _ProductCode;
    }


    /**
     * Sets the _ProductCode value for this DocumentMasterBO.
     * 
     * @param _ProductCode
     */
    public void set_ProductCode(java.lang.String _ProductCode) {
        this._ProductCode = _ProductCode;
    }


    /**
     * Gets the _ProposalID value for this DocumentMasterBO.
     * 
     * @return _ProposalID
     */
    public java.lang.String get_ProposalID() {
        return _ProposalID;
    }


    /**
     * Sets the _ProposalID value for this DocumentMasterBO.
     * 
     * @param _ProposalID
     */
    public void set_ProposalID(java.lang.String _ProposalID) {
        this._ProposalID = _ProposalID;
    }


    /**
     * Gets the deletedBy value for this DocumentMasterBO.
     * 
     * @return deletedBy
     */
    public java.lang.String getDeletedBy() {
        return deletedBy;
    }


    /**
     * Sets the deletedBy value for this DocumentMasterBO.
     * 
     * @param deletedBy
     */
    public void setDeletedBy(java.lang.String deletedBy) {
        this.deletedBy = deletedBy;
    }


    /**
     * Gets the updatedBy value for this DocumentMasterBO.
     * 
     * @return updatedBy
     */
    public java.lang.String getUpdatedBy() {
        return updatedBy;
    }


    /**
     * Sets the updatedBy value for this DocumentMasterBO.
     * 
     * @param updatedBy
     */
    public void setUpdatedBy(java.lang.String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentMasterBO)) return false;
        DocumentMasterBO other = (DocumentMasterBO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this._BusinessType==null && other.get_BusinessType()==null) || 
             (this._BusinessType!=null &&
              this._BusinessType.equals(other.get_BusinessType()))) &&
            this._DocumentID == other.get_DocumentID() &&
            ((this._DocumentName==null && other.get_DocumentName()==null) || 
             (this._DocumentName!=null &&
              this._DocumentName.equals(other.get_DocumentName()))) &&
            this._IsDeleted == other.is_IsDeleted() &&
            this._IsMandatory == other.is_IsMandatory() &&
            this._IsSubmited == other.is_IsSubmited() &&
            ((this._ProductCode==null && other.get_ProductCode()==null) || 
             (this._ProductCode!=null &&
              this._ProductCode.equals(other.get_ProductCode()))) &&
            ((this._ProposalID==null && other.get_ProposalID()==null) || 
             (this._ProposalID!=null &&
              this._ProposalID.equals(other.get_ProposalID()))) &&
            ((this.deletedBy==null && other.getDeletedBy()==null) || 
             (this.deletedBy!=null &&
              this.deletedBy.equals(other.getDeletedBy()))) &&
            ((this.updatedBy==null && other.getUpdatedBy()==null) || 
             (this.updatedBy!=null &&
              this.updatedBy.equals(other.getUpdatedBy())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (get_BusinessType() != null) {
            _hashCode += get_BusinessType().hashCode();
        }
        _hashCode += get_DocumentID();
        if (get_DocumentName() != null) {
            _hashCode += get_DocumentName().hashCode();
        }
        _hashCode += (is_IsDeleted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsMandatory() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsSubmited() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_ProductCode() != null) {
            _hashCode += get_ProductCode().hashCode();
        }
        if (get_ProposalID() != null) {
            _hashCode += get_ProposalID().hashCode();
        }
        if (getDeletedBy() != null) {
            _hashCode += getDeletedBy().hashCode();
        }
        if (getUpdatedBy() != null) {
            _hashCode += getUpdatedBy().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentMasterBO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BusinessType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_BusinessType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_DocumentID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_DocumentID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_DocumentName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_DocumentName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsDeleted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_IsDeleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsMandatory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_IsMandatory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsSubmited");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_IsSubmited"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProductCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_ProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProposalID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_ProposalID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deletedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "deletedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "updatedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
