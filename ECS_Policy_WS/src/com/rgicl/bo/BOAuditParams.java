package com.rgicl.bo;

import java.io.Serializable;
import java.sql.Timestamp;

public class BOAuditParams implements Serializable {
   private static final long serialVersionUID = -956204913L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private String parameter_Name = "";
   private boolean parameter_NameModified = false;
   private String sub_Parameter_Name = "";
   private boolean sub_Parameter_NameModified = false;
   private String auditors_Remarks = "";
   private boolean auditors_RemarksModified = false;
   private String bsm_remarks = "";
   private boolean bsm_remarksModified = false;
   private Long weightage = new Long(0L);
   private boolean weightageModified = false;
   private Timestamp created_date = Timestamp.valueOf("2012-08-13 16:35:14.039");
   private boolean created_dateModified = false;

   public BOAuditParams() {
   }

   public BOAuditParams(String _parameter_Name, String _sub_Parameter_Name, String _auditors_Remarks, String _bsm_remarks, Long _weightage, Timestamp _created_date) {
      this.parameter_Name = _parameter_Name;
      this.sub_Parameter_Name = _sub_Parameter_Name;
      this.auditors_Remarks = _auditors_Remarks;
      this.bsm_remarks = _bsm_remarks;
      this.weightage = _weightage;
      this.created_date = _created_date;
   }

   public String getParameter_Name() {
      return this.parameter_Name;
   }

   public boolean checkIsParameter_NameModified() {
      return this.parameter_NameModified;
   }

   public void setParameter_Name(String _parameter_Name) {
      if (this.parameter_Name != null || _parameter_Name != null) {
         if (this.parameter_Name != null && this.parameter_Name.equals(_parameter_Name)) {
            if (_parameter_Name == null || !_parameter_Name.equals(this.parameter_Name)) {
               this.parameter_Name = _parameter_Name;
               this.parameter_NameModified = true;
               this._modified = true;
            }
         } else {
            this.parameter_Name = _parameter_Name;
            this.parameter_NameModified = true;
            this._modified = true;
         }

      }
   }

   public String getSub_Parameter_Name() {
      return this.sub_Parameter_Name;
   }

   public boolean checkIsSub_Parameter_NameModified() {
      return this.sub_Parameter_NameModified;
   }

   public void setSub_Parameter_Name(String _sub_Parameter_Name) {
      if (this.sub_Parameter_Name != null || _sub_Parameter_Name != null) {
         if (this.sub_Parameter_Name != null && this.sub_Parameter_Name.equals(_sub_Parameter_Name)) {
            if (_sub_Parameter_Name == null || !_sub_Parameter_Name.equals(this.sub_Parameter_Name)) {
               this.sub_Parameter_Name = _sub_Parameter_Name;
               this.sub_Parameter_NameModified = true;
               this._modified = true;
            }
         } else {
            this.sub_Parameter_Name = _sub_Parameter_Name;
            this.sub_Parameter_NameModified = true;
            this._modified = true;
         }

      }
   }

   public String getAuditors_Remarks() {
      return this.auditors_Remarks;
   }

   public boolean checkIsAuditors_RemarksModified() {
      return this.auditors_RemarksModified;
   }

   public void setAuditors_Remarks(String _auditors_Remarks) {
      if (this.auditors_Remarks != null || _auditors_Remarks != null) {
         if (this.auditors_Remarks != null && this.auditors_Remarks.equals(_auditors_Remarks)) {
            if (_auditors_Remarks == null || !_auditors_Remarks.equals(this.auditors_Remarks)) {
               this.auditors_Remarks = _auditors_Remarks;
               this.auditors_RemarksModified = true;
               this._modified = true;
            }
         } else {
            this.auditors_Remarks = _auditors_Remarks;
            this.auditors_RemarksModified = true;
            this._modified = true;
         }

      }
   }

   public String getBsm_remarks() {
      return this.bsm_remarks;
   }

   public boolean checkIsBsm_remarksModified() {
      return this.bsm_remarksModified;
   }

