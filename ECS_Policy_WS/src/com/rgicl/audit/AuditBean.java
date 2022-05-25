package com.rgicl.audit;

import java.io.Serializable;

public class AuditBean implements Serializable {
   private static final long serialVersionUID = -360775713L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private Long weightage_value = new Long(0L);
   private boolean weightage_valueModified = false;
   private Long id = new Long(0L);
   private boolean idModified = false;
   private String category = "";
   private boolean categoryModified = false;
   private Long sub_id = new Long(0L);
   private boolean sub_idModified = false;
   private Long total_percentage = new Long(0L);
   private boolean total_percentageModified = false;
   private String red = "";
   private boolean redModified = false;
   private String parameter = "";
   private boolean parameterModified = false;
   private String green = "";
   private boolean greenModified = false;
   private String amber = "";
   private boolean amberModified = false;
   private Long value = new Long(0L);
   private boolean valueModified = false;
   private String type = "";
   private boolean typeModified = false;

   public AuditBean() {
   }

   public AuditBean(Long _weightage_value, Long _id, String _category, Long _sub_id, Long _total_percentage, String _red, String _parameter, String _green, String _amber, Long _value, String _type) {
      this.weightage_value = _weightage_value;
      this.id = _id;
      this.category = _category;
      this.sub_id = _sub_id;
      this.total_percentage = _total_percentage;
      this.red = _red;
      this.parameter = _parameter;
      this.green = _green;
      this.amber = _amber;
      this.value = _value;
      this.type = _type;
   }

   public Long getWeightage_value() {
      return this.weightage_value;
   }

   public boolean checkIsWeightage_valueModified() {
      return this.weightage_valueModified;
   }

   public void setWeightage_value(Long _weightage_value) {
      if (this.weightage_value != null || _weightage_value != null) {
         if (this.weightage_value != null && this.weightage_value.equals(_weightage_value)) {
            if (_weightage_value == null || !_weightage_value.equals(this.weightage_value)) {
               this.weightage_value = _weightage_value;
               this.weightage_valueModified = true;
               this._modified = true;
            }
         } else {
            this.weightage_value = _weightage_value;
            this.weightage_valueModified = true;
            this._modified = true;
         }

      }
   }

   public Long getId() {
      return this.id;
   }

   public boolean checkIsIdModified() {
      return this.idModified;
   }

   public void setId(Long _id) {
      if (this.id != null || _id != null) {
         if (this.id != null && this.id.equals(_id)) {
            if (_id == null || !_id.equals(this.id)) {
               this.id = _id;
               this.idModified = true;
               this._modified = true;
            }
         } else {
            this.id = _id;
            this.idModified = true;
            this._modified = true;
         }

      }
   }

   public String getCategory() {
      return this.category;
   }

   public boolean checkIsCategoryModified() {
      return this.categoryModified;
   }

   public void setCategory(String _category) {
      if (this.category != null || _category != null) {
         if (this.category != null && this.category.equals(_category)) {
            if (_category == null || !_category.equals(this.category)) {
               this.category = _category;
               this.categoryModified = true;
               this._modified = true;
            }
         } else {
            this.category = _category;
            this.categoryModified = true;
            this._modified = true;
         }

      }
   }

   public Long getSub_id() {
      return this.sub_id;
   }

   public boolean checkIsSub_idModified() {
      return this.sub_idModified;
   }

   public void setSub_id(Long _sub_id) {
      if (this.sub_id != null || _sub_id != null) {
         if (this.sub_id != null && this.sub_id.equals(_sub_id)) {
            if (_sub_id == null || !_sub_id.equals(this.sub_id)) {
               this.sub_id = _sub_id;
               this.sub_idModified = true;
               this._modified = true;
            }
         } else {
            this.sub_id = _sub_id;
            this.sub_idModified = true;
            this._modified = true;
         }

      }
   }

   public Long getTotal_percentage() {
      return this.total_percentage;
   }

   public boolean checkIsTotal_percentageModified() {
      return this.total_percentageModified;
   }

   public void setTotal_percentage(Long _total_percentage) {
      if (this.total_percentage != null || _total_percentage != null) {
         if (this.total_percentage != null && this.total_percentage.equals(_total_percentage)) {
            if (_total_percentage == null || !_total_percentage.equals(this.total_percentage)) {
               this.total_percentage = _total_percentage;
               this.total_percentageModified = true;
               this._modified = true;
            }
         } else {
            this.total_percentage = _total_percentage;
            this.total_percentageModified = true;
            this._modified = true;
         }

      }
   }

   public String getRed() {
      return this.red;
   }

   public boolean checkIsRedModified() {
      return this.redModified;
   }

   public void setRed(String _red) {
      if (this.red != null || _red != null) {
         if (this.red != null && this.red.equals(_red)) {
            if (_red == null || !_red.equals(this.red)) {
               this.red = _red;
               this.redModified = true;
               this._modified = true;
            }
         } else {
            this.red = _red;
            this.redModified = true;
            this._modified = true;
         }

      }
   }

