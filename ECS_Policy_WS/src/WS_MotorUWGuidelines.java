import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItem;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemFilter;
import com.savvion.sbm.bizlogic.client.queryservice.QSWorkItemRS;
import com.savvion.sbm.bizlogic.client.queryservice.QueryService;
import com.savvion.sbm.bizlogic.server.svo.DateTime;
import com.savvion.sbm.bizlogic.server.svo.Decimal;
import com.savvion.sbm.bizlogic.server.svo.QSWorkItemData;
import com.savvion.sbm.bizlogic.server.svo.XML;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.util.NLog;
import com.tdiinc.BizLogic.Server.PAKException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Map.Entry;
import org.apache.axis.AxisFault;

public class WS_MotorUWGuidelines {
   private static BizLogicManager pak = null;
   private String ptName = "MotorUWGuidelines";
   private static Byte[] bytearray = new Byte[0];

   public String START(String BranchID, String ProposalNumber, String ProductName, String BranchCategory, String BusinessType, String AgentCode, String BasCode, String IMDCategory, String ODDiscountRate, String PRStartDate, String PREndDate, String VehicleIDV, String VehicleMake, String VehicleModel, String VehicleVariant, String YearOfManufacture, String ApprovalAuthority, String DiscrepancyCategory, String SMCode, String SMName, String BranchName, String BreakingDays, String Loading, String NCB, String ProposalCNNumber, String PrevPolicyNumber, String InsuredName, String InspectionID, String RecommendedStatus, String ClaimStatus, String VRegnEngCheNo, String CNProposalIssueDate, String InspectionDate, String ProposalInwardDate, String InstrumentStatus, String AddDeductiveStr, String FuelType, String SMPhone, String prevPlcODPrem, String SI, String currentPlcODPrem, String policyRemark, String PrevYearOdDiscount, String GVW, String CarryingCapacity, String username, String password, String piName, String priority) throws AxisFault {
      String sessionId = this.connect(username, password);
      HashMap dsTypeMap = new HashMap();
      String plcRemark = policyRemark;
      if (policyRemark != null && policyRemark.length() > 1999) {
         plcRemark = policyRemark.substring(0, 1999);
      }

      dsTypeMap.put("BranchID", "STRING");
      dsTypeMap.put("ProposalNumber", "STRING");
      dsTypeMap.put("ProductName", "STRING");
      dsTypeMap.put("BranchCategory", "STRING");
      dsTypeMap.put("BusinessType", "STRING");
      dsTypeMap.put("AgentCode", "STRING");
      dsTypeMap.put("BasCode", "STRING");
      dsTypeMap.put("IMDCategory", "STRING");
      dsTypeMap.put("ODDiscountRate", "STRING");
      dsTypeMap.put("PRStartDate", "STRING");
      dsTypeMap.put("PREndDate", "STRING");
      dsTypeMap.put("VehicleIDV", "STRING");
      dsTypeMap.put("VehicleMake", "STRING");
      dsTypeMap.put("VehicleModel", "STRING");
      dsTypeMap.put("VehicleVariant", "STRING");
      dsTypeMap.put("YearOfManufacture", "STRING");
      dsTypeMap.put("ApprovalAuthority", "STRING");
      dsTypeMap.put("DiscrepancyCategory", "STRING");
      dsTypeMap.put("SMCode", "STRING");
      dsTypeMap.put("SMName", "STRING");
      dsTypeMap.put("BranchName", "STRING");
      dsTypeMap.put("BreakingDays", "STRING");
      dsTypeMap.put("Loading", "STRING");
      dsTypeMap.put("NCB", "STRING");
      dsTypeMap.put("ProposalCNNumber", "STRING");
      dsTypeMap.put("PrevPolicyNumber", "STRING");
      dsTypeMap.put("InsuredName", "STRING");
      dsTypeMap.put("InspectionID", "STRING");
      dsTypeMap.put("RecommendedStatus", "STRING");
      dsTypeMap.put("ClaimStatus", "STRING");
      dsTypeMap.put("VRegnEngCheNo", "STRING");
      dsTypeMap.put("CNProposalIssueDate", "STRING");
      dsTypeMap.put("InspectionDate", "STRING");
      dsTypeMap.put("ProposalInwardDate", "STRING");
      dsTypeMap.put("InstrumentStatus", "STRING");
      dsTypeMap.put("AddDeductiveStr", "STRING");
      dsTypeMap.put("FuelType", "STRING");
      dsTypeMap.put("SMPhone", "STRING");
      dsTypeMap.put("prevPlcODPrem", "STRING");
      dsTypeMap.put("SI", "STRING");
      dsTypeMap.put("currentPlcODPrem", "STRING");
      dsTypeMap.put("policyRemark", "STRING");
      dsTypeMap.put("PrevYearOdDiscount", "STRING");
      dsTypeMap.put("GVW", "STRING");
      dsTypeMap.put("CarryingCapacity", "STRING");
      HashMap dsValues = new HashMap();
      dsValues.put("BranchID", BranchID);
      dsValues.put("ProposalNumber", ProposalNumber);
      dsValues.put("ProductName", ProductName);
      dsValues.put("BranchCategory", BranchCategory);
      dsValues.put("BusinessType", BusinessType);
      dsValues.put("AgentCode", AgentCode);
      dsValues.put("BasCode", BasCode);
      dsValues.put("IMDCategory", IMDCategory);
      dsValues.put("ODDiscountRate", ODDiscountRate);
      dsValues.put("PRStartDate", PRStartDate);
      dsValues.put("PREndDate", PREndDate);
      dsValues.put("VehicleIDV", VehicleIDV);
      dsValues.put("VehicleMake", VehicleMake);
      dsValues.put("VehicleModel", VehicleModel);
      dsValues.put("VehicleVariant", VehicleVariant);
      dsValues.put("YearOfManufacture", YearOfManufacture);
      dsValues.put("ApprovalAuthority", ApprovalAuthority);
      dsValues.put("DiscrepancyCategory", DiscrepancyCategory);
      dsValues.put("SMCode", SMCode);
      dsValues.put("SMName", SMName);
      dsValues.put("BranchName", BranchName);
      dsValues.put("BreakingDays", BreakingDays);
      dsValues.put("Loading", Loading);
      dsValues.put("NCB", NCB);
      dsValues.put("ProposalCNNumber", ProposalCNNumber);
      dsValues.put("PrevPolicyNumber", PrevPolicyNumber);
      dsValues.put("InsuredName", InsuredName);
      dsValues.put("InspectionID", InspectionID);
      dsValues.put("RecommendedStatus", RecommendedStatus);
      dsValues.put("ClaimStatus", ClaimStatus);
      dsValues.put("VRegnEngCheNo", VRegnEngCheNo);
      dsValues.put("CNProposalIssueDate", CNProposalIssueDate);
      dsValues.put("InspectionDate", InspectionDate);
      dsValues.put("ProposalInwardDate", ProposalInwardDate);
      dsValues.put("InstrumentStatus", InstrumentStatus);
      dsValues.put("AddDeductiveStr", AddDeductiveStr);
      dsValues.put("FuelType", FuelType);
      dsValues.put("SMPhone", SMPhone);
      dsValues.put("prevPlcODPrem", prevPlcODPrem);
      dsValues.put("SI", SI);
      dsValues.put("currentPlcODPrem", currentPlcODPrem);
      dsValues.put("policyRemark", plcRemark);
      dsValues.put("PrevYearOdDiscount", PrevYearOdDiscount);
      dsValues.put("GVW", GVW);
      dsValues.put("CarryingCapacity", CarryingCapacity);
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      String pi = this.createProcessInstance(sessionId, this.ptName, piName, priority, resolvedDSValues);
      this.disconnect(sessionId);
      return pi;
   }

