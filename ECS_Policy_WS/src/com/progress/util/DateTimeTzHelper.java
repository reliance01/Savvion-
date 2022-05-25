package com.progress.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class DateTimeTzHelper {
   private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

   public static GregorianCalendar convertOEStrTimeToGregCal(String var0) {
      GregorianCalendar var1 = null;
      SimpleTimeZone var2 = null;
      SimpleDateFormat var4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

      String var6;
      try {
         int var3 = getTimeZone(var0);
         if (var3 != -1) {
            String[] var5 = TimeZone.getAvailableIDs(var3);
            if (var5 == null || var5.length == 0) {
               throw new ParseException("Unable to construct a GregorianCalendar with specified time zone", -1);
            }

            var2 = new SimpleTimeZone(var3, var5[0]);
            var4.setTimeZone(var2);
            var1 = new GregorianCalendar(var2);
            var0 = var0.trim();
            var6 = var0.substring(0, 26) + var0.substring(27);

            try {
               var1.setTime(var4.parse(var6));
            } catch (ParseException var8) {
               throw new ParseException("", -1);
            }
         }

         return var1;
      } catch (ParseException var9) {
         var1 = null;
         if (var9.getMessage().length() == 0) {
            var6 = "Invalid date format for DateTimeTZ \"" + var0 + "\". Expected format: \"yyyy-MM-dd'T'HH:mm:ss.SSS('+' | '-')zz:zz\"";
         } else {
            var6 = var9.getMessage();
         }

         throw new RuntimeException(var6, var9);
      } catch (Throwable var10) {
         throw new RuntimeException("Error in converting OE DateTime TimeZone to Calendar object", var10);
      }
   }

   private static int getTimeZone(String var0) throws ParseException {
      int var1 = -1;
      if (var0 != null && var0.trim().length() == 29) {
         var0 = var0.trim();
         String var2 = var0.substring(23);
         if ((var2.charAt(0) == '+' || var2.charAt(0) == '-') && var2.length() == 6 && var2.charAt(3) == ':') {
            String[] var3 = var2.substring(1).split(":");
            Integer var4 = new Integer(var3[0]);
            Integer var5 = new Integer(var3[1]);
            var1 = var4 * 60 * 60 * 1000;
            var1 += var5 * 60 * 1000;
            if (var2.charAt(0) == '-') {
               var1 *= -1;
            }
         }
      }

      if (var1 == -1) {
         throw new ParseException("", -1);
      } else {
         return var1;
      }
   }

   public static String convertGregCalToOEStrTime(GregorianCalendar var0) {
      if (var0 == null) {
         return "";
      } else {
         SimpleDateFormat var1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
         var1.setTimeZone(var0.getTimeZone());
         String var2 = var1.format(var0.getTime());
         String var3 = var2.substring(0, 26) + ":" + var2.substring(26);
         return var3;
      }
   }
}
