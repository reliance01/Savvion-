package app.icm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DocumentMasterBO extends UtilBO implements Serializable {
   private String _BusinessType;
   private int _DocumentID;
   private String _DocumentName;
   private boolean _IsDeleted;
   private boolean _IsMandatory;
   private boolean _IsSubmited;
   private String _ProductCode;
   private String _ProposalID;
   private String deletedBy;
   private String updatedBy;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(DocumentMasterBO.class, true);

   public DocumentMasterBO() {
   }

   public DocumentMasterBO(String coStatus, String createdBy, Calendar createdDate, String _deletedBy, Calendar deletedDate, Calendar maxInstrumentDate, String modifyedBy, Calendar modifyedDate, BigDecimal totalCollection, Calendar transDate, String transactionID, String vendorType, String _BusinessType, int _DocumentID, String _DocumentName, boolean _IsDeleted, boolean _IsMandatory, boolean _IsSubmited, String _ProductCode, String _ProposalID, String deletedBy, String updatedBy) {
      super(coStatus, createdBy, createdDate, _deletedBy, deletedDate, maxInstrumentDate, modifyedBy, modifyedDate, totalCollection, transDate, transactionID, vendorType);
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

   public String get_BusinessType() {
      return this._BusinessType;
   }

   public void set_BusinessType(String _BusinessType) {
      this._BusinessType = _BusinessType;
   }

   public int get_DocumentID() {
      return this._DocumentID;
   }

   public void set_DocumentID(int _DocumentID) {
      this._DocumentID = _DocumentID;
   }

   public String get_DocumentName() {
      return this._DocumentName;
   }

   public void set_DocumentName(String _DocumentName) {
      this._DocumentName = _DocumentName;
   }

   public boolean is_IsDeleted() {
      return this._IsDeleted;
   }

   public void set_IsDeleted(boolean _IsDeleted) {
      this._IsDeleted = _IsDeleted;
   }

   public boolean is_IsMandatory() {
      return this._IsMandatory;
   }

   public void set_IsMandatory(boolean _IsMandatory) {
      this._IsMandatory = _IsMandatory;
   }

   public boolean is_IsSubmited() {
      return this._IsSubmited;
   }

   public void set_IsSubmited(boolean _IsSubmited) {
      this._IsSubmited = _IsSubmited;
   }

   public String get_ProductCode() {
      return this._ProductCode;
   }

   public void set_ProductCode(String _ProductCode) {
      this._ProductCode = _ProductCode;
   }

   public String get_ProposalID() {
      return this._ProposalID;
   }

   public void set_ProposalID(String _ProposalID) {
      this._ProposalID = _ProposalID;
   }

   public String getDeletedBy() {
      return this.deletedBy;
   }

   public void setDeletedBy(String deletedBy) {
      this.deletedBy = deletedBy;
   }

   public String getUpdatedBy() {
      return this.updatedBy;
   }

   public void setUpdatedBy(String updatedBy) {
      this.updatedBy = updatedBy;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof DocumentMasterBO)) {
         return false;
      } else {
         DocumentMasterBO other = (DocumentMasterBO)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = super.equals(obj) && (this._BusinessType == null && other.get_BusinessType() == null || this._BusinessType != null && this._BusinessType.equals(other.get_BusinessType())) && this._DocumentID == other.get_DocumentID() && (this._DocumentName == null && other.get_DocumentName() == null || this._DocumentName != null && this._DocumentName.equals(other.get_DocumentName())) && this._IsDeleted == other.is_IsDeleted() && this._IsMandatory == other.is_IsMandatory() && this._IsSubmited == other.is_IsSubmited() && (this._ProductCode == null && other.get_ProductCode() == null || this._ProductCode != null && this._ProductCode.equals(other.get_ProductCode())) && (this._ProposalID == null && other.get_ProposalID() == null || this._ProposalID != null && this._ProposalID.equals(other.get_ProposalID())) && (this.deletedBy == null && other.getDeletedBy() == null || this.deletedBy != null && this.deletedBy.equals(other.getDeletedBy())) && (this.updatedBy == null && other.getUpdatedBy() == null || this.updatedBy != null && this.updatedBy.equals(other.getUpdatedBy()));
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
         int _hashCode = super.hashCode();
         if (this.get_BusinessType() != null) {
            _hashCode += this.get_BusinessType().hashCode();
         }

         _hashCode += this.get_DocumentID();
         if (this.get_DocumentName() != null) {
            _hashCode += this.get_DocumentName().hashCode();
         }

         _hashCode += (this.is_IsDeleted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_IsMandatory() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_IsSubmited() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.get_ProductCode() != null) {
            _hashCode += this.get_ProductCode().hashCode();
         }

         if (this.get_ProposalID() != null) {
            _hashCode += this.get_ProposalID().hashCode();
         }

         if (this.getDeletedBy() != null) {
            _hashCode += this.getDeletedBy().hashCode();
         }

         if (this.getUpdatedBy() != null) {
            _hashCode += this.getUpdatedBy().hashCode();
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

   static {
      typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("_BusinessType");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_BusinessType"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DocumentID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_DocumentID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DocumentName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_DocumentName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsDeleted");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_IsDeleted"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsMandatory");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_IsMandatory"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsSubmited");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_IsSubmited"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProductCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_ProductCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProposalID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "_ProposalID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("deletedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "deletedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("updatedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "updatedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }
}
