package com.rgicl.motor.policy;
//
//import com.documentum.com.DfClientX;
//import com.documentum.com.IDfClientX;
//import com.documentum.fc.client.DfQuery;
//import com.documentum.fc.client.IDfClient;
//import com.documentum.fc.client.IDfCollection;
//import com.documentum.fc.client.IDfFormat;
//import com.documentum.fc.client.IDfQuery;
//import com.documentum.fc.client.IDfSession;
//import com.documentum.fc.client.IDfSessionManager;
//import com.documentum.fc.client.IDfSysObject;
//import com.documentum.fc.common.DfId;
//import com.documentum.fc.common.IDfLoginInfo;
//import com.rgicl.savvion.motor.uw.PropertyReader;
//import com.savvion.sbm.bizlogic.client.BLClientUtil;
//import com.savvion.sbm.bizlogic.server.ejb.BLServer;
//import com.savvion.sbm.bizlogic.server.svo.DataSlot;
//import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
//import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
//import com.savvion.sbm.bizlogic.server.svo.WorkItem;
//import com.savvion.sbm.bizlogic.util.Session;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
//
public class PolicyDetails {
	
}
//   private IDfSession session;
//
//   private IDfSessionManager createSessionManager(String docbase, String user, String pass) throws Exception {
//      IDfClientX clientx = new DfClientX();
//      IDfClient client = clientx.getLocalClient();
//      IDfSessionManager sMgr = client.newSessionManager();
//      IDfLoginInfo loginInfoObj = clientx.getLoginInfo();
//      loginInfoObj.setUser(user);
//      loginInfoObj.setPassword(pass);
//      sMgr.setIdentity(docbase, loginInfoObj);
//      return sMgr;
//   }
//
//   public void updatePolicyDocuments(String previousPolicyNumber, String proposalCNNumber, String wiName) {
//      PropertyConfigurator.configure("/opt/SBM75/conf/logger.props");
//      Logger log = Logger.getLogger("Document.File");
//      log.info("WorkItem Name $ " + wiName);
//      IDfSessionManager sMgr = null;
//      this.session = null;
//      IDfCollection col = null;
//      IDfCollection col1 = null;
//      DocumentDS docDS = null;
//      DocumentDS docDS1 = null;
//      DataSlot ds = null;
//      DataSlot ds1 = null;
//
//      try {
//         PropertyReader propertyReader = PropertyReader.getInstance();
//         BLServer blServer = BLClientUtil.getBizLogicServer();
//         Session blSession = blServer.connect(propertyReader.getPropertyByKey("bizlogic_admin_user"), propertyReader.getPropertyByKey("bizlogic_admin_password"));
//         WorkItem wi = blServer.getWorkItem(blSession, wiName);
//         ProcessInstance pi = blServer.getProcessInstance(blSession, wi.getProcessInstanceID());
//         ds = pi.getDataSlot("PolicyDocuments");
//         docDS = (DocumentDS)ds.getValue();
//         log.info("PolicyDocuments count $ " + docDS.getCount());
//         ds1 = pi.getDataSlot("InspectionDocument");
//         docDS1 = (DocumentDS)ds1.getValue();
//         log.info("Inspection count $ " + docDS1.getCount());
//         log.info("previousPolicyNumber $ " + previousPolicyNumber);
//         log.info(propertyReader.getPropertyByKey("rgi_documentum_repository_name"));
//         log.info(propertyReader.getPropertyByKey("rgi_documentum_user"));
//         log.info(propertyReader.getPropertyByKey("rgi_documentum_password"));
//         sMgr = this.createSessionManager(propertyReader.getPropertyByKey("rgi_documentum_repository_name"), propertyReader.getPropertyByKey("rgi_documentum_user"), propertyReader.getPropertyByKey("rgi_documentum_password"));
//         this.session = sMgr.getSession(propertyReader.getPropertyByKey("rgi_documentum_repository_name"));
//         log.info("Test");
//         File file1;
//         ArrayList rowsData;
//         String ext;
//         if (!previousPolicyNumber.equals("") && previousPolicyNumber != null && !previousPolicyNumber.equals("<NULL>")) {
//            log.info("PolicyDocuments $ ");
//            if (docDS.getCount() <= 0) {
//               file1 = null;
//               log.info("Documentum Session $ " + this.session.getSessionId());
//               IDfQuery dfQuery = new DfQuery();
//               IDfSysObject sysObj = null;
//               dfQuery.setDQL("select r_object_id from rgi_policy where policy_id = '" + previousPolicyNumber + "'" + " and r_content_size > 0");
//               rowsData = new ArrayList();
//               col = dfQuery.execute(this.session, 0);
//
//               while(col.next()) {
//                  rowsData.add(col.getString("r_object_id"));
//               }
//
//               int existCount = 0;
//               log.info("Number of docs $ " + rowsData.isEmpty());
//               Iterator iterator = rowsData.iterator();
//
//               while(iterator.hasNext()) {
//                  String sObjectId = (String)iterator.next();
//                  IDfSysObject objCopy = null;
//                  sysObj = (IDfSysObject)this.session.getObject(new DfId(sObjectId));
//                  if (sysObj != null) {
//                     objCopy = sysObj;
//                  }
//
//                  IDfFormat myFormat = objCopy.getFormat();
//                  String ext = myFormat.getDOSExtension();
//                  ext = propertyReader.getPropertyByKey("rgi_policy_doc_temp_path") + objCopy.getObjectName() + "." + ext;
//                  objCopy.getFileEx2(ext, objCopy.getContentType(), 0, "", false);
//                  file1 = new File(ext);
//                  if (docDS.isDocumentExist(objCopy.getObjectName() + "." + ext)) {
//                     ++existCount;
//                     docDS.create(blSession.getUser(), objCopy.getObjectName() + existCount + "." + ext, file1, new HashMap());
//                  } else {
//                     docDS.create(blSession.getUser(), objCopy.getObjectName() + "." + ext, file1, new HashMap());
//                  }
//               }
//
//               if (!rowsData.isEmpty()) {
//                  ds.setValue(docDS);
//                  ds.save();
//                  pi.save();
//                  log.info("Updated Policy Document dataslot docs $ ");
//               }
//            }
//         }
//
//         log.info("proposalCNNumber $ " + proposalCNNumber);
//         if (!proposalCNNumber.equals("") && proposalCNNumber != null && !proposalCNNumber.equals("<NULL>")) {
//            log.info("proposalCNNumber $ " + proposalCNNumber);
//            if (docDS1.getCount() <= 0) {
//               file1 = null;
//               String proposalNumber = proposalCNNumber.substring(0, 12);
//               log.info("proposalNumber $ " + proposalNumber);
//               IDfQuery dfQuery1 = new DfQuery();
//               rowsData = null;
//               dfQuery1.setDQL("select r_object_id, object_name from rgi_approved where proposal_id = '" + proposalNumber + "' and supp_doc_type = 'Inspection'");
//               List rowsData1 = new ArrayList();
//               col1 = dfQuery1.execute(this.session, 0);
//
//               while(col1.next()) {
//                  rowsData1.add(col1.getString("r_object_id"));
//               }
//
//               int existCount1 = 0;
//               log.info("Count for inspection document $ ");
//               Iterator iterator1 = rowsData1.iterator();
//
//               while(iterator1.hasNext()) {
//                  String sObjectId1 = (String)iterator1.next();
//                  IDfSysObject objCopy1 = null;
//                  IDfSysObject sysObj1 = (IDfSysObject)this.session.getObject(new DfId(sObjectId1));
//                  if (sysObj1 != null) {
//                     objCopy1 = sysObj1;
//                  }
//
//                  IDfFormat myFormat1 = objCopy1.getFormat();
//                  ext = myFormat1.getDOSExtension();
//                  String filePath = propertyReader.getPropertyByKey("rgi_policy_doc_temp_path") + objCopy1.getObjectName() + "." + ext;
//                  objCopy1.getFileEx2(filePath, objCopy1.getContentType(), 0, "", false);
//                  file1 = new File(filePath);
//                  if (docDS1.isDocumentExist(objCopy1.getObjectName() + "." + ext)) {
//                     ++existCount1;
//                     docDS1.create(blSession.getUser(), objCopy1.getObjectName() + existCount1 + "." + ext, file1, new HashMap());
//                  } else {
//                     docDS1.create(blSession.getUser(), objCopy1.getObjectName() + "." + ext, file1, new HashMap());
//                  }
//               }
//
//               if (!rowsData1.isEmpty()) {
//                  ds1.setValue(docDS1);
//                  ds1.save();
//                  pi.save();
//                  log.info("Updating inspection document $ " + rowsData1.size());
//               }
//            }
//         }
//
//         blServer.disConnect(blSession);
//         log.info("******* Completed processing  for wiName ********" + wiName);
//      } catch (Exception var42) {
//         log.info(var42.getMessage());
//         throw new RuntimeException(var42);
//      } finally {
//         if (col != null) {
//            try {
//               col.close();
//            } catch (Exception var41) {
//            }
//         }
//
//         if (col1 != null) {
//            try {
//               col1.close();
//            } catch (Exception var40) {
//            }
//         }
//
//         if (this.session != null) {
//            try {
//               sMgr.release(this.session);
//               log.info("Documentum Session closed");
//            } catch (Exception var39) {
//            }
//         }
//
//      }
//
//   }
//}
