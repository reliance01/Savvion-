package app.cms;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface IsClaimExists extends Service {
   String getIsClaimExistsSoap12Address();

   IsClaimExistsSoap_PortType getIsClaimExistsSoap12() throws ServiceException;

   IsClaimExistsSoap_PortType getIsClaimExistsSoap12(URL var1) throws ServiceException;

   String getIsClaimExistsSoapAddress();

   IsClaimExistsSoap_PortType getIsClaimExistsSoap() throws ServiceException;

   IsClaimExistsSoap_PortType getIsClaimExistsSoap(URL var1) throws ServiceException;
}
