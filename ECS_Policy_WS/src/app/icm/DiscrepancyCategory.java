package app.icm;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface DiscrepancyCategory extends Service {
   String getBasicHttpBinding_IDiscrepancyCategoryAddress();

   IDiscrepancyCategory getBasicHttpBinding_IDiscrepancyCategory() throws ServiceException;

   IDiscrepancyCategory getBasicHttpBinding_IDiscrepancyCategory(URL var1) throws ServiceException;
}
