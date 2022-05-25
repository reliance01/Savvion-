package rgiclcms.cms.savvion.policy.objectmodel;

public class AuthenticationDetail {
   private String authenticationId;
   private String authenticationPassword;

   public String getAuthenticationId() {
      return this.authenticationId;
   }

   public void setAuthenticationId(String authenticationId) {
      this.authenticationId = authenticationId;
   }

   public String getAuthenticationPassword() {
      return this.authenticationPassword;
   }

   public void setAuthenticationPassword(String authenticationPassword) {
      this.authenticationPassword = authenticationPassword;
   }
}
