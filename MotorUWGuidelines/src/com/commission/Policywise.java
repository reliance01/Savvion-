/**
 * Policywise.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.commission;

public interface Policywise extends javax.xml.rpc.Service {
    public java.lang.String getPolicywiseSoap12Address();

    public com.commission.PolicywiseSoap_PortType getPolicywiseSoap12() throws javax.xml.rpc.ServiceException;

    public com.commission.PolicywiseSoap_PortType getPolicywiseSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getPolicywiseSoapAddress();

    public com.commission.PolicywiseSoap_PortType getPolicywiseSoap() throws javax.xml.rpc.ServiceException;

    public com.commission.PolicywiseSoap_PortType getPolicywiseSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
