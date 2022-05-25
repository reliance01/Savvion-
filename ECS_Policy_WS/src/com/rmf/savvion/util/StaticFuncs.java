package com.rmf.savvion.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public final class StaticFuncs {
   private static Logger log;
   private static final String DBL_COLON_SEPARATOR = " :: ";
   private static final String COLON_SEPARATOR = " : ";
   private static final String MSG_SEPARATOR = " | ";
   private static final String BLANK_SPACE = " ";
   private static final String BEGIN = "Begin ";
   private static final String END = "End ";
   public static final int POS_ANY = 0;
   public static final int POS_BEGIN = 1;
   public static final int POS_END = 2;
   public static final int My_END = 2;

   public static Date convertToSQLDate(java.util.Date date) {
      Date sqlDate = new Date(date.getTime());
      return sqlDate;
   }

   public static Date convertToSQLDate(String date, String format) {
      SimpleDateFormat sf = null;
      if (format == null) {
         sf = new SimpleDateFormat("dd/MM/yyyy");
      } else {
         sf = new SimpleDateFormat(format);
      }

      java.util.Date dt = new java.util.Date();

      try {
         dt = sf.parse(date);
      } catch (Exception var5) {
      }

      Date sqlDate = new Date(dt.getTime());
      System.out.println(sqlDate);
      return sqlDate;
   }

   public static Vector getVector(String str) {
      Vector vector = new Vector();
      StringTokenizer tokenizer = new StringTokenizer(str, "|");

      while(tokenizer.hasMoreTokens()) {
         vector.add(tokenizer.nextElement());
      }

      return vector;
   }

   public static String strCheckNull(String str) {
      return str != null && !str.equals("") ? str : "N.A";
   }

   public static boolean isValidString(String input) {
      return input != null && !input.equals("");
   }

   public static String getValidString(String input) {
      return input == null ? "" : input;
   }

   public static void logEntryExit(String className, String methodName, boolean isEntryLog) {
      if (isEntryLog) {
         log.debug(className + "." + methodName + " ENTERING");
      } else {
         log.debug(className + "." + methodName + " EXITING");
      }

   }

   public static void log(Priority logLevel, String className, String methodName, Object msg) {
      log.log(logLevel, className + "." + methodName + " " + msg);
   }

   public static Timestamp converToTimeStamp(String date) throws Exception {
      String format = null;
      if (format == null) {
         format = "MMM dd, yyyy h:mm a";
      }

      SimpleDateFormat sdf = new SimpleDateFormat(format);
      java.util.Date dt = sdf.parse(date);
      long longDt = dt.getTime();
      Timestamp timestamp = new Timestamp(longDt);
      return timestamp;
   }

   public static Timestamp getStartTimeStamp(String date) throws Exception {
      String format = null;
      if (format == null) {
         format = "MMM dd, yyyy h:mm a";
      }

      SimpleDateFormat sdf = new SimpleDateFormat(format);
      java.util.Date dt = sdf.parse(date);
      long longDt = dt.getTime();
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(longDt);
      cal.set(10, 0);
      cal.set(12, 0);
      cal.set(13, 0);
      System.out.println(longDt);
      longDt = cal.getTimeInMillis();
      System.out.println(longDt);
      Timestamp timestamp = new Timestamp(longDt);
      return timestamp;
   }

   public static Timestamp getEndTimeStamp(String date) throws Exception {
      String format = null;
      if (format == null) {
         format = "MMM dd, yyyy h:mm a";
      }

      SimpleDateFormat sdf = new SimpleDateFormat(format);
      java.util.Date dt = sdf.parse(date);
      long longDt = dt.getTime();
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(longDt);
      cal.set(10, 23);
      cal.set(12, 59);
      cal.set(13, 59);
      System.out.println(longDt);
      longDt = cal.getTimeInMillis();
      System.out.println(longDt);
      Timestamp timestamp = new Timestamp(longDt);
      return timestamp;
   }

   public static int calculateTimeInDays(String date1, String date2) {
      String format = null;
      if (format == null) {
         format = "MM/dd/yyyy";
      }

      SimpleDateFormat sdf = new SimpleDateFormat(format);
      java.util.Date dt1 = null;
      java.util.Date dt2 = null;

      try {
         dt1 = sdf.parse(date1);
         dt2 = sdf.parse(date2);
      } catch (ParseException var13) {
         var13.printStackTrace();
      }

      long longDt1 = dt1.getTime();
      long longDt2 = dt2.getTime();
      long diff = longDt2 - longDt1;
      int tat = (int)diff / 86400000;
      return tat < 0 ? 0 : tat;
   }

   public static void log(Priority logLevel, String className, String methodName, Object msg, Throwable th) {
      log.log(logLevel, className + "." + methodName + " " + msg, th);
   }

   public static String formatDate(java.util.Date dt, String format) {
      if (format == null) {
         format = "dd/MM/yyyy";
      }

      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(dt);
   }

   public static String formatDateShort(String dtValue) {
      String format = "MMM dd, yyyy";
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      String dt = null;
      if (dtValue != null && dtValue.trim().length() != 0) {
         try {
            long value = Long.parseLong(dtValue);
            dt = sdf.format(new java.util.Date(value));
         } catch (NumberFormatException var6) {
         }
      }

      return dt;
   }

   public static String formatDateLong(String dtValue) {
      String format = "MMM dd, yyyy hh:mm aaa";
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      String dt = null;
      if (dtValue != null && dtValue.trim().length() != 0) {
         try {
            long value = Long.parseLong(dtValue);
            dt = sdf.format(new java.util.Date(value));
         } catch (NumberFormatException var6) {
         }
      }

      return dt;
   }

   public static void main(String[] args) {
      System.out.println(formatDateShort("1217529000000"));
      java.util.Date dt = new java.util.Date();
      Date dt1 = new Date(dt.getTime());
      Timestamp ts = new Timestamp(dt1.getTime());
      System.out.println(ts);
      convertToSQLDate(ts.toString(), "dd/mm/yyyy");
      System.exit(1);

      try {
         Timestamp tsStart = getStartTimeStamp("Feb 12, 2008 00:00 AM");
         Timestamp tsEnd = getEndTimeStamp("Feb 12, 2008 00:00 AM");
         System.out.println("TimeStampStrat : " + tsStart);
         System.out.println("TimeStampEnd : " + tsEnd);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      System.exit(1);
      convertToSQLDate(new java.util.Date());
   }

   public static String buildLogStatement(String className, String methodName, int pos, String... msg) {
      StringBuilder sb = new StringBuilder();
      sb.append(className);
      sb.append(" :: ");
      sb.append(methodName);
      switch(pos) {
      case 0:
         sb.append(" : ");
         break;
      case 1:
         sb.append(" :: ");
         sb.append("Begin ");
         break;
      case 2:
         sb.append(" :: ");
         sb.append("End ");
         break;
      default:
         sb.append(" ");
      }

      for(int i = 0; i < msg.length; ++i) {
         if (i > 0) {
            sb.append(" | ");
         }

         sb.append(msg[i]);
      }

      return sb.toString();
   }

   public static Long getNewDueDate(String baseDate, String days) {
      System.out.println("Base Date: " + baseDate);
      System.out.println("Days : " + days);
      Long newDueDate = null;

      try {
         if (baseDate != null && baseDate.trim().length() != 0) {
            newDueDate = new Long(baseDate);
            if (days != null && days.trim().length() != 0) {
               long duration = new Long(days) * 24L * 60L * 60L * 1000L;
               newDueDate = newDueDate + duration;
            }
         }
      } catch (NumberFormatException var5) {
         var5.printStackTrace();
      }

      System.out.println("New Due Date: " + newDueDate);
      return newDueDate;
   }

   public static String displayName(String fullName, String userName) {
      StringBuilder nameStr = new StringBuilder();
      if (userName != null && userName.trim().length() != 0) {
         if (fullName != null && fullName.trim().length() != 0) {
            nameStr.append(fullName);
         }

         nameStr.append("[");
         nameStr.append(userName);
         nameStr.append("]");
      } else {
         nameStr.append("N.A");
      }

      return nameStr.toString();
   }
}
