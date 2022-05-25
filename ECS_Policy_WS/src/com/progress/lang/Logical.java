package com.progress.lang;

import java.io.Serializable;

public class Logical implements Serializable {
   private static final long serialVersionUID = -8113665440189733368L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private boolean valueModified = false;
   private Boolean value;

   public Logical() {
      this.value = false;
   }

   public Logical(boolean var1) {
      this.value = new Boolean(var1);
   }

   public Logical(Boolean var1) {
      this.value = var1;
   }

   public Boolean getValue() {
      return this.value;
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(Boolean var1) {
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

   public void setBooleanValue(boolean var1) {
      this.setValue(new Boolean(var1));
   }

   public boolean getBooleanValue() {
      return this.getValue() != null ? this.getValue() : false;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Logical) {
         Logical var2 = (Logical)var1;
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
