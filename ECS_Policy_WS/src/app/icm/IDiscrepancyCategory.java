package app.icm;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDiscrepancyCategory extends Remote {
   Boolean addDiscrepancyDetailsService(String var1, DiscrepancyCategoryBO[] var2) throws RemoteException;

   String resolveDiscrepancy(ProposalDetailsBO var1) throws RemoteException;

   String resolveDiscrepancySavvion(String var1, String var2, String var3, String var4, Integer var5) throws RemoteException;

   String checkDiscrepancyExists(String var1) throws RemoteException;

   String raisedRemarkService(String var1, String var2) throws RemoteException;
}
