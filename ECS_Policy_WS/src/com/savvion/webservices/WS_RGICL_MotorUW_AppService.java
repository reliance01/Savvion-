package com.savvion.webservices;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface WS_RGICL_MotorUW_AppService extends Service {
   String getRGICL_MotorUW_AppAddress();

   com.savvion.webservices.WS_RGICL_MotorUW_App getRGICL_MotorUW_App() throws ServiceException;

   com.savvion.webservices.WS_RGICL_MotorUW_App getRGICL_MotorUW_App(URL var1) throws ServiceException;
}
