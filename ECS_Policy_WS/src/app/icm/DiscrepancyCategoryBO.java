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

public class DiscrepancyCategoryBO implements Serializable {
   private int _ClassID;
   private String _ClassName;
   private String _CreatedBy;
   private Calendar _CreatedDate;
   private String _DeletedBy;
   private Calendar _DeletedDate;
   private String _DiscrepancyDiscription;
   private int _DiscrepancyID;
   private String _DiscrepancyName;
   private String _UpdatedBy;
   private Calendar _UpdatedDate;
   private String _amount;
   private String _categoryName;
   private String _remark;
   private int _subCategoryID;
   private String _subCategoryName;
   private String actionable;
   private BigDecimal shortPremium;
   private String shortPremiumValue;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(DiscrepancyCategoryBO.class, true);

   public DiscrepancyCategoryBO() {
   }

   public DiscrepancyCategoryBO(int _ClassID, String _ClassName, String _CreatedBy, Calendar _CreatedDate, String _DeletedBy, Calendar _DeletedDate, String _DiscrepancyDiscription, int _DiscrepancyID, String _DiscrepancyName, String _UpdatedBy, Calendar _UpdatedDate, String _amount, String _categoryName, String _remark, int _subCategoryID, String _subCategoryName, String actionable, BigDecimal shortPremium, String shortPremiumValue) {
      this._ClassID = _ClassID;
      this._ClassName = _ClassName;
      this._CreatedBy = _CreatedBy;
      this._CreatedDate = _CreatedDate;
      this._DeletedBy = _DeletedBy;
      this._DeletedDate = _DeletedDate;
      this._DiscrepancyDiscription = _DiscrepancyDiscription;
      this._DiscrepancyID = _DiscrepancyID;
      this._DiscrepancyName = _DiscrepancyName;
      this._UpdatedBy = _UpdatedBy;
      this._UpdatedDate = _UpdatedDate;
      this._amount = _amount;
      this._categoryName = _categoryName;
      this._remark = _remark;
      this._subCategoryID = _subCategoryID;
      this._subCategoryName = _subCategoryName;
      this.actionable = actionable;
      this.shortPremium = shortPremium;
      this.shortPremiumValue = shortPremiumValue;
   }

   public int get_ClassID() {
      return this._ClassID;
   }

   public void set_ClassID(int _ClassID) {
      this._ClassID = _ClassID;
   }

   public String get_ClassName() {
      return this._ClassName;
   }

   public void set_ClassName(String _ClassName) {
      this._ClassName = _ClassName;
   }

   public String get_CreatedBy() {
      return this._CreatedBy;
   }

   public void set_CreatedBy(String _CreatedBy) {
      this._CreatedBy = _CreatedBy;
   }

   public Calendar get_CreatedDate() {
      return this._CreatedDate;
   }

   public void set_CreatedDate(Calendar _CreatedDate) {
      this._CreatedDate = _CreatedDate;
   }

   public String get_DeletedBy() {
      return this._DeletedBy;
   }

   public void set_DeletedBy(String _DeletedBy) {
      this._DeletedBy = _DeletedBy;
   }

   public Calendar get_DeletedDate() {
      return this._DeletedDate;
   }

   public void set_DeletedDate(Calendar _DeletedDate) {
      this._DeletedDate = _DeletedDate;
   }

   public String get_DiscrepancyDiscription() {
      return this._DiscrepancyDiscription;
   }

   public void set_DiscrepancyDiscription(String _DiscrepancyDiscription) {
      this._DiscrepancyDiscription = _DiscrepancyDiscription;
   }

   public int get_DiscrepancyID() {
      return this._DiscrepancyID;
   }

   public void set_DiscrepancyID(int _DiscrepancyID) {
      this._DiscrepancyID = _DiscrepancyID;
   }

   public String get_DiscrepancyName() {
      return this._DiscrepancyName;
   }

   public void set_DiscrepancyName(String _DiscrepancyName) {
      this._DiscrepancyName = _DiscrepancyName;
   }

   public String get_UpdatedBy() {
      return this._UpdatedBy;
   }

   public void set_UpdatedBy(String _UpdatedBy) {
      this._UpdatedBy = _UpdatedBy;
   }

   public Calendar get_UpdatedDate() {
      return this._UpdatedDate;
   }

   public void set_UpdatedDate(Calendar _UpdatedDate) {
      this._UpdatedDate = _UpdatedDate;
   }

   public String get_amount() {
      return this._amount;
   }

   public void set_amount(String _amount) {
      this._amount = _amount;
   }

   public String get_categoryName() {
      return this._categoryName;
   }

   public void set_categoryName(String _categoryName) {
      this._categoryName = _categoryName;
   }

   public String get_remark() {
      return this._remark;
   }

   public void set_remark(String _remark) {
      this._remark = _remark;
   }

   public int get_subCategoryID() {
      return this._subCategoryID;
   }

   public void set_subCategoryID(int _subCategoryID) {
      this._subCategoryID = _subCategoryID;
   }

   public String get_subCategoryName() {
      return this._subCategoryName;
   }

   public void set_subCategoryName(String _subCategoryName) {
      this._subCategoryName = _subCategoryName;
   }

   public String getActionable() {
      return this.actionable;
   }

   public void setActionable(String actionable) {
      this.actionable = actionable;
   }

   public BigDecimal getShortPremium() {
      return this.shortPremium;
   }

   public void setShortPremium(BigDecimal shortPremium) {
      this.shortPremium = shortPremium;
   }

