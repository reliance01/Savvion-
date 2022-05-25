/**
 * BitlyURLLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 16, 2010 (05:58:21 PDT) WSDL2Java emitter.
 */

package bitly.services;

public class BitlyURLLocator extends org.apache.axis.client.Service implements bitly.services.BitlyURL {

    public BitlyURLLocator() {
    }


    public BitlyURLLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BitlyURLLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IBitlyURL
    private java.lang.String BasicHttpBinding_IBitlyURL_address = "http://10.65.15.71:8090/Bitly/Service.svc";

    public java.lang.String getBasicHttpBinding_IBitlyURLAddress() {
        return BasicHttpBinding_IBitlyURL_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IBitlyURLWSDDServiceName = "BasicHttpBinding_IBitlyURL";

    public java.lang.String getBasicHttpBinding_IBitlyURLWSDDServiceName() {
        return BasicHttpBinding_IBitlyURLWSDDServiceName;
    }

    public void setBasicHttpBinding_IBitlyURLWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IBitlyURLWSDDServiceName = name;
    }

    public bitly.services.IBitlyURL getBasicHttpBinding_IBitlyURL() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IBitlyURL_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IBitlyURL(endpoint);
    }

    public bitly.services.IBitlyURL getBasicHttpBinding_IBitlyURL(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            bitly.services.BasicHttpBinding_IBitlyURLStub _stub = new bitly.services.BasicHttpBinding_IBitlyURLStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IBitlyURLWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IBitlyURLEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IBitlyURL_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (bitly.services.IBitlyURL.class.isAssignableFrom(serviceEndpointInterface)) {
                bitly.services.BasicHttpBinding_IBitlyURLStub _stub = new bitly.services.BasicHttpBinding_IBitlyURLStub(new java.net.URL(BasicHttpBinding_IBitlyURL_address), this);
                _stub.setPortName(getBasicHttpBinding_IBitlyURLWSDDServiceName());
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
        if ("BasicHttpBinding_IBitlyURL".equals(inputPortName)) {
            return getBasicHttpBinding_IBitlyURL();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "BitlyURL");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IBitlyURL"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IBitlyURL".equals(portName)) {
            setBasicHttpBinding_IBitlyURLEndpointAddress(address);
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
