package com.progress.lang;

import java.io.Serializable;

public class Character implements Serializable {
   private static final long serialVersionUID = -8300623724316739154L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private boolean valueModified = false;
   private String value;

   public Character() {
      this.value = "";
   }

   public Character(String var1) {
      this.value = var1;
   }

   public String getValue() {
      return this.value;
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(String var1) {
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
      if (var1 != null && var1 instanceof Character) {
         Character var2 = (Character)var1;
         if (!this.isNull() && this.getValue().equals(var2.getValue())) {
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
      return !this.isNull() ? this.value : "null";
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
