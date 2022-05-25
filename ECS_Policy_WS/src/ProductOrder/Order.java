package ProductOrder;

import java.io.Serializable;

public class Order implements Serializable {
   private String customer_id = "";
   private String order_date = "";
   private String order_id = "";
   private String service_id = "";

   public void setCustomerID(String var1) {
      this.customer_id = var1;
   }

   public String getCustomerID() {
      return this.customer_id;
   }

   public void setOrderID(String var1) {
      this.order_id = var1;
   }

   public String getOrderID() {
      return this.order_id;
   }

   public void setOrderDate(String var1) {
      this.order_date = var1;
   }

   public String getOrderDate() {
      return this.order_date;
   }

   public void setServiceID(String var1) {
      this.service_id = var1;
   }

   public String getServiceID() {
      return this.service_id;
   }
}
