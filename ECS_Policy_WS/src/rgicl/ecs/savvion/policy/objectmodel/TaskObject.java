package rgicl.ecs.savvion.policy.objectmodel;

import java.util.Date;

public class TaskObject {
   static final long serialVersionUID = -6727544753966001411L;
   private String name = null;
   private String status = null;
   private Date completedEndDate = null;

   public Date getCompletedEndDate() {
      return this.completedEndDate;
   }

   public void setCompletedEndDate(Date completedEndDate) {
      this.completedEndDate = completedEndDate;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String toString() {
      return this.name + ":" + this.status + ":" + this.completedEndDate;
   }
}
