package com.rel.beans;

public class PageUtil {
   private static PageUtil self = null;

   private PageUtil() {
   }

   public static PageUtil self() {
      if (self == null) {
         self = new PageUtil();
      }

      return self;
   }

   public boolean isNullorEmpty(String str) {
      return str == null || "null".equals(str) || "".equals(str);
   }

   public boolean isNullorAll(String str) {
      return str == null || "null".equals(str) || "all".equals(str);
   }
}
