package app.cms;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IsClaimExistsSoap_PortType extends Remote {
   CheckClaimExistOnPolicyResponseCheckClaimExistOnPolicyResult checkClaimExistOnPolicy(String var1) throws RemoteException;

   String isClaimExist(String var1, String var2) throws RemoteException;

   StatusResponce getClaimStatusForPolicy(String var1) throws RemoteException;
}