   private void CUACTIVITY(String CUApprovalStatus, long AdditionalDeductible, String ApproverRemark, long deviationCount, String CURemarkOther, String CUComments, String FinalStatus, String CUReasonRemark, String ReturnMessage, String ReturnStatus, String DeviationFromUW, String UWRemarkOther, String UWRemarkReason, String PrevPolicyNumber, String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("CUACTIVITY method invoked");
      HashMap dsTypeMap = new HashMap();
      dsTypeMap.put("CUApprovalStatus", "STRING");
      dsTypeMap.put("AdditionalDeductible", "LONG");
      dsTypeMap.put("ApproverRemark", "STRING");
      dsTypeMap.put("deviationCount", "LONG");
      dsTypeMap.put("CURemarkOther", "STRING");
      dsTypeMap.put("CUComments", "STRING");
      dsTypeMap.put("FinalStatus", "STRING");
      dsTypeMap.put("CUReasonRemark", "STRING");
      dsTypeMap.put("ReturnMessage", "STRING");
      dsTypeMap.put("ReturnStatus", "STRING");
      dsTypeMap.put("DeviationFromUW", "STRING");
      dsTypeMap.put("UWRemarkOther", "STRING");
      dsTypeMap.put("UWRemarkReason", "STRING");
      dsTypeMap.put("PrevPolicyNumber", "STRING");
      HashMap dsValues = new HashMap();
      dsValues.put("CUApprovalStatus", CUApprovalStatus);
      dsValues.put("AdditionalDeductible", new Long(AdditionalDeductible));
      dsValues.put("ApproverRemark", ApproverRemark);
      dsValues.put("deviationCount", new Long(deviationCount));
      dsValues.put("CURemarkOther", CURemarkOther);
      dsValues.put("CUComments", CUComments);
      dsValues.put("FinalStatus", FinalStatus);
      dsValues.put("CUReasonRemark", CUReasonRemark);
      dsValues.put("ReturnMessage", ReturnMessage);
      dsValues.put("ReturnStatus", ReturnStatus);
      dsValues.put("DeviationFromUW", DeviationFromUW);
      dsValues.put("UWRemarkOther", UWRemarkOther);
      dsValues.put("UWRemarkReason", UWRemarkReason);
      dsValues.put("PrevPolicyNumber", PrevPolicyNumber);
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   private void FAILEDCASESCU(String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("FAILEDCASESCU method invoked");
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   private void FAILEDCASESSCUI(String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("FAILEDCASESSCUI method invoked");
      HashMap dsTypeMap = new HashMap();
      HashMap dsValues = new HashMap();
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   private void UWAPPROVALACTIVITY(String ApproverRemark, long AdditionalDeductible, String UWRemarkReason, String UWRemarkOther, String FinalStatus, String ReturnMessage, String ReturnStatus, String DeviationFromUW, long deviationCount, String BreakingDays, String Loading, String ODDiscountRate, String NCB, String VehicleDetail, String ProposalInwardDate, String InspectionDate, String ClaimStatus, String ProductName, String BranchName, String AgentCode, String BasCode, String IMDCategory, String SM, String BranchCategory, String CallStatus, String CaseRaisedDt, String UWApproval, String ApprovalAuthority, boolean isForwarded, String sessionId, String piName, String wiName) throws AxisFault {
      NLog.ws.debug("UWAPPROVALACTIVITY method invoked");
      HashMap dsTypeMap = new HashMap();
      dsTypeMap.put("ApproverRemark", "STRING");
      dsTypeMap.put("AdditionalDeductible", "LONG");
      dsTypeMap.put("UWRemarkReason", "STRING");
      dsTypeMap.put("UWRemarkOther", "STRING");
      dsTypeMap.put("FinalStatus", "STRING");
      dsTypeMap.put("ReturnMessage", "STRING");
      dsTypeMap.put("ReturnStatus", "STRING");
      dsTypeMap.put("DeviationFromUW", "STRING");
      dsTypeMap.put("deviationCount", "LONG");
      dsTypeMap.put("BreakingDays", "STRING");
      dsTypeMap.put("Loading", "STRING");
      dsTypeMap.put("ODDiscountRate", "STRING");
      dsTypeMap.put("NCB", "STRING");
      dsTypeMap.put("VehicleDetail", "STRING");
      dsTypeMap.put("ProposalInwardDate", "STRING");
      dsTypeMap.put("InspectionDate", "STRING");
      dsTypeMap.put("ClaimStatus", "STRING");
      dsTypeMap.put("ProductName", "STRING");
      dsTypeMap.put("BranchName", "STRING");
      dsTypeMap.put("AgentCode", "STRING");
      dsTypeMap.put("BasCode", "STRING");
      dsTypeMap.put("IMDCategory", "STRING");
      dsTypeMap.put("SM", "STRING");
      dsTypeMap.put("BranchCategory", "STRING");
      dsTypeMap.put("CallStatus", "STRING");
      dsTypeMap.put("CaseRaisedDt", "STRING");
      dsTypeMap.put("UWApproval", "STRING");
      dsTypeMap.put("ApprovalAuthority", "STRING");
      dsTypeMap.put("isForwarded", "BOOLEAN");
      HashMap dsValues = new HashMap();
      dsValues.put("ApproverRemark", ApproverRemark);
      dsValues.put("AdditionalDeductible", new Long(AdditionalDeductible));
      dsValues.put("UWRemarkReason", UWRemarkReason);
      dsValues.put("UWRemarkOther", UWRemarkOther);
      dsValues.put("FinalStatus", FinalStatus);
      dsValues.put("ReturnMessage", ReturnMessage);
      dsValues.put("ReturnStatus", ReturnStatus);
      dsValues.put("DeviationFromUW", DeviationFromUW);
      dsValues.put("deviationCount", new Long(deviationCount));
      dsValues.put("BreakingDays", BreakingDays);
      dsValues.put("Loading", Loading);
      dsValues.put("ODDiscountRate", ODDiscountRate);
      dsValues.put("NCB", NCB);
      dsValues.put("VehicleDetail", VehicleDetail);
      dsValues.put("ProposalInwardDate", ProposalInwardDate);
      dsValues.put("InspectionDate", InspectionDate);
      dsValues.put("ClaimStatus", ClaimStatus);
      dsValues.put("ProductName", ProductName);
      dsValues.put("BranchName", BranchName);
      dsValues.put("AgentCode", AgentCode);
      dsValues.put("BasCode", BasCode);
      dsValues.put("IMDCategory", IMDCategory);
      dsValues.put("SM", SM);
      dsValues.put("BranchCategory", BranchCategory);
      dsValues.put("CallStatus", CallStatus);
      dsValues.put("CaseRaisedDt", CaseRaisedDt);
      dsValues.put("UWApproval", UWApproval);
      dsValues.put("ApprovalAuthority", ApprovalAuthority);
      dsValues.put("isForwarded", new Boolean(isForwarded));
      Hashtable resolvedDSValues = this.getDSValues(dsTypeMap, dsValues);
      this.setProcessDataslotValues(sessionId, piName, resolvedDSValues);
      this.completeWorkitem(sessionId, wiName);
   }

   private void activateProcessInstance(String sessionId, String piName) throws AxisFault {
      try {
         getBizLogicManager().activateProcessInstance(sessionId, piName);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      } catch (PAKException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private void completeWorkitem(String sessionId, String wiName) throws AxisFault {
      try {
         getBizLogicManager().completeWorkitem(sessionId, wiName);
      } catch (RemoteException var4) {
         throw new AxisFault("SBM Web services error :" + var4.getMessage());
      }
   }

   private static BizLogicManager getBizLogicManager() throws AxisFault {
      if (pak == null) {
         synchronized(bytearray) {
            if (pak == null) {
               try {
                  String appserver = ServiceLocator.self().getAppServerID();
                  BizLogicManagerHome blmhome = (BizLogicManagerHome)((BizLogicManagerHome)SBMHomeFactory.self().lookupHome(appserver, "BizLogicManager", BizLogicManagerHome.class));
                  pak = blmhome.create();
               } catch (Throwable var4) {
                  throw new AxisFault("SBM Web services error :" + var4.getMessage());
               }
            }
         }
      }

      return pak;
   }

   private String connect(String userId, String password) throws AxisFault {
      String sessionId = null;

      try {
         sessionId = getBizLogicManager().connect(userId, password);
         return sessionId;
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private String createProcessInstance(String sessionId, String ptName, String piName, String priority, Hashtable h) throws AxisFault {
      String pi = null;

      try {
         pi = getBizLogicManager().createProcessInstance(sessionId, ptName, piName, priority, h);
         return pi;
      } catch (RemoteException var8) {
         throw new AxisFault("SBM Web services error :" + var8.getMessage());
      }
   }

   private void setProcessDataslotValues(String sessionId, String pName, Hashtable h) throws AxisFault {
      try {
         getBizLogicManager().setProcessDataslotValues(sessionId, pName, h);
      } catch (RemoteException var5) {
         throw new AxisFault("SBM Web services error :" + var5.getMessage());
      }
   }

   private boolean disconnect(String sessionId) {
      try {
         getBizLogicManager().disconnect(sessionId);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   private String[] getAssignedWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, false);
   }

   private String[] getAvailableWorkitemNames(String sessionId) throws AxisFault {
      return this.getWorkItemNames(sessionId, true);
   }

   private String getWorkitemDataslotValue(String sessionId, String wiName, String dsName) throws AxisFault {
      Object obj = null;

      try {
         obj = getBizLogicManager().getWorkitemDataslotValue(sessionId, wiName, dsName);
      } catch (RemoteException var6) {
         throw new AxisFault("SBM Web services error :" + var6.getMessage());
      } catch (PAKException var7) {
         throw new AxisFault("SBM Web services error :" + var7.getMessage());
      }

      return obj instanceof String ? (String)obj : "NST";
   }

   private boolean checkNull(Object dsValue) {
      return dsValue == null || dsValue instanceof String && dsValue.equals("<NULL>");
   }

   private Hashtable getDSValues(HashMap dsTypes, HashMap valueMap) {
      Hashtable resolvedValues = new Hashtable();
      if (valueMap != null && valueMap.size() != 0) {
         Iterator sIterator = dsTypes.entrySet().iterator();

         while(true) {
            while(sIterator.hasNext()) {
               Entry sEntry = (Entry)sIterator.next();
               String key = (String)sEntry.getKey();
               String type = (String)sEntry.getValue();
               Object dsvalue = valueMap.get(key);
               if (this.checkNull(dsvalue)) {
                  resolvedValues.put(key, "<NULL>");
               } else if (!type.equals("DOCUMENT")) {
                  String[] data;
                  if (type.equals("LIST")) {
                     data = (String[])((String[])dsvalue);
                     int size = data.length;
                     Vector v = new Vector(size);

                     for(int i = 0; i < size; ++i) {
                        v.add(data[i]);
                     }

                     resolvedValues.put(key, v);
                  } else if (type.equals("XML")) {
                     XML xml = new XML((String)dsvalue);
                     resolvedValues.put(key, xml);
                  } else if (type.equals("DECIMAL")) {
                     Decimal dec = new Decimal((BigDecimal)dsvalue);
                     resolvedValues.put(key, dec);
                  } else if (type.equals("DATETIME")) {
                     Calendar cal = (Calendar)dsvalue;
                     DateTime dt = new DateTime(cal.getTimeInMillis());
                     resolvedValues.put(key, dt);
                  } else if (!type.equals("MAP")) {
                     resolvedValues.put(key, dsvalue);
                  } else {
                     data = (String[])((String[])dsvalue);
                     Map hm = new HashMap();

                     for(int i = 0; i < data.length; ++i) {
                        StringTokenizer st = new StringTokenizer(data[i], "=");
                        hm.put(st.nextToken(), st.nextToken());
                     }

                     resolvedValues.put(key, hm);
                  }
               }
            }

            return resolvedValues;
         }
      } else {
         return resolvedValues;
      }
   }

   private String[] getWorkItemNames(String sessionId, boolean available) throws AxisFault {
      QueryService qs = null;
      QSWorkItemRS wirs = null;

      try {
         Session sess = getBizLogicManager().getSession(sessionId);
         QSWorkItemFilter wifil = new QSWorkItemFilter("Workitem filter", this.ptName);
         qs = new QueryService(sess);
         QSWorkItem qswi = qs.getWorkItem();
         wifil.setFetchSize(400);
         if (available) {
            wirs = qswi.getAvailableList(wifil);
         } else {
            wirs = qswi.getAssignedList(wifil);
         }

         List wi = wirs.getSVOList();
         String[] winames = new String[wi.size()];

         for(int i = 0; i < wi.size(); ++i) {
            winames[i] = ((QSWorkItemData)wi.get(i)).getName();
         }

         String[] var21 = winames;
         return var21;
      } catch (Exception var19) {
         throw new AxisFault("SBM Web services error :" + var19.getMessage());
      } finally {
         try {
            wirs.close();
         } catch (Exception var18) {
         }

      }
   }
}
