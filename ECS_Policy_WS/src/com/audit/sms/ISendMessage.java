package com.audit.sms;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISendMessage extends Remote {
   String send(SingleMessage var1) throws RemoteException;
}
