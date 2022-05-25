package app.cms;

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

public class IsClaimExistsLocator extends Service implements IsClaimExists {
   private String IsClaimExistsSoap12_address = "http://10.65.15.85:8181/IsClaimExists.asmx";
   private String IsClaimExistsSoap12WSDDServiceName = "IsClaimExistsSoap12";
   private String IsClaimExistsSoap_address = "http://10.65.15.85:8181/IsClaimExists.asmx";
   private String IsClaimExistsSoapWSDDServiceName = "IsClaimExistsSoap";
   private HashSet ports = null;

   public IsClaimExistsLocator() {
   }

   public IsClaimExistsLocator(EngineConfiguration config) {
      super(config);
   }

   public IsClaimExistsLocator(String wsdlLoc, QName sName) throws ServiceException {
      super(wsdlLoc, sName);
   }

   public String getIsClaimExistsSoap12Address() {
      return this.IsClaimExistsSoap12_address;
   }

   public String getIsClaimExistsSoap12WSDDServiceName() {
      return this.IsClaimExistsSoap12WSDDServiceName;
   }

   public void setIsClaimExistsSoap12WSDDServiceName(String name) {
      this.IsClaimExistsSoap12WSDDServiceName = name;
   }

   public IsClaimExistsSoap_PortType getIsClaimExistsSoap12() throws ServiceException {
      URL endpoint;
      try {
         endpoint = new URL(this.IsClaimExistsSoap12_address);
      } catch (MalformedURLException var3) {
         throw new ServiceException(var3);
      }

      return this.getIsClaimExistsSoap12(endpoint);
   }

   public IsClaimExistsSoap_PortType getIsClaimExistsSoap12(URL portAddress) throws ServiceException {
      try {
         IsClaimExistsSoap12Stub _stub = new IsClaimExistsSoap12Stub(portAddress, this);
         _stub.setPortName(this.getIsClaimExistsSoap12WSDDServiceName());
         return _stub;
      } catch (AxisFault var3) {
         return null;
      }
   }

   public void setIsClaimExistsSoap12EndpointAddress(String address) {
      this.IsClaimExistsSoap12_address = address;
   }

   public String getIsClaimExistsSoapAddress() {
      return this.IsClaimExistsSoap_address;
   }

   public String getIsClaimExistsSoapWSDDServiceName() {
      return this.IsClaimExistsSoapWSDDServiceName;
   }

   public void setIsClaimExistsSoapWSDDServiceName(String name) {
      this.IsClaimExistsSoapWSDDServiceName = name;
   }

   public IsClaimExistsSoap_PortType getIsClaimExistsSoap() throws ServiceException {
      URL endpoint;
      try {
         endpoint = new URL(this.IsClaimExistsSoap_address);
      } catch (MalformedURLException var3) {
         throw new ServiceException(var3);
      }

      return this.getIsClaimExistsSoap(endpoint);
   }

   public IsClaimExistsSoap_PortType getIsClaimExistsSoap(URL portAddress) throws ServiceException {
      try {
         IsClaimExistsSoap_BindingStub _stub = new IsClaimExistsSoap_BindingStub(portAddress, this);
         _stub.setPortName(this.getIsClaimExistsSoapWSDDServiceName());
         return _stub;
      } catch (AxisFault var3) {
         return null;
      }
   }

   public void setIsClaimExistsSoapEndpointAddress(String address) {
      this.IsClaimExistsSoap_address = address;
   }

   public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
      try {
         if (IsClaimExistsSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            IsClaimExistsSoap12Stub _stub = new IsClaimExistsSoap12Stub(new URL(this.IsClaimExistsSoap12_address), this);
            _stub.setPortName(this.getIsClaimExistsSoap12WSDDServiceName());
            return _stub;
         }

         if (IsClaimExistsSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            IsClaimExistsSoap_BindingStub _stub = new IsClaimExistsSoap_BindingStub(new URL(this.IsClaimExistsSoap_address), this);
            _stub.setPortName(this.getIsClaimExistsSoapWSDDServiceName());
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
         if ("IsClaimExistsSoap12".equals(inputPortName)) {
            return this.getIsClaimExistsSoap12();
         } else if ("IsClaimExistsSoap".equals(inputPortName)) {
            return this.getIsClaimExistsSoap();
         } else {
            Remote _stub = this.getPort(serviceEndpointInterface);
            ((Stub)_stub).setPortName(portName);
            return _stub;
         }
      }
   }

   public QName getServiceName() {
      return new QName("http://tempuri.org/", "IsClaimExists");
   }

   public Iterator getPorts() {
      if (this.ports == null) {
         this.ports = new HashSet();
         this.ports.add(new QName("http://tempuri.org/", "IsClaimExistsSoap12"));
         this.ports.add(new QName("http://tempuri.org/", "IsClaimExistsSoap"));
      }

      return this.ports.iterator();
   }

   public void setEndpointAddress(String portName, String address) throws ServiceException {
      if ("IsClaimExistsSoap12".equals(portName)) {
         this.setIsClaimExistsSoap12EndpointAddress(address);
      } else {
         if (!"IsClaimExistsSoap".equals(portName)) {
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
         }

         this.setIsClaimExistsSoapEndpointAddress(address);
      }

   }

   public void setEndpointAddress(QName portName, String address) throws ServiceException {
      this.setEndpointAddress(portName.getLocalPart(), address);
   }
}
