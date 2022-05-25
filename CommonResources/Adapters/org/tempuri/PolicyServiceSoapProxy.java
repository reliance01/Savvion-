package org.tempuri;

public class PolicyServiceSoapProxy implements org.tempuri.PolicyServiceSoap {
  private String _endpoint = null;
  private org.tempuri.PolicyServiceSoap policyServiceSoap = null;
  
  public PolicyServiceSoapProxy() {
    _initPolicyServiceSoapProxy();
  }
  
  public PolicyServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initPolicyServiceSoapProxy();
  }
  
  private void _initPolicyServiceSoapProxy() {
    try {
      policyServiceSoap = (new org.tempuri.PolicyServiceLocator()).getPolicyServiceSoap();
      if (policyServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)policyServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)policyServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (policyServiceSoap != null)
      ((javax.xml.rpc.Stub)policyServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.PolicyServiceSoap getPolicyServiceSoap() {
    if (policyServiceSoap == null)
      _initPolicyServiceSoapProxy();
    return policyServiceSoap;
  }
  
  public org.tempuri.ProposalPolicy[] getProposalPolicyidList(java.lang.String sPolicyIds) throws java.rmi.RemoteException{
    if (policyServiceSoap == null)
      _initPolicyServiceSoapProxy();
    return policyServiceSoap.getProposalPolicyidList(sPolicyIds);
  }
  
  
}