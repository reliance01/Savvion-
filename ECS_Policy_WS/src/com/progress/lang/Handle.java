package com.progress.lang;

public class Handle extends Int64 {
   private static final long serialVersionUID = -9057175381212297486L;

   public Handle() {
   }

   public Handle(Long var1) {
      super(var1);
   }

   public Handle(long var1) {
      super(var1);
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Handle) {
         Handle var2 = (Handle)var1;
         if (this.getValue() != null && this.getValue().equals(var2.getValue())) {
            return true;
         } else {
            return this.isNull() && var2.isNull();
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return !this.isNull() ? this.getValue().toString() : "null";
   }
}
