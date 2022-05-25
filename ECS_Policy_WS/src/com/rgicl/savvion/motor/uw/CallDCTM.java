package com.rgicl.savvion.motor.uw;
//
//import com.documentum.com.DfClientX;
//import com.documentum.com.IDfClientX;
//import com.documentum.fc.client.IDfClient;
//import com.documentum.fc.client.IDfCollection;
//import com.documentum.fc.client.IDfSession;
//import com.documentum.fc.client.IDfSessionManager;
//import com.documentum.fc.common.IDfLoginInfo;
//import java.util.Hashtable;
//
public class CallDCTM {
	
}
//   private String PrevPolicyNumber;
//   private IDfSession session;
//   private Hashtable dsValues;
//   private long pid;
//
//   public void setProcessContextData(Hashtable context) {
//      this.pid = (Long)context.get("ProcessInstanceID");
//   }
//
//   public void setPrevPolicyNumber(String PrevPolicyNumber) {
//      this.PrevPolicyNumber = PrevPolicyNumber;
//   }
//
//   public String getPrevPolicyNumber() {
//      return this.PrevPolicyNumber;
//   }
//
//   public void getDocument() {
//      IDfSessionManager sMgr = null;
//      this.session = null;
//      Object col = null;
//
//      try {
//         this.dsValues.put("getPrevPolicyNumber", this.dsValues.get("setPrevPolicyNumber"));
//      } catch (Exception var14) {
//         throw new RuntimeException("Error in CallDCTM MotorUW", var14);
//      } finally {
//         if (col != null) {
//            try {
//               ((IDfCollection)col).close();
//            } catch (Exception var13) {
//            }
//         }
//
//         if (this.session != null) {
//            try {
//               ((IDfSessionManager)sMgr).release(this.session);
//            } catch (Exception var12) {
//            }
//         }
//
//      }
//
//   }
//
//   IDfSessionManager createSessionManager(String docbase, String user, String pass) throws Exception {
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
//   public void setAllInputDataslots(Hashtable dataSlot) {
//      this.dsValues = dataSlot;
//      this.PrevPolicyNumber = (String)this.dsValues.get("setPrevPolicyNumber");
//   }
//
//   public Hashtable getAllOutputDataslots() {
//      return this.dsValues;
//   }
//}
