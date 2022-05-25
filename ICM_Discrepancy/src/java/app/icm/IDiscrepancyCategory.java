/**
 * IDiscrepancyCategory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package app.icm;

public interface IDiscrepancyCategory extends java.rmi.Remote {
    public java.lang.Boolean addDiscrepancyDetailsService(java.lang.String strProposalNo, app.icm.DiscrepancyCategoryBO[] _discrepancyBOList) throws java.rmi.RemoteException;
    public java.lang.String resolveDiscrepancy(app.icm.ProposalDetailsBO objProposalDetailsBO) throws java.rmi.RemoteException;
    public java.lang.String resolveDiscrepancySavvion(java.lang.String strProposalNo, java.lang.String strResolveRemark, java.lang.String strusername, java.lang.String strQcResolvedDate, java.lang.Integer subDiscrepancyID) throws java.rmi.RemoteException;
    public java.lang.String checkDiscrepancyExists(java.lang.String strProposalNo) throws java.rmi.RemoteException;
    public java.lang.String raisedRemarkService(java.lang.String strProposalNo, java.lang.String strRaisedRemark) throws java.rmi.RemoteException;
}
