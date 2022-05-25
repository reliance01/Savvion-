package rgiclcmsci.cms.wsci.savvion.policy.objectmodel;

public class RequestObject {
   private String key;
   private String value;
   private String[] valueArray;

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
}
