package com.progress.lang;

import java.io.Serializable;
import java.math.BigDecimal;

public class ABLDecimal implements Serializable {
   private static final long serialVersionUID = 1982495638L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private BigDecimal value = null;
   private boolean valueModified = false;
   private Boolean unknown = new Boolean(true);
   private boolean unknownModified = false;

   public ABLDecimal() {
   }

   public ABLDecimal(BigDecimal var1, Boolean var2) {
      this.value = var1;
      this.unknown = var2;
   }

   public BigDecimal getValue() {
      return this.value;
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(BigDecimal var1) {
      if (this.value != null || var1 != null) {
         if (this.value != null && this.value.equals(var1)) {
            if (var1 == null || !var1.equals(this.value)) {
               this.value = var1;
               this.valueModified = true;
               this._modified = true;
               this.unknown = false;
            }
         } else {
            this.value = var1;
            this.valueModified = true;
            this._modified = true;
            this.unknown = false;
         }

      }
   }

   public Boolean getUnknown() {
      return this.unknown;
   }

   public boolean checkIsUnknownModified() {
      return this.unknownModified;
   }

   public void setUnknown(Boolean var1) {
      if (this.unknown != null || var1 != null) {
         if (this.unknown != null && this.unknown.equals(var1)) {
            if (var1 == null || !var1.equals(this.unknown)) {
               this.unknown = var1;
               this.unknownModified = true;
               this._modified = true;
            }
         } else {
            this.unknown = var1;
            this.unknownModified = true;
            this._modified = true;
         }

         if (this.unknown) {
            this.value = null;
         }

      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof ABLDecimal)) {
         return false;
      } else {
         ABLDecimal var2 = (ABLDecimal)var1;
         if (var2 == null) {
            return false;
         } else {
            return this == var2 ? true : true;
         }
      }
   }

   public int hashCode() {
      int var1 = 0;
      if (this.value != null) {
         var1 += 29 * var1 + this.value.hashCode();
      }

      if (this.unknown != null) {
         var1 += 29 * var1 + this.unknown.hashCode();
      }

      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(200);
      var1.append("com.progress.lang.ABLDecimal ::");
      var1.append("value=" + this.value + "\n");
      var1.append("unknown=" + this.unknown + "\n");
      return var1.toString();
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
      this.unknownModified = false;
   }
}
