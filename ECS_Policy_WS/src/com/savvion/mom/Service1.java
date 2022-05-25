package com.savvion.mom;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface Service1 extends Service {
   String getService1Soap12Address();

   Service1Soap_PortType getService1Soap12() throws ServiceException;

   Service1Soap_PortType getService1Soap12(URL var1) throws ServiceException;

   String getService1SoapAddress();

   Service1Soap_PortType getService1Soap() throws ServiceException;

   Service1Soap_PortType getService1Soap(URL var1) throws ServiceException;
}
