package com.rmf.savvion.bean;

public class RMFUser {
   private String userName;
   private String password;
   private String firstName;
   private String lastName;
   private String emailId;
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

   public String getDesignation() {
      return this.designation;
   }

   public void setDesignation(String designation) {
      this.designation = designation;
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

   public String getEmailId() {
      return this.emailId;
   }

   public void setEmailId(String emailId) {
      this.emailId = emailId;
   }

   public String getFirstName() {
      return this.firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
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

   public String getLastName() {
      return this.lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
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

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getRegion() {
      return this.region;
   }

   public void setRegion(String region) {
      this.region = region;
   }

   public String getUserName() {
      return this.userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getZone() {
      return this.zone;
   }

   public void setZone(String zone) {
      this.zone = zone;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("\nUserName : ");
      sb.append(this.userName);
      sb.append("\nPassword : ");
      sb.append(this.password);
      sb.append("\nUserName : ");
      sb.append(this.firstName);
      sb.append("\nUserName : ");
      sb.append(this.lastName);
      sb.append("\nUserName : ");
      sb.append(this.emailId);
      sb.append("\nUserName : ");
      sb.append(this.department);
      sb.append("\nUserName : ");
      sb.append(this.company);
      sb.append("\nUserName : ");
      sb.append(this.dob);
      sb.append("\nUserName : ");
      sb.append(this.doj);
      sb.append("\nUserName : ");
      sb.append(this.employeeType);
      sb.append("\nUserName : ");
      sb.append(this.designation);
      sb.append("\nUserName : ");
      sb.append(this.zone);
      sb.append("\nUserName : ");
      sb.append(this.location);
      sb.append("\nUserName : ");
      sb.append(this.region);
      sb.append("\nUserName : ");
      sb.append(this.mobile);
      sb.append("\nUserName : ");
      sb.append(this.keyEmployee);
      sb.append("\nUserName : ");
      sb.append(this.active);
      return sb.toString();
   }
}
