package vam.com.rgi.rcorp.wsdl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;

public interface WorkFlowWS extends Remote {
   String connect(String var1, String var2) throws RemoteException;

   void disconnect(String var1) throws RemoteException;

   String getStatus() throws RemoteException;

   boolean isSessionValid(String var1) throws RemoteException;

   void completeWorkItem(String var1, String var2, DataSlotinstance[] var3) throws RemoteException;

   ProcessInstance[] getProcessInstanceList(String var1) throws RemoteException;

   void removeProcessTemplate(String var1, String var2) throws RemoteException;

   ProcessTemplate getProcessTemplate(String var1, String var2) throws RemoteException;

   void setProcessInstancePriority(String var1, String var2, String var3) throws RemoteException;

   long getProcessTemplateID(String var1, String var2) throws RemoteException;

   String[] getProcessTemplateVersions(String var1, String var2) throws RemoteException;

   String getProcessTemplateXML(String var1, String var2) throws RemoteException;

   ProcessTemplate[] getUserAuthorizedProcessTemplateList(String var1) throws RemoteException;

   ProcessInstance createProcessInstance(String var1, String var2, String var3, String var4, DataSlotTemplate[] var5) throws RemoteException;

   void resumeProcessInstance(String var1, String var2) throws RemoteException;

   void suspendProcessInstance(String var1, String var2) throws RemoteException;

   boolean isProcessTemplateExist(String var1, String var2) throws RemoteException;

   ProcessInstance getProcessInstance(String var1, String var2) throws RemoteException;

   WorkSteptemplate getWorkStepTemplate(String var1, String var2, String var3) throws RemoteException;

   WorkStepInstance getWorkStepInstance(String var1, String var2, String var3) throws RemoteException;

   WorkItem getWorkItem(String var1, String var2) throws RemoteException;

   WorkItem[] getAvailableWorkItemList(String var1) throws RemoteException;

   WorkItem[] getAssignedWorkItemList(String var1) throws RemoteException;

   WorkItem[] getProxyAssignedWorkItemList(String var1) throws RemoteException;

   WorkItem[] getProxyAvailableWorkItemList(String var1) throws RemoteException;

   WorkItem[] getSuspendedWorkItemList(String var1) throws RemoteException;

   String getProcessTemplateAppName(String var1, String var2) throws RemoteException;

   String getProcessTemplateNameFromProcessInstance(String var1, String var2) throws RemoteException;

   String getProcessTemplateNameFromProcessID(String var1, long var2) throws RemoteException;

   WorkSteptemplate[] getProcessTemplateWorkSteps(String var1, String var2) throws RemoteException;

   String[] getUserAuthorizedProcessTemplateNames(String var1) throws RemoteException;

   ProcessTemplate getProcessTemplateFromID(String var1, long var2) throws RemoteException;

   DataSlotTemplate[] getProcessTemplateDataSlots(String var1, String var2) throws RemoteException;

   DataSlotTemplate[] getProcessTemplateDataSlot(String var1, String var2, String[] var3) throws RemoteException;

   DataSlotinstance[] getProcessInstanceDataSlots(String var1, String var2) throws RemoteException;

   DataSlotinstance[] getProcessInstanceDataSlot(String var1, String var2, String[] var3) throws RemoteException;

   DataSlotinstance[] getProcessInstanceDataSlotFromPIID(String var1, long var2, String[] var4) throws RemoteException;

   DataSlotinstance[] getWorkStepInstanceDataSlots(String var1, String var2, String var3) throws RemoteException;

   DataSlotinstance[] getWorkStepInstanceDataSlotsFromPIID(String var1, long var2, String var4) throws RemoteException;

   DataSlotinstance[] getWorkItemDataSlots(String var1, String var2) throws RemoteException;

   DataSlotinstance[] getWorkItemDataSlotsFromWIID(String var1, long var2) throws RemoteException;

   DataSlotTemplate[] getWorkStepTemplateDataSlots(String var1, String var2, String var3) throws RemoteException;

   DataSlotTemplate[] getWorkStepTemplateDataSlotsFromPTID(String var1, long var2, String var4) throws RemoteException;

   void setProcessTemplateDataSlotValue(String var1, String var2, DataSlotTemplate var3) throws RemoteException;

   void setProcessTemplateDataSlotsValue(String var1, String var2, DataSlotTemplate[] var3) throws RemoteException;

   void setProcessInstanceDataSlotValue(String var1, String var2, DataSlotinstance var3) throws RemoteException;

   void setProcessInstanceDataSlotsValue(String var1, String var2, DataSlotinstance[] var3) throws RemoteException;

   void setWorkItemDataSlotsValue(String var1, String var2, DataSlotinstance[] var3) throws RemoteException;

   void setWorkItemDataSlotValue(String var1, String var2, DataSlotinstance var3) throws RemoteException;

   ProcessInstance getProcessInstanceFromID(String var1, long var2) throws RemoteException;

   void setProcessInstanceDueDate(String var1, String var2, Calendar var3) throws RemoteException;

   WorkItem getWorkItemFromID(String var1, long var2) throws RemoteException;

   void suspendWorkItem(String var1, String var2) throws RemoteException;

   void resumeWorkItem(String var1, String var2) throws RemoteException;

   void assignWorkItem(String var1, String var2, String var3) throws RemoteException;

   void assignWorkItemFromWIID(String var1, long var2, String var4) throws RemoteException;

   void reAssignWorkItem(String var1, String var2, String var3) throws RemoteException;

   void reAssignWorkItemFromWIID(String var1, long var2, String var4) throws RemoteException;

   void makeAvailableWorkItem(String var1, String var2, String[] var3) throws RemoteException;

   void makeAvailableWorkItemFromWIID(String var1, long var2, String[] var4) throws RemoteException;

   void completeWorkItemFromWIID(String var1, long var2, DataSlotinstance[] var4) throws RemoteException;

   WorkStepInstance getWorkStepInstanceFromPIID(String var1, long var2, String var4) throws RemoteException;

   WorkStepInstance[] getProcessInstanceWorkSteps(String var1, String var2) throws RemoteException;

   void suspendWorkStepInstance(String var1, String var2, String var3) throws RemoteException;

   void resumeWorkStepInstance(String var1, String var2, String var3) throws RemoteException;

   WorkSteptemplate getStartWorkStepTemplate(String var1, String var2) throws RemoteException;
}