   public String getParameter() {
      return this.parameter;
   }

   public boolean checkIsParameterModified() {
      return this.parameterModified;
   }

   public void setParameter(String _parameter) {
      if (this.parameter != null || _parameter != null) {
         if (this.parameter != null && this.parameter.equals(_parameter)) {
            if (_parameter == null || !_parameter.equals(this.parameter)) {
               this.parameter = _parameter;
               this.parameterModified = true;
               this._modified = true;
            }
         } else {
            this.parameter = _parameter;
            this.parameterModified = true;
            this._modified = true;
         }

      }
   }

   public String getGreen() {
      return this.green;
   }

   public boolean checkIsGreenModified() {
      return this.greenModified;
   }

   public void setGreen(String _green) {
      if (this.green != null || _green != null) {
         if (this.green != null && this.green.equals(_green)) {
            if (_green == null || !_green.equals(this.green)) {
               this.green = _green;
               this.greenModified = true;
               this._modified = true;
            }
         } else {
            this.green = _green;
            this.greenModified = true;
            this._modified = true;
         }

      }
   }

   public String getAmber() {
      return this.amber;
   }

   public boolean checkIsAmberModified() {
      return this.amberModified;
   }

   public void setAmber(String _amber) {
      if (this.amber != null || _amber != null) {
         if (this.amber != null && this.amber.equals(_amber)) {
            if (_amber == null || !_amber.equals(this.amber)) {
               this.amber = _amber;
               this.amberModified = true;
               this._modified = true;
            }
         } else {
            this.amber = _amber;
            this.amberModified = true;
            this._modified = true;
         }

      }
   }

   public Long getValue() {
      return this.value;
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(Long _value) {
      if (this.value != null || _value != null) {
         if (this.value != null && this.value.equals(_value)) {
            if (_value == null || !_value.equals(this.value)) {
               this.value = _value;
               this.valueModified = true;
               this._modified = true;
            }
         } else {
            this.value = _value;
            this.valueModified = true;
            this._modified = true;
         }

      }
   }

   public String getType() {
      return this.type;
   }

   public boolean checkIsTypeModified() {
      return this.typeModified;
   }

   public void setType(String _type) {
      if (this.type != null || _type != null) {
         if (this.type != null && this.type.equals(_type)) {
            if (_type == null || !_type.equals(this.type)) {
               this.type = _type;
               this.typeModified = true;
               this._modified = true;
            }
         } else {
            this.type = _type;
            this.typeModified = true;
            this._modified = true;
         }

      }
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof AuditBean)) {
         return false;
      } else {
         AuditBean other = (AuditBean)obj;
         if (other == null) {
            return false;
         } else {
            return this == other ? true : true;
         }
      }
   }

   public int hashCode() {
      int _hashCode = 0;
      if (this.weightage_value != null) {
         _hashCode += 29 * _hashCode + this.weightage_value.hashCode();
      }

      if (this.id != null) {
         _hashCode += 29 * _hashCode + this.id.hashCode();
      }

      if (this.category != null) {
         _hashCode += 29 * _hashCode + this.category.hashCode();
      }

      if (this.sub_id != null) {
         _hashCode += 29 * _hashCode + this.sub_id.hashCode();
      }

      if (this.total_percentage != null) {
         _hashCode += 29 * _hashCode + this.total_percentage.hashCode();
      }

      if (this.red != null) {
         _hashCode += 29 * _hashCode + this.red.hashCode();
      }

      if (this.parameter != null) {
         _hashCode += 29 * _hashCode + this.parameter.hashCode();
      }

      if (this.green != null) {
         _hashCode += 29 * _hashCode + this.green.hashCode();
      }

      if (this.amber != null) {
         _hashCode += 29 * _hashCode + this.amber.hashCode();
      }

      if (this.value != null) {
         _hashCode += 29 * _hashCode + this.value.hashCode();
      }

      if (this.type != null) {
         _hashCode += 29 * _hashCode + this.type.hashCode();
      }

      return _hashCode;
   }

   public String toString() {
      StringBuilder pw = new StringBuilder(200);
      pw.append("com.rgicl.audit.AuditBean ::");
      pw.append("weightage_value=" + this.weightage_value + "\n");
      pw.append("id=" + this.id + "\n");
      pw.append("category=" + this.category + "\n");
      pw.append("sub_id=" + this.sub_id + "\n");
      pw.append("total_percentage=" + this.total_percentage + "\n");
      pw.append("red=" + this.red + "\n");
      pw.append("parameter=" + this.parameter + "\n");
      pw.append("green=" + this.green + "\n");
      pw.append("amber=" + this.amber + "\n");
      pw.append("value=" + this.value + "\n");
      pw.append("type=" + this.type + "\n");
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
      this.weightage_valueModified = false;
      this.idModified = false;
      this.categoryModified = false;
      this.sub_idModified = false;
      this.total_percentageModified = false;
      this.redModified = false;
      this.parameterModified = false;
      this.greenModified = false;
      this.amberModified = false;
      this.valueModified = false;
      this.typeModified = false;
   }
}
