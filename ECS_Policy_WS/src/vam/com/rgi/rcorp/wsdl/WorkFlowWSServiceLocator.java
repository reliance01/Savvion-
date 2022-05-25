package vam.com.rgi.rcorp.wsdl;

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

public class WorkFlowWSServiceLocator extends Service implements WorkFlowWSService {
   private String BizLogic1_address = "http://localhost:18793/sbm/services/BizLogic1";
   private String BizLogic1WSDDServiceName = "BizLogic1";
   private HashSet ports = null;

   public WorkFlowWSServiceLocator() {
   }

   public WorkFlowWSServiceLocator(EngineConfiguration config) {
      super(config);
   }

   public WorkFlowWSServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
      super(wsdlLoc, sName);
   }

   public String getBizLogic1Address() {
      return this.BizLogic1_address;
   }

   public String getBizLogic1WSDDServiceName() {
      return this.BizLogic1WSDDServiceName;
   }

   public void setBizLogic1WSDDServiceName(String name) {
      this.BizLogic1WSDDServiceName = name;
   }

   public WorkFlowWS getBizLogic1() throws ServiceException {
      URL endpoint;
      try {
         endpoint = new URL(this.BizLogic1_address);
      } catch (MalformedURLException var3) {
         throw new ServiceException(var3);
      }

      return this.getBizLogic1(endpoint);
   }

   public WorkFlowWS getBizLogic1(URL portAddress) throws ServiceException {
      try {
         BizLogic1SoapBindingStub _stub = new BizLogic1SoapBindingStub(portAddress, this);
         _stub.setPortName(this.getBizLogic1WSDDServiceName());
         return _stub;
      } catch (AxisFault var3) {
         return null;
      }
   }

   public void setBizLogic1EndpointAddress(String address) {
      this.BizLogic1_address = address;
   }

   public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
      try {
         if (WorkFlowWS.class.isAssignableFrom(serviceEndpointInterface)) {
            BizLogic1SoapBindingStub _stub = new BizLogic1SoapBindingStub(new URL(this.BizLogic1_address), this);
            _stub.setPortName(this.getBizLogic1WSDDServiceName());
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
         if ("BizLogic1".equals(inputPortName)) {
            return this.getBizLogic1();
         } else {
            Remote _stub = this.getPort(serviceEndpointInterface);
            ((Stub)_stub).setPortName(portName);
            return _stub;
         }
      }
   }

   public QName getServiceName() {
      return new QName("http://workflow.webservice.savvion.com", "WorkFlowWSService");
   }

   public Iterator getPorts() {
      if (this.ports == null) {
         this.ports = new HashSet();
         this.ports.add(new QName("http://workflow.webservice.savvion.com", "BizLogic1"));
      }

      return this.ports.iterator();
   }

   public void setEndpointAddress(String portName, String address) throws ServiceException {
      if ("BizLogic1".equals(portName)) {
         this.setBizLogic1EndpointAddress(address);
      } else {
         throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
      }
   }

   public void setEndpointAddress(QName portName, String address) throws ServiceException {
      this.setEndpointAddress(portName.getLocalPart(), address);
   }
}
