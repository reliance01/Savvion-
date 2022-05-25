package com.rmf.common.savvion.xml;

import com.rmf.common.savvion.emailer.EmailSenderPropertyReader;
import java.util.Hashtable;

public class EmailerPTInfo {
   private String sPTName;
   private Hashtable htWSInformation;

   public EmailerPTInfo() {
      EmailSenderPropertyReader.getLogger().debug("EmailerPTInfo.EmailerPTInfo(): class Initialised.");
      this.htWSInformation = new Hashtable();
   }

   public static void main(String[] args) {
   }

   public Hashtable getWSInformation() {
      return this.htWSInformation;
   }

   public void setWSInformation(Hashtable htWSInformation) {
      this.htWSInformation = htWSInformation;
   }

   public void addWSInformation(String sWsName, EmailerWSInfo wsInfo) {
      this.htWSInformation.put(sWsName, wsInfo);
   }

   public String getPTName() {
      return this.sPTName;
   }

   public void setPTName(String name) {
      this.sPTName = name;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("PTName=" + this.getPTName());
      sb.append("##WSInfo=" + this.getWSInformation());
      return sb.toString();
   }
}
