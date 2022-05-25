package app.icm;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

public class DiscrepancyCategoryLocator extends Service implements DiscrepancyCategory {
   private String BasicHttpBinding_IDiscrepancyCategory_address = "http://icm3app2.reliancecapital.com:91/DiscrepancyCategory.svc";
   private String BasicHttpBinding_IDiscrepancyCategoryWSDDServiceName = "BasicHttpBinding_IDiscrepancyCategory";
   private HashSet ports = null;

   public DiscrepancyCategoryLocator() {
   }

   public DiscrepancyCategoryLocator(EngineConfiguration config) {
      super(config);
   }

   public DiscrepancyCategoryLocator(String wsdlLoc, QName sName) throws ServiceException {
      super(wsdlLoc, sName);
   }

   public String getBasicHttpBinding_IDiscrepancyCategoryAddress() {
      return this.BasicHttpBinding_IDiscrepancyCategory_address;
   }

   public String getBasicHttpBinding_IDiscrepancyCategoryWSDDServiceName() {
      return this.BasicHttpBinding_IDiscrepancyCategoryWSDDServiceName;
   }

   public void setBasicHttpBinding_IDiscrepancyCategoryWSDDServiceName(String name) {
      this.BasicHttpBinding_IDiscrepancyCategoryWSDDServiceName = name;
   }

   public IDiscrepancyCategory getBasicHttpBinding_IDiscrepancyCategory() throws ServiceException {
      URL endpoint;
      try {
         endpoint = new URL(this.BasicHttpBinding_IDiscrepancyCategory_address);
      } catch (MalformedURLException var3) {
         throw new ServiceException(var3);
      }

      return this.getBasicHttpBinding_IDiscrepancyCategory(endpoint);
   }

   public IDiscrepancyCategory getBasicHttpBinding_IDiscrepancyCategory(URL portAddress) throws ServiceException {
      try {
         BasicHttpBinding_IDiscrepancyCategoryStub _stub = new BasicHttpBinding_IDiscrepancyCategoryStub(portAddress, this);
         _stub.setPortName(this.getBasicHttpBinding_IDiscrepancyCategoryWSDDServiceName());
         return _stub;
      } catch (AxisFault var3) {
         return null;
      }
   }

   public void setBasicHttpBinding_IDiscrepancyCategoryEndpointAddress(String address) {
      this.BasicHttpBinding_IDiscrepancyCategory_address = address;
   }

   public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
      try {
         if (IDiscrepancyCategory.class.isAssignableFrom(serviceEndpointInterface)) {
            BasicHttpBinding_IDiscrepancyCategoryStub _stub = new BasicHttpBinding_IDiscrepancyCategoryStub(new URL(this.BasicHttpBinding_IDiscrepancyCategory_address), this);
            _stub.setPortName(this.getBasicHttpBinding_IDiscrepancyCategoryWSDDServiceName());
            return _stub;
         }
      } catch (Throwable var3) {
         throw new ServiceException(var3);
      }

      throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
   }

   public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
      if (portName == null) {
         return this.getPort(serviceEndpointInterface);
      } else {
         String inputPortName = portName.getLocalPart();
         if ("BasicHttpBinding_IDiscrepancyCategory".equals(inputPortName)) {
            return this.getBasicHttpBinding_IDiscrepancyCategory();
         } else {
            Remote _stub = this.getPort(serviceEndpointInterface);
            ((Stub)_stub).setPortName(portName);
            return _stub;
         }
      }
   }

   public QName getServiceName() {
      return new QName("http://tempuri.org/", "DiscrepancyCategory");
   }

   public Iterator getPorts() {
      if (this.ports == null) {
         this.ports = new HashSet();
         this.ports.add(new QName("http://tempuri.org/", "BasicHttpBinding_IDiscrepancyCategory"));
      }

      return this.ports.iterator();
   }

   public void setEndpointAddress(String portName, String address) throws ServiceException {
      if ("BasicHttpBinding_IDiscrepancyCategory".equals(portName)) {
         this.setBasicHttpBinding_IDiscrepancyCategoryEndpointAddress(address);
      } else {
         throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
      }
   }

   public void setEndpointAddress(QName portName, String address) throws ServiceException {
      this.setEndpointAddress(portName.getLocalPart(), address);
   }
}
