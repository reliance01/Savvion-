/**
 * RpasMakeLivePolicyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rpas;

public class RpasMakeLivePolicyServiceLocator extends org.apache.axis.client.Service implements com.rpas.RpasMakeLivePolicyService {

    public RpasMakeLivePolicyServiceLocator() {
    }


    public RpasMakeLivePolicyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RpasMakeLivePolicyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RpasMakeLivePolicyServiceSoap
    private java.lang.String RpasMakeLivePolicyServiceSoap_address = "http://10.65.15.60/Savvion_RPAS_WS/RpasMakeLivePolicyService.asmx";

    public java.lang.String getRpasMakeLivePolicyServiceSoapAddress() {
        return RpasMakeLivePolicyServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RpasMakeLivePolicyServiceSoapWSDDServiceName = "RpasMakeLivePolicyServiceSoap";

    public java.lang.String getRpasMakeLivePolicyServiceSoapWSDDServiceName() {
        return RpasMakeLivePolicyServiceSoapWSDDServiceName;
    }

    public void setRpasMakeLivePolicyServiceSoapWSDDServiceName(java.lang.String name) {
        RpasMakeLivePolicyServiceSoapWSDDServiceName = name;
    }

    public com.rpas.RpasMakeLivePolicyServiceSoap_PortType getRpasMakeLivePolicyServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RpasMakeLivePolicyServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRpasMakeLivePolicyServiceSoap(endpoint);
    }

    public com.rpas.RpasMakeLivePolicyServiceSoap_PortType getRpasMakeLivePolicyServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rpas.RpasMakeLivePolicyServiceSoap_BindingStub _stub = new com.rpas.RpasMakeLivePolicyServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getRpasMakeLivePolicyServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRpasMakeLivePolicyServiceSoapEndpointAddress(java.lang.String address) {
        RpasMakeLivePolicyServiceSoap_address = address;
    }


    // Use to get a proxy class for RpasMakeLivePolicyServiceSoap12
    private java.lang.String RpasMakeLivePolicyServiceSoap12_address = "http://10.65.15.60/Savvion_RPAS_WS/RpasMakeLivePolicyService.asmx";

    public java.lang.String getRpasMakeLivePolicyServiceSoap12Address() {
        return RpasMakeLivePolicyServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RpasMakeLivePolicyServiceSoap12WSDDServiceName = "RpasMakeLivePolicyServiceSoap12";

    public java.lang.String getRpasMakeLivePolicyServiceSoap12WSDDServiceName() {
        return RpasMakeLivePolicyServiceSoap12WSDDServiceName;
    }

    public void setRpasMakeLivePolicyServiceSoap12WSDDServiceName(java.lang.String name) {
        RpasMakeLivePolicyServiceSoap12WSDDServiceName = name;
    }

    public com.rpas.RpasMakeLivePolicyServiceSoap_PortType getRpasMakeLivePolicyServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RpasMakeLivePolicyServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRpasMakeLivePolicyServiceSoap12(endpoint);
    }

    public com.rpas.RpasMakeLivePolicyServiceSoap_PortType getRpasMakeLivePolicyServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.rpas.RpasMakeLivePolicyServiceSoap12Stub _stub = new com.rpas.RpasMakeLivePolicyServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getRpasMakeLivePolicyServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRpasMakeLivePolicyServiceSoap12EndpointAddress(java.lang.String address) {
        RpasMakeLivePolicyServiceSoap12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.rpas.RpasMakeLivePolicyServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rpas.RpasMakeLivePolicyServiceSoap_BindingStub _stub = new com.rpas.RpasMakeLivePolicyServiceSoap_BindingStub(new java.net.URL(RpasMakeLivePolicyServiceSoap_address), this);
                _stub.setPortName(getRpasMakeLivePolicyServiceSoapWSDDServiceName());
                return _stub;
            }
            if (com.rpas.RpasMakeLivePolicyServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.rpas.RpasMakeLivePolicyServiceSoap12Stub _stub = new com.rpas.RpasMakeLivePolicyServiceSoap12Stub(new java.net.URL(RpasMakeLivePolicyServiceSoap12_address), this);
                _stub.setPortName(getRpasMakeLivePolicyServiceSoap12WSDDServiceName());
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
        if ("RpasMakeLivePolicyServiceSoap".equals(inputPortName)) {
            return getRpasMakeLivePolicyServiceSoap();
        }
        else if ("RpasMakeLivePolicyServiceSoap12".equals(inputPortName)) {
            return getRpasMakeLivePolicyServiceSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "RpasMakeLivePolicyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "RpasMakeLivePolicyServiceSoap"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "RpasMakeLivePolicyServiceSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RpasMakeLivePolicyServiceSoap".equals(portName)) {
            setRpasMakeLivePolicyServiceSoapEndpointAddress(address);
        }
        else 
if ("RpasMakeLivePolicyServiceSoap12".equals(portName)) {
            setRpasMakeLivePolicyServiceSoap12EndpointAddress(address);
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
