package vam.com.rgi.rcorp.wsdl;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface WorkFlowWSService extends Service {
   String getBizLogic1Address();

   WorkFlowWS getBizLogic1() throws ServiceException;

   WorkFlowWS getBizLogic1(URL var1) throws ServiceException;
}
