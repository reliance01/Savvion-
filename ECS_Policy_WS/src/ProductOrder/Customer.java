package ProductOrder;

import java.io.Serializable;

public class Customer implements Serializable {
   private String customer_id = "";
   private String first_name = "";
   private String last_name = "";
   private String address = "";
   private String pcode = "";
   private String city = "";

   public void setCustomerID(String var1) {
      this.customer_id = var1;
   }

   public String getCustomerID() {
      return this.customer_id;
   }

   public void setFirstName(String var1) {
      this.first_name = var1;
   }

   public String getFirstName() {
      return this.first_name;
   }

   public void setLastName(String var1) {
      this.last_name = var1;
   }

   public String getLastName() {
      return this.last_name;
   }

   public void setAddress(String var1) {
      this.address = var1;
   }

   public String getAddress() {
      return this.address;
   }

   public void setPCode(String var1) {
      this.pcode = var1;
   }

   public String getPCode() {
      return this.pcode;
   }

   public void setCity(String var1) {
      this.city = var1;
   }

   public String getCity() {
      return this.city;
   }
}
