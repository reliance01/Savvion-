/**
 * Service1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.savvion.mom;

public interface Service1 extends javax.xml.rpc.Service {
    public java.lang.String getService1Soap12Address();

    public com.savvion.mom.Service1Soap_PortType getService1Soap12() throws javax.xml.rpc.ServiceException;

    public com.savvion.mom.Service1Soap_PortType getService1Soap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getService1SoapAddress();

    public com.savvion.mom.Service1Soap_PortType getService1Soap() throws javax.xml.rpc.ServiceException;

    public com.savvion.mom.Service1Soap_PortType getService1Soap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
