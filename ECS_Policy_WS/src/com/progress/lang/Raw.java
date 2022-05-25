package com.progress.lang;

import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;

public class Raw extends Memptr {
   private static final long serialVersionUID = -7086916124006396260L;

   public Raw() {
   }

   public Raw(byte[] var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Raw) {
         Raw var2 = (Raw)var1;
         return Arrays.equals(this.getValue(), var2.getValue());
      } else {
         return false;
      }
   }

   public String toString() {
      return !this.isNull() ? Base64.encodeBase64String(this.getValue()) : "null";
   }
}
