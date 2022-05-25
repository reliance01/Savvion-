package com.rmf.common.savvion.utils;

import com.savvion.sbm.util.PService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

public class StaticFuncs {
   public static int LEVEL_DAYS = 0;
   public static int LEVEL_HOURS = 1;
   public static int LEVEL_MINUTES = 2;
   public static int LEVEL_SECONDS = 3;
   public static int LEVEL_MILLI_SECONDS = 4;

   public static long convertToMilliSeconds(int num, boolean isDays) {
      if (num <= 0) {
         return 0L;
      } else {
         long milliSeconds;
         if (isDays) {
            milliSeconds = (long)(num * 24 * 60 * 60 * 1000);
         } else {
            milliSeconds = (long)(num * 60 * 1000);
         }

         return milliSeconds;
      }
   }

   public static String[] convertVectorToArray(Vector input) {
      if (input == null) {
         return null;
      } else {
         String[] output = new String[input.size()];

         for(int i = 0; i < input.size(); ++i) {
            output[i] = (String)input.get(i);
         }

         return output;
      }
   }

   public static Vector StringtoList(String commaSepList) {
      Vector vec = new Vector();
      StringTokenizer st = new StringTokenizer(commaSepList, ",");

      while(st.hasMoreTokens()) {
         vec.add(st.nextToken());
      }

      return vec;
   }

   public static Vector StringtoListdecrypted(String commaSepList) {
      Vector vec = new Vector();
      StringTokenizer st = new StringTokenizer(commaSepList, ",");

      while(st.hasMoreTokens()) {
         vec.add(PService.self().decrypt(st.nextToken()));
      }

      return vec;
   }

   public static String displayNullAs(Object o, String value) {
      return o == null ? value : "" + o;
   }

   public static String displayBooleanAs(boolean b, String trueValue, String falseValue) {
      return b ? trueValue : falseValue;
   }

   public static String getCurrentFormattedDate(String dateFormat) throws Exception {
      return (new SimpleDateFormat(dateFormat)).format(new Date());
   }

   public static String getFormattedDate(String dateFormat, Date date) throws Exception {
      return (new SimpleDateFormat(dateFormat)).format(date);
   }

   public static String getFormattedDate(String dateFormat, long date) throws Exception {
      return (new SimpleDateFormat(dateFormat)).format(new Date(date));
   }

   public static String getTimeStringValue(long lTime, boolean isMilliSeconds, int level) {
      if (lTime <= 0L) {
         return "0";
      } else {
         long millisec = 0L;
         if (!isMilliSeconds) {
            lTime *= 1000L;
         }

         long days = lTime / 86400000L;
         long hours = lTime / 3600000L - days * 24L;
         long minutes = lTime / 60000L - (hours * 60L + days * 24L * 60L);
         long seconds = lTime / 1000L - (minutes * 60L + hours * 60L * 60L + days * 24L * 60L * 60L);
         if (isMilliSeconds) {
            millisec = lTime - (seconds + minutes * 60L + hours * 60L * 60L + days * 24L * 60L * 60L) * 1000L;
         }

         String time = "";
         if (days > 0L) {
            if (days > 1L) {
               time = time + days + " days";
            } else {
               time = time + days + " day";
            }
         }

         if (hours > 0L && level > 0) {
            if (hours > 1L) {
               time = time + (time.length() == 0 ? hours + " hours" : ", " + hours + " hours");
            } else {
               time = time + (time.length() == 0 ? hours + " hour" : ", " + hours + " hour");
            }
         }

         if (minutes > 0L && level > 1) {
            if (minutes > 1L) {
               time = time + (time.length() == 0 ? minutes + " minutes" : ", " + minutes + " minutes");
            } else {
               time = time + (time.length() == 0 ? minutes + " minute" : ", " + minutes + " minute");
            }
         }

         if (seconds > 0L && level > 2) {
            if (seconds > 1L) {
               time = time + (time.length() == 0 ? seconds + " seconds" : ", " + seconds + " seconds");
            } else {
               time = time + (time.length() == 0 ? seconds + " second" : ", " + seconds + " second");
            }
         }

         if (millisec > 0L && level > 3) {
            if (millisec > 1L) {
               time = time + (time.length() == 0 ? millisec + " mlilliseconds" : ", " + millisec + " mlilliseconds");
            } else {
               time = time + (time.length() == 0 ? millisec + " mlillisecond" : ", " + millisec + " mlillisecond");
            }
         }

         return time;
      }
   }
}
