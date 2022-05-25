package com.savvion.webservices;

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

public class WS_RGICL_MotorUW_AppServiceLocator extends Service implements WS_RGICL_MotorUW_AppService {
   private String RGICL_MotorUW_App_address = "http://bpm.reliancegeneral.co.in/sbm/services/RGICL_MotorUW_App";
   private String RGICL_MotorUW_AppWSDDServiceName = "RGICL_MotorUW_App";
   private HashSet ports = null;

   public WS_RGICL_MotorUW_AppServiceLocator() {
   }

   public WS_RGICL_MotorUW_AppServiceLocator(EngineConfiguration config) {
      super(config);
   }

   public WS_RGICL_MotorUW_AppServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
      super(wsdlLoc, sName);
   }

   public String getRGICL_MotorUW_AppAddress() {
      return this.RGICL_MotorUW_App_address;
   }

   public String getRGICL_MotorUW_AppWSDDServiceName() {
      return this.RGICL_MotorUW_AppWSDDServiceName;
   }

   public void setRGICL_MotorUW_AppWSDDServiceName(String name) {
      this.RGICL_MotorUW_AppWSDDServiceName = name;
   }

   public com.savvion.webservices.WS_RGICL_MotorUW_App getRGICL_MotorUW_App() throws ServiceException {
      URL endpoint;
      try {
         endpoint = new URL(this.RGICL_MotorUW_App_address);
      } catch (MalformedURLException var3) {
         throw new ServiceException(var3);
      }

      return this.getRGICL_MotorUW_App(endpoint);
   }

   public com.savvion.webservices.WS_RGICL_MotorUW_App getRGICL_MotorUW_App(URL portAddress) throws ServiceException {
      try {
         RGICL_MotorUW_AppSoapBindingStub _stub = new RGICL_MotorUW_AppSoapBindingStub(portAddress, this);
         _stub.setPortName(this.getRGICL_MotorUW_AppWSDDServiceName());
         return _stub;
      } catch (AxisFault var3) {
         return null;
      }
   }

   public void setRGICL_MotorUW_AppEndpointAddress(String address) {
      this.RGICL_MotorUW_App_address = address;
   }

   public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
      try {
         if (com.savvion.webservices.WS_RGICL_MotorUW_App.class.isAssignableFrom(serviceEndpointInterface)) {
            RGICL_MotorUW_AppSoapBindingStub _stub = new RGICL_MotorUW_AppSoapBindingStub(new URL(this.RGICL_MotorUW_App_address), this);
            _stub.setPortName(this.getRGICL_MotorUW_AppWSDDServiceName());
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
         if ("RGICL_MotorUW_App".equals(inputPortName)) {
            return this.getRGICL_MotorUW_App();
         } else {
            Remote _stub = this.getPort(serviceEndpointInterface);
            ((Stub)_stub).setPortName(portName);
            return _stub;
         }
      }
   }

   public QName getServiceName() {
      return new QName("http://rgicl.motor.savvion.com", "WS_RGICL_MotorUW_AppService");
   }

   public Iterator getPorts() {
      if (this.ports == null) {
         this.ports = new HashSet();
         this.ports.add(new QName("http://rgicl.motor.savvion.com", "RGICL_MotorUW_App"));
      }

      return this.ports.iterator();
   }

   public void setEndpointAddress(String portName, String address) throws ServiceException {
      if ("RGICL_MotorUW_App".equals(portName)) {
         this.setRGICL_MotorUW_AppEndpointAddress(address);
      } else {
         throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
      }
   }

   public void setEndpointAddress(QName portName, String address) throws ServiceException {
      this.setEndpointAddress(portName.getLocalPart(), address);
   }
}
