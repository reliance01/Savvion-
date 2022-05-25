package com.rmf.savvion.usermanagement;

public class RMFUserDetails {
   private String department;
   private String company;
   private String dob;
   private String doj;
   private String employeeType;
   private String designation;
   private String zone;
   private String location;
   private String region;
   private String mobile;
   private String keyEmployee;
   private String active;

   public String getActive() {
      return this.active;
   }

   public void setActive(String active) {
      this.active = active;
   }

   public String getDesignation() {
      return this.designation;
   }

   public void setDesignation(String designation) {
      this.designation = designation;
   }

   public String getEmployeeType() {
      return this.employeeType;
   }

   public void setEmployeeType(String employeeType) {
      this.employeeType = employeeType;
   }

   public String getKeyEmployee() {
      return this.keyEmployee;
   }

   public void setKeyEmployee(String keyEmployee) {
      this.keyEmployee = keyEmployee;
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getMobile() {
      return this.mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getRegion() {
      return this.region;
   }

   public void setRegion(String region) {
      this.region = region;
   }

   public String getZone() {
      return this.zone;
   }

   public void setZone(String zone) {
      this.zone = zone;
   }

   public String getCompany() {
      return this.company;
   }

   public void setCompany(String company) {
      this.company = company;
   }

   public String getDepartment() {
      return this.department;
   }

   public void setDepartment(String department) {
      this.department = department;
   }

   public String getDob() {
      return this.dob;
   }

   public void setDob(String dob) {
      this.dob = dob;
   }

   public String getDoj() {
      return this.doj;
   }

   public void setDoj(String doj) {
      this.doj = doj;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("dob : ");
      sb.append(this.dob);
      sb.append(", ");
      sb.append("doj : ");
      sb.append(this.doj);
      sb.append(", ");
      sb.append("department : ");
      sb.append(this.department);
      sb.append(", ");
      sb.append("company : ");
      sb.append(this.company);
      return sb.toString();
   }
}
