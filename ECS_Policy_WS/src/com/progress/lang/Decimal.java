package com.progress.lang;

import java.io.Serializable;
import java.math.BigDecimal;

public class Decimal implements Serializable {
   private static final long serialVersionUID = 3018289312217550976L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private boolean valueModified = false;
   private BigDecimal value;

   public Decimal() {
      this.value = BigDecimal.ZERO;
   }

   public Decimal(BigDecimal var1) {
      this.value = var1;
   }

   public BigDecimal getValue() {
      return this.value;
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(BigDecimal var1) {
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

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Decimal) {
         Decimal var2 = (Decimal)var1;
         if (!this.isNull()) {
            return this.getValue().equals(var2.getValue());
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
