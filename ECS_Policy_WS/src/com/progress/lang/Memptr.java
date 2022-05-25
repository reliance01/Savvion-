package com.progress.lang;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;

public class Memptr implements Serializable {
   private static final long serialVersionUID = -7089743714516634395L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private boolean valueModified = false;
   private byte[] value;

   public Memptr() {
      this.value = new byte[0];
   }

   public Memptr(byte[] var1) {
      this.updateValue(var1);
   }

   public byte[] getValue() {
      return this.isNull() ? null : (byte[])this.value.clone();
   }

   public boolean checkIsValueModified() {
      return this.valueModified;
   }

   public void setValue(byte[] var1) {
      if (!this.isNull() || var1 != null) {
         if (!this.isNull() && !Arrays.equals(this.getValue(), var1)) {
            this.updateValue(var1);
            this.valueModified = true;
            this._modified = true;
         } else if (var1 != null && !Arrays.equals(this.getValue(), var1)) {
            this.updateValue(var1);
            this.valueModified = true;
            this._modified = true;
         }

      }
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Memptr) {
         Memptr var2 = (Memptr)var1;
         return Arrays.equals(this.getValue(), var2.getValue());
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
      return !this.isNull() ? Base64.encodeBase64String(this.value) : "null";
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

   private void updateValue(byte[] var1) {
      if (var1 != null) {
         this.value = (byte[])var1.clone();
      } else {
         this.value = null;
      }

   }
}
