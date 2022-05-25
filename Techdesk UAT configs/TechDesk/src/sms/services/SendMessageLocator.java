/**
 * SendMessageLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 16, 2010 (05:58:21 PDT) WSDL2Java emitter.
 */

package sms.services;

public class SendMessageLocator extends org.apache.axis.client.Service implements sms.services.SendMessage {

    public SendMessageLocator() {
    }


    public SendMessageLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SendMessageLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_ISendMessage
    private java.lang.String BasicHttpBinding_ISendMessage_address = "http://relgeninsure3/services/SendMessage.svc";

    public java.lang.String getBasicHttpBinding_ISendMessageAddress() {
        return BasicHttpBinding_ISendMessage_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_ISendMessageWSDDServiceName = "BasicHttpBinding_ISendMessage";

    public java.lang.String getBasicHttpBinding_ISendMessageWSDDServiceName() {
        return BasicHttpBinding_ISendMessageWSDDServiceName;
    }

    public void setBasicHttpBinding_ISendMessageWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_ISendMessageWSDDServiceName = name;
    }

    public sms.services.ISendMessage getBasicHttpBinding_ISendMessage() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_ISendMessage_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_ISendMessage(endpoint);
    }

    public sms.services.ISendMessage getBasicHttpBinding_ISendMessage(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            sms.services.BasicHttpBinding_ISendMessageStub _stub = new sms.services.BasicHttpBinding_ISendMessageStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_ISendMessageWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_ISendMessageEndpointAddress(java.lang.String address) {
        BasicHttpBinding_ISendMessage_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (sms.services.ISendMessage.class.isAssignableFrom(serviceEndpointInterface)) {
                sms.services.BasicHttpBinding_ISendMessageStub _stub = new sms.services.BasicHttpBinding_ISendMessageStub(new java.net.URL(BasicHttpBinding_ISendMessage_address), this);
                _stub.setPortName(getBasicHttpBinding_ISendMessageWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_ISendMessage".equals(inputPortName)) {
            return getBasicHttpBinding_ISendMessage();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "SendMessage");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_ISendMessage"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_ISendMessage".equals(portName)) {
            setBasicHttpBinding_ISendMessageEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
