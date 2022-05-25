/**
 * Service1Soap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.savvion.mom;

public interface Service1Soap_PortType extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public java.lang.String[] getRegions() throws java.rmi.RemoteException;
    public java.lang.String[] getRegionBranchs(java.lang.String regionName, java.lang.String branchType) throws java.rmi.RemoteException;
    public java.lang.String getRegionFromBranch(java.lang.String branchName) throws java.rmi.RemoteException;
    public java.lang.String[] getBMDetails(java.lang.String branchName) throws java.rmi.RemoteException;
    public java.lang.String getRHDetails(java.lang.String branchCode) throws java.rmi.RemoteException;
    public java.lang.String getRHDetailsForICMDiscrepancy(java.lang.String branchCode, java.lang.String productType, java.lang.String clsName) throws java.rmi.RemoteException;
    public java.lang.String[] getBSMDetails(java.lang.String branchName) throws java.rmi.RemoteException;
    public java.lang.String getZHDetails(java.lang.String regionName) throws java.rmi.RemoteException;
    public java.lang.String getEmployeeEmailId(java.lang.String employeeCode) throws java.rmi.RemoteException;
    public java.lang.String getEmployeeContactNo(java.lang.String employeeCode) throws java.rmi.RemoteException;
}
