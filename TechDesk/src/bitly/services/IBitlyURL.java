/**
 * IBitlyURL.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 16, 2010 (05:58:21 PDT) WSDL2Java emitter.
 */

package bitly.services;

public interface IBitlyURL extends java.rmi.Remote {
    public java.lang.String getShortURL(java.lang.String strLongURL, java.lang.String strIdentifierType, java.lang.String strIdentifierValue, java.lang.String strmobileNo) throws java.rmi.RemoteException;
    public java.lang.String getShortURLGoogle(java.lang.String strLongURL, java.lang.String strIdentifierType, java.lang.String strIdentifierValue, java.lang.String strmobileNo) throws java.rmi.RemoteException;
}