   public void setBsm_remarks(String _bsm_remarks) {
      if (this.bsm_remarks != null || _bsm_remarks != null) {
         if (this.bsm_remarks != null && this.bsm_remarks.equals(_bsm_remarks)) {
            if (_bsm_remarks == null || !_bsm_remarks.equals(this.bsm_remarks)) {
               this.bsm_remarks = _bsm_remarks;
               this.bsm_remarksModified = true;
               this._modified = true;
            }
         } else {
            this.bsm_remarks = _bsm_remarks;
            this.bsm_remarksModified = true;
            this._modified = true;
         }

      }
   }

   public Long getWeightage() {
      return this.weightage;
   }

   public boolean checkIsWeightageModified() {
      return this.weightageModified;
   }

   public void setWeightage(Long _weightage) {
      if (this.weightage != null || _weightage != null) {
         if (this.weightage != null && this.weightage.equals(_weightage)) {
            if (_weightage == null || !_weightage.equals(this.weightage)) {
               this.weightage = _weightage;
               this.weightageModified = true;
               this._modified = true;
            }
         } else {
            this.weightage = _weightage;
            this.weightageModified = true;
            this._modified = true;
         }

      }
   }

   public Timestamp getCreated_date() {
      return this.created_date;
   }

   public boolean checkIsCreated_dateModified() {
      return this.created_dateModified;
   }

   public void setCreated_date(Timestamp _created_date) {
      if (this.created_date != null || _created_date != null) {
         if (this.created_date != null && this.created_date.equals(_created_date)) {
            if (_created_date == null || !_created_date.equals(this.created_date)) {
               this.created_date = _created_date;
               this.created_dateModified = true;
               this._modified = true;
            }
         } else {
            this.created_date = _created_date;
            this.created_dateModified = true;
            this._modified = true;
         }

      }
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof BOAuditParams)) {
         return false;
      } else {
         BOAuditParams other = (BOAuditParams)obj;
         if (other == null) {
            return false;
         } else {
            return this == other ? true : true;
         }
      }
   }

   public int hashCode() {
      int _hashCode = 0;
      if (this.parameter_Name != null) {
         _hashCode += 29 * _hashCode + this.parameter_Name.hashCode();
      }

      if (this.sub_Parameter_Name != null) {
         _hashCode += 29 * _hashCode + this.sub_Parameter_Name.hashCode();
      }

      if (this.auditors_Remarks != null) {
         _hashCode += 29 * _hashCode + this.auditors_Remarks.hashCode();
      }

      if (this.bsm_remarks != null) {
         _hashCode += 29 * _hashCode + this.bsm_remarks.hashCode();
      }

      if (this.weightage != null) {
         _hashCode += 29 * _hashCode + this.weightage.hashCode();
      }

      if (this.created_date != null) {
         _hashCode += 29 * _hashCode + this.created_date.hashCode();
      }

      return _hashCode;
   }

   public String toString() {
      StringBuilder pw = new StringBuilder(200);
      pw.append("com.rgicl.bo.BOAuditParams ::");
      pw.append("parameter_Name=" + this.parameter_Name + "\n");
      pw.append("sub_Parameter_Name=" + this.sub_Parameter_Name + "\n");
      pw.append("auditors_Remarks=" + this.auditors_Remarks + "\n");
      pw.append("bsm_remarks=" + this.bsm_remarks + "\n");
      pw.append("weightage=" + this.weightage + "\n");
      pw.append("created_date=" + this.created_date + "\n");
      return pw.toString();
   }

   public boolean checkIsModified() {
      return this._modified;
   }

   public boolean checkIsDeleted() {
      return this._deleted;
   }

   public boolean checkIsNew() {
      return this._new;
   }

   public void markDeleted() {
      this._deleted = true;
   }

   public void markNew() {
      this._new = true;
   }

   public void markModified() {
      this._modified = true;
   }

   public void resetFlags() {
      this._modified = false;
      this._new = false;
      this._deleted = false;
      this.parameter_NameModified = false;
      this.sub_Parameter_NameModified = false;
      this.auditors_RemarksModified = false;
      this.bsm_remarksModified = false;
      this.weightageModified = false;
      this.created_dateModified = false;
   }
}
