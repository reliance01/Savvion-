package com.rmf.common.savvion.xml;

import com.rmf.common.savvion.emailer.EmailSenderPropertyReader;
import java.util.Vector;

public class EmailerWSInfo {
   private String sWSName;
   private Vector vecTemplateFiles;
   private Vector vecDataSlots;
   private String sEmailTo;
   private String sEmailCC;
   private String sEmailBCC;
   private String sSubject;

   public EmailerWSInfo() {
      EmailSenderPropertyReader.getLogger().debug("EmailerWSInfo.EmailerWSInfo(): class Initialised.");
      this.vecDataSlots = new Vector();
      this.vecTemplateFiles = new Vector();
   }

   public static void main(String[] args) {
   }

   public String getEmailBCC() {
      return this.sEmailBCC;
   }

   public void setEmailBCC(String emailBCC) {
      this.sEmailBCC = emailBCC;
   }

   public String getEmailCC() {
      return this.sEmailCC;
   }

   public void setEmailCC(String emailCC) {
      this.sEmailCC = emailCC;
   }

   public String getEmailTo() {
      return this.sEmailTo;
   }

   public void setEmailTo(String emailTo) {
      this.sEmailTo = emailTo;
   }

   public String getWSName() {
      return this.sWSName;
   }

   public void setWSName(String name) {
      this.sWSName = name;
   }

   public Vector getDataSlotList() {
      return this.vecDataSlots;
   }

   public void setDataSlotList(Vector vecDataSlots) {
      this.vecDataSlots = vecDataSlots;
   }

   public Vector getTemplateFileList() {
      return this.vecTemplateFiles;
   }

   public void setTemplateFileList(Vector vecTemplateFiles) {
      this.vecTemplateFiles = vecTemplateFiles;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("WSName=" + this.getWSName());
      sb.append("##TemplateFiles=" + this.getTemplateFileList());
      sb.append("##DataSlots=" + this.getDataSlotList());
      sb.append("TO Mail ID=" + this.getEmailTo());
      sb.append("CC Mail ID=" + this.getEmailCC());
      sb.append("BCC Mail ID=" + this.getEmailBCC());
      return sb.toString();
   }

   public String getSubject() {
      return this.sSubject;
   }

   public void setSubject(String subject) {
      this.sSubject = subject;
   }
}
