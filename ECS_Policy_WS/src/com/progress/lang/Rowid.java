package com.progress.lang;

import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;

public class Rowid extends Memptr {
   private static final long serialVersionUID = -1855000637428121930L;

   public Rowid() {
   }

   public Rowid(byte[] var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Rowid) {
         Rowid var2 = (Rowid)var1;
         return Arrays.equals(this.getValue(), var2.getValue());
      } else {
         return false;
      }
   }

   public String toString() {
      return !this.isNull() ? Base64.encodeBase64String(this.getValue()) : "null";
   }
}
