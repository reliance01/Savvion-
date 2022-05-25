package ProductOrder;

import java.io.Serializable;

public class CustomerDB implements Serializable {
   private static final long serialVersionUID = 7565245702490829100L;
   public Long id;
   public String first_name = "";
   public String last_name = "";
   public String address = "";
   public String pcode = "";
   public String city = "";

   public void setID(Long var1) {
      this.id = var1;
   }

   public Long getID() {
      return this.id;
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
