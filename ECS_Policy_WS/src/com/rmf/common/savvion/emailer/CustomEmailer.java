package com.rmf.common.savvion.emailer;

import com.rmf.common.savvion.utils.StaticFuncs;
import com.rmf.common.savvion.xml.EmailerPTInfo;
import com.rmf.common.savvion.xml.EmailerWSInfo;
import com.rmf.common.savvion.xml.XMLParser;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.util.BizLogicClientException;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.SBMHomeFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CustomEmailer {
   private static final String CLASS_NAME = "CustomEmailer";
   private Logger logger = EmailSenderPropertyReader.getLogger();
   private Hashtable hmDataSlot;
   private long PID;
   private String sWSName;
   private String sPTName;
   private StringBuffer sbBody;
   private String sSubject;
   private String sMailTo;
   private String sMailCC;
   private String sMailBCC;
   private BLServer mblserver;
   private String sPerformer;
   private long timeElapsed;
   private int timeElapsedLevel = 0;
   private ProcessInstance processinstance;
   private Session mblsession;
   private HashMap customBookMarks;

   public CustomEmailer() {
      this.logger.debug("CustomEmailer.CustomEmailer(): Logger initialized");
   }

   public boolean init(String sProcessTemplateName, long PID, String workstepName, int iTemplateNumber, HashMap customBookmarks) {
      this.customBookMarks = customBookmarks;
      return this.init(sProcessTemplateName, PID, workstepName, iTemplateNumber);
   }

   public boolean init(String sProcessTemplateName, long PID, String workstepName, int iTemplateNumber) {
      boolean initSuccessful = false;
      this.logger.debug("CustomEmailer.init(): caller is " + workstepName + "\tProcess Template is " + sProcessTemplateName);
      this.logger.debug("CustomEmailer.init() : PID = " + PID);
      this.sPTName = sProcessTemplateName;
      this.PID = PID;
      this.sWSName = workstepName;
      XMLParser xmlParser = XMLParser.getInstance();
      this.logger.debug("File location " + EmailSenderPropertyReader.getXmlFileLocation());
      if (!xmlParser.isXMLread()) {
         xmlParser.read(EmailSenderPropertyReader.getXmlFileLocation());
      }

      EmailerPTInfo ptInfo = xmlParser.getPTInfo(this.sPTName);
      if (ptInfo == null) {
         this.logger.error("CustomEmailer.init() : Cannot find Information about Process Template from the XML file. PT Name is" + this.sPTName);
         return initSuccessful;
      } else {
         this.logger.debug(ptInfo.getWSInformation().get(this.sWSName));
         Vector vecDataslotList = ((EmailerWSInfo)ptInfo.getWSInformation().get(this.sWSName)).getDataSlotList();
         if (PID != 0L) {
            this.processinstance = this.getProcessInstance(PID);
         }

         if (this.processinstance != null) {
            HashMap map;
            try {
               this.hmDataSlot = new Hashtable();
               map = (HashMap)this.processinstance.getDataSlotValue(StaticFuncs.convertVectorToArray(vecDataslotList));
               Set set = map.keySet();
               Iterator ite = set.iterator();

               while(true) {
                  if (!ite.hasNext()) {
                     this.logger.debug("CustomEmailer.init() : DataSlot Values are " + this.hmDataSlot);
                     break;
                  }

                  String key = (String)ite.next();
                  Object obj1 = map.get(key);
                  if (obj1 == null) {
                     this.hmDataSlot.put(key, EmailSenderPropertyReader.getNoValueSubstitute());
                  } else {
                     this.hmDataSlot.put(key, obj1);
                  }
               }
            } catch (RemoteException var17) {
               this.logger.error("CustomEmailer.init(): Caught RemoteException while getting Data slot values" + var17);
               return initSuccessful;
            } catch (BizLogicClientException var18) {
               this.logger.error("CustomEmailer.init(): Caught BizLogicClientException while getting Data slot values" + var18);
               return initSuccessful;
            }

            map = null;

            WorkStepInstance wsInstance;
            try {
               wsInstance = this.processinstance.getWorkStepInstance(this.sWSName);
            } catch (RemoteException var15) {
               this.logger.error("CustomEmailer.init(): Caught RemoteException while getting Data slot values" + var15);
               return initSuccessful;
            } catch (BizLogicClientException var16) {
               this.logger.error("CustomEmailer.init(): Caught BizLogicClientException while getting Data slot values" + var16);
               return initSuccessful;
            }

            if (wsInstance != null) {
               long WIStartTime = wsInstance.getActivationTime();
               this.timeElapsed = Calendar.getInstance().getTimeInMillis() - WIStartTime;
               this.sPerformer = wsInstance.getActualPerformer();
               this.logger.debug("CustomEmailer.init(): StartTime = " + new Date(WIStartTime));
               this.logger.debug("CustomEmailer.init(): Performer = " + this.sPerformer);
               this.logger.debug("CustomEmailer.init(): Time Elapsed = " + StaticFuncs.getTimeStringValue(this.timeElapsed, true, this.timeElapsedLevel));
            }
         } else {
            this.logger.debug("CustomEmailer.init() : PI object is NULL ");
         }

         this.createMailBody(iTemplateNumber, ptInfo);
         if (PID != 0L) {
            this.createSubjectLine(ptInfo);
            this.createMailTo(ptInfo);
            this.createMailCC(ptInfo);
            this.createMailBCC(ptInfo);
         }

         this.logger.debug("CustomEmailer.init() : ENDS");
         this.closeSession();
         return !initSuccessful;
      }
   }

   private void createSubjectLine(EmailerPTInfo ptInfo) {
      String sSubjectLine = ((EmailerWSInfo)ptInfo.getWSInformation().get(this.sWSName)).getSubject();
      this.logger.debug("CustomEmailer.createSubjectLine() : Original SUBJECT LINE = " + sSubjectLine);
      if (sSubjectLine.indexOf("${") != -1) {
         this.logger.debug("CustomEmailer.createSubjectLine() : Entering IF block for DS handling...");

         while(sSubjectLine.indexOf("${") != -1) {
            int start = sSubjectLine.indexOf("${");
            int end = sSubjectLine.indexOf("}");
            String sDSIdentifier = sSubjectLine.substring(start, end + 1);
            String sDSname = sSubjectLine.substring(start + 2, end).trim();
            this.logger.debug("CustomEmailer.createSubjectLine() : Complete Identifier = " + sDSIdentifier);
            this.logger.debug("CustomEmailer.createSubjectLine() : Data Slot Name = " + sDSname);
            sSubjectLine = this.handleDynamicValues(sDSname, sSubjectLine, sDSIdentifier);
            this.logger.debug("CustomEmailer.createSubjectLine() : SubjectLine = " + sSubjectLine);
         }
      }

      this.sSubject = sSubjectLine;
   }

   private String handleDynamicValues(String name, String sOriginalLine, String sDSIdentifier) {
      if (!name.equals("PID") && !name.equals("WS_NAME") && !name.equals("WS_PERFORMER") && !name.equals("TIME_ELAPSED")) {
         Object obj = this.hmDataSlot.get(name);
         return StringUtils.replace(sOriginalLine, sDSIdentifier, this.handleObject(obj));
      } else if (name.equals("WS_NAME")) {
         return StringUtils.replace(sOriginalLine, sDSIdentifier, this.sWSName);
      } else if (name.equals("PID")) {
         return StringUtils.replace(sOriginalLine, sDSIdentifier, (new Long(this.PID)).toString());
      } else if (name.equals("WS_PERFORMER")) {
         return this.sPerformer == null ? "" : StringUtils.replace(sOriginalLine, sDSIdentifier, this.sPerformer);
      } else {
         return name.equals("TIME_ELAPSED") ? StringUtils.replace(sOriginalLine, sDSIdentifier, StaticFuncs.getTimeStringValue(this.timeElapsed, true, this.timeElapsedLevel)) : EmailSenderPropertyReader.getNoValueSubstitute();
      }
   }

   private void createMailTo(EmailerPTInfo ptInfo) {
      String sMailIDs = ((EmailerWSInfo)ptInfo.getWSInformation().get(this.sWSName)).getEmailTo();
      this.sMailTo = this.handleMailID(StaticFuncs.StringtoList(sMailIDs));
      this.logger.debug("CustomEmailer.createMailTo() : TO Mail ID's are " + this.sMailTo);
   }

   private void createMailCC(EmailerPTInfo ptInfo) {
      String sMailIDs = ((EmailerWSInfo)ptInfo.getWSInformation().get(this.sWSName)).getEmailCC();
      this.sMailCC = this.handleMailID(StaticFuncs.StringtoList(sMailIDs));
      this.logger.debug("CustomEmailer.createMailCC() : CC Mail ID's are " + this.sMailCC);
   }

   private void createMailBCC(EmailerPTInfo ptInfo) {
      String sMailIDs = ((EmailerWSInfo)ptInfo.getWSInformation().get(this.sWSName)).getEmailBCC();
      this.sMailBCC = this.handleMailID(StaticFuncs.StringtoList(sMailIDs));
      this.logger.debug("CustomEmailer.createMailBCC() : BCC Mail ID's are " + this.sMailBCC);
   }

   private String handleMailID(Vector vecMailIDs) {
      String mailID = "";
      String mailIDs = "";

      for(int i = 0; i < vecMailIDs.size(); ++i) {
         mailID = (String)vecMailIDs.elementAt(i);
         if (mailID.indexOf("@") == -1) {
            Object obj = this.hmDataSlot.get(mailID);
            if (obj instanceof String) {
               mailIDs = mailIDs + obj.toString() + ",";
            }
         } else {
            mailIDs = mailIDs + mailID + ",";
         }
      }

      return mailIDs;
   }

   private void createMailBody(int templateNum, EmailerPTInfo ptInfo) {
      Vector vecTemplateFileList = ((EmailerWSInfo)ptInfo.getWSInformation().get(this.sWSName)).getTemplateFileList();
      String sFilePath = (String)vecTemplateFileList.elementAt(templateNum);
      this.logger.debug("CustomEmailer.createMessageBody() : STARTS");
      this.sbBody = new StringBuffer();

      try {
         FileInputStream fis = new FileInputStream(sFilePath);
         BufferedReader br = new BufferedReader(new InputStreamReader(fis));
         this.logger.debug("CustomEmailer.createMessageBody() : Stream Created");

         for(String line = br.readLine(); line != null; line = br.readLine()) {
            this.logger.debug("CustomEmailer.createMessageBody() : Original LINE = " + line);
            int start;
            int end;
            String sDSIdentifier;
            String sBookmark;
            if (this.hmDataSlot != null && line.indexOf("${") != -1) {
               this.logger.debug("CustomEmailer.createMessageBody() : Entering IF block for DS handling...");

               while(line.indexOf("${") != -1) {
                  start = line.indexOf("${");
                  end = line.indexOf("}");
                  sDSIdentifier = line.substring(start, end + 1);
                  sBookmark = line.substring(start + 2, end).trim();
                  this.logger.debug("CustomEmailer.createMessageBody() : Complete Identifier = " + sDSIdentifier);
                  this.logger.debug("CustomEmailer.createMessageBody() : Data Slot Name = " + sBookmark);
                  line = this.handleDynamicValues(sBookmark, line, sDSIdentifier);
                  this.logger.debug("CustomEmailer.createMessageBody() : Line = " + line);
               }
            }

            if (line.indexOf("$(") != -1) {
               this.logger.debug("CustomEmailer.createMessageBody() : Entering IF block for Custom bookmark handling...");

               while(line.indexOf("$(") != -1) {
                  start = line.indexOf("$(");
                  end = line.indexOf(")");
                  sDSIdentifier = line.substring(start, end + 1);
                  sBookmark = line.substring(start + 2, end).trim();
                  this.logger.debug("CustomEmailer.createMessageBody() : Complete Identifier = " + sDSIdentifier);
                  this.logger.debug("CustomEmailer.createMessageBody() : Custom Bookmark = " + sBookmark);
                  String sValue = "Value NOT Aavailable.";
                  if (this.customBookMarks.get(sBookmark) != null) {
                     sValue = (String)this.customBookMarks.get(sBookmark);
                  }

                  this.logger.debug("CustomEmailer.createMessageBody() : Custom Bookmark Value = " + sValue);
                  line = StringUtils.replace(line, sDSIdentifier, sValue);
                  this.logger.debug("CustomEmailer.createMessageBody() : Line = " + line);
               }
            }

            this.sbBody.append(line + "\n");
         }
      } catch (Exception var13) {
         this.logger.fatal("CustomEmailer.createMessageBody() : Exception in creating mail body", var13);
      }

      this.logger.debug("CustomEmailer.createMessageBody() : ENDS\n" + this.sbBody.toString());
   }

   private void getSession() {
      try {
         if (this.mblsession == null || !this.mblserver.isSessionValid(this.mblsession)) {
            this.logger.debug("CustomEmailer.getSession() : Creating BL Session...");
            System.out.println("EmailSenderPropertyReader.getBizlogicUserName():::" + EmailSenderPropertyReader.getBizlogicUserName());
            System.out.println("EmailSenderPropertyReader.getBizlogicPassword():::" + EmailSenderPropertyReader.getBizlogicPassword());
            this.mblsession = this.mblserver.connect(EmailSenderPropertyReader.getBizlogicUserName(), EmailSenderPropertyReader.getBizlogicPassword());
         }
      } catch (RemoteException var2) {
         this.logger.error("CustomEmailer.getSession(): Caught RemoteException while connecting to BLServer." + var2);
      }

   }

   private void getBLServer() {
      if (this.mblserver == null) {
         int ret = this.createBLServer();
         if (ret == -1) {
            this.logger.error("CustomEmailer.getBLServer(): Can not create BLServer object.");
         }
      }

   }

   private int createBLServer() {
      this.logger.debug("CustomEmailer.createBLServer(): Entering method");
      if (this.mblserver != null) {
         this.logger.debug("CustomEmailer.createBLServer(): mblserver is NOT null");
         return 0;
      } else {
         try {
            SBMHomeFactory sbm_home_factory = SBMHomeFactory.self();
            BLServerHome server_home = (BLServerHome)sbm_home_factory.lookupHome(BLServerHome.class);
            this.mblserver = server_home.create();
            this.logger.debug("CustomEmailer.createBLServer(): mblserver is created");
            return 0;
         } catch (Exception var3) {
            this.logger.error("CustomEmailer.createBLServer(): Caught NamingException while looking up BLServerHome" + var3);
            return -1;
         }
      }
   }

   private ProcessInstance getProcessInstance(long pid) {
      ProcessInstance pi = null;
      this.logger.debug("CustomEmailer.getProcessInstance(): STARTED");
      this.getBLServer();
      this.getSession();

      try {
         pi = this.mblserver.getProcessInstance(this.mblsession, pid);
      } catch (Exception var5) {
         this.logger.error("CustomEmailer.getProcessInstance(): Error while getting ProcessInstance svo object " + var5);
      }

      return pi;
   }

   private void closeSession() {
      this.logger.debug("CustomEmailer.closeSession() : Trying to disconnect the BizLogic Session");

      try {
         if (this.mblsession != null && this.mblserver.isSessionValid(this.mblsession)) {
            this.mblserver.disConnect(this.mblsession);
            this.logger.debug("Session closed...");
         }

         this.mblsession = null;
      } catch (Exception var2) {
         this.logger.error("CustomEmailer.closeSession() : Closing BizLogic Session " + var2);
      }

   }

   public StringBuffer getMailBody() {
      return this.sbBody;
   }

   public String getMailBCC() {
      return this.sMailBCC;
   }

   public String getMailCC() {
      return this.sMailCC;
   }

   public String getMailTo() {
      return this.sMailTo;
   }

   public String getSubject() {
      return this.sSubject;
   }

   private String handleObject(Object objType) {
      if (objType == null) {
         return "";
      } else {
         String value = EmailSenderPropertyReader.getNoValueSubstitute();
         this.logger.debug("CustomEmailer.handleObject() : Object type = " + objType.getClass());
         if (objType instanceof String) {
            value = (String)objType;
         } else if (objType instanceof DateTime) {
            DateTime dt = (DateTime)objType;
            long time = dt.getTime();
            if (time > 0L) {
               value = (new SimpleDateFormat(EmailSenderPropertyReader.getDateFormat())).format(new Date(time));
            }
         } else if (objType instanceof Decimal) {
            value = ((Decimal)objType).getStringValue();
         } else if (objType instanceof Long) {
            value = ((Long)objType).toString();
         } else if (objType instanceof Double) {
            value = ((Double)objType).toString();
         } else if (objType instanceof Boolean) {
            if ((Boolean)objType) {
               value = "Yes";
            } else {
               value = "No";
            }
         } else if (objType instanceof Vector) {
            Vector data = (Vector)objType;
            value = "\n";

            for(int i = 0; i < data.size(); ++i) {
               value = value + data.elementAt(i) + "\n";
            }
         } else if (objType instanceof LinkedHashMap) {
            LinkedHashMap map = (LinkedHashMap)objType;
            value = "\n";
            Set keySet = map.keySet();
            Iterator ite = keySet.iterator();

            for(String key = ""; ite.hasNext(); value = value + key + " = " + map.get(key) + "\n") {
               key = (String)ite.next();
            }
         }

         this.logger.debug("CustomEmailer.handleObject(): Return Value = " + value);
         return value;
      }
   }

   public boolean setOverDuetimeLevel(int level) {
      if (level >= StaticFuncs.LEVEL_DAYS && level <= StaticFuncs.LEVEL_MILLI_SECONDS) {
         this.timeElapsedLevel = level;
         return true;
      } else {
         this.timeElapsedLevel = StaticFuncs.LEVEL_HOURS;
         return false;
      }
   }
}
