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

public class UtilBO implements Serializable {
   private String coStatus;
   private String createdBy;
   private Calendar createdDate;
   private String deletedBy;
   private Calendar deletedDate;
   private Calendar maxInstrumentDate;
   private String modifyedBy;
   private Calendar modifyedDate;
   private BigDecimal totalCollection;
   private Calendar transDate;
   private String transactionID;
   private String vendorType;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(UtilBO.class, true);

   public UtilBO() {
   }

   public UtilBO(String coStatus, String createdBy, Calendar createdDate, String deletedBy, Calendar deletedDate, Calendar maxInstrumentDate, String modifyedBy, Calendar modifyedDate, BigDecimal totalCollection, Calendar transDate, String transactionID, String vendorType) {
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

   public String getCoStatus() {
      return this.coStatus;
   }

   public void setCoStatus(String coStatus) {
      this.coStatus = coStatus;
   }

   public String getCreatedBy() {
      return this.createdBy;
   }

   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   public Calendar getCreatedDate() {
      return this.createdDate;
   }

   public void setCreatedDate(Calendar createdDate) {
      this.createdDate = createdDate;
   }

   public String getDeletedBy() {
      return this.deletedBy;
   }

   public void setDeletedBy(String deletedBy) {
      this.deletedBy = deletedBy;
   }

   public Calendar getDeletedDate() {
      return this.deletedDate;
   }

   public void setDeletedDate(Calendar deletedDate) {
      this.deletedDate = deletedDate;
   }

   public Calendar getMaxInstrumentDate() {
      return this.maxInstrumentDate;
   }

   public void setMaxInstrumentDate(Calendar maxInstrumentDate) {
      this.maxInstrumentDate = maxInstrumentDate;
   }

   public String getModifyedBy() {
      return this.modifyedBy;
   }

   public void setModifyedBy(String modifyedBy) {
      this.modifyedBy = modifyedBy;
   }

   public Calendar getModifyedDate() {
      return this.modifyedDate;
   }

   public void setModifyedDate(Calendar modifyedDate) {
      this.modifyedDate = modifyedDate;
   }

   public BigDecimal getTotalCollection() {
      return this.totalCollection;
   }

   public void setTotalCollection(BigDecimal totalCollection) {
      this.totalCollection = totalCollection;
   }

   public Calendar getTransDate() {
      return this.transDate;
   }

   public void setTransDate(Calendar transDate) {
      this.transDate = transDate;
   }

   public String getTransactionID() {
      return this.transactionID;
   }

   public void setTransactionID(String transactionID) {
      this.transactionID = transactionID;
   }

   public String getVendorType() {
      return this.vendorType;
   }

   public void setVendorType(String vendorType) {
      this.vendorType = vendorType;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof UtilBO)) {
         return false;
      } else {
         UtilBO other = (UtilBO)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.coStatus == null && other.getCoStatus() == null || this.coStatus != null && this.coStatus.equals(other.getCoStatus())) && (this.createdBy == null && other.getCreatedBy() == null || this.createdBy != null && this.createdBy.equals(other.getCreatedBy())) && (this.createdDate == null && other.getCreatedDate() == null || this.createdDate != null && this.createdDate.equals(other.getCreatedDate())) && (this.deletedBy == null && other.getDeletedBy() == null || this.deletedBy != null && this.deletedBy.equals(other.getDeletedBy())) && (this.deletedDate == null && other.getDeletedDate() == null || this.deletedDate != null && this.deletedDate.equals(other.getDeletedDate())) && (this.maxInstrumentDate == null && other.getMaxInstrumentDate() == null || this.maxInstrumentDate != null && this.maxInstrumentDate.equals(other.getMaxInstrumentDate())) && (this.modifyedBy == null && other.getModifyedBy() == null || this.modifyedBy != null && this.modifyedBy.equals(other.getModifyedBy())) && (this.modifyedDate == null && other.getModifyedDate() == null || this.modifyedDate != null && this.modifyedDate.equals(other.getModifyedDate())) && (this.totalCollection == null && other.getTotalCollection() == null || this.totalCollection != null && this.totalCollection.equals(other.getTotalCollection())) && (this.transDate == null && other.getTransDate() == null || this.transDate != null && this.transDate.equals(other.getTransDate())) && (this.transactionID == null && other.getTransactionID() == null || this.transactionID != null && this.transactionID.equals(other.getTransactionID())) && (this.vendorType == null && other.getVendorType() == null || this.vendorType != null && this.vendorType.equals(other.getVendorType()));
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
         if (this.getCoStatus() != null) {
            _hashCode += this.getCoStatus().hashCode();
         }

         if (this.getCreatedBy() != null) {
            _hashCode += this.getCreatedBy().hashCode();
         }

         if (this.getCreatedDate() != null) {
            _hashCode += this.getCreatedDate().hashCode();
         }

         if (this.getDeletedBy() != null) {
            _hashCode += this.getDeletedBy().hashCode();
         }

         if (this.getDeletedDate() != null) {
            _hashCode += this.getDeletedDate().hashCode();
         }

         if (this.getMaxInstrumentDate() != null) {
            _hashCode += this.getMaxInstrumentDate().hashCode();
         }

         if (this.getModifyedBy() != null) {
            _hashCode += this.getModifyedBy().hashCode();
         }

         if (this.getModifyedDate() != null) {
            _hashCode += this.getModifyedDate().hashCode();
         }

         if (this.getTotalCollection() != null) {
            _hashCode += this.getTotalCollection().hashCode();
         }

         if (this.getTransDate() != null) {
            _hashCode += this.getTransDate().hashCode();
         }

         if (this.getTransactionID() != null) {
            _hashCode += this.getTransactionID().hashCode();
         }

         if (this.getVendorType() != null) {
            _hashCode += this.getVendorType().hashCode();
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
      typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "UtilBO"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("coStatus");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "CoStatus"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("createdBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "CreatedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("createdDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "CreatedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("deletedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DeletedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("deletedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DeletedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("maxInstrumentDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "MaxInstrumentDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("modifyedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "ModifyedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("modifyedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "ModifyedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("totalCollection");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "TotalCollection"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("transDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "TransDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setMinOccurs(0);
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("transactionID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "TransactionID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("vendorType");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "VendorType"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setMinOccurs(0);
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }
}
