package com.progress.lang;

import java.io.Serializable;

public class Int64 implements Serializable {
   private static final long serialVersionUID = -4332005574767400105L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private boolean valueModified = false;
   private Long value;

   public Int64() {
      this.value = new Long(0L);
   }

   public Int64(long var1) {
      this.value = new Long(var1);
   }

   public Int64(Long var1) {
      this.value = var1;
   }

   public Long getValue() {
      return this.value;
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(Long var1) {
      if (this.getValue() != null || var1 != null) {
         if (!this.isNull() && this.getValue().equals(var1)) {
            if (var1 == null || !var1.equals(this.getValue())) {
               this.value = var1;
               this.valueModified = true;
               this._modified = true;
            }
         } else {
            this.value = var1;
            this.valueModified = true;
            this._modified = true;
         }

      }
   }

   public void setLongValue(long var1) {
      this.setValue(new Long(var1));
   }

   public long getLongValue() {
      return this.getValue() != null ? this.getValue() : 0L;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Int64) {
         Int64 var2 = (Int64)var1;
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
         var1 = this.getValue().hashCode();
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
