package com.audit.sms;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface SendMessage extends Service {
   String getBasicHttpBinding_ISendMessageAddress();

   ISendMessage getBasicHttpBinding_ISendMessage() throws ServiceException;

   ISendMessage getBasicHttpBinding_ISendMessage(URL var1) throws ServiceException;
}
