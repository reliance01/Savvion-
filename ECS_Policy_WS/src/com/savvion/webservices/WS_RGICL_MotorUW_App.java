package com.savvion.webservices;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WS_RGICL_MotorUW_App extends Remote {
   String getUnderWriterDetails(String var1) throws RemoteException;

   ResponseObject initiateMotorIRPASFlow(WorkItemObject var1) throws RemoteException;

   ResponseObject getIRPASMotorTaskList(WorkItemObject var1) throws RemoteException;

   boolean checkProposalExists(String var1) throws RemoteException;

   String deleteProposalQuoteFromSavvion(String var1) throws RemoteException;

   String getActionDetails(String var1) throws RemoteException;

   String getRuntimeActionDetails(String var1, String var2) throws RemoteException;

   ResponseObject updateMotorIRPASSetUpDataSlots(WorkItemObject var1) throws RemoteException;

   String getHistory(String var1) throws RemoteException;

   void main(String[] var1) throws RemoteException;
}
