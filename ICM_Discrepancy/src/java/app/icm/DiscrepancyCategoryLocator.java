/**
 * DiscrepancyCategoryLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package app.icm;

public class DiscrepancyCategoryLocator extends org.apache.axis.client.Service implements app.icm.DiscrepancyCategory {

    public DiscrepancyCategoryLocator() {
    }


    public DiscrepancyCategoryLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DiscrepancyCategoryLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IDiscrepancyCategory
    private java.lang.String BasicHttpBinding_IDiscrepancyCategory_address = "http://rgiuicmdd:91/DiscrepancyCategory.svc";

    public java.lang.String getBasicHttpBinding_IDiscrepancyCategoryAddress() {
        return BasicHttpBinding_IDiscrepancyCategory_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IDiscrepancyCategoryWSDDServiceName = "BasicHttpBinding_IDiscrepancyCategory";

    public java.lang.String getBasicHttpBinding_IDiscrepancyCategoryWSDDServiceName() {
        return BasicHttpBinding_IDiscrepancyCategoryWSDDServiceName;
    }

    public void setBasicHttpBinding_IDiscrepancyCategoryWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IDiscrepancyCategoryWSDDServiceName = name;
    }

    public app.icm.IDiscrepancyCategory getBasicHttpBinding_IDiscrepancyCategory() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IDiscrepancyCategory_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IDiscrepancyCategory(endpoint);
    }

    public app.icm.IDiscrepancyCategory getBasicHttpBinding_IDiscrepancyCategory(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            app.icm.BasicHttpBinding_IDiscrepancyCategoryStub _stub = new app.icm.BasicHttpBinding_IDiscrepancyCategoryStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IDiscrepancyCategoryWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IDiscrepancyCategoryEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IDiscrepancyCategory_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (app.icm.IDiscrepancyCategory.class.isAssignableFrom(serviceEndpointInterface)) {
                app.icm.BasicHttpBinding_IDiscrepancyCategoryStub _stub = new app.icm.BasicHttpBinding_IDiscrepancyCategoryStub(new java.net.URL(BasicHttpBinding_IDiscrepancyCategory_address), this);
                _stub.setPortName(getBasicHttpBinding_IDiscrepancyCategoryWSDDServiceName());
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
        if ("BasicHttpBinding_IDiscrepancyCategory".equals(inputPortName)) {
            return getBasicHttpBinding_IDiscrepancyCategory();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "DiscrepancyCategory");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IDiscrepancyCategory"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IDiscrepancyCategory".equals(portName)) {
            setBasicHttpBinding_IDiscrepancyCategoryEndpointAddress(address);
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
