package com.audit.sms;

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

public class SendMessageLocator extends Service implements SendMessage {
   private String BasicHttpBinding_ISendMessage_address = "http://relgeninsure3/services/SendMessage.svc";
   private String BasicHttpBinding_ISendMessageWSDDServiceName = "BasicHttpBinding_ISendMessage";
   private HashSet ports = null;

   public SendMessageLocator() {
   }

   public SendMessageLocator(EngineConfiguration config) {
      super(config);
   }

   public SendMessageLocator(String wsdlLoc, QName sName) throws ServiceException {
      super(wsdlLoc, sName);
   }

   public String getBasicHttpBinding_ISendMessageAddress() {
      return this.BasicHttpBinding_ISendMessage_address;
   }

   public String getBasicHttpBinding_ISendMessageWSDDServiceName() {
      return this.BasicHttpBinding_ISendMessageWSDDServiceName;
   }

   public void setBasicHttpBinding_ISendMessageWSDDServiceName(String name) {
      this.BasicHttpBinding_ISendMessageWSDDServiceName = name;
   }

   public ISendMessage getBasicHttpBinding_ISendMessage() throws ServiceException {
      URL endpoint;
      try {
         endpoint = new URL(this.BasicHttpBinding_ISendMessage_address);
      } catch (MalformedURLException var3) {
         throw new ServiceException(var3);
      }

      return this.getBasicHttpBinding_ISendMessage(endpoint);
   }

   public ISendMessage getBasicHttpBinding_ISendMessage(URL portAddress) throws ServiceException {
      try {
         BasicHttpBinding_ISendMessageStub _stub = new BasicHttpBinding_ISendMessageStub(portAddress, this);
         _stub.setPortName(this.getBasicHttpBinding_ISendMessageWSDDServiceName());
         return _stub;
      } catch (AxisFault var3) {
         return null;
      }
   }

   public void setBasicHttpBinding_ISendMessageEndpointAddress(String address) {
      this.BasicHttpBinding_ISendMessage_address = address;
   }

   public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
      try {
         if (ISendMessage.class.isAssignableFrom(serviceEndpointInterface)) {
            BasicHttpBinding_ISendMessageStub _stub = new BasicHttpBinding_ISendMessageStub(new URL(this.BasicHttpBinding_ISendMessage_address), this);
            _stub.setPortName(this.getBasicHttpBinding_ISendMessageWSDDServiceName());
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
         if ("BasicHttpBinding_ISendMessage".equals(inputPortName)) {
            return this.getBasicHttpBinding_ISendMessage();
         } else {
            Remote _stub = this.getPort(serviceEndpointInterface);
            ((Stub)_stub).setPortName(portName);
            return _stub;
         }
      }
   }

   public QName getServiceName() {
      return new QName("http://tempuri.org/", "SendMessage");
   }

   public Iterator getPorts() {
      if (this.ports == null) {
         this.ports = new HashSet();
         this.ports.add(new QName("http://tempuri.org/", "BasicHttpBinding_ISendMessage"));
      }

      return this.ports.iterator();
   }

   public void setEndpointAddress(String portName, String address) throws ServiceException {
      if ("BasicHttpBinding_ISendMessage".equals(portName)) {
         this.setBasicHttpBinding_ISendMessageEndpointAddress(address);
      } else {
         throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
      }
   }

   public void setEndpointAddress(QName portName, String address) throws ServiceException {
      this.setEndpointAddress(portName.getLocalPart(), address);
   }
}
