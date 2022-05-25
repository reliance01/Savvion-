package org.rgicl.motor.util;

public class RequestObject {
   static final long serialVersionUID = -6727544753963908867L;
   private String key = null;
   private String value = null;
   private String[] valueArray = null;

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String[] getValueArray() {
      return this.valueArray;
   }

   public void setValueArray(String[] valueArray) {
      this.valueArray = valueArray;
   }

   public String toString() {
      return this.key + ":" + this.value + ":" + this.valueArray + ":";
   }
}