   public String getShortPremiumValue() {
      return this.shortPremiumValue;
   }

   public void setShortPremiumValue(String shortPremiumValue) {
      this.shortPremiumValue = shortPremiumValue;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof DiscrepancyCategoryBO)) {
         return false;
      } else {
         DiscrepancyCategoryBO other = (DiscrepancyCategoryBO)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this._ClassID == other.get_ClassID() && (this._ClassName == null && other.get_ClassName() == null || this._ClassName != null && this._ClassName.equals(other.get_ClassName())) && (this._CreatedBy == null && other.get_CreatedBy() == null || this._CreatedBy != null && this._CreatedBy.equals(other.get_CreatedBy())) && (this._CreatedDate == null && other.get_CreatedDate() == null || this._CreatedDate != null && this._CreatedDate.equals(other.get_CreatedDate())) && (this._DeletedBy == null && other.get_DeletedBy() == null || this._DeletedBy != null && this._DeletedBy.equals(other.get_DeletedBy())) && (this._DeletedDate == null && other.get_DeletedDate() == null || this._DeletedDate != null && this._DeletedDate.equals(other.get_DeletedDate())) && (this._DiscrepancyDiscription == null && other.get_DiscrepancyDiscription() == null || this._DiscrepancyDiscription != null && this._DiscrepancyDiscription.equals(other.get_DiscrepancyDiscription())) && this._DiscrepancyID == other.get_DiscrepancyID() && (this._DiscrepancyName == null && other.get_DiscrepancyName() == null || this._DiscrepancyName != null && this._DiscrepancyName.equals(other.get_DiscrepancyName())) && (this._UpdatedBy == null && other.get_UpdatedBy() == null || this._UpdatedBy != null && this._UpdatedBy.equals(other.get_UpdatedBy())) && (this._UpdatedDate == null && other.get_UpdatedDate() == null || this._UpdatedDate != null && this._UpdatedDate.equals(other.get_UpdatedDate())) && (this._amount == null && other.get_amount() == null || this._amount != null && this._amount.equals(other.get_amount())) && (this._categoryName == null && other.get_categoryName() == null || this._categoryName != null && this._categoryName.equals(other.get_categoryName())) && (this._remark == null && other.get_remark() == null || this._remark != null && this._remark.equals(other.get_remark())) && this._subCategoryID == other.get_subCategoryID() && (this._subCategoryName == null && other.get_subCategoryName() == null || this._subCategoryName != null && this._subCategoryName.equals(other.get_subCategoryName())) && (this.actionable == null && other.getActionable() == null || this.actionable != null && this.actionable.equals(other.getActionable())) && (this.shortPremium == null && other.getShortPremium() == null || this.shortPremium != null && this.shortPremium.equals(other.getShortPremium())) && (this.shortPremiumValue == null && other.getShortPremiumValue() == null || this.shortPremiumValue != null && this.shortPremiumValue.equals(other.getShortPremiumValue()));
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
         int _hashCode = _hashCode + this.get_ClassID();
         if (this.get_ClassName() != null) {
            _hashCode += this.get_ClassName().hashCode();
         }

         if (this.get_CreatedBy() != null) {
            _hashCode += this.get_CreatedBy().hashCode();
         }

         if (this.get_CreatedDate() != null) {
            _hashCode += this.get_CreatedDate().hashCode();
         }

         if (this.get_DeletedBy() != null) {
            _hashCode += this.get_DeletedBy().hashCode();
         }

         if (this.get_DeletedDate() != null) {
            _hashCode += this.get_DeletedDate().hashCode();
         }

         if (this.get_DiscrepancyDiscription() != null) {
            _hashCode += this.get_DiscrepancyDiscription().hashCode();
         }

         _hashCode += this.get_DiscrepancyID();
         if (this.get_DiscrepancyName() != null) {
            _hashCode += this.get_DiscrepancyName().hashCode();
         }

         if (this.get_UpdatedBy() != null) {
            _hashCode += this.get_UpdatedBy().hashCode();
         }

         if (this.get_UpdatedDate() != null) {
            _hashCode += this.get_UpdatedDate().hashCode();
         }

         if (this.get_amount() != null) {
            _hashCode += this.get_amount().hashCode();
         }

         if (this.get_categoryName() != null) {
            _hashCode += this.get_categoryName().hashCode();
         }

         if (this.get_remark() != null) {
            _hashCode += this.get_remark().hashCode();
         }

         _hashCode += this.get_subCategoryID();
         if (this.get_subCategoryName() != null) {
            _hashCode += this.get_subCategoryName().hashCode();
         }

         if (this.getActionable() != null) {
            _hashCode += this.getActionable().hashCode();
         }

         if (this.getShortPremium() != null) {
            _hashCode += this.getShortPremium().hashCode();
         }

         if (this.getShortPremiumValue() != null) {
            _hashCode += this.getShortPremiumValue().hashCode();
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
      typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("_ClassID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_ClassID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ClassName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_ClassName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_CreatedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_CreatedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_CreatedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_CreatedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DeletedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_DeletedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DeletedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_DeletedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DiscrepancyDiscription");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_DiscrepancyDiscription"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DiscrepancyID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_DiscrepancyID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DiscrepancyName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_DiscrepancyName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_UpdatedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_UpdatedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_UpdatedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_UpdatedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_amount");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_amount"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_categoryName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_categoryName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_remark");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_remark"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_subCategoryID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_subCategoryID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_subCategoryName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "_subCategoryName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("actionable");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "actionable"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("shortPremium");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "shortPremium"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("shortPremiumValue");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "shortPremiumValue"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }
}
