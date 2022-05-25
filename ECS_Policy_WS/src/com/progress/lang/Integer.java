package com.progress.lang;

import java.io.Serializable;

public class Integer implements Serializable {
   private static final long serialVersionUID = -6312793207222244143L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private boolean valueModified = false;
   private java.lang.Integer value;

   public Integer() {
      this.value = 0;
   }

   public Integer(int var1) {
      this.value = new java.lang.Integer(var1);
   }

   public Integer(java.lang.Integer var1) {
      this.value = var1;
   }

   public java.lang.Integer getValue() {
      return this.value;
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(java.lang.Integer var1) {
      if (!this.isNull() || var1 != null) {
         if (!this.isNull() && !this.getValue().equals(var1)) {
            this.value = var1;
            this.valueModified = true;
            this._modified = true;
         } else if (var1 != null && !var1.equals(this.getValue())) {
            this.value = var1;
            this.valueModified = true;
            this._modified = true;
         }

      }
   }

   public void setIntValue(int var1) {
      this.setValue(new java.lang.Integer(var1));
   }

   public int getIntValue() {
      return this.getValue() != null ? this.getValue() : 0;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Integer) {
         Integer var2 = (Integer)var1;
         if (this.getValue() != null && this.getValue().equals(var2.getValue())) {
            return true;
         } else {
            return this.isNull() && var2.isNull();
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = 0;
      if (this.getValue() != null) {
         var1 += this.getValue().hashCode();
      }

      return var1;
   }

   public String toString() {
      return !this.isNull() ? this.value.toString() : "null";
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
      this.valueModified = false;
   }

   public boolean isNull() {
      return this.value == null;
   }
}
