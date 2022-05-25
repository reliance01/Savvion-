package com.savvion.mom;

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

public class Service1Locator extends Service implements Service1 {
   private String Service1Soap12_address = "http://10.65.15.160:8081/Service1.asmx";
   private String Service1Soap12WSDDServiceName = "Service1Soap12";
   private String Service1Soap_address = "http://10.65.15.160:8081/Service1.asmx";
   private String Service1SoapWSDDServiceName = "Service1Soap";
   private HashSet ports = null;

   public Service1Locator() {
   }

   public Service1Locator(EngineConfiguration config) {
      super(config);
   }

   public Service1Locator(String wsdlLoc, QName sName) throws ServiceException {
      super(wsdlLoc, sName);
   }

   public String getService1Soap12Address() {
      return this.Service1Soap12_address;
   }

   public String getService1Soap12WSDDServiceName() {
      return this.Service1Soap12WSDDServiceName;
   }

   public void setService1Soap12WSDDServiceName(String name) {
      this.Service1Soap12WSDDServiceName = name;
   }

   public Service1Soap_PortType getService1Soap12() throws ServiceException {
      URL endpoint;
      try {
         endpoint = new URL(this.Service1Soap12_address);
      } catch (MalformedURLException var3) {
         throw new ServiceException(var3);
      }

      return this.getService1Soap12(endpoint);
   }

   public Service1Soap_PortType getService1Soap12(URL portAddress) throws ServiceException {
      try {
         Service1Soap12Stub _stub = new Service1Soap12Stub(portAddress, this);
         _stub.setPortName(this.getService1Soap12WSDDServiceName());
         return _stub;
      } catch (AxisFault var3) {
         return null;
      }
   }

   public void setService1Soap12EndpointAddress(String address) {
      this.Service1Soap12_address = address;
   }

   public String getService1SoapAddress() {
      return this.Service1Soap_address;
   }

   public String getService1SoapWSDDServiceName() {
      return this.Service1SoapWSDDServiceName;
   }

   public void setService1SoapWSDDServiceName(String name) {
      this.Service1SoapWSDDServiceName = name;
   }

   public Service1Soap_PortType getService1Soap() throws ServiceException {
      URL endpoint;
      try {
         endpoint = new URL(this.Service1Soap_address);
      } catch (MalformedURLException var3) {
         throw new ServiceException(var3);
      }

      return this.getService1Soap(endpoint);
   }

   public Service1Soap_PortType getService1Soap(URL portAddress) throws ServiceException {
      try {
         Service1Soap_BindingStub _stub = new Service1Soap_BindingStub(portAddress, this);
         _stub.setPortName(this.getService1SoapWSDDServiceName());
         return _stub;
      } catch (AxisFault var3) {
         return null;
      }
   }

   public void setService1SoapEndpointAddress(String address) {
      this.Service1Soap_address = address;
   }

   public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
      try {
         if (Service1Soap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            Service1Soap12Stub _stub = new Service1Soap12Stub(new URL(this.Service1Soap12_address), this);
            _stub.setPortName(this.getService1Soap12WSDDServiceName());
            return _stub;
         }

         if (Service1Soap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
            Service1Soap_BindingStub _stub = new Service1Soap_BindingStub(new URL(this.Service1Soap_address), this);
            _stub.setPortName(this.getService1SoapWSDDServiceName());
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
         if ("Service1Soap12".equals(inputPortName)) {
            return this.getService1Soap12();
         } else if ("Service1Soap".equals(inputPortName)) {
            return this.getService1Soap();
         } else {
            Remote _stub = this.getPort(serviceEndpointInterface);
            ((Stub)_stub).setPortName(portName);
            return _stub;
         }
      }
   }

   public QName getServiceName() {
      return new QName("http://tempuri.org/", "Service1");
   }

   public Iterator getPorts() {
      if (this.ports == null) {
         this.ports = new HashSet();
         this.ports.add(new QName("http://tempuri.org/", "Service1Soap12"));
         this.ports.add(new QName("http://tempuri.org/", "Service1Soap"));
      }

      return this.ports.iterator();
   }

   public void setEndpointAddress(String portName, String address) throws ServiceException {
      if ("Service1Soap12".equals(portName)) {
         this.setService1Soap12EndpointAddress(address);
      } else {
         if (!"Service1Soap".equals(portName)) {
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
         }

         this.setService1SoapEndpointAddress(address);
      }

   }

   public void setEndpointAddress(QName portName, String address) throws ServiceException {
      this.setEndpointAddress(portName.getLocalPart(), address);
   }
}
