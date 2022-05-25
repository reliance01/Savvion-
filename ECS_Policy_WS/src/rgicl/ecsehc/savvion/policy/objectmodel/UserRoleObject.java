package rgicl.ecsehc.savvion.policy.objectmodel;

public class UserRoleObject {
   static final long serialVersionUID = -6727544753963912451L;
   private String role = null;
   private String location = null;
   private String LOB = null;

   public String getLOB() {
      return this.LOB;
   }

   public void setLOB(String lob) {
      this.LOB = lob;
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getRole() {
      return this.role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public String toString() {
      return this.role + ":" + this.location + this.LOB;
   }
}
