/**
 * PolicywiseLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.commission;

public class PolicywiseLocator extends org.apache.axis.client.Service implements com.commission.Policywise {

    public PolicywiseLocator() {
    }


    public PolicywiseLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PolicywiseLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PolicywiseSoap12
    private java.lang.String PolicywiseSoap12_address = "http://10.65.15.119:8081/Webservice/Policywise.asmx";

    public java.lang.String getPolicywiseSoap12Address() {
        return PolicywiseSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PolicywiseSoap12WSDDServiceName = "PolicywiseSoap12";

    public java.lang.String getPolicywiseSoap12WSDDServiceName() {
        return PolicywiseSoap12WSDDServiceName;
    }

    public void setPolicywiseSoap12WSDDServiceName(java.lang.String name) {
        PolicywiseSoap12WSDDServiceName = name;
    }

    public com.commission.PolicywiseSoap_PortType getPolicywiseSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PolicywiseSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPolicywiseSoap12(endpoint);
    }

    public com.commission.PolicywiseSoap_PortType getPolicywiseSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.commission.PolicywiseSoap12Stub _stub = new com.commission.PolicywiseSoap12Stub(portAddress, this);
            _stub.setPortName(getPolicywiseSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPolicywiseSoap12EndpointAddress(java.lang.String address) {
        PolicywiseSoap12_address = address;
    }


    // Use to get a proxy class for PolicywiseSoap
    private java.lang.String PolicywiseSoap_address = "http://10.65.15.119:8081/Webservice/Policywise.asmx";

    public java.lang.String getPolicywiseSoapAddress() {
        return PolicywiseSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PolicywiseSoapWSDDServiceName = "PolicywiseSoap";

    public java.lang.String getPolicywiseSoapWSDDServiceName() {
        return PolicywiseSoapWSDDServiceName;
    }

    public void setPolicywiseSoapWSDDServiceName(java.lang.String name) {
        PolicywiseSoapWSDDServiceName = name;
    }

    public com.commission.PolicywiseSoap_PortType getPolicywiseSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PolicywiseSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPolicywiseSoap(endpoint);
    }

    public com.commission.PolicywiseSoap_PortType getPolicywiseSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.commission.PolicywiseSoap_BindingStub _stub = new com.commission.PolicywiseSoap_BindingStub(portAddress, this);
            _stub.setPortName(getPolicywiseSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPolicywiseSoapEndpointAddress(java.lang.String address) {
        PolicywiseSoap_address = address;
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
            if (com.commission.PolicywiseSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.commission.PolicywiseSoap12Stub _stub = new com.commission.PolicywiseSoap12Stub(new java.net.URL(PolicywiseSoap12_address), this);
                _stub.setPortName(getPolicywiseSoap12WSDDServiceName());
                return _stub;
            }
            if (com.commission.PolicywiseSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.commission.PolicywiseSoap_BindingStub _stub = new com.commission.PolicywiseSoap_BindingStub(new java.net.URL(PolicywiseSoap_address), this);
                _stub.setPortName(getPolicywiseSoapWSDDServiceName());
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
        if ("PolicywiseSoap12".equals(inputPortName)) {
            return getPolicywiseSoap12();
        }
        else if ("PolicywiseSoap".equals(inputPortName)) {
            return getPolicywiseSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "Policywise");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "PolicywiseSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "PolicywiseSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PolicywiseSoap12".equals(portName)) {
            setPolicywiseSoap12EndpointAddress(address);
        }
        else 
if ("PolicywiseSoap".equals(portName)) {
            setPolicywiseSoapEndpointAddress(address);
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
