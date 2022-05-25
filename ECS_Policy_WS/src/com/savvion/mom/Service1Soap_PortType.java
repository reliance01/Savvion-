package com.savvion.mom;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Service1Soap_PortType extends Remote {
   String helloWorld() throws RemoteException;

   String[] getRegions() throws RemoteException;

   String[] getRegionBranchs(String var1, String var2) throws RemoteException;

   String getRegionFromBranch(String var1) throws RemoteException;

   String[] getBMDetails(String var1) throws RemoteException;

   String[] getBSMDetails(String var1) throws RemoteException;

   String getZHDetails(String var1) throws RemoteException;

   String getEmployeeEmailId(String var1) throws RemoteException;

   String getEmployeeContactNo(String var1) throws RemoteException;
}
