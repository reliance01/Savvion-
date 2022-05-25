package com.rel.beans;

public class AuditParamBean {
   private String pid = null;
   private String secid = null;
   private String parameter_name = null;
   private String sub_parameter_name = null;

   public void setPid(String _pid) {
      this.pid = _pid;
   }

   public String getPiD() {
      return this.pid;
   }

   public void setSecId(String _secid) {
      this.secid = _secid;
   }

   public String getSECID() {
      return this.secid;
   }

   public void setParameterName(String _parameter_name) {
      this.parameter_name = _parameter_name;
   }

   public String getParameterName() {
      return this.parameter_name;
   }

   public void setSubParameterName(String _sub_parameter_name) {
      this.sub_parameter_name = _sub_parameter_name;
   }

   public String getSubParameterName() {
      return this.sub_parameter_name;
   }
}
