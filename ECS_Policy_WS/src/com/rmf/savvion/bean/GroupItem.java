package com.rmf.savvion.bean;

public class GroupItem {
   private String name;
   private String description;
   private String calendarName;

   public String getCalendarName() {
      return this.calendarName;
   }

   public void setCalendarName(String calendarName) {
      this.calendarName = calendarName;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDescription() {
      return this.description;
   }
}
