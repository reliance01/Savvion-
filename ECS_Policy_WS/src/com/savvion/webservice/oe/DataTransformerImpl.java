package com.savvion.webservice.oe;

import com.progress.util.DateTimeTzHelper;
import com.savvion.sbm.processgraph.model.datafield.DataField;
import com.savvion.sbm.processgraph.model.datafield.ObjectField;
import com.savvion.webservice.bizlogic.IDataTransformer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import org.apache.commons.codec.binary.Base64;

public class DataTransformerImpl implements IDataTransformer {
   private static final String BYTE_ARRAY = "byte[]";
   private static final String JAVA_UTIL_GREGORIAN_CALENDAR = "java.util.GregorianCalendar";
   private static final String JAVA_LANG_BOOLEAN = "java.lang.Boolean";
   private static final String JAVA_MATH_BIG_DECIMAL = "java.math.BigDecimal";
   private static final String JAVA_LANG_STRING = "java.lang.String";
   private static final String JAVA_LANG_LONG = "java.lang.Long";
   private static final String JAVA_LANG_INTEGER = "java.lang.Integer";
   private static final String ABLDS_VALUE_FIELD = "value";
   private static final String ABLDS_VALUE_SETTER = "setValue";

   public Object transform(String var1, Object var2, DataField var3) {
      if (this.isNullOrEmpty(var1)) {
         throw new RuntimeException("Invalid dataslot name : " + var1);
      } else {
         return var3.isObject() ? this.transformObject(var1, var2, (ObjectField)var3) : null;
      }
   }

   private boolean isNull(Object var1) {
      return var1 == null;
   }

   private boolean isNullOrEmpty(String var1) {
      if (this.isNull(var1)) {
         return true;
      } else {
         return var1.trim().length() == 0;
      }
   }

   private Object transformObject(String var1, Object var2, ObjectField var3) {
      String var4 = var3.getDefaultValue();
      if (var4 != null && var4.trim().length() != 0) {
         try {
            Class var5 = Class.forName(var4);
            Field var6 = this.getDeclaredField(var5);
            Object var7 = this.convertValue(var6.getType(), var2);
            Object var8 = var5.newInstance();
            Class[] var9 = new Class[]{var6.getType()};
            Object[] var10 = new Object[]{var7};
            Method var11 = var5.getMethod("setValue", var9);
            var11.invoke(var8, var10);
            return var8;
         } catch (Throwable var12) {
            throw new RuntimeException("Cannot set value " + var2 + " for dataslot " + var1 + ", type " + var3.toString(), var12);
         }
      } else {
         throw new RuntimeException("Cannot retrive class for dataslot " + var1);
      }
   }

   private Field getDeclaredField(Class var1) throws NoSuchFieldException, SecurityException {
      Field var2 = null;

      try {
         var2 = var1.getDeclaredField("value");
         return var2;
      } catch (NoSuchFieldException var4) {
         var1 = var1.getSuperclass();
         if (var1.toString().equals("java.lang.Class")) {
            throw var4;
         } else {
            return this.getDeclaredField(var1);
         }
      }
   }

   private Object convertValue(Class var1, Object var2) {
      Object var3 = var2;
      if (var2 == null) {
         return null;
      } else {
         String var4 = null;
         if (var2 instanceof String) {
            var4 = (String)var2;
            if (var4.trim().equalsIgnoreCase("null")) {
               return null;
            }
         }

         String var5 = var1.toString();
         if (var5.indexOf("java.lang.Integer") != -1) {
            if (!this.isNull(var4)) {
               var3 = Integer.valueOf(var4);
            }
         } else if (var5.indexOf("java.lang.Long") != -1) {
            var4 = var2.toString().trim();
            if (var4.endsWith("l") || var4.endsWith("L")) {
               var4 = var4.substring(0, var4.length() - 1);
            }

            var3 = Long.valueOf(var4);
         } else if (var5.indexOf("java.lang.String") != -1) {
            if (!this.isNull(var4)) {
               var3 = var4;
            }
         } else if (var5.indexOf("java.math.BigDecimal") != -1) {
            if (!this.isNull(var4)) {
               var3 = new BigDecimal(var4);
            }
         } else if (var5.indexOf("java.lang.Boolean") != -1) {
            if (!this.isNull(var4)) {
               var3 = Boolean.parseBoolean(var4);
            }
         } else if (var5.indexOf("java.util.GregorianCalendar") != -1) {
            if (!this.isNull(var4)) {
               var3 = DateTimeTzHelper.convertOEStrTimeToGregCal(var4);
            }
         } else if (var1.getCanonicalName().indexOf("byte[]") != -1 && !this.isNull(var4)) {
            var3 = Base64.decodeBase64(var4);
         }

         return var3;
      }
   }
}
