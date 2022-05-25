package com.agent.ejb.initiateagent;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import com.agent.ejb.mail.Mailer;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;

public class CallUpdation {

	private final String blUser = "rgicl";
	private final String blPassword = "rgicl";
	
	private Connection con = null;
	
	private CallableStatement cstmt = null;

	BLServer blServer = null;
	Session blSession = null;

	PreparedStatement pstmt = null;
	GetResource rsc = null;
	
	
	
//	/**
//	 *  This method will add BM & immediate Manager Remarks In Custom Table  
//	 * @param piID
//	 */
//	public void addBmRemarks(long piID) {
//		System.out.println("in BM addRemarks method : ");
//	try {
//		blServer = BLClientUtil.getBizLogicServer();
//		blSession = blServer.connect(blUser, blPassword);
//		ProcessInstance pi = blServer.getProcessInstance(blSession, piID);
//		//rsc = new GetResource();
//		String query = "INSERT INTO AGENT_IMMEDIATEMANAGERREMARK "
//				+ "( PROCESS_INSTANCE_ID, BM_REMARKS, IMMEDIATEMANAGER_REMARKS, CREATED_DATE) values (?,?,?,?)";
//		con = GetResource.getDBConnection();
//		pstmt = con.prepareStatement(query);
//		pstmt.setLong(1, piID);
//		pstmt.setString(2, (String) pi.getDataSlotValue("bmRemark"));
//		pstmt.setString(3, (String) pi.getDataSlotValue("immediateManagerRemarks"));
//		pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date()
//		.getTime()));
//		
//		
//		pstmt.executeUpdate();
//
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw new RuntimeException("Error in adding BM Remark Details : "
//				+ e.getMessage());
//	} finally {
//		try {
//			if (blSession != null) {
//				blServer.disConnect(blSession);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException("BLSession closing error : "
//					+ e.getMessage());
//		}
//		GetResource.releaseResource(con, pstmt);
//	}
//}
	
	public void updateEscalationComplete(long piid)
	{
		try
		{
			System.out.println("Inside updateEscalationComplete method !!");
			Connection con = null;
			PreparedStatement pstmt = null;
			
			String query = " UPDATE AGENT_ESCALATION_TBL SET STATUS = ? WHERE PROCESS_INSTANCE_ID = ? AND STATUS = 'Pending'";
			con = GetResource.getDBConnection();
			pstmt = con.prepareCall(query);
			pstmt.setString(1, "Complete");
			pstmt.setLong(2, piid);
			pstmt.executeUpdate();
			
		}catch(Exception e)
		{
			System.out.println("Error while updating Performer escalation flag" + e.getMessage());
		}
		finally
		{
			GetResource.releaseResource(con, pstmt);
		}
	}

	 public void insertPerformerEscalation(long piid, String performer, String role, int days)
	    {
		 
		 try
		 {
		 Connection con = null;
		 PreparedStatement pstmt = null;
		 System.out.println("Inside insertPerformerEscalation method");	 
		 Mailer objmailer = new Mailer();
		 java.util.Date currentDate = new Date();
		 java.util.Date javaDate = objmailer.EscalationDate(days);
		 java.sql.Date escalateDate = new java.sql.Date(javaDate.getTime());
		 java.sql.Date assignedDate = new java.sql.Date(currentDate.getTime());
		 
		 String query = " INSERT INTO AGENT_ESCALATION_TBL VALUES (?,?,?,?,?,?,?) ";
		 con = GetResource.getDBConnection();
		 pstmt = con.prepareStatement(query);
		 pstmt.setLong(1, piid);
         pstmt.setString(2, performer.trim());
         pstmt.setString(3, role.trim());
         pstmt.setDate(4, assignedDate);
         pstmt.setDate(5, escalateDate);
         pstmt.setInt(6, 0);
         pstmt.setString(7, "Pending");
         pstmt.executeUpdate();
		 
		 }catch(Exception e)
		 {
			System.out.println("Error while inserting Performer Escalation Matrix  "); 
			 
		 }finally
		 {
			 GetResource.releaseResource(con, pstmt);
		 }
	    	
	    }
	
	 public void setPerformerEscalation(long piID)
	 {
		 try
		 {
		 System.out.println("Inside setPerformerEscalation method!!");
		 BLServer blserver = BLClientUtil.getBizLogicServer();
		 Session blsession = blserver.connect("rgicl", "rgicl");
		 ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
		 String performer = pi.getDataSlot("bm").toString();
		 String role = pi.getDataSlot("role").toString();
		 
		 AgentFlowProcess obj = new AgentFlowProcess();
		 String defaultId = obj.getMandatoryInfo().split(",")[1].trim();
		 if(!performer.equals(defaultId))
		 {
			 insertPerformerEscalation(piID, performer, role, 5);
		 }
		 
		 }catch(Exception e)
		 {
			 System.out.println("Error wihle calling setPerformerEscalation");
		 }
		 
	 }
	
	/**
	 * This method will call a SP and update BM & Immediate Manager remarks in agent_immediatemanagerremark table 
	 * 
	 */
	public void addBmRemark(long piID, String performer)
	{
		System.out.println("Inside addBmRemark Method !!!");
		try
		{
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(blUser, blPassword);
			ProcessInstance pi = blServer.getProcessInstance(blSession, piID);
			
			con = GetResource.getDBConnection();
			cstmt = con.prepareCall("{call agent_setbmremarks(?,?,?,?)}");
			cstmt.setLong(1, piID);
			cstmt.setString(2, (String) pi.getDataSlotValue("bmRemark"));
			cstmt.setString(3, (String) pi.getDataSlotValue("immediateManagerRemarks"));
			cstmt.setString(4, performer);
			cstmt.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("Error in adding BM Remark Details : " + "Performer is " + performer + "Piid is" +piID + e.getMessage());
			
		}
		finally
		{
			try {
				if (blSession != null) {
					blServer.disConnect(blSession);
				}
			} catch (Exception e) {
				throw new RuntimeException("BLSession closing error : "
						+ e.getMessage());
			}
			GetResource.releaseResource(con, cstmt);
			
		}
		
	}
	
	
}
