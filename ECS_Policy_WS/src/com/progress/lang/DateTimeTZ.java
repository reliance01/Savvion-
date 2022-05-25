package com.progress.lang;

import com.progress.util.DateTimeTzHelper;
import java.io.Serializable;
import java.util.GregorianCalendar;

public class DateTimeTZ implements Serializable {
   private static final long serialVersionUID = 5826271978870857535L;
   private boolean _modified;
   private boolean _deleted;
   private boolean _new;
   private boolean valueModified;
   private GregorianCalendar value;

   public DateTimeTZ() {
      this._modified = false;
      this._deleted = false;
      this._new = true;
      this.valueModified = false;
      this.value = null;
   }

   public DateTimeTZ(GregorianCalendar var1) {
      this._modified = false;
      this._deleted = false;
      this._new = true;
      this.valueModified = false;
      this.value = var1;
   }

   public DateTimeTZ(String var1) {
      this(DateTimeTzHelper.convertOEStrTimeToGregCal(var1));
   }

   public GregorianCalendar getValue() {
      return this.value;
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(GregorianCalendar var1) {
      if (!this.isNull() || var1 != null) {
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

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof DateTimeTZ) {
         DateTimeTZ var2 = (DateTimeTZ)var1;
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
      return !this.isNull() ? DateTimeTzHelper.convertGregCalToOEStrTime(this.getValue()) : "null";
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
